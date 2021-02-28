package dao.carts;

import java.util.HashMap;
import java.util.Map;

import dao.book.Book;
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



	public static class Builder{
		private String id;
		private Map<Book,Integer> books;

		public Builder(Cart cart){
			this.id=cart.id;
			this.books=cart.books;
		}
		
		public Builder(){
			this.id="";
			this.books=new HashMap<Book, Integer>();
		}

		public Builder withId(String id){
			this.id=id;
			return this;
		}

		public Builder withBooks(Map<Book,Integer> books){
			this.books=books;
			return this;
		}
		
		public Builder withBook(Book book,int amount){
			this.books.put(book, amount);
			return this;
		}

		public Cart build(){
			Cart cart=new Cart();
			cart.id=this.id;
			cart.books=this.books;
			return cart;
		}

	}
}
