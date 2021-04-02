

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import data.beans.Book;
import data.beans.Customer;
import data.beans.Id;
import data.beans.PurchaseOrder;
import data.dao.BookDAO;
import data.dao.CustomerDAO;
import data.dao.UpdateBook;
import data.dao.UpdateCustomer;
import data.dao.UpdateReview;
import data.query.DataObjectCompiler;

/**
 * Servlet implementation class BookTestCtrl
 */
@WebServlet("/BookTestCtrl")
public class BookTestCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookTestCtrl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("do get");
		PrintWriter out = response.getWriter();
      response.setContentType("text");
      response.setCharacterEncoding("UTF-8");
      out.write("TESTING  !!!");
//		String query ="SELECT BOOK.ID AS BOOK_ID,BOOK.TITLE AS BOOK_TITLE, BOOK.ISBN AS BOOK_ISBN,REVIEW.TITLE AS REVIEW_TITLE,REVIEW.BODY AS REVIEW_BODY, REVIEW.CUSTOMER AS REVIEW_CUSTOMER, REVIEW.BOOK AS REVIEW_BOOK, CUSTOMER.ID AS CUSTOMER_ID, CUSTOMER.GIVENNAME AS CUSTOMER_GIVENNAME, CUSTOMER.SURNAME AS CUSTOMER_SURNAME FROM BOOK,REVIEW,CUSTOMER WHERE  BOOK.ID=REVIEW.BOOK AND REVIEW.CUSTOMER=CUSTOMER.ID AND REVIEW.BOOK='b7441b2a-0739-3641-a78f-1d973daee854'";
//
//		Map<String,Set<String>> attributesIncludedInResults = new LinkedHashMap<String, Set<String>>();
//		attributesIncludedInResults.put("CUSTOMER", new LinkedHashSet<String>());
//		attributesIncludedInResults.get("CUSTOMER").add("ID");
//		attributesIncludedInResults.get("CUSTOMER").add("GIVENNAME");
//		attributesIncludedInResults.get("CUSTOMER").add("SURNAME");
//		attributesIncludedInResults.put("BOOK", new LinkedHashSet<String>());
//		attributesIncludedInResults.get("BOOK").add("ID");
//		attributesIncludedInResults.get("BOOK").add("TITLE");
//		attributesIncludedInResults.get("BOOK").add("ISBN");
//		attributesIncludedInResults.put("REVIEW", new LinkedHashSet<String>());
//		attributesIncludedInResults.get("REVIEW").add("CUSTOMER");
//		attributesIncludedInResults.get("REVIEW").add("BOOK");
//		attributesIncludedInResults.get("REVIEW").add("TITLE");
//		attributesIncludedInResults.get("REVIEW").add("BODY");
		
//		Book book = new Book.Builder().withId(new Id("b7441b2a-0739-3641-a78f-1d973daee854")).build();
		//http://localhost:8080/BookStore/BookTestCtrl
//		PrintWriter out = response.getWriter();
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
		Customer customer = new Customer.Builder().withId(new Id("f86e4678-f6af-30d6-82ef-e9b4792e8669")).build();
		new BookDAO().newQueryRequest()
				.includeAllAttributesInResultFromSchema()
				.queryAttribute()
				.whereBook()
				.isBook("b7441b2a-0739-3641-a78f-1d973daee854")
				.executeQuery()
				.executeCompilation()
				.compileBooks().stream().map(abook->abook.toJson()).forEach(System.out::println);
		
		
		System.out.println(Boolean.toString(new CustomerDAO().loginCustomer("SPhillips4588","Scottpassword").isLoggedOn()));
//        CustomerDAO customerDAO = new CustomerDAO();
//        DataObjectCompiler customerResults=
//        customerDAO.newQueryRequest()
//		.includeAllAttributesInResultFromSchema()
//		.queryPurchaseOrder()
//		.includeAllAttributesInResultFromSchema()
//		.queryAttribute()
//		.wherePurchaseOrderCustomer()
//		.isCustomer(customer)
//		.queryBook()
//		.includeAllAttributesInResultFromSchema()
//		.queryReviews()
//		.includeAllAttributesInResultFromSchema()
//		.executeQuery()
//		.executeCompilation()
//        ;
//        customerResults.compileCustomers();
//        System.out.println(customerResults.getCompiledCustomersJson());
//        
//        Customer loggedCustomer =customerDAO.loginCustomer("SPhillips4588","Scottpassword");
//        System.out.println(Boolean.toString(loggedCustomer.isLoggedOn()));
//        System.out.println(loggedCustomer.toJson());
        
//        
//        new BookDAO()
//        .newQueryRequest()
//        .queryAttribute()
//        .whereBook()
//        .isBook("b7441b2a-0739-3641-a78f-1d973daee854")
//        .queryReview()
//        .queryAttribute()
//        .whereBookTitle()
//        .queryAsDisjunction()
//        .varCharContainsIgnoreCase("queen of      air")
//        .queryAttribute()
//        .whereBookCategory()
//        .queryAsDisjunction()
//        .varCharContains("a")
//        .executeQuery()
//        .executeCompilation()
//        .compileBooks()
//        .stream()
//        .map(abook->abook.toJson()).forEach(System.out::println);
//        out.write(customerResults.getCompiledCustomersJson());
//        
//        System.out.println("get req:");
//        
//		BookDAO bookDAO= new BookDAO();
//		bookDAO.newQueryRequest()
//		.includeAllAttributesInResultFromSchema()
//		.excludeBookDescriptionInResult()
//		.excludeBookPriceInResult()
//		.queryAttribute()
//		.whereBookAuthor()
//		.varCharContains("Tolkien")
//		.queryAttribute()
//		.whereBookAmountSold()
//		.withAscendingOrderOf()
//		.withResultLimit(50)
//		.withPageNumber(3)
//		.queryReviews()
//		.includeAllAttributesInResultFromSchema()
//		.queryAttribute()
//		.whereReviewRating()
//		.numberAtLeast("5")
//		.queryCustomers()
//		.includeAllAttributesInResultFromSchema()
//		.excludeCustomerPasswordInResult()
//		.executeQuery()
//		.executeCompilation()
//		.compileBooks()
//		.stream().map(abook->abook.toJson()).forEach(json->out.print(json));
//		
//		new CustomerDAO().newQueryRequest()
//		.includeAllAttributesInResultFromSchema()
//		.queryReviews()
//		.includeAllAttributesInResultFromSchema()
//		.queryAttribute()
//		.whereReviewRating()
//		.numberAtLeast("3")
//		.withAscendingOrderOf()
//		.queryCustomers()
//		.queryPurchaseOrder()
//		.includeAllAttributesInResultFromSchema()
//		.queryAttribute()
//		.wherePurchaseOrderStatus()
//		.isOrdered()
//		.executeQuery()
//		.executeCompilation()
//		.compileBooks();
//        BookDAO bookDAO =  new BookDAO();
//        DataObjectCompiler bookQueryDOC = 
//        bookDAO.newQueryRequest()
//        .includeBookCoverInResult()
//        .includeBookPriceInResult()
//        .executeQuery()
//        .executeCompilation(); 
        //THIS METHOD RETURNS A DataObjectCompiler object associated with your query. It is useful to assign a variable for it
        //so you can use your query results multiple times without making additional queries
//        
//        bookQueryDOC.compileBooks(); //ex: a list of books
//        out.write(bookQueryDOC.getCompiledBooksJson()); //ex: that compiled results to JSON, books have to be compiled first to get results

//		
//		
//
//		
//		
//		
//
//
//
//		out.flush();
//		
//		bookDAO.newUpdateRequest().requestNewBookInsertion()
//		.insertBookWithTitle(getServletInfo())
//		.insertBookWithSeries(getServletInfo())
//		.insertBookWithCategory(getServletInfo())
//		.insertBookWithAuthor(getServletInfo())
//		.insertBookWithDescription(getServletInfo())
//		.insertBookWithPublishYear(0)
//		.insertBookWithCover(null)
//		.insertBookWithPrice(serialVersionUID)
//		.insertBookWithISBN(getServletInfo())
//		.executeBookInsertion();
//		
//
//



		
//		DataObjectCompiler dataObjectCompiler = new DataObjectCompiler(query, attributesIncludedInResults);
//		dataObjectCompiler.executeCompilation();
//		System.out.println("compiled bookes");
//		dataObjectCompiler.compileBooks().stream().map(book->book.toJson()).forEach(System.out::println);
//		System.out.println("customers");
//		dataObjectCompiler.compileCustomers().stream().map(cust->cust.toJson()).forEach(System.out::println);
		//		List<String> bookInserts= new ArrayList<String>();
//		try {
//			ids = new HashSet<String>(Files.readAllLines(new File("bookIds.txt").toPath()));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			bookInserts=Files.readAllLines(new File("poInserts.txt").toPath());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		FileWriter writer=null;
//		List<String> formatBookInserts = new ArrayList<String>();
//		Connection connection= null;
//		PreparedStatement preparedStatement=null;
//		List<String> inserted=new ArrayList<String>();
//		try {
//		DataSource dataSource= (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
//			int count=0;
//			for(String insert:bookInserts) {//|[^:]|[^,]|[^']|[^\"]|[^?]|[^.]|\\S
//				String format = insert.replaceAll("','","_-_-_-_").replace("'", "''").replaceAll("[^[\\s][.][?]['][\"][,]0-9A-Za-z_-]", "");
//				format=format.replaceAll("_-_-_-_","','");
//				format=format.replaceAll("\",","',");
//				format=format.substring(1,format.length());
//				format="('"+format+")";
//				String update ="INSERT INTO PURCHASE_ORDER (ID, BOOK,STATUS,AMOUNT,CREATED_AT_EPOCH) VALUES "+insert;
//				System.out.println(update);
//				connection= dataSource.getConnection();
//				preparedStatement = connection.prepareStatement(update);
//				preparedStatement.executeUpdate();
//				inserted.add(insert);
//				
//			}
//			
//			
//			inserted.forEach(System.out::println);
//			
//			
//
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				System.out.println("ERRORR FIRSTTTTT");
//				inserted.forEach(System.out::println);
//				e.printStackTrace();
//			} catch (NamingException e) {
//				// TODO Auto-generated catch block
//				System.out.println("ERRORR FIRSTTTTT");
//				inserted.forEach(System.out::println);
//				e.printStackTrace();
//			}finally {
//				if(connection!= null) {
//					try {
//						connection.close();
//						System.out.println("ERRORR FIRSTTTTT");
//						inserted.forEach(System.out::println);
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						System.out.println("ERRORR FIRSTTTTT");
//						inserted.forEach(System.out::println);
//						e.printStackTrace();
//					}
//				}
//				if(preparedStatement!=null) {
//					try {
//						preparedStatement.close();
//						System.out.println("ERRORR FIRSTTTTT");
//						inserted.forEach(System.out::println);
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						System.out.println("ERRORR FIRSTTTTT");
//						inserted.forEach(System.out::println);
//						e.printStackTrace();
//					}
//				}
//
//			}
//		
		// TODO Auto-generated method stub
//		BookDAO bookDAO=new BookDAO();
//		System.out.println(
//		 bookDAO.newQueryRequest()
//		.includeAttributesInResults("TITLE","CATEGORY","PRICE")
//		.accessAttributes("TITLE")
//		.wordContains("a")
//		.queryReviews()
//		.accessAttributes("TITLE")
//		.wordContains("title")
//		.executeRequest()
//		.toJson()
//		);
////
//		DataSource dataSource = null;
//		Connection connection = null;
//		PreparedStatement preparedStatement= null;
//		ResultSet resultSet= null;

//		try {
//			dataSource = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
//			connection = dataSource.getConnection();
//			preparedStatement= connection.prepareStatement("SELECT BOOK.ID AS BOOK_ID, REVIEW.BOOK AS REVIEW_BOOK, BOOK.TITLE AS BOOK_TITLE, REVIEW.TITLE AS REVIEW_TITLE FROM BOOK INNER JOIN REVIEW ON BOOK.ID = REVIEW.BOOK");
//			resultSet= preparedStatement.executeQuery();
//			
//			while(resultSet.next()){
//				System.out.println("book title: "+ resultSet.getString("BOOK_TITLE")+"   "+"review title: "+ resultSet.getString("REVIEW_TITLE"));	
//			}
//			
//		}  catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NamingException e) {
//				// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally {			
//				
//				try {
//					if(resultSet!=null)resultSet.close();
//					if(preparedStatement!=null)preparedStatement.close();
//					if(connection!=null)connection.close();
//				}catch (Exception e) {
//					e.printStackTrace();
//				}	
//				
//				
//		}
//

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
