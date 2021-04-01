package model;

import data.beans.Cart;
import data.beans.Customer;
import data.dao.CustomerDAO;

public class ShoppingCartModel {
	
	private static ShoppingCartModel instance;
	CustomerDAO customerDAO;
	
	private ShoppingCartModel()
	{
		customerDAO = new CustomerDAO();
	}
	
	public static ShoppingCartModel getInstance()
	{
		if(instance == null)
		{
			instance = new ShoppingCartModel();
		}
		
		return instance;
	}
	
	
	public String createShoppingCartHTML(Cart cart)
	{
		String shoppingCartHTML = "";
		return shoppingCartHTML;
	}
	
}
