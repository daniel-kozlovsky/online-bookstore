package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		if (this.attributeQueryStringRequests==null||this.attributeQueryStringRequests.isEmpty()) {
			return "";
		}else {
			queryString+=" WHERE ";
			for(String key : this.attributeQueryStringRequests.keySet()) {
				keyCount++;
				for(String parameter: this.attributeQueryStringRequests.get(key)) {
					queryString+=key+parameter;
					if(this.attributeQueryStringRequests.get(key).indexOf(parameter)<this.attributeQueryStringRequests.get(key).size()-1) {
						queryString+=" AND ";
					}
				}
				
				if(keyCount<this.attributeQueryStringRequests.keySet().size()) {
					queryString+=" AND ";
				}
			}
		}
		System.out.println(queryString);
		return queryString;
	}



	@Override
	public String renderMutationString() {
		// TODO Auto-generated method stub
		return null;
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

	

	
	public static class SetupProperties extends Queryable{


		
		SetupProperties(String tableName){
			
		}

		@Override
		QuerySQL build() {
			// TODO Auto-generated method stub
			QuerySQL query = new QuerySQL(this.tableName);
			query.wordAttributes= this.wordAttributes;
			query.numberAttributes= this.numberAttributes;
			query.varCharAttributes= this.varCharAttributes;
			query.objectAttributes= this.objectAttributes;
			return query;
		}

		@Override
		protected boolean isAttrubuteExists(String queryType) {
			// TODO Auto-generated method stub
			
			return false;
		}
		
		@Override
		protected boolean isQueryTypeExists(String queryType) {
			return wordQueryTypes.contains(queryType)||wordQueryTypes.contains(queryType)||wordQueryTypes.contains(queryType)||wordQueryTypes.contains(queryType);
		}


		
	}









}
