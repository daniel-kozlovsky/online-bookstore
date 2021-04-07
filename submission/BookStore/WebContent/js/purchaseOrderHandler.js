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
	`<div><span class=\x22label\x22>CVV2</span>:<span class=\x22value\x22>${creditCard.creditCardCVV2}</span></div>`+
	`<label for=\x22defaultCreditCard\x22>use default credit card on account?`+
	`<input type=\x22checkbox\x22 id=\x22defaultCreditCard\x22 name=\x22defaultCreditCard\x22></input>`+
	`</label>`
}

const addressHTML=(address)=>{
return	`<div><span class=\x22label\x22>Number</span>:<span class=\x22value\x22>${address.number}</span></div>`+
	`<div><span class=\x22label\x22>Street</span>:<span class=\x22value\x22>${address.street}</span></div>`+
	`<div><span class=\x22label\x22>City</span>:<span class=\x22value\x22>${address.city}</span></div>`+
	`<div><span class=\x22label\x22>Province</span>:<span class=\x22value\x22>${address.province}</span></div>`+
	`<div><span class=\x22label\x22>Country</span>:<span class=\x22value\x22>${address.country}</span></div>`+
	`<div><span class=\x22label\x22>Postal Code</span>:<span class=\x22value\x22>${address.postalCode}</span></div>`+
		`<label for=\x22defaultAddress\x22>use default address on account?`+
	`<input type=\x22checkbox\x22 id=\x22defaultAddress\x22 name=\x22defaultAddress\x22></input>`+
	`</label>`
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


const pageLoad =(address)=>{
	document.getElementById("address").innerHTML=spinnerHTML;	
	document.getElementById("creditCard").innerHTML=spinnerHTML;	
    let request = new XMLHttpRequest()
    request.open("GET", address+'?ajax=true', true);
    request.onreadystatechange = ()=>{
            if ((request.readyState == 4) && (request.status == 200)){            
                 let data=JSON.parse(request.responseText);

                 if(data.error){
                 	alert(data.error)
                 }
                  if(data.status){
            	  	if(data.status===''){
            	  	history.back();
            	  	}else if(data.status===''){
            	  	
            	  	}		
            	  }else {
            	  if(data.creditCard){
                 document.getElementById("creditCard").innerHTML=creditCardHTML(data.creditCard);
                 }else{
                 document.getElementById("creditCard").innerHTML=failHTML;	
                 }

                 if(data.address){
                 document.getElementById("address").innerHTML=addressHTML(data.address);
                 }else{
                 document.getElementById("address").innerHTML=failHTML;	
                 }
            	  }    
            	               
    		}
    }
    request.send(null);
}


