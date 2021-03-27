package data.schema;

import java.util.Arrays;
import java.util.List;



public abstract class DataSchema {
	protected String TABLE_NAME; 
	protected String[] ATTRIBUTE_LABELS; 
	protected String[] WORD_ATTRIBUTE_LABELS; 
	protected String[] NUMBER_ATTRIBUTE_LABELS; 
	protected String[] VARCHAR_ATTRIBUTE_LABELS; 
	protected String[] OBJECT_ATTRIBUTE_LABELS; 
	
	public final String tableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}
	
	protected DataSchema(){
		TABLE_NAME=""; 
		ATTRIBUTE_LABELS=new String[0]; 
		WORD_ATTRIBUTE_LABELS=new String[0]; 
		NUMBER_ATTRIBUTE_LABELS=new String[0]; 
		VARCHAR_ATTRIBUTE_LABELS=new String[0]; 
		OBJECT_ATTRIBUTE_LABELS=new String[0]; 
	}
	
	public final List<String> wordAttributeLabels() {
		// TODO Auto-generated method stub
		return Arrays.asList(WORD_ATTRIBUTE_LABELS);
	}
	public final List<String> numberAttributeLabels() {
		// TODO Auto-generated method stub
		return Arrays.asList(NUMBER_ATTRIBUTE_LABELS);
	}
	public final List<String> varCharAttributeLabels() {
		// TODO Auto-generated method stub
		return Arrays.asList(VARCHAR_ATTRIBUTE_LABELS);
	}
	public final List<String> objectAttributeLabels() {
		// TODO Auto-generated method stub
		return Arrays.asList(OBJECT_ATTRIBUTE_LABELS);
	}
	public final List<String> getAttributeLabels() {
		// TODO Auto-generated method stub
		return Arrays.asList(ATTRIBUTE_LABELS);
	}

}
