package dao;

import java.util.List;

public interface Query {
	
	
	
	
	
	
	public static class SetupProperties extends Queryable{

		@Override
		protected boolean isQueryTypeExists(String queryType) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		Query build() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		Queryable acceptContains() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		Queryable acceptWith() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		Queryable acceptStartsWith() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		Queryable acceptEndsWith() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		Queryable acceptPattern() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		Queryable acceptEquals() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		Queryable acceptAtMost() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		Queryable acceptAtLeast() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		Queryable acceptWithin() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
