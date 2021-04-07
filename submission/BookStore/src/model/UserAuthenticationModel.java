package model;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpSession;

import data.beans.Customer;
import data.dao.CartDAO;
import data.dao.CustomerDAO;

public class UserAuthenticationModel {

	private static UserAuthenticationModel instance;
	//Customer currentCustomer;
	CustomerDAO customerDAO;
	CartDAO cartDAO;
	
	private UserAuthenticationModel()
	{
		customerDAO = new CustomerDAO();
		cartDAO = new CartDAO();
	}
	
	public static UserAuthenticationModel getInstance()
	{
		if(instance == null)
		{
			instance = new UserAuthenticationModel();
		}
		
		return instance;
	}
	
	
	public List<String> validateSignIn(String username, String password)
	{
		List<String> errors = new ArrayList<String>();
		
		if(username.length() <= 0)
		{
			errors.add("Username cannot be empty");
		}
		if(password.length() <= 0)
		{
			errors.add("Password cannot be empty");
		}
		return errors;
	}
	
	public List<String> validateRegister(Customer customer)
	{
		List<String> errors = new ArrayList<String>();
		
		if(customer.getUserName().length() <= 0)
		{
			errors.add("Username cannot be empty");
		}
		if(customer.getPassword().length() <= 0)
		{
			errors.add("Password cannot be empty");
		}
		if(customer.getEmail().length() <= 0)
		{
			errors.add("Email cannot be empty");
		}
		if(customer.getGivenName().length() <= 0)
		{
			errors.add("Given name cannot be empty");
		}
		if(customer.getSurName().length() <= 0)
		{
			errors.add("Surname cannot be empty");
		}
		
//		if(customer.getAddress().getNumber().length() <= 0)
//		{
//			errors.add("Street number cannot be empty");
//		}
//		if(customer.getAddress().length() <= 0)
//		{
//			errors.add("Password cannot be empty");
//		}
//		if(customer.getAddress().length() <= 0)
//		{
//			errors.add("Email cannot be empty");
//		}
//		if(customer.getAddress().length() <= 0)
//		{
//			errors.add("Given name cannot be empty");
//		}
//		if(customer.getAddress().length() <= 0)
//		{
//			errors.add("Surname cannot be empty");
//		}if(customer.getUserName().length() <= 0)
//		{
//			errors.add("Username cannot be empty");
//		}
//		if(customer.getPassword().length() <= 0)
//		{
//			errors.add("Password cannot be empty");
//		}
//		if(customer.getEmail().length() <= 0)
//		{
//			errors.add("Email cannot be empty");
//		}
//		if(customer.getGivenName().length() <= 0)
//		{
//			errors.add("Given name cannot be empty");
//		}
//		if(customer.getSurName().length() <= 0)
//		{
//			errors.add("Surname cannot be empty");
//		}
		
		return errors;
	}
	
	public Customer logUserIn(String username, String password)
	{
		return customerDAO.loginCustomer(username, password);
	}
	
	public void logUserOut(HttpSession session)
	{
		session.setAttribute("customer", null);
	}
	
	public void registerUser(Customer newCustomer)
	{
		customerDAO.newUpdateRequest()
			.requestNewCustomerInsertion()
			.insertCustomerWithGivenName(newCustomer.getGivenName())
			.insertCustomerWithSurName(newCustomer.getSurName())
			.insertCustomerWithUserName(newCustomer.getUserName())
			.insertCustomerWithPassWord(newCustomer.getPassword())
			.insertCustomerWithEmail(newCustomer.getEmail())
			.insertCustomerWithStreetNumber(newCustomer.getAddress().getNumber())
			.insertCustomerWithStreet(newCustomer.getAddress().getStreet())
			.insertCustomerWithPostalCode(newCustomer.getAddress().getPostalCode())
			.insertCustomerWithCity(newCustomer.getAddress().getCity())
			.insertCustomerWithProvince(newCustomer.getAddress().getProvince())
			.insertCustomerWithCountry(newCustomer.getAddress().getCountry())
			.executeCustomerInsertion();
		
	}
	
	public boolean isUserRegistered(String username)
	{
		boolean isRegistered = false;
		
		List<Customer> customers = customerDAO.newQueryRequest()
			.includeCustomerUserNameInResult()
			.queryAttribute()
			.whereCustomerUserName()
			.varCharEquals(username)
			.executeQuery()
			.compileCustomers();
		
		if(!customers.isEmpty())
		{
			isRegistered = true;
		}
		
		return isRegistered;
	}
	
	
}
