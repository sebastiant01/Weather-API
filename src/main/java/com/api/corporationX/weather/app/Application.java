package com.api.corporationX.weather.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Entry point of the Weather App Spring Boot application.
 *
 * <p>{@link SpringBootApplication} enables three key Spring Boot features at once:</p>
 * <ul>
 *   <li>{@code @Configuration} — marks this class as a source of bean definitions</li>
 *   <li>{@code @EnableAutoConfiguration} — lets Spring Boot auto-configure beans based on the classpath</li>
 *   <li>{@code @ComponentScan} — scans this package and sub-packages for Spring components
 *       ({@code @Service}, {@code @RestController}, etc.)</li>
 * </ul>
 */
@SpringBootApplication
public class Application {

	/**
	 * Main method that bootstraps and launches the Spring Boot application.
	 *
	 * @param args command-line arguments passed to the application at startup
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Registers a {@link RestTemplate} instance as a Spring-managed bean.
	 *
	 * <p>Declaring it here allows Spring to inject it via {@code @Autowired} anywhere
	 * in the application — specifically in {@link com.api.corporationX.weather.app.services.WeatherService},
	 * where it is used to perform HTTP GET requests to the Visual Crossing Weather API.</p>
	 *
	 * @return a new {@link RestTemplate} instance
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}