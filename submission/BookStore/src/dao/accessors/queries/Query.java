package dao.accessors.queries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import dao.DataSchema;
import dao.accessors.DataAccessRequest;

import java.util.Queue;


/**
 * Flexible mechanism to submit queries. Query can be set up with several properties to specify
 * what type of queries are allowed for each attribute. Only the allowed query parameters can be submitted for any given attribute
 * Queries can easily be built on demand to allow for modular and flexible requests.
*/
public abstract class Query<T extends Query> extends DataAccessRequest<T>{

	public abstract String renderQueryString();
	public abstract Query includeAttributesInResults(String ...attributeNames);
	public abstract Query withAscendingOrderOf(String attributeName);	
	public abstract Query withDescendingOrderOf(String attributeName);
	
	protected String tableName;
	
	protected String ascOrderOf;/*order of query results ascending*/
	protected String descOrderOf;/*order of query results descending*/
	
	
	protected List<String> attributesToIncludInResults; 	/*Attributes that will be received after a query*/

	public abstract String toJson();

	
	public T resultContainsAttributes(String ...attributeNames) {
		for(String attributeName:attributeNames) {
			if(_pre_isAttributeAccessAllowed(attributeName)) {
				this.attributesToIncludInResults.add(attributeName);
			}
		}
		return (T) this;
	}
	
	public boolean _pre_isAttributeAccessAllowed(String attributeName) {
		boolean result=this.allowedAttributeDataAccessTypes.keySet().contains(attributeName);
		if(!result) System.err.println("Warning: requested results to include '"+attributeName+"' which do not exist in the schema, and will be left out of the results");
		return result;
	}

	
	public Query requestNewQuery() {
		clearQueryCache();
		return this;
	}
	


	
	
	
	
}
	


