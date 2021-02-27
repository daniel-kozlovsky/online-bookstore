package dao.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.DataSchema;

public class CustomerSchema implements DataSchema {
	public static final String ID="ID";
	public static final String FIRST_NAME="FIRST_NAME";
	public static final String LAST_NAME="LAST_NAME";
	public static final String USER_NAME="USER_NAME";
	public static final String PASS_WORD="PASS_WORD";
	
	public static final String STREET_NUMBER="STREET_NUMBER";
	public static final String STREET="STREET";
	public static final String POSTAL_CODE="POSTAL_CODE";
	public static final String PROVINCE="PROVINCE";
	public static final String COUNTRY="COUNTRY";
	
	public static final String REVIEW="REVIEW";
	public static final String CART="CART";
	public static final String PURCHASE_ORDER="PURCHASE_ORDER";
	
	
	
	
	
	private static final List<String> _wordAttributeLabels= Arrays.asList(new String[]{FIRST_NAME,LAST_NAME,STREET,PROVINCE,COUNTRY});
	private static final List<String> _numberAttributeLabels= new ArrayList<String>();
	private static final List<String> _varAttributeLabels= Arrays.asList(new String[]{ID,USER_NAME,PASS_WORD,POSTAL_CODE,STREET_NUMBER});
	private static final List<String> _objectAttributeLabels= Arrays.asList(new String[]{REVIEW,CART,PURCHASE_ORDER});
	private static final List<String> _attributeLabels= Arrays.asList(new String[]{ID,FIRST_NAME,LAST_NAME,USER_NAME,PASS_WORD,STREET_NUMBER,STREET,POSTAL_CODE,PROVINCE,COUNTRY});
	
	
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
