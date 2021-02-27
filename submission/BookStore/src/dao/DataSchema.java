package dao;

import java.lang.reflect.Field;
import java.util.List;


public interface DataSchema {
	abstract String tableName();
	abstract List<String> wordAttributeLabels();//=Arrays.asList(CONTAINS, WITH, STARTSWITH,ENDSWITH,PATTERN,EQUALS); 	/*Enum for all word type queries*/
	abstract List<String> numberAttributeLabels();//=Arrays.asList(ATMOST,ATLEAST,WITHIN,EQUALS);	/*Enum for all number based queries*/
	abstract List<String> varAttributeLabels();//=Arrays.asList(CONTAINS, WITH, STARTSWITH,ENDSWITH,PATTERN,EQUALS); 	/*Enum for all word type queries*/
	abstract List<String> objectAttributeLabels();//=Arrays.asList(CONTAINS, WITH, STARTSWITH,ENDSWITH,PATTERN,EQUALS); 	/*Enum for all word type queries*/
	abstract List<String> getAttributeLabels();
	abstract String getAttributeLabel(String attributeName);
	
//	
//	public DataSchema() {
//		
//		for(Field field:this.getClass().getDeclaredFields()) {
//			field.getName();
//			this.getClass().getF.getField(field.getName())
//		}
//		assert constructorPostcondition();
//	}
//	
//	public boolean constructorPostcondition() {
//		for(Field field:this.getClass().getDeclaredFields()) {
//			field.getName();
//			if(!wordAttributeLabels.contains(field.getName())
//					||
//				!numberAttributeLabels.contains(field.getName())
//					||
//				!varAttributeLabels.contains(field.getName())
//					||
//				!objectAttributeLabels.contains(field.getName()))
//				return false;
//		}
//	}
	
}
