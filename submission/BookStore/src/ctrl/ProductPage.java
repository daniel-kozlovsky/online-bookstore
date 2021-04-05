package ctrl;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.beans.Book;
import data.beans.Review;
import model.MainPageModel;

/**
 * Servlet implementation class ProductPage
 */
@WebServlet("/ProductPage")
public class ProductPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CUSTOMER = "customer";
	private static final String VISITOR = "visitor";
	
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
		
		getServletContext().setAttribute("user", VISITOR);
		HttpSession h = request.getSession();
		
		if (request.getParameter(bookID) != null) {
			
			try {
				String id =  request.getParameter(bookID);
				
				h.setAttribute(bookID, id);
				request.setAttribute(bookID, id);
				
				String html = getTop10Reviews(request, model, id);
				setAttributes (request,  model,  id);
				
				request.setAttribute(VIWE_SOME_REVIEWS, html);
			}catch (Exception e) {
				System.out.println("An error occured." + e.getMessage());
			}
			
		} else if (h.getAttribute(bookID) != null) {
			try {
				
				String id =  (String) h.getAttribute(bookID);
				h.setAttribute(bookID, id);
				String html = getTop10Reviews(request, model, id);
			
				request.setAttribute(VIWE_SOME_REVIEWS, html);
			
			} catch (Exception e) {
				System.out.println("An error occured." + e.getMessage());
			}
			
		} else  {
			System.out.println("ID = "+request.getParameter(bookID));
			System.out.println("An error occured. Could have come here only if pressed 'load more reviews'");
		}
		
		request.getRequestDispatcher("html/ProductPage.jspx").forward(request, response);
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
		
		request.setAttribute(TITLE, b.getTitle());
		request.setAttribute(AUTHOR, b.getAuthor());
		request.setAttribute(YEAR, b.getPublishYear());
		request.setAttribute(COVER, b.getCover());
		request.setAttribute(PRICE, b.getPrice());
		request.setAttribute(RATING, b.getRating());
		request.setAttribute(ISBN, b.getISBN());
		request.setAttribute(CATEGORY, b.getCategory());
		
	}
	
	private String getTop10Reviews(HttpServletRequest request, MainPageModel model, String id) {
		
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
		
		
		for (int i = 0; i < r.length; i ++) {
			
			html +=   "				<div class=\"review_row\" style=\"margin-top:50px;\">\n"
					+ "					<p> <img class=\"user_image\" style=\"float:left;width:30px;height:30px;vertical-align:center;\" src=\"/BookStore/res/user_logo.png\" /> "+r[i].getCustomer().getSurName() + ", "+ r[i].getCustomer().getGivenName() + " " + r[i].getRating() + " / 5 </p>"
					+ "					<div class=\"container_title  \">"+r[i].getTitle()+"</div>\n"
					+ "					<p>\n"
					+ "						"+r[i].getBody()
					+ "					</p>\n"
					+ "				</div>\n";
					
			
		}

		return html;
	}

}
