package dao.orders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import dao.DataSchema;
import dao.beans.Book;
import dao.beans.Customer;

public class PurchaseOrderSchema implements DataSchema{
	public static final String ID="ID";
	public static final String CUSTOMER_ID="CUSTOMER_ID";
	public static final String STATUS="STATUS";
	public static final String BOOK_ID="BOOK_ID";
	public static final String AMOUNT="AMOUNT";
	
	public static final String BOOK="BOOK";
	public static final String CUSTOMER="CUSTOMER";
	
	private static final List<String> _wordAttributeLabels= Arrays.asList(new String[]{STATUS});
	private static final List<String> _numberAttributeLabels= Arrays.asList(new String[]{AMOUNT});
	private static final List<String> _varAttributeLabels= Arrays.asList(new String[]{ID,CUSTOMER_ID,BOOK_ID});
	private static final List<String> _objectAttributeLabels= Arrays.asList(new String[]{BOOK,CUSTOMER});
	private static final List<String> _attributeLabels= Arrays.asList(new String[]{ID,CUSTOMER_ID,BOOK_ID,AMOUNT,STATUS});
	
	
	@Override
	public String tableName() {
		// TODO Auto-generated method stub
		return "CUSTOMER";
	}
	@Override
	public List<String> wordAttributeLabels() {
		// TODO Auto-generated method stub
		return _wordAttributeLabels;
	}

	@Override
	public List<String> numberAttributeLabels() {
		// TODO Auto-generated method stub
		return _numberAttributeLabels;
	}

	@Override
	public List<String> varAttributeLabels() {
		// TODO Auto-generated method stub
		return _varAttributeLabels;
	}

	@Override
	public List<String> objectAttributeLabels() {
		// TODO Auto-generated method stub
		return _objectAttributeLabels;
	}

	@Override
	public List<String> getAttributeLabels() {
		// TODO Auto-generated method stub
		return _attributeLabels;
	}
	
}
