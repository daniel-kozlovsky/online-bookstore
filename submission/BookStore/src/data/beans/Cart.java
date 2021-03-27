package data.beans;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import data.beans.IdObject.IdObjectBuilder;
import data.beans.PurchaseOrder.Builder;
import data.schema.CustomerSchema;
import data.schema.UserTypes;
import data.schema.VisitorSchema;

public class Cart extends IdObject{
	private Map<Book,Integer> books;
	private User user;
	private String userType;
	private Customer customer;
	private Visitor visitor;

	private boolean _isWithinCustomer;
	private boolean _isWithinVisitor;

	
	
	@Override
	public boolean equals(Object object) {
		Cart other = (Cart)object;
		return other.user.isEqual(this.user);		
	}
	
	public User getUser() {
		return this.user;
	}
	
	public boolean isEmpty() {
		return this.books.isEmpty();
	}
	public boolean isCartOfUser(User user) {
		return user.isEqual(this.user);		
	}
	
	public boolean isBookInCart(Book book) {
		return books.get(book)!=null &&books.get(book)>0;		
	}
	
	public int numberOfBook(Book book) {
		return isBookInCart(book)?this.books.get(book):0;		
	}
	
	public Map<Book, Integer> getBooks() {
		return books;
	}
	
	public void combineBooks(Map<Book,Integer> books) {
		for(Entry<Book,Integer> entry:books.entrySet()) {
			if(!this.books.containsKey(entry.getKey())) {
				this.books.put(entry.getKey(), entry.getValue());
			}else {
				int currentCount = this.books.get(entry.getKey());
				this.books.remove(entry.getKey());
				this.books.put(entry.getKey(), currentCount+entry.getValue());
			}
		}
	}
	
	public void combineCarts(Cart cart) {
		combineBooks(cart.getBooks());
	}
	
	public void withBookAmountReplace(Book book,int amount){
	this.books.put(book, amount);
	}
 
	public boolean isVisitorCart() {
		return this.user.equals("VISITOR");
	}

	public boolean isCustomerCart() {
		return this.user.equals("CUSTOMER");
	}
	
	public String getUserType() {
		return this.userType;
	}
	
	public boolean isWithinCustomer() {
		return this._isWithinCustomer;
	}
	

	public boolean isWithinVisitor() {
		return this._isWithinVisitor;
	}
	

	public static class Builder extends IdObjectBuilder<Builder>{

		private Map<Book,Integer> books;
		private User user;
		private String userType;
		private Customer customer;
		private Visitor visitor;

		private boolean _isWithinCustomer;
		private boolean _isWithinVisitor;
		

		
		public Builder(Cart cart){
			this.user=cart.user;
			this.books=cart.books;
			this._isWithinVisitor=cart._isWithinVisitor;
			this._isWithinCustomer=cart._isWithinCustomer;
			if(cart.customer!=null) {
				this.customer=cart.customer;
				this.visitor=null;
			}else if(cart.visitor!=null) {
				this.visitor=cart.visitor;
				this.customer=null;
			}
		}
		
		public Builder(){
			super();
			this.books=new HashMap<Book, Integer>();
			this.user=null;
			this.userType=UserTypes.VISITOR;
			this.customer=null;
			this.visitor=null;
			this._isWithinVisitor=false;
			this._isWithinCustomer=false;
		}

		
		public Builder withUser(Visitor visitor){
			this.user=visitor;
			this.userType=UserTypes.VISITOR;
			this.visitor=visitor;
			return this;
		}
		
		public Builder withUser(Customer customer){
			this.user=customer;
			this.userType=UserTypes.CUSTOMER;
			this.customer=customer;
			
			return this;
		}


		public Builder withBooks(Map<Book,Integer> books){
			this.books=books;
			return this;
		}
		
		public Builder withAdditionalBooks(Map<Book,Integer> books){
			this.books.putAll(books);
			return this;
		}
		
		public Builder withBook(Book book){
			if(this.books.get(book)==null ||this.books.get(book)==0) {
				this.books.put(book, 1);
			}else {
				this.books.put(book, this.books.get(book)+1);
			}
			
			return this;
		}

		public Builder withInCustomer() {
			this._isWithinVisitor=false;
			this._isWithinCustomer=true;
			return this;
		}
		
		public Builder withInVisitor() {
			this._isWithinCustomer=false;
			this._isWithinVisitor=true;
			return this;
		}
		
		
		public Builder withBookAmount(Book book,int amount){
				this.books.put(book, amount);
			return this;
		}
		
//		public Builder withBookIncrement(Book book,int amount){
//			this.books.put(book, amount);
//			return this;
//		}
//		
//		
//		public Builder withBookAmountReplace(Book book,int amount){
//			this.books.put(book, amount);
//			return this;
//		}
//		
//		public Builder withBookAmountIncrement(Book book,int amount){
//			int count=this.books.get(book)!=null && this.books.get(book)>=0?this.books.get(book)+1:1;
//			this.books.put(book, count);
//			return this;
//		}
//		
//		
//		
//		public Builder withBookIncrement(Book book){
//			int count=this.books.get(book)!=null && this.books.get(book)>=0?this.books.get(book)+1:1;
//			this.books.put(book, count);
//			return this;
//		}

		public Cart build(){
			Cart cart=new Cart();
			cart.user=this.user;
			cart.books=this.books;
			cart.userType=this.userType;
			cart._isWithinCustomer=this._isWithinCustomer;
			cart._isWithinVisitor=this._isWithinVisitor;
			cart.id=this.id;
			if(this.customer!=null) {
				cart.customer=this.customer;
				cart.visitor=null;
			}else if(this.visitor!=null) {
				cart.visitor=this.visitor;
				cart.customer=null;
			}
			return cart;
		}

	}



	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		String customerJson="";
		String visitorJson="";
		if(isWithinCustomer()) {
			customerJson=isWithinCustomer()?Bean.jsonMapNumber("customer","{},"):this.customer.toJson();
		}else if (isWithinVisitor()) {
			visitorJson=isWithinVisitor()?Bean.jsonMapNumber("visitor","{},"):this.visitor.toJson();
		}

		

		String booksJson="\"books\": [";
		if (this.books!=null && !this.books.isEmpty()) {
			for(Entry<Book,Integer> entry:this.books.entrySet()) {
					booksJson+="{\"amount\":"+Integer.toString(entry.getValue())+",";
					booksJson+="\"book\":";
					booksJson+=entry.getKey().toJson()+"},";
			}
			booksJson=booksJson.substring(0, booksJson.length() - 1);
			
		}
		booksJson+="]";
		String userJson=this.userType.equals(UserTypes.VISITOR)?visitorJson:customerJson;
//		String userJsonLabel=this.customer==null?"visitor":"customer";
		return "{"+userJson+
				booksJson+
				"}";				
	}
}
