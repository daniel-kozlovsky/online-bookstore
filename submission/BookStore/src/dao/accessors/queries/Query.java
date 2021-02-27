package dao.accessors.queries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import dao.accessors.DataAccessRequest;

import java.util.Queue;


/**
 * Flexible mechanism to submit queries. Query can be set up with several properties to specify
 * what type of queries are allowed for each attribute. Only the allowed query parameters can be submitted for any given attribute
 * Queries can easily be built on demand to allow for modular and flexible requests.
*/
public abstract class Query extends DataAccessRequest{

	public abstract String renderQueryString();
	public abstract Query includeAttributesInResults(String ...attributeNames);
	public abstract Query withAscendingOrderOf(String attributeName);	
	public abstract Query withDescendingOrderOf(String attributeName);
	
	protected String tableName;
	protected Map<String,List<String>> queryRules;	
	protected Map<String,List<String>> allowedQueries;	
	protected String ascOrderOf;/*order of query results ascending*/
	protected String descOrderOf;/*order of query results descending*/
	
	
	protected List<String> attributesToIncludInResults; 	/*Attributes that will be received after a query*/



	
	public Query resultContainsAttributes(String ...attributeNames) {
		for(String attributeName:attributeNames) {
			if(this.allowedDataAccessRequests.keySet().contains(attributeName)) {
				this.attributesToIncludInResults.add(attributeName);
			}else {
				System.err.println("Warning: requested results to include '"+attributeName+"' which do not exist in the schema, and will be left out of the results");
			}
		}
		return this;
	}

	
	public Query requestNewQuery() {
		clearQueryCache();
		return this;
	}
	


	
	
	
	
}
	


