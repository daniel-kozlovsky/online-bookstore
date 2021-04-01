package ctrl;

import java.io.IOException;
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
@WebServlet("/ShoppingCart")
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
		
		Book b = new Book.Builder()
				.withAmountSold(5)
				.withAuthor("Author")
				.withCategory("Category")
				.withDescription("Desc")
				.withISBN("1234")
				.withPrice(13.99)
				.withPublishYear(1997)
				.withRating(5.0)
				.withTitle("Random Book")
				.build();
		Map<Book, Integer> m = new HashMap<Book, Integer>();
		m.put(b, 1);
		
		Customer customer = (Customer) session.getAttribute("customer");
		//request.setAttribute("books", customer.getCart().getBooks());
		request.setAttribute("books", m);
		request.getRequestDispatcher(CART_TARGET).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	

}
