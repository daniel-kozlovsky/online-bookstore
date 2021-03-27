package data.schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import data.beans.Book;
public class CartSchema extends DataSchema{
	public static final String ID="ID";
	public static final String BOOK="BOOK";
	public static final String AMOUNT="AMOUNT";
	public static final String USER_TYPE="USER_TYPE";

	public CartSchema() {
		super();
		this.TABLE_NAME="CART";
		VARCHAR_ATTRIBUTE_LABELS = new String[]{ID,BOOK,AMOUNT};
		ATTRIBUTE_LABELS = new String[]{ID,BOOK,AMOUNT,USER_TYPE};
		WORD_ATTRIBUTE_LABELS = new String[]{USER_TYPE};
		NUMBER_ATTRIBUTE_LABELS = new String[]{};
		OBJECT_ATTRIBUTE_LABELS =  new String[]{};		
	}

}
