package ctrl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.dao.BookDAO;

/**
 * Servlet implementation class MainPage
 */
@WebServlet("/MainPage")
public class MainPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CUSTOMER = "customer";
	private static final String VISITOR = "visitor";
    
	BookDAO book = new BookDAO();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		getServletContext().setAttribute("user", VISITOR);
		//request.getRequestDispatcher("html/mainPage.jspx").forward(request, response);	
		
		PrintWriter out = response.getWriter();
		
//		book.newQueryRequest()
//			.includeAttributeInResult("COVER")
//			.includeAttributeInResult("PRICE")
//			.executeQuery()
//			.executeCompilation()
//			.compileBooks()
//			;
		
		book.newQueryRequest()
			.includeAllAttributesInResultFromSchema()
			.excludeBookDescriptionInResult()
			.excludeBookPriceInResult()
			.queryAttribute()
			.whereBookAuthor()
			.varCharContains("Tolkien")
			.queryAttribute()
			.whereBookAmountSold()
			.withAscendingOrderOf() 
			.withResultLimit(50)
			.withPageNumber(3) //ordering, limit and page number applies to the latest whereAttribute, in this case it is whereBookAmountSold()
			.queryReviews()
			.includeAllAttributesInResultFromSchema()
			.queryAttribute()
			.whereReviewRating()
			.numberAtLeast("5")
			.queryCustomers()
			.includeAllAttributesInResultFromSchema()
			.excludeCustomerPasswordInResult()
			.executeQuery()
			.executeCompilation()
			.compileBooks();
			
		String query = "select id, name, author, cover, price from Book where category = 'comedy' group by rating having count(*) <= 20";
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
