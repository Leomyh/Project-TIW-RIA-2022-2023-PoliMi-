/**
 * 
 */
(function() {
    document.getElementById("loginButton").addEventListener('click' , (e) => {

        console.log("Login event!");
        //closest form
        let form = e.target.closest("form");

        //Check form validity
        if(form.checkValidity()){

            //call server
            makeCall("POST" , 'CheckLogin' , form ,
                function (x) {

                    if(x.readyState == XMLHttpRequest.DONE){
                        let message = x.responseText;
                        switch(x.status){
                            //If ok -> set the name in the session
                            case 200:
                                sessionStorage.setItem('jsonUser' , message);
                                window.location.href = "Home.html";
                                break;
                            //If ko -> show the error
                            default:
                                document.getElementById("errorAuth").textContent = message;
                                break;
                        }
                    }
                }
            );
        }else{
            form.reportValidity();
        }
    });
})();