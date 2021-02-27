package dao.accessors;

import java.util.Arrays;
import java.util.List;

public class DataAccessTypesSQL implements DataAccessTypes {

	
	public DataAccessTypesSQL() {
		
	}
	
	@Override
	public List<String> wordDataAccessTypes(){
		return Arrays.asList(CONTAINS, WITH, STARTS_WITH,ENDS_WITH,PATTERN,EQUALS); 	/*Enum for all word type queries*/
	}
	
	@Override
	public List<String> numberDataAccessTypes(){
		return Arrays.asList(CONTAINS, WITH, STARTS_WITH,ENDS_WITH,PATTERN,EQUALS); 	/*Enum for all word type queries*/
	}
	
	@Override
	public List<String> varCharDataAccessTypes(){
		return Arrays.asList(CONTAINS, WITH, STARTS_WITH,ENDS_WITH,PATTERN,EQUALS); 	/*Enum for all word type queries*/
	}
	
	@Override
	public List<String> objectDataAccessTypes(){
		return Arrays.asList(""); 	/*Enum for all object type queries*/
	}
}
