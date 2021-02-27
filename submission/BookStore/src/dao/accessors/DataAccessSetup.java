package dao.accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dao.DataSchema;

public abstract class DataAccessSetup{
	protected String tableName;
	protected List<String> wordAttributes;
	protected List<String> numberAttributes;
	protected List<String> varCharAttributes;
	protected List<String> objectAttributes;
	
	protected DataSchema dataSchema;
	
	protected DataAccessTypes dataAccessTypes;
	protected Map<String,List<String>> allowedAttributeDataAccessTypes;			
	private List<String> currentList;
	
	public abstract DataAccessRequest build();	
	
	
	
	
	protected DataAccessSetup(DataSchema dataSchema,DataAccessTypes dataAccessTypes) {
		this.dataSchema=dataSchema;
		this.wordAttributes=dataSchema.wordAttributeLabels();
		this.numberAttributes=dataSchema.numberAttributeLabels();
		this.varCharAttributes=dataSchema.varAttributeLabels();
		this.objectAttributes=dataSchema.objectAttributeLabels();
		this.dataAccessTypes=dataAccessTypes;
		this.tableName=dataSchema.tableName();		
	}
	
	
	

	
	
	

	public DataAccessSetup withAccessibleWordAttributes(String ...attributeNames) {		
		clearCurrentList();		
		for(String attributeName:attributeNames) {
			if(!_pre_isAttrubuteExistsInSchema(attributeName)) {
				this.wordAttributes.add(attributeName);
				addToCurrentList(attributeName);				
			}
		}
		return this;
	}

	public DataAccessSetup withAccessibleNumberAttributes(String ...attributeNames) {
		clearCurrentList();		
		for(String attributeName:attributeNames) {
			if(!_pre_isAttrubuteExistsInSchema(attributeName)) {
				this.numberAttributes.add(attributeName);
				addToCurrentList(attributeName);				
			}
		}
		return this;
	}
	
	

	public DataAccessSetup withAccessibleVarCharAttributes(String ...attributeNames) {
		clearCurrentList();
		for(String attributeName:attributeNames) {
			if(!_pre_isAttrubuteExistsInSchema(attributeName)) {
				this.varCharAttributes.add(attributeName);
				addToCurrentList(attributeName);				
			}
		}
		return this;		
	}
	
	
	
	


	public DataAccessSetup allowAllWordDataAccessTypes() {
		this.dataAccessTypes.wordDataAccessTypes().forEach(parameter->allowDataAccessTypesForAttributes(this.wordAttributes,parameter));
		return this;	
		
	}

	public DataAccessSetup allowAllNumberDataAccessTypes() {
		this.dataAccessTypes.numberDataAccessTypes().forEach(parameter->allowDataAccessTypesForAttributes(this.numberAttributes,parameter));
		return this;	
		
	}
	

	public DataAccessSetup allowAllVarCharDataAccessTypes() {
		this.dataAccessTypes.varCharDataAccessTypes().forEach(parameter->allowDataAccessTypesForAttributes(this.varCharAttributes,parameter));
		return this;		
	}
	

	
	

	public DataAccessSetup allowDataAccessTypesForAttributes(List<String> attributes,String dataAccessParameter) {
		if(!_pre_isLegalDataAccessTypeAndAttributeCombinations(attributes,dataAccessParameter))return this;		
		for(String attribute:attributes) {
			if(this.allowedAttributeDataAccessTypes.get(attribute)==null)	this.allowedAttributeDataAccessTypes.put(attribute, new ArrayList<String>());
			this.allowedAttributeDataAccessTypes.get(attribute).add(dataAccessParameter);
		}
		return this;		
	}
	
	
	
	public DataAccessSetup allowContains() {
		allowDataAccessTypesForAttributes(this.currentList, dataAccessTypes.CONTAINS);
		return this;
	}
	
	public DataAccessSetup allowWith() {
		allowDataAccessTypesForAttributes(this.currentList, dataAccessTypes.WITH);
		return this;
	}
	public DataAccessSetup allowStartsWith() {
		allowDataAccessTypesForAttributes(this.currentList,dataAccessTypes.STARTS_WITH);
		return this;
	}
	public DataAccessSetup allowEndsWith() {
		allowDataAccessTypesForAttributes(this.currentList,dataAccessTypes.ENDS_WITH);
		return this;
	}
	public DataAccessSetup allowPattern() {
		allowDataAccessTypesForAttributes(this.currentList,dataAccessTypes.PATTERN);
		return this;
	}
	public DataAccessSetup allowEquals() {
		allowDataAccessTypesForAttributes(this.currentList,dataAccessTypes.EQUALS);
		return this;
	}
	public DataAccessSetup allowAtMost() {
		allowDataAccessTypesForAttributes(this.currentList,dataAccessTypes.ATMOST);
		return this;
	}
	public DataAccessSetup allowAtLeast() {
		allowDataAccessTypesForAttributes(this.currentList,dataAccessTypes.ATLEAST);
		return this;
	}
	public DataAccessSetup allowWithin() {
		allowDataAccessTypesForAttributes(this.currentList,dataAccessTypes.WITHIN);
		return this;
	};
	
	//contracts	
	public boolean _pre_isLegalDataAccessTypeAndAttributeCombinations(List<String> attributes,String dataAccessParameter) {
		return attributes.stream()
				.filter(attribute->_pre_isAttributeAndDataAccessTypeLegal(attribute,dataAccessParameter))
				.collect(Collectors.toList())
				.isEmpty();
	}
	
	protected boolean _pre_isAttributeAndDataAccessTypeLegal(String attribute,String dataAccessParameter) {
		if(
				!_pre_isDataAccessTypeExist(dataAccessParameter)
				||
				!_pre_isAttrubuteExistsInSchema(attribute)) {
			return false;
		}
		

		if(this.wordAttributes.contains(attribute) && this.dataAccessTypes.wordDataAccessTypes().contains(dataAccessParameter)) {
			return true;
		}
		
		if(this.numberAttributes.contains(attribute) && this.dataAccessTypes.numberDataAccessTypes().contains(dataAccessParameter)) {
			return true;
		}		
		
		if(this.varCharAttributes.contains(attribute) && this.dataAccessTypes.varCharDataAccessTypes().contains(dataAccessParameter)) {
			return true;
		}
		
		System.err.println("Warning attribute : "+attribute+" and data access parameter: "+dataAccessParameter+" are not a legal combination");		
		return false;
	}
	
	private boolean _pre_isDataAccessTypeExist(String parameter) {
		boolean result=	!dataAccessTypes.wordDataAccessTypes().contains(parameter)
							&&
						!dataAccessTypes.numberDataAccessTypes().contains(parameter)
							&&
						!dataAccessTypes.varCharDataAccessTypes().contains(parameter);
		if(!result)System.err.println("Warning data access parameter : "+parameter+" does not exist");
	
		return true;
	}

	
	public boolean _pre_isAttrubuteExistsInSchema(String attribute) {
		boolean result=!dataSchema.wordAttributeLabels().contains(attribute)
							||
						!dataSchema.numberAttributeLabels().contains(attribute)
							||
						!dataSchema.varAttributeLabels().contains(attribute)
							||
						!dataSchema.objectAttributeLabels().contains(attribute);
		if(!result)System.err.println("Warning attribute : "+attribute+" does not exist in schema");
		return true;		
	}
		

	//helpers
	private void addToCurrentList(String attributeName) {
		this.currentList.add(attributeName);
	}
	
	
	
	private void clearCurrentList() {
		this.currentList=new ArrayList<String>();
	}
	
	
}
