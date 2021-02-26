package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Queryable {
	private String tableName;
	private List<String> wordAttributes;
	private List<String> numberAttributes;
	private List<String> varCharAttributes;
	private List<String> objectAttributes;
	
	private List<String> wordQueryTypes;
	private List<String> numberQueryTypes;
	private List<String> varCharQueryTypes;
	private List<String> objectQueryTypes;
	
	
	protected Map<String,List<String>> applicableQueries;			
	private List<String> currentList;
	private String[] keyWord;
	
	
	private void addToCurrentList(String attributeName) {
		this.currentList.add(attributeName);
	}
	
	private void clearCurrentList() {
		this.currentList=new ArrayList<String>();
	}

	

	public Queryable withQueryableWordAttributes(String ...attributeNames) {
		isAllowedQuerisSetForAttributes();
		clearCurrentList();
		for(String attributeName:attributeNames) {
			this.wordAttributes.add(attributeName);
			addToCurrentList(attributeName);
		}
		return this;
	}
	

	public Queryable withQueryableNumberAttributes(String ...attributeNames) {
		isAllowedQuerisSetForAttributes();
		clearCurrentList();
		for(String attributeName:attributeNames) {
			this.numberAttributes.add(attributeName);
			addToCurrentList(attributeName);
		}
		return this;
	}
	
	

	public Queryable withQueryableVarCharAttributes(String ...attributeNames) {
		isAllowedQuerisSetForAttributes();
		clearCurrentList();
		for(String attributeName:attributeNames) {
			this.varCharAttributes.add(attributeName);
			addToCurrentList(attributeName);
		}
		return this;		
	}
	
	

	public Queryable withQueryableObjectAttributes(String ...attributeNames) {
		isAllowedQuerisSetForAttributes();
		clearCurrentList();
		for(String attributeName:attributeNames) {
			this.objectAttributes.add(attributeName);
			addToCurrentList(attributeName);
		}
		return this;	
	}
	

	public Queryable allowAllWordQueries() {
		for(String wordQuery: wordQueryTypes) {
			addQueryTypeToAttributes(this.wordAttributes,wordQuery);
		}
		return this;	
		
	}

	public Queryable allowAllNumberQueries() {
		for(String numQuery: numberQueryTypes) {
			addQueryTypeToAttributes(numberAttributes,numQuery);
		}
		return this;	
		
	}
	

	public Queryable allowAllVarCharQueries() {
		for(String numQuery: numberQueryTypes) {
			addQueryTypeToAttributes(numberAttributes,numQuery);
		}
		return this;	
		
	}
	

	public Queryable allowAllObjectQueries() {
		for(String numQuery: numberQueryTypes) {
			addQueryTypeToAttributes(numberAttributes,numQuery);
		}
		return this;	
		
	}
	
	

	public void allowQueryTypeForAttributes(List<String> attributes,String queryType) {
		for(String attribute:attributes) {
			if(isAttributeAllowedQueryType(attribute,queryType)) {
				if(this.applicableQueries.get(attribute)==null)	this.applicableQueries.put(attribute, new ArrayList<String>());
				this.applicableQueries.get(attribute).add(queryType);
			}

		}
		
	}

	public void allowAttributesForQueryType(List<String> attributes,String queryType) {
		for(String attribute:attributes) {
			if(isAttributeAllowedQueryType(attribute,queryType)) {
				if(this.applicableQueries.get(attribute)==null)	this.applicableQueries.put(attribute, new ArrayList<String>());
				this.applicableQueries.get(attribute).add(queryType);
			}

		}
		
	}
	
	private void addQueryTypeToAttributes(List<String> attributes,String queryType) {
		for(String attribute:attributes) {
			if(isAttributeAllowedQueryType(attribute,queryType)) {
				if(this.applicableQueries.get(attribute)==null)	this.applicableQueries.put(attribute, new ArrayList<String>());
				this.applicableQueries.get(attribute).add(queryType);
			}

		}
		
	}
	
	
	
	protected boolean isAttributeExists(String attribute) {
		if(this.wordAttributes.contains(attribute)) {
			return true;
		}
		
		if(this.numberAttributes.contains(attribute)) {
			return true;
		}
		
		
		if(this.varCharAttributes.contains(attribute)) {
			return true;
		}
		
		if(this.objectAttributes.contains(attribute)) {
			return true;
		}
		return false;
	}
	
	protected boolean isAttributeAllowedQueryType(String attribute,String queryType) {
		if(!isQueryTypeExists(queryType)) return false;
		
		if(this.wordAttributes.contains(attribute) && this.wordQueryTypes.contains(queryType)) {
			return true;
		}
		
		if(this.numberAttributes.contains(attribute) && this.numberQueryTypes.contains(queryType)) {
			return true;
		}
		
		
		if(this.varCharAttributes.contains(attribute) && this.varCharQueryTypes.contains(queryType)) {
			return true;
		}
		
		if(this.objectAttributes.contains(attribute) && this.objectQueryTypes.contains(queryType)) {
			return true;
		}
		return false;
	}
	
	
	
	protected abstract boolean isQueryTypeExists(String queryType);
	
	private boolean isAllowedQuerisSetForAttributes() {
		if(!this.currentList.isEmpty()) {
			String prefixError="Warning query properties have not yet been set for the current listed ";
			String suffixError=", listing new attributes of a differnt type will erase the previous list, set allowed queries for attributes first!\n";
			
			if(wordAttributes.contains(this.currentList.indexOf(0))){
				if(wordQueryTypes.isEmpty()) {
					System.err.println(prefixError+"word attributes"+suffixError);
					return false;
				}
			}else if(numberAttributes.contains(this.currentList.indexOf(0))) {
				if(numberQueryTypes.isEmpty()) {
					System.err.println(prefixError+"number attributes"+suffixError);
					return false;
				}
				
			}else if(varCharAttributes.contains(this.currentList.indexOf(0))) {
				if(varCharQueryTypes.isEmpty()) {
					System.err.println(prefixError+"varChar attributes"+suffixError);
					return false;
				}
				
			}else if(objectAttributes.contains(this.currentList.indexOf(0))) {
				if(objectQueryTypes.isEmpty()) {
					System.err.println(prefixError+"object attributes"+suffixError);
					return false;
				}
			}
		}
		return true;
		
	}
	
	

	abstract Query build();
	abstract Queryable acceptContains();
	abstract Queryable acceptWith();
	abstract Queryable acceptStartsWith();
	abstract Queryable acceptEndsWith();
	abstract Queryable acceptPattern();
	abstract Queryable acceptEquals();
	abstract Queryable acceptAtMost();
	abstract Queryable acceptAtLeast();
	abstract Queryable acceptWithin();

}
