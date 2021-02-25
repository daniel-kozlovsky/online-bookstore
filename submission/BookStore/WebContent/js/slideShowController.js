/**
 * 
 */

var slideIndexDeal = 1;	
var slideIndexTop = 1;
var slideIndexRating = 1;
var slideIndexProfile = 1;

showSlidesDeal(slideIndexDeal);
showSlidesTop(slideIndexTop);
showSlidesRating(slideIndexRating);
showSlidesProfile(slideIndexProfile);

function plusSlidesDeal(n) {
  showSlidesDeal(slideIndexDeal += n);
}

function currentSlideDeal(n) {
  showSlidesDeal(slideIndexDeal = n);
}

function plusSlidesTop(n) {
  showSlidesTop(slideIndexTop += n);
}

function currentSlideTop(n) {
  showSlidesTop(slideIndexTop = n);
}

function plusSlidesRating(n) {
  showSlidesRating(slideIndexRating += n);
}

function currentSlideRating(n) {
  showSlidesRating(slideIndexRating = n);
}

function plusSlidesProfile(n) {
  showSlidesProfile(slideIndexProfile += n);
}

function currentSlideProfile(n) {
  showSlidesProfile(slideIndexProfile = n);
}

function showSlidesDeal(n) {
	var i;
  	var slides = document.getElementsByClassName("slides_deals");
	var counter;
	
	for (i = 0; i < slides.length; i++) {
    	slides[i].style.display = "none";  
 	 }
	  
	var slideNum = n;

	console.log(slides);
	
	// Adjust slideIndex if there exists overflow or underflow
	if (slideNum  > slides.length) {slideIndexDeal = 1; }
  	if (slideNum  < 1) {slideIndexDeal = slides.length; }	
	
	for (counter = 0; counter < 7; counter ++) {
		
		if (slideNum > slides.length) {slideNum = 1;}
	    if (slideNum < 1) {slideNum = slides.length; }

		slides[slideNum-1].style.display = "inline";
		slideNum++;
	}
}

function showSlidesTop(n) {
	var i;
  	var slides = document.getElementsByClassName("slides_top");
	var counter;
	var slideNum = n;
	
	for (i = 0; i < slides.length; i++) {
    	slides[i].style.display = "none";  
 	 }

	console.log(slides);
	
	// Adjust slideIndex if there exists overflow or underflow
	if (slideNum  > slides.length) {slideIndexTop = 1; }
  	if (slideNum  < 1) {slideIndexTop = slides.length; }	
	
	for (counter = 0; counter < 7; counter ++) {
		
		if (slideNum > slides.length) {slideNum = 1;}
	    if (slideNum < 1) {slideNum = slides.length; }

		slides[slideNum-1].style.display = "inline";
		slideNum++;
	}
}

function showSlidesRating(n) {
	var i;
  	var slides = document.getElementsByClassName("slides_rating");
	var counter;
	var slideNum = n;
	
	for (i = 0; i < slides.length; i++) {
    	slides[i].style.display = "none";  
 	 }

	console.log(slides);
	
	// Adjust slideIndex if there exists overflow or underflow
	if (slideNum  > slides.length) {slideIndexRating = 1; }
  	if (slideNum  < 1) {slideIndexRating = slides.length; }	
	
	for (counter = 0; counter < 7; counter ++) {
		
		if (slideNum > slides.length) {slideNum = 1;}
	    if (slideNum < 1) {slideNum = slides.length; }

		slides[slideNum-1].style.display = "inline";
		slideNum++;
	}
}

function showSlidesProfile(n) {
	var i;
  	var slides = document.getElementsByClassName("slides_profile");
	var counter;
	var slideNum = n;
	
	for (i = 0; i < slides.length; i++) {
    	slides[i].style.display = "none";  
 	 }

	console.log(slides);
	
	// Adjust slideIndex if there exists overflow or underflow
	if (slideNum  > slides.length) {slideIndexProfile = 1; }
  	if (slideNum  < 1) {slideIndexProfile = slides.length; }	
	
	for (counter = 0; counter < 7; counter ++) {
		
		if (slideNum > slides.length) {slideNum = 1;}
	    if (slideNum < 1) {slideNum = slides.length; }

		slides[slideNum-1].style.display = "inline";
		slideNum++;
	}
}