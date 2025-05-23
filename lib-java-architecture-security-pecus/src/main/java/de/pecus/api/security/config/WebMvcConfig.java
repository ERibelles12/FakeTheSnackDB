/**
 * 
 */
package de.pecus.api.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Clase de configuracion del modulo MVC
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
//@Configuration
//@ComponentScan(basePackages = { "de.pecus.api.controllers", "de.pecus.api.smartlogger.controllers" })
//@EnableAsync
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * Referencia a interceptor
     */
	private RestInterceptor restInterceptor;

    /**
     * Obtiene una instancia del interceptor
     * 
     * @return Instancia de interceptor
     */
	@Bean
	public RestInterceptor getRestInterceptor() {
		return new RestInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		restInterceptor = getRestInterceptor();
		registry.addInterceptor(restInterceptor);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

}