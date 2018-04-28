## Backend Challenge

I was curious to play around with Spring Boot on a moderately big project, therefore I decided to use it here.

### Design Choices  

The ovierview of the architecture of my solution consists of 3 packages: _/stylists_, _/users_, _/reservedtime_

Because this way it's _more_ easily extractable into separate microservices, as I imagine those have specific domain boundaries.

* **_/users_** package

Methods to operate on users were not required, but I did a small set of them for completion's sake

* **_/stylists_** package

I modeled the status of a stylist as Enums according to the state machine in the coding task description.

* **_/reservedtime_** package

I consider sick leaves, vacations and appointments for calls as reserved(booked) time for the stylists.

Therefore I modeled all three to inherit from a common supper class with a JPA/Hibernate inheritance strategy
of _SINGLE_TABLE_. I wanted to be able to polymorphicly query for all reserved time spaces, to display the
general availability of the stylists, yet I didn't want to use _JOINED_ as an inheritance strategy, because of
the extra bloated table that this is going to create. I realise the tradeoff that now some entries in
that table might have a relationship to a user and some might not, which could lead to loss of data integrity.


### Possible improvements

**Write more tests**. I firmly believe in writing tests, however it took
me a long time sifting through readmes, guides and tutorials online to
learn how people test in Spring and therefore I wasn't left with much time
to write proper tests.

For _/users_ and _/stylists_ I created one custom exception as a basis for potentially more to come.
If I had more time, I would have modeled 40x error responses to not only have an http status code and a message,
but a static error code and a description. The static error code is a long, uniquely assigned to this particular error case.
Clients can check these error codes in a documentation page, where they are described in details. And the extra description
to guide the clients with possible solutions/next steps to respond to the error.

Regarding versioning of APIs, I subscribe to the opinions of Google/Apigee to have a version number in the path,
for when we introduce a entity change (non-backwards compatible) and a version number in the header,
for when we make a formatting change (backwards compatible). If I had more time, I would have addead header versioning.


### Future Work

1. Must haves
* SSL
* Rate Limiting
* Pretty-printted gzipped json responses

2. Nice to haves
* HATEOAS/API Discoverability

### Running the project
> 1. ./gradlew clean build && ./gradlew bootRun
> 2. Use curl, httpie or postman to make requests at
<user_name>:\<pass>@localhost:8080/v1 (there is basic auth)
> 3. (Optional) Explore the API at localhost:8080/swagger-ui.html
> 4. (Optional) Check the DB under localhost:8080/h2, when in doubt