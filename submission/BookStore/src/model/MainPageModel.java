package model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data.beans.Address;
import data.beans.Book;
import data.beans.Customer;
import data.beans.PurchaseOrder;
import data.beans.Review;
import data.dao.BookDAO;
import data.dao.CustomerDAO;
import data.dao.PurchaseOrderDAO;
import data.dao.ReviewDAO;


public class MainPageModel {
	
	public static MainPageModel instance;
	private BookDAO book;
	private CustomerDAO user;
	private PurchaseOrderDAO order;
	private ReviewDAO review;
	
	public MainPageModel() {};
	
	/**
	 * Get the book instance of a specific book id
	 * 
	 * @param prodID - unique id of book
	 * @return a book instance
	 * @throws Exception if id not found or not unique
	 */
	public Book getBookByID (String prodID) throws Exception{
		
		List<Book> b = book.newQueryRequest()
				 .includeAllAttributesInResultFromSchema()
				 .queryAttribute()
				 .whereBook()
				 .isBook(prodID)
				 .executeQuery()
				 .executeCompilation()
				 .compileBooks();
		
		System.out.println(b.size());
		
		if (b.size() == 0)
			throw new Exception("ID not found in database");
		else if (b.size() > 1)
			throw new Exception("There seem to be ID duplicaes. Aborting!");
		
		return b.get(0);
	}
	
	/**
	 * Get the number of books in each category
	 * 
	 * @return
	 * 		a map containing the genre of the book and the number of books in that genre
	 */
	public Map <String, Integer> getBestSellerBooksByCategory () {
		return book.getCountPerCategory();
	}
	
	/**
	 * Get the 20 most recommended books in a specific category
	 * 
	 * @param category - a specific genre
	 * @return
	 * 		a list of most recommended books in that category
	 */
	public List<Book> getBooksInThisCategory (String category) {
		return  book.newQueryRequest()
				.includeAllAttributesInResultFromSchema()
				.queryAttribute()
				.whereBookCategory()
				.varCharEquals(category)
				.queryAttribute()
				.whereBookRating()
				.withDescendingOrderOf()
				.withResultLimit(20)
				.executeQuery()
				.executeCompilation()
				.compileBooks();
	}
	
	/**
	 * Get a list of the next reviews to display to the user
	 * 
	 * @param prodID - the unique id of a book
	 * @param currentNumReviews - current number of displayed reviews 
	 * @return
	 * 		a list of new reviews to load
	 */
	public Book getReviewsForThisBook (String prodID, boolean isRestrict, int restrict) {
		
		int num = 0;
		
		if (isRestrict) 
			num = restrict; 
		else
			num = Integer.MAX_VALUE;
		
		try {
			this.getBookByID("941cc27e-d5e7-3ab6-bb3d-c7891b5b50f7");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR: Didn't find the book!");
			e.printStackTrace();
		}
		
		List<Book> b = book.newQueryRequest()
			.includeAllAttributesInResultFromSchema()
			.queryAttribute()
			.whereBook()
			.isBook(prodID)
			.queryReview()
			.includeAllAttributesInResultFromSchema()
			.queryAttribute()
			.whereReviewRating()
			.withResultLimit(num)
			.withDescendingOrderOf()
			.executeQuery()
			.executeCompilation()
			.compileBooks();
		
		if (b.size() == 0)
			System.out.println("Couln't find book id: "+prodID + " in Book");
		
		return b.get(0);
	}
	
	/**
	 * Finds books where the input appears in their title, author's name, description or category
	 * 
	 * 
	 * @param input - input in the search bar
	 * @return
	 */
	public List<Book>  prepSearchResult (String input) {
		
		int maxNum = book.getNumberBooks();
		
		
		
		List<Book> b= book.newQueryRequest()
						.includeAllAttributesInResultFromSchema()
						
						.queryAttribute()
						.whereBookCategory()
//						.queryAsDisjunction()
						.varCharContainsIgnoreCase(input)
//						.varCharContains(input)
//						
//						.queryAttribute()
//						.whereBookAuthor()
//						.queryAsDisjunction()
//						.varCharContains(input)
//						
//						.queryAttribute()
//						.whereBookAuthor()
//						.queryAsDisjunction()
//						.varCharContains(input)
//						
//						.queryAttribute()
//						.whereBookDescription()
//						.queryAsDisjunction()
//						.varCharContains(input)
						
						.withResultLimit(book.getNumberBooks())
						.executeQuery().
						executeCompilation().
						compileBooks();
						
		List<Book> b2 = book.newQueryRequest()
						.includeAllAttributesInResultFromSchema()		
						.queryAttribute()
						.whereBookAuthor()
						.varCharContainsIgnoreCase(input)
						.withResultLimit(maxNum)
						.executeQuery().
						executeCompilation().
						compileBooks();
		
		List<Book> b3 = book.newQueryRequest()
						.includeAllAttributesInResultFromSchema()	
						.queryAttribute()
						.whereBookTitle()
						.varCharContainsIgnoreCase(input)
						.withResultLimit(maxNum)
						.executeQuery().
						executeCompilation().
						compileBooks();
		List<Book> b4 = book.newQueryRequest()
						.includeAllAttributesInResultFromSchema()	
						.queryAttribute()
						.whereBookDescription()
						.varCharContainsIgnoreCase(input)
						.withResultLimit(maxNum)
						.executeQuery().
						executeCompilation().
						compileBooks();
		
		b.addAll(b2);
		b.addAll(b3);
		b.addAll(b4);
		
		return b;
	}
	
	/**
	 * Identifies the customer with username and password, then finds all the order
	 * transactions and books related to that customer
	 * 
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	public PurchaseOrder[] getCustomerOrders(Customer customer) throws Exception{
		List<PurchaseOrder> p = null;
		Map<Long, PurchaseOrder> m;
		PurchaseOrder[] p1;
		try {
//			System.out.println("here! 1 ");
//			System.out.println("here! 2");
			
			List<Customer> c = user.newQueryRequest()
					.includeAllAttributesInResultFromSchema()
					.queryPurchaseOrder()
					.queryAttribute()
					.wherePurchaseOrderCustomer()
					.isCustomer(customer)
					.withResultLimit(Integer.MAX_VALUE)
					.executeQuery()
					.executeCompilation()
					.compileCustomers()
					;
			p1 = c.get(0).getPurchaseOrders();
			
			//p1 = s.getPurchaseOrders();
//			System.out.println("here! 3");
			
//			Iterator iterator = m.entrySet().iterator(); 
//			
//			System.out.println("here! 4 iterator = "+iterator.hasNext());
//			
//			if (iterator.hasNext()) {
//				Map.Entry me = (Map.Entry) iterator.next(); 
//				p.add((PurchaseOrder) me.getValue());
//			}
			
			System.out.println("p.size() = "+p.size());
		} catch (Exception e) {
			throw new Exception("Couldn't find username and password in the database!");
		}
		return p1;
	}
	
	/**
	 * Adds a review of the customer
	 * 
	 * @param customer
	 * @param title
	 * @param body
	 * @param rate
	 * @param book_id
	 * @throws Exception
	 */
	public void addReview (Customer customer, String title, String body, int rate, String book_id) throws Exception{
		
		Book b = getBookByID(book_id);
						
		review.newUpdateRequest()
			.requestNewReviewInsertion(customer, b)
			.insertReviewWithTitle(title)
			.insertReviewWithBody(body)
			.insertReviewWithRating(rate)
			.executeReviewInsertion();
	}
	
	/**
	 * Checks if the customer reviewed the product previously or not
	 * 
	 * @param customer
	 * @param book_id
	 * @return
	 * @throws Exception
	 */
	public boolean didCustomerAddReview (Customer customer, String book_id) throws Exception {
		
		List<Book> b = review.newQueryRequest()
				.includeAllAttributesInResultFromSchema()
				.queryAttribute()
				.whereReviewCustomer()
				.isCustomer(customer)
				.queryBook()
				.queryAttribute()
				.whereBook()
				.isBook(book_id)
				.executeQuery()
				.executeCompilation()
				.compileBooks();
		
		if (b.size() > 1)
			throw new Exception ("At most 1 book can be associated with a user review!");
		
		return (b.size() == 1);
	}
	
	/**
	 * Gived the reviewID, it returns the Review object which identifies 
	 * with that id
	 * 
	 * @param customer
	 * @param bookID
	 * @return
	 * @throws Exception 
	 */
	public List<Review> getReview (Customer customer, String bookID) throws Exception {
		
		Book b = this.getBookByID(bookID);
		
		List<Customer> s = review.newQueryRequest()
			.includeAllAttributesInResultFromSchema()
			.queryAttribute()
			.whereReviewBook()
			.isBook(b)
			.queryAttribute()
			.whereReviewCustomer()
			.isCustomer(customer)
			.executeQuery()
			.executeCompilation()
			.compileCustomers();
			
		return s.get(0).getReviews();
	}
	
	public Customer getUser (String username, String passwd) {
		return user.loginCustomer(username, passwd);
	}
	
	/**
	 * Updates the user entry 
	 * 
	 * @param username
	 * @param passwd
	 * @param customer
	 * @param name
	 * @param last_name
	 * @param email
	 * @param street
	 * @param street_num
	 * @param city
	 * @param province
	 * @param country
	 * @param postal_code
	 * @throws Exception
	 */
	public void updateUserInfo (String username, String passwd, 
								Customer customer, String name,
								String last_name, String email,
								String street, String street_num, 
								String city, String province, 
								String country, String postal_code) throws Exception {
		
		
		List<Customer> c = user.newQueryRequest()
				.includeAllAttributesInResultFromSchema()
				.queryAttribute()
				.whereCustomerUserName()
				.varCharEquals(username)
				.executeQuery()
				.executeCompilation()
				.compileCustomers();
		
		// username doesn't exist or stays the same for the same user
		if (c.size() == 0 || c.size() == 1 && c.get(0).getId().isEqual(customer.getId())) {
			user.newUpdateRequest()
			.requestUpdateExistingCustomer(customer)
			.updateCustomerUserName(username)
			.executeUpdate();
			
		} else {
			throw new Exception ("Problem updating information - "
					+ "there exists a username in the database with the same "
					+ "usrname. Try a different name!");
		}
		
		c = user.newQueryRequest()
			.includeAllAttributesInResultFromSchema()
			.queryAttribute()
			.whereCustomerEmail()
			.varCharEquals(email)
			.executeQuery()
			.executeCompilation()
			.compileCustomers();
		
		if (c.size() == 0 || c.size() == 1 && c.get(0).getId().isEqual(customer.getId())) {
			user.newUpdateRequest()
			.requestUpdateExistingCustomer(customer)
			.updateCustomerEmail(email)
			.executeUpdate();
		} else 
			throw new Exception ("Problem updating information - "
					+ "this email already exists in the database. Try a different one or login with "
					+ "the other account!");
	
		
		try {
			user.newUpdateRequest()
				.requestUpdateExistingCustomer(customer)
				.updateCustomerCity(city)
				.updateCustomerCountry(country)
				.updateCustomerGivenName(name)
				.updateCustomerPassword(passwd)
				.updateCustomerPostalCode(postal_code)
				.updateCustomerProvince(province)
				.updateCustomerStreet(street)
				.updateCustomerStreetNumber(street_num)
				.updateCustomerSurName(last_name)
				.executeUpdate()
				;
		} catch (Exception e) {
			throw new Exception("Troble updating the information! " + e.getMessage());
		}
	}
	
	//getInstance will return that ONE instance of the pattern 
	//with the the DAO objects initialized..
	public static MainPageModel getInstance()throws ClassNotFoundException{
		if (instance==null) {
			instance =new MainPageModel();
			instance.book = new BookDAO();
			instance.review = new ReviewDAO();
			instance.user = new CustomerDAO();
		}
		return instance;
	}
}
