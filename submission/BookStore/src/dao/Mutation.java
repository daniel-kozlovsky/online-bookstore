package dao;

import java.util.Map;

public class Mutation extends Query{
	public Map<String,QueryString> queryTypeTranslation;

	@Override
	public String renderQueryString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String renderMutationString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query includeAttributesInResults(String... attributeNames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query withAscendingOrderOf(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query withDescendingOrderOf(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}
}
