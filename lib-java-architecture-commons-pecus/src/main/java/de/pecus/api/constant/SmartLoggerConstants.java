package de.pecus.api.constant;

import org.apache.logging.log4j.Level;

public class SmartLoggerConstants {
    
    /**
     * Private constructor
     */
    private SmartLoggerConstants() {
    }
    
    /**
     * Audit logger level for Log4j from SmartLogger
     */
    public static final Level SMART_LOGGER_AUDIT = Level.forName("SMART_LOGGER_AUDIT", 96);
    
    /**
     * MeasureTime logger level for Log4j from SmartLogger
     */
    public static final Level SMART_LOGGER_MEASURE_TIME = Level.forName("SMART_LOGGER_MEASURE_TIME", 97);
    
    /**
     * Debug logger level for Log4j from SmartLogger
     */
    public static final Level SMART_LOGGER_DEBUG = Level.forName("SMART_LOGGER_DEBUG", 99);
    
    /**
     * Info logger level for Log4j from SmartLogger
     */
    public static final Level SMART_LOGGER_INFO = Level.forName("SMART_LOGGER_INFO", 98);
    
    /**
     * BusinessError logger level for Log4j from SmartLogger
     */
    public static final Level SMART_LOGGER_BUSINESS_ERROR = Level.forName("SMART_LOGGER_BUSINESS_ERROR", 96);
    
    /**
     * Error logger level for Log4j from SmartLogger
     */
    public static final Level SMART_LOGGER_ERROR = Level.forName("SMART_LOGGER_ERROR", 95);

}