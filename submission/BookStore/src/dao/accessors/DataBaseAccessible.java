package dao.accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DataBaseAccessible{
	protected String tableName;
	protected List<String> wordAttributes;
	protected List<String> numberAttributes;
	protected List<String> varCharAttributes;
	protected List<String> objectAttributes;
	
	
	
	protected Map<String,List<String>> allowedQueries;			
	private List<String> currentList;
	protected DataAccessRequest dataAccessRequest;
	
	public abstract DataAccessRequest build();	
	protected abstract boolean isQueryTypeExists(String queryType);
	protected abstract boolean isAttrubuteExists(String queryType);
		

	
	
	private void addToCurrentList(String attributeName) {
		this.currentList.add(attributeName);
	}
	
	private void clearCurrentList() {
		this.currentList=new ArrayList<String>();
	}

	

	public DataBaseAccessible withQueryableWordAttributes(String ...attributeNames) {

		
		clearCurrentList();
		
		for(String attributeName:attributeNames) {
			if(!isAttrubuteExists(attributeName)) {
				this.wordAttributes.add(attributeName);
				addToCurrentList(attributeName);				
			}
		}
		return this;
	}
	


	public DataBaseAccessible withQueryableNumberAttributes(String ...attributeNames) {
		clearCurrentList();
		
		for(String attributeName:attributeNames) {
			if(!isAttrubuteExists(attributeName)) {
				this.numberAttributes.add(attributeName);
				addToCurrentList(attributeName);				
			}
		}
		return this;
	}
	
	

	public DataBaseAccessible withQueryableVarCharAttributes(String ...attributeNames) {
		clearCurrentList();
		for(String attributeName:attributeNames) {
			if(!isAttrubuteExists(attributeName)) {
				this.varCharAttributes.add(attributeName);
				addToCurrentList(attributeName);				
			}
		}
		return this;		
	}
	
	
	
	


	public DataBaseAccessible allowAllWordQueries() {
		for(String wordQuery: dataAccessRequest.wordDataAccessRequestTypes) {
			allowQueryTypeForAttributes(this.wordAttributes,wordQuery);
		}
		return this;	
		
	}

	public DataBaseAccessible allowAllNumberQueries() {
		for(String numQuery: dataAccessRequest.numberDataAccessRequestTypes) {
			allowQueryTypeForAttributes(numberAttributes,numQuery);
		}
		return this;	
		
	}
	

	public DataBaseAccessible allowAllVarCharQueries() {
		for(String varCharQuery: dataAccessRequest.varCharDataAccessRequestTypes) {
			allowQueryTypeForAttributes(numberAttributes,varCharQuery);
		}
		return this;	
		
	}
	

	
	

	public DataBaseAccessible allowQueryTypeForAttributes(List<String> attributes,String queryType) {
		for(String attribute:attributes) {
			if(isAttributeWithQueryLegal(attribute,queryType)) {
				if(this.allowedQueries.get(attribute)==null)	this.allowedQueries.put(attribute, new ArrayList<String>());
				this.allowedQueries.get(attribute).add(queryType);
			}else {
				System.err.println("Warning query: "+queryType+" not legal with attribute:"+attribute+"for setting query rule, will not add");
			}
		}
		return this;
		
	}



	protected boolean isAttributeWithQueryLegal(String attribute,String queryType) {
		if(!isQueryTypeExists(queryType)) {
			System.err.println("Warning query: "+queryType+" for setting query rule properties does not exist");
			return false;
		}
		
		if(!isAttrubuteExists(attribute)) {
			System.err.println("Warning attribute: "+attribute+" for setting query rule properties does not exist");
			return false;
		}
		

		if(this.wordAttributes.contains(attribute) && this.dataAccessRequest.wordDataAccessRequestTypes.contains(queryType)) {
			return true;
		}
		
		if(this.numberAttributes.contains(attribute) && this.dataAccessRequest.numberDataAccessRequestTypes.contains(queryType)) {
			return true;
		}		
		
		if(this.varCharAttributes.contains(attribute) && this.dataAccessRequest.varCharDataAccessRequestTypes.contains(queryType)) {
			return true;
		}
		
		return false;
	}	
	
	public DataBaseAccessible allowContains() {
		allowQueryTypeForAttributes(this.currentList, dataAccessRequest.CONTAINS);
		return this;
	}
	public DataBaseAccessible allowWith() {
		allowQueryTypeForAttributes(this.currentList, dataAccessRequest.WITH);
		return this;
	}
	public DataBaseAccessible allowStartsWith() {
		allowQueryTypeForAttributes(this.currentList,dataAccessRequest.STARTS_WITH);
		return this;
	}
	public DataBaseAccessible allowEndsWith() {
		allowQueryTypeForAttributes(this.currentList,dataAccessRequest.ENDS_WITH);
		return this;
	}
	public DataBaseAccessible allowPattern() {
		allowQueryTypeForAttributes(this.currentList,dataAccessRequest.PATTERN);
		return this;
	}
	public DataBaseAccessible allowEquals() {
		allowQueryTypeForAttributes(this.currentList,dataAccessRequest.EQUALS);
		return this;
	}
	public DataBaseAccessible allowAtMost() {
		allowQueryTypeForAttributes(this.currentList,dataAccessRequest.ATMOST);
		return this;
	}
	public DataBaseAccessible allowAtLeast() {
		allowQueryTypeForAttributes(this.currentList,dataAccessRequest.ATLEAST);
		return this;
	}
	public DataBaseAccessible allowWithin() {
		allowQueryTypeForAttributes(this.currentList,dataAccessRequest.WITHIN);
		return this;
	};
	
	

}
