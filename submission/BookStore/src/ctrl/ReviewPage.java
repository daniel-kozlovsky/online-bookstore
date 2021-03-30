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

import model.MainPageModel;

/**
 * Servlet implementation class ReviewPage
 */
@WebServlet("/ReviewPage")
public class ReviewPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String CUSTOMER = "customer";
	private static final String VISITOR = "visitor";
	
	private static final String MODEL = "model";
	
	private static final String LOAD_REVIEWS = "LOAD_REVIEWS";
	private static final String COMM = "COMM";
	
	private static final String NUM_COMMENTS = "NUM_COMMENTS";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewPage() {
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
	    	System.out.println("ERROR initializeing review page model!");
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
		
		
		if (request.getParameter(COMM) != null && request.getParameter(COMM).equals(LOAD_REVIEWS))
		{
			int numReviews = 0;
			
			if (h.getAttribute(NUM_COMMENTS) == null)
				numReviews = 3;
			else
				numReviews = Integer.parseInt( (String) h.getAttribute(NUM_COMMENTS));
			
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
