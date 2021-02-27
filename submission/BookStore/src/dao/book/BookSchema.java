package dao.book;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import dao.DataSchema;
import dao.reviews.Review;

public class BookSchema implements DataSchema{
	
	private static final String ID="ID";
	private static final String TITLE="TITLE";
	private static final String DESCRIPTION="DESCRIPTION";
	private static final String CATEGORY="CATEGORY";
	private static final String AUTHOR="AUTHOR";
	private static final String PRICE="PRICE";
	private static final String COVER="COVER";
	private static final String REVIEW="REVIEW";
	private static final List<String> _wordAttributeLabels= Arrays.asList(new String[]{CATEGORY,AUTHOR});
	private static final List<String> _numberAttributeLabels= Arrays.asList(new String[]{ID,PRICE});
	private static final List<String> _objectAttributeLabels= Arrays.asList(new String[]{REVIEW});
	private static final List<String> _varAttributeLabels= Arrays.asList(new String[]{TITLE,DESCRIPTION,ID,COVER});
	private static final List<String> _attributeLabels= Arrays.asList(new String[]{ID,TITLE,DESCRIPTION,CATEGORY,AUTHOR,PRICE,COVER});

	
	@Override
	public String tableName() {
		// TODO Auto-generated method stub
		return "BOOK";
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
