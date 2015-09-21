# csblogger
Rest webservice example using RestEasy

## Dependency
* Java JDK www.oracle.com/technetwork/java/javase/downloads
* Maven http://maven.apache.org

## Running
1. Clone this repo (of course) and get inside the local repo dir
2. Install dependencies using `mvn install` and resolve any funny error(s)
3. Run the application using `mvn jetty:run`
4. Access the API on `http://localhost:10000/services/[resource]/[api]/[param]`. Check available API in `communityblogger.services.BloggerResource` class. Configure Jetty context path and port in the `pom.xml`
5. Stop using `mvn jetty:stop`
6. Enjoy
