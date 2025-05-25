/**
 * 
 */
package de.pecus.api.config.test;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Clase de configuracion de Spring para acceso a datos en pruebas unitarias
 * 
 * @author Pecus
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "de.pecus.api.repositories")
public class PersistenceConfigTest {

    @Autowired
    private Environment env;

    @Value("${init-db:true}")
    private String initDatabase;

    @Primary
    @Bean
    public PlatformTransactionManager transactionManager() {
        EntityManagerFactory factory = entityManagerFactory().getObject();
        return new JpaTransactionManager( factory );
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl( Boolean.FALSE );
        vendorAdapter.setShowSql( Boolean.TRUE );

        factory.setDataSource( dataSource() );
        factory.setJpaVendorAdapter( vendorAdapter );
        factory.setPackagesToScan( "de.pecus.api.entities" );

        Properties jpaProperties = new Properties();
        jpaProperties.put( "hibernate.hbm2ddl.auto", env.getProperty( "hibernate.hbm2ddl.auto" ) );
        factory.setJpaProperties( jpaProperties );

        factory.afterPropertiesSet();
        factory.setLoadTimeWeaver( new InstrumentationLoadTimeWeaver() );
        return factory;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    @Primary
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName( env.getProperty( "jdbc.driverClassName" ) );
        dataSource.setUrl( env.getProperty( "jdbc.url" ) );
        dataSource.setUsername( env.getProperty( "jdbc.username" ) );
        dataSource.setPassword( env.getProperty( "jdbc.password" ) );
        return dataSource;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer( DataSource dataSource ) {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource( dataSource );
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript( new ClassPathResource( "bd-embedded/ddl.sql" ) );
        databasePopulator.addScript( new ClassPathResource( "bd-embedded/dataset.sql" ) );
        dataSourceInitializer.setDatabasePopulator( databasePopulator );
        dataSourceInitializer.setEnabled( Boolean.parseBoolean( initDatabase ) );
        return dataSourceInitializer;
    }
    
}
