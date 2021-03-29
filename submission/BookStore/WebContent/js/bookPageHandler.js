/** -------------------------------------------
 * Handles a book selection request
 ------------------------------------------- */
 
 function pageHandler(address){
	
	var request = new XMLHttpRequest();
	var data = '';
		

	console.log("===> OUTPUT: " + address);
	request.open("GET", (address + data), true); // opens a GET communication to the server, specify the URL, and provide parameters. synchronous communication
	
	request.onreadystatechange = function(){
		handler(request);	
	};
	
	request.send();
}


function handler(request){
	if ((request.readyState == 4) && (request.status == 200)){
		var target = document.getElementById("ajaxTarget");
		target.innerHTML = request.responseText; 
	}
}

