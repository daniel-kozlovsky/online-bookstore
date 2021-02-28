package dao.reviews;


import dao.book.Book;
import dao.users.Customer;

public class Review {
	String body;
	String title;
	String userName;
	int stars;
	Customer customer;
	Book book;
	
	
	public boolean isEqual(Review review) {
		return false;
		
	}
	
	public boolean isReviewByCustomer(Customer customer){
		return true;
		
	}
	
	
	public boolean isReviewOfBook(Book book){
		return true;
	}
	

	public static class Builder{
		private String body;
		private String title;
		private String userName;
		private int stars;
		private Customer customer;
		private Book book;

		public Builder(){
			this.body="";
			this.title="";
			this.userName="";
			this.stars=0;
			this.customer=new Customer.Builder().build();
			this.book=new Book.Builder().build();
		}
		
		public Builder(Review review){
			this.body=review.body;
			this.title=review.title;
			this.userName=review.userName;
			this.stars=review.stars;
			this.customer=review.customer;
			this.book=review.book;
		}

		public Builder withBody(String body){
			this.body=body;
			return this;
		}

		public Builder withTitle(String title){
			this.title=title;
			return this;
		}

		public Builder withUserName(String userName){
			this.userName=userName;
			return this;
		}

		public Builder withStars(int stars){
			this.stars=stars;
			return this;
		}

		public Builder withCustomer(Customer customer){
			this.customer=customer;
			return this;
		}

		public Builder withBook(Book book){
			this.book=book;
			return this;
		}

		public Review build(){
			Review review=new Review();
			review.body=this.body;
			review.title=this.title;
			review.userName=this.userName;
			review.stars=this.stars;
			review.customer=this.customer;
			review.book=this.book;
			return review;
		}

	}
}
