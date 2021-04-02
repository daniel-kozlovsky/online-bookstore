package ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserAuthenticationModel;

/**
 * Servlet implementation class Login
 */
@WebServlet(urlPatterns={"/SignIn", "/SignIn/*"})
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserAuthenticationModel UAuthModel;  
	final String MAIN_PAGE_TARGET = "/BookStore/MainPage";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignIn() {
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
		
		response.setContentType("application/text");
		PrintWriter out = response.getWriter();
		//validate variables
		List<String> errors = UAuthModel.validateSignIn(username, password);
		String responseText = "";
		if(!errors.isEmpty())
		{
			responseText = errors.toString();
		}
		else
		{
			//log user in
			UAuthModel.logUserIn(request.getSession(), username, password);
			//go to main page
			response.sendRedirect(MAIN_PAGE_TARGET);
		}
		
		out.printf(responseText);
		out.flush();
		
	}

}
