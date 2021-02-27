package dao.orders;

import java.util.Map;

import dao.book.Book;
import dao.users.Customer;

public class PurchaseOrder {
	String id;
	Customer customer;
	String status;
	Map<Book,Integer> books;	
	
	public Customer getCustomer() {
		return customer;
	}

	public String getStatus() {
		return status;
	}

	public Map<Book, Integer> getBooks() {
		return books;
	}
	
	
	public boolean isEqual(PurchaseOrder purchaseOrder) {
		return false;
		
	}
	
	public boolean isPurchaseOrderByCustomer(Customer customer) {
		return false;
		
	}
	
	public boolean isPurchaseOrderOfBook(Book book) {
		return false;
		
	}
	
	
}
