package com.api.corporationX.weather.app;

import com.api.corporationX.weather.app.models.WeatherResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.cache.autoconfigure.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

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
@Configuration
@EnableCaching
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

	/**
	 * Configures the default Redis cache behavior for the application.
	 *
	 * <p>Registers a {@link RedisCacheManagerBuilderCustomizer} that overrides Spring Boot's
	 * default Java serialization strategy with JSON serialization via {@link JacksonJsonRedisSerializer},
	 * avoiding the requirement for cached objects to implement {@link java.io.Serializable}.</p>
	 *
	 * <p>The following settings are applied globally to all caches:</p>
	 * <ul>
	 *   <li><b>Serializer</b> — {@link JacksonJsonRedisSerializer} typed to {@link WeatherResponse},
	 *       ensuring Redis stores human-readable JSON and deserializes directly to the correct type
	 *       without ambiguity (prevents {@link ClassCastException} from untyped {@code LinkedHashMap}
	 *       fallback)</li>
	 *   <li><b>TTL</b> — 10 minutes per cache entry</li>
	 * </ul>
	 *
	 * @return a {@link RedisCacheManagerBuilderCustomizer} that applies JSON serialization
	 *         and TTL defaults to the auto-configured {@link org.springframework.data.redis.cache.RedisCacheManager}
	 */
	@Bean
	public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
		var serializer = new JacksonJsonRedisSerializer<>(WeatherResponse.class);

		var json = RedisSerializationContext.SerializationPair.fromSerializer(serializer);

		return builder -> builder.cacheDefaults(
				RedisCacheConfiguration.defaultCacheConfig()
						.entryTtl(Duration.ofMinutes(10))
						.serializeValuesWith(json)
		);
	}
}