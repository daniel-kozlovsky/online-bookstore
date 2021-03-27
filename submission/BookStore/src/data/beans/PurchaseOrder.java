package data.beans;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import data.beans.Customer.Builder;
import data.beans.IdObject.IdObjectBuilder;

public class PurchaseOrder implements Bean{
	private Customer customer;
	private String status;
	private Map<Book,Integer> books;	
	private long createdAtEpoch;
	private boolean _isWithinCustomer;
	

	public boolean isWithinCustomer() {
		return this._isWithinCustomer;
	}
	
	public String getStatus() {
		return status;
	}

	public Map<Book, Integer> getBooks() {
		return books;
	}
	
	public long getCreatedAtEpoch() {
		return this.createdAtEpoch;
	}
	
	@Override
	public boolean equals(Object object) {
		PurchaseOrder other = (PurchaseOrder)object;
		return other.customer.isEqual(this.customer) && other.createdAtEpoch==this.createdAtEpoch;		
	}
	
	public boolean isPurchaseOrderByCustomer(Customer customer) {
		return customer.isEqual(this.customer);
		
	}
	
	public boolean isBookInPurchaseOrder(Book book) {
		return books.get(book)!=null &&books.get(book)>0;		
	}
	
	public boolean isEmpty() {
		return books.isEmpty() || createdAtEpoch==0;
	}
	
	public Customer getCustomer() {
		return this.customer;	
	}
	
	public int numberOfBook(Book book) {
		return isBookInPurchaseOrder(book)?this.books.get(book):0;		
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
	
	
	
	public static class Builder extends IdObjectBuilder<Builder>{
		private Customer customer;
		private String status;
		private Map<Book,Integer> books;	
		private long createdAtEpoch;
		private boolean _isWithinCustomer;
		
		public Builder withInCustomer() {
			this._isWithinCustomer=true;
			return this;
		}
		

		public Builder(PurchaseOrder purchaseOrder){
			this.customer=purchaseOrder.customer;
			this.status=purchaseOrder.status;
			this.books=purchaseOrder.books;
			this.createdAtEpoch=purchaseOrder.createdAtEpoch;
			this._isWithinCustomer=purchaseOrder._isWithinCustomer;
		}

		public Builder(){
			this.customer=new Customer.Builder().build();
			this.status="";
			this.books=new HashMap<Book, Integer>();
			this.createdAtEpoch=0;
			this._isWithinCustomer=false;
		}


		public Builder withCustomer(Customer customer){
			this.customer=customer;
			return this;
		}
		
		

		public Builder withStatus(String status){
			this.status=status;
			return this;
		}
		
		public Builder withCreatedAtEpoch(long createdAtEpoch){
			this.createdAtEpoch=createdAtEpoch;
			return this;
		}

		public Builder withBooks(Map<Book,Integer> books){
			this.books=books;
			return this;
		}
		
		public Builder withBookAndAmount(Book book,int amount){
			this.books.put(book, amount);
			return this;
		}
		
		public Builder withAdditionalBooks(Map<Book,Integer> books){
			this.books.putAll(books);
			return this;
		}
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


		public PurchaseOrder build(){
			PurchaseOrder purchaseOrder=new PurchaseOrder();
			purchaseOrder.customer=this.customer;
			purchaseOrder.status=this.status;
			purchaseOrder.books=this.books;
			purchaseOrder.createdAtEpoch=this.createdAtEpoch;
			return purchaseOrder;
		}

	}



	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		String customerJson="\"customer\":";
		if(!isWithinCustomer()) {
			customerJson+=this.customer.toJson();
		}else {
			customerJson+="{}";
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
		return "{"+Bean.jsonMapNumber("customer",customerJson)+","+
				Bean.jsonMapNumber("createdAtEpoch",Long.toString(this.createdAtEpoch))+
				Bean.jsonMapVarChar("status",this.status)+
				Bean.jsonMapNumber("books",booksJson)+
				"}";				
	}
}
