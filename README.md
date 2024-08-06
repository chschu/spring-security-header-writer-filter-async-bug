# Reproducer for https://github.com/spring-projects/spring-security/issues/15510

This repository contains a Spring Boot application that reproduces the race conditions caused
by writing HTTP response headers in concurrent threads.

The Spring Boot application can be started using the following command:

```sh
./gradlew bootRun
```

Accessing the URL http://localhost:8080/async will trigger the race condition with high
probablity. The most common symptom of the race condition is a `NullPointerException` in
`org.apache.tomcat.util.http.MimeHeaders`.

The URL http://localhost:8080/sync doesn't exhibit the race condition, because there is no
asynchronous processing involved.

The URL http://localhost:8080/async-workaround doesn't exhibit the race condition, because
the response is already committed in the synchronous part. Only the response body is produced
asynchronously.
