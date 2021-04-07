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

const registerHtml=`<fieldset id=\x22newCustomerAccount\x22><legend>New Registration</legend>
<label>Given Name</label><input type=\x22text\x22 id=\x22inputGivenNameCheckout\x22/>
<label>Sur Name</label><input type=\x22text\x22 id=\x22inputSurNameCheckout\x22/>
<label>Email</label><input type=\x22text\x22 id=\x22inputEmailCheckout\x22/>
<label>Username</label><input type=\x22text\x22 id=\x22inputUserNameCheckout\x22/>
<label>Password</label><input type=\x22password\x22 id=\x22inputPasswordCheckout\x22/>
</fieldset>`;
const registerRequest=()=>{
	if( document.getElementById("customerCheckoutLogin")){
	document.getElementById("visitorCheckout").innerHTML=registerHtml;
	}else if( document.getElementById("newCustomerAccount")){
	alert("registering")
	register();
	}else{
	document.getElementById("visitorCheckout").innerHTML=registerHtml;
	}
}
const loginRequest=()=>{
	if( document.getElementById("newCustomerAccount")){
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

const defaultAdressCheckHTML=		`<label for=\x22defaultAddress\x22>use default address on account?`+
	`<input type=\x22checkbox\x22 id=\x22defaultAddress\x22 name=\x22defaultAddress\x22></input>`+
	`</label>`;
	
const defaultCreditCardCheckHTML=	`<label for=\x22defaultCreditCard\x22>use default credit card on account?`+
	`<input type=\x22checkbox\x22 id=\x22defaultCreditCard\x22 name=\x22defaultCreditCard\x22></input>`+
	`</label>`;


const signInRegisterButtonsHTML=`
<button  id=\x22registerFromCheckout\x22onclick=\x22registerRequest();\x22>Register</button>
<button  id=\x22signInFromCheckout\x22onclick=\x22loginRequest();\x22>Sign In</button>`



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
	alert('validating')

	return true;
}

const confirmPurchaseOrder =()=>{
	alert('purchaseOrderConfirmed')
	return true;
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
    request.open("GET", address+'?ajax=true', true);
    request.onreadystatechange = ()=>{
            if ((request.readyState == 4) && (request.status == 200)){            
                 let data=JSON.parse(request.responseText);


                  if(data.emptyCartError){    
                  document.getElementById("newAddressBox").disabled=true;
            	  document.getElementById("newCreditCardBox").disabled=true;  
            	  document.getElementById("submitPurchaseOrder").disabled=true;            
                  alert(data.emptyCartError)	
                  history.back();
            	  } else {
            	 if(data.creditCard){
                 document.getElementById("creditCard").innerHTML=creditCardHTML(data.creditCard)+defaultCreditCardCheckHTML;
                 }else{
                 document.getElementById("creditCard").innerHTML=failHTML;	
                 }

                 if(data.address){
                 document.getElementById("address").innerHTML=addressHTML(data.address)+defaultAdressCheckHTML;
                 }else{
                 document.getElementById("address").innerHTML=failHTML;	
                 }
            	  if(data.customerNotExist){
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
    request.open("GET", address+'?ajax=true', true);
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
            	  }else if(data.customerNotExist){
            	  document.getElementById("confirmAddressBox").disabled=true;
            	  document.getElementById("confirmCreditCardBox").disabled=true;  
            	  document.getElementById("confirmContentsBox").disabled=true; 
            	  document.getElementById("confirmPurchaseOrder").disabled=true;
            	  document.getElementById("editPurchaseOrder").disabled=true;  
            	  alert(data.customerNotExist)
            	  document.getElementById("confirmPurchaseOrderForm").style.display="none"
            	  history.back();
            	  }  else {
            	 if(data.creditCard){
                 document.getElementById("confirmCreditCard").innerHTML=creditCardHTML(data.creditCard);
                 }else{
                 document.getElementById("confirmCreditCard").innerHTML=failHTML;	
                 }

                 if(data.address){
                 document.getElementById("confirmAddress").innerHTML=addressHTML(data.address);
                 }else{
                 document.getElementById("confirmAddress").innerHTML=failHTML;	
                 }
                 
                 if(data.cart){
                 document.getElementById("confirmContents").innerHTML=cartHTML(data.cart);
                 }else{
                 document.getElementById("confirmContents").innerHTML=failHTML;	
                 }
            	  }    
            	               
    		}
    }
    request.send(null);
}


