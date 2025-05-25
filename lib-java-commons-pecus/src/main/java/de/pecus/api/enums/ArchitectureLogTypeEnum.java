package de.pecus.api.enums;

/**
 * Log level enum, to allow control the through the configuration on the
 * database
 * 
 * @author Juan Carlos Contreras Vazquez
 */
public enum ArchitectureLogTypeEnum {
    
    ARCHITECTURE_COMPONENT(1);
    
    /** Error type, ordered from highest to lowest */
    private Integer type;

    /**
     * Private constructor
     * 
     * @param type
     */
    private ArchitectureLogTypeEnum(int type) {
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