package dao;

import java.util.Map;

public interface DataSchema {
	Map<String,String> wordAttributeLabels();//=Arrays.asList(CONTAINS, WITH, STARTSWITH,ENDSWITH,PATTERN,EQUALS); 	/*Enum for all word type queries*/
	Map<String,String> numberAttributeLabels();//=Arrays.asList(ATMOST,ATLEAST,WITHIN,EQUALS);	/*Enum for all number based queries*/
	Map<String,String> varAttributeLabels();//=Arrays.asList(CONTAINS, WITH, STARTSWITH,ENDSWITH,PATTERN,EQUALS); 	/*Enum for all word type queries*/
	Map<String,String> objectAttributeLabels();//=Arrays.asList(CONTAINS, WITH, STARTSWITH,ENDSWITH,PATTERN,EQUALS); 	/*Enum for all word type queries*/
	Map<String,String> getAttributeLabels();
	String getAttributeLabel(String attributeName);
}
