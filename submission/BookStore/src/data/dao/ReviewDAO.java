package data.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import data.beans.Bean;
import data.beans.Book;
import data.beans.Customer;
import data.beans.Id;
import data.beans.Review;
import data.beans.User;
import data.dao.BookDAO.BookStoreBookQuery;
import data.dao.CustomerDAO.BookStoreCustomerQuery;
import data.dao.PurchaseOrderDAO.BookStorePurchaseOrderQuery;
import data.dao.PurchaseOrderDAO.PurchaseOrderAttributeAccess;
import data.dao.PurchaseOrderDAO.PurchaseOrderBookQuery;
import data.dao.PurchaseOrderDAO.PurchaseOrderObjectQuery;
import data.dao.PurchaseOrderDAO.PurchaseOrderUserQuery;
import data.dao.ReviewDAO.BookStoreReviewQuery;
import data.query.AttributeAccess;
import data.query.BookStoreNumberQuery;
import data.query.BookStoreQuery;
import data.query.BookStoreVarCharQuery;
import data.query.DataAccessString;
import data.query.PageRequestMetaData;
import data.schema.BookSchema;
import data.schema.CartSchema;
import data.schema.CustomerSchema;
import data.schema.DataSchema;
import data.schema.PurchaseOrderSchema;
import data.schema.ReviewSchema;

public class ReviewDAO implements DAO{
	private ReviewSchema reviewSchema;
	public ReviewDAO(){
		this.reviewSchema=new ReviewSchema();
	}
	
	@Override
	public UpdateReview newUpdateRequest() {
		return new UpdateReview();
	}
	
	@Override
	public BookStoreReviewQuery newQueryRequest(){
		BookStoreReviewQuery bookStoreReviewQuery= new BookStoreReviewQuery(reviewSchema);
		ReviewAttributeAccess reviewAttributeAccess= new ReviewAttributeAccess();
		bookStoreReviewQuery.setAttribute(reviewAttributeAccess);
		reviewAttributeAccess.setBookStoreReviewQuery(bookStoreReviewQuery);
		return bookStoreReviewQuery;
	}
	
	public class BookStoreReviewQuery extends BookStoreQuery<BookStoreReviewQuery,ReviewAttributeAccess>{
		public BookStoreReviewQuery(BookStoreReviewQuery query, DataSchema dataSchema){
			super(query,  dataSchema);
		}
		public BookStoreReviewQuery(DataSchema dataSchema){
			super(dataSchema);
		}
		public BookStoreReviewQuery includeReviewBodyInResult(){
			if(!this.attributesToIncludInResults.containsKey(reviewSchema.tableName())) this.attributesToIncludInResults.put(reviewSchema.tableName(), new HashSet<String>());
				this.attributesToIncludInResults.get(reviewSchema.tableName()).add(reviewSchema.BODY);				
			includeKeyInResults();
			return this;
		}
		public BookStoreReviewQuery includeReviewTitleInResult(){
			if(!this.attributesToIncludInResults.containsKey(reviewSchema.tableName())) this.attributesToIncludInResults.put(reviewSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(reviewSchema.tableName()).add(reviewSchema.TITLE);
			includeKeyInResults();
			return this;
		}
		public BookStoreReviewQuery includeReviewRatingInResult(){
			if(!this.attributesToIncludInResults.containsKey(reviewSchema.tableName())) this.attributesToIncludInResults.put(reviewSchema.tableName(), new HashSet<String>());
				this.attributesToIncludInResults.get(reviewSchema.tableName()).add(reviewSchema.RATING);
				includeKeyInResults();
				return this;
		}
		
		public BookStoreReviewQuery includeReviewCreatedAtEpochInResult(){
			if(!this.attributesToIncludInResults.containsKey(reviewSchema.tableName())) this.attributesToIncludInResults.put(reviewSchema.tableName(), new HashSet<String>());
				this.attributesToIncludInResults.get(reviewSchema.tableName()).add(reviewSchema.CREATED_AT_EPOCH);
				includeKeyInResults();
				return this;
		}
		
		
//		public BookStoreReviewQuery includeReviewCustomerInResult(){
//			if(!this.attributesToIncludInResults.containsKey(reviewSchema.tableName())) this.attributesToIncludInResults.put(reviewSchema.tableName(), new HashSet<String>());
//			if(!this.attributesToIncludInResults.containsKey(new BookSchema().tableName())) this.attributesToIncludInResults.put(new BookSchema().tableName(), new HashSet<String>());
//			if (!isDisjunctionMode) {
//				if(!this.dataAccessRequestsConjunction.containsKey(new CustomerSchema().tableName())) this.dataAccessRequestsConjunction.put(new CustomerSchema().tableName(),new ArrayList<DataAccessString>());		
//			}else {
//				if(!this.dataAccessRequestsDisjunction.containsKey(new CustomerSchema().tableName())) this.dataAccessRequestsDisjunction.put(new CustomerSchema().tableName(), new ArrayList<DataAccessString>());
//			}
//			includeKeyInResults();
//			return this;
//		}
//		
//		public BookStoreReviewQuery includeReviewBooksInResult(){
//			if(!this.attributesToIncludInResults.containsKey(reviewSchema.tableName())) this.attributesToIncludInResults.put(reviewSchema.tableName(), new HashSet<String>());
//			if(!this.attributesToIncludInResults.containsKey(new BookSchema().tableName())) this.attributesToIncludInResults.put(new BookSchema().tableName(), new HashSet<String>());
//			if (!isDisjunctionMode) {
//				if(!this.dataAccessRequestsConjunction.containsKey(new BookSchema().tableName())) this.dataAccessRequestsConjunction.put(new BookSchema().tableName(),new ArrayList<DataAccessString>());		
//			}else {
//				if(!this.dataAccessRequestsDisjunction.containsKey(new BookSchema().tableName())) this.dataAccessRequestsDisjunction.put(new BookSchema().tableName(), new ArrayList<DataAccessString>());
//			}
//			includeKeyInResults();
//			return this;
//		}
//		
		public BookStoreReviewQuery excludeReviewBodyInResult(){
			if(this.attributesToIncludInResults.containsKey(reviewSchema.tableName())) this.attributesToIncludInResults.get(reviewSchema.tableName()).remove(reviewSchema.BODY);
			return this;
		}
		public BookStoreReviewQuery excludeReviewTitleInResult(){
			if(this.attributesToIncludInResults.containsKey(reviewSchema.tableName())) this.attributesToIncludInResults.get(reviewSchema.tableName()).remove(reviewSchema.TITLE);
			return this;
		}
		public BookStoreReviewQuery excludeReviewRatingInResult(){
			if(this.attributesToIncludInResults.containsKey(reviewSchema.tableName())) this.attributesToIncludInResults.get(reviewSchema.tableName()).remove(reviewSchema.RATING);
			return this;
		}
		
		public BookStoreReviewQuery excludeReviewCreatedAtEpochInResult(){
			if(this.attributesToIncludInResults.containsKey(reviewSchema.tableName())) this.attributesToIncludInResults.get(reviewSchema.tableName()).remove(reviewSchema.CREATED_AT_EPOCH);
			return this;
		}
		
		public BookStoreBookQuery queryBook() {
			this.tableJoins.add(
					new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(ReviewSchema.BOOK)
					.withDataAccessParameterPrefix("=")
					.withDataAccessParameterSuffix("")
					.withDataAccessParameter(new BookSchema().tableName()+this.referenceOperator+BookSchema.ID)
					.build()
					);
			return new BookDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData).addTableJoins(tableJoins);
		}
		public BookStoreCustomerQuery queryCustomer() {
			this.tableJoins.add(
					new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(ReviewSchema.CUSTOMER)
					.withDataAccessParameterPrefix("="+"")
					.withDataAccessParameterSuffix("")
					.withDataAccessParameter(new CustomerSchema().tableName()+this.referenceOperator+CustomerSchema.ID)
					.build()
					);
			return new CustomerDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData).addTableJoins(tableJoins);
		}
		
		private void includeKeyInResults() {
			if(!this.attributesToIncludInResults.containsKey(reviewSchema.tableName()) && !this.attributesToIncludInResults.get(reviewSchema.tableName()).isEmpty() && !this.attributesToIncludInResults.get(reviewSchema.tableName()).contains(reviewSchema.BOOK))
			this.attributesToIncludInResults.get(reviewSchema.tableName()).add(reviewSchema.BOOK);
			
			if(!this.attributesToIncludInResults.containsKey(reviewSchema.tableName()) && !this.attributesToIncludInResults.get(reviewSchema.tableName()).isEmpty() && !this.attributesToIncludInResults.get(reviewSchema.tableName()).contains(reviewSchema.CUSTOMER))
			this.attributesToIncludInResults.get(reviewSchema.tableName()).add(reviewSchema.CUSTOMER);

		}
	}
	public class ReviewVarCharQuery extends BookStoreVarCharQuery<ReviewVarCharQuery,ReviewAttributeAccess,BookStoreReviewQuery>{
		private ReviewAttributeAccess reviewAttributeAccess;
		ReviewVarCharQuery(BookStoreReviewQuery bookStoreReviewQuery, String currentAttributeAccess){
			super(bookStoreReviewQuery,new ReviewSchema());
			this.currentAttributeAccess=currentAttributeAccess;
		}
		ReviewVarCharQuery(BookStoreReviewQuery bookStoreReviewQuery, String currentAttributeAccess,PageRequestMetaData pageRequestMetaData){
			super(bookStoreReviewQuery,new ReviewSchema());
			this.currentAttributeAccess=currentAttributeAccess;
			this.pageRequestMetaData=pageRequestMetaData;
		}
		public ReviewAttributeAccess queryReviewAttribute(){
			return reviewAttributeAccess;
		}
		public BookStoreBookQuery queryBook() {
			this.tableJoins.add(
					new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(ReviewSchema.BOOK)
					.withDataAccessParameterPrefix("=")
					.withDataAccessParameterSuffix("")
					.withDataAccessParameter(new BookSchema().tableName()+this.referenceOperator+BookSchema.ID)
					.build()
					);
			return new BookDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData).addTableJoins(tableJoins);
		}
		public BookStoreCustomerQuery queryCustomer() {
			this.tableJoins.add(
					new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(ReviewSchema.CUSTOMER)
					.withDataAccessParameterPrefix("="+"")
					.withDataAccessParameterSuffix("")
					.withDataAccessParameter(new CustomerSchema().tableName()+this.referenceOperator+CustomerSchema.ID)
					.build()
					);
			return new CustomerDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData).addTableJoins(tableJoins);
		}

	}
	public class ReviewNumberQuery extends BookStoreNumberQuery<ReviewNumberQuery,ReviewAttributeAccess,BookStoreReviewQuery>{
		private ReviewAttributeAccess reviewAttributeAccess;
		ReviewNumberQuery(BookStoreReviewQuery bookStoreReviewQuery, String currentAttributeAccess){
			super(bookStoreReviewQuery,new ReviewSchema());
			this.currentAttributeAccess=currentAttributeAccess;
		}
		ReviewNumberQuery(BookStoreReviewQuery bookStoreReviewQuery, String currentAttributeAccess,PageRequestMetaData pageRequestMetaData){
			super(bookStoreReviewQuery,new ReviewSchema());
			this.currentAttributeAccess=currentAttributeAccess;
			this.pageRequestMetaData=pageRequestMetaData;
		}
		public ReviewAttributeAccess queryReviewAttribute(){
			return reviewAttributeAccess;
		}
		public BookStoreBookQuery queryBook() {
			this.tableJoins.add(
					new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(ReviewSchema.BOOK)
					.withDataAccessParameterPrefix("=")
					.withDataAccessParameterSuffix("")
					.withDataAccessParameter(new BookSchema().tableName()+this.referenceOperator+BookSchema.ID)
					.build()
					);
			return new BookDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData).addTableJoins(tableJoins);
		}
		public BookStoreCustomerQuery queryCustomer() {
			this.tableJoins.add(
					new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(ReviewSchema.CUSTOMER)
					.withDataAccessParameterPrefix("="+"")
					.withDataAccessParameterSuffix("")
					.withDataAccessParameter(new CustomerSchema().tableName()+this.referenceOperator+CustomerSchema.ID)
					.build()
					);
			return new CustomerDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData).addTableJoins(tableJoins);
		}

	}
	
	
	public abstract class ReviewObjectQuery<T extends ReviewObjectQuery> extends BookStoreQuery<T,ReviewAttributeAccess>{
		protected ReviewAttributeAccess reviewAttributeAccess;

		ReviewObjectQuery(BookStoreReviewQuery bookStoreReviewQuery, String currentAttributeAccess){
			super(bookStoreReviewQuery,new ReviewSchema());
			this.currentAttributeAccess=currentAttributeAccess;
		}
		ReviewObjectQuery(BookStoreReviewQuery bookStoreReviewQuery, String currentAttributeAccess,PageRequestMetaData pageRequestMetaData){
			super(bookStoreReviewQuery,new ReviewSchema());
			this.currentAttributeAccess=currentAttributeAccess;
			this.pageRequestMetaData=pageRequestMetaData;
		}
		
		public ReviewAttributeAccess queryReviewAttributeAccess(){
			return reviewAttributeAccess;
		}
		
		public BookStoreBookQuery queryBook() {
			this.tableJoins.add(
					new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(ReviewSchema.BOOK)
					.withDataAccessParameterPrefix("=")
					.withDataAccessParameterSuffix("")
					.withDataAccessParameter(new BookSchema().tableName()+this.referenceOperator+BookSchema.ID)
					.build()
					);
			return new BookDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData).addTableJoins(tableJoins);
		}
		public BookStoreCustomerQuery queryCustomer() {
			this.tableJoins.add(
					new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(ReviewSchema.CUSTOMER)
					.withDataAccessParameterPrefix("="+"")
					.withDataAccessParameterSuffix("")
					.withDataAccessParameter(new CustomerSchema().tableName()+this.referenceOperator+CustomerSchema.ID)
					.build()
					);
			return new CustomerDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData).addTableJoins(tableJoins);
		}
	}
	
	
	public class ReviewCustomerQuery extends ReviewObjectQuery<ReviewCustomerQuery>{


		ReviewCustomerQuery(BookStoreReviewQuery bookStoreReviewQuery, String currentAttributeAccess) {
			super(bookStoreReviewQuery, currentAttributeAccess);
			// TODO Auto-generated constructor stub
		}
		ReviewCustomerQuery(BookStoreReviewQuery bookStoreReviewQuery, String currentAttributeAccess,
				PageRequestMetaData pageRequestMetaData) {
			super(bookStoreReviewQuery, currentAttributeAccess, pageRequestMetaData);
			// TODO Auto-generated constructor stub
		}

		public ReviewCustomerQuery isCustomer(Customer customer) {
//			if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//				this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//			}
//			this.dataAccessRequests.get(this.dataSchema.tableName())
//			.add(new DataAccessString.Builder()
//					.withTableName(this.dataSchema.tableName())
//					.withReferenceOperator(this.referenceOperator)
//					.withAttributeName(ReviewSchema.CUSTOMER)
//					.withDataAccessParameterPrefix("="+"'")
//					.withDataAccessParameterSuffix("'")
//					.withDataAccessParameter(customer.getId().toString())
//					.build()
//					);
			this.addDataAccessString(new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(ReviewSchema.CUSTOMER)
					.withDataAccessParameterPrefix("="+"'")
					.withDataAccessParameterSuffix("'")
					.withDataAccessParameter(customer.getId().toString())
					.build()
					);
			return  this;
		}

	}
	
	public class ReviewBookQuery extends ReviewObjectQuery<ReviewBookQuery>{
		ReviewBookQuery(BookStoreReviewQuery bookStoreReviewQuery, String currentAttributeAccess) {
			super(bookStoreReviewQuery, currentAttributeAccess);
			// TODO Auto-generated constructor stub
		}
		
		ReviewBookQuery(BookStoreReviewQuery bookStoreReviewQuery, String currentAttributeAccess,
				PageRequestMetaData pageRequestMetaData) {
			super(bookStoreReviewQuery, currentAttributeAccess, pageRequestMetaData);
			// TODO Auto-generated constructor stub
		}


		public ReviewBookQuery isBook(Book book) {
//			if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//				this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//			}
//			this.dataAccessRequests.get(this.dataSchema.tableName())
//			.add(new DataAccessString.Builder()
//					.withTableName(this.dataSchema.tableName())
//					.withReferenceOperator(this.referenceOperator)
//					.withAttributeName(ReviewSchema.BOOK)
//					.withDataAccessParameterPrefix("="+"'")
//					.withDataAccessParameterSuffix("'")
//					.withDataAccessParameter(book.getId().toString())
//					.build()
//					);
			this.addDataAccessString(new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(ReviewSchema.BOOK)
					.withDataAccessParameterPrefix("="+"'")
					.withDataAccessParameterSuffix("'")
					.withDataAccessParameter(book.getId().toString())
					.build()
					);
			return  this;
		}
	}
	
	public class ReviewKeyQuery extends ReviewObjectQuery<ReviewKeyQuery>{
		ReviewKeyQuery(BookStoreReviewQuery bookStoreReviewQuery, String currentAttributeAccess) {
			super(bookStoreReviewQuery, currentAttributeAccess);
			// TODO Auto-generated constructor stub
		}
		
		ReviewKeyQuery(BookStoreReviewQuery bookStoreReviewQuery, String currentAttributeAccess,
				PageRequestMetaData pageRequestMetaData) {
			super(bookStoreReviewQuery, currentAttributeAccess, pageRequestMetaData);
			// TODO Auto-generated constructor stub
		}


		public ReviewKeyQuery isReview(Review review) {
//			if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//				this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//			}
//			this.dataAccessRequests.get(this.dataSchema.tableName())
//			.add(new DataAccessString.Builder()
//					.withTableName(this.dataSchema.tableName())
//					.withReferenceOperator(this.referenceOperator)
//					.withAttributeName(reviewSchema.CUSTOMER)
//					.withDataAccessParameterPrefix("="+"'")
//					.withDataAccessParameterSuffix("'")
//					.withDataAccessParameter(review.getCustomer().getId().toString())
//					.build()
//					);
			this.addDataAccessString(new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(reviewSchema.CUSTOMER)
					.withDataAccessParameterPrefix("="+"'")
					.withDataAccessParameterSuffix("'")
					.withDataAccessParameter(review.getCustomer().getId().toString())
					.build()
					);
//			this.dataAccessRequests.get(this.dataSchema.tableName())
//			.add(new DataAccessString.Builder()
//					.withTableName(this.dataSchema.tableName())
//					.withReferenceOperator(this.referenceOperator)
//					.withAttributeName(reviewSchema.BOOK)
//					.withDataAccessParameterPrefix("="+"'")
//					.withDataAccessParameterSuffix("'")
//					.withDataAccessParameter(review.getBook().getId().toString())
//					.build()
//					);
			this.addDataAccessString(new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(reviewSchema.BOOK)
					.withDataAccessParameterPrefix("="+"'")
					.withDataAccessParameterSuffix("'")
					.withDataAccessParameter(review.getBook().getId().toString())
					.build()
					);
			return  this;
		}
	}
	
	public class ReviewAttributeAccess extends AttributeAccess<BookStoreReviewQuery>{
		protected BookStoreReviewQuery bookStoreReviewQuery;
		protected ReviewAttributeAccess() {
		}
		protected void setBookStoreReviewQuery(BookStoreReviewQuery bookStoreReviewQuery){
			this.bookStoreReviewQuery=bookStoreReviewQuery;
		}
		
		public ReviewKeyQuery whereReview(){
			ReviewKeyQuery reviewKeyQuery= new ReviewKeyQuery(this.bookStoreReviewQuery,ReviewSchema.CUSTOMER);
			reviewKeyQuery.setAttribute(this);
			return reviewKeyQuery;
		}
		
		public ReviewVarCharQuery whereReviewBody(){
			ReviewVarCharQuery reviewVarCharQuery= new ReviewVarCharQuery(this.bookStoreReviewQuery,ReviewSchema.BODY);
			reviewVarCharQuery.setAttribute(this);
			return reviewVarCharQuery;
		}
		public ReviewVarCharQuery whereReviewTitle(){
			ReviewVarCharQuery reviewVarCharQuery= new ReviewVarCharQuery(this.bookStoreReviewQuery,ReviewSchema.TITLE);
			reviewVarCharQuery.setAttribute(this);
			return reviewVarCharQuery;
		}
		public ReviewNumberQuery whereReviewRating(){
			ReviewNumberQuery reviewNumberQuery= new ReviewNumberQuery(this.bookStoreReviewQuery,ReviewSchema.RATING);
			reviewNumberQuery.setAttribute(this);
			return reviewNumberQuery;
		}
		
		public ReviewNumberQuery whereReviewCreatedAtEpoch(){
			ReviewNumberQuery reviewNumberQuery= new ReviewNumberQuery(this.bookStoreReviewQuery,ReviewSchema.CREATED_AT_EPOCH);
			reviewNumberQuery.setAttribute(this);
			return reviewNumberQuery;
		}
		
		public ReviewBookQuery whereReviewBook(){
			ReviewBookQuery reviewBookQuery= new ReviewBookQuery(this.bookStoreReviewQuery,ReviewSchema.BOOK);
			reviewBookQuery.setAttribute(this);
			return reviewBookQuery;
		}
		
		public ReviewCustomerQuery whereReviewCustomer(){
			ReviewCustomerQuery reviewCustomerQuery= new ReviewCustomerQuery(this.bookStoreReviewQuery,PurchaseOrderSchema.ID);
			reviewCustomerQuery.setAttribute(this);
			return reviewCustomerQuery;
		}
	}
}