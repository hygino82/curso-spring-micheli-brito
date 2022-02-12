package com.api.parkingcontrol.configs;

import java.time.format.DateTimeFormatter;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class DateConfig {

	public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public static LocalDateTimeSerializer LOCAL_DATE_TIME_SERIALIZER = new LocalDateTimeSerializer(
			DateTimeFormatter.ofPattern(DATETIME_FORMAT));

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		JavaTimeModule module = new JavaTimeModule();
		module.addSerializer(LOCAL_DATE_TIME_SERIALIZER);

		return new ObjectMapper().registerModule(module);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.api.parkingcontrol"))
				.paths(PathSelectors.any())
				//.paths(PathSelectors.regex("/parking-spot*"))
				.build()
				.apiInfo(apiInfo());
			
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfo(
				"Estacionamento API",
				"Gerenciamento de vagas de estacionamento de um condomínio",
				"API v1",
				"Termos de serviço.",
				new Contact("Adroaldo", "www.github.com/hygino82","hygino82@gmail.com"),
				"licence", "url",Collections.emptyList()
				
				);
				
	}
}
