package model;

import java.util.List;
import java.util.Map;

import data.beans.Book;
import data.dao.BookDAO;

public class MainPageModel {
	
	public static MainPageModel instance;
	private BookDAO book;;
	
	public MainPageModel() {};
	
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
	
	public Map <String, Integer> getBestSellerBooksByCategory () {
		return book.getCountPerCategory();
	}
	
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
	
	//getInstance will return that ONE instance of the pattern 
	//with the the DAO objects initialized..
	public static MainPageModel getInstance()throws ClassNotFoundException{
		if (instance==null) {
			instance =new MainPageModel();
			instance.book = new BookDAO();
		}
		return instance;
	}
	
}
