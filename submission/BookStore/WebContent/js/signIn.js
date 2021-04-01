/**
 * signIn.js
 * Handles posting for user sign-in
 */
const signInAddr = "/BookStore/SignIn"

function signIn()
{

	let request = new XMLHttpRequest();
	
	let username = document.getElementById("input-uname").value;
	let password = document.getElementById("input-pword").value;
	
	let url = new URL(signInAddr, window.location.protocol + window.location.host);
	
	
	url.searchParams.set("username", username);
	url.searchParams.set("password", password);
	
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