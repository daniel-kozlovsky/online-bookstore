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

import data.beans.Cart;
import data.beans.Customer;
import data.beans.PurchaseOrder;
import data.dao.CustomerDAO;
import data.dao.PurchaseOrderDAO;

/**
 * Servlet implementation class PurchaseOrder
 */
@WebServlet("/PurchaseOrder")
public class PurchaseOrderPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String loginSignUpPage="SignIn.jspx";
	private static final String cartPage="Cart.jspx";
	private static final String checkoutConfirmationPage="/html/PurchaseOrderConfirmation.html";
	private static final String checkoutMainPage="/html/PurchaseOrder.html";
	private static final String checkoutCompletePage="/html/PurchaseOrderComplete.html";
	private static final String mainPage="MainPage";
	private static final String signInPage="SignIn";
	private static final String customerInfoStage="custInfoStage";
	private static final String reviewStage="reviewStage";
	private static final String confirmationStage="confirmationStage";
	private static final String checkoutStageKeyName="stage";
	
	private static final String customerAttributeName="customer";
	
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
		• TODO UC P2: for a new account they enter their account name, password, and default billing and
		shipping information. The new account is submitted to the Order Processing service. 
		•TODO  UC P3: to submit their order, they verify their billing and shipping information, and enter in their
		credit card number.
		• TODO UC P4: “Confirm order” button
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
		
		
		if(isAjaxRequest(request)) {
//	    	  Customer customer=getCustomer();
			
				if(isCheckoutCartEmpty()) {
		    	  System.out.println("ajax request recevied emptu cart");
			      response.setContentType("application/json");
			      PrintWriter out = response.getWriter();
			      out.flush();
			      out.printf(errorJson("emptyCartError","your cart is empty please add items before checking out")); 
			      out.close();			      
			      }	else if(!isCustomerLoggedIn(request)) {//TODO: NEGATE THIS AFTER
			    	  System.out.println("ajax request recevied emptu cart");
				      response.setContentType("application/json");
				      PrintWriter out = response.getWriter();
				      out.flush();
				      out.printf(errorJson("customerNotExist","Please login or create an account before checking out")); 
				      out.close();
				      
				  }else {
			    	  System.out.println("ajax request recevied");
			    	  String customerJson=getCustomerJson(request);
				      response.setContentType("application/json");
				      PrintWriter out = response.getWriter();
				      out.flush();
				      out.printf(customerJson); 
				      out.close();			    	  
			      }	     
	       }else if(isCheckoutCartEmpty()) {
	    	   request.getRequestDispatcher(mainPage).forward(request, response);
		   }else if(isCustomerLoggedIn(request)) {//TODO: NEGATE THIS AFTER
	    	   request.getRequestDispatcher(signInPage).forward(request, response);
		   }else {
			   request.getRequestDispatcher(checkoutMainPage).forward(request, response);	  
	      }
	      
	      
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		
		
		if(request.getParameter("submitPurchaseOrder")!=null && request.getParameter("submitPurchaseOrder").equals("true")) {
			if(isValidPurchaseOrder()) {
				request.getRequestDispatcher(checkoutConfirmationPage).forward(request, response);	
			}
				
		}else if(request.getParameter("confirmPurchaseOrder") !=null && request.getParameter("confirmPurchaseOrder").equals("true")) {
			Customer customer=getCustomerFromSession(request);
			if(isCustomerRegistering(request)) {
				//TODO INSERT CUSTOMER HERE
//				customer = new CustomerDAO()
//						.newUpdateRequest()
//						.requestNewCustomerInsertion()
//						.insertCustomerWithGivenName(cartPage)
//						.insertCustomerWithSurName(cartPage)
//						.insertCustomerWithUserName(cartPage)
//						.insertCustomerWithPassWord(cartPage)
//						.insertCustomerWithEmail(checkoutMainPage)
//						.insertCustomerWithStreetNumber(cartPage)
//						.insertCustomerWithStreet(cartPage)
//						.insertCustomerWithPostalCode(cartPage)
//						.insertCustomerWithCity(cartPage)
//						.insertCustomerWithProvince(cartPage)
//						.insertCustomerWithCountry(cartPage)
//						.executeCustomerInsertion();
			}
			if(customer !=null) {
				customer = new Customer.Builder(customer).withCart(getCart(request)).build();
				request.getSession().setAttribute("customer", customer);
				PurchaseOrder purchaseOrder =new PurchaseOrderDAO().newUpdateRequest().insertPurchaseOrder(getCustomerFromSession(request));
				getCustomerFromSession(request).addPurchaseOrder(purchaseOrder);
				getCustomerFromSession(request).getCart().clearCart();
				request.getRequestDispatcher(checkoutCompletePage).forward(request, response);
			}else {
				request.getRequestDispatcher(checkoutMainPage).forward(request, response);	
//				request.getRequestDispatcher(checkoutCompletePage).forward(request, response);					
			}

		}
		
	}
	
	private boolean isCustomerLoggedIn(HttpServletRequest request) {
		return request.getSession().getAttribute(customerAttributeName)!=null;
	}
	
	private void reloadFormFields(HttpServletRequest request) {
	
	}
	
	private Cart getCart(HttpServletRequest request) {
		return (Cart)request.getSession().getAttribute("cart");
	}
	private boolean isCustomerRegistering(HttpServletRequest request) {
		return false;
	}
	
	private boolean isAjaxRequest(HttpServletRequest request) {
		return request.getParameter("ajax") !=null && request.getParameter("ajax").equals("true");
	}
	
	private boolean isValidPurchaseOrder() {
		return true;
	}
	
	private String errorJson(String errorLabel,String errorMessage) {
		return "{\""+errorLabel+"\":\""+errorMessage+"\"}";
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
		return false;
	}
	
	private Customer getCustomerFromSession(HttpServletRequest request) {
		return (Customer) request.getSession().getAttribute("customer");
	}
	
	private String getCustomerJson(HttpServletRequest request) {
		CustomerDAO user =new CustomerDAO();
		String username="WRitter163";
		String passwd = "Walterpassword";
		Customer customer = getCustomerFromSession(request);
		customer = user.loginCustomer(username, passwd);
		String result=customer.toJson().replaceAll("(creditCardNumber\":\\s*\"\\d{4})(\\d+)(\\d{4}\")", "$1*************$3");
		System.out.println(result);
		return result;
	}
	

}
