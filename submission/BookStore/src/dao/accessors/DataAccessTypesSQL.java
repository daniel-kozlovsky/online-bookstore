package dao.accessors;

import java.util.Arrays;
import java.util.List;

public class DataAccessTypesSQL implements DataAccessTypes {
	protected final static String CONTAINS="contains";
	protected final static String WITH="with";
	protected final static String STARTS_WITH="startsWith";
	protected final static String ENDS_WITH="endsWith";
	protected final static String PATTERN="pattern";
	protected final static String EQUALS="equals";
	protected final static String ATMOST="to";
	protected final static String ATLEAST="from";
	protected final static String WITHIN="within";
	
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
