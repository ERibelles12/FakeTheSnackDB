package de.pecus.api.constant;

import org.apache.logging.log4j.Level;

public class ArchitectureLoggerConstants {
    
    /**
     * Private constructor
     */
    private ArchitectureLoggerConstants() {
    }
    
    /**
     * Architecture component logger level for Log4j from ArchitectureLogger
     */
    public static final Level ARCHITECTURE_COMPONENT_LOGGER = Level.forName("ARCHITECTURE_COMPONENT_LOGGER", 94);
}