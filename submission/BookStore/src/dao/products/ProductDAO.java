package dao.products;

import java.util.List;
import java.util.Map;

import dao.accessors.queries.Query;

public class ProductDAO {
	//by id
	//flexible
	//
	//
	
	ProductDAO(){
		
	}
	
	
	Product getById(Product product) {
		return null;
	}
	
	Product getById(String id) {
		return null;
	}


	Query query;
	
	
	Query newQueryRequest() {
		return query;
	}
	
	List<Product> retrieveQueryResults() {
		return null;
	}
	
	
	
	Map<String,Product> retrieveQueryResultsMap() {
		return null;
	}
	
	
	List<Product> retrieveQueryResultsAsJson() {
		return null;
	}
	
	
	
	
	
}
