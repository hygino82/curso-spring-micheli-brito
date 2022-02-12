package com.api.parkingcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ParkingControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingControlApplication.class, args);
	}

	@GetMapping("/ola")
	public String index() {
		return "Olá mundo!";
	}
	/*
	@Value("${ads04.swagger.path}")
	private String swaggerPath;
	
	@Bean
	public Docket allApi() {
		ParameterBuilder aParameterBuilder= new ParameterBuilder();
		aParameterBuilder.name("Authorization")
		.modelRef(new ModelRef("string"))
		.parameterType("header")
		.required(false)
		.build();
		List<Parameter> aParameters=new ArrayList<>();
		aParameters.add(aParameterBuilder.build());
		
		Set<String> protocols=new HashSet<>();
		protocols.add("http");
		protocols.add("https");
		
		return new Docket(DocumentationType.SWAGGER_2).host(swaggerPath)
				.groupName("All")
				.apiInfo(apiInfo())
				.select()
				.paths(PathSelectors.any())
				.build()
				.protocols(protocols)
				.ignoredParameterTypes(ApiIgnore.class)
				.enableUrlTemplating(true).globalOperationParameters(aParameters);
	}
	
	private ApiInfo apiInfo() {
		return  new ApiInfoBuilder()
				.title("Gerenciador de estacionamento")
				.description("Gerenciamento de vagas de estacionamento de um condomínio")
				.termsOfServiceUrl("http://localhost:8080")
				.license("")
				.licenseUrl("http://localhost:8080")
				.version("1.0")
				.build();
				
	}*/

}
