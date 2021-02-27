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

}
