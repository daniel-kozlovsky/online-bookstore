package dao.reviews;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.DataSchema;

public class ReviewSchema implements DataSchema {

	private static final String BODY="BODY";
	private static final String TITLE="TITLE";
	private static final String USER_NAME="USER_NAME";
	private static final String STARS="STARS";
	private static final String USER_ID="USER_ID";
	private static final String BOOK_ID="BOOK_ID";
	
	private static final String CUSTOMER="CUSTOMER";
	private static final String BOOK="BOOK";
	
	private static final List<String> _wordAttributeLabels= new ArrayList<String>();
	private static final List<String> _numberAttributeLabels= Arrays.asList(new String[]{STARS});
	private static final List<String> _objectAttributeLabels= Arrays.asList(new String[]{CUSTOMER,BOOK});
	private static final List<String> _varAttributeLabels= Arrays.asList(new String[]{TITLE,BODY,USER_NAME,USER_ID,BOOK_ID});
	private static final List<String> _attributeLabels= Arrays.asList(new String[]{BODY,TITLE,USER_NAME,STARS,USER_ID,BOOK_ID});
	
	@Override
	public String tableName() {
		// TODO Auto-generated method stub
		return "REVIEW";
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
