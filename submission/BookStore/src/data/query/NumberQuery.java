package data.query;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import data.schema.DataSchema;

public abstract class NumberQuery<T extends NumberQuery,U extends AttributeAccess> extends Query<T,U> implements NumberQueryable<T>{

//	this.dataAccessTypeTranslations=new HashMap<String, DataAccessTypeTranslation>();
//	this.dataAccessTypeTranslations.put(CONTAINS, new DataAccessTypeTranslation.Builder().withReferenceRenameSeparator(REFERENCE_RENAME_SEPARATOR).withReferenceOperator(REFERENCE_OPERATOR).withDataAccessType(CONTAINS).withPrefix(" like "+"'%").withSuffix( "%'").build());
//	this.dataAccessTypeTranslations.put(EQUALS, new DataAccessTypeTranslation.Builder().withReferenceRenameSeparator(REFERENCE_RENAME_SEPARATOR).withReferenceOperator(REFERENCE_OPERATOR).withDataAccessType(EQUALS).withPrefix("="+"'").withSuffix("'").build());
//	this.dataAccessTypeTranslations.put(STARTS_WITH, new DataAccessTypeTranslation.Builder().withReferenceRenameSeparator(REFERENCE_RENAME_SEPARATOR).withReferenceOperator(REFERENCE_OPERATOR).withDataAccessType(STARTS_WITH).withPrefix(" like "+"'").withSuffix("%'").build());
//	this.dataAccessTypeTranslations.put(ENDS_WITH, new DataAccessTypeTranslation.Builder().withReferenceRenameSeparator(REFERENCE_RENAME_SEPARATOR).withReferenceOperator(REFERENCE_OPERATOR).withDataAccessType(ENDS_WITH).withPrefix(" like "+"'%").withSuffix("'" ).build());
//	this.dataAccessTypeTranslations.put(PATTERN, new DataAccessTypeTranslation.Builder().withReferenceRenameSeparator(REFERENCE_RENAME_SEPARATOR).withReferenceOperator(REFERENCE_OPERATOR).withDataAccessType(PATTERN).withPrefix(" ~ "+"'").withSuffix("'").build());
//	this.dataAccessTypeTranslations.put(ATMOST, new DataAccessTypeTranslation.Builder().withReferenceRenameSeparator(REFERENCE_RENAME_SEPARATOR).withReferenceOperator(REFERENCE_OPERATOR).withDataAccessType(ATMOST).withPrefix(" >= ").withSuffix("").build());
//	this.dataAccessTypeTranslations.put(ATLEAST, new DataAccessTypeTranslation.Builder().withReferenceRenameSeparator(REFERENCE_RENAME_SEPARATOR).withReferenceOperator(REFERENCE_OPERATOR).withDataAccessType(ATLEAST).withPrefix(" >= ").withSuffix("").build());
//	this.dataAccessTypeTranslations.put(EQUALS_NUM, new DataAccessTypeTranslation.Builder().withReferenceRenameSeparator(REFERENCE_RENAME_SEPARATOR).withReferenceOperator(REFERENCE_OPERATOR).withDataAccessType(EQUALS_NUM).withPrefix("=").withSuffix("").build());
//	this.dataAccessTypeTranslations.put(ASC_ORDER, new DataAccessTypeTranslation.Builder().withReferenceRenameSeparator(REFERENCE_RENAME_SEPARATOR).withReferenceOperator(REFERENCE_OPERATOR).withDataAccessType(ASC_ORDER).withPrefix(" ORDER BY ").withSuffix(" ASC" ).build());
//	this.dataAccessTypeTranslations.put(DSC_ORDER, new DataAccessTypeTranslation.Builder().withReferenceRenameSeparator(REFERENCE_RENAME_SEPARATOR).withReferenceOperator(REFERENCE_OPERATOR).withDataAccessType(DSC_ORDER).withPrefix(" ORDER BY ").withSuffix(" DESC").build());
//	this.dataAccessTypeTranslations.put(LIMIT, new DataAccessTypeTranslation.Builder().withReferenceRenameSeparator(REFERENCE_RENAME_SEPARATOR).withReferenceOperator(REFERENCE_OPERATOR).withDataAccessType(LIMIT).withPrefix(" fetch first ").withSuffix(" rows only ").build());
//	this.dataAccessTypeTranslations.put(INTERSECTION, new DataAccessTypeTranslation.Builder().withReferenceRenameSeparator(REFERENCE_RENAME_SEPARATOR).withReferenceOperator(REFERENCE_OPERATOR).withDataAccessType(INTERSECTION).withPrefix("=").withSuffix("").build());
//


	protected NumberQuery(Query query, DataSchema dataSchema) {
		super(query, dataSchema);
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public T getReferenceDataAccessString(String otherTableName) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Map<String, Set<String>> getReferenceRules() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	@Override
	public T numberAtMost(String max) {
//		if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//			this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//		}
//		this.dataAccessRequests.get(this.dataSchema.tableName())
//		.add(new DataAccessString.Builder()
//				.withTableName(this.dataSchema.tableName())
//				.withReferenceOperator(this.referenceOperator)
//				.withAttributeName(this.currentAttributeAccess)
//				.withDataAccessParameterPrefix(" <= ")
//				.withDataAccessParameterSuffix("")
//				.withDataAccessParameter(max)
//				.build()
//				);
		this.addDataAccessString(new DataAccessString.Builder()
				.withTableName(this.dataSchema.tableName())
				.withReferenceOperator(this.referenceOperator)
				.withAttributeName(this.currentAttributeAccess)
				.withDataAccessParameterPrefix(" <= ")
				.withDataAccessParameterSuffix("")
				.withDataAccessParameter(max)
				.build()
				);
		return (T) this;
	}
	@Override
	public T numberAtLeast(String min) {
//		if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//			this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//		}
//		this.dataAccessRequests.get(this.dataSchema.tableName())
//		.add(new DataAccessString.Builder()
//				.withTableName(this.dataSchema.tableName())
//				.withReferenceOperator(this.referenceOperator)
//				.withAttributeName(this.currentAttributeAccess)
//				.withDataAccessParameterPrefix(" >= ")
//				.withDataAccessParameterSuffix("")
//				.withDataAccessParameter(min)
//				.build()
//				);
		this.addDataAccessString(new DataAccessString.Builder()
				.withTableName(this.dataSchema.tableName())
				.withReferenceOperator(this.referenceOperator)
				.withAttributeName(this.currentAttributeAccess)
				.withDataAccessParameterPrefix(" >= ")
				.withDataAccessParameterSuffix("")
				.withDataAccessParameter(min)
				.build());
		return (T) this;
	}
	@Override
	public T numberEquals(String number) {
//		if(!this.dataAccessRequests.containsKey(dataSchema.tableName())) {
//			this.dataAccessRequests.put(this.dataSchema.tableName(), new ArrayList<DataAccessString>());
//		}
//		this.dataAccessRequests.get(this.dataSchema.tableName())
//		.add(new DataAccessString.Builder()
//				.withTableName(this.dataSchema.tableName())
//				.withReferenceOperator(this.referenceOperator)
//				.withAttributeName(this.currentAttributeAccess)
//				.withDataAccessParameterPrefix(" = ")
//				.withDataAccessParameterSuffix("")
//				.withDataAccessParameter(number)
//				.build()
//				);
		this.addDataAccessString(new DataAccessString.Builder()
				.withTableName(this.dataSchema.tableName())
				.withReferenceOperator(this.referenceOperator)
				.withAttributeName(this.currentAttributeAccess)
				.withDataAccessParameterPrefix(" = ")
				.withDataAccessParameterSuffix("")
				.withDataAccessParameter(number)
				.build()
				);
		return (T) this;
	}
	@Override
	public T numberBetween(String min,String max) {
		numberAtLeast(min);
		numberAtMost(max);
		return (T) this;
	}

}
