package dao.carts;

import java.util.Map;

import dao.book.Book;
import dao.reviews.Review;
import dao.users.Customer;
import dao.users.Visitor;

public class Cart {
	String id;
	Map<Book,Integer> books;
	
	
	public boolean isEqual(Cart cart) {
		return false;
		
	}
	
	public boolean isCartOfUser(Customer customer) {
		return false;		
	}
	
	public boolean isCartOfUser(Visitor visitor) {
		return false;		
	}
	
	public Map<Book, Integer> getBooks() {
		return books;
	}



}
