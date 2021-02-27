package dao.accessors.queries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import dao.accessors.DataAccessRequest;
import dao.accessors.DataBaseAccessible;

public class QuerySQL extends Query{
	
	private final String tableName;
	private List<String> wordAttributes;
	private List<String> numberAttributes;
	private List<String> varCharAttributes;
	private List<String> objectAttributes;
	
	protected Map<String,List<String>> allowedQueries;			
	private List<String> currentList;
	

	private String limit = "50";
	
	final static List<String> wordQueryTypes=Arrays.asList(CONTAINS, WITH, STARTS_WITH,ENDS_WITH,PATTERN,EQUALS); 	/*Enum for all word type queries*/
	final static List<String> numQueryTypes=Arrays.asList(ATMOST,ATLEAST,WITHIN,EQUALS);	/*Enum for all number based queries*/
	final static List<String> varCharQueryTypes=Arrays.asList(CONTAINS, WITH, STARTS_WITH,ENDS_WITH,PATTERN,EQUALS); 	/*Enum for all word type queries*/

	
	private QuerySQL(String tableName) {
		this.tableName=tableName;
		this.wordAttributes= new ArrayList<String>();
		this.numberAttributes= new ArrayList<String>();
		this.varCharAttributes= new ArrayList<String>();
		this.objectAttributes= new ArrayList<String>();
		this.allowedQueries=new HashMap<String, List<String>>();

	}




	@Override
	public String renderQueryString() {
		String queryString="SELECT";
		if(this.attributesToIncludInResults.isEmpty()) {
			queryString+=" *";
		}else {			
			for(String attribute: this.attributesToIncludInResults) {

				queryString+=" "+attribute;
				if(this.attributesToIncludInResults.indexOf(attribute)<this.attributesToIncludInResults.size()-1) {
					queryString+=",";
				}	

			}
		}
		
		queryString+=" from "+this.tableName+" "+buildQueryParametersString();

		if(this.ascOrderOf!=null && !this.ascOrderOf.isEmpty() && (descOrderOf==null||descOrderOf.isEmpty())) {
			queryString+=" ORDER BY "+this.ascOrderOf+ " ASC";
		}else if(this.descOrderOf!=null && !descOrderOf.isEmpty() && (ascOrderOf==null||ascOrderOf.isEmpty())) {
			queryString+=" ORDER BY "+this.ascOrderOf+ " DESC";
		}
		
		queryString+=" fetch first "+this.limit+" rows only ";
		
		System.out.println(queryString);
		return queryString;
	}
	
	private String buildQueryParametersString() {
		String queryString="";
		int keyCount=0;
		if (this.dataAccessFormattedRequest.isEmpty()) {
			return "";
		}else {
			queryString+=" WHERE ";
			for(String attributeName : this.dataAccessFormattedRequest.keySet()) {
				for(Entry<String,String> entry : this.dataAccessFormattedRequest.get(attributeName).entrySet()) {		
				queryString+=entry.getValue();
					if(this.dataAccessFormattedRequest.get(attributeName).keySet().size()<this.dataAccessFormattedRequest.get(attributeName).size()-1) {
						queryString+=" AND ";
					}
				}
			}
		}
		System.out.println(queryString);
		return queryString;
	}






	@Override
	public Query includeAttributesInResults(String... attributeNames) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Query withAscendingOrderOf(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Query withDescendingOrderOf(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static class SetupProperties extends DataBaseAccessible{

		QuerySQL querySQL;
		
		SetupProperties(String tableName){
			this.tableName=tableName;
			QuerySQL querySQL=new QuerySQL(tableName);
			this.dataAccessRequest=querySQL;
			
		}

		@Override
		public DataAccessRequest build() {
			// TODO Auto-generated method stub
			this.querySQL.wordAttributes= this.wordAttributes;
			this.querySQL.numberAttributes= this.numberAttributes;
			this.querySQL.varCharAttributes= this.varCharAttributes;
			return querySQL;
		}

		@Override
		protected boolean isAttrubuteExists(String queryType) {
			// TODO Auto-generated method stub
			
			return this.querySQL.wordAttributes.contains(queryType)||this.querySQL.numberAttributes.contains(queryType)||this.querySQL.varCharAttributes.contains(queryType);
		}
		
		@Override
		protected boolean isQueryTypeExists(String queryType) {
			return querySQL.wordDataAccessRequestTypes.contains(queryType)||querySQL.numberDataAccessRequestTypes.contains(queryType)||querySQL.varCharDataAccessRequestTypes.contains(queryType);
		}


		
	}









}
