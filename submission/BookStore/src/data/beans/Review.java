package data.beans;


	
public class Review implements Bean{
	
//	private static final String BODY="BODY";
//	private static final String TITLE="TITLE";
//	private static final String RATING="RATING";
//	private static final String USER="USER";
//	private static final String BOOK="BOOK";
//	private static final String UTC="UTC";
//	
	private String body;
	private String title;
	private Customer customer;
	private int rating;
	private Book book;//SUBJECT TO CHANGE
	private long createdAtEpoch;
	private boolean _isWithinBook;
	private boolean _isWithinCustomer;
	public String getBody() {
		return body;
	}

	public String getTitle() {
		return title;
	}

	public Customer getCustomer() {
		return customer;
	}

	public int getRating() {
		return rating;
	}

	public Book getBook() {
		return book;
	}

	public long getCreatedAtEpoch() {
		return createdAtEpoch;
	}
	
	public boolean isWithinCustomer() {
		return this._isWithinCustomer;
	}
	
	public boolean isWithinBook() {
		return this._isWithinBook;
	}



	
	public String toJson() {
		String customerJson=isWithinCustomer()?Bean.jsonMapNumber("customer","{}"):Bean.jsonMapNumber("customer",this.customer.toJson());
		String bookJson=isWithinBook()?Bean.jsonMapNumber("book","{}"):Bean.jsonMapNumber("book",this.book.toJson());


		return "{"+Bean.jsonMapVarChar("customer",this.customer.getId().toString())+","+
				Bean.jsonMapVarChar("book",this.book.getId().toString())+","+
				Bean.jsonMapVarChar("body",this.body.replaceAll("\"", "\\\""))+","+
				Bean.jsonMapVarChar("title",this.title.replaceAll("\"", "\\\""))+","+
				Bean.jsonMapNumber("rating",Integer.toString(this.rating))+","+
				Bean.jsonMapNumber("createdAtEpoch",Long.toString(this.createdAtEpoch))+","+
				customerJson+","+
				bookJson+
				"}"
				;

	}

	public static class Builder{
		private String body;
		private String title;
		private Customer customer;
		private int rating;
		private Book book;//SUBJECT TO CHANGE
		private long createdAtEpoch;
		private boolean _isWithinBook;
		private boolean _isWithinCustomer;

		public Builder(){
			this.body="";
			this.title="";
			this.customer=new Customer.Builder().build();
			this.rating=0;
			this.book=new Book.Builder().build();
			this._isWithinBook=false;
			this._isWithinCustomer=false;
		}
		
		public Builder withinBook() {
			this._isWithinBook=true;
			return this;
		}
		
		public Builder withinCustomer() {
			this._isWithinCustomer=true;
			return this;
		}
		
		public Builder(Review review){
			this.body=review.body;
			this.title=review.title;
			this.customer=review.customer;
			this.rating=review.rating;
			this.book=new Book.Builder(review.book).withReviews(new Review[0]).build();
			this.createdAtEpoch=review.createdAtEpoch;
			this._isWithinBook=review._isWithinBook;
			this._isWithinCustomer=review._isWithinCustomer;
			
		}

		public Builder withBody(String body){
			this.body=body;
			return this;
		}

		public Builder withTitle(String title){
			this.title=title;
			return this;
		}

		public Builder withRating(int rating){
			this.rating=rating;
			return this;
		}

		public Builder withCustomer(Customer customer){
			this.customer=customer;
			return this;
		}
		


		public Builder withBook(Book book){
			this.book=new Book.Builder(book).withReviews(new Review[0]).build();
			return this;
		}
		
		
		public Builder withCreatedAtEpoch(long createdAtEpoch){
			this.createdAtEpoch=createdAtEpoch;
			return this;
		}

		public Review build(){
			Review review=new Review();
			review.body=this.body;
			review.title=this.title;
			review.customer=this.customer;
			review.rating=this.rating;
			review.book=this.book;
			review.createdAtEpoch=this.createdAtEpoch;
			review._isWithinBook=this._isWithinBook;
			review._isWithinCustomer=this._isWithinCustomer;
			return review;
		}

	}
}
