package com.bancolc;

import com.bancolc.config.property.BancoLcApiProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(BancoLcApiProperty.class)
public class BancolcApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancolcApplication.class, args);
	}

}
