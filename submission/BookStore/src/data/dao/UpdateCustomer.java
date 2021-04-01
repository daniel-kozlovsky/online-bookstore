package data.dao;


import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.Map.Entry;
import data.beans.Book;
import data.beans.Customer;
import data.schema.BookSchema;
import data.schema.CustomerSchema;
import data.schema.UserTypes;

public class UpdateCustomer extends DataUpdate{	
	
	UpdateCustomer(){
		
	}
	
	public CustomerUpdater requestUpdateExistingCustomer(Customer customer) {
		return new CustomerUpdater(customer);
	}
	
	
	public InsertCustomerGivenName requestNewCustomerInsertion() {
		return new InsertCustomerGivenName(new Customer.Builder().build());
	}

	abstract class CustomerInsert extends DataUpdate{
		protected Customer customer;
		CustomerInsert(Customer customer){
			this.customer=customer;
		}
	}
	
	public class InsertCustomerGivenName extends CustomerInsert{
		private InsertCustomerGivenName(Customer customer) {
			super(customer);
		}
		public InsertCustomerSurname  insertCustomerWithGivenName(String givenName){
			return new InsertCustomerSurname(new Customer.Builder(customer).withGivenName(givenName).build());
		}
		
	}
	
	public class InsertCustomerSurname extends CustomerInsert{

		private InsertCustomerSurname(Customer customer) {
			super(customer);
		}
		
		public InsertCustomerUserName  insertCustomerWithSurName(String surName){
			return new InsertCustomerUserName(new Customer.Builder(customer).withSurName(surName).build());
		}
		
	}
	public class InsertCustomerUserName extends CustomerInsert{

		private InsertCustomerUserName (Customer customer) {
			super(customer);
		}
		
		public InsertCustomerPassWord  insertCustomerWithUserName(String userName){
			return new InsertCustomerPassWord(new Customer.Builder(customer).withUserName(userName).build());
		}
		
	}
	public class InsertCustomerPassWord extends CustomerInsert{

		private InsertCustomerPassWord(Customer customer) {
			super(customer);
		}
		
		public InsertCustomerEmail  insertCustomerWithPassWord(String password){
			return new InsertCustomerEmail(new Customer.Builder(customer).withPassword(password).build());
		}
		
	}	
	public class InsertCustomerEmail extends CustomerInsert{

		private InsertCustomerEmail (Customer customer) {
			super(customer);
		}
		
		public InsertCustomerStreetNumber  insertCustomerWithEmail(String email){
			return new InsertCustomerStreetNumber(new Customer.Builder(customer).withEmail(email).build());
		}
		
	}	
	public class InsertCustomerStreetNumber extends CustomerInsert{

		private InsertCustomerStreetNumber (Customer customer) {
			super(customer);
		}
		
		public InsertCustomerStreet  insertCustomerWithStreetNumber(String streetNumber){
			return new InsertCustomerStreet(new Customer.Builder(customer).withStreetNumber(streetNumber).build());
		}
		
	}	
	public class InsertCustomerStreet extends CustomerInsert{

		private InsertCustomerStreet (Customer customer) {
			super(customer);
		}
		
		public InsertCustomerPostalCode  insertCustomerWithStreet(String street){
			return new InsertCustomerPostalCode(new Customer.Builder(customer).withStreetNumber(street).build());
		}
		
	}	
	public class InsertCustomerPostalCode extends CustomerInsert{

		private InsertCustomerPostalCode (Customer customer) {
			super(customer);
		}
		
		public InsertCustomerCity  insertCustomerWithPostalCode(String postalCode){
			return new InsertCustomerCity(new Customer.Builder(customer).withPostalCode(postalCode).build());
		}
		
	}	
	public class InsertCustomerCity extends CustomerInsert{

		private InsertCustomerCity (Customer customer) {
			super(customer);
		}
		
		public InsertCustomerProvince insertCustomerWithCity(String city){
			return new InsertCustomerProvince(new Customer.Builder(customer).withCity(city).build());
		}
		
	}	
	public class InsertCustomerProvince extends CustomerInsert{

		private InsertCustomerProvince (Customer customer) {
			super(customer);
		}
		public InsertCustomerCountry insertCustomerWithProvince(String province){
			return new InsertCustomerCountry(new Customer.Builder(customer).withProvince(province).build());
		}
	}	
	public class InsertCustomerCountry extends CustomerInsert{

		private InsertCustomerCountry (Customer customer) {
			super(customer);
		}
		
		public ExecuteCustomerInsert insertCustomerWithCountry(String country){
			return new ExecuteCustomerInsert(new Customer.Builder(customer).withCountry(country).build());
		}
		
	}	
	public class ExecuteCustomerInsert extends CustomerInsert{

		private ExecuteCustomerInsert (Customer customer) {
			super(customer);
		}
		public 	void executeCustomerInsertion(){

		    String epoch =Long.toString(Instant.now().getEpochSecond());
			String id =UUID.nameUUIDFromBytes(customer.getUserName().getBytes()).toString().stripLeading().stripTrailing();
			String userTablesUpdate = "INSERT INTO SITE_USER (ID,USER_TYPE) VALUES ('"+id+"','"+UserTypes.CUSTOMER+"')";
			sendUpdateToDatabase(userTablesUpdate);
			String update="INSERT INTO CUSTOMER (USER_TYPE,ID,GIVENNAME,SURNAME,USERNAME,PASSWORD ,EMAIL,STREET_NUMBER,STREET,POSTAL_CODE,CITY,PROVINCE,COUNTRY,CREATED_AT_EPOCH) VALUES "+
					"('"+UserTypes.CUSTOMER+"','"+id+"','"+customer.getGivenName()+"','"+customer.getSurName()+"','"+customer.getUserName()+"','"+customer.getPassword()+"','"+customer.getEmail()+"','"+
					customer.getAddress().getNumber()+"',"+customer.getAddress().getStreet()+"','"+customer.getAddress().getPostalCode()+"','"+customer.getAddress().getCity()+"','"+customer.getAddress().getProvince()+"','"+customer.getAddress().getCountry()+"',"+epoch+")";
			sendUpdateToDatabase(update);
			
		}
	}
	
	class CustomerUpdater extends DataUpdate{
		Map<String,String> updateRequest;
		private Customer customer;
		private CustomerSchema customerSchema = new CustomerSchema();
		private CustomerUpdater(Customer customer){
			this.updateRequest=new LinkedHashMap<String, String>();
			this.customer=customer;
		}
		public CustomerUpdater updateCustomerGivenName(String givenName){
			this.updateRequest.put(customerSchema.GIVENNAME, surroundWithQuotes(givenName));
			return this;
			
		}
		public CustomerUpdater updateCustomerSurName(String surName){
			this.updateRequest.put(customerSchema.SURNAME, surroundWithQuotes(surName));
			return this;
			
		}
		public CustomerUpdater updateCustomerUserName(String userName){
			this.updateRequest.put(customerSchema.USERNAME, surroundWithQuotes(userName));
			return this;
			
		}
		public CustomerUpdater updateCustomerPassword(String password){
			this.updateRequest.put(customerSchema.PASSWORD, surroundWithQuotes(password));
			return this;
			
		}
		public CustomerUpdater updateCustomerEmail(String email){
			this.updateRequest.put(customerSchema.EMAIL, surroundWithQuotes(email));
			return this;
			
		}
		public CustomerUpdater updateCustomerStreetNumber(String streetNumber){
			this.updateRequest.put(customerSchema.STREET_NUMBER, surroundWithQuotes(streetNumber));
			return this;
			
		}
		public CustomerUpdater updateCustomerStreet(String street){
			this.updateRequest.put(customerSchema.STREET, surroundWithQuotes(street));
			return this;
			
		}
		public CustomerUpdater updateCustomerPostalCode(String postalCode){
			this.updateRequest.put(customerSchema.POSTAL_CODE, surroundWithQuotes(postalCode));
			return this;
			
		}
		public CustomerUpdater updateCustomerCity(String city){
			this.updateRequest.put(customerSchema.CITY, surroundWithQuotes(city));
			return this;
			
		}
		public CustomerUpdater updateCustomerProvince(String province){
			this.updateRequest.put(customerSchema.PROVINCE, surroundWithQuotes(province));
			return this;
			
		}
		public CustomerUpdater updateCustomerCountry(String country){
			this.updateRequest.put(customerSchema.COUNTRY, surroundWithQuotes(country));
			return this;
			
		}
		
		public CustomerUpdater updateCustomerCreditCardType(String creditCardType){
			this.updateRequest.put(customerSchema.CREDIT_CARD, surroundWithQuotes(creditCardType));
			return this;
			
		}
		public CustomerUpdater updateCustomerCreditCardNumber(String creditCardNumber){
			this.updateRequest.put(customerSchema.CREDIT_CARD_NUMBER, surroundWithQuotes(creditCardNumber));
			return this;
			
		}		
		
		public CustomerUpdater updateCustomerCreditCardExpiry(String creditCardExpiry){
			this.updateRequest.put(customerSchema.CREDIT_CARD_EXPIRY, surroundWithQuotes(creditCardExpiry));
			return this;
			
		}		
		
		public CustomerUpdater updateCustomerCreditCardCvv2(String cvv2){
			this.updateRequest.put(customerSchema.CREDIT_CARD_CVV2, surroundWithQuotes(cvv2));
			return this;
			
		}
		
		
		public void executeUpdate() {
			String update = "UPDATE CUSTOMER SET ";
			for(Entry<String,String> entry:this.updateRequest.entrySet()) {
				update+=entry.getKey()+"="+entry.getValue() + ",";
			}
			update=update.substring(0,update.length()-1);
			update+=" WHERE ID='"+customer.getId().toString()+"'";
			sendUpdateToDatabase(update);
		}
	}
	

	
}
