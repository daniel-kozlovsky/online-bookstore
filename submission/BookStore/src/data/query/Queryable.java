package data.access.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import data.access.DataAccessRequest;
import data.access.queries.Query.Generator;
import data.access.queries.fetchers.BookDataFetcher;
import data.access.queries.fetchers.CartDataFetcher;
import data.access.queries.fetchers.CustomerDataFetcher;
import data.access.queries.fetchers.PurchaseOrderDataFetcher;
import data.access.queries.fetchers.VisitorDataFetcher;
import data.beans.Bean;
import data.beans.Book;
import data.beans.Cart;
import data.beans.Customer;
import data.beans.Id;
import data.beans.PurchaseOrder;
import data.beans.Review;
import data.beans.Visitor;
import data.schema.BookSchema;
import data.schema.CartSchema;
import data.schema.CustomerSchema;
import data.schema.DataSchema;
import data.schema.PurchaseOrderSchema;
import data.schema.ReviewSchema;
import data.schema.VisitorSchema;

public interface Queryable<T extends Query> extends DataAccessRequest<T>{
	public String getQueryString();
	public boolean isLegalDataAccessReference(String tableName);
	public T withAscendingOrderOf();
	public T withDescendingOrderOf();
	public T withPageNumber(int pageNumber);
	public T withResultLimit(int resultLimit);
	public T includeAllAttributesInResultFromSchema(DataSchema dataSchema);
	public T includeAllAttributesInResultFromSchema();
	public T includeAttributeInResult(String attributeName);
	public T excludeAttributeInResult(String attributeName);
	public T excludeAttributeInResult(DataSchema dataSchema,String attributeName);
	public T includeAttributesInResults(String ...attributeNames);	

	


}
