package data.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.validation.Schema;
import data.beans.Book;
import data.beans.Customer;
import data.beans.Id;
import data.beans.Review;
import data.dao.BookDAO.BookAttributeAccess;
import data.dao.BookDAO.BookNumberQuery;
import data.dao.BookDAO.BookStoreBookQuery;
import data.dao.CartDAO.BookStoreCartQuery;
import data.dao.PurchaseOrderDAO.BookStorePurchaseOrderQuery;
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
import data.schema.UserTypes;
public class CustomerDAO implements DAO{
	private CustomerSchema customerSchema;
	public CustomerDAO(){
		this.customerSchema=new CustomerSchema();
	}
	@Override
	public UpdateCustomer newUpdateRequest() {
		return new UpdateCustomer();
	}
	
	@Override
	public BookStoreCustomerQuery newQueryRequest(){
		BookStoreCustomerQuery bookStoreCustomerQuery= new BookStoreCustomerQuery(customerSchema);
		CustomerAttributeAccess customerAttributeAccess= new CustomerAttributeAccess();
		bookStoreCustomerQuery.setAttribute(customerAttributeAccess);
		customerAttributeAccess.setBookStoreCustomerQuery(bookStoreCustomerQuery);
		return bookStoreCustomerQuery;
	}
	public class BookStoreCustomerQuery extends BookStoreQuery<BookStoreCustomerQuery,CustomerAttributeAccess>{
		public BookStoreCustomerQuery(BookStoreCustomerQuery query, DataSchema dataSchema){
			super(query,  dataSchema);
		}
		public BookStoreCustomerQuery(DataSchema dataSchema){
			super(dataSchema);
		}
		public BookStoreCustomerQuery includeCustomerGivenNameInResult(){
			if(!this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.put(customerSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(customerSchema.tableName()).add(customerSchema.GIVENNAME);
			includeKeyInResults();
			return this;
		}
		public BookStoreCustomerQuery includeCustomerSurNameInResult(){
			if(!this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.put(customerSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(customerSchema.tableName()).add(customerSchema.SURNAME);
			includeKeyInResults();
			return this;
		}
		public BookStoreCustomerQuery includeCustomerUserNameInResult(){
			if(!this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.put(customerSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(customerSchema.tableName()).add(customerSchema.USERNAME);
			return this;
		}
		public BookStoreCustomerQuery includeCustomerEmailInResult(){
			if(!this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.put(customerSchema.tableName(), new HashSet<String>());
				this.attributesToIncludInResults.get(customerSchema.tableName()).add(customerSchema.EMAIL);
				includeKeyInResults();
				return this;
		}
		public BookStoreCustomerQuery includeCustomerPasswordInResult(){
			if(!this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.put(customerSchema.tableName(), new HashSet<String>());
				this.attributesToIncludInResults.get(customerSchema.tableName()).add(customerSchema.PASSWORD);
				includeKeyInResults();
				return this;
		}
		
		
		public BookStoreCustomerQuery includeCustomerCreatedAtEpochInResult() {
			if(!this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.put(customerSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(customerSchema.tableName()).add(customerSchema.CREATED_AT_EPOCH);
			includeKeyInResults();
			return this;
		}
		


		
		public BookStoreCustomerQuery includeCustomerAddressInResult() {
			if(!this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.put(customerSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(customerSchema.tableName()).add(customerSchema.STREET_NUMBER);
			
			if(!this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.put(customerSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(customerSchema.tableName()).add(customerSchema.STREET);
			
			if(!this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.put(customerSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(customerSchema.tableName()).add(customerSchema.POSTAL_CODE);
			
			if(!this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.put(customerSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(customerSchema.tableName()).add(customerSchema.CITY);
			
			if(!this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.put(customerSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(customerSchema.tableName()).add(customerSchema.PROVINCE);
			
			if(!this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.put(customerSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(customerSchema.tableName()).add(customerSchema.COUNTRY);
			includeKeyInResults();
			return this;
		}
		
		public BookStoreCustomerQuery excludeCustomerAddressInResult(){
			if(this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.get(customerSchema.tableName()).remove(customerSchema.STREET_NUMBER);
			if(this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.get(customerSchema.tableName()).remove(customerSchema.STREET);
			if(this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.get(customerSchema.tableName()).remove(customerSchema.POSTAL_CODE);
			if(this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.get(customerSchema.tableName()).remove(customerSchema.CITY);
			if(this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.get(customerSchema.tableName()).remove(customerSchema.PROVINCE);
			if(this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.get(customerSchema.tableName()).remove(customerSchema.COUNTRY);
			return this;
		}
		
		
		
		public BookStoreCustomerQuery excludeCustomerCreatedAtEpochInResult(){
			if(this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.get(customerSchema.tableName()).remove(customerSchema.CREATED_AT_EPOCH);
			return this;
		}
		
		public BookStoreCustomerQuery excludeCustomerGivenNameInResult(){
			if(this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.get(customerSchema.tableName()).remove(customerSchema.GIVENNAME);
			return this;
		}
		public BookStoreCustomerQuery excludeCustomerSurNameInResult(){
			if(this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.get(customerSchema.tableName()).remove(customerSchema.SURNAME);
			return this;
		}
		public BookStoreCustomerQuery excludeCustomerUserNameInResult(){
			if(this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.get(customerSchema.tableName()).remove(customerSchema.USERNAME);
			return this;
		}
		public BookStoreCustomerQuery excludeCustomerEmailInResult(){
			if(this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.get(customerSchema.tableName()).remove(customerSchema.EMAIL);
			return this;
		}
		public BookStoreCustomerQuery excludeCustomerPasswordInResult(){
			if(this.attributesToIncludInResults.containsKey(customerSchema.tableName())) this.attributesToIncludInResults.get(customerSchema.tableName()).remove(customerSchema.PASSWORD);
			return this;
		}
		
		private void includeKeyInResults() {
			if(!this.attributesToIncludInResults.containsKey(customerSchema.tableName()) && !this.attributesToIncludInResults.get(customerSchema.tableName()).isEmpty() && !this.attributesToIncludInResults.get(customerSchema.tableName()).contains(customerSchema.ID))
			this.attributesToIncludInResults.get(customerSchema.tableName()).add(customerSchema.ID);


		}
		
		public BookStoreReviewQuery queryReviews() {
//			this.references.put(tableName, new ArrayList<DataAccessString>());
//			this.references.get(tableName).addAll(BookStoreDAO.getReferenceDataAccessString(tableName, new ReviewSchema().tableName()));
			return new ReviewDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData);
		}
		
		public BookStoreCartQuery queryCart() {
//			this.references.put(tableName, new ArrayList<DataAccessString>());
//			this.references.get(tableName).addAll(BookStoreDAO.getReferenceDataAccessString(tableName, new CartSchema().tableName()));
//			this.dataAccessRequests.get(tableName)
//			.add(new DataAccessString.Builder()
//					.withTableName(this.dataSchema.tableName())
//					.withReferenceOperator(this.referenceOperator)
//					.withAttributeName(CartSchema.USER_TYPE)
//					.withDataAccessParameterPrefix("="+"'")
//					.withDataAccessParameterSuffix("'")
//					.withDataAccessParameter(UserTypes.CUSTOMER)
//					.build()
//					);
			this.addDataAccessString(new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(CartSchema.USER_TYPE)
					.withDataAccessParameterPrefix("="+"'")
					.withDataAccessParameterSuffix("'")
					.withDataAccessParameter(UserTypes.CUSTOMER)
					.build()
					);
			return new CartDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData);
		}
		
		public BookStorePurchaseOrderQuery queryPurchaseOrder() {
//			this.references.put(tableName, new ArrayList<DataAccessString>());
//			this.references.get(tableName).addAll(BookStoreDAO.getReferenceDataAccessString(tableName, new PurchaseOrderSchema().tableName()));
			return new PurchaseOrderDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData);
		}
	}
	public class CustomerVarCharQuery extends BookStoreVarCharQuery<CustomerVarCharQuery,CustomerAttributeAccess,BookStoreCustomerQuery>{
		private CustomerAttributeAccess customerAttributeAccess;
		CustomerVarCharQuery(BookStoreCustomerQuery bookStoreCustomerQuery, String currentAttributeAccess){
			super(bookStoreCustomerQuery,new CustomerSchema());
			this.currentAttributeAccess=currentAttributeAccess;
		}
		CustomerVarCharQuery(BookStoreCustomerQuery bookStoreCustomerQuery, String currentAttributeAccess,PageRequestMetaData pageRequestMetaData){
			super(bookStoreCustomerQuery,new CustomerSchema());
			this.currentAttributeAccess=currentAttributeAccess;
			this.pageRequestMetaData=pageRequestMetaData;
		}
		public CustomerAttributeAccess queryCustomerAttribute(){
			return customerAttributeAccess;
		}
		public BookStoreReviewQuery queryReviews() {
//			this.references.put(tableName, new ArrayList<DataAccessString>());
//			this.references.get(tableName).addAll(BookStoreDAO.getReferenceDataAccessString(tableName, new ReviewSchema().tableName()));
			return new ReviewDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData);
		}
		
		public BookStoreCartQuery queryCart() {
//			this.references.put(tableName, new ArrayList<DataAccessString>());
//			this.references.get(tableName).addAll(BookStoreDAO.getReferenceDataAccessString(tableName, new CartSchema().tableName()));
//			this.dataAccessRequests.get(tableName)
//			.add(new DataAccessString.Builder()
//					.withTableName(this.dataSchema.tableName())
//					.withReferenceOperator(this.referenceOperator)
//					.withAttributeName(CartSchema.USER_TYPE)
//					.withDataAccessParameterPrefix("="+"'")
//					.withDataAccessParameterSuffix("'")
//					.withDataAccessParameter(UserTypes.CUSTOMER)
//					.build()
//					);
			this.addDataAccessString(new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(CartSchema.USER_TYPE)
					.withDataAccessParameterPrefix("="+"'")
					.withDataAccessParameterSuffix("'")
					.withDataAccessParameter(UserTypes.CUSTOMER)
					.build()
					);
			return new CartDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData);
		}
		
		public BookStorePurchaseOrderQuery queryPurchaseOrder() {
//			this.references.put(tableName, new ArrayList<DataAccessString>());
//			this.references.get(tableName).addAll(BookStoreDAO.getReferenceDataAccessString(tableName, new PurchaseOrderSchema().tableName()));
			return new PurchaseOrderDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData);
		}

	}
	public class CustomerNumberQuery extends BookStoreNumberQuery<CustomerNumberQuery,CustomerAttributeAccess,BookStoreCustomerQuery>{
		private CustomerAttributeAccess customerAttributeAccess;
		CustomerNumberQuery(BookStoreCustomerQuery bookStoreCustomerQuery, String currentAttributeAccess){
			super(bookStoreCustomerQuery,new CustomerSchema());
			this.currentAttributeAccess=currentAttributeAccess;
		}
		CustomerNumberQuery(BookStoreCustomerQuery bookStoreCustomerQuery, String currentAttributeAccess,PageRequestMetaData pageRequestMetaData){
			super(bookStoreCustomerQuery,new CustomerSchema());
			this.currentAttributeAccess=currentAttributeAccess;
			this.pageRequestMetaData=pageRequestMetaData;
		}
		public CustomerAttributeAccess queryCustomerAttribute(){
			return customerAttributeAccess;
		}		
		
		public BookStoreReviewQuery queryReviews() {
//			this.references.put(tableName, new ArrayList<DataAccessString>());
//			this.references.get(tableName).addAll(BookStoreDAO.getReferenceDataAccessString(tableName, new ReviewSchema().tableName()));
			return new ReviewDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData);
		}
		
		public BookStoreCartQuery queryCart() {
//			this.references.put(tableName, new ArrayList<DataAccessString>());
//			this.references.get(tableName).addAll(BookStoreDAO.getReferenceDataAccessString(tableName, new CartSchema().tableName()));
//			this.dataAccessRequests.get(tableName)
//			.add(new DataAccessString.Builder()
//					.withTableName(this.dataSchema.tableName())
//					.withReferenceOperator(this.referenceOperator)
//					.withAttributeName(CartSchema.USER_TYPE)
//					.withDataAccessParameterPrefix("="+"'")
//					.withDataAccessParameterSuffix("'")
//					.withDataAccessParameter(UserTypes.CUSTOMER)
//					.build()
//					);
			this.addDataAccessString(new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(CartSchema.USER_TYPE)
					.withDataAccessParameterPrefix("="+"'")
					.withDataAccessParameterSuffix("'")
					.withDataAccessParameter(UserTypes.CUSTOMER)
					.build()
					);
			return new CartDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData);
		}
		
		public BookStorePurchaseOrderQuery queryPurchaseOrder() {
//			this.references.put(tableName, new ArrayList<DataAccessString>());
//			this.references.get(tableName).addAll(BookStoreDAO.getReferenceDataAccessString(tableName, new PurchaseOrderSchema().tableName()));
			return new PurchaseOrderDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData);
		}
	}
	
	public class CustomerKeyQuery extends BookStoreQuery<CustomerKeyQuery,CustomerAttributeAccess>{
		private CustomerAttributeAccess customerAttributeAccess;
		CustomerKeyQuery(BookStoreCustomerQuery bookStoreCustomerQuery, String currentAttributeAccess){
			super(bookStoreCustomerQuery,new CustomerSchema());
			this.currentAttributeAccess=currentAttributeAccess;
		}
		CustomerKeyQuery(BookStoreCustomerQuery bookStoreCustomerQuery, String currentAttributeAccess,PageRequestMetaData pageRequestMetaData){
			super(bookStoreCustomerQuery,new CustomerSchema());
			this.currentAttributeAccess=currentAttributeAccess;
			this.pageRequestMetaData=pageRequestMetaData;
		}
		public CustomerAttributeAccess queryCustomerAttribute(){
			return customerAttributeAccess;
		}		
		
		public BookStoreReviewQuery queryReviews() {
//			this.references.put(tableName, new ArrayList<DataAccessString>());
//			this.references.get(tableName).addAll(BookStoreDAO.getReferenceDataAccessString(tableName, new ReviewSchema().tableName()));
			return new ReviewDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData);
		}
		
		public BookStoreCartQuery queryCart() {
//			this.references.put(tableName, new ArrayList<DataAccessString>());
//			this.references.get(tableName).addAll(BookStoreDAO.getReferenceDataAccessString(tableName, new CartSchema().tableName()));
//			this.dataAccessRequests.get(tableName)
//			.add(new DataAccessString.Builder()
//					.withTableName(this.dataSchema.tableName())
//					.withReferenceOperator(this.referenceOperator)
//					.withAttributeName(CartSchema.USER_TYPE)
//					.withDataAccessParameterPrefix("="+"'")
//					.withDataAccessParameterSuffix("'")
//					.withDataAccessParameter(UserTypes.CUSTOMER)
//					.build()
//					);
			this.addDataAccessString(new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(CartSchema.USER_TYPE)
					.withDataAccessParameterPrefix("="+"'")
					.withDataAccessParameterSuffix("'")
					.withDataAccessParameter(UserTypes.CUSTOMER)
					.build()
					);
			return new CartDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData);
		}
		
		public BookStorePurchaseOrderQuery queryPurchaseOrder() {
//			this.references.put(tableName, new ArrayList<DataAccessString>());
//			this.references.get(tableName).addAll(BookStoreDAO.getReferenceDataAccessString(tableName, new PurchaseOrderSchema().tableName()));
			return new PurchaseOrderDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData);
		}
		
		public CustomerKeyQuery isCustomer(Customer customer) {
//			if(!this.attributesToIncludInResults.containsKey(tableName) || this.attributesToIncludInResults.get(tableName)==null) this.dataAccessRequests.put(tableName, new ArrayList<DataAccessString>());
//
//			if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//				this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//			}
//			this.dataAccessRequests.get(this.dataSchema.tableName())
//			.add(new DataAccessString.Builder()
//					.withTableName(this.dataSchema.tableName())
//					.withReferenceOperator(this.referenceOperator)
//					.withAttributeName(customerSchema.ID)
//					.withDataAccessParameterPrefix("="+"'")
//					.withDataAccessParameterSuffix("'")
//					.withDataAccessParameter(customer.getId().toString())
//					.build()
//					);
			this.addDataAccessString(new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(customerSchema.ID)
					.withDataAccessParameterPrefix("="+"'")
					.withDataAccessParameterSuffix("'")
					.withDataAccessParameter(customer.getId().toString())
					.build()
					);
			return  this;
		}
		
	}
	public class CustomerAttributeAccess extends AttributeAccess<BookStoreCustomerQuery>{
		protected BookStoreCustomerQuery bookStoreCustomerQuery;
		protected CustomerAttributeAccess() {
		}
		protected void setBookStoreCustomerQuery(BookStoreCustomerQuery bookStoreCustomerQuery){
			this.bookStoreCustomerQuery=bookStoreCustomerQuery;
		}
		public CustomerKeyQuery whereCustomer(){
			CustomerKeyQuery customerKeyQuery= new CustomerKeyQuery(this.bookStoreCustomerQuery,CustomerSchema.ID);
			customerKeyQuery.setAttribute(this);
			return customerKeyQuery;
		}
		
		public CustomerVarCharQuery whereCustomerGivenName(){
			CustomerVarCharQuery customerVarCharQuery= new CustomerVarCharQuery(this.bookStoreCustomerQuery,CustomerSchema.GIVENNAME);
			customerVarCharQuery.setAttribute(this);
			return customerVarCharQuery;
		}
		public CustomerVarCharQuery whereCustomerSurName(){
			CustomerVarCharQuery customerVarCharQuery= new CustomerVarCharQuery(this.bookStoreCustomerQuery,CustomerSchema.SURNAME);
			customerVarCharQuery.setAttribute(this);
			return customerVarCharQuery;
		}
		public CustomerVarCharQuery whereCustomerUserName(){
			CustomerVarCharQuery customerVarCharQuery= new CustomerVarCharQuery(this.bookStoreCustomerQuery,CustomerSchema.USERNAME);
			customerVarCharQuery.setAttribute(this);
			return customerVarCharQuery;
		}
		public CustomerVarCharQuery whereCustomerEmail(){
			CustomerVarCharQuery customerVarCharQuery= new CustomerVarCharQuery(this.bookStoreCustomerQuery,CustomerSchema.EMAIL);
			customerVarCharQuery.setAttribute(this);
			return customerVarCharQuery;
		}
		public CustomerVarCharQuery whereCustomerPassword(){
			CustomerVarCharQuery customerVarCharQuery= new CustomerVarCharQuery(this.bookStoreCustomerQuery,CustomerSchema.PASSWORD);
			customerVarCharQuery.setAttribute(this);
			return customerVarCharQuery;
		}
		
		public CustomerVarCharQuery whereCustomerStreet(){
			CustomerVarCharQuery customerVarCharQuery= new CustomerVarCharQuery(this.bookStoreCustomerQuery,CustomerSchema.STREET);
			customerVarCharQuery.setAttribute(this);
			return customerVarCharQuery;
		}
		
		public CustomerVarCharQuery whereCustomerStreetNumber(){
			CustomerVarCharQuery customerVarCharQuery= new CustomerVarCharQuery(this.bookStoreCustomerQuery,CustomerSchema.STREET_NUMBER);
			customerVarCharQuery.setAttribute(this);
			return customerVarCharQuery;
		}
		
		
		public CustomerVarCharQuery whereCustomerPostalCode(){
			CustomerVarCharQuery customerVarCharQuery= new CustomerVarCharQuery(this.bookStoreCustomerQuery,CustomerSchema.POSTAL_CODE);
			customerVarCharQuery.setAttribute(this);
			return customerVarCharQuery;
		}
		
		
		public CustomerVarCharQuery whereCustomerCity(){
			CustomerVarCharQuery customerVarCharQuery= new CustomerVarCharQuery(this.bookStoreCustomerQuery,CustomerSchema.CITY);
			customerVarCharQuery.setAttribute(this);
			return customerVarCharQuery;
		}
		
		public CustomerVarCharQuery whereCustomerProvince(){
			CustomerVarCharQuery customerVarCharQuery= new CustomerVarCharQuery(this.bookStoreCustomerQuery,CustomerSchema.PROVINCE);
			customerVarCharQuery.setAttribute(this);
			return customerVarCharQuery;
		}
		
		public CustomerVarCharQuery whereCustomerCountry(){
			CustomerVarCharQuery customerVarCharQuery= new CustomerVarCharQuery(this.bookStoreCustomerQuery,CustomerSchema.COUNTRY);
			customerVarCharQuery.setAttribute(this);
			return customerVarCharQuery;
		}
		
		
		public CustomerNumberQuery whereCustomerCreatedAtEpoch() {
			CustomerNumberQuery customerNumberQuery= new CustomerNumberQuery(this.bookStoreCustomerQuery,CustomerSchema.CREATED_AT_EPOCH);
			customerNumberQuery.setAttribute(this);
			return customerNumberQuery;
		}
	}
}