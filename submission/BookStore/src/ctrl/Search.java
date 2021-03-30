package ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.beans.Book;
import model.MainPageModel;

/**
 * Servlet implementation class Search
 */
@WebServlet({"/Search", "/Search/*"})
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CUSTOMER = "customer";
	private static final String VISITOR = "visitor";
    
	private static final String SEARCH = "searchBar";
	private static final String SEARCH_VAL = "search_value";
	
	private static final String MODEL = "model";
	
	private static final String COMM = "COMM";
    private static final String AJAX = "AJAX";
    
    private static final String NUM_DISP = "currentNumDisplayed";
    
    private static final String FIRST_OUTPUT = "FIRST_OUTPUT";
    
    private static final String USER_INPUT = "USER_INPUT";
    private static final String NUM_RES_FOUND = "NUM_RES_FOUND";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
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
		
		HttpSession h = request.getSession();
		
		System.out.println("In search servlet!");
		ServletContext context = getServletContext();
		
		MainPageModel model = (MainPageModel) context.getAttribute(MODEL);
		
//		String html = prepareSearchResults ("Harry", model,  0);
//		System.out.println(html);
//		if (!html.equals("")) {
//			response.setContentType("application/json");
//			PrintWriter out = response.getWriter();
//			out.printf(html); 
//			out.flush();
//		}
//		if (request.getParameter(COMM)!= null && request.getParameter(COMM).equals(AJAX)) {
//			System.out.println("-> AJAX");
//			System.out.println("");
//			
//			try {
//				int currentNumDisplayed = Integer.parseInt(request.getParameter(NUM_DISP));
//				System.out.println("current # of books displayed: "+currentNumDisplayed);
//				String input = (String) h.getAttribute(SEARCH_VAL);
//				String html = prepareSearchResults (input, model,  currentNumDisplayed);
//				
//				response.setContentType("application/json");
//				PrintWriter out = response.getWriter();
//				out.printf(html); 
//				out.flush();
//				
//			} catch (Exception e) {
//				System.out.println("An error occured! Either nothing was saved in session, this is not the 1st call, or this calls was an accident!\n"+e.getMessage());
//			}
//			
//		}
	   if (request.getParameter(SEARCH) != null) {
			String input = (String) request.getParameter(SEARCH);
			
			h.setAttribute(SEARCH_VAL, input);
			request.setAttribute(USER_INPUT, input);
			System.out.print(input);
			
			// if empty - go to main page
			if (input.equals("")) {
				RequestDispatcher rd = request.getRequestDispatcher("MainPage");
				rd.forward(request,response);
			} 
			// otherwise - prepare search results
			else {
				try {
					String html = prepareSearchResults (request, input, model);
					
					request.setAttribute(FIRST_OUTPUT, html);
					request.setAttribute(USER_INPUT, input);
					
					
					request.getRequestDispatcher("html/SearchedResults.jspx").forward(request, response);
				} catch (Exception e) {
					System.out.println("An error occured! Either nothing was saved in session, this is not the 1st call, or this calls was an accident!\n"+e.getMessage());
				}
			}
			
		} else {
			System.out.println("There is an error in the code. Shouldn't have come in Search unless pressed search button");
		}
					
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/**
	 * Prepares all the html portion made of search results.
	 * 
	 * @param request
	 * @param input	- search value request by user
	 * @param model 
	 * @return
	 */
	private String prepareSearchResults (HttpServletRequest request, String input, MainPageModel model) {
		
		List<Book> b = model.prepSearchResult (input); 

		request.setAttribute(NUM_RES_FOUND, b.size());
		
		if (b.isEmpty())
			return "<p> We could not find any results matching your input: "+input+" </p>";
		
		int numPages = (int) Math.ceil((double)b.size()/16.0);
		
		System.out.println("Number of results = "+b.size() + ", numPages = "+numPages);
		String html = "";
		
		int index = 0;
		
		for (int p = 0; p < numPages; p ++) {
			for (int row = 0; row < 4; row ++) {
				
				if (row*4+p*16 < b.size()) {
						if (p == 0)
							html += "		<div class=\"book_row\" style=\"display:inline;\">\n";
						else
							html += "		<div class=\"book_row\" style=\"display:none;\">\n";
					
				
				
					for (int col = 0; col < 4 && col+row*4+p*16 < b.size(); col ++) {
						
						html +=
								"			<div class=\"column_1_4\">\n"
								+ "				<button id=\"press\" class=\"book book_hover\" \n"
								+ "					onclick=\"pageHandler('/BookStore/MainPage/?COMM=AJAX&;ID="+b.get(index).getId()+"');return true;\"\n"
								+ "					style=\"background-image:url('/BookStore/res/book_images/covers/"+b.get(index).getCover()+"');\">\n"
								+ "					<div class=\"overlay\">\n"
								+ "						<div class=\"text\">\n"
								+ "							"+b.get(index).getTitle()+"<BR />\n"
								+ "							"+b.get(index).getAuthor()+" <BR/><BR/>\n"
								+ "							"+b.get(index).getPublishYear()+" <BR/><BR/>\n"
								+ "						\n"
								+ "							<span style=\"color:red;\">$"+b.get(index).getPrice()+"</span>\n"
								+ "						</div>\n"
								+ "					</div>\n"
								+ "				</button>\n"
								+ "			</div>\n";
						index ++;
					}
					html += "		</div>\n";
				}
			}
		}
		
		return html;
	}

}
