package dao.beans;

import java.util.HashMap;
import java.util.Map;

public class PurchaseOrder extends DataObject{
	private String customerId;
	private String status;
	private Map<Book,Integer> books;	


	public String getStatus() {
		return status;
	}

	public Map<Book, Integer> getBooks() {
		return books;
	}
	
	public void setBooks(Map<Book,Integer> books) {
		this.books=books;
	}
	
	
	public boolean isEqual(PurchaseOrder purchaseOrder) {
		return false;
		
	}
	
	public boolean isPurchaseOrderByCustomer(Customer customer) {
		return false;
		
	}
	
	
	
	public static class Builder extends DataObjectBuilder{
		private String customerId; //SUBJECT TO CHANGE
		private String status;
		private Map<Book,Integer> books;

		public Builder(PurchaseOrder purchaseOrder){
			this.id=purchaseOrder.getId();
			this.customerId=purchaseOrder.customerId;
			this.status=purchaseOrder.status;
			this.books=purchaseOrder.books;
		}

		public Builder(){
			this.id=emptyId();
			this.customerId=emptyId();
			this.status="";
			this.books=new HashMap<Book, Integer>();
		}

		public Builder withId(String id){//SUBJECT TO CHANGE
			this.id=id;
			return this;
		}

		public Builder withCustomer(Customer customer){
			this.customerId=customer.getId();
			return this;
		}

		public Builder withStatus(String status){
			this.status=status;
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

		public PurchaseOrder build(){
			PurchaseOrder purchaseOrder=new PurchaseOrder();
			purchaseOrder.id=this.id;
			purchaseOrder.customerId=this.customerId;
			purchaseOrder.status=this.status;
			purchaseOrder.books=this.books;
			return purchaseOrder;
		}

	}
}
