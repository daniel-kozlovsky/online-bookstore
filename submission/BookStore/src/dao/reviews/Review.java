package dao.reviews;


import dao.book.Book;
import dao.users.Customer;

public class Review {
	String body;
	String title;
	String userName;
	int stars;
	Customer customer;
	Book book;
	
	
	public boolean isEqual(Review review) {
		return false;
		
	}
	
	public boolean isReviewByCustomer(Customer customer){
		return true;
		
	}
	
	
	public boolean isReviewOfBook(Book book){
		return true;
	}
	

}
 