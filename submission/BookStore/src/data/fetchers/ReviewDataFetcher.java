package data.access.queries.fetchers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import data.access.queries.Query;
import data.beans.Book;
import data.beans.Customer;
import data.beans.Id;
import data.beans.Review;
import data.dao.BookDAO;
import data.dao.CustomerDAO;
import data.schema.BookSchema;
import data.schema.CustomerSchema;
import data.schema.ReviewSchema;

public class ReviewDataFetcher extends DataFetcher<Review> {

	public ReviewDataFetcher(Map<String, Set<String>> attributesToIncludInResults) {
		super(attributesToIncludInResults);
	}

	private ReviewSchema schema = new ReviewSchema();

	@Override
	public Review resultSetToBean(ResultSet resultSet) {
		try {
			boolean isRequestAllAttributes = this.attributesToIncludInResults.get(schema.tableName()).isEmpty();
			String prefix = isReferenceQuery()?schema.tableName()+Query.referenceSeparator:"";
			Customer customer = new Customer.Builder().withId(new Id(resultSet.getString(prefix + ReviewSchema.CUSTOMER))).build();
			Book book = new Book.Builder().withId(new Id(resultSet.getString(prefix + ReviewSchema.BOOK))).build();	
			Review review = new Review.Builder()
					.withCustomer(customer)
					.withBook(book)
					.build();

			if (isRequestAllAttributes || attributesToIncludInResults.get(schema.tableName()).contains(schema.TITLE)) {
				review = new Review.Builder(review).withTitle(resultSet.getString(prefix + schema.TITLE)).build();
			}

			if (isRequestAllAttributes || attributesToIncludInResults.get(schema.tableName()).contains(schema.BODY)) {
				review = new Review.Builder(review).withBody(resultSet.getString(prefix + schema.BODY)).build();
			}

			if (isRequestAllAttributes || attributesToIncludInResults.get(schema.tableName()).contains(schema.RATING)) {
				review = new Review.Builder(review).withRating(resultSet.getInt(prefix + schema.RATING)).build();
			}

			if (isRequestAllAttributes || attributesToIncludInResults.get(schema.tableName()).contains(schema.CREATED_AT_EPOCH)) {
				review = new Review.Builder(review).withCreatedAtEpoch(resultSet.getLong(prefix + schema.CREATED_AT_EPOCH)).build();
			}
			return review;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.err.println("Warning empty book, since resultSet could not produce review object");
		return null;
	}
}
