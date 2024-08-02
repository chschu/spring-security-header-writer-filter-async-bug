package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class Application {

	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(a -> a
						.anyRequest().permitAll())
				.headers(h -> h
						.addHeaderWriter((request, response) -> {
							LOG.info("HeaderWriter invoked in Thread: {}", Thread.currentThread().getName());
							// add a lot of headers to increase the probability of the race condition
							for (int i = 0; i < 1000; i++)
								response.addHeader("X-" + i, "" + i);
						}))
				.build();
	}
}
