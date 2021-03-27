package data.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import data.query.AttributeAccess;
import data.query.BookStoreNumberQuery;
import data.query.BookStoreQuery;
import data.query.BookStoreVarCharQuery;
import data.query.DataAccessString;
import data.query.PageRequestMetaData;
import data.beans.Book;
import data.beans.Id;
import data.beans.Review;
import data.dao.CartDAO.CartAttributeAccess;
import data.dao.ReviewDAO.BookStoreReviewQuery;
import data.schema.BookSchema;
import data.schema.CartSchema;
import data.schema.DataSchema;
import data.schema.PurchaseOrderSchema;
import data.schema.ReviewSchema;
import data.schema.UserTypes;

public class BookDAO implements DAO{

	private BookSchema bookSchema;
	public BookDAO() {
		this.bookSchema=new BookSchema();
	}
	
	@Override
	public UpdateBook newUpdateRequest() {
		return new UpdateBook();
	}

	@Override
	public BookStoreBookQuery newQueryRequest() {
		BookStoreBookQuery bookStoreQuery= new BookStoreBookQuery(bookSchema);
		BookAttributeAccess bookAttributeAccess= new BookAttributeAccess();
		bookStoreQuery.setAttribute(bookAttributeAccess);
		bookAttributeAccess.setBookQuery(bookStoreQuery);
		return bookStoreQuery;
	}
	

//	private static BookStoreReviewQuery queryReviews(Map<String,Set<String>> attributesToIncludInResults, Map<String,List<DataAccessString>> dataAccessRequests,PageRequestMetaData pageRequestMetaData,List<DataAccessString> references) {
//		
//		return new ReviewDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequests(dataAccessRequests).setPageRequestMetaData(pageRequestMetaData).setReferences(references);
//	}

	
	public class BookStoreBookQuery extends BookStoreQuery<BookStoreBookQuery,BookAttributeAccess>{

		public BookStoreBookQuery(BookStoreBookQuery query, DataSchema dataSchema) {
			super(query, dataSchema);
		}
		
		public BookStoreBookQuery(DataSchema dataSchema) {
			super(dataSchema);
		}
		


		public BookStoreBookQuery includeBookTitleInResult() {
			if(!this.attributesToIncludInResults.containsKey(bookSchema.tableName())) this.attributesToIncludInResults.put(bookSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(bookSchema.tableName()).add(bookSchema.TITLE);
			includeKeyInResults();
			return this;
		}
		public BookStoreBookQuery includeBookDescriptionInResult() {
			if(!this.attributesToIncludInResults.containsKey(bookSchema.tableName())) this.attributesToIncludInResults.put(bookSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(bookSchema.tableName()).add(bookSchema.DESCRIPTION);
			includeKeyInResults();
			return this;
		}
		public BookStoreBookQuery includeBookCategoryInResult() {
			if(!this.attributesToIncludInResults.containsKey(bookSchema.tableName())) this.attributesToIncludInResults.put(bookSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(bookSchema.tableName()).add(bookSchema.CATEGORY);
			includeKeyInResults();
			return this;
		}
		public BookStoreBookQuery includeBookAuthorInResult() {
			if(!this.attributesToIncludInResults.containsKey(bookSchema.tableName())) this.attributesToIncludInResults.put(bookSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(bookSchema.tableName()).add(bookSchema.AUTHOR);
			includeKeyInResults();
			return this;
		}
		public BookStoreBookQuery includeBookCoverInResult() {
			if(!this.attributesToIncludInResults.containsKey(bookSchema.tableName())) this.attributesToIncludInResults.put(bookSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(bookSchema.tableName()).add(bookSchema.COVER);
			includeKeyInResults();
			return this;
		}
		public BookStoreBookQuery includeBookPublishYearInResult() {
			if(!this.attributesToIncludInResults.containsKey(bookSchema.tableName())) this.attributesToIncludInResults.put(bookSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(bookSchema.tableName()).add(bookSchema.PUBLISH_YEAR);
			includeKeyInResults();
			return this;
		}
		public BookStoreBookQuery includeBookRatingInResult() {
			if(!this.attributesToIncludInResults.containsKey(bookSchema.tableName())) this.attributesToIncludInResults.put(bookSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(bookSchema.tableName()).add(bookSchema.RATING);
			includeKeyInResults();
			return this;
		}

		public BookStoreBookQuery includeBookPriceInResult() {
			if(!this.attributesToIncludInResults.containsKey(bookSchema.tableName())) this.attributesToIncludInResults.put(bookSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(bookSchema.tableName()).add(bookSchema.PRICE);
			includeKeyInResults();
			return this;
		}
		public BookStoreBookQuery includeBookAmountSoldInResult() {
			if(!this.attributesToIncludInResults.containsKey(bookSchema.tableName())) this.attributesToIncludInResults.put(bookSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(bookSchema.tableName()).add(bookSchema.AMOUNT_SOLD);
			includeKeyInResults();
			return this;
		}
		
		public BookStoreBookQuery excludeBookTitleInResult() {
			if(this.attributesToIncludInResults.containsKey(bookSchema.tableName())) this.attributesToIncludInResults.get(bookSchema.tableName()).remove(bookSchema.TITLE);
			return this;
		}
		
		public BookStoreBookQuery excludeBookDescriptionInResult() {
			if(this.attributesToIncludInResults.containsKey(bookSchema.tableName())) this.attributesToIncludInResults.get(bookSchema.tableName()).remove(bookSchema.DESCRIPTION);		
			return this;
		}
		public BookStoreBookQuery excludeBookCategoryInResult() {
			if(this.attributesToIncludInResults.containsKey(bookSchema.tableName())) this.attributesToIncludInResults.get(bookSchema.tableName()).remove(bookSchema.CATEGORY);
			return this;
		}
		public BookStoreBookQuery excludeBookAuthorInResult() {
			if(this.attributesToIncludInResults.containsKey(bookSchema.tableName()))this.attributesToIncludInResults.get(bookSchema.tableName()).remove(bookSchema.AUTHOR);
			return this;
		}
		public BookStoreBookQuery excludeBookCoverInResult() {
			if(this.attributesToIncludInResults.containsKey(bookSchema.tableName())) this.attributesToIncludInResults.get(bookSchema.tableName()).remove(bookSchema.COVER);
			return this;
		}
		public BookStoreBookQuery excludeBookPublishYearInResult() {
			if(this.attributesToIncludInResults.containsKey(bookSchema.tableName())) this.attributesToIncludInResults.get(bookSchema.tableName()).remove(bookSchema.PUBLISH_YEAR);
			return this;
		}
		public BookStoreBookQuery excludeBookRatingInResult() {
			if(!this.attributesToIncludInResults.containsKey(bookSchema.tableName())) this.attributesToIncludInResults.get(bookSchema.tableName()).remove(bookSchema.RATING);
			return this;
		}

		public BookStoreBookQuery excludeBookPriceInResult() {
			if(!this.attributesToIncludInResults.containsKey(bookSchema.tableName())) this.attributesToIncludInResults.get(bookSchema.tableName()).remove(bookSchema.PRICE);
			return this;
		}
		public BookStoreBookQuery excludeBookAmountSoldInResult() {
			if(!this.attributesToIncludInResults.containsKey(bookSchema.tableName())) this.attributesToIncludInResults.get(bookSchema.tableName()).remove(bookSchema.TITLE);
			return this;
		}
		
		
		public BookStoreReviewQuery queryReviews() {
//			this.references.put(tableName, new ArrayList<DataAccessString>());
//			this.references.get(tableName).addAll(BookStoreDAO.getReferenceDataAccessString(tableName, new ReviewSchema().tableName()));
			return new ReviewDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData);

		}
		
		private void includeKeyInResults() {
			if(!this.attributesToIncludInResults.containsKey(bookSchema.tableName()) && !this.attributesToIncludInResults.get(bookSchema.tableName()).isEmpty() && !this.attributesToIncludInResults.get(bookSchema.tableName()).contains(bookSchema.ID))
			this.attributesToIncludInResults.get(bookSchema.tableName()).add(bookSchema.ID);


		}



	}
	
	
	public class BookNumberQuery extends BookStoreNumberQuery<BookNumberQuery,BookAttributeAccess,BookStoreBookQuery>{
		private BookAttributeAccess bookAttributeAccess;
		
		BookNumberQuery(BookStoreBookQuery bookStoreQuery,String currentAttributeAccess) {
			super(bookStoreQuery, new BookSchema());
			this.currentAttributeAccess=currentAttributeAccess;
		}
		
		BookNumberQuery(BookStoreBookQuery bookStoreQuery,String currentAttributeAccess, PageRequestMetaData pageRequestMetaData) {
			super(bookStoreQuery, new BookSchema());
			this.pageRequestMetaData=pageRequestMetaData;
			this.currentAttributeAccess=currentAttributeAccess;
		}
		
		public BookAttributeAccess queryBookAttribute() {		
			return bookAttributeAccess;
		}
		
		public BookStoreReviewQuery queryReviews() {
//			this.references.put(tableName, new ArrayList<DataAccessString>());
//			this.references.get(tableName).addAll(BookStoreDAO.getReferenceDataAccessString(tableName, new ReviewSchema().tableName()));
			return new ReviewDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData);

		}
		
	}
	

	public class BookVarCharQuery extends BookStoreVarCharQuery<BookVarCharQuery,BookAttributeAccess,BookStoreBookQuery>{
		private BookAttributeAccess bookAttributeAccess;
		private BookDAO bookDAO;
		
		BookVarCharQuery(BookStoreBookQuery bookStoreQuery, String currentAttributeAccess) {
			super(bookStoreQuery, new BookSchema());
			this.currentAttributeAccess=currentAttributeAccess;
		}
		
		BookVarCharQuery(BookStoreBookQuery bookStoreQuery, String currentAttributeAccess, PageRequestMetaData pageRequestMetaData) {
			super(bookStoreQuery, new BookSchema());
			this.pageRequestMetaData=pageRequestMetaData;
			this.currentAttributeAccess=currentAttributeAccess;
		}
		
		public BookAttributeAccess queryBookAttribute() {		
			return bookAttributeAccess;
		}
		
		
		public BookStoreReviewQuery queryReviews() {
//			this.references.put(tableName, new ArrayList<DataAccessString>());
//			this.references.get(tableName).addAll(BookStoreDAO.getReferenceDataAccessString(tableName, new ReviewSchema().tableName()));
			return new ReviewDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData);

		}
		
	}
	
	public class BookKeyQuery extends BookStoreQuery<BookKeyQuery,BookAttributeAccess>{
		private BookAttributeAccess bookAttributeAccess;
		private BookDAO bookDAO;
		
		BookKeyQuery(BookStoreBookQuery bookStoreQuery, String currentAttributeAccess) {
			super(bookStoreQuery, new BookSchema());
			this.currentAttributeAccess=currentAttributeAccess;
		}
		
		BookKeyQuery(BookStoreBookQuery bookStoreQuery, String currentAttributeAccess, PageRequestMetaData pageRequestMetaData) {
			super(bookStoreQuery, new BookSchema());
			this.pageRequestMetaData=pageRequestMetaData;
			this.currentAttributeAccess=currentAttributeAccess;
		}
		
		public BookAttributeAccess queryBookAttribute() {		
			return bookAttributeAccess;
		}
		
		public BookStoreReviewQuery queryReviews() {
//			this.references.put(tableName, new ArrayList<DataAccessString>());
//			this.references.get(tableName).addAll(BookStoreDAO.getReferenceDataAccessString(tableName, new ReviewSchema().tableName()));
			return new ReviewDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData);

		}
		
		public BookKeyQuery isBook(Book book) {
//			if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//				this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//			}
//			this.addDataAccessString(null);
			this.addDataAccessString(new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(BookSchema.ID)
					.withDataAccessParameterPrefix("="+"'")
					.withDataAccessParameterSuffix("'")
					.withDataAccessParameter(book.getId().toString())
					.build()
					);
//			.add(new DataAccessString.Builder()
//					.withTableName(this.dataSchema.tableName())
//					.withReferenceOperator(this.referenceOperator)
//					.withAttributeName(BookSchema.ID)
//					.withDataAccessParameterPrefix("="+"'")
//					.withDataAccessParameterSuffix("'")
//					.withDataAccessParameter(book.getId().toString())
//					.build()
//					);
			return  this;
		}
	}
	
	public class BookAttributeAccess extends AttributeAccess<BookStoreBookQuery>{

		protected BookStoreBookQuery bookStoreBookQuery;
		protected BookAttributeAccess() {
			
		}
		
		protected void setBookQuery(BookStoreBookQuery bookStoreQuery) {			
			this.bookStoreBookQuery=bookStoreQuery;
		}
		
		public BookKeyQuery whereBook() {
			BookKeyQuery bookKeyQuery= new BookKeyQuery(this.bookStoreBookQuery,BookSchema.ID);
			bookKeyQuery.setAttribute(this);
			return bookKeyQuery;
		}
		
		public BookVarCharQuery whereBookTitle() {
			BookVarCharQuery bookVarCharQuery= new BookVarCharQuery(this.bookStoreBookQuery,BookSchema.TITLE);
			bookVarCharQuery.setAttribute(this);
			return bookVarCharQuery;
		}
		
		public BookVarCharQuery whereBookDescription() {
			BookVarCharQuery bookVarCharQuery= new BookVarCharQuery(this.bookStoreBookQuery,BookSchema.DESCRIPTION);
			bookVarCharQuery.setAttribute(this);
			return bookVarCharQuery;
			
		}
		

		
		public BookVarCharQuery whereBookCategory() {
			BookVarCharQuery bookVarCharQuery= new BookVarCharQuery(this.bookStoreBookQuery,BookSchema.CATEGORY,this.bookStoreBookQuery.getPageData());
			bookVarCharQuery.setAttribute(this);
			return bookVarCharQuery;
			
		}
		
		public BookVarCharQuery whereBookAuthor() {
			BookVarCharQuery bookVarCharQuery= new BookVarCharQuery(this.bookStoreBookQuery,BookSchema.AUTHOR);
			bookVarCharQuery.setAttribute(this);
			return bookVarCharQuery;
			
		}
		
		public BookVarCharQuery whereBookISBN() {
			BookVarCharQuery bookVarCharQuery= new BookVarCharQuery(this.bookStoreBookQuery,BookSchema.ISBN,this.bookStoreBookQuery.getPageData());
			bookVarCharQuery.setAttribute(this);
			return bookVarCharQuery;
			
		}
		
		public BookNumberQuery whereBookPublishYear() {
			BookNumberQuery bookNumberQuery= new BookNumberQuery(this.bookStoreBookQuery,BookSchema.PUBLISH_YEAR);
			bookNumberQuery.setAttribute(this);
			return bookNumberQuery;
			
		}
		
		public BookNumberQuery whereBookRating() {
			BookNumberQuery bookNumberQuery= new BookNumberQuery(this.bookStoreBookQuery,BookSchema.RATING);
			bookNumberQuery.setAttribute(this);
			return bookNumberQuery;
		}
		
		public BookNumberQuery whereBookPrice() {
			BookNumberQuery bookNumberQuery= new BookNumberQuery(this.bookStoreBookQuery,BookSchema.PRICE);
			bookNumberQuery.setAttribute(this);
			return bookNumberQuery;
		}
		
		public BookNumberQuery whereBookAmountSold() {
			BookNumberQuery bookNumberQuery= new BookNumberQuery(this.bookStoreBookQuery,BookSchema.AMOUNT_SOLD);
			bookNumberQuery.setAttribute(this);
			return bookNumberQuery;
		}

		
	}
	
	
	









}
