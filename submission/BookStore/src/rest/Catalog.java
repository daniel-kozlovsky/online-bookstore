package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import data.beans.Book;
import model.RestModel;

@Path("catalog")

public class Catalog {

	@GET
	@Path("/getProductInfo/")
	@Produces("application/json")
	public Response getProductInfo(@QueryParam("productID") String productID) throws Exception {
		
		String bookJSON = RestModel.getInstance().getBookByID(productID);

		return Response.ok(bookJSON).build();
	}
	
}

/*
Book book = RestModel.getInstance().getBookByID(productID);
String bookJSON;

if (book == null)
	bookJSON = null;
else 
	bookJSON = book.toJson();

return Response.ok(book).build();
*/