package ctrl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.beans.Book;
import data.beans.Customer;
import data.beans.PurchaseOrder;
import model.UserModel;

/**
 * Servlet implementation class Orders
 */
@WebServlet("/Orders")
public class Orders extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CUSTOMER = "customer";
	private static final String VISITOR = "visitor";
    
    private static final String USER_MODEL = "user_model";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWD = "PASSWD";
    private static final String ORDER_ID = "ORDER_ID";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Orders() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException { 
    	super.init(config);

	    ServletContext context = getServletContext();
	    
	    try {
	    	UserModel model = UserModel.getInstance();
		    
		    context.setAttribute(USER_MODEL, model);
	    }
	    catch (Exception e) {
	    	System.out.println("ERROR initializeing main page model!");
	    }
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter(ORDER_ID)!= null) {
			request.getRequestDispatcher("html/ProdOrderView.jspx").forward(request, response);
		}
		
		//getServletContext().setAttribute("user", CUSTOMER);
		String username = (String) getServletContext().getAttribute(USERNAME);
		String passwd = (String) getServletContext().getAttribute(PASSWD);
		request.getRequestDispatcher("html/Orders.jspx").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String loadPage(String username, String passwd) {
		ServletContext context = getServletContext();
		UserModel model = (UserModel) context.getAttribute(USER_MODEL);
		List<Customer> user = model.getCustomerOrders(username, passwd);
		
		PurchaseOrder[] orders = user.get(0).getPurchaseOrders();
		Map<Book, Integer> books = orders[0].getBooks();
		
		String html = "";
		
		for (int i = 0; i < orders.length; i ++) {
			html += 
			 	  "				<tr>\n"
				+ "					<th style=\"font-weight: bold;\">"+orders[i].getId()+"</th>\n"
				+ "					<th style=\"font-weight: bold;\">Book ID </th>\n"
				+ "					<th style=\"font-weight: bold;\">Title</th>\n"
				+ "					<th style=\"font-weight: bold;\">Price</th>\n"
				+ "					<th style=\"font-weight: bold;\">Status</th>\n"
				+ "					<th style=\"font-weight: bold;\">Amount</th>\n"
				+ "					<th style=\"font-weight: bold;\">Write Review</th>\n"
				+ "				</tr>";
		}
		
		return "";
	}
	
	private String loadIndividualProduct(String order_id) {
		String html = "";
		
		
		
		return html;
	}
}
