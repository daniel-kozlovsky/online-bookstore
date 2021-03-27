package data.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.util.Map.Entry;

import data.beans.Book;
import data.beans.Cart;
import data.beans.Customer;
import data.beans.Id;
import data.beans.Visitor;
import data.schema.CartSchema;
import data.schema.CustomerSchema;

public class UpdateCart extends DataUpdate{
	
	UpdateCart() {
		
	}
	public void executeClearCart(Visitor visitor){
		if(!visitor.getCart().isEmpty()) return;
		String update="DELETE FROM CART WHERE ID='"+visitor.getId().toString()+"' AND  USER_TYPE='"+visitor.getUserType()+"'";
		sendUpdateToDatabase(update);
		
	}
	
	public void executeClearCart(Customer customer){
		if(!customer.getCart().isEmpty()) return;
		String update="DELETE FROM CART WHERE ID='"+customer.getId().toString()+"' AND  USER_TYPE='"+customer.getUserType()+"'";
		sendUpdateToDatabase(update);
		
	}
	
	public void executeCartBookDeletion(Visitor visitor,Book book){
		if(book==null ||book.getId().isEmpty() ||!visitor.getCart().isBookInCart(book)) return;
		String update="DELETE FROM CART WHERE ID='"+visitor.getId().toString()+"' AND  USER_TYPE='"+visitor.getUserType()+"' AND BOOK='"+book.getId().toString()+"'";
		sendUpdateToDatabase(update);
		
	}
	
	public void executeCartBookDeletion(Customer customer,Book book){
		if(book==null ||book.getId().isEmpty() ||!customer.getCart().isBookInCart(book)) return;
		String update="DELETE FROM CART WHERE ID='"+customer.getId().toString()+"' AND  USER_TYPE='"+customer.getUserType()+"' AND BOOK='"+book.getId().toString()+"'";
		sendUpdateToDatabase(update);
	}
	
	

	public void executeCartInsertion(Visitor visitor,Book book, int amount){
		if(book==null ||book.getId().isEmpty() ||!visitor.getCart().isBookInCart(book) || amount <=0) return;
		String update ="INSERT INTO CART (ID,BOOK ,USER_TYPE,AMOUNT )	VALUES 	"+
				"('"+visitor.getId().toString()+"','"+book.getId().toString()+"','"+visitor.getUserType()+"',"+Integer.toString(amount)+")";
		sendUpdateToDatabase(update);
		
	}
	
	public void executeCartInsertion(Customer customer,Book book, int amount){
		if(book==null ||book.getId().isEmpty() ||!customer.getCart().isBookInCart(book) || amount <=0) return;
		String update ="INSERT INTO CART (ID,BOOK ,USER_TYPE,AMOUNT )	VALUES 	"+
				"('"+customer.getId().toString()+"','"+book.getId().toString()+"','"+customer.getUserType()+"',"+Integer.toString(amount)+")";
		sendUpdateToDatabase(update);
		
	}
	
	
	public void executeCartUpdate(Visitor visitor,Book book, int amount) {
		
		if(book==null ||book.getId().isEmpty() ||!visitor.getCart().isBookInCart(book) || amount <=0) {
			return;
		}else {
			String update = "UPDATE CART SET AMOUNT=" + Integer.toString(amount) + " WHERE ID='"+visitor.getId().toString()+"' AND  USER_TYPE='"+visitor.getUserType()+"' AND BOOK='"+book.getId().toString()+"'";		
			sendUpdateToDatabase(update);
		}

	}
	
	public void executeCartUpdate(Customer customer,Book book, int amount) {
		
		if(book==null ||book.getId().isEmpty() ||!customer.getCart().isBookInCart(book) || amount <=0) {
			return;
		}else {
			String update = "UPDATE CART SET AMOUNT=" + Integer.toString(amount) + " WHERE ID='"+customer.getId().toString()+"' AND  USER_TYPE='"+customer.getUserType()+"' AND BOOK='"+book.getId().toString()+"'";		
			sendUpdateToDatabase(update);
		}

	}
	

	

	
	
}
