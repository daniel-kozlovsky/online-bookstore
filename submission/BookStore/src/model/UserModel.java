package model;

import java.util.List;

import data.beans.Book;
import data.beans.Customer;
import data.dao.CustomerDAO;
import data.dao.PurchaseOrderDAO;
import data.dao.ReviewDAO;
import data.dao.BookDAO;

public class UserModel {
	public static UserModel instance;
	private CustomerDAO user;
	private PurchaseOrderDAO order;
	private ReviewDAO review;
	private BookDAO book;
	
	public UserModel () {
		
	}
	
//	public Customer getCustomerOrders(String username, String passwd) throws Exception{
//		List<Customer> l = null;
//		
//		try {
//			Customer s = user.loginCustomer(username, passwd);
//			
//			l = user.newQueryRequest()
//									.includeAllAttributesInResultFromSchema()
//									.queryAttribute()
//									.whereCustomer()
//									.isCustomer(s)
//									.queryPurchaseOrder()
//									.queryBook()
//									.executeQuery()
//									.executeCompilation()
//									.compileCustomers();
//				
//		} catch (Exception e) {
//			throw new Exception("Couldn't find username and password in the database!");
//		}
//		return l.get(0);
//	}
	
	public void getOrder(String order_id, String username, String passwd) {
		Customer s = user.loginCustomer(username, passwd);
//		
//		user.newQueryRequest()
//			.includeAllAttributesInResultFromSchema()
//			.queryAttribute()
//			.whereCustomer()
//			.isCustomer(s)
//			.queryPurchaseOrder()
//			.queryAttribute()
//			.wherePurchaseOrder()
//			.queryPurchaseOrderAttribute()
//			.
		
//		order.newQueryRequest()
//			.includeAllAttributesInResultFromSchema()
//			.queryAttribute()
//			.wherePurchaseOrder()
//			.
		
	}
	

	
	//getInstance will return that ONE instance of the pattern 
	//with the the DAO objects initialized..
	public static UserModel getInstance()throws ClassNotFoundException{
		if (instance==null) {
			instance =new UserModel();
			instance.user = new CustomerDAO();
			instance.order = new PurchaseOrderDAO();
			instance.review = new ReviewDAO();
			instance.book = new BookDAO();
		}
		return instance;
	}
}
