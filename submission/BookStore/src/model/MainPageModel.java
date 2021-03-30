package model;

import java.util.List;
import java.util.Map;

import data.beans.Book;
import data.dao.BookDAO;

import data.beans.Review;
import data.dao.ReviewDAO;


public class MainPageModel {
	
	public static MainPageModel instance;
	private BookDAO book;
	private ReviewDAO review;
	
	public MainPageModel() {};
	
	/**
	 * Get the book instance of a specific book id
	 * 
	 * @param prodID - unique id of book
	 * @return a book instance
	 * @throws Exception if id not found or not unique
	 */
	public Book getBookByID (String prodID) throws Exception{
		List<Book> b = book.newQueryRequest()
				 .includeAllAttributesInResultFromSchema()
				 .queryAttribute()
				 .whereBookISBN()
				 .varCharEquals(prodID)
				 .executeQuery()
				 .executeCompilation()
				 .compileBooks();
		
		
		if (b.size() == 0)
			throw new Exception("ID not found in database");
		else if (b.size() > 1)
			throw new Exception("There seem to be ID duplicaes. Aborting!");
		
		return b.get(0);
	}
	
	/**
	 * Get the number of books in each category
	 * 
	 * @return
	 * 		a map containing the genre of the book and the number of books in that genre
	 */
	public Map <String, Integer> getBestSellerBooksByCategory () {
		return book.getCountPerCategory();
	}
	
	/**
	 * Get the 20 most recommended books in a specific category
	 * 
	 * @param category - a specific genre
	 * @return
	 * 		a list of most recommended books in that category
	 */
	public List<Book> getBooksInThisCategory (String category) {
		return  book.newQueryRequest()
				.includeAllAttributesInResultFromSchema()
				.queryAttribute()
				.whereBookCategory()
				.varCharEquals(category)
				.queryAttribute()
				.whereBookRating()
				.withDescendingOrderOf()
				.withResultLimit(20)
				.executeQuery()
				.executeCompilation()
				.compileBooks();
	}
	
	/**
	 * Get a list of the next reviews to display to the user
	 * 
	 * @param prodID - the unique id of a book
	 * @param currentNumReviews - current number of displayed reviews 
	 * @return
	 * 		a list of new reviews to load
	 */
	public List<Review> getNextReviewsForThisBook (String prodID, int currentNumReviews) {
		
		int num = 0;
		
		if (currentNumReviews+15 < 0)
			num = 0;
		
		review.newQueryRequest()
			.includeAllAttributesInResultFromSchema()
			.queryAttribute()
			.whereReviewBody()
			.varCharEquals(prodID)
			.queryAttribute()
			.whereReviewRating()
			.withDescendingOrderOf()
			.withResultLimit(currentNumReviews+15)
			.executeQuery().executeCompilation();
		
		
		return null;
	}
	
	
	public List<Book>  prepSearchResult (String input) {
		
		int maxNum = book.getNumberBooks();
		
		
		
		List<Book> b= book.newQueryRequest()
						.includeAllAttributesInResultFromSchema()
						.queryAttribute()
						.whereBookCategory()
						.varCharContains(input)
						
						.queryAsDisjunction()
						.queryAttribute()
						.whereBookAuthor()
						.varCharContains(input)
						
						.queryAsDisjunction()
						.queryAttribute()
						.whereBookTitle()
						.varCharContains(input)
						
						.queryAsDisjunction()
						.queryAttribute()
						.whereBookDescription()
						.varCharContains(input)
						
						.withResultLimit(book.getNumberBooks())
						.executeQuery().
						executeCompilation().
						compileBooks();
						
		List<Book> b2 = book.newQueryRequest()
						.includeAllAttributesInResultFromSchema()		
						.queryAttribute()
						.whereBookAuthor()
						.varCharContains(input)
						.withResultLimit(maxNum)
						.executeQuery().
						executeCompilation().
						compileBooks();
		
		List<Book> b3 = book.newQueryRequest()
						.includeAllAttributesInResultFromSchema()	
						.queryAttribute()
						.whereBookTitle()
						.varCharContains(input)
						.withResultLimit(maxNum)
						.executeQuery().
						executeCompilation().
						compileBooks();
		
		List<Book> b4 = book.newQueryRequest()
						.includeAllAttributesInResultFromSchema()	
						.queryAttribute()
						.whereBookDescription()
						.varCharContains(input)
						.withResultLimit(maxNum)
						.executeQuery().
						executeCompilation().
						compileBooks();
		
//		b.addAll(b2);
//		b.addAll(b3);
//		b.addAll(b4);
//		
		return b;
	}
	
	//getInstance will return that ONE instance of the pattern 
	//with the the DAO objects initialized..
	public static MainPageModel getInstance()throws ClassNotFoundException{
		if (instance==null) {
			instance =new MainPageModel();
			instance.book = new BookDAO();
			instance.review = new ReviewDAO();
		}
		return instance;
	}
}
