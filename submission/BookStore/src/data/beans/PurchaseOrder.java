package data.beans;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import data.beans.Customer.Builder;
import data.beans.IdObject.IdObjectBuilder;

public class PurchaseOrder extends IdObject {
//	private Customer customer;
	private String status;
	private Map<Book,Integer> books;	
	private long createdAtEpoch;
	private Address address;
	private CreditCard creditCard;
	private String email;
	private String userType;
//	private boolean _isWithinCustomer;
	

//	public boolean isWithinCustomer() {
//		return this._isWithinCustomer;
//	}
	
	public String getStatus() {
		return status;
	}
	
	public void addBookAmount(Book book,int amount){
	this.books.put(book, amount);
	}
	
	public boolean isBookInPurchaseOrder(Book book) {
		boolean result=false;
		for(Entry<Book,Integer> entry :this.books.entrySet()) {
			if(book.getId().equals(entry.getKey().getId())) result=true;
		}
		return result;		
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
		return other.getId().isEqual(this.id) && other.createdAtEpoch==this.createdAtEpoch;		
	}
	
	public boolean isPurchaseOrderByCustomer(Customer customer) {
		return customer.getId().equals(this.id);
		
	}
	public String getUserType() {
		return this.userType;
	}
	
//	public boolean isBookInPurchaseOrder(Book book) {
//		return books.get(book)!=null &&books.get(book)>0;		
//	}
	
	public boolean isEmpty() {
		return books.isEmpty() || createdAtEpoch==0;
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
//		private Customer customer;
		private String status;
		private Map<Book,Integer> books;	
		private long createdAtEpoch;
		private String email;
		private String creditCardType;
		private String creditCardNumber;
		private String creditCardExpiry;
		private String creditCardCVV2;
		private String streetNumber;
		private String street;
		private String postalCode;
		private String province;
		private String country;
		private String city;
		private String userType;
//		private boolean _isWithinCustomer;
		
//		public Builder withInCustomer() {
//			this._isWithinCustomer=true;
//			return this;
//		}
//		

		public Builder(PurchaseOrder purchaseOrder){
//			this.customer=purchaseOrder.customer;
			this.status=purchaseOrder.status;
			this.books=purchaseOrder.books;
			this.createdAtEpoch=purchaseOrder.createdAtEpoch;
			this.id=purchaseOrder.getId();
		}

		public Builder(){
//			this.customer=new Customer.Builder().build();
			this.status="";
			this.books=new LinkedHashMap<Book, Integer>();
			this.createdAtEpoch=0;
			this.id=new Id("");
		}


		public Builder withCustomer(Customer customer){
			this.id=customer.getId();
			return this;
		}
		
		
		public Builder withSiteUser(SiteUser siteUser){
			this.id=siteUser.getId();
			this.userType=siteUser.getUserType();
			return this;
		}
		
		public Builder withCreditCard(CreditCard creditCard){
			this.creditCardCVV2=creditCard.getCreditCardCVV2();
			this.creditCardNumber=creditCard.getCreditCardNumber();
			this.creditCardExpiry=creditCard.getCreditCardExpiry();
			this.creditCardType=creditCard.getCreditCardType();
			return this;
		}
		public Builder withAddress(Address address){
			this.streetNumber=address.getNumber();
			this.street=address.getStreet();
			this.postalCode=address.getPostalCode();
			this.province=address.getProvince();
			this.country=address.getCountry();
			this.city=address.getCity();
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
		public Builder withCreditCardType(String creditCardType) {
			this.creditCardType=creditCardType;
			return this;
		}
		public Builder withCreditCardNumber(String creditCardNumber ) {
			this.creditCardNumber=creditCardNumber;
			return this;
		}
		public Builder withCreditCardExpiry(String creditCardExpiry) {
			this.creditCardExpiry=creditCardExpiry;
			return this;
		}
		public Builder withCreditCardCVV2(String creditCardCVV2) {
			this.creditCardCVV2=creditCardCVV2;
			return this;
		}
		


		
		public Builder withStreetNumber(String streetNumber){
			this.streetNumber=streetNumber;
			return this;
		}
		
		public Builder withStreet(String street){
			this.street=street;
			return this;
		}
		public Builder withPostalCode(String postalCode){
			this.postalCode=postalCode;
			return this;
		}
		public Builder withProvince(String province){
			this.province=province;
			return this;
		}
		
		public Builder withCountry(String country){
			this.country=country;
			return this;
		}
		
		public Builder withCity(String city){
			this.city=city;
			return this;
		}
		
		public Builder withEmail(String email){
			this.email=email;
			return this;
		}
		public Builder withUserType(String userType){
			this.userType=userType;
			return this;
		}
//		
//		public Builder withAdditionalBooks(Map<Book,Integer> books){
//			this.books.putAll(books);
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


		public PurchaseOrder build(){
			Address address =new Address.Builder().withCountry(country).withNumber(streetNumber).withCity(city).withPostalCode(postalCode).withProvince(province).withStreet(street).build();
			CreditCard creditCard = new CreditCard.Builder().withCreditCardType(creditCardType).withCreditCardNumber(creditCardNumber).withCreditCardExpiry(creditCardExpiry).withCreditCardCVV2(creditCardCVV2).build();
			PurchaseOrder purchaseOrder=new PurchaseOrder();			
			purchaseOrder.status=this.status;
			purchaseOrder.books=this.books;
			purchaseOrder.userType=this.userType;
			purchaseOrder.createdAtEpoch=this.createdAtEpoch;
			purchaseOrder.email=this.email;
			purchaseOrder.address=address;
			purchaseOrder.creditCard=creditCard;
			purchaseOrder.id=this.id;
			return purchaseOrder;
		}

	}



	@Override
	public String toJson() {
		// TODO Auto-generated method stub
//		String customerJson="\"customer\":";
//		if(!isWithinCustomer()) {
//			customerJson+=this.customer.toJson();
//		}else {
//			customerJson+="{}";
//		}
//		
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
		return "{"+Bean.jsonMapVarChar("id",this.id.toString())+","+
				Bean.jsonMapVarChar("userType",this.userType)+","+
				Bean.jsonMapVarChar("email",this.email)+","+
				Bean.jsonMapNumber("createdAtEpoch",Long.toString(this.createdAtEpoch))+","+
				Bean.jsonMapVarChar("status",this.status)+","+
				Bean.jsonMapNumber("address",this.address.toJson())+","+
				Bean.jsonMapNumber("creditCard",this.creditCard.toJson())+","+
				booksJson+
				"}";				
	}
}
