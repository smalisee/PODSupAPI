package com.amos.podsupapi;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application extends SpringBootServletInitializer {

	public Application() {
		super();
		this.setRegisterErrorPageFilter(false);
	}

	@PostConstruct
	void started() {
		Logger logger = LogManager.getLogger(Application.class);
		Locale.setDefault(Locale.ENGLISH);
		logger.info("POD From Supplier API Application Start!!!");
		logger.info("CHECK TIME ZONE: " + ZoneId.systemDefault());
		logger.info("CHECK CALENDAR: " + Calendar.getInstance());
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}