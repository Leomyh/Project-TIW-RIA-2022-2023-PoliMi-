{ // avoid variables ending up in the global scope

	// page components
	let registerwizard,
		pageOrchestrator = new PageOrchestrator(); // main controller

	window.addEventListener("load", () => {
		pageOrchestrator.start(); // initialize the components
		pageOrchestrator.refresh();
	}, false);

	function RegisterWizard(registerwizard, errormessage) {
		this.registerwizard = registerwizard;
		this.errormessage = errormessage;

		this.registerEvents = function(orchestrator) {

			Array.from(this.registerwizard.querySelectorAll("input[type='button'].form_backbutton, input[type='button'].form_nextbutton")).forEach(b => {
				b.addEventListener("click", (e) => {
					var eventfieldset = e.target.closest("fieldset"),
						valid = true;

					if (e.target.className == "form_nextbutton") {
						for (i = 0; i < eventfieldset.elements.length; i++) {
							if (!eventfieldset.elements[i].checkValidity()) {
								eventfieldset.elements[i].reportValidity();
								valid = false;
								break;
							}
						}
					}
					if (valid) {
						this.changeStep(e.target.parentNode, (e.target.className === "form_nextbutton") ? e.target.parentNode.nextElementSibling : e.target.parentNode.previousElementSibling);
					}

				}, false)
			});

			// Manage submit button
			this.registerwizard.querySelector("input[type='button'].form_submitbutton").addEventListener('click', (e) => {
				var eventfieldset = e.target.closest("fieldset"),
					valid = true;
				for (i = 0; i < eventfieldset.elements.length; i++) {
					if (!eventfieldset.elements[i].checkValidity()) {
						eventfieldset.elements[i].reportValidity();
						valid = false;
						break;
					}
				}

				if (valid) {
					var self = this;
					makeCall("POST", 'DoRegistration', e.target.closest("form"),
						function(req) {
							if (req.readyState == XMLHttpRequest.DONE) {
								var message = req.responseText;
								if (req.status == 200) {
									orchestrator.refresh();
									window.location.href = "SuccessfulReg.html";
								} else if (req.status == 403) {
									window.location.href = req.getResponseHeader("Location");
								}
								else {
									self.errormessage.textContent = message;
									self.reset();
								}
							}
						}
					);
				}


			});


			this.reset = function() {
				var fieldsets = document.querySelectorAll("#" + this.registerwizard.id + " fieldset");
				fieldsets[0].hidden = false;
				fieldsets[1].hidden = true;
				fieldsets[2].hidden = true;
				fieldsets[3].hidden = true;

			}

			this.changeStep = function(origin, destination) {
				origin.hidden = true;
				destination.hidden = false;
			}

		};
	}

	function PageOrchestrator() {
		let errormessage = document.getElementById("errorReg");

		this.start = function() {

			registerwizard = new RegisterWizard(document.getElementById("registerbutton"), errormessage);
			registerwizard.registerEvents(this);
		};

		this.refresh = function() {

			registerwizard.reset();
		};
	}
}
