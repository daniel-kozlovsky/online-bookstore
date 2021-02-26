package dao;

public interface DAO {
	public String[] getAttributeLabels();
	public Query newQuery();
	public Query newUpdate();
	public void retrieveQueryResults();
	

}
