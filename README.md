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
