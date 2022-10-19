package com.vijayvepa.restapidemo;

import com.thirdparty.Droid;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
public class RestApiDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiDemoApplication.class, args);
	}

	@Bean
	@ConfigurationProperties(prefix = "droid")
	Droid createDroid(){
		return new Droid();
	}

}
