/**
 * 
 */


const spinnerHTML = '<div class=\x22lds-ring\x22><div></div><div></div><div></div><div></div></div>';
const failHTML = '<div style=\x22color:red\x22>server failed to load</div>';
//Object { creditCardType: "MasterCard", creditCardNumber: "5114170954034388", creditCardExpiry: "11/2024", creditCardCVV2: "412" }
const creditCardHTML=(creditCard)=>{
return	`<div><span class=\x22label\x22>Type</span>:<span class=\x22value\x22>${creditCard.creditCardType}</span></div>`+
	`<div><span class=\x22label\x22>Number</span>:<span class=\x22value\x22>${creditCard.creditCardNumber}</span></div>`+
	`<div><span class=\x22label\x22>Expiry</span>:<span class=\x22value\x22>${creditCard.creditCardExpiry}</span></div>`+
	`<div><span class=\x22label\x22>CVV2</span>:<span class=\x22value\x22>${creditCard.creditCardCVV2}</span></div>`
}

const loginSignUpHTML=()=>{
return	`<fieldset id=\x22loginSignupBox\x22><legend>Login/Signup</legend><div id=\x22loginSignup\x22>`+
`<button name=\x22goToSignIn\x22 id=\x22goToSignIn\x22  onclick=\x22window.location='/BookStore/SignIn';\x22>Sign In</button>`+
`<button name=\x22goToRegister\x22 id=\x22goToRegister\x22  onclick=\x22window.location='/BookStore/Register';\x22>Register</button>`+
`</div></fieldset>`
}

const addressHTML=(address)=>{
return	`<div><span class=\x22label\x22>Number</span>:<span class=\x22value\x22>${address.number}</span></div>`+
	`<div><span class=\x22label\x22>Street</span>:<span class=\x22value\x22>${address.street}</span></div>`+
	`<div><span class=\x22label\x22>City</span>:<span class=\x22value\x22>${address.city}</span></div>`+
	`<div><span class=\x22label\x22>Province</span>:<span class=\x22value\x22>${address.province}</span></div>`+
	`<div><span class=\x22label\x22>Country</span>:<span class=\x22value\x22>${address.country}</span></div>`+
	`<div><span class=\x22label\x22>Postal Code</span>:<span class=\x22value\x22>${address.postalCode}</span></div>`

}
const signInHtml=`<fieldset id=\x22customerCheckoutLogin\x22><legend>Login</legend>
<label>Username</label><input type=\x22text\x22 id=\x22inputUserNameCheckout\x22/>
<label>Password</label><input type=\x22text\x22 id=\x22inputPasswordCheckout\x22/>
</fieldset>`;

const returnToMainHTML=`<div><button onclick=\x22window.location='/BookStore/MainPage';\x22>Go to Store front</button></div>`
const backHTML=`<div><button onclick=\x22history.back()\x22;\x22>Go to Store front</button></div>`
const registerHtml=`<fieldset id=\x22customerCheckoutRegister\x22><legend>New Registration</legend>
<label>Given Name</label><input type=\x22text\x22 id=\x22inputGivenNameCheckout\x22/>
<label>Sur Name</label><input type=\x22text\x22 id=\x22inputSurNameCheckout\x22/>
<label>Email</label><input type=\x22text\x22 id=\x22inputEmailCheckout\x22/>
<label>Username</label><input type=\x22text\x22 id=\x22inputUserNameCheckout\x22/>
<label>Password</label><input type=\x22password\x22 id=\x22inputPasswordCheckout\x22/>
</fieldset>`;
const registerRequest=()=>{
	if( document.getElementById("customerCheckoutLogin")){
	document.getElementById("visitorCheckout").innerHTML=registerHtml;
	}else if( document.getElementById("customerCheckoutRegister")){
	alert("registering")
	register();
	}else{
	document.getElementById("visitorCheckout").innerHTML=registerHtml;
	}
}
const loginRequest=()=>{
	if( document.getElementById("customerCheckoutRegister")){
	document.getElementById("visitorCheckout").innerHTML=signInHtml;
	}else if(document.getElementById("customerCheckoutLogin")){
	alert("signing in")
	login()
	}else{
	document.getElementById("visitorCheckout").innerHTML=signInHtml;
	}
}
const login=()=>{
	document.getElementById("address").innerHTML=spinnerHTML;	
	document.getElementById("creditCard").innerHTML=spinnerHTML;	
    let request = new XMLHttpRequest()
    request.open("GET", address+'?login=true', true);
    request.onreadystatechange = ()=>{
            if ((request.readyState == 4) && (request.status == 200)){            
                 let data=JSON.parse(request.responseText);
                
                 if(data.incorrectCredentials){
                 }
                 if(data.customer){
                 
                 }
            	  

            	     
            	               
    		}
    }
    pageLoadMain("/BookStore/PurchaseOrder")
    request.send(null);
}
const register=()=>{
	document.getElementById("address").innerHTML=spinnerHTML;	
	document.getElementById("creditCard").innerHTML=spinnerHTML;	
    let request = new XMLHttpRequest()
    request.open("GET", address+'?register=true', true);
    request.onreadystatechange = ()=>{
            if ((request.readyState == 4) && (request.status == 200)){            
                 let data=JSON.parse(request.responseText);    	  

            	     
            	               
    		}
    }
    pageLoadMain("/BookStore/PurchaseOrder")
    request.send(null);
}

const defaultAdressCheckHTML=		`<label for=\x22defaultAddressCheck\x22>use default address for shipping?`+
	`<input type=\x22checkbox\x22 id=\x22defaultAddressCheck\x22 name=\x22defaultAddressCheck\x22 onclick=\x22defaultAddressSelector();\x22></input>`+
	`</label>`;
	
const defaultCreditCardCheckHTML=	`<label for=\x22defaultCreditCardCheck\x22>use default credit card payment?`+
	`<input type=\x22checkbox\x22 id=\x22defaultCreditCardCheck\x22 name=\x22defaultCreditCardCheck\x22 onclick=\x22defaultCreditCardSelector();\x22></input>`+
	`</label>`;


const signInRegisterButtonsHTML=`
<button  id=\x22registerFromCheckout\x22onclick=\x22registerIframeRequest();\x22>Register</button>
<button  id=\x22signInFromCheckout\x22onclick=\x22loginIframeRequest();\x22>Sign In</button>`


const defaultAddressSelector=()=>{
	if(document.getElementById('defaultAddressCheck') && document.getElementById('defaultAddressCheck').checked){
            	   document.getElementById("newAddressBox").disabled=true;            	 
	}else{
		document.getElementById("newAddressBox").disabled=false;
	}
		document.getElementById("newAddressBox").classList.toggle('expand')
	document.getElementById("newAddressBox").classList.toggle('collapse')
}
const defaultCreditCardSelector=()=>{
	if(document.getElementById('defaultCreditCardCheck') && document.getElementById('defaultCreditCardCheck').checked){
	 document.getElementById("newCreditCardBox").disabled=true;            	 
	}else{
	 document.getElementById("newCreditCardBox").disabled=false;
	}
			document.getElementById("newCreditCardBox").classList.toggle('expand')
	document.getElementById("newCreditCardBox").classList.toggle('collapse')
}
const cartHTML=(cart)=>{
let html=`<table><tr><td>Cover</td><td>Title</td><td>Quantity</td> </tr>`;
cart.books.forEach(cartBook=>{
html+=`<tr><td><img class=\x22checkoutBookCover\x22 src=\x22/BookStore/res/book_images/covers/${cartBook.book.cover}\x22></img></td><td class=\x22checkoutBookTitle\x22>${cartBook.book.title}</td><td class=\x22checkoutBookAmount\x22>${cartBook.amount}</td> </tr>`
})
html+=`</table><div id=\x22checkoutTotal\x22>Total:${cart.total}<div>`
return html;

}


const purchaseOrderSubmissionGET =(address)=>{
	let isDefaultAddressChecked=document.getElementById('defaultAddress').checked;
	let isDefaultCreditCardChecked=document.getElementById('defaultCreditCard').checked;
	let isNewAddressChecked=document.getElementById('newAddress').checked;
	let isNewCreditCardChecked=document.getElementById('newCreditCard').checked;
    let request = new XMLHttpRequest()
    request.open("POST", address+`?defaultAddress=${isDefaultAddressChecked}&defaultCreditCard=${isDefaultCreditCardChecked}&newAddress=${isNewAddressChecked}&newCreditCard=${isNewCreditCardChecked}`, true);
    request.onreadystatechange = ()=>{
            if ((request.readyState == 4) && (request.status == 200)){
                 let data=JSON.parse(request.responseText);

    		}
    };
    request.send(null);
}

const validatePurchaseOrder =()=>{

	return true;
}

const toggleConfirmationOverlay=()=>{
	document.getElementById("confirmationResults").classList.toggle('frontOverlayElement')
	document.getElementById("confirmationInfo").classList.toggle('backOverlayElement')
	document.getElementById("confirmationResults").innerHTML=spinnerHTML;
	document.getElementById("editPurchaseOrder").disabled=true;
	document.getElementById("editCart").disabled=true;
	document.getElementById("confirmPurchaseOrder").disabled=true;
}

const registerIFrameHTML=`<iframe   id=\x22registerIFrame\x22 src=\x22/BookStore/html/Register.jspx\x22 onload=\x22watchRegisterFrame();\x22></iframe>`
const loginIFrameHTML=`<iframe  id=\x22loginIFrame\x22 src=\x22/BookStore/html/SignIn.jspx\x22 onload=\x22watchLoginFrame();\x22></iframe>`

const toggleIFrameOverlay=(iframeHTML)=>{
document.getElementById("iframeContainer").innerHTML=iframeHTML;
	document.getElementById("iframeContainer").classList.toggle('frontOverlayElement')
	document.getElementById("purchaseOrder").classList.toggle('backOverlayElement')

}

const watchRegisterFrame=()=>{
	let iframe = document.getElementById('registerIFrame');
	let registerDoc = iframe.contentDocument || iframe.contentWindow.document;
	registerDoc.getElementById("button-login").addEventListener('click', event => {
  checkLoginStatus()
});

}

const loginSignUpControl=()=>{
document.getElementById("purchaseOrder").classList.toggle('backOverlayElement')
//document.getElementById("iframeContainer").classList.toggle('frontOverlayElement')
document.getElementById("iframeContainer").innerHTML= `<div id=loginControlBox>${signInRegisterButtonsHTML}</div>`
document.getElementById("loginControlBox").classList.toggle('frontOverlayElement')

}
const registerIframeRequest=()=>{
document.getElementById("iframeContainer").classList.toggle('registerSignUpFrame')
document.getElementById("iframeContainer").innerHTML=registerIFrameHTML;
}
const loginIframeRequest=()=>{
document.getElementById("iframeContainer").classList.toggle('registerSignUpFrame')
document.getElementById("iframeContainer").innerHTML=loginIFrameHTML;
}
const loadRegisterFrame=()=>{

}
const loadLoginFrame=()=>{

}
const watchLoginFrame=()=>{
	let iframe = document.getElementById('loginIFrame');
	let registerDoc = iframe.contentDocument || iframe.contentWindow.document;
	registerDoc.getElementById("button-login").addEventListener('click', event => {
  checkLoginStatus()
});

}

const checkLoginStatus=()=>{
 let request = new XMLHttpRequest();
    request.open("GET", '/BookStore/PurchaseOrder'+'?ajax=true&checkoutInfo=true', true);
    request.onreadystatechange = ()=>{
            if ((request.readyState == 4) && (request.status == 200)){   
       
                 let data=JSON.parse(request.responseText);
                  console.log(data.customerNotExistError)
            	  if(!data.customerNotExistError){
            	  let child=document.getElementById('registerIFrame');
 					child.parentNode.removeChild(child);
 					alert("worked:")
 					location.reload();
 					
            	  }  
            	               
    		}
    }
    request.send(null);
}

const checkoutAttemptsHTML=(checkoutResponse)=>{
	return `<div>You have attempted to checkout ${checkoutResponse.numberAttempts} times, your payment method will be blocked for 1 hour after ${checkoutResponse.maxNumberAttempts} attempts</div>`
}

const confirmPurchaseOrderSubmission =(address)=>{
	toggleConfirmationOverlay()
    let request = new XMLHttpRequest()
    request.open("GET", address+'?ajax=true&checkoutComplete=true', true);
    request.onreadystatechange = ()=>{
            if ((request.readyState == 4) && (request.status == 200)){            
                 let data=JSON.parse(request.responseText);
                 if(data.paymentError){
                 	//document.getElementById("confirmationResults").innerHTML=data.paymentError+checkoutAttemptsHTML+backHTML;
                 	//history.back();
                 }else if(data.checkoutSuccess){
                 document.getElementById("confirmationResults").innerHTML=data.checkoutSuccess+returnToMainHTML;

                 //window.location='/BookStore/MainPage';
                 }

            	               
    		}
    }
    request.send(null);
    
}
const editPurchaseOrder =()=>{
	alert('going back to edit purchase order')
	history.back();
	return true;
}
const pageLoadMain =(address)=>{
	document.getElementById("addressBox").style.display='block';
	document.getElementById("creditCardBox").style.display='block';
	document.getElementById("submitPurchaseOrder").style.display='block';
	document.getElementById("address").innerHTML=spinnerHTML;	
	document.getElementById("creditCard").innerHTML=spinnerHTML;	


    let request = new XMLHttpRequest()
    request.open("GET", address+'?ajax=true&checkoutInfo=true', true);
    request.onreadystatechange = ()=>{
            if ((request.readyState == 4) && (request.status == 200)){            
                 let data=JSON.parse(request.responseText);

				loginSignUpControl()
                  if(data.emptyCartError){    
                  document.getElementById("newAddressBox").disabled=true;
            	  document.getElementById("newCreditCardBox").disabled=true;  
            	  document.getElementById("submitPurchaseOrder").disabled=true;            
                  alert(data.emptyCartError)	
                  history.back();
            	  } else {
            	 if(data.customer && data.customer.creditCard && !data.missingCreditCard){
                 document.getElementById("creditCard").innerHTML=creditCardHTML(data.customer.creditCard)+defaultCreditCardCheckHTML;
                  document.getElementById("defaultCreditCardCheck").checked=true
                 
                 defaultCreditCardSelector();
                 }else{
                 document.getElementById("creditCard").innerHTML=failHTML;	
                 }

                 if(data.customer && data.customer.address && !data.missingAddressComponents){
                 document.getElementById("address").innerHTML=addressHTML(data.customer.address)+defaultAdressCheckHTML;
                document.getElementById("defaultAddressCheck").checked=true
                defaultAddressSelector();
                 }else{
                 document.getElementById("address").innerHTML=failHTML;	
                 }
            	  if(data.customerNotExistError){
            	  document.getElementById("submitPurchaseOrder").style.display='none';
				  document.getElementById("addressBox").style.display='none';
				  document.getElementById("creditCardBox").style.display='none';
            	  alert("looks like you havent logged in, if you are registered please log in now, or sign up below")
            	  document.getElementById("newCustomerCheckoutButtons").innerHTML=signInRegisterButtonsHTML;
            	  }
            	  
            	  

            	  }    
            	               
    		}
    }
    request.send(null);
}
const pageLoadConfirmation =(address)=>{
	document.getElementById("confirmAddress").innerHTML=spinnerHTML;	
	document.getElementById("confirmCreditCard").innerHTML=spinnerHTML;	
	document.getElementById("confirmContents").innerHTML=spinnerHTML;
    let request = new XMLHttpRequest()
    request.open("GET", address+'?ajax=true&checkoutConfirmation=true', true);
    request.onreadystatechange = ()=>{
            if ((request.readyState == 4) && (request.status == 200)){            
                 let data=JSON.parse(request.responseText);
                  if(data.emptyCartError){    
                  document.getElementById("confirmAddressBox").disabled=true;
            	  document.getElementById("confirmCreditCardBox").disabled=true;  
            	  document.getElementById("confirmContentsBox").disabled=true; 
            	  document.getElementById("confirmPurchaseOrder").disabled=true;
            	  document.getElementById("editPurchaseOrder").disabled=true;           
                  alert(data.emptyCartError)	
                  history.back();
            	  }else if(data.customerNotExistError){
            	  document.getElementById("confirmAddressBox").disabled=true;
            	  document.getElementById("confirmCreditCardBox").disabled=true;  
            	  document.getElementById("confirmContentsBox").disabled=true; 
            	  document.getElementById("confirmPurchaseOrder").disabled=true;
            	  document.getElementById("editPurchaseOrder").disabled=true;  
            	  alert(data.customerNotExist)
            	  document.getElementById("confirmPurchaseOrderForm").style.display="none"
            	  history.back();
            	  }  else {
            	 if(data.customer.creditCard){
                 document.getElementById("confirmCreditCard").innerHTML=creditCardHTML(data.customer.creditCard);
                 }else{
                 document.getElementById("confirmCreditCard").innerHTML=failHTML;	
                 }

                 if(data.customer.address){
                 document.getElementById("confirmAddress").innerHTML=addressHTML(data.customer.address);
                 }else{
                 document.getElementById("confirmAddress").innerHTML=failHTML;	
                 }
                 
                 if(data.customer.cart){
                 document.getElementById("confirmContents").innerHTML=cartHTML(data.customer.cart);
                 }else{
                 document.getElementById("confirmContents").innerHTML=failHTML;	
                 }
            	  }    
            	               
    		}
    }
    request.send(null);
}


