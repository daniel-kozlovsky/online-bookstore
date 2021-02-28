package dao.users;
import dao.book.Book;
import dao.carts.Cart;
import dao.orders.PurchaseOrder;
import dao.reviews.Review;

public class Customer {
	String id;
	String firstName;
	String lastName;
	Address address;
	String userName;
	String password;
	Review[] reviews;
	Cart cart;
	PurchaseOrder[] orders;
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Address getAddress() {
		return address;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public Review[] getReviews() {
		return reviews;
	}

	public Cart getCart() {
		return cart;
	}

	public PurchaseOrder[] getOrders() {
		return orders;
	}

	public boolean isReviewByCustomer(Review review) {
		return true;
	}
	
	public boolean isPurchaseOrderByCustomer(PurchaseOrder purchaseOrder) {
		return true;
	}
	
	public boolean isCartByCustomer(PurchaseOrder purchaseOrder) {
		return true;
	}

	public static class Builder{
		private String id;
		private String firstName;
		private String lastName;
		private Address address;
		private String userName;
		private String password;
		private Review[] reviews;
		private Cart cart;
		private PurchaseOrder[] orders;

		public Builder(){
			this.id="";
			this.firstName="";
			this.lastName="";
			this.userName="";
			this.password="";
			this.address=new Address.Builder().build();
			this.reviews=new Review[]{(new Review.Builder().build())};
			this.cart=new Cart.Builder().build();
			this.orders=new PurchaseOrder[] { (new PurchaseOrder.Builder().build())};
		}
		
		public Builder(Customer customer){
			this.id=customer.id;
			this.firstName=customer.firstName;
			this.lastName=customer.lastName;
			this.address=customer.address;
			this.userName=customer.userName;
			this.password=customer.password;
			this.reviews=customer.reviews;
			this.cart=customer.cart;
			this.orders=customer.orders;
		}

		public Builder withId(String id){
			this.id=id;
			return this;
		}

		public Builder withFirstName(String firstName){
			this.firstName=firstName;
			return this;
		}

		public Builder withLastName(String lastName){
			this.lastName=lastName;
			return this;
		}

		public Builder withAddress(Address address){
			this.address=address;
			return this;
		}

		public Builder withUserName(String userName){
			this.userName=userName;
			return this;
		}

		public Builder withPassword(String password){
			this.password=password;
			return this;
		}

		public Builder withReviews(Review[] reviews){
			this.reviews=reviews;
			return this;
		}

		public Builder withCart(Cart cart){
			this.cart=cart;
			return this;
		}

		public Builder withOrders(PurchaseOrder[] orders){
			this.orders=orders;
			return this;
		}

		public Customer build(){
			Customer customer=new Customer();
			customer.id=this.id;
			customer.firstName=this.firstName;
			customer.lastName=this.lastName;
			customer.address=this.address;
			customer.userName=this.userName;
			customer.password=this.password;
			customer.reviews=this.reviews;
			customer.cart=this.cart;
			customer.orders=this.orders;
			return customer;
		}

	}
}
