package flab.gumipayments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class GumiPaymentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GumiPaymentsApplication.class, args);
	}

}