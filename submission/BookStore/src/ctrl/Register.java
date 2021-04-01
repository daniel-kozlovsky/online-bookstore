package ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.beans.Customer;
import model.UserAuthenticationModel;

/**
 * Servlet implementation class Register
 */
@WebServlet(urlPatterns={"/Register", "/Register/*"})
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserAuthenticationModel UAuthModel;
	final String SIGNIN_TARGET = "/html/SignIn.jspx";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
    	UAuthModel = UserAuthenticationModel.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String givenName = request.getParameter("givenName");
		String surname = request.getParameter("surname");
		
		response.setContentType("application/text");
		PrintWriter out = response.getWriter();
		
		Customer customer = new Customer.Builder()
				.withEmail(email)
				.withGivenName(givenName)
				.withSurName(surname)
				.withUserName(username)
				.withPassword(password)
				.build();
		
		
		//validate variables
		List<String> errors = UAuthModel.validateRegister(username, password, email, givenName, surname);
		String responseText = "";
		if(!errors.isEmpty())
		{
			responseText = errors.toString();
		}
		else
		{
			UAuthModel.registerUser(customer);
			
			if(UAuthModel.isUserRegistered(username))
			{
				//continue to sign in page
				request.getRequestDispatcher(SIGNIN_TARGET).forward(request, response);
				return;
			}
			else
			{
				responseText = "Something went wrong. Please Try again later.";
				//error
			}
			
		}
		
		out.printf(responseText);
		out.flush();
		
	}

}
