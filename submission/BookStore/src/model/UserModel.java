package model;

import java.util.List;

import data.beans.Customer;
import data.dao.CustomerDAO;
import data.dao.PurchaseOrderDAO;

public class UserModel {
	public static UserModel instance;
	private CustomerDAO user;
	private PurchaseOrderDAO order;
	
	public UserModel () {
		
	}
	
	public List<Customer> getCustomerOrders(String username, String passwd) {
		Customer s = user.loginCustomer(username, passwd);
		
		List<Customer> l = user.newQueryRequest()
								.includeAllAttributesInResultFromSchema()
								.queryAttribute()
								.whereCustomer()
								.isCustomer(s)
								.queryPurchaseOrder()
								.queryBook()
								.executeQuery()
								.executeCompilation()
								.compileCustomers();
			
		
		return l;
	}
	
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
		}
		return instance;
	}
}
