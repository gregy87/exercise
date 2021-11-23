# Exercise
## Objective
- receives HTTP POST requests only on a "/track" route
  - gets data in JSON format passed in the request body
  - saves the JSON data into a local file (append)
  - if the data contains a "count" parameter, the application increments the value of the "count" key by the value of the 'count' parameter in a Redis database
- receives HTTP GET requests only on a "/count" route
  - returns the value of the "count" key from the Redis database
- Write appropriate unit tests.

## How to build it
Basic mvn commands like `mvn clean install` will work.

War file will be created in `target` folder.

## How to run it
For default configuration: `java -jar .\exercise-0.0.1-SNAPSHOT.war`

For cutom configuration, create _config_ subdirectory of the current directory and plase your own _application.properties_ file there.

## Prerequisities (or at least what I worked with)
- Java 11.0.6
- Maven 3.8.2
- Access to Maven Central repository

## Some notes regarding the solution
- Some parts (including business logic) were simplified
  - If there is no count value in DB, 0 is returned, when count is requested
  - Not all checks are done (e.g. attempt to increment value, which might not be number in DB)
    - There can also be IO Exceptions which will result in 500 response code 
  - For `/track` request, if _valid_ JSON contains invalid _count_ value, exception is thrown (and usbsequently, BAD REQUEST is returned)
- Zero security
  - No encrypted connections
  - DB credentials in configuration file 
- I investigated briefly possibility of having in memorz DB of Redis for unit tests, but it does not support all features, so there is one not needed class in tests
  - It is also possible, that the features are there, but I just did not find the way, how they should be used.
- I sticked with the default Eclipse formatter

## Possible further improvements
- Docker image
  - In theory just a different maven command for build should suffice, but I did not test that
