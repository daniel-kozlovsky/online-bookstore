package model;

public class UserAuthenticationModel {

	private static UserAuthenticationModel instance;
	
	private UserAuthenticationModel()
	{
		
	}
	
	public static UserAuthenticationModel getInstance()
	{
		if(instance == null)
		{
			instance = new UserAuthenticationModel();
		}
		
		return instance;
	}
	
	public void logUserIn(String userName, String password)
	{
		
	}
	
	public void logUserOut(String userName)
	{
		
	}
	
	public void registerUser()
	{
		
	}
	
	
}
