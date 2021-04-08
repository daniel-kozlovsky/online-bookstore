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
import javax.servlet.http.HttpSession;

import data.beans.Address;
import data.beans.Cart;
import data.beans.CreditCard;
import data.beans.Customer;
import data.beans.PurchaseOrder;
import data.dao.CustomerDAO;
import data.dao.PurchaseOrderDAO;
import data.dao.exceptions.UpdateDBFailureException;

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
//	private static final String customerInfoStage="custInfoStage";
//	private static final String reviewStage="reviewStage";
//	private static final String confirmationStage="confirmationStage";
//	private static final String checkoutStageKeyName="stage";

	private static final String customerAttributeName="customer";
	
	private static final String poSubmissionAjax="stage";
	
	private static final String emptyCartStatus="emptyCart";
	
	private static final String streetNumber="streetNumber";
	private static final String street="street";
	private static final String city="city";
	private static final String province="province";
	private static final String country="country";
	private static final String postalCode="postalCode";
	
	private static final String creditCardType="creditCardType";
	private static final String creditCardNumber="creditCardNumber";
	private static final String creditCardExpiry="creditCardExpiry";
	private static final String creditCardCVV2="creditCardCVV2";
	
	private static final String newAddressCheck="newAddress";
	private static final String newCreditCardCheck="newCreditCard";
	private static final String defaultCreditCardCheck="defaultCreditCardCheck";
	private static final String defaultAddressCheck="defaultAddressCheck";
	
	private static final String inputUserNameCheckout="inputUserNameCheckout";
	private static final String inputPasswordCheckout="inputPasswordCheckout";
	
	private static final String inputGivenNameCheckout="inputGivenNameCheckout";
	private static final String inputSurNameCheckout="inputSurNameCheckout";
	private static final String inputEmailCheckout="inputEmailCheckout";
	
	private static final String customerCheckoutLogin="customerCheckoutLogin";
	private static final String customerCheckoutRegister="customerCheckoutRegister";
	
	private static final String purchaseOrderShippingAddress="purchaseOrderShippingAddress";
	private static final String purchaseOrderPaymentMethod="purchaseOrderPaymentMethod";
	
	private static final String checkoutConfirmationPageRequest="checkoutConfirmation";
	private static final String checkoutInfoPageRequest="checkoutInfo";
	private static final String checkoutCompleteRequest="checkoutComplete";
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
//	    	  }
//		else if(isCheckoutReviewStage()) {
//	    		  //return cart json
//	    		  //with totals
//	    	  }else if(isCheckoutCompleteStage()) {
//	    		  //
//	    	  }
//	      }
		//TEST METHOD THAT LOADS CUSTOMER TO SESSION
		HttpSession session =request.getSession(true);		
		if(session.getAttribute("customer")==null) {
			Customer customer =new CustomerDAO().loginCustomer("WRitter163", "Walterpassword");
			if(customer==null) {
				System.out.println("whops cust null");
			}
			session.setAttribute("customer", customer);
		}

		
		if(isAjaxRequest(request)) {
				if(isCheckoutCartEmpty()) {
					emptyCartJsonResponse(request,response);			      
			    }else if(!isCustomerLoggedIn(request)) {//TODO: NEGATE THIS AFTER
			    	noLoggedCustomerJsonResponse(request,response);				      
				}else {
				 if(isCheckoutInfoPageRequest(request)) {
					  loadCheckoutInfoPage(request,response);
				  } else if(isCheckoutConfirmationPageRequest(request)) {
					  loadCheckoutConfirmationPage(request,response);
				  }else if(isCheckoutCompleteRequest(request)){
					  completeCheckoutProcess(request,response);
				  }else {
					  request.getRequestDispatcher(checkoutMainPage).forward(request, response);
				  }					
			    }	     
	     }
		else if(isCheckoutCartEmpty()) {
			emptyCartServerResponse(request,response);
		 }
	    else if(!isCustomerLoggedIn(request)) {//TODO: NEGATE THIS AFTER
		   noLoggedCustomerServerResponse(request, response);
		 }
		else {
			   request.getRequestDispatcher(checkoutMainPage).forward(request, response);	  
	     }
	      
	      
		
	}
	
	private void completeCheckoutProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CreditCard paymentMethodPurchaseOrder=getPaymentMethodFromSession(request);
		Address shippingAddressPurchaseOrder=getShippingAddressFromSession(request);
		Customer customer = getCustomerFromSession(request);
				
		Customer customerPurchaseOrder = new Customer.Builder(customer)
				.withAddress(shippingAddressPurchaseOrder)
				.withCreditCard(paymentMethodPurchaseOrder)
				.build();
		
		try {
			
			PurchaseOrder purchaseOrder =new PurchaseOrderDAO().newUpdateRequest().insertPurchaseOrder(customerPurchaseOrder);
			
		} catch (UpdateDBFailureException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		      PrintWriter out = response.getWriter();
		      out.flush();
		      out.printf(errorJson("checkoutError","Your order was not submitted, please double check you information and try again shortly")); 
		      out.close();
		      return;
		}
		System.out.println("procced properly");
		if(isNewShippingAddressDefault(request)) {
			System.out.println("update addyu");
			customer = new Customer.Builder(customer).withCreditCard(paymentMethodPurchaseOrder).build();
			try {
				new CustomerDAO().newUpdateRequest().requestUpdateExistingCustomer(getCustomerFromSession(request))
				.updateCustomerCity(shippingAddressPurchaseOrder.getCity())
				.updateCustomerStreet(shippingAddressPurchaseOrder.getStreet())
				.updateCustomerStreetNumber(shippingAddressPurchaseOrder.getNumber())
				.updateCustomerProvince(shippingAddressPurchaseOrder.getProvince())
				.updateCustomerCountry(shippingAddressPurchaseOrder.getCountry())
				.updateCustomerPostalCode(shippingAddressPurchaseOrder.getPostalCode())
				.executeUpdate();
				updateCustomer(request,customer);
			} catch (UpdateDBFailureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			      response.setContentType("application/json");
			      PrintWriter out = response.getWriter();
			      out.flush();
			      out.printf(errorJson("addressUpdateError","Your order was successfully submitted, but your new default address was not updated please try again shortly")); 
			      out.close();
			      return;
			}
			
			
		}
		if(isNewPaymentMethodDefault(request)) {
			System.out.println("update payment");
			customer = new Customer.Builder(customer).withAddress(shippingAddressPurchaseOrder).build();
			try {
				new CustomerDAO().newUpdateRequest().requestUpdateExistingCustomer(getCustomerFromSession(request))
				.updateCustomerCreditCardType(paymentMethodPurchaseOrder.getCreditCardType())
				.updateCustomerCreditCardNumber(paymentMethodPurchaseOrder.getCreditCardNumber())
				.updateCustomerCreditCardExpiry(paymentMethodPurchaseOrder.getCreditCardExpiry())
				.updateCustomerCreditCardCvv2(paymentMethodPurchaseOrder.getCreditCardCVV2())
				.executeUpdate();
				updateCustomer(request,customer);
			} catch (UpdateDBFailureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			      response.setContentType("application/json");
			      PrintWriter out = response.getWriter();
			      out.flush();
			      out.printf(errorJson("creditCardUpdateError","Your order was successfully submitted, but your new default credit card was not updated please try again shortly")); 
			      out.close();
			      return;
			}
			
		}

		
//		getCustomerFromSession(request).addPurchaseOrder(purchaseOrder);
//		getCustomerFromSession(request).getCart().clearCart();
//		request.getRequestDispatcher(checkoutCompletePage).forward(request, response);
	      response.setContentType("application/json");
	      PrintWriter out = response.getWriter();
	      out.flush();
	      out.printf(errorJson("checkoutSuccess","Thank you for your order it will be processed and shipped to you shortly")); 
	      out.close();
	}
	
	private boolean isCheckoutInfoPageRequest(HttpServletRequest request) {
		return request.getParameter(checkoutInfoPageRequest)!=null && request.getParameter(checkoutInfoPageRequest).equals("true");
	}
	
	private boolean isCheckoutConfirmationPageRequest(HttpServletRequest request) {
		return request.getParameter(checkoutConfirmationPageRequest)!=null && request.getParameter(checkoutConfirmationPageRequest).equals("true");
	}
	
	
	private boolean isCheckoutCompleteRequest(HttpServletRequest request) {
		return request.getParameter(checkoutCompleteRequest)!=null && request.getParameter(checkoutCompleteRequest).equals("true");
	}
	
	private void loadCheckoutInfoPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		customerJsonResponse(request,response);
	}
	
	private void loadCheckoutCompletePage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		customerJsonResponse(request,response);
	}
	
	private void loadCheckoutConfirmationPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		  Customer purchaseOrderCustomer = new Customer.Builder(getCustomerFromSession(request)).build();
		  if(getPaymentMethodFromSession(request)!=null) {
			  purchaseOrderCustomer= new Customer.Builder(purchaseOrderCustomer).withCreditCard(getPaymentMethodFromSession(request)).build();
			  
		  }
		  if(getShippingAddressFromSession(request)!=null) {
			  purchaseOrderCustomer= new Customer.Builder(purchaseOrderCustomer).withAddress(getShippingAddressFromSession(request)).build();
			
		  }
		  if(getPaymentMethodFromSession(request)==null && getShippingAddressFromSession(request)==null) {
			  customerJsonResponse(request,response);
		  }else {
			  customerJsonResponse(purchaseOrderCustomer,request,response);
		  }
		  
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		
		
		if(isCheckOutInfoProcessStage(request)) {
			checkOutInfoSubmissionProcess(request,response);				
		}else {
			request.getRequestDispatcher(checkoutMainPage).forward(request, response);
		}
		
	}
	
	

	
	private void checkOutInfoSubmissionProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isValidPurchaseOrder()) {
			saveShippingAddressToSession(request);
			savePaymentMethodToSession(request);
			request.getRequestDispatcher(checkoutConfirmationPage).forward(request, response);	
		}else {
			
		}
	}
	
	private void emptyCartJsonResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
	      response.setContentType("application/json");
	      PrintWriter out = response.getWriter();
	      out.flush();
	      out.printf(errorJson("emptyCartError","your cart is empty please add items before checking out")); 
	      out.close();	
	}
	
	
	private void noLoggedCustomerJsonResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
	      response.setContentType("application/json");
	      PrintWriter out = response.getWriter();
	      out.flush();
	      out.printf(errorJson("customerNotExistError","Please login or create an account before checking out")); 
	      out.close();
	}
	
	private void customerJsonResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		customerJsonResponse(getCustomerFromSession(request),request,response);
	}
	
	private void customerJsonResponse(Customer customer,HttpServletRequest request, HttpServletResponse response) throws IOException {
	      response.setContentType("application/json");
	      PrintWriter out = response.getWriter();
	      out.flush();
	      out.printf(getCustomerJson(customer,request)); 
	      out.close();	
	}
	
	
	private void emptyCartServerResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
	      response.setContentType("text/html");
	      PrintWriter out = response.getWriter();
	      out.flush();
	      out.printf(serverResponseAlert("/BookStore/MainPage","Store front","emptyCartError: your cart is empty please add items before checking out")); 
	      out.close();	
	}

	
	private void noLoggedCustomerServerResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
	      response.setContentType("text/html");
	      PrintWriter out = response.getWriter();
	      out.flush();
	      out.printf(serverResponseAlert("/BookStore/SignIn","Sign in page","customerNotExistError: Please login or create an account before checking out")); 
	      out.close();
	}
	
	private void invalidFormInputServerResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
	      response.setContentType("text/html");
	      PrintWriter out = response.getWriter();
	      out.flush();
	      out.printf(serverResponseAlert("","previousPage","Validation errors: Please login or create an account before checking out")); 
	      out.close();
	}

	private void checkOutInfoStageProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	private String serverResponseAlert(String url,String urlRedirectMessage,String ...messages) {
		String urlHTML =url.isEmpty()?"":";url="+url;
		
		String result="<html><head><meta http-equiv=\"refresh\" content=\"10"+urlHTML+"\" /><title>Error</title></head><body><div>Warning Javascript is disabled, please enable it for proper functionality!</div> <div>Errors</div><ul>";
		for(String message:messages) {
			result+="<li>"+message+"</li>";
		}
		result+="</ul><div><a href=\""+url+"\">if auto redirection failed go to: "+urlRedirectMessage+"</a> </div></body></html>";
		return result;
	}
	

	


	private void checkOutCompletionStageProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	private void updateCustomer(HttpServletRequest request, Customer customer) {
		request.getSession().setAttribute("customer", customer);
	}
	
	private void saveShippingAddressToSession(HttpServletRequest request) {
		request.getSession().setAttribute(purchaseOrderShippingAddress,getFormShippingAddress(request));
	}
	private Address getShippingAddressFromSession(HttpServletRequest request) {
		return (Address)request.getSession().getAttribute(purchaseOrderShippingAddress);
	}
	private void savePaymentMethodToSession(HttpServletRequest request) {
		request.getSession().setAttribute(purchaseOrderPaymentMethod,getFormPaymentMethod(request));
	}
	private CreditCard getPaymentMethodFromSession(HttpServletRequest request) {
		return (CreditCard)request.getSession().getAttribute(purchaseOrderPaymentMethod);
	}
	
	private Address getFormShippingAddress(HttpServletRequest request) {
		Address address=null;
		if(isShippingAddressNew(request)) {
			address= new Address.Builder()
					.withNumber(getParam(streetNumber, request))
					.withStreet(getParam(street, request))
					.withPostalCode(getParam(postalCode, request))
					.withCity(getParam(city, request))
					.withProvince(getParam(province, request))						
					.withCountry(getParam(country, request))
					.build();
			if(isNewShippingAddressDefault(request)) {
				request.getSession().setAttribute(newAddressCheck, "true");
			}
		}else {
			address=getCustomerFromSession(request).getAddress();
		}
		return address;

	}
	

	
	private boolean isCustomerInSession() {
		return true;
	}
	private CreditCard getFormPaymentMethod(HttpServletRequest request) {
		CreditCard creditCard=null;
		if(isPaymentMethodNew(request)) {
			String[] date=getParam(creditCardExpiry, request).split("-");
			creditCard= new CreditCard.Builder()
					.withCreditCardType(getParam(creditCardType, request))
					.withCreditCardNumber(getParam(creditCardNumber, request))
					.withCreditCardExpiry(date[1]+"-"+date[0])
					.withCreditCardCVV2(getParam(creditCardCVV2, request))
					.build();	
			if(isNewPaymentMethodDefault(request)) {
				request.getSession().setAttribute(newCreditCardCheck, "true");
			}
		}else {
			creditCard=getCustomerFromSession(request).getCreditCard();
		}
		return creditCard;
	}
	
	private boolean isShippingAddressNew(HttpServletRequest request) {
			return request.getParameter(defaultAddressCheck)==null;
		
	}
	
	private boolean isPaymentMethodNew(HttpServletRequest request) {
		return request.getParameter(defaultCreditCardCheck)==null;
	}
	
	
	private boolean isNewShippingAddressDefault(HttpServletRequest request) {
		return request.getParameter(newAddressCheck)!=null || request.getSession().getAttribute(newAddressCheck)!=null ;	
	}

	private boolean isNewPaymentMethodDefault(HttpServletRequest request) {
		return request.getParameter(newCreditCardCheck)!=null|| request.getSession().getAttribute(newCreditCardCheck)!=null ;	
	}
	
	private void checkOutConfirmationStageProcess(HttpServletRequest request) {
		
	}
	
	private boolean isCheckOutInfoProcessStage(HttpServletRequest request) {
		return request.getParameter("submitPurchaseOrder")!=null && request.getParameter("submitPurchaseOrder").equals("true");
		
	}
	private boolean isCheckOutConfirmationProcessStage(HttpServletRequest request) {
		return request.getParameter("confirmPurchaseOrder") !=null && request.getParameter("confirmPurchaseOrder").equals("true");
	}
	
	private String getParam(String paramName,HttpServletRequest request) {
		String result=request.getParameter(paramName)==null?"":request.getParameter(paramName);
		return result;
	}
	
	private boolean isCustomerLoggedIn(HttpServletRequest request) {
		return request.getSession().getAttribute(customerAttributeName)!=null;
	}
	
	private void reloadFormFields(HttpServletRequest request) {
	
	}
	
	private Cart getCart(HttpServletRequest request) {
		return (Cart)request.getSession().getAttribute("cart");
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
	
	private boolean isCustomerLoggingIn(HttpServletRequest request) {
		return request.getParameter("loginCheckoutRequest")!=null && request.getParameter("loginCheckoutRequest").equals("true");
	}
	private boolean isCustomerRegistering(HttpServletRequest request) {
		return request.getParameter("registerCheckoutRequest")!=null && request.getParameter("registerCheckoutRequest").equals("true");
	}
	private Customer getCustomerFromSession(HttpServletRequest request) {
		return (Customer) request.getSession().getAttribute("customer");
	}
	
	private String getCustomerJson(HttpServletRequest request) {
//		String result="{";
//		Customer customer = getCustomerFromSession(request);
//		
//		if(customer.getAddress().hasMissingComponents()) {
//			result+="\"missingAddressComponents\":true,";
//		}
//		if(customer.getCreditCard().isEmpty()) {
//			result+="\"missingCreditCard\":true,";
//		}
//		if(request.getSession().getAttribute("customer")!=null) {
//			result+="\"customer\":"+((Customer) request.getSession().getAttribute("customer")).toJson().replaceAll("(creditCardNumber\":\\s*\"\\d{4})(\\d+)(\\d{4}\")", "$1*************$3");
//		}
//		result+="}";
		return getCustomerJson(getCustomerFromSession(request),request);
	}
	
	private String getCustomerJson(Customer customer,HttpServletRequest request) {
		String result="{";
	
		
		if(customer.getAddress().hasMissingComponents()) {
			result+="\"missingAddressComponents\":true,";
		}
		if(customer.getCreditCard().isEmpty()) {
			result+="\"missingCreditCard\":true,";
		}
		if(request.getSession().getAttribute("customer")!=null) {
			result+="\"customer\":"+customer.toJson().replaceAll("(creditCardNumber\":\\s*\"\\d{4})(\\d+)(\\d{4}\")", "$1*************$3");
		}
		result+="}";
		return result;
	}
	
	private String getFormDefaultParamSelection(String paramName,String check, HttpServletRequest request) {
		return request.getParameter(check)==null?request.getParameter(paramName):getDefaultIdName(request.getParameter(paramName));
	}
	
	private String getDefaultIdName(String idLabel) {
		return "default"+idLabel.substring(0,1).toUpperCase()+idLabel.substring(1,idLabel.length());
	}
	
	private String getConfirmIdName(String idLabel) {
		return "confirm"+idLabel.substring(0,1).toUpperCase()+idLabel.substring(1,idLabel.length());
	}
	
	private String getFormCustomerAccountParam(String paramName,String check, HttpServletRequest request) {
		String result=request.getParameter(paramName)==null?"":request.getParameter(paramName);
		return result;
	}
}

//Customer customer=getCustomerFromSession(request);
//
//
//if(customer ==null  && isCustomerRegistering(request)) {
//	//TODO INSERT CUSTOMER HERE
//	customer = new CustomerDAO()
//			.newUpdateRequest()
//			.requestNewCustomerInsertion()
//			.insertCustomerWithGivenName(getParam(inputGivenNameCheckout,request))
//			.insertCustomerWithSurName(getParam(inputSurNameCheckout,request))
//			.insertCustomerWithUserName(getParam(inputUserNameCheckout,request))
//			.insertCustomerWithPassWord(getParam(inputPasswordCheckout,request))
//			.insertCustomerWithEmail(getParam(inputEmailCheckout,request))
//			.insertCustomerWithStreetNumber(getParam(streetNumber,request))
//			.insertCustomerWithStreet(getParam(street,request))
//			.insertCustomerWithPostalCode(getParam(postalCode,request))
//			.insertCustomerWithCity(getParam(city,request))
//			.insertCustomerWithProvince(getParam(province,request))
//			.insertCustomerWithCountry(getParam(country,request))
//			.executeCustomerInsertion();
//	if(customer!=null) {
//		request.getSession().setAttribute("customer", customer);
//	}else {
//	      response.setContentType("application/json");
//	      PrintWriter out = response.getWriter();
//	      out.flush();
//	      out.printf(errorJson("registerFail","incorrect credentials please try again"));  //TODO 
//	      out.close();	
//	}
//}else if(customer ==null  && isCustomerLoggingIn(request)) {
//	String username =request.getParameter(inputUserNameCheckout);
//	String password =request.getParameter(inputPasswordCheckout);
//	customer =new CustomerDAO().loginCustomer(username, password);
//	if(customer!=null) {
//		request.getSession().setAttribute("customer", new CustomerDAO().loginCustomer(username, password));
//	}else {
//	      response.setContentType("application/json");
//	      PrintWriter out = response.getWriter();
//	      out.flush();
//	      out.printf(errorJson("loginFail","incorrect credentials please try again")); 
//	      out.close();	
//	}
//	
//}else {
//	Address address= new Address.Builder()
//			.withNumber(creditCardNumber)
//			.withStreet(street)
//			.withPostalCode(postalCode)
//			.withCity(city)
//			.withProvince(province)						
//			.withCountry(country)
//			.build();
//	CreditCard creditCard = new CreditCard.Builder()
//			.withCreditCardType(creditCardType)
//			.withCreditCardExpiry(creditCardExpiry)
//			.withCreditCardNumber(creditCardNumber)
//			.withCreditCardCVV2(defaultCreditCardCheck)
//			.build();
//	PurchaseOrder purchaseOrder =new PurchaseOrderDAO().newUpdateRequest().insertPurchaseOrder(new Customer.Builder(getCustomerFromSession(request)).withAddress(address).withCreditCard(creditCard).build());
//	getCustomerFromSession(request).addPurchaseOrder(purchaseOrder);
//	getCustomerFromSession(request).getCart().clearCart();
//	request.getRequestDispatcher(checkoutCompletePage).forward(request, response);
//	request.getRequestDispatcher(checkoutMainPage).forward(request, response);	
//	request.getRequestDispatcher(checkoutCompletePage).forward(request, response);					
//}