package data.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import data.beans.Book;
import data.beans.Customer;
import data.beans.PurchaseOrder;
import data.beans.User;
import data.dao.BookDAO.BookStoreBookQuery;
import data.dao.CartDAO.BookStoreCartQuery;
import data.dao.CartDAO.CartAttributeAccess;
import data.dao.CartDAO.CartBookQuery;
import data.dao.CartDAO.CartCustomerQuery;
import data.dao.CartDAO.CartNumberQuery;
import data.dao.CartDAO.CartObjectQuery;
import data.dao.CartDAO.CartUserTypeQuery;
import data.dao.CartDAO.CartVarCharQuery;
import data.dao.CustomerDAO.BookStoreCustomerQuery;
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
import data.schema.UserTypes;

public class PurchaseOrderDAO implements DAO{
	private PurchaseOrderSchema purchaseOrderSchema;
	public PurchaseOrderDAO(){
		this.purchaseOrderSchema=new PurchaseOrderSchema();
	}
	
	@Override
	public UpdatePurchaseOrder newUpdateRequest() {
		return new UpdatePurchaseOrder();
	}
	
	@Override
	public BookStorePurchaseOrderQuery newQueryRequest(){
		BookStorePurchaseOrderQuery bookStorePurchaseOrderQuery= new BookStorePurchaseOrderQuery(purchaseOrderSchema);
		PurchaseOrderAttributeAccess purchaseOrderAttributeAccess= new PurchaseOrderAttributeAccess();
		bookStorePurchaseOrderQuery.setAttribute(purchaseOrderAttributeAccess);
		purchaseOrderAttributeAccess.setBookStorePurchaseOrderQuery(bookStorePurchaseOrderQuery);
		return bookStorePurchaseOrderQuery;
	}
	public class BookStorePurchaseOrderQuery extends BookStoreQuery<BookStorePurchaseOrderQuery,PurchaseOrderAttributeAccess>{
		public BookStorePurchaseOrderQuery(BookStorePurchaseOrderQuery query, DataSchema dataSchema){
			super(query,  dataSchema);
		}
		public BookStorePurchaseOrderQuery(DataSchema dataSchema){
			super(dataSchema);
		}
		public BookStorePurchaseOrderQuery includePurchaseOrderStatusInResult(){
			if(!this.attributesToIncludInResults.containsKey(purchaseOrderSchema.tableName())) this.attributesToIncludInResults.put(purchaseOrderSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(purchaseOrderSchema.tableName()).add(purchaseOrderSchema.STATUS);
			includeKeyInResults();
			return this;
		}
		
		public BookStorePurchaseOrderQuery includePurchaseOrderAmountInResult(){
			if(!this.attributesToIncludInResults.containsKey(purchaseOrderSchema.tableName())) this.attributesToIncludInResults.put(purchaseOrderSchema.tableName(), new HashSet<String>());
			this.attributesToIncludInResults.get(purchaseOrderSchema.tableName()).add(purchaseOrderSchema.AMOUNT);
			includeKeyInResults();
			return this;
		}
		public BookStorePurchaseOrderQuery includeCustomerInResults(){
			if(!this.attributesToIncludInResults.containsKey(purchaseOrderSchema.tableName())) this.attributesToIncludInResults.put(purchaseOrderSchema.tableName(), new HashSet<String>());
			includeKeyInResults();
			if(!this.attributesToIncludInResults.containsKey(new CustomerSchema().tableName())) this.attributesToIncludInResults.put(new CustomerSchema().tableName(), new HashSet<String>());	
			return this;
		}
		
		public BookStorePurchaseOrderQuery includeBookInResults(){
			if(!this.attributesToIncludInResults.containsKey(purchaseOrderSchema.tableName())) this.attributesToIncludInResults.put(purchaseOrderSchema.tableName(), new HashSet<String>());
			includeKeyInResults();
			if(!this.attributesToIncludInResults.containsKey(new BookSchema().tableName())) this.attributesToIncludInResults.put(new BookSchema().tableName(), new HashSet<String>());	
			return this;
		}
		
//		public BookStorePurchaseOrderQuery includePurchaseOrderCustomerInResult(){
//			if(!this.attributesToIncludInResults.containsKey(purchaseOrderSchema.tableName())) this.attributesToIncludInResults.put(purchaseOrderSchema.tableName(), new HashSet<String>());
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
//		public BookStorePurchaseOrderQuery includePurchaseOrderBooksInResult(){
//			if(!this.attributesToIncludInResults.containsKey(purchaseOrderSchema.tableName())) this.attributesToIncludInResults.put(purchaseOrderSchema.tableName(), new HashSet<String>());
//			if(!this.attributesToIncludInResults.containsKey(new BookSchema().tableName())) this.attributesToIncludInResults.put(new BookSchema().tableName(), new HashSet<String>());
//			if (!isDisjunctionMode) {
//				if(!this.dataAccessRequestsConjunction.containsKey(new BookSchema().tableName())) this.dataAccessRequestsConjunction.put(new BookSchema().tableName(),new ArrayList<DataAccessString>());		
//			}else {
//				if(!this.dataAccessRequestsDisjunction.containsKey(new BookSchema().tableName())) this.dataAccessRequestsDisjunction.put(new BookSchema().tableName(), new ArrayList<DataAccessString>());
//			}
//			includeKeyInResults();
//			return this;
//		}

		public BookStorePurchaseOrderQuery excludePurchaseOrderAmountInResult(){
			if(this.attributesToIncludInResults.containsKey(purchaseOrderSchema.tableName())) this.attributesToIncludInResults.get(purchaseOrderSchema.tableName()).remove(purchaseOrderSchema.AMOUNT);
			return this;
		}
		
		public BookStorePurchaseOrderQuery excludePurchaseOrderStatusInResult(){
			if(this.attributesToIncludInResults.containsKey(purchaseOrderSchema.tableName())) this.attributesToIncludInResults.get(purchaseOrderSchema.tableName()).remove(purchaseOrderSchema.STATUS);
			return this;
		}

		public BookStoreBookQuery queryBook() {
			this.tableJoins.add(
					new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(PurchaseOrderSchema.BOOK)
					.withDataAccessParameterPrefix("="+"")
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
					.withAttributeName(PurchaseOrderSchema.ID)
					.withDataAccessParameterPrefix("="+"")
					.withDataAccessParameterSuffix("")
					.withDataAccessParameter(new CustomerSchema().tableName()+this.referenceOperator+CustomerSchema.ID)
					.build()
					);
			return new CustomerDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData).addTableJoins(tableJoins);
		}
		
		private void includeKeyInResults() {
			if(!this.attributesToIncludInResults.containsKey(purchaseOrderSchema.tableName()) && !this.attributesToIncludInResults.get(purchaseOrderSchema.tableName()).isEmpty() && !this.attributesToIncludInResults.get(purchaseOrderSchema.tableName()).contains(purchaseOrderSchema.ID))
			this.attributesToIncludInResults.get(purchaseOrderSchema.tableName()).add(purchaseOrderSchema.ID);
			
			if(!this.attributesToIncludInResults.containsKey(purchaseOrderSchema.tableName()) && !this.attributesToIncludInResults.get(purchaseOrderSchema.tableName()).isEmpty() && !this.attributesToIncludInResults.get(purchaseOrderSchema.tableName()).contains(purchaseOrderSchema.CREATED_AT_EPOCH))
			this.attributesToIncludInResults.get(purchaseOrderSchema.tableName()).add(purchaseOrderSchema.CREATED_AT_EPOCH);
			
			
			if(!this.attributesToIncludInResults.containsKey(purchaseOrderSchema.tableName()) && !this.attributesToIncludInResults.get(purchaseOrderSchema.tableName()).isEmpty() && !this.attributesToIncludInResults.get(purchaseOrderSchema.tableName()).contains(purchaseOrderSchema.CREATED_AT_EPOCH))
			this.attributesToIncludInResults.get(purchaseOrderSchema.tableName()).add(purchaseOrderSchema.BOOK);


		}

		
		
	}
	public class PurchaseOrderVarCharQuery extends BookStoreVarCharQuery<PurchaseOrderVarCharQuery,PurchaseOrderAttributeAccess,BookStorePurchaseOrderQuery>{
		private PurchaseOrderAttributeAccess purchaseOrderAttributeAccess;
		PurchaseOrderVarCharQuery(BookStorePurchaseOrderQuery bookStorePurchaseOrderQuery, String currentAttributeAccess){
			super(bookStorePurchaseOrderQuery,new PurchaseOrderSchema());
			this.currentAttributeAccess=currentAttributeAccess;
		}
		PurchaseOrderVarCharQuery(BookStorePurchaseOrderQuery bookStorePurchaseOrderQuery, String currentAttributeAccess,PageRequestMetaData pageRequestMetaData){
			super(bookStorePurchaseOrderQuery,new PurchaseOrderSchema());
			this.currentAttributeAccess=currentAttributeAccess;
			this.pageRequestMetaData=pageRequestMetaData;
		}
		public PurchaseOrderAttributeAccess queryPurchaseOrderAttribute(){
			return purchaseOrderAttributeAccess;
		}
		public BookStoreBookQuery queryBook() {
//			this.references.put(tableName, new ArrayList<DataAccessString>());
//			this.references.get(tableName).addAll(BookStoreDAO.getReferenceDataAccessString(tableName, new BookSchema().tableName()));
			return new BookDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData);		
		}
		public BookStoreCustomerQuery queryCustomer() {
//			this.references.put(tableName, new ArrayList<DataAccessString>());
//			this.references.get(tableName).addAll(BookStoreDAO.getReferenceDataAccessString(tableName, new CustomerSchema().tableName()));
			return new CustomerDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData);
		}


	}
	public class PurchaseOrderNumberQuery extends BookStoreNumberQuery<PurchaseOrderNumberQuery,PurchaseOrderAttributeAccess,BookStorePurchaseOrderQuery>{
		private PurchaseOrderAttributeAccess purchaseOrderAttributeAccess;
		PurchaseOrderNumberQuery(BookStorePurchaseOrderQuery bookStorePurchaseOrderQuery, String currentAttributeAccess){
			super(bookStorePurchaseOrderQuery,new PurchaseOrderSchema());
			this.currentAttributeAccess=currentAttributeAccess;
		}
		PurchaseOrderNumberQuery(BookStorePurchaseOrderQuery bookStorePurchaseOrderQuery, String currentAttributeAccess,PageRequestMetaData pageRequestMetaData){
			super(bookStorePurchaseOrderQuery,new PurchaseOrderSchema());
			this.currentAttributeAccess=currentAttributeAccess;
			this.pageRequestMetaData=pageRequestMetaData;
		}
		public PurchaseOrderAttributeAccess queryPurchaseOrderAttribute(){
			return purchaseOrderAttributeAccess;
		}
		public BookStoreBookQuery queryBook() {
			this.tableJoins.add(
					new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(PurchaseOrderSchema.BOOK)
					.withDataAccessParameterPrefix("="+"")
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
					.withAttributeName(PurchaseOrderSchema.ID)
					.withDataAccessParameterPrefix("="+"")
					.withDataAccessParameterSuffix("")
					.withDataAccessParameter(new CustomerSchema().tableName()+this.referenceOperator+CustomerSchema.ID)
					.build()
					);
			return new CustomerDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData).addTableJoins(tableJoins);
		}
	}
	
	public abstract class PurchaseOrderObjectQuery<T extends PurchaseOrderObjectQuery> extends BookStoreQuery<T,PurchaseOrderAttributeAccess>{
		protected PurchaseOrderAttributeAccess purchaseOrderAttributeAccess;

		PurchaseOrderObjectQuery(BookStorePurchaseOrderQuery bookStorePurchaseOrderQuery, String currentAttributeAccess){
			super(bookStorePurchaseOrderQuery,new PurchaseOrderSchema());
			this.currentAttributeAccess=currentAttributeAccess;
		}
		PurchaseOrderObjectQuery(BookStorePurchaseOrderQuery bookStorePurchaseOrderQuery, String currentAttributeAccess,PageRequestMetaData pageRequestMetaData){
			super(bookStorePurchaseOrderQuery,new PurchaseOrderSchema());
			this.currentAttributeAccess=currentAttributeAccess;
			this.pageRequestMetaData=pageRequestMetaData;
		}
		public PurchaseOrderAttributeAccess queryPurchaseOrderAttribute(){
			return purchaseOrderAttributeAccess;
		}
		public BookStoreBookQuery queryBook() {
			this.tableJoins.add(
					new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(PurchaseOrderSchema.BOOK)
					.withDataAccessParameterPrefix("="+"")
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
					.withAttributeName(PurchaseOrderSchema.ID)
					.withDataAccessParameterPrefix("="+"")
					.withDataAccessParameterSuffix("")
					.withDataAccessParameter(new CustomerSchema().tableName()+this.referenceOperator+CustomerSchema.ID)
					.build()
					);
			return new CustomerDAO().newQueryRequest().setAttributesToIncludInResults(attributesToIncludInResults).setDataAccessRequestsConjunction(this.dataAccessRequestsConjunction).setDataAccessRequestsDisjunction(this.dataAccessRequestsDisjunction).setPageRequestMetaData(pageRequestMetaData).addTableJoins(tableJoins);
		}
	}
	
	
	public class PurchaseOrderUserQuery extends PurchaseOrderObjectQuery<PurchaseOrderUserQuery>{
		PurchaseOrderUserQuery(BookStorePurchaseOrderQuery bookStorePurchaseOrderQuery, String currentAttributeAccess) {
			super(bookStorePurchaseOrderQuery, currentAttributeAccess);
			// TODO Auto-generated constructor stub
		}
		PurchaseOrderUserQuery(BookStorePurchaseOrderQuery bookStorePurchaseOrderQuery, String currentAttributeAccess,
				PageRequestMetaData pageRequestMetaData) {
			super(bookStorePurchaseOrderQuery, currentAttributeAccess, pageRequestMetaData);
			// TODO Auto-generated constructor stub
		}

		public PurchaseOrderUserQuery isCustomer(Customer customer) {
//			if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//				this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//			}
//			this.dataAccessRequests.get(this.dataSchema.tableName())
//			.add(new DataAccessString.Builder()
//					.withTableName(this.dataSchema.tableName())
//					.withReferenceOperator(this.referenceOperator)
//					.withAttributeName(PurchaseOrderSchema.ID)
//					.withDataAccessParameterPrefix("="+"'")
//					.withDataAccessParameterSuffix("'")
//					.withDataAccessParameter(customer.getId().toString())
//					.build()
//					);
			this.addDataAccessString(new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(PurchaseOrderSchema.ID)
					.withDataAccessParameterPrefix("="+"'")
					.withDataAccessParameterSuffix("'")
					.withDataAccessParameter(customer.getId().toString())
					.build()
					);
			return  this;
		}
	}
	
	public class PurchaseOrderBookQuery extends PurchaseOrderObjectQuery<PurchaseOrderBookQuery>{
		PurchaseOrderBookQuery(BookStorePurchaseOrderQuery bookStorePurchaseOrderQuery, String currentAttributeAccess) {
			super(bookStorePurchaseOrderQuery, currentAttributeAccess);
			// TODO Auto-generated constructor stub
		}
		PurchaseOrderBookQuery(BookStorePurchaseOrderQuery bookStorePurchaseOrderQuery, String currentAttributeAccess,
				PageRequestMetaData pageRequestMetaData) {
			super(bookStorePurchaseOrderQuery, currentAttributeAccess, pageRequestMetaData);
			// TODO Auto-generated constructor stub
		}

		public PurchaseOrderBookQuery isBook(Book book) {
//			if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//				this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//			}
//			this.dataAccessRequests.get(this.dataSchema.tableName())
//			.add(new DataAccessString.Builder()
//					.withTableName(this.dataSchema.tableName())
//					.withReferenceOperator(this.referenceOperator)
//					.withAttributeName(PurchaseOrderSchema.ID)
//					.withDataAccessParameterPrefix("="+"'")
//					.withDataAccessParameterSuffix("'")
//					.withDataAccessParameter(book.getId().toString())
//					.build()
//					);
			this.addDataAccessString(new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(PurchaseOrderSchema.ID)
					.withDataAccessParameterPrefix("="+"'")
					.withDataAccessParameterSuffix("'")
					.withDataAccessParameter(book.getId().toString())
					.build()
					);
			return  this;
		}

	}
	
	public class PurchaseOrderStatusQuery extends PurchaseOrderObjectQuery<PurchaseOrderStatusQuery>{
		PurchaseOrderStatusQuery(BookStorePurchaseOrderQuery bookStorePurchaseOrderQuery, String currentAttributeAccess) {
			super(bookStorePurchaseOrderQuery, currentAttributeAccess);
			// TODO Auto-generated constructor stub
		}
		PurchaseOrderStatusQuery(BookStorePurchaseOrderQuery bookStorePurchaseOrderQuery, String currentAttributeAccess,
				PageRequestMetaData pageRequestMetaData) {
			super(bookStorePurchaseOrderQuery, currentAttributeAccess, pageRequestMetaData);
			// TODO Auto-generated constructor stub
		}
//		'PROCESSED','SHIPPED','DENIED','DELIVERED','ORDERED'
		public PurchaseOrderStatusQuery isProcessed() {
//			if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//				this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//			}
//			this.dataAccessRequests.get(this.dataSchema.tableName())
//			.add(new DataAccessString.Builder()
//					.withTableName(this.dataSchema.tableName())
//					.withReferenceOperator(this.referenceOperator)
//					.withAttributeName(PurchaseOrderSchema.STATUS)
//					.withDataAccessParameterPrefix("="+"'")
//					.withDataAccessParameterSuffix("'")
//					.withDataAccessParameter(PurchaseOrderSchema.PROCESSED_STATUS)
//					.build()
//					);
			
			this.addDataAccessString(new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(PurchaseOrderSchema.STATUS)
					.withDataAccessParameterPrefix("="+"'")
					.withDataAccessParameterSuffix("'")
					.withDataAccessParameter(PurchaseOrderSchema.PROCESSED_STATUS)
					.build()
					);
			return this;
		}
		
		public PurchaseOrderStatusQuery isOrdered() {
//			if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//				this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//			}
//			this.dataAccessRequests.get(this.dataSchema.tableName())
//			.add(new DataAccessString.Builder()
//					.withTableName(this.dataSchema.tableName())
//					.withReferenceOperator(this.referenceOperator)
//					.withAttributeName(PurchaseOrderSchema.STATUS)
//					.withDataAccessParameterPrefix("="+"'")
//					.withDataAccessParameterSuffix("'")
//					.withDataAccessParameter(PurchaseOrderSchema.ORDERED_STATUS)
//					.build()
//					);
			this.addDataAccessString(new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(PurchaseOrderSchema.STATUS)
					.withDataAccessParameterPrefix("="+"'")
					.withDataAccessParameterSuffix("'")
					.withDataAccessParameter(PurchaseOrderSchema.ORDERED_STATUS)
					.build()
					);
			return this;
		}
		public PurchaseOrderStatusQuery isShipped() {
//			if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//				this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//			}
//			this.dataAccessRequests.get(this.dataSchema.tableName())
//			.add(new DataAccessString.Builder()
//					.withTableName(this.dataSchema.tableName())
//					.withReferenceOperator(this.referenceOperator)
//					.withAttributeName(PurchaseOrderSchema.STATUS)
//					.withDataAccessParameterPrefix("="+"'")
//					.withDataAccessParameterSuffix("'")
//					.withDataAccessParameter(PurchaseOrderSchema.SHIPPED_STATUS)
//					.build()
//					);
			this.addDataAccessString(new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(PurchaseOrderSchema.STATUS)
					.withDataAccessParameterPrefix("="+"'")
					.withDataAccessParameterSuffix("'")
					.withDataAccessParameter(PurchaseOrderSchema.SHIPPED_STATUS)
					.build()
					);
			return this;
		}
		public PurchaseOrderStatusQuery isDelivered() {
//			if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//				this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//			}
//			this.dataAccessRequests.get(this.dataSchema.tableName())
//			.add(new DataAccessString.Builder()
//					.withTableName(this.dataSchema.tableName())
//					.withReferenceOperator(this.referenceOperator)
//					.withAttributeName(PurchaseOrderSchema.STATUS)
//					.withDataAccessParameterPrefix("="+"'")
//					.withDataAccessParameterSuffix("'")
//					.withDataAccessParameter(PurchaseOrderSchema.DELIVERED_STATUS)
//					.build()
//					);
			this.addDataAccessString(new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(PurchaseOrderSchema.STATUS)
					.withDataAccessParameterPrefix("="+"'")
					.withDataAccessParameterSuffix("'")
					.withDataAccessParameter(PurchaseOrderSchema.DELIVERED_STATUS)
					.build()
					);
			return this;
		}
		public PurchaseOrderStatusQuery isDenied() {
//			if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//				this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//			}
//			this.dataAccessRequests.get(this.dataSchema.tableName())
//			.add(new DataAccessString.Builder()
//					.withTableName(this.dataSchema.tableName())
//					.withReferenceOperator(this.referenceOperator)
//					.withAttributeName(PurchaseOrderSchema.STATUS)
//					.withDataAccessParameterPrefix("="+"'")
//					.withDataAccessParameterSuffix("'")
//					.withDataAccessParameter(PurchaseOrderSchema.DENIED_STATUS)
//					.build()
//					);
			this.addDataAccessString(new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(PurchaseOrderSchema.STATUS)
					.withDataAccessParameterPrefix("="+"'")
					.withDataAccessParameterSuffix("'")
					.withDataAccessParameter(PurchaseOrderSchema.DENIED_STATUS)
					.build()
					);
			return this;
		}

	}
	
	public class PurchaseOrderKeyQuery extends PurchaseOrderObjectQuery<PurchaseOrderKeyQuery>{
		PurchaseOrderKeyQuery(BookStorePurchaseOrderQuery bookStorePurchaseOrderQuery, String currentAttributeAccess) {
			super(bookStorePurchaseOrderQuery, currentAttributeAccess);
			// TODO Auto-generated constructor stub
		}
		PurchaseOrderKeyQuery(BookStorePurchaseOrderQuery bookStorePurchaseOrderQuery, String currentAttributeAccess,
				PageRequestMetaData pageRequestMetaData) {
			super(bookStorePurchaseOrderQuery, currentAttributeAccess, pageRequestMetaData);
			// TODO Auto-generated constructor stub
		}

		public PurchaseOrderKeyQuery isPurchaseOrder(PurchaseOrder purchaseOrder) {
//			if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//				this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//			}
//			this.dataAccessRequests.get(this.dataSchema.tableName())
//			.add(new DataAccessString.Builder()
//					.withTableName(this.dataSchema.tableName())
//					.withReferenceOperator(this.referenceOperator)
//					.withAttributeName(purchaseOrderSchema.ID)
//					.withDataAccessParameterPrefix("="+"'")
//					.withDataAccessParameterSuffix("'")
//					.withDataAccessParameter(purchaseOrder.getCustomer().getId().toString())
//					.build()
//					);
			this.addDataAccessString(new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(purchaseOrderSchema.ID)
					.withDataAccessParameterPrefix("="+"'")
					.withDataAccessParameterSuffix("'")
					.withDataAccessParameter(purchaseOrder.getCustomer().getId().toString())
					.build()
					);
//			this.dataAccessRequests.get(this.dataSchema.tableName())
//			.add(new DataAccessString.Builder()
//					.withTableName(this.dataSchema.tableName())
//					.withReferenceOperator(this.referenceOperator)
//					.withAttributeName(purchaseOrderSchema.CREATED_AT_EPOCH)
//					.withDataAccessParameterPrefix("=")
//					.withDataAccessParameterSuffix("")
//					.withDataAccessParameter(Long.toString(purchaseOrder.getCreatedAtEpoch()))
//					.build()
//					);
			this.addDataAccessString(new DataAccessString.Builder()
					.withTableName(this.dataSchema.tableName())
					.withReferenceOperator(this.referenceOperator)
					.withAttributeName(purchaseOrderSchema.CREATED_AT_EPOCH)
					.withDataAccessParameterPrefix("=")
					.withDataAccessParameterSuffix("")
					.withDataAccessParameter(Long.toString(purchaseOrder.getCreatedAtEpoch()))
					.build()
					);
			return  this;
		}

	}
	public class PurchaseOrderAttributeAccess extends AttributeAccess<BookStorePurchaseOrderQuery>{
		protected BookStorePurchaseOrderQuery bookStorePurchaseOrderQuery;
		protected PurchaseOrderAttributeAccess() {
		}
		protected void setBookStorePurchaseOrderQuery(BookStorePurchaseOrderQuery bookStorePurchaseOrderQuery){
			this.bookStorePurchaseOrderQuery=bookStorePurchaseOrderQuery;
		}
		public PurchaseOrderKeyQuery wherePurchaseOrder(){
			PurchaseOrderKeyQuery purchaseOrderKeyQuery= new PurchaseOrderKeyQuery(this.bookStorePurchaseOrderQuery,PurchaseOrderSchema.ID);
			purchaseOrderKeyQuery.setAttribute(this);
			return purchaseOrderKeyQuery;
		}
		
		public PurchaseOrderStatusQuery wherePurchaseOrderStatus(){
			PurchaseOrderStatusQuery purchaseOrderStatusQuery= new PurchaseOrderStatusQuery(this.bookStorePurchaseOrderQuery,PurchaseOrderSchema.STATUS);
			purchaseOrderStatusQuery.setAttribute(this);
			return purchaseOrderStatusQuery;
		}
		
		
		public PurchaseOrderNumberQuery wherePurchaseOrderAmount(){
			PurchaseOrderNumberQuery purchaseOrderNumberQuery= new PurchaseOrderNumberQuery(this.bookStorePurchaseOrderQuery,PurchaseOrderSchema.AMOUNT);
			purchaseOrderNumberQuery.setAttribute(this);
			return purchaseOrderNumberQuery;
		}
		
		public PurchaseOrderNumberQuery wherePurchaseOrderCreatedAtEpoch(){
			PurchaseOrderNumberQuery purchaseOrderNumberQuery= new PurchaseOrderNumberQuery(this.bookStorePurchaseOrderQuery,PurchaseOrderSchema.CREATED_AT_EPOCH);
			purchaseOrderNumberQuery.setAttribute(this);
			return purchaseOrderNumberQuery;
		}
		
		
		public PurchaseOrderBookQuery wherePurchaseOrderBook(){
			PurchaseOrderBookQuery purchaseOrderBookQuery= new PurchaseOrderBookQuery(this.bookStorePurchaseOrderQuery,PurchaseOrderSchema.BOOK);
			purchaseOrderBookQuery.setAttribute(this);
			return purchaseOrderBookQuery;
		}
		
		public PurchaseOrderUserQuery wherePurchaseOrderUser(){
			PurchaseOrderUserQuery purchaseOrderUserQuery= new PurchaseOrderUserQuery(this.bookStorePurchaseOrderQuery,PurchaseOrderSchema.ID);
			purchaseOrderUserQuery.setAttribute(this);
			return purchaseOrderUserQuery;
		}
	}
}