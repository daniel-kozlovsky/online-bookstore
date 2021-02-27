package dao.carts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import dao.DataSchema;
import dao.book.Book;

public class CartSchema implements DataSchema{
	private static final String ID="ID";
	private static final String BOOK_ID="BOOK_ID";
	private static final String AMOUNT="AMOUNT";
	private static final String BOOK="BOOK";
	
	private static final List<String> _wordAttributeLabels= new ArrayList<String>();
	private static final List<String> _numberAttributeLabels= Arrays.asList(new String[]{AMOUNT});
	private static final List<String> _objectAttributeLabels= Arrays.asList(new String[]{BOOK});
	private static final List<String> _varAttributeLabels= Arrays.asList(new String[]{ID,BOOK_ID});
	private static final List<String> _attributeLabels= Arrays.asList(new String[]{ID,BOOK_ID,AMOUNT});
	
	@Override
	public String tableName() {
		// TODO Auto-generated method stub
		return "CART";
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
