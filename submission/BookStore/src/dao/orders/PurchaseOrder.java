package dao.orders;

import java.util.Map;

import dao.book.Book;
import dao.users.Customer;

public class PurchaseOrder {
	String id;
	Customer customer;
	String status;
	Map<Book,Integer> books;	
	
	public Customer getCustomer() {
		return customer;
	}

	public String getStatus() {
		return status;
	}

	public Map<Book, Integer> getBooks() {
		return books;
	}
	
	
	public boolean isEqual(PurchaseOrder purchaseOrder) {
		return false;
		
	}
	
	public boolean isPurchaseOrderByCustomer(Customer customer) {
		return false;
		
	}
	
	public boolean isPurchaseOrderOfBook(Book book) {
		return false;
		
	}
	
	
	public static class Builder{
		private String id;
		private Customer customer;
		private String status;
		private Map books;

		public Builder(){
		}

		public Builder withId(String id){
			this.id=id;
			return this;
		}

		public Builder withCustomer(Customer customer){
			this.customer=customer;
			return this;
		}

		public Builder withStatus(String status){
			this.status=status;
			return this;
		}

		public Builder withBooks(Map books){
			this.books=books;
			return this;
		}

		public PurchaseOrder build(){
			PurchaseOrder purchaseOrder=new PurchaseOrder();
			purchaseOrder.id=this.id;
			purchaseOrder.customer=this.customer;
			purchaseOrder.status=this.status;
			purchaseOrder.books=this.books;
			return purchaseOrder;
		}

	}
}
