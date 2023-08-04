/**
 *
 */
{
	var currentUser;
	//Page components
	let menu, homePage, orderPage, cartPage, resultPage, searchBar, cartManager, myCart;
	var pageOrchestrator = new PageOrchestrator();

	window.addEventListener("load", () => {
		if (sessionStorage.getItem("jsonUser") == null) {
			window.location.href = "Index.html";

		} else {
			console.log("SessionStorage 'jsonUser':" + sessionStorage.getItem("jsonUser"));
			pageOrchestrator.start();
			pageOrchestrator.refresh("home");
		}
	}, false);


	function MenuList(_alert, _menulist) {
		this.alert = _alert;
		this.menulist = _menulist;
		console.log("Menu list created");

		this.show = function() {
			var home, hometext, order, ordertext, cart, carttext;

			this.menulist.innerHTML = "";
			var self = this.menulist;

			//-----------------------------------------------------
			home = document.createElement("a");
			hometext = document.createTextNode("HOME");
			home.appendChild(hometext);
			//Click Event Home
			home.addEventListener("click", () => {

				pageOrchestrator.refresh("home");

			}, false);
			home.href = "#";
			self.appendChild(home);
			//---------------------------------------------------------
			order = document.createElement("a");
			ordertext = document.createTextNode("ORDER");
			order.appendChild(ordertext);
			//Click Event Order
			order.addEventListener("click", () => {

				pageOrchestrator.refresh("orders");


			}, false);
			order.href = "#";
			self.appendChild(order);
			//-------------------------------------------
			cart = document.createElement("a");
			carttext = document.createTextNode("CART");
			cart.appendChild(carttext);
			//Click Event Cart
			cart.addEventListener("click", () => {

				pageOrchestrator.refresh("cart");

			}, false);
			cart.href = "#";
			self.appendChild(cart);

			this.menulist.style.visibility = "visible";

		};


		this.update = function() {
			this.alert = _alert;
			this.menulist = _menulist;
			console.log("Menu list updated");

			this.show = function() {
				var home, hometext, order, ordertext, cart, carttext;

				this.menulist.innerHTML = "";
				var self = this.menulist;

				//-----------------------------------------------------
				home = document.createElement("a");
				hometext = document.createTextNode("HOME");
				home.appendChild(hometext);
				home.addEventListener("click", () => {

					pageOrchestrator.refresh("home");


				}, false);
				home.href = "#";
				self.appendChild(home);
				//---------------------------------------------------------
				order = document.createElement("a");
				ordertext = document.createTextNode("ORDER");
				order.appendChild(ordertext);
				order.addEventListener("click", () => {

					pageOrchestrator.refresh("orders");

				}, false);
				order.href = "#";
				self.appendChild(order);
				//-------------------------------------------
				cart = document.createElement("a");
				carttext = document.createTextNode("CART");
				cart.appendChild(carttext);
				cart.addEventListener("click", () => {

					pageOrchestrator.refresh("cart");
				}, false);
				cart.href = "#";
				self.appendChild(cart);

				this.menulist.style.visibility = "visible";

			}

		}

	}


	function HomePage(_alert, _home_div) {
		this.home_div = _home_div;
		var self = this;
		//SHOW HOME
		this.show = function() {
			searchBar.show();

			makeCall("GET", "GetLastSeen", null,
				function(req) {
					if (req.readyState == 4) {
						var products = req.responseText;
						switch (req.status) {
							case 200:
								lastSeenList = JSON.parse(products);
								if (lastSeenList.length == 0) {
									alert("Chronology error");
									return;
								}
								self.update(lastSeenList);
								break;
							case 500:
								alert("Chronology Undefined Error");
								break;
							default:
								alert("Default Error");
								break;

						}
					}

				});


		}

		this.update = function(lastSeenList) {
			var product, image, productText;
			this.home_div.innerHTML = "";
			var self = this;

			self.home_div.classList.add("image_section");
			var divHome = document.createElement("div");
			divHome.classList.add("image_container");
			this.home_div.appendChild(divHome);

			lastSeenList.forEach(function(element) {

				product = document.createElement("div");
				product.classList.add("product_item");
				image = document.createElement("img");
				image.src = element.photo;
				image.style.maxWidth = "180px";
				image.style.maxHeight = "180px";
				product.appendChild(image);
				productText = document.createElement("p");
				productText.textContent = element.name;
				product.appendChild(productText);
				divHome.appendChild(product);
			});

			self.home_div.style.display = "block";
		}

		this.reset = function() {
			searchBar.reset();
			this.home_div.style.display = "none";

		}

	}

	function Search(_alert, _search_div) {
		this.alert = _alert;
		this.search_div = _search_div;

		this.show = function() {
			var self = this;
			self.update();

		}

		this.update = function() {
			var self = this;
			self.search_div.style.display = "block";

		}

		this.reset = function() {
			this.search_div.style.display = "none";
		}

		this.registerEvents = function() {
			this.search_div.querySelector("input[type='button'].button_search").addEventListener('click', (e) => {

				var form = e.target.closest("form");

				if (form.checkValidity()) {
		
					resultToReport = form.querySelector("input[type = 'text'].input_search").value;

					console.log("Search is: " + resultToReport);
					makeCall("POST", 'GetProducts', form,
						function(req) {

							if (req.readyState == 4) {
								var productslist = req.responseText;
								switch (req.status) {
									case 200:
										resultPage.show(productslist, resultToReport);
										break;
									case 400:
										alert("Server error");
										break;
									case 204:
										alert("No product found");
										break;
									default:
										alert("Error");
									
								}
							}
						}
					);
				} else {
					form.reportValidity();
				}
			});

		}

	}

	function ResultPage(_alert, _result_div) {
		this.alert = _alert;
		this.result_div = _result_div;

		this.show = function(result, resultToReport) {
			console.log("Show Result");
			var self = this;
			productList = JSON.parse(result);
			self.update(productList, resultToReport);
			homePage.reset();
			orderPage.reset();
			cartPage.reset();
			searchBar.reset();

		}

		this.update = function(productList, keyword) {
			this.result_div.innerHTML = "";
			var self = this;

			var resultText = document.createElement("h2");
			resultText.textContent = "Search results for: " + keyword;
			this.result_div.appendChild(resultText);
			var resultTable = document.createElement("table");
			resultTable.classList.add("product_table");
			var resultThead = document.createElement("thead");
			var resultTrHead = document.createElement("tr");
			var thCode = document.createElement("th");
			thCode.textContent = "Code";
			resultTrHead.appendChild(thCode);
			var thProductName = document.createElement("th");
			thProductName.textContent = "Product Name";
			resultTrHead.appendChild(thProductName);
			var thPrice = document.createElement("th");
			thPrice.textContent = "Price";
			resultTrHead.appendChild(thPrice);
			var thPicture = document.createElement("th");
			thPicture.textContent = "Picture";
			resultTrHead.appendChild(thPicture);
			var thAction = document.createElement("th");
			thAction.textContent = "Action";
			resultTrHead.appendChild(thAction);
			var thMore = document.createElement("th");
			thMore.textContent = "More Supplier";
			resultTrHead.appendChild(thMore);
			resultThead.appendChild(resultTrHead);
			resultTable.appendChild(resultThead);
			var resultTBody = document.createElement("tbody");

			productList.forEach(function(product) {

				var resultTab = document.createElement("tr");
				var resultCodeProduct = document.createElement("td");
				resultCodeProduct.style.width = "80px";
				resultCodeProduct.textContent = product.code;
				resultTab.appendChild(resultCodeProduct);
				var resultNameProduct = document.createElement("td");
				resultNameProduct.textContent = product.name;
				resultTab.appendChild(resultNameProduct);
				var resultPriceProduct = document.createElement("td");
				resultPriceProduct.textContent = "€ " + parseFloat(product.minPrice).toFixed(2);
				resultTab.appendChild(resultPriceProduct);
				var resultImgProduct = document.createElement("td");
				resultImgProduct.classList.add("image_td");
				var resultImgSrc = document.createElement("img");
				resultImgSrc.src = product.photo;
				resultImgSrc.style.maxWidth = "160px";
				resultImgSrc.style.maxHeight = "160px";
				resultImgProduct.appendChild(resultImgSrc);
				resultTab.appendChild(resultImgProduct);
				var resultClicked = document.createElement("td");
				var resultClickedForm = document.createElement("form");
				resultClickedForm.id = "supplier_expand";
				resultClickedForm.action = "#";
				var clickActionHidden = document.createElement("input");
				clickActionHidden.id = "product_code";
				clickActionHidden.name = "product_code";
				clickActionHidden.type = "hidden";
				clickActionHidden.value = product.code;
				resultClickedForm.appendChild(clickActionHidden);

				var buttonShow = document.createElement("input");
				buttonShow.classList.add("show_more");
				buttonShow.type = "button";
				buttonShow.name = "submit"
				buttonShow.id = product.code;
				buttonShow.value = "Show More";
				resultClickedForm.appendChild(buttonShow);
				resultClicked.appendChild(resultClickedForm);
				resultTab.appendChild(resultClicked);
				var fragmentCell = document.createElement("td");
				var fragmentDiv = document.createElement("div");
				fragmentDiv.classList.add("scrollable-content");
				var selectedDiv = document.createElement("div");
				selectedDiv.id = "id_selectionShow";
				var selectedTable = document.createElement("table");
				selectedDiv.appendChild(selectedTable);
				var selectedTbody = document.createElement("tbody");
				selectedTable.appendChild(selectedTbody);
				var selectedTr = document.createElement("tr");
				selectedTbody.appendChild(selectedTr);
				fragmentDiv.appendChild(selectedDiv);

				fragmentCell.appendChild(fragmentDiv);
				resultTab.appendChild(fragmentCell);
				resultTBody.appendChild(resultTab);
				//call ClickProduct
				buttonShow.addEventListener("click", function() {
					var data = new FormData();
					data.append("product_code", product.code);
					if (!product.isSelected) {
						fetch('ClickProduct', {
							method: 'POST',
							body: data

						}).catch(function() {
							alert("Fetch Error");
						}).then(response => {
							switch (response.status) {
								case 200:
									var supplierFragment = new SupplierFragment(_alert, selectedTr);
									if (!product.isSelected) {
										buttonShow.value = "Close";

										var suppliersSupplierGroup = product.offerList.map(offer => offer.supplier);
										var suppliersOfferGroup = product.offerList.map(offer => offer.offer);
										supplierFragment.show(product.name, product.code, suppliersSupplierGroup, suppliersOfferGroup, product.description, product.category);

									}
									else {
										buttonShow.value = "Show More";
										supplierFragment.reset();
									}
									//change states
									product.isSelected = !product.isSelected;
									break;
								case 204:
									alert("Click Failed");
									break;
								case 500:
									console.log("SQL exception");
									break;
							}


						});
					}


				}, false);

			});

			resultTable.appendChild(resultTBody);
			this.result_div.appendChild(resultTable);
			self.result_div.style.display = "block";
		}

		this.reset = function() {
			this.result_div.style.display = "none";
		}

	}

	function SupplierFragment(_alert, _fragment_div) {
		this.alert = _alert;
		this.fragment_div = _fragment_div;

		this.show = function(productName, productCode, supplierSuppliers, supplierOffers, productDescription, productCategory) {

			var self = this;
			self.update(productName, productCode, supplierSuppliers, supplierOffers, productDescription, productCategory);

		};

		this.update = function(productName, productCode, supplierSuppliers, supplierOffers, productDescription, productCategory) {

			this.fragment_div.innerHTML = "";

			var self = this;

			supplierSuppliers.forEach(function(supplierSupplier, index) {


				var supplierTd = document.createElement("td");
				var supplierCardDiv = document.createElement("div");
				supplierCardDiv.classList.add("supplier_card");
				var supplierProductInfo = document.createElement("div");
				supplierProductInfo.classList.add("product_info");
				var supplierHead = document.createElement("h2");
				supplierHead.textContent = supplierSupplier.name;
				supplierProductInfo.appendChild(supplierHead);
				var pRating = document.createElement("p");
				pRating.textContent = "_Rating: " + supplierSupplier.rating + "_";
				supplierProductInfo.appendChild(pRating);
				var ulSupplier1 = document.createElement("ul");

				supplierSupplier.ranges.forEach(function(range) {
					var liSupplier1 = document.createElement("li");
					liSupplier1.textContent = "Qty: from " + range.minQty + " to " + range.maxQty;
					var pSupplier1 = document.createElement("p");
					var smallSupplier1 = document.createElement("small");
					smallSupplier1.textContent = "Price: " + range.price + " €";
					pSupplier1.appendChild(smallSupplier1);
					liSupplier1.appendChild(pSupplier1);
					ulSupplier1.appendChild(liSupplier1);
				});

				var ulSupplier2 = document.createElement("ul");
				var liSupplier2 = document.createElement("li");
				liSupplier2.textContent = "Free shipping Threshold: ";
				ulSupplier2.appendChild(liSupplier2);
				var pSupplier2 = document.createElement("p");
				var smallSupplier2 = document.createElement("small");
				smallSupplier2.textContent = supplierSupplier.freeShipping != 0 ? supplierSupplier.freeShipping + "€" : 'ND';
				pSupplier2.appendChild(smallSupplier2);
				ulSupplier2.appendChild(pSupplier2);
				supplierProductInfo.appendChild(ulSupplier1);
				supplierProductInfo.appendChild(ulSupplier2);

				var supplierForm = document.createElement("form");
				supplierForm.action = "#";
				supplierForm.id = "id_putCart";

				var supplierDivProduct1 = document.createElement("div");
				supplierDivProduct1.classList.add("div_product");
				var supplierDivProductHead3 = document.createElement("h3");
				supplierDivProductHead3.textContent = "Price";
				supplierDivProduct1.appendChild(supplierDivProductHead3);
				var supplierDivProductHead2 = document.createElement("h2");
				var supPrice = parseFloat(supplierOffers[index].price);
				supplierDivProductHead2.textContent = supPrice.toFixed(2) + " €"
				supplierDivProduct1.appendChild(supplierDivProductHead2);
				var supplierDivProcductDiv = document.createElement("div");
				supplierDivProduct1.appendChild(supplierDivProcductDiv);
				var supplierDivProductDivDiv = document.createElement("div");
				supplierDivProductDivDiv.classList.add("product_input");
				var supplierDivProductDivDivInput = document.createElement("input");
				supplierDivProductDivDivInput.type = "number";
				supplierDivProductDivDivInput.min = "1";
				supplierDivProductDivDivInput.max = "999";
				supplierDivProductDivDivInput.step = "1";
				supplierDivProductDivDivInput.name = "quantity";
				supplierDivProductDivDivInput.placeholder = "Quantity";
				supplierDivProductDivDivInput.required = true;
				supplierDivProductDivDiv.appendChild(supplierDivProductDivDivInput);
				supplierDivProcductDiv.appendChild(supplierDivProductDivDiv);
				var supplierDivProduct2 = document.createElement("div");
				supplierDivProduct2.classList.add("div_product");
				var supplierDivProductDivDiv2Add = document.createElement("product_add");
				supplierDivProduct2.appendChild(supplierDivProductDivDiv2Add);
				var supplierDivProductDivDiv2AddInput2 = document.createElement("input");
				supplierDivProductDivDiv2AddInput2.type = "hidden";
				supplierDivProductDivDiv2AddInput2.name = "ref_supplier_code";
				supplierDivProductDivDiv2AddInput2.value = supplierSupplier.code;
				supplierDivProductDivDiv2Add.appendChild(supplierDivProductDivDiv2AddInput2);
				var supplierDivProductDivDiv2AddButton = document.createElement("input");
				supplierDivProductDivDiv2AddButton.classList.add("product_add_button");
				supplierDivProductDivDiv2AddButton.type = "button";
				supplierDivProductDivDiv2AddButton.name = "Put in Cart";
				supplierDivProductDivDiv2AddButton.value = "Put in Cart";

				supplierDivProductDivDiv2AddButton.addEventListener("click", function() {

					var regex = /^[1-9][0-9]*$/;
					if (!regex.test(supplierDivProductDivDivInput.value)) {
						alert("Please enter a valid number");
						supplierDivProductDivDivInput.value = "";
					}
					else {
						myCart = cartManager.createCart(supplierSupplier.code, supplierSupplier.freeShipping, supplierSupplier.name);
						supplierSupplier.ranges.forEach(function(range) {
							myCart.addRanges(range.maxQty, range.minQty, range.price);

						});
						if (myCart.checkMaxium(productCode, supplierDivProductDivDivInput.value)) {
							myCart.addProduct(productCode, supplierDivProductDivDivInput.value, supPrice.toFixed(2), productName, productDescription, productCategory);
							cartManager.loadCart(supplierSupplier.code, supplierSupplier.freeShipping);
							// Show Cart
							pageOrchestrator.refresh("cart");
						}
						else
							supplierDivProductDivDivInput.value = "";
					}

				}, false);


				supplierDivProductDivDiv2Add.appendChild(supplierDivProductDivDiv2AddButton);
				var supplierDivProduct2p1 = document.createElement("p");
				var supplierDivProduct2small1 = document.createElement("small");
				if (cartManager.getCart(supplierSupplier.code) != null)
					supplierDivProduct2small1.textContent = "Total qty: " + cartManager.getCart(supplierSupplier.code).getTotalQuantity();
				else
					supplierDivProduct2small1.textContent = "Total qty: 0 ";


				supplierDivProduct2p1.appendChild(supplierDivProduct2small1);
				var supplierDivProduct2p2 = document.createElement("p");
				var supplierDivProduct2small2 = document.createElement("small");
				if (cartManager.getCart(supplierSupplier.code) != null)
					supplierDivProduct2small2.textContent = "Total price: " + cartManager.getCart(supplierSupplier.code).calculateTotalPrice().toFixed(2);
				else
					supplierDivProduct2small2.textContent = "Total price: 0";

				supplierDivProduct2p2.appendChild(supplierDivProduct2small2);
				supplierDivProduct2.appendChild(supplierDivProduct2p1);
				supplierDivProduct2.appendChild(supplierDivProduct2p2);

				supplierForm.appendChild(supplierDivProduct1);
				supplierForm.appendChild(supplierDivProduct2);
				supplierCardDiv.appendChild(supplierProductInfo);
				supplierCardDiv.appendChild(supplierForm);
				supplierTd.appendChild(supplierCardDiv);

				self.fragment_div.appendChild(supplierTd);

				supplierDivProduct2small1.addEventListener('mouseover', function() {
					//show details
					var detailsCart;

					if (cartManager.getCart(supplierSupplier.code) != null) {
						for (let product of cartManager.getCart(supplierSupplier.code).products) {
							if (detailsCart == null)
								detailsCart = "- Name: " + product.name + " (" + product.code + ") " + "\n| Category: " + product.category + "\n| Num: " + product.quantity + " (" + product.price + "€) " + "\n| Description: " + product.description + "\n";
							else
								detailsCart += "- Name: " + product.name + " (" + product.code + ") " + "\n| Category: " + product.category + "\n| Num: " + product.quantity + " (" + product.price + "€) " + "\n| Description: " + product.description + "\n";
						}
					}
					else {
						detailsCart = "ND";
					}

					var tooltip = document.createElement('div');
					tooltip.textContent = "Supplier: " + supplierSupplier.name + " (" + supplierSupplier.code + ")\n " +
						"Cart Content:\n " + detailsCart;

					tooltip.style.position = 'absolute';
					tooltip.style.backgroundColor = 'blue';
					tooltip.style.color = 'white';
					tooltip.style.padding = '5px';
					tooltip.style.top = (event.pageY - 20) + 'px';
					tooltip.style.left = event.pageX + 'px';
					tooltip.style.zIndex = '9999';
					tooltip.style.whiteSpace = 'pre-line';

					supplierDivProduct2small1.appendChild(tooltip);
					// leave mouse so hidden 
					supplierDivProduct2small1.addEventListener('mouseout', function() {
						if (tooltip && tooltip.parentNode) {
							tooltip.parentNode.removeChild(tooltip);
						}

					});
				});
			});

			self.fragment_div.style.display = "block";

		};

		this.reset = function() {
			this.fragment_div.style.display = "none";
		}

	};


	function CartPage(_alert, _cart_div) {
		this.alert = _alert;
		this.cart_div = _cart_div;
		//var self = this;
		this.show = function() {
			console.log("Cart Show");
			var self = this;
			var allCarts = cartManager.getAllCarts();
			self.update(allCarts);
			resultPage.reset();

		}

		this.update = function(allCarts) {
			this.cart_div.innerHTML = "";
			var self = this;

			var cartDivHead = document.createElement("div");
			cartDivHead.classList.add("cart_list");
			this.cart_div.appendChild(cartDivHead);

			for (let cart of allCarts) {

				var cartDiv = document.createElement("div");
				cartDiv.classList.add("cart_tab");
				var cartHead = document.createElement("h2");
				cartHead.textContent = cart.supplierName;
				cartDiv.appendChild(cartHead);
				var cartTable = document.createElement("table");
				cartTable.classList.add("myCart_table");
				var cartTr1 = document.createElement("tr");
				var cartThName = document.createElement("th");
				cartThName.textContent = "Product Name";
				cartTr1.appendChild(cartThName);
				var cartThQuantity = document.createElement("th");
				cartThQuantity.textContent = "Quantity";
				cartTr1.appendChild(cartThQuantity);
				var cartThPrice = document.createElement("th");
				cartThPrice.textContent = "Price";
				cartTr1.appendChild(cartThPrice);
				cartTable.appendChild(cartTr1);
				var cartTbody = document.createElement("tbody");

				for (let product of cart.products) {
					var productTr = document.createElement("tr");
					var productTdName = document.createElement("td");
					productTdName.textContent = product.name;
					productTr.appendChild(productTdName);
					var productTdQuantity = document.createElement("td");
					productTdQuantity.textContent = product.quantity;
					productTr.appendChild(productTdQuantity);
					var productTdPrice = document.createElement("td");
					productTdPrice.textContent = product.price + " €";
					productTr.appendChild(productTdPrice);
					cartTbody.appendChild(productTr);
				}


				var cartTr2 = document.createElement("tr");
				var cartThShippingFee = document.createElement("th");
				cartThShippingFee.textContent = "Shipping Fee";
				cartTr2.appendChild(cartThShippingFee);
				var cartTh_ = document.createElement("th");
				cartTh_.textContent = "-";
				cartTr2.appendChild(cartTh_);
				var cartThCost = document.createElement("th");
				let feeTemp = cart.calculateShippingFee();
				cartThCost.textContent = feeTemp + " €";
				cartTr2.appendChild(cartThCost);
				cartTbody.appendChild(cartTr2);

				var cartTr3 = document.createElement("tr");
				var cartThTotal = document.createElement("th");
				cartThTotal.textContent = "Total";
				cartTr3.appendChild(cartThTotal);
				var cartThTotalQualtity = document.createElement("th");
				let quantityTemp = cart.getTotalQuantity();
				cartThTotalQualtity.textContent = quantityTemp;
				cartTr3.appendChild(cartThTotalQualtity);
				var cartThTotalCost = document.createElement("th");
				cartThTotalCost.textContent = cart.calculateTotalPrice().toFixed(2) + " €";
				cartTr3.appendChild(cartThTotalCost);

				cartTbody.appendChild(cartTr3);
				cartTable.appendChild(cartTbody);
				cartDiv.appendChild(cartTable);
				var cartForm = document.createElement("form");
				var cartFormButton = document.createElement("input");
				cartFormButton.type = "button";
				cartFormButton.value = "PLACE ORDER";
				cartFormButton.name = "place_order";
				cartFormButton.id = "place_order_button";
				cartFormButton.classList.add("cart_checkout");
				// Event Click Place Order
				cartFormButton.addEventListener("click", function() {

					var productIDList = [];
					var quantityList = [];
					//Create a list of product and quantity
					for (let product of cart.products) {
						productIDList.push(product.code);
						quantityList.push(product.quantity);
					}
					// Data to send in JSON format
					var data = {
						email: currentUser.useremail,
						address: currentUser.useraddress,
						supplierCode: cart.supplierCode,
						productList: productIDList,
						quantityList: quantityList,
						totalPrice: cart.calculateTotalPrice(),
						shippingFee: cart.calculateShippingFee()
					};


					fetch('PlaceOrder', {
						method: 'POST',
						body: JSON.stringify(data)

					}).then(response => {
						if (response.ok) {
							console.log("Ordered");
							//Order Placed
							//Delete cart
							cartManager.deleteCart(cart.supplierCode);
							pageOrchestrator.refresh("cart");
						} else {
							alert("Failed to Order");
							pageOrchestrator.refresh("cart");
						}
					}).catch(function(error) {
						alert("error");
						console.log("Order error:" + error);
					});


				});

				cartForm.appendChild(cartFormButton)
				cartDiv.appendChild(cartForm);
				cartDivHead.appendChild(cartDiv);

			}
			if (allCarts.length == 0) {
				var cartEmpty = document.createElement("h2");
				cartEmpty.textContent = "Cart Empty";
				cartEmpty.style.color = "gray";
				cartEmpty.style.textAlign = "center";
				this.cart_div.appendChild(cartEmpty);
			}
			self.cart_div.style.display = "block";

		}

		this.reset = function() {
			this.cart_div.style.display = "none";
		}

	}


	function OrderPage(_alert, _order_div) {
		this.alert = _alert;
		this.order_div = _order_div;
		//SHOW Orders
		this.show = function(orderList) {
			var self = this;
			makeCall("GET", "ViewOrder", null,
				function(req) {
					if (req.readyState == 4) {
						var message = req.responseText;
						if (req.status == 200) {
							orderList = JSON.parse(message);
							self.update(orderList);
						} else if (req.status == 401) {
							alert("No user error");
						} else if (req.status == 500) {
							console.log("Query failed");
						}
						else {
							self.alert.textContent = message;

						}
					}
				}
			);
		};

		this.update = function(orderList) {
			this.order_div.innerHTML = "";
			var self = this;
			if (orderList.length > 0) {

				self.order_div.classList.add("results_section");
				var orderHistoryHead = document.createElement("h2");
				orderHistoryHead.textContent = "Orders History";
				this.order_div.appendChild(orderHistoryHead);
				var mainTableOrder = document.createElement("table");
				mainTableOrder.classList.add("myOrder_table");
				var orderThead = document.createElement("thead");
				var orderTr = document.createElement("tr");
				var orderTh1 = document.createElement("th");
				orderTh1.textContent = "Date";
				orderTr.appendChild(orderTh1);
				var orderTh2 = document.createElement("th");
				orderTh2.textContent = "Code";
				orderTr.appendChild(orderTh2);
				var orderTh3 = document.createElement("th");
				orderTh3.textContent = "Supplier";
				orderTr.appendChild(orderTh3);
				var orderTh4 = document.createElement("th");
				orderTh4.textContent = "Product";
				orderTr.appendChild(orderTh4);
				var orderTh5 = document.createElement("th");
				orderTh5.textContent = "Total Payment";
				orderTr.appendChild(orderTh5);
				var orderTh6 = document.createElement("th");
				orderTh6.textContent = "Address of shipping";
				orderTr.appendChild(orderTh6);
				orderThead.appendChild(orderTr);
				mainTableOrder.appendChild(orderThead);
				var orderTbody = document.createElement("tbody");

				orderList.forEach(function(element) {
					var orders = document.createElement("tr");
					var shippingDate = document.createElement("td");
					shippingDate.textContent = element.shippingDate;
					orders.appendChild(shippingDate);
					var codeOrder = document.createElement("td");
					codeOrder.textContent = element.code;
					orders.appendChild(codeOrder);
					var codeSupplier = document.createElement("td");
					codeSupplier.textContent = element.supplierCode;
					orders.appendChild(codeSupplier);

					var productListTd = document.createElement("td");
					orders.appendChild(productListTd);
					var productList = document.createElement("table");
					productList.classList.add("myOrder_table");
					element.productNameList.forEach(function(productName) {
						var productHead = document.createElement("tr");
						var productCell = document.createElement("td");
						productCell.textContent = productName;
						productHead.appendChild(productCell);
						productList.appendChild(productHead);

					});
					productListTd.appendChild(productList);

					var totalPayment = document.createElement("td")
					totalPayment.textContent = element.totalValue + " €";
					orders.appendChild(totalPayment);
					var address = document.createElement("td")
					address.textContent = element.userAddress;
					orders.appendChild(address);

					orderTbody.appendChild(orders);

				});

				mainTableOrder.appendChild(orderTbody)
				this.order_div.appendChild(mainTableOrder);
			} else {
				var orderEmpty = document.createElement("h2");
				orderEmpty.textContent = "No orders yet";
				orderEmpty.style.color = "gray";
				orderEmpty.style.textAlign = "center";
				this.order_div.appendChild(orderEmpty);

			}

			self.order_div.style.display = "block";
		}

		this.reset = function() {

			this.order_div.style.display = "none";

		}

	}

	function CurrentUser(_json, messagecontainer) {

		var user = JSON.parse(_json);
		this.username = user.name;
		this.useremail = user.email;
		this.useraddress = user.address;
		console.log("Current Username:" + this.username);
		this.show = function() {
			messagecontainer.textContent = this.username;
		}

	}


	function PageOrchestrator() {
		var alertContainer = document.getElementById("alertContainer");

		this.start = function() {
			currentUser = new CurrentUser(sessionStorage.getItem("jsonUser"), document.getElementById("name"));
			currentUser.show();

			menu = new MenuList(
				alertContainer,
				document.getElementById("id_menu"));
			menu.show();

			searchBar = new Search(
				alertContainer,
				document.getElementById("id_search")
			);

			searchBar.show();
			searchBar.registerEvents(this);

			homePage = new HomePage(alertContainer, document.getElementById("id_home"));
			homePage.show();

			orderPage = new OrderPage(alertContainer, document.getElementById("id_order"));
			orderPage.reset();

			cartPage = new CartPage(alertContainer, document.getElementById("id_cart"));

			cartManager = new CartManager(currentUser.useremail);

			// load carts in local Storage
			cartManager.updateCarts();

			// get allCarts 
			var allCarts = cartManager.getAllCarts();

			resultPage = new ResultPage(alertContainer, document.getElementById("id_result"));
			resultPage.reset();

			cartPage.update(allCarts);
			cartPage.reset();

		}

		this.refresh = function(page) {
			homePage.reset();
			orderPage.reset();
			cartPage.reset();
			resultPage.reset();
			switch (page) {
				case "home":
					homePage.show();
					break;
				case "orders":
					orderPage.show();
					break;
				case "cart":
					cartPage.show();
					break;
				default:
					homePage.show();
			}

		}

	}

}


