package ctrl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.beans.Book;
import data.beans.Cart;
import data.beans.Customer;
import data.beans.Visitor;
import data.beans.Review;
import data.beans.Visitor;
import model.MainPageModel;

/**
 * Servlet implementation class ProductPage
 */
@WebServlet("/ProductPage")
public class ProductPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String MODEL = "model";

	private static final String TITLE = "TITLE";
	private static final String AUTHOR = "AUTHOR";
	private static final String YEAR = "YEAR";
	private static final String COVER = "COVER";
	private static final String PRICE = "PRICE";
	private static final String RATING = "RATING";
	private static final String CATEGORY = "CATEGORY";
	private static final String ISBN = "ISBN";
	
	private static final String bookID = "bookID";
	
	private static final String VIWE_SOME_REVIEWS = "VIWE_SOME_REVIEWS";
	
    private static final String ONE_STAR = "ONE_STAR";
    private static final String TWO_STAR = "TWO_STAR";
    private static final String THREE_STAR = "THREE_STAR";
    private static final String FOUR_STAR = "FOUR_STAR";
    private static final String FIVE_STAR = "FIVE_STAR";
    
    private static final String rate = "rate";
    private static final String title = "title";
    private static final String body = "body";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductPage() {
        super();
        // TODO Auto-generated constructor stub
    }
    

    @Override
    public void init(ServletConfig config) throws ServletException { 
    	super.init(config);

	    ServletContext context = getServletContext();
	    
	    try {
		    MainPageModel model = MainPageModel.getInstance();
		    
		    context.setAttribute(MODEL, model);
	    }
	    catch (Exception e) {
	    	System.out.println("ERROR initializeing main page model!");
	    }
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		MainPageModel model = (MainPageModel) context.getAttribute(MODEL);
		
		HttpSession h = request.getSession();
		
		//----------------------------------------------------------------
		//---------------------- Adds product to cart  -------------------
		if (request.getParameter("addProduct") != null ) {
			String book_id = request.getParameter(bookID);
			request.setAttribute(bookID, book_id);
			
			System.out.println("\tADDING A BOOK TO CART:\tbook_id = " + book_id);
			
			// ------ customer ------
			if (h.getAttribute("customer") != null) {
				
				try {				
					Customer customer = (Customer) h.getAttribute("customer");
					Cart cart = customer.getCart();
					
					System.out.println("\t num of books in cart BEFORE adding product: "+cart.getBooks().size());
					
					//================================ TO DO ============================
					//============== Check if user keeps the pointer to that updated cart
					if (!cart.isBookInCart(model.getBookByID(book_id)))
						cart.addBookAmount(model.getBookByID(book_id), 1);
					
					System.out.println("\t num of books in cart AFTER adding product: "+cart.getBooks().size());
					System.out.println("\t num of books in CUSTOMER's cart AFTER adding product: "+customer.getCart().getBooks().size());
					
					h.setAttribute("customer", customer);
					
					request.setAttribute("success", "Product added successfully");
				} catch (Exception e) {
					System.out.println("ERROR - Failed to add book to customer cart! "+e.getMessage());
				}
			
			// ------ visitor ------
			} else {
				try {
					Visitor visitor;
					if (h.getAttribute("visitor") == null) 
						 visitor = model.getVisitor(request);
					else
						visitor = (Visitor) h.getAttribute("visitor");
					
					
					Cart cart = visitor.getCart();

					if (!cart.isBookInCart(model.getBookByID(book_id)))
						cart.addBookAmount(model.getBookByID(book_id), 1);
					
					h.setAttribute("visitor", visitor);
				
					request.setAttribute("success", "Product added successfully");
				} catch (Exception e) {
					System.out.println("ERROR - Failed to add book to visitor's cart! "+e.getMessage());
				}
			}
			
			try {
				completeProductLoad( request,  model,  book_id);
				request.getRequestDispatcher("html/ProductPage.jspx").forward(request, response);
			} catch (Exception e) { 
				System.out.println("Problem loading the page after addig to cart! " + e.getMessage()); 
			}
			
		//----------------------------------------------------------------
		//---------------- redirect user to add a review  ----------------
		} else if (request.getParameter("addReview") != null) {
			System.out.println("==========> HERE 1 <==========");
			String book_id;
			
			if (request.getParameter(bookID) != null)
				book_id = request.getParameter(bookID);
			else
				book_id = (String) h.getAttribute(bookID);
			
			// if a user is loged in
			if (h.getAttribute("customer") != null) {
				
				Customer customer = (Customer) h.getAttribute("customer");
				
				try {
					// give customer option to update his review or check it out
					if (model.didCustomerAddReview(customer, book_id)) {
						List<Review> review = model.getReview(customer, book_id);
						Review r = review.get(0);
						
						int rating_number = r.getRating();
						
						if (rating_number == 1)
							request.setAttribute(ONE_STAR, 1);
						else if (rating_number == 2)
							request.setAttribute(TWO_STAR, 2);
						else if (rating_number == 3)
							request.setAttribute(THREE_STAR, 3);
						else if (rating_number == 4)
							request.setAttribute(FOUR_STAR, 4);
						else if (rating_number == 5)
							request.setAttribute(FIVE_STAR, 5);
						
						
						request.setAttribute(title, r.getTitle());
						request.setAttribute(body, r.getBody()); 
					}
				} catch (Exception e) {
					System.out.println("There was a problem going back form a specific order page to writing its review! " +e.getMessage());
				}		
			}	
			try {
				Book book = model.getBookByID(book_id);
			
				String title = book.getTitle();
				String author = book.getAuthor();
	
				request.setAttribute(bookID, book_id);
				request.setAttribute(TITLE, title);
				request.setAttribute(AUTHOR, author);
				
				request.setAttribute("redirectTo", "ProductPage");
				request.getRequestDispatcher("html/Review.jspx").forward(request, response);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//----------------------------------------------------------------
		//------------------ initial loading of the page  ----------------
		else if (request.getParameter(bookID) != null) {
			System.out.println("==========> HERE 3 <==========");
			String book_id =  request.getParameter(bookID);
			h.setAttribute(bookID, book_id);
			try {
				
				//----------------------------------------------------------------
				//---------------- user just submitted a review  -----------------
				if (request.getParameter("review_form") != null) {
					System.out.println("\t==========> HERE 2 <==========");
					

					System.out.println("\t2. get rank!");
					int rank = Integer.parseInt(request.getParameter(rate));
					
					System.out.println("\t3. get title!");
					String this_title = request.getParameter(title);
					
					System.out.println("\t4. get body!");
					String this_body = request.getParameter(body);
					
					// update database with this user
					if (h.getAttribute("customer") != null) {

						Customer customer = (Customer) h.getAttribute("customer");
						
						// add review 
						System.out.println("About to add Customer review!");
						model.addReview(customer, this_title, this_body, rank, book_id);
					} 
					// update database anonymously
					else {
						// add review  anonymously
						
						Visitor visitor;
						if (h.getAttribute("visitor") == null) 
							 visitor = model.getVisitor(request);
						else
							visitor = (Visitor) h.getAttribute("visitor");
						
						System.out.println("About to add Visitor review!");
						model.addAnonymousReview(visitor, this_title, this_body, rank, book_id);
					}
					
					request.setAttribute("review_success", "review was added successfully");
				}
				
				request.setAttribute(bookID, book_id);
				
				completeProductLoad( request,  model,  book_id);
				
			}catch (Exception e) {
				
				request.setAttribute(bookID, book_id);
				try {
					completeProductLoad( request,  model,  book_id);
					
				} catch (Exception e1) {
					System.out.println("An error occured." + e1.getMessage());
				}
				System.out.println("An error occured." + e.getMessage());
			}
			
			request.getRequestDispatcher("html/ProductPage.jspx").forward(request, response);
		
		//----------------------------------------------------------------
		//----- page was refreshed - need to set attributes again  -------
		} else if (h.getAttribute(bookID) != null) {
			System.out.println("==========> HERE 4 <==========");
			try {
				String id =  (String) h.getAttribute(bookID);
				request.setAttribute(bookID, id);
				h.setAttribute(bookID, id);
				
				completeProductLoad( request,  model,  id);
			
			} catch (Exception e) {
				System.out.println("An error occured." + e.getMessage());
			}
			
			request.getRequestDispatcher("html/ProductPage.jspx").forward(request, response);
		
		//----------------------------------------------------------------
		//------------------------- An ERROR  ----------------------------
		} else  {
			System.out.println("==========> HERE 5 <==========");
			System.out.println("ID = "+request.getParameter(bookID));
			System.out.println("An error occured. Could have come here only if pressed 'load more reviews'");
			
			//request.getRequestDispatcher("html/ProductPage.jspx").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void setAttributes (HttpServletRequest request, MainPageModel model, String id) throws Exception{
		Book b = model.getBookByID(id);
		
		double this_rate = (double)((int)(Math.ceil(b.getRating() * 100)))/100;
		
		request.setAttribute(TITLE, b.getTitle());
		request.setAttribute(AUTHOR, b.getAuthor());
		System.out.println("year of "+b.getId() + " is " + b.getPublishYear());
		request.setAttribute(YEAR, b.getPublishYear());
		request.setAttribute(COVER, b.getCover());
		request.setAttribute(PRICE, b.getPrice());
		request.setAttribute(RATING, this_rate);
		request.setAttribute(ISBN, b.getISBN());
		request.setAttribute(CATEGORY, b.getCategory());	
		request.setAttribute("DESC", b.getDescription());
	}
	
	private String getTop10Reviews(HttpServletRequest request, MainPageModel model, String id) throws Exception {
		
		String html = "";
		Book b = null;
		
		try {
			b = model.getReviewsForThisBook (id, true, 10);
			
		} catch (Exception e) {
			return "<p> This book has no reviews </p>";
		}
		
		Review[] r = b.getReviews();

		
		System.out.println("number of reviews= " + r.length);
		
		int numPages = (int) Math.ceil( (double) r.length / 10);
		double this_rate;
		
		for (int i = 0; i < r.length; i ++) {
			
			String tmpLine = "";
			
			this_rate = (double)((int)(Math.ceil(r[i].getRating() * 100)))/100;
			
			if (r[i].getUserType().equals("CUSTOMER")) {
				Customer customer = model.getUserByUsername(r[i].getSiteUser());
				
				tmpLine = "					<p> <img class=\"user_image\" style=\"float:left;width:30px;height:30px;vertical-align:center;\" src=\"/BookStore/res/user_logo.png\" /> "+customer.getSurName() + ", "+ customer.getGivenName() + " " + this_rate+ " / 5 </p>";
			} else {
				tmpLine = "					<p> <img class=\"user_image\" style=\"float:left;width:30px;height:30px;vertical-align:center;\" src=\"/BookStore/res/user_logo.png\" /> <i> site visitor </i> " + this_rate + " / 5 </p>";
			}
				
			html +=   "				<div class=\"review_row\" style=\"margin-top:50px;\">\n"
					+ tmpLine
					+ "					<div class=\"container_title  \">"+r[i].getTitle()+"</div>\n"
					+ "					<p>\n"
					+ "						"+r[i].getBody()
					+ "					</p>\n"
					+ "				</div>\n";
		}
		
		return html;
	}
	
	private void completeProductLoad(HttpServletRequest request, MainPageModel model, String id) throws Exception {
		String html = getTop10Reviews(request, model, id);
		
		setAttributes (request,  model,  id);
		
		request.setAttribute(VIWE_SOME_REVIEWS, html);
	}

}
