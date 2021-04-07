package ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.beans.Customer;
import data.dao.CustomerDAO;

/**
 * Servlet implementation class PurchaseOrder
 */
@WebServlet("/PurchaseOrder")
public class PurchaseOrderPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String loginSignUpPage="SignIn.jspx";
	private static final String cartPage="Cart.jspx";
	private static final String emptyCartCheckoutPage="/html/EmptyPurchaseOrder.html";
	private static final String confirmationCheckoutPage="/html/PurchaseOrderConfirmation.html";
	private static final String customerInfoStage="custInfoStage";
	private static final String reviewStage="reviewStage";
	private static final String confirmationStage="confirmationStage";
	private static final String checkoutStageKeyName="stage";
	
	private static final String poSubmissionAjax="stage";
	
	private static final String emptyCartStatus="emptyCart";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchaseOrderPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		
		

	}
	
	/*
	 * G. Payment Page
		The visitor can
		• UC P1: either log into their account with a password, or create a new account.
		• UC P2: for a new account they enter their account name, password, and default billing and
		shipping information. The new account is submitted to the Order Processing service.
		• UC P3: to submit their order, they verify their billing and shipping information, and enter in their
		credit card number.
		• UC P4: “Confirm order” button
		Note: You should hard code that every 3rd request is denied on your website. If the order is approved, you
		should display “Order Successfully Completed.” If it is denied, you should display “Credit Card
		Authorization Failed.”
	 * 
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());

//	      out.write("{\"message\":\"THIS ASYN MESSAGE\"}");
//	      
//	      if(isCheckoutCartEmpty()) {
//	    	  request.getRequestDispatcher("/html/PurchaseOrder.html").forward(request, response);	    	
//	      }else if(!isCustomerLoggedIn(request)) {
//	    	  this.getServletContext().getRequestDispatcher(loginSignUpPage).forward(request, response);
//	      }else {
//	    	  if(isCustomerInfoStage()) {
//	    		  //return customer credit card
//	    		  //return customer address
//	    	  }else if(isCheckoutReviewStage()) {
//	    		  //return cart json
//	    		  //with totals
//	    	  }else if(isCheckoutCompleteStage()) {
//	    		  //
//	    	  }
//	      }
  	  if(request.getAttribute("error")!=null) {
		  String custJson=getCustomerJson();
	      response.setContentType("application/json");
	      PrintWriter out = response.getWriter();
	      out.flush();
	      out.printf("{\"error\":\""+request.getAttribute("error")+"\","+custJson.substring(1,custJson.length()));
	      out.close();
	  }else  if(isAjaxRequest(request)) {
//	    	  Customer customer=getCustomer();
	    	  

		    	  System.out.println("ajax request recevied");
			      response.setContentType("application/json");
			      PrintWriter out = response.getWriter();
			      out.flush();
			      out.printf(getCustomerJson()); 
			      out.close();
	    	  

//		      if(isCheckoutCartEmpty()) {
		    	  
//		    	  out.printf(statusJson(emptyCartStatus));
//		      }
		     
	      }else {
	    	  request.getRequestDispatcher("/html/PurchaseOrder.html").forward(request, response);
//		      if(isCheckoutCartEmpty()) {		    	  
//		    	  request.getRequestDispatcher(emptyCartCheckoutPage).forward(request, response);	
//		      }else {
//		    	  request.getRequestDispatcher("/html/PurchaseOrder.html").forward(request, response);	   
//		      }
	    	  
	      }
	      
	      
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		
		
		if(request.getParameter("submitPurchaseOrder")!=null && !request.getParameter("submitPurchaseOrder").equals("true")) {
			if(isValidPurchaseOrder()) {
				request.getRequestDispatcher(confirmationCheckoutPage).forward(request, response);	
			}
				
		}else {
			request.setAttribute("error", "error from respons obj");
//			doGet(request,response);
//			request.getRequestDispatcher("/html/PurchaseOrder.html").forward(request, response);
			PrintWriter out = response.getWriter();
			out.flush();
			   out.println("<meta http-equiv='refresh' content='3;URL=/BookStore/PurchaseOrder'>");//redirects after 3 seconds
			   out.println("<p style='color:red;'>User or password incorrect!</p>");
			   out.close();
		}
		
	}
	
	private boolean isCustomerLogged(HttpServletRequest request) {
		return true;
	}
	
	private boolean isAjaxRequest(HttpServletRequest request) {
		return request.getParameter("ajax") !=null && request.getParameter("ajax").equals("true");
	}
	
	private boolean isValidPurchaseOrder() {
		return false;
	}
	
	private String statusJson(String status) {
		return "{\"status\":\""+status+"\"}";
	}
	
	private boolean isCustomerInfoStage() {
		return true;
	}
	private boolean isCheckoutReviewStage() {
		return true;
	}
	private boolean isCheckoutCompleteStage() {
		return true;
	}
	
	private boolean isCheckoutCartEmpty() {
		return true;
	}
	
	private String getCustomerJson() {
		CustomerDAO user =new CustomerDAO();
		String username="WRitter163";
		String passwd = "Walterpassword";
//		Customer s = user.loginCustomer(username, passwd).toJson().replaceAll("creditCardNumber: \"\\G", "asdf");
//		Pattern creditCardPattern = Pattern.compile("'([0-9]+)',[0-9]+,[0-9.]+,[0-9]+,[0-9]+\\)");//. represents single character  
//		Matcher creditCardMatcher = creditCardPattern.matcher(s.toJson());  
//		while(creditCardMatcher.find()) {
//			creditCardMatcher.
//		}
//		String json="{ creditCardType: \"MasterCard\", creditCardNumber: \"5114170954034388\", creditCardExpiry: \"11/2024\", creditCardCVV2: \"412\" }";
//		System.out.println(json("creditCardNumber:\\s*(\"\\d{4})(\\d+)(\\d{4}\")", "$1*************$3"));
		String result=user.loginCustomer(username, passwd).toJson().replaceAll("(creditCardNumber\":\\s*\"\\d{4})(\\d+)(\\d{4}\")", "$1*************$3");
		System.out.println(result);
		return result;
	}
	

}
