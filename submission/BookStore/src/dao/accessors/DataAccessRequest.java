package dao.accessors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import dao.DataSchema;
import dao.accessors.queries.QueryString;

import java.util.Queue;


/**
 * Flexible mechanism to submit queries. Query can be set up with several properties to specify
 * what type of queries are allowed for each attribute. Only the allowed query parameters can be submitted for any given attribute
 * Queries can easily be built on demand to allow for modular and flexible requests.
*/
public abstract class DataAccessRequest{
	
	
	protected String tableName;
	protected List<String> wordAttributes;
	protected List<String> numberAttributes;
	protected List<String> varCharAttributes;
	protected Map<String,List<String>> allowedAttributeDataAccessTypes;	
	
	private List<String> attributesToBeAccessed;
	protected DataSchema dataSchema;
	
	//Query Request properties
	protected Map<String,Map<String,String>> dataAccessFormattedRequest; 	/*Map of attributes and all formatted query strings associated with it*/
	protected Map<String,QueryString> dataAccessTypeTranslation;
	DataAccessTypes dataAccessTypes;
	
	public abstract String toJson();
	

	public DataAccessRequest accessAttributes(String ...attributeNames) {
		if(_pre_isAttributeTypesConsistent(attributeNames)) {
			return this;
		}
		
		for(String attributeName: attributeNames) {
			if(_pre_isAttributeAllowedToBeAccessed(attributeName)) {
				if(!attributesToBeAccessed.contains(attributeName)) {
					this.attributesToBeAccessed.add(attributeName);
				}
			}			
		}
		return this;
	}
	

	
	public DataAccessRequest addFormattedQueryString(String dataAccessType,String dataAccessParameter) {
		if(!_pre_attributesToBeAccessedNotEmpty()) return this;
		if(!_pre_translationExistsForDataAccessRequest(dataAccessType)) return this;
		
		for(String attributeName:this.attributesToBeAccessed) {
			String prefix=this.dataAccessTypeTranslation.get(dataAccessType).prefix;
			String suffix=this.dataAccessTypeTranslation.get(dataAccessType).suffix;
			if(_pre_isAttributeDataAccessTypeAllowed(attributeName,dataAccessType)) {
				if(_pre_isAttributeNotRequestedDataAccessForParameter(attributeName,dataAccessParameter));								
				this.dataAccessFormattedRequest.get(attributeName).putIfAbsent(dataAccessType, prefix+dataAccessParameter+suffix);
			}
		}
		return this;
	}
	

	
	
	public DataAccessRequest wordContains(String contains) {	
		if(_pre_isWordAttributeDataAccessRequestValid(contains)) {
			addFormattedQueryString(this.dataAccessTypes.CONTAINS,contains);
		}
		
		return this;				
	}
	
	public DataAccessRequest wordEquals(String equals) {
		if(_pre_isWordAttributeDataAccessRequestValid(equals)) {
		addFormattedQueryString(this.dataAccessTypes.EQUALS,equals);
		}
		return this;	
	}
	
	public DataAccessRequest wordStartsWith(String prefix) {
		if(_pre_isWordAttributeDataAccessRequestValid(prefix)) {
		addFormattedQueryString(this.dataAccessTypes.STARTS_WITH,prefix);
		}
		return this;
	}
	
	public DataAccessRequest wordEndsWith(String suffix) {
		if(_pre_isWordAttributeDataAccessRequestValid(suffix)) {
		addFormattedQueryString(this.dataAccessTypes.ENDS_WITH,suffix);
		}
		return this;
	}
	
	public DataAccessRequest wordWithPattern(String pattern) {
		if(_pre_isWordAttributeDataAccessRequestValid(pattern)) {
		addFormattedQueryString(this.dataAccessTypes.PATTERN,pattern);
		}
		return this;
	}
	
	
	public DataAccessRequest varCharContains(String contains) {	
		if(_pre_isWordAttributeDataAccessRequestValid(contains)) {
			addFormattedQueryString(this.dataAccessTypes.CONTAINS,contains);
		}
		
		return this;				
	}
	
	public DataAccessRequest varCharEquals(String equals) {
		if(_pre_isWordAttributeDataAccessRequestValid(equals)) {
		addFormattedQueryString(this.dataAccessTypes.EQUALS,equals);
		}
		return this;	
	}
	
	public DataAccessRequest varCharStartsWith(String prefix) {
		if(_pre_isWordAttributeDataAccessRequestValid(prefix)) {
		addFormattedQueryString(this.dataAccessTypes.STARTS_WITH,prefix);
		}
		return this;
	}
	
	public DataAccessRequest varCharEndsWith(String suffix) {
		if(_pre_isWordAttributeDataAccessRequestValid(suffix)) {
		addFormattedQueryString(this.dataAccessTypes.ENDS_WITH,suffix);
		}
		return this;
	}
	
	public DataAccessRequest varCharWithPattern(String pattern) {
		if(_pre_isWordAttributeDataAccessRequestValid(pattern)) {
		addFormattedQueryString(this.dataAccessTypes.PATTERN,pattern);
		}
		return this;
	}
	

	public DataAccessRequest numberAtMost(String max) {
		if(_pre_isNumberDataAccessRequestValid(max)) {
		addFormattedQueryString(this.dataAccessTypes.ATMOST,max);
		}
		return this;
	}
	
	public DataAccessRequest numberAtLeast(String min) {
		if(_pre_isNumberDataAccessRequestValid(min)) {
		addFormattedQueryString(this.dataAccessTypes.ATLEAST,min);
		}
		return this;
	}
	
	public DataAccessRequest numberBetween(String min,String max) {
		if(_pre_isNumberDataAccessRequestValid(min) && _pre_isNumberDataAccessRequestValid(max)) {
			addFormattedQueryString(this.dataAccessTypes.ATMOST,max);
			addFormattedQueryString(this.dataAccessTypes.ATLEAST,min);
		}
		return this;
	}
	

	public void clearQueryCache() {
		this.attributesToBeAccessed=new ArrayList<String>();
	}

	
	
	private boolean _pre_isAttributeTypesConsistent(String ...attributeNames) {
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
		boolean result=(wordAttcount>0&&numAttcount==0&&varAttcount==0) 
				||
				(wordAttcount==0&&numAttcount>0&&varAttcount==0) 
				||
				(wordAttcount==0&&numAttcount==0&&varAttcount>0);

		if (!result) System.err.println("Warning, the requested access attributes are not of a consistent type, please only request same type attributes for each request. Attributes were not added" );
		
		return result;
		

		
	}
	
	boolean _pre_isAttributeAllowedToBeAccessed(String attributeName) {
		boolean result= this.allowedAttributeDataAccessTypes.keySet().contains(attributeName);
		if(!result)	System.err.println("Warning: the attribute named: '"+attributeName+"' cannot be accessed it is not allowed, and will be left out of the results");
		return result;
	}


	public boolean _pre_isAttributeDataAccessTypeAllowed(String attributeName, String dataAccessParameter) {
		boolean result=this.allowedAttributeDataAccessTypes.get(attributeName)!=null && !this.allowedAttributeDataAccessTypes.get(attributeName).isEmpty()&&this.allowedAttributeDataAccessTypes.get(attributeName).contains(dataAccessParameter);
		if(!result)System.err.println("Warning: the attribute named: '"+attributeName+"' is not permitted to make this type of data access");	
		return result;
	}
	
	private boolean _pre_isAttributeNotRequestedDataAccessForParameter(String attributeName, String dataAccesParameter) {
		boolean result=this.dataAccessFormattedRequest.get(attributeName).get(dataAccesParameter)!=null;
		if(!result)	System.err.println("Warning: attribute : "+attributeName+" already requested, access of type: "+dataAccesParameter+" the previous version will be replaced with most recent one.");
		
		return result;
	}
	
	public boolean _pre_attributesToBeAccessedNotEmpty()  {
		boolean result=this.attributesToBeAccessed==null || this.attributesToBeAccessed.isEmpty();
		if(!result)System.err.println("Warning: no attributes were requested for query");
		return result;
	}
	
	public boolean _pre_translationExistsForDataAccessRequest(String dataAccessParameter) {
		boolean result=this.dataAccessTypeTranslation.get(dataAccessParameter)!=null;
		if(!result)System.err.println("Warning: no attributes were requested for query");
		return result;
	}
	
	
	public boolean _pre_isWordAttributeDataAccessRequestValid(String queryParameter) {
		boolean result=true;
		if (!result) {
			System.err.println("Warning parameter for word query is invalid, will not add query request for attribute");
		}
		return true;
	}
	
	public boolean _pre_isvarCharQueryDataAccessRequestValid(String queryParameter) {
		boolean result=true;
		if (!result) {
			System.err.println("Warning parameter for word query is invalid, will not add query request for attribute");
		}
		return true;
	}
	
	public boolean _pre_isNumberDataAccessRequestValid(String queryParameter) {
		return true;
	}	
	
	

	
	
	
	
}
	


