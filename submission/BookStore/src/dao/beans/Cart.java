package dao.beans;

import java.util.HashMap;
import java.util.Map;

public class Cart extends DataObject{
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



	public static class Builder extends DataObjectBuilder{

		private Map<Book,Integer> books;

		public Builder(Cart cart){
			this.id=cart.getId();
			this.books=cart.books;
		}
		
		public Builder(){
			this.id=emptyId();
			this.books=new HashMap<Book, Integer>();
		}

		public Builder withUser(Customer customer){
			this.id=customer.getId();
			return this;
		}
		
		public Builder withUser(Visitor visitor){
			this.id=visitor.getId();
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
