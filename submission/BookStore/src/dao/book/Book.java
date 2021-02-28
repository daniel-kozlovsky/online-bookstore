package dao.book;

import java.io.File;

import dao.reviews.Review;

public class Book {
	private String id;
	private String title;
	private String description;
	private String category;
	private String author;
	private double price;
	private File cover;
	private Review[] reviews;
	
	private Book(){
		
	}
	
	public boolean isEqual(Book book){
		return false;		
	}

	public String getTitle() {
		return title;
	}


	public String getDescription() {
		return description;
	}


	public String getCategory() {
		return category;
	}


	public String getAuthor() {
		return author;
	}


	public double getPrice() {
		return price;
	}


	public File getCover() {
		return cover;
	}


	public Review[] getReviews() {
		return reviews;
	}


	public boolean isReviewOfBook(Review review) {
		return false;		
	}
}