package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Queryable{
	protected String tableName;
	protected List<String> wordAttributes;
	protected List<String> numberAttributes;
	protected List<String> varCharAttributes;
	protected List<String> objectAttributes;
	
	
	
	protected Map<String,List<String>> allowedQueries;			
	private List<String> currentList;
	private Query query;
	
	abstract Query build();	
	protected abstract boolean isQueryTypeExists(String queryType);
	protected abstract boolean isAttrubuteExists(String queryType);
		

	
	
	private void addToCurrentList(String attributeName) {
		this.currentList.add(attributeName);
	}
	
	private void clearCurrentList() {
		this.currentList=new ArrayList<String>();
	}

	

	public Queryable withQueryableWordAttributes(String ...attributeNames) {

		
		clearCurrentList();
		
		for(String attributeName:attributeNames) {
			if(!isAttrubuteExists(attributeName)) {
				this.wordAttributes.add(attributeName);
				addToCurrentList(attributeName);				
			}
		}
		return this;
	}
	


	public Queryable withQueryableNumberAttributes(String ...attributeNames) {
		clearCurrentList();
		
		for(String attributeName:attributeNames) {
			if(!isAttrubuteExists(attributeName)) {
				this.numberAttributes.add(attributeName);
				addToCurrentList(attributeName);				
			}
		}
		return this;
	}
	
	

	public Queryable withQueryableVarCharAttributes(String ...attributeNames) {
		clearCurrentList();
		for(String attributeName:attributeNames) {
			if(!isAttrubuteExists(attributeName)) {
				this.varCharAttributes.add(attributeName);
				addToCurrentList(attributeName);				
			}
		}
		return this;		
	}
	
	
	
	


	public Queryable allowAllWordQueries() {
		for(String wordQuery: query.wordQueryTypes) {
			allowQueryTypeForAttributes(this.wordAttributes,wordQuery);
		}
		return this;	
		
	}

	public Queryable allowAllNumberQueries() {
		for(String numQuery: query.numberQueryTypes) {
			allowQueryTypeForAttributes(numberAttributes,numQuery);
		}
		return this;	
		
	}
	

	public Queryable allowAllVarCharQueries() {
		for(String varCharQuery: query.varCharQueryTypes) {
			allowQueryTypeForAttributes(numberAttributes,varCharQuery);
		}
		return this;	
		
	}
	

	
	

	public Queryable allowQueryTypeForAttributes(List<String> attributes,String queryType) {
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
		

		if(this.wordAttributes.contains(attribute) && this.query.wordQueryTypes.contains(queryType)) {
			return true;
		}
		
		if(this.numberAttributes.contains(attribute) && this.query.numberQueryTypes.contains(queryType)) {
			return true;
		}		
		
		if(this.varCharAttributes.contains(attribute) && this.query.varCharQueryTypes.contains(queryType)) {
			return true;
		}
		
		return false;
	}	
	
	public Queryable allowContains() {
		allowQueryTypeForAttributes(this.currentList, query.CONTAINS);
		return this;
	}
	public Queryable allowWith() {
		allowQueryTypeForAttributes(this.currentList, query.WITH);
		return this;
	}
	public Queryable allowStartsWith() {
		allowQueryTypeForAttributes(this.currentList,query.STARTS_WITH);
		return this;
	}
	public Queryable allowEndsWith() {
		allowQueryTypeForAttributes(this.currentList,query.ENDS_WITH);
		return this;
	}
	public Queryable allowPattern() {
		allowQueryTypeForAttributes(this.currentList,query.PATTERN);
		return this;
	}
	public Queryable allowEquals() {
		allowQueryTypeForAttributes(this.currentList,query.EQUALS);
		return this;
	}
	public Queryable allowAtMost() {
		allowQueryTypeForAttributes(this.currentList,query.ATMOST);
		return this;
	}
	public Queryable allowAtLeast() {
		allowQueryTypeForAttributes(this.currentList,query.ATLEAST);
		return this;
	}
	public Queryable allowWithin() {
		allowQueryTypeForAttributes(this.currentList,query.WITHIN);
		return this;
	};
	
	

}
