package dao;

import dao.accessors.queries.Query;

public interface DAO {
	public String[] getAttributeLabels();
	public Query newQuery();
	public Query newUpdate();
	public void retrieveQueryResults();
	

}
