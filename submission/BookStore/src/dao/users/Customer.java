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

}
