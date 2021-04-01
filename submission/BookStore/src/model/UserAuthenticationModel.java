package model;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpSession;

import data.beans.Customer;
import data.dao.CustomerDAO;

public class UserAuthenticationModel {

	private static UserAuthenticationModel instance;
	//Customer currentCustomer;
	CustomerDAO customerDAO;
	
	private UserAuthenticationModel()
	{
		customerDAO = new CustomerDAO();
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
	
	public List<String> validateRegister(String username, String password, String email, String givenName, String surname)
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
		if(email.length() <= 0)
		{
			errors.add("Email cannot be empty");
		}
		if(givenName.length() <= 0)
		{
			errors.add("Given name cannot be empty");
		}
		if(surname.length() <= 0)
		{
			errors.add("Surname cannot be empty");
		}
		
		return errors;
	}
	
	public void logUserIn(HttpSession session, String username, String password)
	{
		Customer c = customerDAO.loginCustomer(username, password);
		session.setAttribute("customer", c);
	}
	
	public void logUserOut(HttpSession session, String userName)
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
