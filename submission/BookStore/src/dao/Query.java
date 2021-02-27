package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;


/**
 * Flexible mechanism to submit queries. Query can be set up with several properties to specify
 * what type of queries are allowed for each attribute. Only the allowed query parameters can be submitted for any given attribute
 * Queries can easily be built on demand to allow for modular and flexible requests.
*/
public abstract class Query extends DataAccessRequest{
	
//	//Query Type Enumeration//
	final static String CONTAINS="contains";
	final static String WITH="with";
	final static String STARTS_WITH="startsWith";
	final static String ENDS_WITH="endsWith";
	final static String PATTERN="pattern";
	final static String EQUALS="equals";
	final static String ATMOST="to";
	final static String ATLEAST="from";
	final static String WITHIN="within";
	
	protected List<String> wordQueryTypes;//=Arrays.asList(CONTAINS, WITH, STARTSWITH,ENDSWITH,PATTERN,EQUALS); 	/*Enum for all word type queries*/
	protected List<String> numberQueryTypes;//=Arrays.asList(ATMOST,ATLEAST,WITHIN,EQUALS);	/*Enum for all number based queries*/
	protected List<String> varCharQueryTypes;//=Arrays.asList(CONTAINS, WITH, STARTSWITH,ENDSWITH,PATTERN,EQUALS); 	/*Enum for all word type queries*/
	

	public abstract String renderQueryString();
	public abstract String renderMutationString();
	public abstract Query includeAttributesInResults(String ...attributeNames);
	public abstract Query withAscendingOrderOf(String attributeName);	
	public abstract Query withDescendingOrderOf(String attributeName);
	
	public Map<String,QueryString> queryTypeTranslation;
	
	protected String tableName;
	protected List<String> wordAttributes;
	protected List<String> numberAttributes;
	protected List<String> varCharAttributes;
	protected Map<String,List<String>> queryRules;	
	protected Map<String,List<String>> allowedQueries;	
	protected String ascOrderOf;/*order of query results ascending*/
	protected String descOrderOf;/*order of query results descending*/
	
	private List<String> attributesToBeQueried;
	
	//Query Request properties
	private Map<String,Map<String,String>> attributeFormattedQueries; 	/*Map of attributes and all formatted query strings associated with it*/
	protected List<String> attributesToIncludInResults; 	/*Attributes that will be received after a query*/

	

	
	public Query resultContainsAttributes(String ...attributeNames) {
		for(String attributeName:attributeNames) {
			if(this.allowedQueries.keySet().contains(attributeName)) {
				this.attributesToIncludInResults.add(attributeName);
			}else {
				System.err.println("Warning: requested results to include '"+attributeName+"' which do not exist in the schema, and will be left out of the results");
			}
		}
		return this;
	}

	
	public Query requestNewQuery() {
		clearQueryCache();
		return this;
	}
	



	
	public Query queryAttributes(String ...attributeNames) {
		if(isAttributeTypesConsistent(attributeNames)) {
			System.err.println("Warning, the requested query attributes are not of a consistent type, please only request same type attributes for each queryAttribyte() request, attributes not added" );
			return this;
		}
		
		for(String attributeName: attributeNames) {
			if(this.allowedQueries.keySet().contains(attributeName)) {
				if(!attributesToBeQueried.contains(attributeName)) {
					this.attributesToBeQueried.add(attributeName);
				}
			}else {
				System.err.println("Warning: the attribute named: '"+attributeName+"' cannot be queried it does not exist in the schema, and will be left out of the results");
			}			
		}
		return this;
	}

	
	public Query addFormattedQueryString(String queryType,String queryParameter) {
		if(this.attributesToBeQueried==null || this.attributesToBeQueried.isEmpty()){
			System.err.println("Warning: no attributes were requested for query");
			return this;
		}
		
		if(queryTypeTranslation.get(queryType)==null) {
			System.err.println("Warning: no mapped translation format for query string, cannot render");
			return this;
		}
		
		for(String attributeName:this.attributesToBeQueried) {
			String prefix=queryTypeTranslation.get(queryType).prefix;
			String suffix=queryTypeTranslation.get(queryType).suffix;
			if(isAttributeQueryAllowed(attributeName,queryType)) {
				if(this.attributeFormattedQueries.get(attributeName).get(queryType)!=null) {
					System.err.println("Warning: query type was already requested, and will be replaced with new version.");
				}				
				this.attributeFormattedQueries.get(attributeName).putIfAbsent(queryType, prefix+queryParameter+suffix);
			}else {
				System.err.println("Warning: the attribute named: '"+attributeName+"' is not permitted to make this type of query");			
			}
		}
		return this;
	}
	
	
	
	public Query wordContains(String contains) {	
		if(isWordQueryValid(contains)) {
			addFormattedQueryString(this.CONTAINS,contains);
		}
		
		return this;				
	}
	
	public Query wordEquals(String equals) {
		if(isWordQueryValid(equals)) {
		addFormattedQueryString(this.EQUALS,equals);
		}
		return this;	
	}
	
	public Query wordStartsWith(String prefix) {
		if(isWordQueryValid(prefix)) {
		addFormattedQueryString(this.STARTS_WITH,prefix);
		}
		return this;
	}
	
	public Query wordEndsWith(String suffix) {
		if(isWordQueryValid(suffix)) {
		addFormattedQueryString(this.ENDS_WITH,suffix);
		}
		return this;
	}
	
	public Query wordWithPattern(String pattern) {
		if(isWordQueryValid(pattern)) {
		addFormattedQueryString(this.PATTERN,pattern);
		}
		return this;
	}
	
	
	public Query varCharContains(String contains) {	
		if(isWordQueryValid(contains)) {
			addFormattedQueryString(this.CONTAINS,contains);
		}
		
		return this;				
	}
	
	public Query varCharEquals(String equals) {
		if(isWordQueryValid(equals)) {
		addFormattedQueryString(this.EQUALS,equals);
		}
		return this;	
	}
	
	public Query varCharStartsWith(String prefix) {
		if(isWordQueryValid(prefix)) {
		addFormattedQueryString(this.STARTS_WITH,prefix);
		}
		return this;
	}
	
	public Query varCharEndsWith(String suffix) {
		if(isWordQueryValid(suffix)) {
		addFormattedQueryString(this.ENDS_WITH,suffix);
		}
		return this;
	}
	
	public Query varCharWithPattern(String pattern) {
		if(isWordQueryValid(pattern)) {
		addFormattedQueryString(this.PATTERN,pattern);
		}
		return this;
	}
	

	public Query numberAtMost(String max) {
		if(isNumberQueryValid(max)) {
		addFormattedQueryString(this.ATMOST,max);
		}
		return this;
	}
	
	public Query numberAtLeast(String min) {
		if(isNumberQueryValid(min)) {
		addFormattedQueryString(this.ATLEAST,min);
		}
		return this;
	}
	
	public Query numberBetween(String min,String max) {
		if(isNumberQueryValid(min) && isNumberQueryValid(max)) {
			addFormattedQueryString(this.ATMOST,max);
			addFormattedQueryString(this.ATLEAST,min);
		}
		return this;
	}
	

	public void clearQueryCache() {
		this.attributesToBeQueried=new ArrayList<String>();
	}

	
	public abstract String toJson();
	
	
	private boolean isAttributeTypesConsistent(String ...attributeNames) {
		int wordAttcount=0;
		int numAttcount=0;
		int varAttcount=0;
		if(attributeNames.length==0) return true;
		for(String attributeName: attributeNames) {
			if(this.wordAttributes.contains(attributeName)) {
				wordAttcount++;
			}
			
			if(this.numberAttributes.contains(attributeName)) {
				numAttcount++;
			}
			
			if(this.varCharAttributes.contains(attributeName)) {
				varAttcount++;
			}
		}

		return (wordAttcount>0&&numAttcount==0&&varAttcount==0) 
				||
				(wordAttcount==0&&numAttcount>0&&varAttcount==0) 
				||
				(wordAttcount==0&&numAttcount==0&&varAttcount>0);
		
	}

	public boolean isAttributeQueryAllowed(String attributeName, String queryType) {
		return this.allowedQueries.get(attributeName)!=null && !this.allowedQueries.get(attributeName).isEmpty()&&this.allowedQueries.get(attributeName).contains(queryType);
	}
	
	public boolean isQueryValid(String attributeName, String queryType) {
		return this.allowedQueries.get(attributeName).contains(queryType);
	}
	
	public boolean isWordQueryValid(String queryParameter) {
		boolean result=true;
		if (!result) {
			System.err.println("Warning parameter for word query is invalid, will not add query request for attribute");
		}
		return true;
	}
	
	public boolean isvarCharQueryValid(String queryParameter) {
		boolean result=true;
		if (!result) {
			System.err.println("Warning parameter for word query is invalid, will not add query request for attribute");
		}
		return true;
	}
	
	public boolean isNumberQueryValid(String queryParameter) {
		return true;
	}	
	
	
	
	
}
	


