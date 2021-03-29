package data.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import data.beans.Bean;
import data.beans.Book;
import data.beans.Cart;
import data.beans.Customer;
import data.beans.Id;
import data.beans.PurchaseOrder;
import data.beans.Review;
import data.beans.Visitor;
import data.schema.BookSchema;
import data.schema.CartSchema;
import data.schema.CustomerSchema;
import data.schema.DataSchema;
import data.schema.PurchaseOrderSchema;
import data.schema.ReviewSchema;
import data.schema.VisitorSchema;

import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * Flexible mechanism to submit queries. Query can be set up with several properties to specify
 * what type of queries are allowed for each attribute. Only the allowed query parameters can be submitted for any given attribute
 * Queries can easily be built on demand to allow for modular and flexible requests.
*/
public abstract class Query<T extends Query,U extends AttributeAccess> implements Queryable<T>{
	
	protected Query query;
	protected String pageNumber;
	protected String ascOrderOfAttribute;/*order of query results ascending*/
	protected String ascOrderOfTable;
	protected String descOrderOfAttribute;/*order of query results descending*/
	protected String descOrderOfTable;/*order of query results descending*/
//	protected Map<String,List<DataAccessString>> references;
	protected String resultLimit;
	protected String tableName;
	protected DataSchema dataSchema;
	public final static String referenceOperator=".";
	public final static String referenceSeparator="_";
	protected U attributeAccess;
	protected String currentAttributeAccess;
	protected PageRequestMetaData pageRequestMetaData;
	protected Map<String,Set<String>> attributesToIncludInResults; 	/*Attributes that will be received after a query*/
//	protected Map<String,List<DataAccessString>> dataAccessRequests;
	protected Map<String,List<DataAccessString>> dataAccessRequestsConjunction;
	protected Map<String,List<DataAccessString>> dataAccessRequestsDisjunction;
	protected boolean isDisjunctionMode;
	protected List<DataAccessString> tableJoins;
	
	public T queryAsDisjunction() {
		isDisjunctionMode=true;
		return (T) this;
	}
	
	public T queryAsConjunction() {
		isDisjunctionMode=false;
		return (T) this;
	}
	
	public T setDisjunctionMode(boolean disjunctionMode) {
		this.isDisjunctionMode=disjunctionMode;
		return (T) this; 
	}
	
	public PageRequestMetaData getPageData() {
		return pageRequestMetaData;
	}
	
	protected T addDataAccessString(DataAccessString dataAccessString) {
		if(isDisjunctionMode) {
			if(!this.dataAccessRequestsDisjunction.containsKey(tableName) || this.dataAccessRequestsDisjunction.get(tableName)==null)
			 this.dataAccessRequestsDisjunction.put(tableName, new ArrayList<DataAccessString>());
			this.dataAccessRequestsDisjunction.get(tableName).add(dataAccessString);
		}else {
			if(!this.dataAccessRequestsConjunction.containsKey(tableName) || this.dataAccessRequestsConjunction.get(tableName)==null)
				 this.dataAccessRequestsConjunction.put(tableName, new ArrayList<DataAccessString>());
				this.dataAccessRequestsConjunction.get(tableName).add(dataAccessString);
		}
		return (T) this;
	}
	
//	public void setCurrentAttributeAccess(String attribute) {
//		this.currentAttributeAccess=attribute;
//	}
	protected Query(DataSchema dataSchema) {
		this.isDisjunctionMode=false;
		this.tableJoins=new LinkedList<DataAccessString>();
		this.dataAccessRequestsConjunction=new HashMap<String, List<DataAccessString>>();
		this.dataAccessRequestsDisjunction= new HashMap<String, List<DataAccessString>>();
		this.pageRequestMetaData=new PageRequestMetaData();
		this.pageNumber="";
		this.attributesToIncludInResults=new HashMap<String, Set<String>>();	/*Attributes that will be received after a query*/
		this.ascOrderOfAttribute="";/*order of query results ascending*/
		this.ascOrderOfTable="";
		this.descOrderOfAttribute="";/*order of query results descending*/
		this.descOrderOfTable="";/*order of query results descending*/
//		this.references=new HashMap<String, List<DataAccessString>>();
		this.resultLimit="20";
//		this.dataAccessRequests=new HashMap<String, List<DataAccessString>>();
		this.dataSchema=dataSchema;		
		this.attributeAccess=attributeAccess;
		this.currentAttributeAccess="";
		this.tableName=dataSchema.tableName();
		this.attributesToIncludInResults.put(tableName, new HashSet<String>());
		if(isDisjunctionMode) {
			if(!this.dataAccessRequestsDisjunction.containsKey(tableName) || this.dataAccessRequestsDisjunction.get(tableName)==null) this.dataAccessRequestsDisjunction.put(tableName, new ArrayList<DataAccessString>());

		}else {
			if(!this.dataAccessRequestsConjunction.containsKey(tableName) || this.dataAccessRequestsConjunction.get(tableName)==null) this.dataAccessRequestsConjunction.put(tableName, new ArrayList<DataAccessString>());

		}
//		this.dataAccessRequests.put(tableName, new ArrayList<DataAccessString>());
//		this.references.put(tableName, new ArrayList<DataAccessString>());
	}
	
	protected Query(Query query,DataSchema dataSchema) {
		this.pageRequestMetaData=query.pageRequestMetaData;
		this.tableJoins=query.tableJoins;
		this.pageNumber=query.pageNumber;
		this.attributesToIncludInResults=query.attributesToIncludInResults;	/*Attributes that will be received after a query*/
		this.ascOrderOfAttribute=query.ascOrderOfAttribute;/*order of query results ascending*/
		this.ascOrderOfTable=query.ascOrderOfTable;
		this.descOrderOfAttribute=query.descOrderOfAttribute;/*order of query results descending*/
		this.descOrderOfTable=query.descOrderOfTable;/*order of query results descending*/
//		this.references=query.references;
		this.resultLimit=query.resultLimit;
//		this.dataAccessRequests=query.dataAccessRequests;
		this.dataSchema=dataSchema;	
		this.attributeAccess=attributeAccess;
		this.currentAttributeAccess=query.currentAttributeAccess;
//		this.references=query.references;
		this.dataAccessRequestsConjunction=query.dataAccessRequestsConjunction;
		this.dataAccessRequestsDisjunction= query.dataAccessRequestsDisjunction;
		this.isDisjunctionMode=query.isDisjunctionMode;
		this.tableName=dataSchema.tableName();
		if(!this.attributesToIncludInResults.containsKey(tableName) || this.attributesToIncludInResults.get(tableName)==null)this.attributesToIncludInResults.put(tableName, new HashSet<String>());
		if(isDisjunctionMode) {
			if(!this.dataAccessRequestsDisjunction.containsKey(tableName) || this.dataAccessRequestsDisjunction.get(tableName)==null) this.dataAccessRequestsDisjunction.put(tableName, new ArrayList<DataAccessString>());

		}else {
			if(!this.dataAccessRequestsConjunction.containsKey(tableName) || this.dataAccessRequestsConjunction.get(tableName)==null) this.dataAccessRequestsConjunction.put(tableName, new ArrayList<DataAccessString>());

		}
//		if(!this.dataAccessRequests.containsKey(tableName) || this.dataAccessRequests.get(tableName)==null) this.dataAccessRequests.put(tableName, new ArrayList<DataAccessString>());
//		if(!this.dataAccessRequests.containsKey(tableName) || this.dataAccessRequests.get(tableName)==null) this.dataAccessRequests.put(tableName, new ArrayList<DataAccessString>());
//		if(!this.references.containsKey(tableName) || this.references.get(tableName)==null) this.references.put(tableName, new ArrayList<DataAccessString>());
	}
	
	public U queryAttribute() {
		return (U) this.attributeAccess;
	}
	
	public void setAttribute(U attributeAccess) {
		this.attributeAccess=attributeAccess;
	}
	




	@Override
	public String getQueryString() {
//		System.out.println("CONJ AND DISJ");
//		this.dataAccessRequestsConjunction.entrySet().stream().flatMap(entry->entry.getValue().stream()).map(das->das.getReferenceDataAcessString()).forEach(System.out::println);
//		this.dataAccessRequestsDisjunction.entrySet().stream().map(entry->entry.getKey()).forEach(System.out::println);
//		System.out.println("CONJ AND DISJ END");
		BookSchema bookSchema = new BookSchema();
		CartSchema cartSchema = new CartSchema();
		CustomerSchema customerSchema = new CustomerSchema();
		PurchaseOrderSchema purchaseOrderSchema = new  PurchaseOrderSchema();
		ReviewSchema reviewSchema= new ReviewSchema();
		VisitorSchema visitorSchema=new VisitorSchema();
		Map<String,List<String>> tableAttributes = new LinkedHashMap<String, List<String>>();
		tableAttributes.put(bookSchema.tableName(), bookSchema.getAttributeLabels());
		tableAttributes.put(cartSchema.tableName(), cartSchema.getAttributeLabels());
		tableAttributes.put(customerSchema.tableName(), customerSchema.getAttributeLabels());
		tableAttributes.put(purchaseOrderSchema.tableName(), purchaseOrderSchema.getAttributeLabels());
		tableAttributes.put(reviewSchema.tableName(), reviewSchema.getAttributeLabels());
		tableAttributes.put(visitorSchema.tableName(), visitorSchema.getAttributeLabels());
		boolean hasReferences=this.dataAccessRequestsConjunction.keySet().size()>1 ||this.dataAccessRequestsDisjunction.keySet().size()>1;
		this.attributesToIncludInResults.keySet().forEach(System.out::println);
		String queryString="SELECT ";
		if(this.attributesToIncludInResults.isEmpty() || (this.attributesToIncludInResults.keySet().size()==1 && this.attributesToIncludInResults.get(tableName).isEmpty())) {
			queryString+=" *";
		}else {	

				
			int count =0;
			for(Entry<String,Set<String>> entry: this.attributesToIncludInResults.entrySet()) {
				count++;
				String tableName=entry.getKey();
				List<String> attributeNames=new ArrayList<String>(entry.getValue());
				if(attributeNames.isEmpty()) {
					attributeNames=tableAttributes.get(entry.getKey());
				}

				for(String attribute:attributeNames) {
					queryString+= hasReferences?tableName+this.referenceOperator+attribute+" AS "+tableName+this.referenceSeparator+attribute:attribute;
					queryString+=",";
				}


			}
			queryString=queryString.substring(0, queryString.length()-1);
		}
		String andQuery=" AND ";
		if(hasReferences) {
			
			
			queryString+=" FROM ";
			Set<String> references = new HashSet<String>();
			references.addAll(this.dataAccessRequestsConjunction.keySet());
			references.addAll(this.dataAccessRequestsDisjunction.keySet());
			for(String tablesReference:references) {
				queryString+=  tablesReference+",";
			}
			queryString=queryString.substring(0, queryString.length()-1);
			queryString+= " WHERE ";
//			for(Entry<String, List<DataAccessString>> entry:this.dataAccessRequests.entrySet()) {
				int count=0;
//				for(String reference:this.dataAccessRequests.keySet()) {
//					count++;
//					System.out.println("refererefre: "+reference);
////					queryString+=dataAccessString.getReferenceDataAcessString();	
//					queryString+=andQuery;
//				}
//				Map<String,List<DataAccessString>> tableReferences = new HashMap<String, List<DataAccessString>>();
//				List<String> referenceQueries= new ArrayList<String>(references);
//				for(int i=0; i<referenceQueries.size();i++) {
//					if(!tableReferences.containsKey(referenceQueries.get(i)) || tableReferences.get(referenceQueries.get(i))==null)tableReferences.put(referenceQueries.get(i), new ArrayList<DataAccessString>());
//					for(int j=i+1; j<referenceQueries.size();j++) {
//						
//						tableReferences.get(referenceQueries.get(i)).addAll(getReferenceDataAccessString(referenceQueries.get(i), referenceQueries.get(j)));
//					}
//				}
//				String orQuery=" AND ";
				String joins="";
				for(DataAccessString referenceQuery:this.tableJoins) {

					joins+=referenceQuery.getReferenceDataAcessString() + andQuery;
//					for(DataAccessString referenceQuery:referenceEntry.getValue()) {
//						currentJoin+=referenceQuery.getReferenceDataAcessString()+andQuery;	
//						
//					}
//					if(currentJoin.contains(andQuery))
//						joins="("+currentJoin.substring(0,currentJoin.length()-andQuery.length())+")"+orQuery+joins;
//					else if(!currentJoin.isEmpty())
//						joins+=orQuery;

				}

//			}
			if(joins.contains(andQuery))
				queryString+=joins.substring(0,joins.length()-andQuery.length());
		}else {
			queryString+=" FROM "+this.tableName+" ";
		}
		int queryCount=0;
		for(Entry<String,List<DataAccessString>> entry : this.dataAccessRequestsDisjunction.entrySet()) {
			queryCount=queryCount+entry.getValue().size();
		}
		for(Entry<String,List<DataAccessString>> entry : this.dataAccessRequestsConjunction.entrySet()) {
			queryCount=queryCount+entry.getValue().size();
		}
//		if(!queryString.contains("WHERE") && queryCount>=1) queryString+=" WHERE ";
		
		String queryParameters=getQueryParameterString();	
		if(queryString.contains("WHERE") && !queryParameters.isEmpty())
			queryString+=andQuery;
		queryString+=queryParameters;	
		
//		System.out.println(this.pageRequestMetaData.attributeName +" "+this.pageRequestMetaData.tableName);
		
		if(this.pageRequestMetaData.hasOrder() && this.pageRequestMetaData.isAscending()) {
			String ascOrderRequest = hasReferences?this.pageRequestMetaData.getTableName()+this.referenceOperator+this.pageRequestMetaData.getAttributeName():this.pageRequestMetaData.getAttributeName();
			queryString+=" ORDER BY "+ascOrderRequest+ " ASC";
		}else if(this.pageRequestMetaData.hasOrder() && this.pageRequestMetaData.isDescending()) {			
			String descOrderRequest = hasReferences?this.pageRequestMetaData.getTableName()+this.referenceOperator+this.pageRequestMetaData.getAttributeName():this.pageRequestMetaData.getAttributeName();
			queryString+=" ORDER BY "+descOrderRequest+ " DESC ";
		}

		
		if(this.pageRequestMetaData.isPaginated()) {
			queryString+=" OFFSET "+Integer.toString(Integer.parseInt(this.pageRequestMetaData.getLimit())*Integer.parseInt(this.pageRequestMetaData.pageNumber)) + " ROWS ";
			queryString+=" FETCH NEXT "+this.pageRequestMetaData.getLimit()+ " ROWS ONLY";	
		}else {
			queryString+=" FETCH FIRST "+this.pageRequestMetaData.getLimit()+" ROWS ONLY";
		}
		
		
		System.out.println("Requested Query: "+queryString);
		
		return queryString;
	}
	
	

	
	protected String getQueryParameterString() {
		
		boolean hasReferences=this.dataAccessRequestsConjunction.keySet().size()>1 ||this.dataAccessRequestsDisjunction.keySet().size()>1;
		
		String queryString="";
		int count=0;
		String andConnector=" AND ";
		String orConnector=" OR ";
		String conjunctionQueries="";
		String disjunctionQueries="";
		if (this.dataAccessRequestsConjunction.isEmpty()) {
			System.err.println("No conjunction request submitted so nothing to render for Query.Sql.getQueryParameterString()!");
		}else {
			
			for(Entry<String,List<DataAccessString>> entry : this.dataAccessRequestsConjunction.entrySet()) {
				for(DataAccessString dataAccessString:entry.getValue()) {
					count++;
					if(hasReferences) {
						conjunctionQueries+=dataAccessString.getReferenceDataAcessString();
					}else {
						conjunctionQueries+=dataAccessString.getDataAccessString();	
					}
					conjunctionQueries+=andConnector;
				
				}
			
			}
			if(conjunctionQueries.contains(andConnector)) {
				conjunctionQueries=conjunctionQueries.substring(0,conjunctionQueries.length()-andConnector.length());	
			}			
		}
		if (this.dataAccessRequestsDisjunction.isEmpty()) {
			System.err.println("No disjunction request submitted so nothing to render for Query.Sql.getQueryParameterString()!");
		}else {
			
			for(Entry<String,List<DataAccessString>> entry : this.dataAccessRequestsDisjunction.entrySet()) {
				for(DataAccessString dataAccessString:entry.getValue()) {
					count++;
					if(hasReferences) {
						disjunctionQueries+=dataAccessString.getReferenceDataAcessString();
					}else {
						disjunctionQueries+=dataAccessString.getDataAccessString();	
					}
					disjunctionQueries+=orConnector;
				
				}
			
			}
			if(disjunctionQueries.contains(orConnector)) {
				disjunctionQueries=disjunctionQueries.substring(0,disjunctionQueries.length()-orConnector.length());	
			}			
		}
		if(!this.dataAccessRequestsDisjunction.isEmpty() && !this.dataAccessRequestsConjunction.isEmpty()) {
//			System.out.println("1");
			return "("+conjunctionQueries+") AND ("+disjunctionQueries+")";
		}else if(!this.dataAccessRequestsDisjunction.isEmpty()) {
//			System.out.println("2");
			return  disjunctionQueries;
		}else if(!this.dataAccessRequestsConjunction.isEmpty()) {
//			System.out.println("3");
			return conjunctionQueries;
		}else {
//			System.out.println("4");
			return "";	
		}
		
	}

	@Override
	public boolean isLegalDataAccessReference(String tableName) {
		// TODO Auto-generated method stub
//		return getReferenceRules().get(this.tableName).contains(tableName);
		return true;
	}
	
	
	
	@Override
	public T withAscendingOrderOf() {
		this.pageRequestMetaData.setAscOrder();
		this.pageRequestMetaData.setAttributeName(currentAttributeAccess);
		this.pageRequestMetaData.setTableName(tableName);
		return (T) this;
	}

	@Override
	public T withDescendingOrderOf() {
		this.pageRequestMetaData.setDescOrder();
		this.pageRequestMetaData.setAttributeName(currentAttributeAccess);
		this.pageRequestMetaData.setTableName(tableName);
		return (T) this;
	}
	@Override
	public T withPageNumber(int pageNumber){
		this.pageRequestMetaData.setPageNumber(pageNumber);
		return (T)this;
	}
	
	@Override
	public T withResultLimit(int resultLimit) {
		this.pageRequestMetaData.setLimit(resultLimit);
		return (T) this;
	}
	
	@Override
	public T includeAllAttributesInResultFromSchema(DataSchema dataSchema) {
		if(!this.attributesToIncludInResults.containsKey(dataSchema.tableName()))this.attributesToIncludInResults.put(dataSchema.tableName(), new HashSet<String>());
		for(String attributeName:dataSchema.getAttributeLabels()) {
			this.attributesToIncludInResults.get(dataSchema.tableName()).add(attributeName);
		}
		return (T) this;
	}
	
	@Override
	public T includeAllAttributesInResultFromSchema() {
		includeAllAttributesInResultFromSchema(this.dataSchema);
		return (T) this;
	}
	
	@Override
	public T includeAttributeInResult(String attributeName) {
		if(this.attributesToIncludInResults.containsKey(attributeName))this.attributesToIncludInResults.put(this.tableName, new HashSet<String>());
		this.attributesToIncludInResults.get(this.tableName).add(attributeName);
		return (T) this;
	}
	
	@Override
	public T excludeAttributeInResult(String attributeName) {
		if(this.attributesToIncludInResults.containsKey(this.tableName))this.attributesToIncludInResults.get(this.tableName).remove(attributeName);
		this.attributesToIncludInResults.get(this.tableName).add(attributeName);
		return (T) this;
	}
	
	@Override
	public T excludeAttributeInResult(DataSchema dataSchema,String attributeName) {
		if(this.attributesToIncludInResults.containsKey(dataSchema.tableName()))this.attributesToIncludInResults.get(dataSchema.tableName()).remove(attributeName);
		this.attributesToIncludInResults.get(this.tableName).add(attributeName);
		return (T) this;
	}
	
	

	@Override
	public T includeAttributesInResults(String ...attributeNames) {
//		this.attributesToIncludInResults=new ArrayList<DataAttribute>();
//		for(String attributeName:attributeNames) {
//			if(_pre_isAttributeAllowedToBeAccessed(attributeName)) {
//				this.attributesToIncludInResults.add(new DataAttribute.Builder().withTableName(tableName).withAttributeName(attributeName).build());
//			}
//		}
		return (T) this;
	}
//	protected PageRequestMetaData pageRequestMetaData;
//	protected Map<String,Set<String>> attributesToIncludInResults; 	/*Attributes that will be received after a query*/
//	protected Map<String,List<DataAccessString>> dataAccessRequests;


	public T setPageRequestMetaData( PageRequestMetaData pageRequestMetaData){
		this.pageRequestMetaData=pageRequestMetaData;
		return (T) this;
	}
	public T setAttributesToIncludInResults(Map<String,Set<String>> attributesToIncludInResults){
		this.attributesToIncludInResults=attributesToIncludInResults;
		if(!this.attributesToIncludInResults.containsKey(tableName) || this.attributesToIncludInResults.get(tableName)==null)this.attributesToIncludInResults.put(tableName, new HashSet<String>());
		return (T) this;
	}
//	public T setDataAccessRequests(Map<String,List<DataAccessString>> dataAccessRequests){
//		this.dataAccessRequests=dataAccessRequests;
//		if(!this.dataAccessRequests.containsKey(tableName) || this.dataAccessRequests.get(tableName)==null) this.dataAccessRequests.put(tableName, new ArrayList<DataAccessString>());
//		return (T) this;
//	}
	
	public T setDataAccessRequestsConjunction(Map<String,List<DataAccessString>> conjunctionRequests){
		this.dataAccessRequestsConjunction=conjunctionRequests;
		if(isDisjunctionMode) {
			if(!this.dataAccessRequestsDisjunction.containsKey(tableName) || this.dataAccessRequestsDisjunction.get(tableName)==null) this.dataAccessRequestsDisjunction.put(tableName, new ArrayList<DataAccessString>());
		}else {
			if(!this.dataAccessRequestsConjunction.containsKey(tableName) || this.dataAccessRequestsConjunction.get(tableName)==null) this.dataAccessRequestsConjunction.put(tableName, new ArrayList<DataAccessString>());
		}
		return (T) this;
	}
	
	
	public T setDataAccessRequestsDisjunction(Map<String,List<DataAccessString>> disjunctionRequests){
		this.dataAccessRequestsDisjunction=disjunctionRequests;
		if(isDisjunctionMode) {
			if(!this.dataAccessRequestsDisjunction.containsKey(tableName) || this.dataAccessRequestsDisjunction.get(tableName)==null) this.dataAccessRequestsDisjunction.put(tableName, new ArrayList<DataAccessString>());
		}else {
			if(!this.dataAccessRequestsConjunction.containsKey(tableName) || this.dataAccessRequestsConjunction.get(tableName)==null) this.dataAccessRequestsConjunction.put(tableName, new ArrayList<DataAccessString>());
		}
		return (T) this;
	}
	
	public T addTableJoins(List<DataAccessString> tableJoins){
		this.tableJoins.addAll(tableJoins);
		return (T) this;
	}
	
	
//	public T setReferences(Map<String,List<DataAccessString>> references){
//		this.references=references;
//		return (T) this;
//	}



//	
//	protected boolean _pre_isAttributeAccessAllowed(String attributeName) {
//		boolean result=this.allowedAttributeDataAccessTypes.keySet().contains(attributeName);
//		if(!result) System.err.println("Warning: requested results to include '"+attributeName+"' which do not exist in the schema, and will be left out of the results");
//		return result;
//	}

//	@Override
//	public T requestNewQuery() {
//		clearQueryCache();
//		return (T) this;
//	}
	
	private void clearQueryCache() {
		this.pageNumber="";
		this.attributesToIncludInResults=new HashMap<String, Set<String>>();	/*Attributes that will be received after a query*/
		this.ascOrderOfAttribute="";/*order of query results ascending*/
		this.ascOrderOfTable="";
		this.descOrderOfAttribute="";/*order of query results descending*/
		this.descOrderOfTable="";/*order of query results descending*/
//		this.references=new HashMap<String, List<DataAccessString>>();
		this.resultLimit="20";
	}
	
	public DataObjectCompiler executeQuery() {
		System.out.println("execuiote quire "+ getQueryString());
		return new DataObjectCompiler(getQueryString(), attributesToIncludInResults);
	}
	
	protected DataAccessString generateReferenceWiring(String sourceTableName,String sourceTableAttribute, String referenceTableName, String referenceTableAttribute) {
	return new DataAccessString.Builder()
			 .withTableName(sourceTableName)
			 .withAttributeName(sourceTableAttribute)
			 .withDataAccessParameter(referenceTableName+referenceOperator+referenceTableAttribute)
			 .withDataAccessParameterPrefix("=")
			 .withReferenceOperator(referenceOperator)
			 .build();
	}

	protected abstract List<DataAccessString> getReferenceDataAccessString(String tableName, String otherTableName);
	protected abstract Map<String, Set<String>> getReferenceRules();
	
	public static class Generator{
//		String buildOrder; //ordered via stack?
//		public String book = new BookSchema().tableName();
//		public String customer  = new CustomerSchema().tableName();
//		public String review = new ReviewSchema().tableName();
//		public String purchaseOrder = new PurchaseOrderSchema().tableName();
//		public String cart = new CartSchema().tableName();
//		public String visitor = new VisitorSchema().tableName();
//		private static final String DELIMITER= ">";
//		private Set<Visitor> visitors;
//		private Set<Customer> customers;
//		private Set<Book> books;
//		private Set<PurchaseOrder> purchaseOrders;
//		private Set<Review> reviews;
//		private Set<Cart> carts;
//		private Set<Bean> currentResults;
//
//		private BookDataFetcher bookDataFetcher;
//		private CustomerDataFetcher customerDataFetcher;
//		private CartDataFetcher cartDataFetcher;
//		private VisitorDataFetcher visitorDataFetcher;
//		private ReviewDataFetcher reviewDataFetcher;
//		private PurchaseOrderDataFetcher purchaseOrderDataFetcher;
//		private String queryString;
//		
//		public Generator(String queryString,List<String> attributesIncludedInResults) {
//			this.visitors=new HashSet<Visitor>();
//			this.customers=new HashSet<Customer>();
//			this.books=new HashSet<Book>();
//			this.purchaseOrders=new HashSet<PurchaseOrder>();
//			this.reviews=new HashSet<Review>();
//			this.carts=new HashSet<Cart>();
//			this.buildOrder="";
//			this.queryString=queryString;
//			
//
//			Connection connection=null;
//			PreparedStatement preparedStatement=null;
//			ResultSet resultSet=null;
//			if(this.queryString==null || this.queryString.isEmpty()) {
//				System.err.println("Warning empty query string was submitted!");
//				return;
//			}
//	
//			try {
//				DataSource dataSource= (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
//				connection = dataSource.getConnection();
//				preparedStatement= connection.prepareStatement(this.queryString);
//				resultSet= preparedStatement.executeQuery();
//				while(resultSet.next()){
//					if(resultAttributeIncludesTable(cart)) this.carts.add(this.cartDataFetcher.resultSetToBean(resultSet));
//					if(resultAttributeIncludesTable(customer))this.customers.add(this.customerDataFetcher.resultSetToBean(resultSet));
//					if(resultAttributeIncludesTable(purchaseOrder))this.purchaseOrders.add(this.purchaseOrderDataFetcher.resultSetToBean(resultSet));
//					if(resultAttributeIncludesTable(visitor))this.visitors.add(this.visitorDataFetcher.resultSetToBean(resultSet));
//					if(resultAttributeIncludesTable(book))this.books.add(this.bookDataFetcher.resultSetToBean(resultSet));
////					if(resultAttributeIncludesTable(review))this.reviews.add(this.reviewDataFetcher.resultSetToBean(resultSet));
//					
//				}
//				
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (NamingException e) {
//					// TODO Auto-generated catch block
//				e.printStackTrace();
//			}finally {			
//					
//					try {
//						if(resultSet!=null)resultSet.close();
//						if(preparedStatement!=null)preparedStatement.close();
//						if(connection!=null)connection.close();
//					}catch (Exception e) {
//						e.printStackTrace();
//					}	
//					
//					
//			}
//			
//		}
//		
//		
//		
//
//		
//		private void writeBuildOrder(String name) {
//			this.buildOrder+=!buildOrder.contains(name)?name+DELIMITER:"";
//		}
//		
//		Generator thenBuildBooks() {
//			if(!resultAttributeIncludesTable(book)) return this;
//			if(this.buildOrder.isEmpty() || isLastBuildStep(review) ||isLastBuildStep(cart) || isLastBuildStep(purchaseOrder)) writeBuildOrder(review);
//			return this;			
//		}
//		
//		Generator thenBuildReview() {
//			if(!resultAttributeIncludesTable(review)) return this;
//			if(this.buildOrder.isEmpty() || isLastBuildStep(book)) writeBuildOrder(review);
//			return this;
//		}
//		
//		Generator thenBuildCustomer() {
//			if(!resultAttributeIncludesTable(customer)) return this;
//			if(this.buildOrder.isEmpty() || isLastBuildStep(review)) writeBuildOrder(customer);
//			return this;
//		}
//		
//		Generator thenBuildVisitors() {
//			if(!resultAttributeIncludesTable(visitor)) return this;
//			if(this.buildOrder.isEmpty() || isLastBuildStep(cart)) writeBuildOrder(visitor);
//			return this;
//		}
//		
//		Generator thenBuildCarts() {
//			if(!resultAttributeIncludesTable(cart)) return this;
//			if(this.buildOrder.isEmpty() || isLastBuildStep(customer)|| isLastBuildStep(visitor)) writeBuildOrder(cart);
//			return this;
//		}
//		
//		Generator thenBuildPurchaseOrder() {
//			if(!resultAttributeIncludesTable(purchaseOrder)) return this;
//			if(this.buildOrder.isEmpty() || isLastBuildStep(customer)) writeBuildOrder(purchaseOrder);
//			return this;
//		}
//		
//
//
//		
//		private boolean hasBuildOrder(String ...names) {
//			String order="";
//			for(String name:names) {
//				order+=name+DELIMITER;
//			}	
//			return this.buildOrder.contains(order);
//		}
//		
//		private boolean resultAttributeIncludesTable(String tableName) {
//			return true;
//		}
//		
//		private boolean isLastBuildStep(String name) {	
//			return this.buildOrder.endsWith(name+DELIMITER);
//		}
//		
//
//		
//		public List<Book> getBooks(){
//			List<Book> results = new ArrayList<Book>(this.books);
//			if(hasBuildOrder(book,review)) {
//				Set<Review> reviews = new HashSet<Review>(getReviews());
//				Map<Book,HashSet<Review>> bookToReviews = new HashMap<Book, HashSet<Review>>();
//				for(Review review:reviews) {
//					if(!bookToReviews.containsKey(review.getBook())) {
//						bookToReviews.put(review.getBook(), new HashSet<Review>());
//					}
//					bookToReviews.get(review.getBook()).add(review);
//				}	
//				
//				List<Book> bookWithReview = new ArrayList<Book>();
//				for(Book book: this.books) {
//					Set<Review> reviewsOfBook=bookToReviews.get(book);
//					bookToReviews.remove(book);
//					bookWithReview.add(new Book.Builder(book).withReviews(reviewsOfBook.toArray(new Review[reviewsOfBook.size()])).build());					
//				}
//				results=bookWithReview;
//			}
//			return results;
//		}
//		
//		public List<Review> getReviews(){
//
//			return new ArrayList<Review>(this.reviews);
//		}
//		
//		
//		public List<Customer> getCustomers(){		
//			List<Customer> results = new ArrayList<Customer>(this.customers);
//			
//			if(hasBuildOrder(customer,review)) {
//				Map<Customer,HashSet<Review>> customerToReviews = new HashMap<Customer, HashSet<Review>>();
//				for(Review review:getReviews()) {
//					if(!customerToReviews.containsKey(review.getCustomer())) {
//						customerToReviews.put(review.getCustomer(), new HashSet<Review>());
//					}
//					customerToReviews.get(review.getCustomer()).add(review);
//				}	
//				List<Customer> customerWithReviews=new ArrayList<Customer>();
//				for(Customer customer: results) {
//					Set<Review> reviewsByCustomer=customerToReviews.get(customer);
//					customerWithReviews.add(new Customer.Builder(customer).withReviews(reviewsByCustomer.toArray(new Review[reviewsByCustomer.size()])).build());					
//				}
//				results=customerWithReviews;
//			}
//			
//			if(hasBuildOrder(customer,cart)) {
//				Map<Customer,Cart> customerToCarts = new HashMap<Customer, Cart>();
//				for(Cart cart:getCarts()) {
//					if(!cart.isCustomerCart()) continue;
//					Customer customer = new Customer.Builder().withId(cart.getId()).build();
//					if(!customerToCarts.containsKey(customer)) {
//						customerToCarts.put(customer, cart);
//					}else {
//						customerToCarts.get(customer).combineCarts(cart);
//					}
//
//				}	
//				List<Customer> customerWithCart=new ArrayList<Customer>();
//				for(Customer customer: results) {
//					customerWithCart.add(new Customer.Builder(customer).withCart(customerToCarts.get(customer)).build());					
//				}
//				results=customerWithCart;
//			}
//			
//			
//			if(hasBuildOrder(customer,purchaseOrder)) {
//				Map<Customer,Set<PurchaseOrder>> customerToPurchaseOrders = new HashMap<Customer, Set<PurchaseOrder>>();
//				for(PurchaseOrder purchaseOrder:getPurchaseOrder()) {
//					Customer customer = new Customer.Builder(purchaseOrder.getCustomer()).build();
//					if(!customerToPurchaseOrders.containsKey(customer)) {
//						customerToPurchaseOrders.put(customer, new HashSet<PurchaseOrder>());
//					}
//					customerToPurchaseOrders.get(customer).add(purchaseOrder);
//				}	
//				List<Customer> customerWithPurchseOrder=new ArrayList<Customer>();
//				
//				for(Customer customer: results) {
//					PurchaseOrder[] purchaseOrders= new PurchaseOrder[customerToPurchaseOrders.get(customer).size()];
//					customerToPurchaseOrders.get(customer).toArray(purchaseOrders);
//					customerWithPurchseOrder.add(new Customer.Builder(customer).withPurchaseOrders(purchaseOrders).build());					
//				}
//				results=customerWithPurchseOrder;
//			}
//			return results;
//			
//		}
//		
//		public List<Visitor> getVisitor(){
//			//build visitor.withCart get books
//			List<Visitor> visitorResults = new ArrayList<Visitor>(this.visitors);
//			if(hasBuildOrder(visitor,cart)) {
//				Map<Visitor,Cart> visitorToCarts = new HashMap<Visitor, Cart>();
//				for(Cart cart:getCarts()) {
//					if(!cart.isVisitorCart()) continue;
//					Visitor visitor = new Visitor.Builder().withId(cart.getId()).build();
//					if(!visitorToCarts.containsKey(visitor)) {
//						visitorToCarts.put(visitor, cart);
//					}else {
//						visitorToCarts.get(visitor).combineCarts(cart);
//					}
//				}	
//				List<Visitor> visitorWithCart=new ArrayList<Visitor>();
//				for(Visitor visitor: visitorResults) {
//					visitorWithCart.add(new Visitor.Builder(visitor).withCart(visitorToCarts.get(visitor)).build());					
//				}
//				visitorResults=visitorWithCart;
//			}
//			return visitorResults;
//			
//		}
//		
//		public List<Cart> getCarts(){
//			List<Cart> results = new ArrayList<Cart>();
//			Map<Id,Book> idToBooks=new HashMap<Id, Book>();
//			for(Book book: getBooks()) {
//				idToBooks.put(book.getId(), book);
//			}
//			
//			for(Cart cart: this.carts) {
//				Map<Book,Integer> booksInCart=new HashMap<Book, Integer>();
//				for(Entry<Book,Integer> entry:cart.getBooks().entrySet()) {
//					booksInCart.put(idToBooks.get(entry.getKey().getId()),entry.getValue());
//				}
//				results.add(new Cart.Builder(cart).withBooks(booksInCart).build());
//			}
//			return results;
//		}
//		
//		public List<PurchaseOrder> getPurchaseOrder(){
//			List<PurchaseOrder> results = new ArrayList<PurchaseOrder>();
//			Map<Id,Book> idToBooks=new HashMap<Id, Book>();
//			for(Book book: getBooks()) {
//				idToBooks.put(book.getId(), book);
//			}
//			
//			for(PurchaseOrder purchaseOrder: this.purchaseOrders) {
//				Map<Book,Integer> booksInPurchaseOrder=new HashMap<Book, Integer>();
//				for(Entry<Book,Integer> entry:purchaseOrder.getBooks().entrySet()) {
//					booksInPurchaseOrder.put(idToBooks.get(entry.getKey().getId()),entry.getValue());
//				}
//				results.add(new PurchaseOrder.Builder().withBooks(booksInPurchaseOrder).build());
//			}
//			return results;
//			
//		}
//		
//		
//		public String toJson() {
//			//returns current results to 
//			return "";
//		}
//		
//		
//
//		
//
//		
	}
	


	

	

	
	
}



