/**
 * 
 */
package de.pecus.api.config.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment; 
 
/**
 * Clase de configuracion de Spring MVC para pruebas unitarias
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
@Configuration
@ComponentScan(basePackages={"de.pecus.api.controllers, de.pecus.api.services, de.pecus.api.web.config"})
@PropertySource(value = "classpath:test.properties" )
@EnableAspectJAutoProxy
@EnableCaching
public class AppConfigControllerTest {
	
	@SuppressWarnings("unused")
    @Autowired
	private Environment env;

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager();
	}
	
}
