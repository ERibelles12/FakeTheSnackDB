package de.pecus.api.enums;

/**
 * Log level enum, to allow control the through the configuration on the
 * database
 * 
 * They are arranged by major to minor scope, ALL is the highest scope that
 * allows log everything, while ERROR is the most specific scope Id that has the
 * level of error in each BD.
 * 
 * The levels: ALL, DEBUG, INFO and WARNING, are stored in the pecus_audit database
 * The level: BUSINESS_ERROR are stored in the pecus_business_error database
 * The level: ERROR are stored in the pecus_error database
 * 
 * @author Juan Carlos Contreras Vazquez
 */
public enum LogTypeEnum {

    ALL(1), DEBUG(2), INFO(3), WARN(4), ERROR(5), BUSINESS_ERROR(6), AUDIT(7), MEASURE_TIME(8);

    /** Error type, ordered from highest to lowest */
    private Integer type;

    /**
     * Private constructor
     * 
     * @param type
     */
    private LogTypeEnum(int type) {
        this.type = type;
    }

    /**
     * Get the log level
     * 
     * @return int with the log level
     */
    public int getType() {
        return type;
    }

}