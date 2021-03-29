package model;

import java.util.List;

import data.beans.Book;
import data.dao.BookDAO;

public class MainPageModel {

	BookDAO book;;
	
	public MainPageModel() {
		book = new BookDAO();
	};
	
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
	
	
}
