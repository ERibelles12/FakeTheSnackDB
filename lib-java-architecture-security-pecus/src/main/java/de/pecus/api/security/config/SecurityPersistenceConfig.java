/**
 * 
 */
package de.pecus.api.security.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import de.pecus.api.configprops.PecusDataSourceConfigProps;
import de.pecus.api.configprops.HibernateConfigProps;
import de.pecus.api.configprops.JdbcConfigProps;
import de.pecus.api.error.GeneralBusinessErrors;
import de.pecus.api.exception.EnvironmentVariableNotFoundException;

/**
 * Clase de configuracion de entorno de acceso a datos mediante Hibernate
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = {"com.pecus.api.security.repositories"})
public class SecurityPersistenceConfig {
    
    private static final Logger LOG = LoggerFactory.getLogger(SecurityPersistenceConfig.class);
    
    @Autowired
    private HibernateConfigProps hibernateConfigProps;
    
    @Autowired
    private PecusDataSourceConfigProps pecusDataSourceConfigProps;
    
    @Autowired
    private JdbcConfigProps jdbcConfigProps;
	
    @Bean
    public PlatformTransactionManager transactionManager() {
        EntityManagerFactory factory = entityManagerFactory().getObject();
        return new JpaTransactionManager( factory );
    }
	
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	
        vendorAdapter.setGenerateDdl( false );
        vendorAdapter.setShowSql( hibernateConfigProps.getShowSql() );
	
        factory.setDataSource( dataSource() );
        factory.setJpaVendorAdapter( vendorAdapter );
        factory.setPackagesToScan( "com.pecus.api.entities" );
	
        Properties jpaProperties = new Properties();
        jpaProperties.put( "hibernate.hbm2ddl.auto", "none" );
        jpaProperties.put( "hibernate.dialect", hibernateConfigProps.getDialect() );
        jpaProperties.put( "hibernate.format_sql", "true" );
        jpaProperties.put( "hibernate.show_sql", hibernateConfigProps.getShowSql() );
        factory.setJpaProperties( jpaProperties );
  
        factory.afterPropertiesSet();
        factory.setLoadTimeWeaver( new InstrumentationLoadTimeWeaver() );
	
        return factory;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }
  
    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        try {
            ds.setDriverClass(pecusDataSourceConfigProps.getDriverClassName());
        } catch (IllegalStateException | PropertyVetoException ex) {
            throw new RuntimeException("Error while setting the driver class name in the datasource", ex);
        } catch (EnvironmentVariableNotFoundException evnf) {
            LOG.error(GeneralBusinessErrors.NOT_FOUND_ENVIRONMENT_VAR, evnf);
        }
        ds.setJdbcUrl(pecusDataSourceConfigProps.getJdbcUrl());
        ds.setUser(pecusDataSourceConfigProps.getUsername());
        ds.setPassword(pecusDataSourceConfigProps.getPassword());
        ds.setAcquireIncrement( jdbcConfigProps.getAcquireIncrement() );
        ds.setMinPoolSize( jdbcConfigProps.getMinPoolSize() );
        ds.setMaxPoolSize( jdbcConfigProps.getMaxPoolSize() );
        ds.setMaxIdleTime( jdbcConfigProps.getMaxIdleTime() );
        ds.setUnreturnedConnectionTimeout( jdbcConfigProps.getUnreturnedConnectionTimeout() );

        return ds;
    }

}
