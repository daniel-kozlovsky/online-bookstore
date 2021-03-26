package data.access.queries.fetchers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import data.access.queries.DataAccessString;
import data.beans.Bean;
import data.beans.Book;

public abstract class DataFetcher<T extends Bean> {

	protected Map<String,Set<String>> attributesToIncludInResults;
	
	DataFetcher(Map<String,Set<String>> attributesToIncludInResults) {
		this.attributesToIncludInResults=attributesToIncludInResults;
	}

	public abstract T resultSetToBean(ResultSet resultSet);
	
	public boolean isReferenceQuery() {
		return attributesToIncludInResults.keySet().size()>1;
	}

	
}
