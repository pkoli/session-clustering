# session-clustering
SpringBoot project to demo Session Clustering with Hazelcast by creating cluster using Eureka 

To start the application.

1. cd eureka-server
2. mvn clean install && java -jar target\eureka-server-0.1-SNAPSHOT.jar
3. New terminal
4. cd hazelcast-eureka-session-clustering/
5. mvn spring-boot:run
6. New terminal
7. Edit application.yml to change the port to 8080
8. mvn spring-boot:run

There will be two applications running one on port 8080 and the other on port 8081.

Now to test if session clustering is working.

Hit localhost:8080/testing on one browser tab
Hit localhost:8081/testing on the other browser tab

There is a count that is stored in HttpSession, we should be able to increase the count from any one of the browser tab and that should be reflected on the other browser tab.
