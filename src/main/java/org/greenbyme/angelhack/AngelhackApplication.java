package org.greenbyme.angelhack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AngelhackApplication {

	public static void main(String[] args) {
		SpringApplication.run(AngelhackApplication.class, args);
	}

}
