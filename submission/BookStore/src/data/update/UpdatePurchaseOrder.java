package data.update;

import java.time.Instant;
import java.util.Map.Entry;

import data.beans.Book;
import data.beans.CreditCard;
import data.beans.Customer;
import data.beans.PurchaseOrder;
import data.schema.PurchaseOrderSchema;

public class UpdatePurchaseOrder extends DataUpdate {
	
	
//	public static final String PROCESSED_STATUS="PROCESSED";
//	public static final String SHIPPED_STATUS="SHIPPED";
//	public static final String DENIED_STATUS="DENIED";
//	public static final String DELIVERED_STATUS="DELIVERED";
//	public static final String ORDERED_STATUS="ORDERED";
	
	public void executeUpdatePurchaseOrderStatusToShipped(Customer customer, PurchaseOrder purchaseOrder) {
		executeUpdatePurchaseOrderStatusTo(customer,purchaseOrder,PurchaseOrderSchema.SHIPPED_STATUS);
	}
	public void executeUpdatePurchaseOrderStatusToProcessed(Customer customer, PurchaseOrder purchaseOrder) {
		executeUpdatePurchaseOrderStatusTo(customer,purchaseOrder,PurchaseOrderSchema.PROCESSED_STATUS);
	}
	public void executeUpdatePurchaseOrderStatusToDenied(Customer customer, PurchaseOrder purchaseOrder) {
		executeUpdatePurchaseOrderStatusTo(customer,purchaseOrder,PurchaseOrderSchema.DENIED_STATUS);
	}
	public void executeUpdatePurchaseOrderStatusToDelivered(Customer customer, PurchaseOrder purchaseOrder) {
		executeUpdatePurchaseOrderStatusTo(customer,purchaseOrder,PurchaseOrderSchema.DELIVERED_STATUS);
	}
	public void executeUpdatePurchaseOrderStatusToOrdered(Customer customer, PurchaseOrder purchaseOrder) {
		executeUpdatePurchaseOrderStatusTo(customer,purchaseOrder,PurchaseOrderSchema.ORDERED_STATUS);
	}
	
	private void executeUpdatePurchaseOrderStatusTo(Customer customer, PurchaseOrder purchaseOrder,String newStatus) {
		if(customer==null || purchaseOrder==null || newStatus==null || purchaseOrder.isEmpty()|| 
				!newStatus.equals(PurchaseOrderSchema.ORDERED_STATUS)|| !newStatus.equals(PurchaseOrderSchema.PROCESSED_STATUS)|| 
				!newStatus.equals(PurchaseOrderSchema.SHIPPED_STATUS)|| !newStatus.equals(PurchaseOrderSchema.DELIVERED_STATUS) ||
				!newStatus.equals(PurchaseOrderSchema.DENIED_STATUS)) return;
		String update = "UPDATE PURCHASE_ORDER SET ";
		update+=" STATUS='"+newStatus+"' ";
		update+="WHERE ID='"+customer.getId().toString()+"' AND CREATED_AT_EPOCH="+Long.toString(purchaseOrder.getCreatedAtEpoch());
		sendUpdateToDatabase(update);
	}
	
	public void insertPurchaseOrder(Customer customer,CreditCard creditCard) {
		//add cart items to PO
		//clear cart items
		if(customer.getCart()==null ||customer.getId().isEmpty() ||!customer.getCart().isEmpty()||creditCard==null ||creditCard.isEmpty()) return;
		String epoch =Long.toString(Instant.now().getEpochSecond());
//		ID, BOOK,STATUS,AMOUNT,CREATED_AT_EPOCH
		String update ="INSERT INTO PURCHASE_ORDER (ID,BOOK,STATUS,AMOUNT,CREATED_AT_EPOCH,CREDIT_CARD,CREDIT_CARD_NUMBER,CREDIT_CARD_EXPIRY,CREDIT_CARD_CVV2)	VALUES 	";
		for(Entry<Book,Integer> entry:customer.getCart().getBooks().entrySet()) {
			if(!entry.getKey().hasId()) continue;
			update+="('"+customer.getId().toString()+"','"+entry.getKey().getId().toString()+"','"+PurchaseOrderSchema.ORDERED_STATUS+"',"+Integer.toString(entry.getValue())+","+epoch+",'"
			+creditCard.getCreditCardType()+"','"+creditCard.getCreditCardNumber()+"','"+creditCard.getCreditCardExpiry()+"','"+creditCard.getCreditCardCVV2()+"'),";
		}
		update=update.substring(0,update.length()-1);
		new UpdateCart().executeClearCart(customer);				
	}
	
	public void insertPurchaseOrder(Customer customer) {
		insertPurchaseOrder(customer,customer.getCreditCard());	
				
	}
//	public void executeClearCart(Visitor visitor){
//		if(!visitor.getCart().isEmpty()) return;
//		String update="DELETE FROM CART WHERE ID='"+visitor.getId().toString()+"' AND  USER_TYPE='"+visitor.getUserType()+"'";
//		sendUpdateToDatabase(update);
//		
//	}
//	
//	public void executeClearCart(Customer customer){
//		if(!customer.getCart().isEmpty()) return;
//		String update="DELETE FROM CART WHERE ID='"+customer.getId().toString()+"' AND  USER_TYPE='"+customer.getUserType()+"'";
//		sendUpdateToDatabase(update);
//		
//	}
//	
//	public void executeCartBookDeletion(Visitor visitor,Book book){
//		if(book==null ||book.getId().isEmpty() ||!visitor.getCart().isBookInCart(book)) return;
//		String update="DELETE FROM CART WHERE ID='"+visitor.getId().toString()+"' AND  USER_TYPE='"+visitor.getUserType()+"' AND BOOK='"+book.getId().toString()+"'";
//		sendUpdateToDatabase(update);
//		
//	}
//	
//	public void executeCartBookDeletion(Customer customer,Book book){
//		if(book==null ||book.getId().isEmpty() ||!customer.getCart().isBookInCart(book)) return;
//		String update="DELETE FROM CART WHERE ID='"+customer.getId().toString()+"' AND  USER_TYPE='"+customer.getUserType()+"' AND BOOK='"+book.getId().toString()+"'";
//		sendUpdateToDatabase(update);
//	}
//	
//	
//
//	public void executeCartInsertion(Visitor visitor,Book book, int amount){
//		if(book==null ||book.getId().isEmpty() ||!visitor.getCart().isBookInCart(book) || amount <=0) return;
//		String update ="INSERT INTO CART (ID,BOOK ,USER_TYPE,AMOUNT )	VALUES 	"+
//				"('"+visitor.getId().toString()+"','"+book.getId().toString()+"','"+visitor.getUserType()+"',"+Integer.toString(amount)+")";
//		sendUpdateToDatabase(update);
//		
//	}
//	
//	public void executeCartInsertion(Customer customer,Book book, int amount){
//		if(book==null ||book.getId().isEmpty() ||!customer.getCart().isBookInCart(book) || amount <=0) return;
//		String update ="INSERT INTO CART (ID,BOOK ,USER_TYPE,AMOUNT )	VALUES 	"+
//				"('"+customer.getId().toString()+"','"+book.getId().toString()+"','"+customer.getUserType()+"',"+Integer.toString(amount)+")";
//		sendUpdateToDatabase(update);
//		
//	}
//	
//	
//	public void executeCartUpdate(Visitor visitor,Book book, int amount) {
//		
//		if(book==null ||book.getId().isEmpty() ||!visitor.getCart().isBookInCart(book) || amount <=0) {
//			return;
//		}else {
//			String update = "UPDATE CART SET AMOUNT=" + Integer.toString(amount) + " WHERE ID='"+visitor.getId().toString()+"' AND  USER_TYPE='"+visitor.getUserType()+"' AND BOOK='"+book.getId().toString()+"'";		
//			sendUpdateToDatabase(update);
//		}
//
//	}
//	
//	public void executeCartUpdate(Customer customer,Book book, int amount) {
//		
//		if(book==null ||book.getId().isEmpty() ||!customer.getCart().isBookInCart(book) || amount <=0) {
//			return;
//		}else {
//			String update = "UPDATE CART SET AMOUNT=" + Integer.toString(amount) + " WHERE ID='"+customer.getId().toString()+"' AND  USER_TYPE='"+customer.getUserType()+"' AND BOOK='"+book.getId().toString()+"'";		
//			sendUpdateToDatabase(update);
//		}
//
//	}
	
}
