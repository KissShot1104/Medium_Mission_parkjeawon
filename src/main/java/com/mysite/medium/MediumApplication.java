package com.mysite.medium;

import com.mysite.medium.global.security.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({AppConfig.class})
public class MediumApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediumApplication.class, args);
	}

}
