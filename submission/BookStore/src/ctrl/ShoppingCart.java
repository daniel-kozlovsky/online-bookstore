package ctrl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.beans.Book;
import data.beans.Cart;
import data.beans.Customer;
import model.ShoppingCartModel;

/**
 * Servlet implementation class ShoppingCart
 */
@WebServlet(urlPatterns = {"/ShoppingCart", "/ShoppingCart/*"})
public class ShoppingCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ShoppingCartModel cartModel;
	final String CART_TARGET = "/html/Cart.jspx";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingCart() {
       cartModel = ShoppingCartModel.getInstance();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		/*******testing********************************************/
//		Book b = new Book.Builder()
//				.withCover(new File("Queen_of_Air_and_Darkness_9781442468450.jpg"))
//				.withAmountSold(5)
//				.withAuthor("Author")
//				.withCategory("Category")
//				.withDescription("Desc")
//				.withISBN("1234")
//				.withPrice(13.99)
//				.withPublishYear(1997)
//				.withRating(5.0)
//				.withTitle("Random Book")
//				.build();
//		Map<Book, Integer> m = new HashMap<Book, Integer>();
//		m.put(b, 1);
//		request.setAttribute("books", m);
		/*****************************************************/
		 
		Customer customer = (Customer) session.getAttribute("customer");
		request.setAttribute("books", customer.getCart().getBooks());
		double totalPrice = cartModel.getTotalPrice(customer.getCart());
		request.setAttribute("totalPrice", totalPrice);
		
		request.getRequestDispatcher(CART_TARGET).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/text");
		PrintWriter out = response.getWriter();
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		String isbn = request.getParameter("isbn");
		int quantity;
		
		//remove book
		if(request.getPathInfo() != null && request.getPathInfo().indexOf("remove") >= 0)
		{
			cartModel.removeBook(customer.getCart(), isbn);
		}
		//update price
		else
		{
			try
			{
				quantity = Integer.parseInt(request.getParameter("quantity"));
			}
			catch(NumberFormatException NFE)
			{
				quantity = 0;
			}
			
			String responseText = "";
			if(quantity > 0 && !isbn.isEmpty())
			{
				cartModel.updateBookQuantity(customer.getCart(), isbn, quantity);
				responseText = Double.toString(cartModel.getTotalPrice(customer.getCart()));
			}
			
			
			out.printf(responseText);
			out.flush();
		}
		
		
	}
	
	

}
