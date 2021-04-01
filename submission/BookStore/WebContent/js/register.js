/**
 * 
 */
const registerAddr = "/BookStore/Register"

function register()
{

	let request = new XMLHttpRequest();
	
	let email = document.getElementById("input-email").value;
	let username = document.getElementById("input-username").value;
	let password = document.getElementById("input-password").value;
	let givenName = document.getElementById("input-given-name").value;
	let surname = document.getElementById("input-surname").value;
	
	let url = new URL(registerAddr, window.location.protocol + window.location.host);
	
	
	url.searchParams.set("username", username);
	url.searchParams.set("password", password);
	url.searchParams.set("email", email);
	url.searchParams.set("givenName", givenName);
	url.searchParams.set("surname", surname);
	
	console.log(url.toString());
	
	request.open("POST", url.toString());
	request.onreadystatechange = function (){
		handler(request);
	};
	
	request.send(null);
}

function handler(request)
{
	if(request.readyState == 4 && request.status == 200)
	{
		let target = document.getElementById("label-error");
		
		target.innerHTML = request.responseText;
	}
}