package data.update;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import data.beans.Book;
import data.beans.Customer;
import data.beans.Review;
import data.schema.BookSchema;
import data.schema.ReviewSchema;
import data.update.UpdateBook.BookInsert;
import data.update.UpdateBook.InsertBookSeries;
import data.update.UpdateBook.InsertBookTitle;

public class UpdateReview extends DataUpdate{
	public void executeDeleteReview(Customer customer, Review review) {
		if(customer.getId().isEmpty() || review.getBook().getId().isEmpty()|| !review.getCustomer().getId().equals(customer.getId())) return;
		String update="DELETE FROM REVIEW WHERE CUSTOMER='"+customer.getId().toString()+"' AND  BOOK='"+review.getBook().getId().toString()+"'";
		sendUpdateToDatabase(update);
	}
	
	public ReviewUpdater requestUpdateReview(Customer customer, Review review) {
		if(customer.getId().isEmpty() || review.getBook().getId().isEmpty()|| !review.getCustomer().getId().equals(customer.getId())) return null;
		return new ReviewUpdater(review);
	}
	
	public InsertReviewTitle requestNewReviewInsertion(Customer customer, Book book) {
		return new InsertReviewTitle(new Review.Builder().withCustomer(customer).withBook(book).build());
	}

	public class InsertReviewTitle extends ReviewInsert{
		InsertReviewTitle(Review review){
			super(review);
		}
		
		public InsertReviewBody insertReviewWithTitle(String title){
			return new InsertReviewBody(new Review.Builder(review).withTitle(title).build());
		}
	}
	
	public class InsertReviewBody extends ReviewInsert{
		InsertReviewBody(Review review){
			super(review);
		}
		
		public InsertReviewRating insertReviewWithBody(String body){
			return new InsertReviewRating(new Review.Builder(review).withBody(body).build());
		}
	}
	
	public class InsertReviewRating extends ReviewInsert{
		InsertReviewRating(Review review){
			super(review);
		}
		
		public ExecuteReviewInsertion insertReviewWithRating(int rating){
			return new ExecuteReviewInsertion(new Review.Builder(review).withRating(rating).build());
		}
	}
	
	abstract class ReviewInsert extends DataUpdate{
		Review review;
		ReviewInsert(Review review){
			this.review=review;
		}
	}
	public class ExecuteReviewInsertion extends ReviewInsert{
		ExecuteReviewInsertion(Review review) {
			super(review);
			// TODO Auto-generated constructor stub
		}

		public void executeReviewInsertion(){
			String epoch =Long.toString(Instant.now().getEpochSecond());
			String update ="INSERT INTO REVIEW (CUSTOMER,BOOK,RATING,TITLE,BODY,CREATED_AT_EPOCH) VALUES "+
					"('"+review.getCustomer().getId().toString()+"','"+review.getBook().getId().toString()+"',"+Integer.toString(review.getRating())+",'"+review.getTitle()+"','"+review.getBody()+"',"+epoch+")";
			sendUpdateToDatabase(update);
		}
	}
	
	
	public class ReviewUpdater extends DataUpdate{
		Map<String,String> updateRequest;
		private Review review;
		private ReviewSchema reviewSchema = new ReviewSchema();
		ReviewUpdater(Review review){
			this.updateRequest=new LinkedHashMap<String, String>();
			this.review=review;
		}
//		INSERT INTO REVIEW (CUSTOMER,BOOK,RATING,TITLE,BODY,CREATED_AT_EPOCH) VALUES 
		public ReviewUpdater updateReviewRating(int rating ) {
			this.updateRequest.put(reviewSchema.RATING, Integer.toString(rating));
			return this;
		}
		public ReviewUpdater updateReviewTitle(String title) {
			this.updateRequest.put(reviewSchema.TITLE, surroundWithQuotes(title));
			return this;
		}
		public ReviewUpdater updateReviewBody(String body) {
			this.updateRequest.put(reviewSchema.BODY, surroundWithQuotes(body));
			return this;
		}
		
		public void executeUpdate() {
			String update = "UPDATE REVIEW SET ";
			for(Entry<String,String> entry:this.updateRequest.entrySet()) {
				update+=entry.getKey()+"="+entry.getValue()+",";
			}
			update=update.substring(0,update.length()-1);
			update+=" WHERE BOOK='"+review.getBook().getId().toString()+"' + AND CUSTOMER='"+review.getCustomer().getId().toString()+"'";
			sendUpdateToDatabase(update);
		}
		
	}
	

}
