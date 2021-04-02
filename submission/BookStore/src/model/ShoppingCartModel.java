package model;

import java.util.Map;

import data.beans.Book;
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
	
	public double getTotalPrice(Cart cart)
	{
		double totalPrice = 0;
		
		for(Map.Entry<Book, Integer> entry : cart.getBooks().entrySet())
		{
			totalPrice += entry.getKey().getPrice() * entry.getValue();
		}
		
		return totalPrice;
		
	}
	
	public void updateBookQuantity(Cart cart, String isbn, int newQuantity)
	{
		
		for(Book b : cart.getBooks().keySet())
		{
			if(b.getISBN().equals(isbn))
			{
				cart.getBooks().put(b, newQuantity);
				break;
			}
		}
	}
	
	public void removeBook(Cart cart, String isbn)
	{
		for(Book b : cart.getBooks().keySet())
		{
			if(b.getISBN().equals(isbn))
			{
				cart.getBooks().remove(b);
				break;
			}
		}
	}
	
}
