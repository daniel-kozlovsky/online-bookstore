package model;

import javax.servlet.http.HttpServletRequest;

import data.dao.BookDAO;
import data.dao.PurchaseOrderDAO;
import data.dao.ReviewDAO;

public class PurchaseOrderModel {

	public static PurchaseOrderModel instance;
	private PurchaseOrderDAO purchaseOrderDAO;
	
	public boolean isCustomerLoggedIn(HttpServletRequest request) {
		return true;		
	}
}
