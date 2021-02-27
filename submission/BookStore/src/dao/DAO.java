package dao;

import java.util.List;
import java.util.Map;

import dao.accessors.queries.Query;

public interface DAO<T> {
	T getById(T t);
	T getById(String id);	
	Query newQueryRequest();	
	List<T> retrieveQueryResults();	
	Map<String,T> retrieveQueryResultsMap();	
	String retrieveQueryResultsAsJson();
}
