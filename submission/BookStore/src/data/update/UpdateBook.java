package data.update;


import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import data.beans.Book;
import data.beans.Customer;
import data.schema.BookSchema;

public class UpdateBook {
	public UpdateBook() {	
		
	}
	
	public BookUpdater requestUpdateExistingBook(Book book) {
		return new BookUpdater(book);
	}
	
	
	public InsertBookTitle requestNewBookInsertion() {
		return new InsertBookTitle(new Book.Builder().build());
	}

	public class InsertBookTitle extends BookInsert{
		InsertBookTitle(Book book){
			super(book);
		}
		
		public InsertBookSeries insertBookWithTitle(String title){
			return new InsertBookSeries(new Book.Builder(book).withTitle(title).build());
		}
	}
	

	public class InsertBookSeries extends BookInsert{
		InsertBookSeries(Book book){
			super(book);
		}
		
		public InsertBookCategory insertBookWithSeries(String series){
			return new InsertBookCategory(new Book.Builder(book).withSeries(series).build());
		}
	}
	
	public class InsertBookCategory extends BookInsert {
		InsertBookCategory(Book book) {
			super(book);
		}

		public InsertBookAuthor insertBookWithCategory(String category){
			return new InsertBookAuthor(new Book.Builder(book).withCategory(category).build());
		}
	}
	
	public class InsertBookAuthor extends BookInsert{
		InsertBookAuthor(Book book) {
			super(book);
			// TODO Auto-generated constructor stub
		}

		public InsertBookDescription insertBookWithAuthor(String author){
			return new InsertBookDescription(new Book.Builder(book).withAuthor(author).build());
		}
	}
	
	
	
	public class InsertBookDescription extends BookInsert{
		InsertBookDescription(Book book){
			super(book);
		}
		
		public InsertBookPublishYear insertBookWithDescription(String description){
			
			return new InsertBookPublishYear(new Book.Builder(book).withDescription(description).build());
		}
	}
	
	public class InsertBookPublishYear extends BookInsert{
		InsertBookPublishYear(Book book){
			super(book);
		}
		
		public InsertBookCover insertBookWithPublishYear(int publishYear){
			
			return new InsertBookCover(new Book.Builder(book).withPublishYear(publishYear).build());
		}
	}
	
	
	
	public class InsertBookCover extends BookInsert{
		InsertBookCover(Book book) {
			super(book);
			// TODO Auto-generated constructor stub
		}

		public InsertBookPrice insertBookWithCover(File cover){
			return new InsertBookPrice(new Book.Builder(book).withCover(cover).build());

		}
	}
	

	
	
	public class InsertBookPrice extends BookInsert{
		InsertBookPrice(Book book) {
			super(book);
			// TODO Auto-generated constructor stub
		}

		public InsertBookISBN insertBookWithPrice(double price){
			return new InsertBookISBN(new Book.Builder(book).withPrice(price).build());

		}
	}
	
	public class InsertBookISBN extends BookInsert{
		InsertBookISBN(Book book) {
			super(book);
			// TODO Auto-generated constructor stub
		}

		public ExecuteBookInsertion insertBookWithISBN(String ISBN){
			return new ExecuteBookInsertion(new Book.Builder(book).withISBN(ISBN).build());

		}
	}
	
	public class ExecuteBookInsertion extends BookInsert{
		ExecuteBookInsertion(Book book) {
			super(book);
			// TODO Auto-generated constructor stub
		}

		public void executeBookInsertion(){
			String idInput=book.getISBN()+book.getPublishYear();
			String id =UUID.nameUUIDFromBytes(idInput.getBytes()).toString().stripLeading().stripTrailing();
			String update ="INSERT INTO BOOK (ID,TITLE ,SERIES ,DESCRIPTION ,CATEGORY,AUTHOR,COVER,ISBN ,PUBLISH_YEAR,PRICE)	VALUES 	"+
					"('"+id+"','"+book.getTitle()+"','"+book.getSeries()+"','"+book.getDescription()+"','"+book.getCategory()+"','"+book.getAuthor()+"','"+book.getCover().getName()+"','"+book.getISBN()+"',"+Integer.toString(book.getPublishYear())+","+Double.toString(book.getPrice())+")";
			sendUpdateToDatabase(update);
		}
	}
	
	abstract class BookInsert extends DataUpdate{
		Book book;
		BookInsert(Book book){
			this.book=book;
		}
	}
	
	public class BookUpdater extends DataUpdate{
		Map<String,String> updateRequest;
		private Book book;
		private BookSchema bookSchema = new BookSchema();
		BookUpdater(Book book){
			this.updateRequest=new LinkedHashMap<String, String>();
			this.book=book;
		}
		public BookUpdater updateBookTitle(String title){
			this.updateRequest.put(bookSchema.TITLE, surroundWithQuotes(title));
			return this;
			
		}
		
		public BookUpdater updateBookSeries(String series){
			this.updateRequest.put(bookSchema.SERIES, surroundWithQuotes(series));
			return this;
		}
		
		public BookUpdater updateBookCategory(String category){
			this.updateRequest.put(bookSchema.CATEGORY, surroundWithQuotes(category));
			return this;
		}
		
		
		public BookUpdater updateBookDescription(String description){
			this.updateRequest.put(bookSchema.DESCRIPTION, surroundWithQuotes(description));
			return this;
		}
		
		public BookUpdater updateBookAuthor(String author){
			this.updateRequest.put(bookSchema.AUTHOR, surroundWithQuotes(author));
			return this;
		}
		
		public BookUpdater updateBookCover(File cover){
			this.updateRequest.put(bookSchema.COVER, surroundWithQuotes(cover.getName()));
			return this;
		}
		
		public BookUpdater updateBookISBN(String ISBN){
			this.updateRequest.put(bookSchema.ISBN, surroundWithQuotes(ISBN));
			return this;
		}
		
		private String surroundWithQuotes(String word) {
			return "'"+word+"'";
		}
		
		public BookUpdater updateBookPrice(double price){
			this.updateRequest.put(bookSchema.PRICE, Double.toString(price));
			return this;
		}
		
		public BookUpdater updateBookPublishYear(int publishYear){
			this.updateRequest.put(bookSchema.PUBLISH_YEAR, Integer.toString(publishYear));
			return this;
		}
		
		public void executeUpdate() {
			String update = "UPDATE BOOK SET ";
			for(Entry<String,String> entry:this.updateRequest.entrySet()) {
				update+=entry.getKey()+"="+entry.getValue();
			}
			update+="WHERE ID='"+book.getId().toString()+"'";
			sendUpdateToDatabase(update);
		}
	}

}
