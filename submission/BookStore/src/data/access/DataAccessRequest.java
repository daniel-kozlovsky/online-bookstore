package data.access;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import data.access.queries.DataAccessString;
import data.access.queries.fetchers.BookDataFetcher;
import data.access.queries.fetchers.CartDataFetcher;
import data.access.queries.fetchers.CustomerDataFetcher;
import data.access.queries.fetchers.DataFetcher;
import data.access.queries.fetchers.PurchaseOrderDataFetcher;
import data.access.queries.fetchers.VisitorDataFetcher;
import data.beans.Bean;
import data.schema.BookSchema;
import data.schema.CartSchema;
import data.schema.CustomerSchema;
import data.schema.DataSchema;
import data.schema.PurchaseOrderSchema;
import data.schema.ReviewSchema;
import data.schema.VisitorSchema;

import java.util.Queue;
import java.util.Set;


/**
 * Flexible mechanism to submit queries. Query can be set up with several properties to specify
 * what type of queries are allowed for each attribute. Only the allowed query parameters can be submitted for any given attribute
 * Queries can easily be built on demand to allow for modular and flexible requests.
*/
public interface DataAccessRequest<T extends DataAccessRequest>{	
	//Query Request properties

//	List<DataAccessString> getReferenceDataAccessString(String otherTableName);
//	Map<String, Set<String>> getReferenceRules();
	
}
