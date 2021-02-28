package dao.beans;

public class Review extends DataObject{
	private String body;
	private String title;
	private String userName;
	private int stars;
	private String bookId;//SUBJECT TO CHANGE
	
	
	public String getBody() {
		return body;
	}

	public String getTitle() {
		return title;
	}

	public String getUserName() {
		return userName;
	}

	public int getStars() {
		return stars;
	}

	public boolean isEqual(Review review) {
		return false;
		
	}
	
	public boolean isReviewByCustomer(Customer customer){
		return true;
		
	}
	
	
	public boolean isReviewOfBook(Book book){
		return true;
	}
	

	public static class Builder extends DataObjectBuilder{
		private String body;
		private String title;
		private String userName;
		private int stars;
		private String id;
		private String bookId;	

		public Builder(){
			this.body="";
			this.title="";
			this.userName="";
			this.stars=0;
			this.id=emptyId();
			this.bookId=emptyId();
		}
		
		public Builder(Review review){
			this.body=review.body;
			this.title=review.title;
			this.userName=review.userName;
			this.stars=review.stars;
			this.id=review.getId();
			this.bookId=review.bookId;
		}

		public Builder withBody(String body){
			this.body=body;
			return this;
		}

		public Builder withTitle(String title){
			this.title=title;
			return this;
		}

		public Builder withStars(int stars){
			this.stars=stars;
			return this;
		}

		public Builder withCustomer(Customer customer){
			this.id=customer.getId();
			this.userName=customer.getUserName();
			return this;
		}
		

		public Builder withBook(Book book){
			this.bookId=book.getId();
			return this;
		}

		public Review build(){
			Review review=new Review();
			review.body=this.body;
			review.title=this.title;
			review.userName=this.userName;
			review.stars=this.stars;
			review.id=this.id;
			review.bookId=this.bookId;
			return review;
		}

	}
}
