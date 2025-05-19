package de.pecus.api.log;

import java.util.HashMap;
import java.util.Map;

import de.pecus.api.constant.GeneralConstants;

/**
 * Factory to create the ArchitectureLogger instances, it is limited by {n} 
 * objects per class to ensure the efficiency of the log.
 * 
 * The amount of objects can be configured inside the general 
 * "Constants.java" class
 *  
 * @author Juan Carlos Contreras Vazquez
 * Creation date: 04-25-2019
 **/
public class ArchitectureLoggerFactory {
    
    /**
     * ArchitectureLogger instances 
     */
    private static Map<String, ArchitectureLogger[]> architectureLoggerMap;
    
    /**
     * Map for switch the returned ArchitectureLogger instances
     */
    private static Map<String, Integer> architectureLoggerCounterMap;

    private ArchitectureLoggerFactory() {
    }

    @SuppressWarnings("rawtypes")
    public static synchronized ArchitectureLogger getLogger(Class clazz) {
        ArchitectureLogger architectureLogger = null;
        if (architectureLoggerMap == null) {
            architectureLoggerMap = new HashMap<String, ArchitectureLogger[]>();
        }
        if (architectureLoggerMap.containsKey(clazz.getName()) == false) {
            architectureLoggerMap.put(clazz.getName(), initArchitectureLoggerArray(clazz));
        }
        architectureLogger = architectureLoggerMap.get(clazz.getName())[getArchitectureLoggerCounter(clazz)];
        return architectureLogger;
    }
    
    @SuppressWarnings("rawtypes")
    private static int getArchitectureLoggerCounter(Class clazz) {
        int counter = GeneralConstants.ZERO;
        if (architectureLoggerCounterMap == null) {
            architectureLoggerCounterMap = new HashMap<String, Integer>();
        }
        if (architectureLoggerCounterMap.get(clazz.getName()) == null || architectureLoggerCounterMap.get(clazz.getName()).equals(GeneralConstants.ARCHITECTURE_LOGGER_INSTANCES_PER_CLASS)) {
            architectureLoggerCounterMap.put(clazz.getName(), GeneralConstants.ZERO);
        }
        counter = architectureLoggerCounterMap.get(clazz.getName());
        architectureLoggerCounterMap.put(clazz.getName(), counter + 1);
        return counter;
    }
    
    @SuppressWarnings("rawtypes")
    private static ArchitectureLogger[] initArchitectureLoggerArray(Class clazz) {
        ArchitectureLogger architectureLoggerArray[] = new ArchitectureLogger[GeneralConstants.ARCHITECTURE_LOGGER_INSTANCES_PER_CLASS];
        for(int count = 0 ; count < GeneralConstants.LOGGER_INSTANCES_PER_CLASS ; count++) {
            architectureLoggerArray[count] = new ArchitectureLogger(clazz);
        }
        return architectureLoggerArray;
    }
}