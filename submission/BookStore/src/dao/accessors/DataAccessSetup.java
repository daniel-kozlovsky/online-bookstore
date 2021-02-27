package dao.accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dao.DataSchema;

public abstract class DataAccessSetup<T extends DataAccessSetup>{
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
	
	
	

	
	
	

	public T withAccessibleWordAttributes(String ...attributeNames) {		
		clearCurrentList();		
		for(String attributeName:attributeNames) {
			if(!_pre_isAttrubuteExistsInSchema(attributeName)) {
				this.wordAttributes.add(attributeName);
				addToCurrentList(attributeName);				
			}
		}
		return (T) this;
	}

	public T withAccessibleNumberAttributes(String ...attributeNames) {
		clearCurrentList();		
		for(String attributeName:attributeNames) {
			if(!_pre_isAttrubuteExistsInSchema(attributeName)) {
				this.numberAttributes.add(attributeName);
				addToCurrentList(attributeName);				
			}
		}
		return (T) this;
	}
	
	

	public T withAccessibleVarCharAttributes(String ...attributeNames) {
		clearCurrentList();
		for(String attributeName:attributeNames) {
			if(!_pre_isAttrubuteExistsInSchema(attributeName)) {
				this.varCharAttributes.add(attributeName);
				addToCurrentList(attributeName);				
			}
		}
		return (T) this;		
	}
	
	
	
	


	public T allowAllWordDataAccessTypes() {
		this.dataAccessTypes.wordDataAccessTypes().forEach(parameter->allowDataAccessTypesForAttributes(this.wordAttributes,parameter));
		return (T) this;	
		
	}

	public T allowAllNumberDataAccessTypes() {
		this.dataAccessTypes.numberDataAccessTypes().forEach(parameter->allowDataAccessTypesForAttributes(this.numberAttributes,parameter));
		return (T) this;	
		
	}
	

	public T allowAllVarCharDataAccessTypes() {
		this.dataAccessTypes.varCharDataAccessTypes().forEach(parameter->allowDataAccessTypesForAttributes(this.varCharAttributes,parameter));
		return (T) this;		
	}
	

	
	

	public T allowDataAccessTypesForAttributes(List<String> attributes,String dataAccessParameter) {
		if(!_pre_isLegalDataAccessTypeAndAttributeCombinations(attributes,dataAccessParameter))return (T) this;		
		for(String attribute:attributes) {
			if(this.allowedAttributeDataAccessTypes.get(attribute)==null)	this.allowedAttributeDataAccessTypes.put(attribute, new ArrayList<String>());
			this.allowedAttributeDataAccessTypes.get(attribute).add(dataAccessParameter);
		}
		return (T) this;		
	}
	
	
	
	public T allowContains() {
		allowDataAccessTypesForAttributes(this.currentList, dataAccessTypes.CONTAINS);
		return (T) this;
	}
	
	public T allowWith() {
		allowDataAccessTypesForAttributes(this.currentList, dataAccessTypes.WITH);
		return (T) this;
	}
	public T allowStartsWith() {
		allowDataAccessTypesForAttributes(this.currentList,dataAccessTypes.STARTS_WITH);
		return (T) this;
	}
	public T allowEndsWith() {
		allowDataAccessTypesForAttributes(this.currentList,dataAccessTypes.ENDS_WITH);
		return (T) this;
	}
	public T allowPattern() {
		allowDataAccessTypesForAttributes(this.currentList,dataAccessTypes.PATTERN);
		return (T) this;
	}
	public T allowEquals() {
		allowDataAccessTypesForAttributes(this.currentList,dataAccessTypes.EQUALS);
		return (T) this;
	}
	public T allowAtMost() {
		allowDataAccessTypesForAttributes(this.currentList,dataAccessTypes.ATMOST);
		return (T) this;
	}
	public T allowAtLeast() {
		allowDataAccessTypesForAttributes(this.currentList,dataAccessTypes.ATLEAST);
		return (T) this;
	}
	public T allowWithin() {
		allowDataAccessTypesForAttributes(this.currentList,dataAccessTypes.WITHIN);
		return (T) this;
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
