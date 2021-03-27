package data.fetcher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import data.beans.Book;
import data.beans.Cart;
import data.beans.Customer;
import data.beans.Id;
import data.beans.PurchaseOrder;
import data.beans.Visitor;
import data.query.Query;
import data.schema.CartSchema;
import data.schema.PurchaseOrderSchema;

public class PurchaseOrderDataFetcher  extends DataFetcher<PurchaseOrder>{




	public PurchaseOrderDataFetcher(Map<String, Set<String>> attributesToIncludInResults) {
		super(attributesToIncludInResults);
		// TODO Auto-generated constructor stub
	}



	private PurchaseOrderSchema schema=new PurchaseOrderSchema(); 
	
	@Override
	public PurchaseOrder resultSetToBean(ResultSet resultSet) {
		String prefix = isReferenceQuery()?schema.tableName()+Query.referenceSeparator:"";
		PurchaseOrder purchaseOrder = new PurchaseOrder.Builder().build();
				
		try {
			
			
			Book book = new Book.Builder().withId(new Id(resultSet.getString(prefix+schema.BOOK))).build();
			
			return new PurchaseOrder.Builder(purchaseOrder)
					.withId(new Id(resultSet.getString(prefix+schema.ID)))
					.withCreatedAtEpoch(resultSet.getLong(prefix+schema.CREATED_AT_EPOCH))
					.withBookAndAmount(book,resultSet.getInt(prefix+schema.AMOUNT))
					.build();		
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		System.err.println("Warning empty book, since resultSet could not produce book object");
		return new PurchaseOrder.Builder().build();
		
	}

}