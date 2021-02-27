package dao.accessors.queries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import dao.DataSchema;
import dao.accessors.DataAccessRequest;
import dao.accessors.DataAccessSetup;
import dao.accessors.DataAccessTypesSQL;

public class QuerySQL extends Query{
	
	private String limit = "50";


	
	private QuerySQL(String tableName) {
		this.tableName=tableName;
		this.attributesToIncludInResults= new ArrayList<String>();
		this.ascOrderOf="";
		this.descOrderOf="";
		this.dataAccessTypeTranslation=new HashMap<String, QueryString>();

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

	
	public static class SetupProperties extends DataAccessSetup{


		public SetupProperties(DataSchema dataSchema){
			super(dataSchema, new DataAccessTypesSQL());		
		}

		@Override
		public QuerySQL build() {
			// TODO Auto-generated method stub
			QuerySQL querySQL = new QuerySQL(this.tableName);
			querySQL.allowedAttributeDataAccessTypes=this.allowedAttributeDataAccessTypes;
			querySQL.varCharAttributes=this.varCharAttributes;
			querySQL.wordAttributes=this.wordAttributes;
			querySQL.numberAttributes=this.numberAttributes;
			return querySQL;
		}



		
	}









}
