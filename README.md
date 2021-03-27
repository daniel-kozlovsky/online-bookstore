# UPDATE

video in the google docs how to use my DAO's
let me know if there are any bugs, I didnt have time test everything


# Quick DAO GUIDE:

There is a video on the google drive, its about 10 mins and i give a quick demo.

DAOs were made to be flexible, you can chain up the methods however you want
Example: lets say you want all the books from an author, all the reviews of those books with 5 stars, and all the customer who wrote the reviews Then you want all those results to be ordered by best selling books of the authors, and give at most 50 results per page, and the results for the 3rd page

```
		BookDAO bookDAO= new BookDAO();
		bookDAO.newQueryRequest()
		.includeAllAttributesInResultFromSchema()
		.excludeBookDescriptionInResult()
		.excludeBookPriceInResult()
		.queryAttribute()
		.whereBookAuthor()
		.varCharContains("Tolkien")
		.queryAttribute()
		.whereBookAmountSold()
		.withAscendingOrderOf()
		.withResultLimit(50)
		.withPageNumber(3)
		.queryReviews()
		.includeAllAttributesInResultFromSchema()
		.queryAttribute()
		.whereReviewRating()
		.numberAtLeast("5")
		.queryCustomers()
		.includeAllAttributesInResultFromSchema()
		.excludeCustomerPasswordInResult()
		.executeQuery()
		.executeCompilation()
		.compileBooks()
```
A few things to note. you may have to query back and forth between tables to properly set up your search for example you may need to go from customer-> to review, but now to get the purchase orders, you have to go back to customer to access the method

Example: get customer, with reviews rating at least 3, and purchase orders of that customer where the status is ordered
```
		new CustomerDAO().newQueryRequest()
		.includeAllAttributesInResultFromSchema()
		.queryReviews()  //to reviews
		.includeAllAttributesInResultFromSchema()
		.queryAttribute()
		.whereReviewRating()
		.numberAtLeast("3")
		.queryCustomers() //back to customers
		.queryPurchaseOrder() //so purchase orders can be accessed
		.includeAllAttributesInResultFromSchema()
		.queryAttribute()
		.wherePurchaseOrderStatus()
		.isOrdered()
		.executeQuery()
		.executeCompilation()
		.compileBooks();
		
```

You can only chain query properties to one attribute at a time any time you call the following method it will give you a list of all the attributes related to the object
```
queryAttribute()
```

from here you must pick one of the attributes they are always prefixed with keyword "where" ex for book Title 
```
whereBookTitle()
```

After you select the attribute the set of methods that you can query will come up, you can add as many as you want, it will all apply to the lastest call of 
queryAttribute() chained with the whereAttribute(). They will be all conjunctions unless you explicitly state it to be a disjunction.

# Ecommerce-EECS4413

Our actual source code is in the submission folder

Utilities folder is for stuff that will help us develop

Docs, for docs

# OTHER NOTES:
Ive merged my branch, no pull request, since main wasnt big enough.
How to instantiate a bean or as i call it dataobject:

Root data objects: Book, Customer, Visitor etc. have the actual child objects as fields, eg. Customer has the Review object etc.

non root objects contained by some root eg. reviews and purchase order are contained by customer, will refer to their root parent via Id.

All the data objects use builder pattern to build of the fly heres an example of building a book.
```
Book book=new Book.builder()
           .withTitle("Book Title")
           .withDescription("Book about stuff")
           .withAuthor("Mr Writer")
           .withPrice(10.00)
           .withCover(cover)
           .withReviews(Review[] reviews)
           .build();
```

The builder fields will be stable, i wont change them. Using this method to instantiate means, that i can add new fields if needed, and it wont break anyones code like a regular constructor would.


BECAREFUL ABOUT USING ID!!, I have the type set as string for now, but it may change just use it for the mock objects for now, 

but in your actual implementation use the comparator methods and pass in the object instead. 

eg. instead of using id to check which cart belongs to a customer instead call:
```
isCartOfUser(Customer customer)
```

# Style/Format

**branches naming schema:**

feature related:
```
feature/name_of_feature
```
bug related:
```
bug/name_of_bug
```

test related:
```
test/name_of_test
```

for anything that is purely experimental meant to be thrownout:

```
sandbox/name_of_experiment
```

# TASKS:

Nina: User stuff, cart, payment, purchase history.. etc

Jess: Product Oriented stuff Analytics

Daniel: Security (no analytics)

Kevin: DAO data stuff
