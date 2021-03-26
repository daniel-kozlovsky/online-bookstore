package data.access.queries;

import data.schema.DataSchema;

public interface VarCharQueryable<T extends Query> extends Queryable<T> {


	public T varCharContains(String contains);	
	public T varCharEquals(String equals);
	public T varCharStartsWith(String prefix);
	
	public T varCharEndsWith(String suffix);
	
//	public T varCharWithPattern(String pattern);
}
