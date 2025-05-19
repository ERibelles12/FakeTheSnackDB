package de.pecus.api.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnection;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.error.GeneralBusinessErrors;
import de.pecus.api.exception.EnvironmentVariableNotFoundException;
import de.pecus.api.exception.PecusException;
import de.pecus.api.util.EnvironmentVariableUtil;
import de.pecus.api.util.PropertiesUtil;
import de.pecus.api.util.ValidatorUtil;

/**
 * Configuracion de log4j para las conexiones a base de datos, estas se obtienen de un archivo properties,
 * que debe quedar especificado en la variable de entorno "SMARTLOGGER_DB_CREDENTIALS", el archivo
 * contiene la url, usuario, password y cadena de validacion sql.
 * 
 * Ejemplo de archivo de configuracion:
 * 
 * ------------------------------------------
 * ~/.smartlogger/db-credentials.properties
 * ------------------------------------------
 * # SmartLogger audit database
 * smartlogger.db.audit.url                        = jdbc:mysql://localhost:3306/pecus_audit
 * smartlogger.db.audit.user                       = pecus
 * smartlogger.db.audit.pass                       = pecuspwd
 * smartlogger.db.audit.driver                     = org.mariadb.jdbc.Driver
 * smartlogger.db.audit.validationQuery            = SELECT 1
 * 
 * # SmartLogger business_error database
 * smartlogger.db.business_error.url               = jdbc:mysql://localhost:3306/pecus_business_error
 * smartlogger.db.business_error.user              = pecus
 * smartlogger.db.business_error.pass              = pecuspwd
 * smartlogger.db.business_error.driver            = org.mariadb.jdbc.Driver
 * smartlogger.db.business_error.validationQuery   = SELECT 1
 * 
 * # SmartLogger error database
 * smartlogger.db.error.url                        = jdbc:mysql://localhost:3306/pecus_error
 * smartlogger.db.error.user                       = pecus
 * smartlogger.db.error.pass                       = pecuspwd
 * smartlogger.db.error.driver                     = org.mariadb.jdbc.Driver
 * smartlogger.db.error.validationQuery            = SELECT 1
 * ------------------------------------------
 * 
 * Log4j obtiene de forma automatica la instancia creada en esta clase para el bitacoreo en base de datos
 * 
 * @author Juan Carlos Contreras
 */
public class ConfigLog4JConnectionFactory {
    
    private static final Logger LOGGER = LoggerFactory.getLogger( ConfigLog4JConnectionFactory.class );

    // Se genera instancia singleton
    private static interface Singleton {
        final ConfigLog4JConnectionFactory INSTANCE = new ConfigLog4JConnectionFactory();
    }
 
    private final DataSource dataSource;
 
    /**
     * Se genera el constructor privado, donde se incializan los datasources
     */
    private ConfigLog4JConnectionFactory() {
        /**
         * Obtenemos las propiedades de conexion del archivo configurado en la variable
         * de entorno "SMART_LOGGER_DB_SQLS_CREDENTIALS_ENV_VARIABLE"
         */
        Properties properties = null;
        String credentialsFilePath = "";
        
        try {
            credentialsFilePath = EnvironmentVariableUtil.getVariable(GeneralConstants.SMART_LOGGER_DB_SQLS_CREDENTIALS_ENV_VARIABLE);
            properties = PropertiesUtil.loadProperties(credentialsFilePath);
        } catch (EnvironmentVariableNotFoundException e) {
            LOGGER.error(MessageFormat.format(GeneralBusinessErrors.NOT_FOUND_ENVIRONMENT_VAR, GeneralConstants.SMART_LOGGER_DB_SQLS_CREDENTIALS_ENV_VARIABLE), e);
        } catch (IOException e) {
            LOGGER.error(MessageFormat.format(GeneralBusinessErrors.FILE_NOT_FOUND, credentialsFilePath), e);
        }
        
        GenericObjectPool<PoolableConnection> pool = new GenericObjectPool<PoolableConnection>();
        
        this.dataSource = initDataSource(pool, GeneralConstants.SMART_LOGGER_AUDIT_PROPERTY, properties);

    }
 
    /**
     * Inicializamos cada datasource a partir de la informacion extraida del archivo properties
     * @param pool de conexiones
     * @param dbName nombre de la base de datos que se va a configurar
     * @param properties archivo con la configuracion de todas las bases de datos configuradas para log4j
     * @return el datasource inicializado
     */
    private DataSource initDataSource(GenericObjectPool<PoolableConnection> pool, String dbName, Properties properties) {
        String baseProperty = GeneralConstants.SMART_LOGGER_DB_PROPERTY_PREFIX + dbName;
        
        // Se extraen los valores de los properties para configurar la conexion a base de datos
        String url = properties.getProperty(baseProperty + GeneralConstants.SMART_LOGGER_DB_URL_SUFIX);
        LOGGER.debug(MessageFormat.format("URL for database {0} is: {1}", baseProperty, url));
        String user = properties.getProperty(baseProperty + GeneralConstants.SMART_LOGGER_DB_USER_SUFIX);
        String password = properties.getProperty(baseProperty + GeneralConstants.SMART_LOGGER_DB_PASSWORD_SUFIX);
        String validationQuery = properties.getProperty(baseProperty + GeneralConstants.SMART_LOGGER_DB_VALIDATION_QUERY_SUFIX);
        
        if(validateRequiredProperties(dbName, url, user, password, validationQuery)) {
            // Se genera el pool de conexiones
            DriverManagerConnectionFactory connectionFactory = new DriverManagerConnectionFactory(url, user, password);
            new PoolableConnectionFactory(connectionFactory, pool, null, validationQuery, false, false, Connection.TRANSACTION_READ_COMMITTED);
        }
        return new PoolingDataSource(pool);
    }
    
    /**
     * Se validan los parametros requeridos para las conexiones de bd de log4j
     * 
     * @param dbName nombre de la bd a validar
     * @param url
     * @param user
     * @param password
     * @param validationQuery
     * @return true si es valido, caso contrario false y una excepcion
     */
    private boolean validateRequiredProperties(String dbName, String url, String user, String password, String validationQuery) {
        boolean isValid = true;
        
        // Url requerida
        if(ValidatorUtil.isNullOrEmpty(url)) {
            isValid = Boolean.FALSE;
            throw new PecusException(GeneralBusinessErrors.LOG4J_REQUIRED_URL_ERROR, null, dbName);
        }
        // User requerido
        if(ValidatorUtil.isNullOrEmpty(user)) {
            isValid = Boolean.FALSE;
            throw new PecusException(GeneralBusinessErrors.LOG4J_REQUIRED_USER_ERROR, null, dbName);
        }
        // Password requerido
        if(ValidatorUtil.isNullOrEmpty(password)) {
            isValid = Boolean.FALSE;
            throw new PecusException(GeneralBusinessErrors.LOG4J_REQUIRED_PASSWORD_ERROR, null, dbName);
        }
        // Query de validacion requerida
        if(ValidatorUtil.isNullOrEmpty(validationQuery)) {
            isValid = Boolean.FALSE;
            throw new PecusException(GeneralBusinessErrors.LOG4J_REQUIRED_VALIDATION_QUERY_ERROR, null, dbName);
        }
        
        return isValid;
    }

    /**
     * Obtenemos la instancia de conexion para la base de datos de auditoria
     * 
     * @return conexion a base de datos de auditoria
     * @throws SQLException puede arrojar una excepcion sql
     */
    public static Connection getPecusBitacoraDatabaseConnection() throws SQLException {
        return Singleton.INSTANCE.dataSource.getConnection();
    }
    
}