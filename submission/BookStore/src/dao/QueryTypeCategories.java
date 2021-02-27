package dao;

public class QueryTypeCategories {
	final static String CONTAINS="contains";
	final static String WITH="with";
	final static String STARTS_WITH="startsWith";
	final static String ENDS_WITH="endsWith";
	final static String PATTERN="pattern";
	final static String EQUALS="equals";
	final static String ATMOST="to";
	final static String ATLEAST="from";
	final static String WITHIN="within";	
	public static final String[] queryLabels= {CONTAINS,WITH,STARTS_WITH,ENDS_WITH,PATTERN,EQUALS,ATMOST,ATLEAST,WITHIN};


}
