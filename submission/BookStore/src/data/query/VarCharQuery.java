package data.query;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import data.schema.DataSchema;

public abstract class VarCharQuery<T extends Query,U extends AttributeAccess> extends Query<T,U> implements VarCharQueryable<T>{
	
	protected VarCharQuery(Query query, DataSchema dataSchema) {
		super(query, dataSchema);
		// TODO Auto-generated constructor stub
	}

	@Override
	public T varCharContains(String contains) {
		// TODO Auto-generated method stub
//		if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//			this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//		}
//		this.dataAccessRequests.get(this.dataSchema.tableName())
//		.add(new DataAccessString.Builder()
//				.withTableName(this.dataSchema.tableName())
//				.withReferenceOperator(this.referenceOperator)
//				.withAttributeName(this.currentAttributeAccess)
//				.withDataAccessParameterPrefix(" like "+"'%")
//				.withDataAccessParameterSuffix("%'")
//				.withDataAccessParameter(contains)
//				.build()
//				);
		this.addDataAccessString(new DataAccessString.Builder()
				.withTableName(this.dataSchema.tableName())
				.withReferenceOperator(this.referenceOperator)
				.withAttributeName(this.currentAttributeAccess)
				.withDataAccessParameterPrefix(" like "+"'%")
				.withDataAccessParameterSuffix("%'")
				.withDataAccessParameter(contains)
				.build());
		return (T) this;
	}

	@Override
	public T varCharEquals(String equals) {
		// TODO Auto-generated method stub
//		if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//			this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//		}
//		this.dataAccessRequests.get(this.dataSchema.tableName())
//		.add(new DataAccessString.Builder()
//				.withTableName(this.dataSchema.tableName())
//				.withReferenceOperator(this.referenceOperator)
//				.withAttributeName(this.currentAttributeAccess)
//				.withDataAccessParameterPrefix("="+"'")
//				.withDataAccessParameterSuffix("'")
//				.withDataAccessParameter(equals)
//				.build()
//				);
		this.addDataAccessString(new DataAccessString.Builder()
				.withTableName(this.dataSchema.tableName())
				.withReferenceOperator(this.referenceOperator)
				.withAttributeName(this.currentAttributeAccess)
				.withDataAccessParameterPrefix("="+"'")
				.withDataAccessParameterSuffix("'")
				.withDataAccessParameter(equals)
				.build()
				);
		return (T) this;
	}

	@Override
	public T varCharStartsWith(String prefix) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
//		if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//			this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//		}
//		this.dataAccessRequests.get(this.dataSchema.tableName())
//		.add(new DataAccessString.Builder()
//				.withTableName(this.dataSchema.tableName())
//				.withReferenceOperator(this.referenceOperator)
//				.withAttributeName(this.currentAttributeAccess)
//				.withDataAccessParameterPrefix(" like "+"'")
//				.withDataAccessParameterSuffix("%'")
//				.withDataAccessParameter(prefix)
//				.build()
//				);
		this.addDataAccessString(new DataAccessString.Builder()
				.withTableName(this.dataSchema.tableName())
				.withReferenceOperator(this.referenceOperator)
				.withAttributeName(this.currentAttributeAccess)
				.withDataAccessParameterPrefix(" like "+"'")
				.withDataAccessParameterSuffix("%'")
				.withDataAccessParameter(prefix)
				.build()
				);
		return (T) this;
	}

	@Override
	public T varCharEndsWith(String suffix) {
//		if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//			this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//		}
//		this.dataAccessRequests.get(this.dataSchema.tableName())
//		.add(new DataAccessString.Builder()
//				.withTableName(this.dataSchema.tableName())
//				.withReferenceOperator(this.referenceOperator)
//				.withAttributeName(this.currentAttributeAccess)
//				.withDataAccessParameterPrefix(" like "+"'%")
//				.withDataAccessParameterSuffix("'")
//				.withDataAccessParameter(suffix)
//				.build()
//				);
		this.addDataAccessString(new DataAccessString.Builder()
				.withTableName(this.dataSchema.tableName())
				.withReferenceOperator(this.referenceOperator)
				.withAttributeName(this.currentAttributeAccess)
				.withDataAccessParameterPrefix("="+"'%")
				.withDataAccessParameterSuffix("'")
				.withDataAccessParameter(suffix)
				.build()
				);
		return (T) this;
	}
	@Override
	public T varCharContainsIgnoreCase(String contains) {
		
		this.addDataAccessString(new DataAccessString.Builder()
				.withTableName("LOWER("+this.dataSchema.tableName())
				.withReferenceOperator(this.referenceOperator)
				.withAttributeName(this.currentAttributeAccess+")")
				.withDataAccessParameterPrefix(" like "+"'%")
				.withDataAccessParameterSuffix("%'")
				.withDataAccessParameter(contains.toLowerCase().replaceAll("\\s+", " "))
				.build());
		return (T) this;
	}
	@Override
	public T varCharEqualsIgnoreCase(String equals){
		this.addDataAccessString(new DataAccessString.Builder()
				.withTableName("LOWER("+this.dataSchema.tableName())
				.withReferenceOperator(this.referenceOperator)
				.withAttributeName(this.currentAttributeAccess+")")
				.withDataAccessParameterPrefix("="+"'")
				.withDataAccessParameterSuffix("'")
				.withDataAccessParameter(equals.toLowerCase().replaceAll("\\s+", " "))
				.build()
				);
		return (T) this;
	}
	@Override
	public T varCharStartsWithIgnoreCase(String prefix){
		this.addDataAccessString(new DataAccessString.Builder()
				.withTableName("LOWER("+this.dataSchema.tableName())
				.withReferenceOperator(this.referenceOperator)
				.withAttributeName(this.currentAttributeAccess+")")
				.withDataAccessParameterPrefix(" like "+"'")
				.withDataAccessParameterSuffix("%'")
				.withDataAccessParameter(prefix.toLowerCase().replaceAll("\\s+", " "))
				.build()
				);
		return (T) this;
	}
	@Override
	public T varCharEndsWithIgnoreCase(String suffix){
		this.addDataAccessString(new DataAccessString.Builder()
				.withTableName("LOWER("+this.dataSchema.tableName())
				.withReferenceOperator(this.referenceOperator)
				.withAttributeName(this.currentAttributeAccess+")")
				.withDataAccessParameterPrefix("="+"'%")
				.withDataAccessParameterSuffix("'")
				.withDataAccessParameter(suffix.toLowerCase().replaceAll("\\s+", " "))
				.build()
				);
		return (T) this;
	}
//
//	@Override
//	public T varCharWithPattern(String pattern) {
//		if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//			this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//		}
//		this.dataAccessRequests.get(this.dataSchema.tableName())
//		.add(new DataAccessString.Builder()
//				.withTableName(this.dataSchema.tableName())
//				.withReferenceOperator(this.referenceOperator)
//				.withAttributeName(this.currentAttributeAccess)
//				.withDataAccessParameterPrefix(" ~ "+"'")
//				.withDataAccessParameterSuffix("'")
//				.withDataAccessParameter(pattern)
//				.build()
//				);
//		return (T) this;
//	}

	
}