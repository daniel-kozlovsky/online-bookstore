package ctrl;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.beans.Book;
import data.beans.Customer;
import data.beans.PurchaseOrder;
import data.beans.Review;
import data.dao.PurchaseOrderDAO;
import model.MainPageModel;


/**
 * Servlet implementation class Orders
 */
@WebServlet("/Orders")
public class Orders extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CUSTOMER = "customer";
	private static final String VISITOR = "visitor";
    
    private static final String MODEL = "model";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWD = "PASSWD";
    private static final String ORDER_ID = "orderID";
    private static final String review_form = "review_form";
    private static final String addReview = "addReview";
    private static final String viewReview = "viewReview";
    private static final String bookID = "bookID";
    private static final String reviewID = "reviewID";
    private static final String AUTHOR = "AUTHOR";
    private static final String TITLE = "TITLE";
    private static final String BOOKS_IN_ORDER = "BOOKS_IN_ORDER";
    
    private static final String  USER_ORDERS = "USER_ORDERS";
    private static final String rate = "rate";
    private static final String title = "title";
    private static final String body = "body";
    
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
	    	MainPageModel model = MainPageModel.getInstance();
		    
		    context.setAttribute( MODEL, model);
	    }
	    catch (Exception e) {
	    	System.out.println("ERROR initializeing main page model!");
	    }
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println(request.getParameterNames().toString());
		HttpSession h = request.getSession();
		
		ServletContext context = getServletContext();
		MainPageModel model = (MainPageModel) context.getAttribute(MODEL);
		
		//getServletContext().setAttribute("user", CUSTOMER);
//			String username = (String) getServletContext().getAttribute(USERNAME);
//			String passwd = (String) getServletContext().getAttribute(PASSWD);
		
		String username="WRitter163";
		String passwd = "Walterpassword";
		
		// customer just submitted a review - add review to database and go back to customer's specific order
		if (request.getParameter(review_form) != null) {
			try {
				System.out.println("Review was successfully submitted!");
				request.setAttribute(ORDER_ID, h.getAttribute(ORDER_ID));
				String order_id = (String) h.getAttribute(ORDER_ID);
				
				int rank = Integer.parseInt(request.getParameter(rate));
				String this_title = request.getParameter(title);
				String this_body = request.getParameter(body);
				String book_id = request.getParameter(bookID);
				
				// add review 
				model.addReview(username, passwd, this_title, this_body, rank, book_id);
				
				String html = loadIndividualOrder(order_id, model, username, passwd);
				request.setAttribute(BOOKS_IN_ORDER, html);
				
				request.getRequestDispatcher("html/ProdOrderView.jspx").forward(request, response);
				
			} catch (Exception e) {
				System.out.println("There was a problem going back form user submitted review to specific order page! " +e.getMessage());
			}
		}
		
		// user selects a specific order to view
		else if (request.getParameter(ORDER_ID)!= null) {
			try {
			    String order = request.getParameter(ORDER_ID);
				request.setAttribute(ORDER_ID, order);
				h.setAttribute(ORDER_ID, order);
				
				String order_id = (String) request.getAttribute(ORDER_ID);
				String html = loadIndividualOrder(order_id, model, username, passwd);
				
				request.setAttribute(BOOKS_IN_ORDER, html);
			
				request.getRequestDispatcher("html/ProdOrderView.jspx").forward(request, response);
			} catch (Exception e) {
				System.out.println("There was a problem going back form orders to a specific order page! " +e.getMessage());
			}
			
		// user selects adding a review	
		} else if (request.getParameter(addReview) != null){
			try {
				String book_id = request.getParameter(bookID);
				String title = request.getParameter(TITLE);
				String author = request.getParameter(AUTHOR);
				request.setAttribute(bookID, book_id);
				request.setAttribute(TITLE, title);
				request.setAttribute(AUTHOR, author);
				
				request.getRequestDispatcher("html/Review.jspx").forward(request, response);
			} catch (Exception e) {
				System.out.println("There was a problem going back form a specific order page to writing its review! " +e.getMessage());
			}
			
		} else if (request.getParameter(viewReview) != null) {
			try {
				Review review = model.getReview(request.getParameter(reviewID));
				
				request.setAttribute(rate, review.getRating());
				request.setAttribute(title, review.getTitle());
				request.setAttribute(body, review.getBody()); 
	
				String book_id = request.getParameter(bookID);
				String title = request.getParameter(TITLE);
				String author = request.getParameter(AUTHOR);
				request.setAttribute(bookID, book_id);
				request.setAttribute(TITLE, title);
				request.setAttribute(AUTHOR, author);
				request.getRequestDispatcher("html/Review.jspx").forward(request, response);
			
			} catch (Exception e) {
				System.out.println("There was a problem going back form a specific order page to writing its review! " +e.getMessage());
			}
		} else {
			try {
				String html = loadPage( username,  passwd,  model);
				request.setAttribute(USER_ORDERS, html);
				
				request.getRequestDispatcher("html/Orders.jspx").forward(request, response);
			} catch (Exception e) {
				System.out.println("Error loading user's orders! "+e.getMessage());
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String loadPage(String username, String passwd, MainPageModel model) {
		
		List<PurchaseOrder> orders = null;
		String html = "";
		
		try {
			orders = model.getCustomerOrders(username, passwd);
			System.out.println("orders: "+orders.size());
			for (int i = 0; i < orders.size(); i ++) {
			
				html += 
				 	  "						<tr class=\"row\">\n"
				 	  + "						<th>"+orders.get(i).getId()+"</th>\n"
				 	  + "						<th>"+orders.get(i).getStatus()+"</th>\n"
				 	  + "						<th>"+orders.get(i).getBooks().size()+"</th>\n"
				 	  + "						<th><button style=\"width:90%;height:90%;\" id=\"orderID\" name=\"orderID\" value=\""+orders.get(i).getId()+"\">view order</button></th>\n"
				 	  + "					</tr>";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("An error occured getting the orders. "+e.getMessage());
		}
			
		return html;
	}
	
	private String loadIndividualOrder(String order_id, MainPageModel model, String username, String passwd) throws Exception {
		String html = "";
		
		PurchaseOrder order = null;
		Map<Book, Integer> books = order.getBooks();
		Iterator iterator = books.entrySet().iterator(); 
		
		while (iterator.hasNext()) {
			
			Map.Entry me = (Map.Entry) iterator.next(); 
			Book b=(Book)me.getKey();
			int numBooks = (int) me.getValue();
			String book_id = b.getId().toString();
			
			String review = ""; 
			if (model.didCustomerAddReview(username, passwd, book_id)) 
				review += "Review already submitted\n";
			else
				review += "<p style=\"margin-left:0px;\"><button id=\"addReview\" name=\"addReview\" class=\"button addReview\" type=\"addReview\" value=\""+b.getId()+"\">write a review</button></p>\n";
			
			html += 
					"		<FORM action=\"/BookStore/Orders\" method=\"Post\">\n"
					+ "			<div class=\"row\">\n"
					+ "				<div class=\"column_1_3\">\n"
					+ "					<img class=\"prod_img\" style=\"float:center;height:100%;\" src=\"/BookStore/res/book_images/covers/"+b.getCover()+"\" alt=\"search\" /> \n"
					+ "				</div>\n"
					+ "				<div class=\"column_1_3\">\n"
					+ "					<div class=\"row\">\n"
					+ "						<h2>"+b.getTitle()+", "+b.getPublishYear()+"</h2> <h3>by "+b.getAuthor()+"</h3>\n"
					+ "					</div>\n"
					+ "					<div class=\"row\" >\n"
					+ "						ISBN: "+b.getISBN()+" <BR />\n"
					+ "						Quantity: "+numBooks+"\n"
					+ "					</div>\n"
					+ "					<div class=\"row\">\n"
					+ "						<p style=\"color:red;font-weight:bold;margin-left:0px;\">$"+b.getPrice()+"</p>\n"
					+ "					</div>\n"
					+ "					<div class=\"row\">\n"
					+ "						<input type=\"hidden\" id=\"TITLE\" name=\"TITLE\" value=\""+b.getTitle()+"\"></input>\n"
					+ "						<input type=\"hidden\" id=\"AUTHOR\" name=\"AUTHOR\" value=\""+b.getAuthor()+"\"></input>\n"
					+ "						<input type=\"hidden\" id=\"bookID\" name=\"bookID\" value=\""+b.getId()+"\"></input>\n"
					+ "						" + review
					+ "					</div>\n"
					+ "				</div>\n"
					+ "			</div>\n"
					+ "		</FORM>";
		}
			
		return html;
	}
}
