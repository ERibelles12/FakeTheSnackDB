package de.pecus.api.log;

import java.util.HashMap;
import java.util.Map;

import de.pecus.api.constant.GeneralConstants;

/**
 * Factory to create the SmartLogger instances, it is limited by {n} 
 * objects per class to ensure the efficiency of the log.
 * 
 * The amount of objects can be configured inside the general 
 * "Constants.java" class
 *  
 * @author Juan Carlos Contreras Vazquez
 * Creation date: 04-25-2019
 **/
public class SmartLoggerFactory {

    /**
     * SmartLogger instances 
     */
    private static Map<String, SmartLogger[]> smartLoggerMap;
    
    /**
     * Map for switch the returned SmartLogger instances
     */
    private static Map<String, Integer> smartLoggerCounterMap;

    private SmartLoggerFactory() {
    }

    @SuppressWarnings("rawtypes")
    public static synchronized SmartLogger getLogger(Class clazz) {
        SmartLogger smartLogger = null;
        if (smartLoggerMap == null) {
            smartLoggerMap = new HashMap<String, SmartLogger[]>();
        }
        if (smartLoggerMap.containsKey(clazz.getName()) == false) {
            smartLoggerMap.put(clazz.getName(), initSmartLoggerArray(clazz));
        }
        smartLogger = smartLoggerMap.get(clazz.getName())[getSmartLoggerCounter(clazz)];
        return smartLogger;
    }
    
    @SuppressWarnings("rawtypes")
    private static int getSmartLoggerCounter(Class clazz) {
        int counter = GeneralConstants.ZERO;
        if (smartLoggerCounterMap == null) {
            smartLoggerCounterMap = new HashMap<String, Integer>();
        }
        if (smartLoggerCounterMap.get(clazz.getName()) == null || smartLoggerCounterMap.get(clazz.getName()).equals(GeneralConstants.LOGGER_INSTANCES_PER_CLASS)) {
            smartLoggerCounterMap.put(clazz.getName(), GeneralConstants.ZERO);
        }
        counter = smartLoggerCounterMap.get(clazz.getName());
        smartLoggerCounterMap.put(clazz.getName(), counter + 1);
        return counter;
    }
    
    @SuppressWarnings("rawtypes")
    private static SmartLogger[] initSmartLoggerArray(Class clazz) {
        SmartLogger smartLoggerArray[] = new SmartLogger[GeneralConstants.LOGGER_INSTANCES_PER_CLASS];
        for(int count = 0 ; count < GeneralConstants.LOGGER_INSTANCES_PER_CLASS ; count++) {
            smartLoggerArray[count] = new SmartLogger(clazz);
        }
        return smartLoggerArray;
    }
}