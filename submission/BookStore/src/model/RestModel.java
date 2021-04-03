package model;

import java.util.List;

import data.beans.Book;
import data.dao.BookDAO;

public class RestModel {
	
	private static RestModel instance;
	private BookDAO book;
	
	public RestModel() {
		
	}
	
	public static RestModel getInstance() {
		if (instance == null) {
			instance = new RestModel();
			instance.book = new BookDAO();
		}
		return instance;
	}
	
	/**
	 * Get the book instance of a specific book id
	 * 
	 * @param prodID - unique id of book
	 * @return a JSON String containing the information of the book, or error messages.
	 */
	public String getBookByID (String prodID) throws Exception{
		
		String bookString = "";
		
		List<Book> b = this.book.newQueryRequest()
				 .includeAllAttributesInResultFromSchema()
				 .queryAttribute()
				 .whereBookISBN()
				 .varCharEquals(prodID)
				 .executeQuery()
				 .executeCompilation()
				 .compileBooks();
		
		System.out.println(b.size());
		
		if (b.size() == 0)
			bookString = "{\n"
					+ "\"error\": {\n"
					+ " \"errors\": [\n"
					+ "  {\n"
					+ "   \"domain\": \"global\",\n"
					+ "   \"reason\": \"notFound\",\n"
					+ "   \"message\": \"Book with ISBN Not Found\"\n"
					+ "  }\n"
					+ " ],\n"
					+ " \"code\": 404,\n"
					+ " \"message\": \"Not Found\"\n"
					+ " }\n"
					+ "}";
		else if (b.size() > 1)
			bookString = "{\n"
					+ " \"error\": {\n"
					+ "  \"errors\": [\n"
					+ "   {\n"
					+ "    \"domain\": \"global\",\n"
					+ "    \"reason\": \"forbidden\",\n"
					+ "    \"message\": \"Forbidden, Retrieved multiple books with given ISBN: \'" + prodID + "\' please providd this output to site admin: info@bookstore.ca\"\n"
					+ "    }\n"
					+ "  ],\n"
					+ "  \"code\": 403,\n"
					+ "  \"message\": \"Forbidden\"\n"
					+ " }\n"
					+ "}";
		else 
			bookString = b.get(0).toJson();
		
		return bookString;
	}
	
}
