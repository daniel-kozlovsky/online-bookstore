package dao.beans;

import java.io.File;

public class Book extends DataObject{
	private String title;
	private String description;
	private String category;
	private String author;
	private double price;
	private File cover;
	private Review[] reviews;
	
	private Book(){
		
	}
	
	public boolean isEqual(Book book){
		return false;		
	}

	public String getTitle() {
		return title;
	}


	public String getDescription() {
		return description;
	}


	public String getCategory() {
		return category;
	}


	public String getAuthor() {
		return author;
	}


	public double getPrice() {
		return price;
	}


	public File getCover() {
		return cover;
	}


	public Review[] getReviews() {
		return reviews;
	}
	
	public void setReviews(Review[] reviews) {
		this.reviews=reviews;
	}


	public boolean isReviewOfBook(Review review) {
		return false;		
	}
	public static class Builder extends DataObjectBuilder{
		private String title;
		private String description;
		private String category;
		private String author;
		private double price;
		private File cover;
		private Review[] reviews;

		public Builder(Book book){
			this.id=book.getId();
			this.title=book.title;
			this.description=book.description;
			this.category=book.category;
			this.author=book.author;
			this.price=book.price;
			this.cover=book.cover;
			this.reviews=book.reviews;
		}
		
		public Builder(){
			this.id=emptyId();
			this.title="";
			this.description="";
			this.category="";
			this.author="";
			this.price=0.0;
			this.cover=new File("");
			this.reviews=new Review[] { (new Review.Builder().build())};
		}


		public Builder withId(String id){
			this.id=id;
			return this;
		}

		public Builder withTitle(String title){
			this.title=title;
			return this;
		}

		public Builder withDescription(String description){
			this.description=description;
			return this;
		}

		public Builder withCategory(String category){
			this.category=category;
			return this;
		}

		public Builder withAuthor(String author){
			this.author=author;
			return this;
		}

		public Builder withPrice(double price){
			this.price=price;
			return this;
		}

		public Builder withCover(File cover){
			this.cover=cover;
			return this;
		}

		public Builder withReviews(Review[] reviews){
			this.reviews=reviews;
			return this;
		}

		public Book build(){
			Book book=new Book();
			book.id=this.id;
			book.title=this.title;
			book.description=this.description;
			book.category=this.category;
			book.author=this.author;
			book.price=this.price;
			book.cover=this.cover;
			book.reviews=this.reviews;
			return book;
		}

	}
}
