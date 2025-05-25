/**
 * 
 */
package de.pecus.api.config.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import de.pecus.api.config.AWSProperties;
import de.pecus.api.config.FileStorageProperties;
import de.pecus.api.config.FirebaseProperties;
import de.pecus.api.config.NotificacionProperties;
import de.pecus.api.config.SecurityRolMenuProperties;
 
/**
 * Clase de configuracion de Spring para pruebas unitarias
 * 
 * @author Pecus
 *
 */
@Configuration
@ComponentScan(basePackages={"de.pecus.api.services"}, excludeFilters=@ComponentScan.Filter(type=FilterType.REGEX, pattern={"de.pecus.api.controllers.*"}))
@PropertySource(value = "classpath:test.properties" )
@EnableScheduling
@EnableAspectJAutoProxy
@EnableCaching 
@EnableSpringConfigured
@EnableConfigurationProperties({
    AWSProperties.class,
    FirebaseProperties.class,
    SecurityRolMenuProperties.class,
	FileStorageProperties.class,
	NotificacionProperties.class
})
public class AppConfigTest {
	
	/*@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}*/

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
