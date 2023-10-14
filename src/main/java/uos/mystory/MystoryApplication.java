package uos.mystory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MystoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MystoryApplication.class, args);
	}

}
