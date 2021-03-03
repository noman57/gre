# To Do App for Golden real Easte
Backedend for golden real estate to do app.

## Pre-requisites

Java 11
Apache Maven 4.0.0
Postgres Database named Test


## Application Requirements

* Person,Building and Project crud API
* Project's status can be filtered by building and person


### Running application

Import the porject as maven project from you  ide.  
And running as an spring boot application should work.

Please run you postgres database. 
Change in application properties if required.

To run using terminal please from project home 
```
mvn install
```

```

mvn spring-boot:run
```
Front end can be found https://github.com/noman57/gre-front
## Project Architecture and features

   * Uses typical Rest architecture 
   * Follows MVC design pattern
   * Uses Models to  define entity
   * Designed using model view pattern 
   * Can easily be add to any microservice architecture 
   * Maintainable and extendable (New statuses can easily be added)
   * Global error handling  to improve response messages.
   * Uses Spring global logging 
   * Lombok removes boilerplate codes
   * It uses Javax validation libraries validates incoming DTOs.
   * Implements Spring Java based initializer
   * Get method was used for filter to enable caching 
   * Swagger Ui was added for rest endpoint debugging http://localhost:8080/swagger-ui/
  
## Challenges
Since I did not work in front end for last four years it was quite a challenge I must say.

## For future Considerations 
   * Spring security for client backend communication. 
   * Profiles
   * RestAssured for Test
   * Auth0 


## Additional Libraries
   * Project uses Lombok to remove boilerplate code. Please visit https://projectlombok.org for more details.
   * I really love swagger. When working with the backend it's really great way to test out your endpoints. More info can be found on their website https://swagger.io
   
## External Resources
Axios

https://vuejs.org/v2/cookbook/using-axios-to-consume-apis.html

Spring boot 

https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/

Spring data Jpa 

https://spring.io/projects/spring-data-jpa

Spring specification for filter

https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/

JAXa validation

https://www.baeldung.com/javax-validation

