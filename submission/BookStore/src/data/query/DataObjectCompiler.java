package data.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import data.beans.Book;
import data.beans.Cart;
import data.beans.Customer;
import data.beans.Id;
import data.beans.PurchaseOrder;
import data.beans.Review;
import data.beans.Visitor;
import data.fetcher.BookDataFetcher;
import data.fetcher.CartDataFetcher;
import data.fetcher.CustomerDataFetcher;
import data.fetcher.PurchaseOrderDataFetcher;
import data.fetcher.ReviewDataFetcher;
import data.fetcher.VisitorDataFetcher;
import data.schema.BookSchema;
import data.schema.CartSchema;
import data.schema.CustomerSchema;
import data.schema.PurchaseOrderSchema;
import data.schema.ReviewSchema;
import data.schema.VisitorSchema;

public class DataObjectCompiler {
	String queryString;
	DataSource dataSource;
	Map<String,Set<String>> attributesIncludedInResults;
	private final String bookTableName = new BookSchema().tableName();
	private final  String customerTableName  = new CustomerSchema().tableName();
	private final  String reviewTableName = new ReviewSchema().tableName();
	private final  String purchaseOrderTableName = new PurchaseOrderSchema().tableName();
	private final  String cartTableName = new CartSchema().tableName();
	private final  String visitorTableName = new VisitorSchema().tableName();
	private String buildOrder;
	private static final String DELIMITER= ">";
	private Map<Id,Book>   bookResults;
	private Map<String,Review>   reviewResults;
	private Map<Id,List<Review>>   customerReviewResults;
	private Map<Id,List<Review>>   bookReviewResults;
	private Map<Id,Visitor>   visitorResults;
	private Map<Id,Customer>   customerResults;
	private Map<Id,Cart>   cartResults;
	private Map<Id,List<PurchaseOrder>>   purchaseOrderResults;
	private BookDataFetcher bookDataFetcher;
	private CartDataFetcher cartDataFetcher;
	private CustomerDataFetcher customerDataFetcher;
	private PurchaseOrderDataFetcher purchaseOrderDataFetcher;
	private VisitorDataFetcher visitorDataFetcher;
	private ReviewDataFetcher reviewDataFetcher;
	
	private void writeBuildOrder(String name) {
		this.buildOrder+=!buildOrder.contains(name)?name+DELIMITER:"";
	}
	
	private boolean hasBuildOrder(String ...names) {
		String order="";
		for(String name:names) {
			order+=name+DELIMITER;
		}	
		return this.buildOrder.contains(order);
	}
	

	private boolean isLastBuildStep(String name) {	
		return this.buildOrder.endsWith(name+DELIMITER);
	}
	
	private boolean isFirstBuildStep(String name) {	
		return this.buildOrder.startsWith(name);
	}
	
	public DataObjectCompiler(String queryString,Map<String,Set<String>> attributesIncludedInResults) {
//		this.queryString=queryString;
		try {
			this.dataSource=(DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.queryString=queryString;
		this.attributesIncludedInResults=attributesIncludedInResults;
		this.buildOrder="";
		this.bookResults=new LinkedHashMap<Id, Book>();
		this.reviewResults=  new LinkedHashMap<String, Review>();
		this.visitorResults= new LinkedHashMap<Id, Visitor>();
		this.customerResults= new LinkedHashMap<Id, Customer>();
		this.cartResults= new LinkedHashMap<Id, Cart>();
		this.purchaseOrderResults= new LinkedHashMap<Id, List<PurchaseOrder>>();
		this.customerReviewResults=new LinkedHashMap<Id, List<Review>>();
		this.bookReviewResults= new LinkedHashMap<Id, List<Review>>();
		bookDataFetcher=new BookDataFetcher(this.attributesIncludedInResults);
		cartDataFetcher=new CartDataFetcher(this.attributesIncludedInResults);
		customerDataFetcher=new CustomerDataFetcher(this.attributesIncludedInResults);
		purchaseOrderDataFetcher=new PurchaseOrderDataFetcher(this.attributesIncludedInResults);
		visitorDataFetcher= new VisitorDataFetcher(this.attributesIncludedInResults);
		reviewDataFetcher= new ReviewDataFetcher(this.attributesIncludedInResults);
	}
	
	protected List<Review> compileReviewsFromBook(Book book){
		List<Review> results = new ArrayList<Review>();
		
		return results;
	}
	
	protected List<Review> compileReviewsFromCustomer(Customer customer){
		List<Review> results = new ArrayList<Review>();
		
		return results;
	}
	
	public List<Visitor> compileVisitors(){
		return null;
	}
	
	
	public List<Customer> compileCustomers(){
		List<Customer> results = new ArrayList<Customer>();
		for(Entry<Id,Customer> entry:this.customerResults.entrySet()) {
			List<PurchaseOrder> customerPo=new ArrayList<PurchaseOrder>();
			if(this.purchaseOrderResults.containsKey(results)) {
				for(PurchaseOrder purchaseOrder:this.purchaseOrderResults.get(entry.getKey())) {
					customerPo.add(new PurchaseOrder.Builder(purchaseOrder).withInCustomer().build()) ;	
				}
			}
			List<Review> customerReviews=new ArrayList<Review>();
			if(this.customerReviewResults.containsKey(results)) {
				for(Review review:this.customerReviewResults.get(entry.getKey())) {
					Book book = new Book.Builder(review.getBook()).build();
					if(this.bookResults.containsKey(review.getBook().getId())) {
						book=this.bookResults.get(review.getBook().getId());
						book = new Book.Builder(book).withInReview().build();
					}
					customerReviews.add(new Review.Builder(review).withBook(book).withinCustomer().build());
				}				
			}
			
			Cart customerCart=new Cart.Builder().withId(entry.getKey()).build();
			if(this.cartResults.containsKey(results)) {
				customerCart=new Cart.Builder(this.cartResults.get(entry.getKey())).withInCustomer().build();
				Map<Book,Integer> cartBooks=new LinkedHashMap<Book, Integer>();
				for(Entry<Book,Integer> bookEntry:customerCart.getBooks().entrySet()) {
					Book book = new Book.Builder(bookEntry.getKey()).withInReview().build();
					List<Review> cartBookReviews=new ArrayList<Review>();
					if(this.bookReviewResults.containsKey(results)) {
						for(Review review:this.bookReviewResults.get(bookEntry.getKey())) {
							cartBookReviews.add(new Review.Builder(review).withinCustomer().withinBook().build());
						}
					}
					if(this.bookResults.containsKey(bookEntry.getKey().getId())) {
						book=this.bookResults.get(bookEntry.getKey().getId());
						book = new Book.Builder(book).build();
						if(!cartBookReviews.isEmpty()) {
							book= new Book.Builder(book).withReviews(cartBookReviews).build();
						}
						cartBooks.put(book, customerCart.getBooks().get(book));
					}
					
				}	

				if(!cartBooks.isEmpty()) {
					customerCart= new Cart.Builder(customerCart).withBooks(cartBooks).withInCustomer().build();
				}
			}
			
			
			
			results.add(new Customer.Builder(entry.getValue())
					.withCart(customerCart)
					.withPurchaseOrders(customerPo)
					.withReviews(customerReviews)
					.build()
					);
		}

		return results;
	}

	
	public List<Book> compileBooks(){
		List<Book> results = new ArrayList<Book>();
		
//		if(attributesIncludedInResults.containsKey(bookTableName)) {
//			
//		}else {
//			return new ArrayList<Book>();
//		}
		Map<Id,Book> compiledBookResults = new LinkedHashMap<Id, Book>(this.bookResults);
		for(Entry<Id,Book> entry:bookResults.entrySet()) {
			System.out.println("builll boks id :" +entry.getKey().toString());
			List<Review> reviews=new LinkedList<Review>();
			if(bookReviewResults.containsKey(entry.getKey())) {
				reviews=bookReviewResults.get(entry.getKey());
			}
			Map<Id,List<Review>> reviewOfBooks=new LinkedHashMap<Id, List<Review>>();
			
			for(Review review:reviews) {
				Id bookId=entry.getKey();

				if(!reviewOfBooks.containsKey(bookId) || reviewOfBooks.get(bookId)==null) {
					reviewOfBooks.put(bookId, new LinkedList<Review>());
				}
				Customer customer =new Customer.Builder(review.getCustomer()).withinReview().build();
				if(customerResults.containsKey(review.getCustomer().getId())) {
					customer =new Customer.Builder(customerResults.get(review.getCustomer().getId())).withinReview().build();
				}
				
				reviewOfBooks.get(bookId).add(new Review.Builder(review).withCustomer(customer).withinBook().build());
				
			}

			compiledBookResults.replace(entry.getKey(), new Book.Builder(entry.getValue()).withReviews(reviewOfBooks.get(entry.getKey())).build());	
			
		}
		for(Entry<Id,Book> entry:compiledBookResults.entrySet()) {
			results.add(entry.getValue());
		}
		return results;
	}
	
//	public Map<Id,Review> buildCustomerReview(){
//		Map<Id,Review> result = new LinkedHashMap<Id, Review>();
//		
//	}
//	
//	public Map<Id,Review> buildBookReview(){
//		Map<Id,Review> result = new LinkedHashMap<Id, Review>();
//		
//	}
//	
//	public List<Review> buildReview(){
//		List<Book> results = new ArrayList<Book>();
//		
//		
//		for(Entry<Id,Book> entry:bookResults.entrySet()) {
//			if(bookReviewResults.containsKey(entry.getKey())) {
//				bookResults.replace(entry.getKey(), new Book.Builder(entry.getValue()).withAdditionalReviews(bookReviewResults.get(entry.getKey())).build());				
//			}
//		}
//		
//		if(attributesIncludedInResults.containsKey(reviewTableName)) {
//			
//		}
//	}
	
	DataObjectCompiler thenBuildBooks() {
	if(!attributesIncludedInResults.containsKey(bookTableName)) return this;
	writeBuildOrder(bookTableName);
	return this;			
	}
	
	
	DataObjectCompiler thenBuildReview() {
		if(!attributesIncludedInResults.containsKey(reviewTableName)) return this;
		writeBuildOrder(reviewTableName);
		return this;
	}
	
	DataObjectCompiler thenBuildCustomer() {
		if(!attributesIncludedInResults.containsKey(customerTableName)) return this;
		writeBuildOrder(customerTableName);
		return this;
	}
	
	DataObjectCompiler thenBuildVisitors() {
		if(!attributesIncludedInResults.containsKey(visitorTableName)) return this;
		writeBuildOrder(visitorTableName);
		return this;
	}
	
	DataObjectCompiler thenBuildCarts() {
		if(!attributesIncludedInResults.containsKey(cartTableName)) return this;
		writeBuildOrder(cartTableName);
		return this;
	}
	
	DataObjectCompiler thenBuildPurchaseOrder() {
		if(!attributesIncludedInResults.containsKey(purchaseOrderTableName)) return this;
		writeBuildOrder(purchaseOrderTableName);
		return this;
	}
	private List<String> getBuildOrderAsList(){		
		return Arrays.asList(this.buildOrder.split(DELIMITER));
	}
	
	public 	DataObjectCompiler executeCompilation(){
		Queue<String> buildQueue = new LinkedList<String>(getBuildOrderAsList());
		//while result set not empty
		Connection connection= null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try {
			connection= this.dataSource.getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			resultSet= preparedStatement.executeQuery();
			while(resultSet.next()) {
				if(attributesIncludedInResults.containsKey(bookTableName)) {
					Book book=bookDataFetcher.resultSetToBean(resultSet);
					bookResults.put(book.getId(), book);
				}
				if(attributesIncludedInResults.containsKey(customerTableName)) {
					Customer customer=customerDataFetcher.resultSetToBean(resultSet);
					customerResults.put(customer.getId(), customer);
				}
				if(attributesIncludedInResults.containsKey(visitorTableName)) {
					Visitor visitor=visitorDataFetcher.resultSetToBean(resultSet);
					visitorResults.put(visitor.getId(), visitor);
					
				}
				if(attributesIncludedInResults.containsKey(cartTableName)) {
					Cart cart=cartDataFetcher.resultSetToBean(resultSet);
					if(cartResults.containsKey(cart.getId())) {
						Cart oldCart = cartResults.get(cart.getId());
						cartResults.replace(cart.getId(),new Cart.Builder(oldCart).withAdditionalBooks(cart.getBooks()).build());
					}else {
						cartResults.put(cart.getId(), cart);	
					}
					
				}
				
				if(attributesIncludedInResults.containsKey(purchaseOrderTableName)) {
					PurchaseOrder purchaseOrder=purchaseOrderDataFetcher.resultSetToBean(resultSet);
					Book[] resultBook=new Book[0];
					purchaseOrder.getBooks().keySet().toArray(resultBook);
					String resultBookId =resultBook[0].getId().toString();
					String epoch = Long.toString(purchaseOrder.getCreatedAtEpoch());
					Id customerId= purchaseOrder.getCustomer().getId();
					String purchaseOrderkey = customerId+resultBookId+epoch;
					if(!purchaseOrderResults.containsKey(customerId)) {
						this.purchaseOrderResults.put(customerId, new ArrayList<PurchaseOrder>());
					}
					purchaseOrderResults.get(customerId).add(purchaseOrder);
					
				}
				
				if(attributesIncludedInResults.containsKey(reviewTableName)) {
					Review review=reviewDataFetcher.resultSetToBean(resultSet);
					Id reviewBookId =review.getBook().getId();
					Id reviewCustomerId=review.getCustomer().getId();
//					String reviewKey=reviewCustomerId+reviewBookId;
//					reviewResults.put(reviewKey, review);	
					if(!customerReviewResults.containsKey(reviewCustomerId)||customerReviewResults.get(reviewCustomerId)==null) {
						customerReviewResults.put(reviewCustomerId, new LinkedList<Review>());
					}
					customerReviewResults.get(reviewCustomerId).add(review);
					
					if(!bookReviewResults.containsKey(reviewBookId)||bookReviewResults.get(reviewBookId)==null) {
						bookReviewResults.put(reviewBookId, new LinkedList<Review>());
					}
					bookReviewResults.get(reviewBookId).add(review);
				}
				
			}
			Map<Id,List<PurchaseOrder>> combinedPo = new LinkedHashMap<Id, List<PurchaseOrder>>();
			for(Entry<Id,List<PurchaseOrder>> entry:purchaseOrderResults.entrySet()) {
				Map<Long,PurchaseOrder> epochPo=new LinkedHashMap<Long, PurchaseOrder>();
				for(PurchaseOrder purchaseOrder:entry.getValue()) {
					if(epochPo.containsKey(purchaseOrder.getCreatedAtEpoch())){
						PurchaseOrder purchaseOrderEpoch= epochPo.get(purchaseOrder.getCreatedAtEpoch());
						epochPo.replace(purchaseOrder.getCreatedAtEpoch(), new PurchaseOrder.Builder(purchaseOrderEpoch).withAdditionalBooks(purchaseOrder.getBooks()).build());
					}else {
						epochPo.put(purchaseOrder.getCreatedAtEpoch(), purchaseOrder);
					}
				}
				for(Entry<Long,PurchaseOrder> entryPoEpoch:epochPo.entrySet()) {
					if(!combinedPo.containsKey(entry.getKey())||combinedPo.get(entry.getKey())==null) {
						combinedPo.put(entry.getKey(), new LinkedList<PurchaseOrder>());
					}
					combinedPo.get(entry.getKey()).add(entryPoEpoch.getValue());
				}
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(connection!= null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(preparedStatement!=null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
//		bookReviewResults.entrySet().stream().map(entry->entry.getValue()).flatMap(a->a.stream()).map(review->review.getBook().getId().toString()).forEach(System.out::println);
//		customerReviewResults.entrySet().stream().map(entry->entry.getValue()).flatMap(a->a.stream()).map(review->review.getCustomer().getId().toString()).forEach(System.out::println);
//		reviewResults.entrySet().stream().map(entry->entry.getValue()).map(review->review.toJson()).forEach(System.out::println);
//		purchaseOrderResults.entrySet().stream().map(entry->entry.getValue()).flatMap(a->a.stream()).map(po->po.toJson()).forEach(System.out::println);
//		customerResults.entrySet().stream().map(entry->entry.getValue()).map(review->review.toJson()).forEach(System.out::println);
//		bookResults.entrySet().stream().map(entry->entry.getValue()).map(review->review.toJson()).forEach(System.out::println);
//		cartResults.entrySet().stream().map(entry->entry.getValue()).map(review->review.toJson()).forEach(System.out::println);
//		visitorResults.entrySet().stream().map(entry->entry.getValue()).map(review->review.toJson()).forEach(System.out::println);
		return this;
//		while(!buildQueue.isEmpty()) {
//			String currentBuildRequest=buildQueue.poll();
//			
//			
//		}
		//if book>review
		//if review>book
		//if review>customer
		//if customer>review or book or cart or PO... everything goes inside customer
		//if cart>customer
		//if customer>cart
		//PO>BOOK>REVIEW>CUSTOMER
		//CART>BOOK>REVIEW>CUSTOMER
		//CART>BOOK>REVIEW>VISITOR
		//review >> cust>>book = review, with customer and book
		//book>> review >> cust =? book wit reviews, review with cust
//		
//		if(hasBuildOrder(bookTableName,reviewTableName,customerTableName)) {
//			
//		}
//		
//		if(hasBuildOrder(bookTableName,reviewTableName)) {
//			//creates book object and attaches reviews to it
//			
//		}
//		
//		if(hasBuildOrder(reviewTableName,bookTableName,customerTableName) || hasBuildOrder(reviewTableName,customerTableName,bookTableName)) {
//			//creates review obj, and attaches book or customer to it
//			
//		}
//		if(isBuildBefore(customerTableName,cartTableName,purchaseOrderTableName,reviewTableName)) {
//			//creates customer obj and attaches all other sub objects
//		}
//		
//		if(isBuildBefore(cartTableName,bookTableName,customerTableName)) {
//			//creates customer obj and attaches all other sub objects
//		}
//		if(isBuildBefore(cartTableName,bookTableName,visitorTableName)) {
//			//creates customer obj and attaches all other sub objects
//		}
//		
//		if(isBuildBefore(purchaseOrderTableName,bookTableName)) {
//			//creates customer obj and attaches all other sub objects
//		}
		
	}
	

//	
//	private boolean isBuildBefore(String buildStep, String...after) {
//		
//		return false;
//	}
//	
//
//	
//	
//	public List<Book> compileBooks() {
//		List<Book> result= new ArrayList<Book>();
//		Queue<String> buildOrderRequest = new LinkedList<String>(this.buildOrder);
//		
//		while(!buildOrderRequest.peek().equals(bookTableName)) {
//			buildOrderRequest.poll();
//		}
//		
//		//untill resultSet null
//		while(true) {
//			Queue<String> buildOrderCycle = new LinkedList<String>(buildOrderRequest);
//			String currentBuild="";
//			if(!buildOrderCycle.isEmpty()) currentBuild=buildOrderCycle.poll();
//			if(!currentBuild.isEmpty()) {
//				//starts with
//				
//				//contains...
//				
//			}
//		}		
//		
//		return result;
//		
//	}
	
	protected List<Cart> buildCarts() {
		List<Cart> result= new ArrayList<Cart>();
		return result;
	}
	protected List<Customer> buildCustomers() {
		List<Customer> result= new ArrayList<Customer>();
		//if has book, if has review po cart the all get put int
		return result;
	}
	protected List<Visitor> buildVisitors() {
		List<Visitor> result= new ArrayList<Visitor>();
		return result;
	}
	protected List<PurchaseOrder> buildPurchaseOrders() {
		List<PurchaseOrder> result= new ArrayList<PurchaseOrder>();
		return result;
	}
	protected List<Review> buildReviews() {
		List<Review> result= new ArrayList<Review>();
		return result;
	}
//	String buildOrder; //ordered via stack?
//	public String book = new BookSchema().tableName();
//	public String customer  = new CustomerSchema().tableName();
//	public String review = new ReviewSchema().tableName();
//	public String purchaseOrder = new PurchaseOrderSchema().tableName();
//	public String cart = new CartSchema().tableName();
//	public String visitor = new VisitorSchema().tableName();
//	private static final String DELIMITER= ">";
//	private Set<Visitor> visitors;
//	private Set<Customer> customers;
//	private Set<Book> books;
//	private Set<PurchaseOrder> purchaseOrders;
//	private Set<Review> reviews;
//	private Set<Cart> carts;
//	private Set<Bean> currentResults;
//
//	private BookDataFetcher bookDataFetcher;
//	private CustomerDataFetcher customerDataFetcher;
//	private CartDataFetcher cartDataFetcher;
//	private VisitorDataFetcher visitorDataFetcher;
//	private ReviewDataFetcher reviewDataFetcher;
//	private PurchaseOrderDataFetcher purchaseOrderDataFetcher;
//	private String queryString;
//	
//	public Generator(String queryString,List<String> attributesIncludedInResults) {
//		this.visitors=new HashSet<Visitor>();
//		this.customers=new HashSet<Customer>();
//		this.books=new HashSet<Book>();
//		this.purchaseOrders=new HashSet<PurchaseOrder>();
//		this.reviews=new HashSet<Review>();
//		this.carts=new HashSet<Cart>();
//		this.buildOrder="";
//		this.queryString=queryString;
//		
//
//		Connection connection=null;
//		PreparedStatement preparedStatement=null;
//		ResultSet resultSet=null;
//		if(this.queryString==null || this.queryString.isEmpty()) {
//			System.err.println("Warning empty query string was submitted!");
//			return;
//		}
//
//		try {
//			DataSource dataSource= (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
//			connection = dataSource.getConnection();
//			preparedStatement= connection.prepareStatement(this.queryString);
//			resultSet= preparedStatement.executeQuery();
//			while(resultSet.next()){
//				if(resultAttributeIncludesTable(cart)) this.carts.add(this.cartDataFetcher.resultSetToBean(resultSet));
//				if(resultAttributeIncludesTable(customer))this.customers.add(this.customerDataFetcher.resultSetToBean(resultSet));
//				if(resultAttributeIncludesTable(purchaseOrder))this.purchaseOrders.add(this.purchaseOrderDataFetcher.resultSetToBean(resultSet));
//				if(resultAttributeIncludesTable(visitor))this.visitors.add(this.visitorDataFetcher.resultSetToBean(resultSet));
//				if(resultAttributeIncludesTable(book))this.books.add(this.bookDataFetcher.resultSetToBean(resultSet));
////				if(resultAttributeIncludesTable(review))this.reviews.add(this.reviewDataFetcher.resultSetToBean(resultSet));
//				
//			}
//			
//		} catch (SQLException e) {
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
//	}
//	
//	
//	
//
//	
//	private void writeBuildOrder(String name) {
//		this.buildOrder+=!buildOrder.contains(name)?name+DELIMITER:"";
//	}
//	
//	Generator thenBuildBooks() {
//		if(!resultAttributeIncludesTable(book)) return this;
//		if(this.buildOrder.isEmpty() || isLastBuildStep(review) ||isLastBuildStep(cart) || isLastBuildStep(purchaseOrder)) writeBuildOrder(review);
//		return this;			
//	}
//	
//	Generator thenBuildReview() {
//		if(!resultAttributeIncludesTable(review)) return this;
//		if(this.buildOrder.isEmpty() || isLastBuildStep(book)) writeBuildOrder(review);
//		return this;
//	}
//	
//	Generator thenBuildCustomer() {
//		if(!resultAttributeIncludesTable(customer)) return this;
//		if(this.buildOrder.isEmpty() || isLastBuildStep(review)) writeBuildOrder(customer);
//		return this;
//	}
//	
//	Generator thenBuildVisitors() {
//		if(!resultAttributeIncludesTable(visitor)) return this;
//		if(this.buildOrder.isEmpty() || isLastBuildStep(cart)) writeBuildOrder(visitor);
//		return this;
//	}
//	
//	Generator thenBuildCarts() {
//		if(!resultAttributeIncludesTable(cart)) return this;
//		if(this.buildOrder.isEmpty() || isLastBuildStep(customer)|| isLastBuildStep(visitor)) writeBuildOrder(cart);
//		return this;
//	}
//	
//	Generator thenBuildPurchaseOrder() {
//		if(!resultAttributeIncludesTable(purchaseOrder)) return this;
//		if(this.buildOrder.isEmpty() || isLastBuildStep(customer)) writeBuildOrder(purchaseOrder);
//		return this;
//	}
//	
//
//
//	
//	private boolean hasBuildOrder(String ...names) {
//		String order="";
//		for(String name:names) {
//			order+=name+DELIMITER;
//		}	
//		return this.buildOrder.contains(order);
//	}
//	
//	private boolean resultAttributeIncludesTable(String tableName) {
//		return true;
//	}
//	
//	private boolean isLastBuildStep(String name) {	
//		return this.buildOrder.endsWith(name+DELIMITER);
//	}
//	
//
//	
//	public List<Book> getBooks(){
//		List<Book> results = new ArrayList<Book>(this.books);
//		if(hasBuildOrder(book,review)) {
//			Set<Review> reviews = new HashSet<Review>(getReviews());
//			Map<Book,HashSet<Review>> bookToReviews = new HashMap<Book, HashSet<Review>>();
//			for(Review review:reviews) {
//				if(!bookToReviews.containsKey(review.getBook())) {
//					bookToReviews.put(review.getBook(), new HashSet<Review>());
//				}
//				bookToReviews.get(review.getBook()).add(review);
//			}	
//			
//			List<Book> bookWithReview = new ArrayList<Book>();
//			for(Book book: this.books) {
//				Set<Review> reviewsOfBook=bookToReviews.get(book);
//				bookToReviews.remove(book);
//				bookWithReview.add(new Book.Builder(book).withReviews(reviewsOfBook.toArray(new Review[reviewsOfBook.size()])).build());					
//			}
//			results=bookWithReview;
//		}
//		return results;
//	}
//	
//	public List<Review> getReviews(){
//
//		return new ArrayList<Review>(this.reviews);
//	}
//	
//	
//	public List<Customer> getCustomers(){		
//		List<Customer> results = new ArrayList<Customer>(this.customers);
//		
//		if(hasBuildOrder(customer,review)) {
//			Map<Customer,HashSet<Review>> customerToReviews = new HashMap<Customer, HashSet<Review>>();
//			for(Review review:getReviews()) {
//				if(!customerToReviews.containsKey(review.getCustomer())) {
//					customerToReviews.put(review.getCustomer(), new HashSet<Review>());
//				}
//				customerToReviews.get(review.getCustomer()).add(review);
//			}	
//			List<Customer> customerWithReviews=new ArrayList<Customer>();
//			for(Customer customer: results) {
//				Set<Review> reviewsByCustomer=customerToReviews.get(customer);
//				customerWithReviews.add(new Customer.Builder(customer).withReviews(reviewsByCustomer.toArray(new Review[reviewsByCustomer.size()])).build());					
//			}
//			results=customerWithReviews;
//		}
//		
//		if(hasBuildOrder(customer,cart)) {
//			Map<Customer,Cart> customerToCarts = new HashMap<Customer, Cart>();
//			for(Cart cart:getCarts()) {
//				if(!cart.isCustomerCart()) continue;
//				Customer customer = new Customer.Builder().withId(cart.getId()).build();
//				if(!customerToCarts.containsKey(customer)) {
//					customerToCarts.put(customer, cart);
//				}else {
//					customerToCarts.get(customer).combineCarts(cart);
//				}
//
//			}	
//			List<Customer> customerWithCart=new ArrayList<Customer>();
//			for(Customer customer: results) {
//				customerWithCart.add(new Customer.Builder(customer).withCart(customerToCarts.get(customer)).build());					
//			}
//			results=customerWithCart;
//		}
//		
//		
//		if(hasBuildOrder(customer,purchaseOrder)) {
//			Map<Customer,Set<PurchaseOrder>> customerToPurchaseOrders = new HashMap<Customer, Set<PurchaseOrder>>();
//			for(PurchaseOrder purchaseOrder:getPurchaseOrder()) {
//				Customer customer = new Customer.Builder(purchaseOrder.getCustomer()).build();
//				if(!customerToPurchaseOrders.containsKey(customer)) {
//					customerToPurchaseOrders.put(customer, new HashSet<PurchaseOrder>());
//				}
//				customerToPurchaseOrders.get(customer).add(purchaseOrder);
//			}	
//			List<Customer> customerWithPurchseOrder=new ArrayList<Customer>();
//			
//			for(Customer customer: results) {
//				PurchaseOrder[] purchaseOrders= new PurchaseOrder[customerToPurchaseOrders.get(customer).size()];
//				customerToPurchaseOrders.get(customer).toArray(purchaseOrders);
//				customerWithPurchseOrder.add(new Customer.Builder(customer).withPurchaseOrders(purchaseOrders).build());					
//			}
//			results=customerWithPurchseOrder;
//		}
//		return results;
//		
//	}
//	
//	public List<Visitor> getVisitor(){
//		//build visitor.withCart get books
//		List<Visitor> visitorResults = new ArrayList<Visitor>(this.visitors);
//		if(hasBuildOrder(visitor,cart)) {
//			Map<Visitor,Cart> visitorToCarts = new HashMap<Visitor, Cart>();
//			for(Cart cart:getCarts()) {
//				if(!cart.isVisitorCart()) continue;
//				Visitor visitor = new Visitor.Builder().withId(cart.getId()).build();
//				if(!visitorToCarts.containsKey(visitor)) {
//					visitorToCarts.put(visitor, cart);
//				}else {
//					visitorToCarts.get(visitor).combineCarts(cart);
//				}
//			}	
//			List<Visitor> visitorWithCart=new ArrayList<Visitor>();
//			for(Visitor visitor: visitorResults) {
//				visitorWithCart.add(new Visitor.Builder(visitor).withCart(visitorToCarts.get(visitor)).build());					
//			}
//			visitorResults=visitorWithCart;
//		}
//		return visitorResults;
//		
//	}
//	
//	public List<Cart> getCarts(){
//		List<Cart> results = new ArrayList<Cart>();
//		Map<Id,Book> idToBooks=new HashMap<Id, Book>();
//		for(Book book: getBooks()) {
//			idToBooks.put(book.getId(), book);
//		}
//		
//		for(Cart cart: this.carts) {
//			Map<Book,Integer> booksInCart=new HashMap<Book, Integer>();
//			for(Entry<Book,Integer> entry:cart.getBooks().entrySet()) {
//				booksInCart.put(idToBooks.get(entry.getKey().getId()),entry.getValue());
//			}
//			results.add(new Cart.Builder(cart).withBooks(booksInCart).build());
//		}
//		return results;
//	}
//	
//	public List<PurchaseOrder> getPurchaseOrder(){
//		List<PurchaseOrder> results = new ArrayList<PurchaseOrder>();
//		Map<Id,Book> idToBooks=new HashMap<Id, Book>();
//		for(Book book: getBooks()) {
//			idToBooks.put(book.getId(), book);
//		}
//		
//		for(PurchaseOrder purchaseOrder: this.purchaseOrders) {
//			Map<Book,Integer> booksInPurchaseOrder=new HashMap<Book, Integer>();
//			for(Entry<Book,Integer> entry:purchaseOrder.getBooks().entrySet()) {
//				booksInPurchaseOrder.put(idToBooks.get(entry.getKey().getId()),entry.getValue());
//			}
//			results.add(new PurchaseOrder.Builder().withBooks(booksInPurchaseOrder).build());
//		}
//		return results;
//		
//	}
//	
//	
//	public String toJson() {
//		//returns current results to 
//		return "";
//	}
//	
//	
//
//	
//
//	
}
