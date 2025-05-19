package de.pecus.api.log;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.enums.ArchitectureLogTypeEnum;
import de.pecus.api.util.ValidatorUtil;
import de.pecus.api.vo.ArchitectureRequestVO;
import de.pecus.api.vo.ResponseVO;

/**
 * SmartLogger class created to control and log the use of architecture components
 * 
 * In the database you can control the next log levels: 
 * SMART_LOGGER_ARQ_COMPONENTE: Log all the methods annotated with ArchitectureComponent annotation
 * 
 * @author Juan Carlos Contreras Vazquez Creation date: 07-01-2020
 */
public class ArchitectureLogger {
    
    @SuppressWarnings("rawtypes")
    private Class clazz = null;

    @SuppressWarnings("rawtypes")
    protected ArchitectureLogger(Class clazz) {
        this.clazz = clazz;
    }
    
    private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(GeneralConstants.SMART_LOGGER_THREAD_NUMBER);
    
    /**
     * Log all the methods annotated with ArchitectureComponent annotation
     * 
     * @param token
     * @param message
     * @param throwable
     * @param params
     */
    @SuppressWarnings("rawtypes")
    public void logArchitectureComponent(String className, ArchitectureRequestVO request, ResponseVO response, String message, Throwable throwable, Object... params) {
        Class originalClass = clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            clazz = originalClass;
        }
        log(request, response, message, ArchitectureLogTypeEnum.ARCHITECTURE_COMPONENT, throwable, params);
    }

    /**
     * Main operation with the SmartLogger logic
     * 
     * @param token
     * @param message
     * @param logTypeEnum
     * @param throwable
     * @param params
     * @throws JsonProcessingException
     */
    @SuppressWarnings("rawtypes")
    private void log(ArchitectureRequestVO request, ResponseVO response, String message, ArchitectureLogTypeEnum architectureLogTypeEnum, Throwable throwable, Object... params) {
        // Creamos el objeto runnable
        ArchitectureLoggerThread architectureLoggerThread = new ArchitectureLoggerThread();
        architectureLoggerThread.setToken(request.getToken());
        architectureLoggerThread.setUserId(request.getUserId());
        architectureLoggerThread.setArqComponenteId(request.getArqComponenteId());
        architectureLoggerThread.setMessage(message);
        architectureLoggerThread.setParams(params);
        architectureLoggerThread.setUbicacion(calcularUbicacion(request.getLatitude(), request.getLongitude()));
        architectureLoggerThread.setLogTypeEnum(architectureLogTypeEnum);
        architectureLoggerThread.setThrowable(throwable);
        
        if(!ValidatorUtil.isNull(request)) {
            architectureLoggerThread.setToken(request.getToken());
            if(!ValidatorUtil.isNull(request.getSmartLoggerVO())) {
                architectureLoggerThread.setToken(request.getSmartLoggerVO().getToken());
            }
        }
        
        Runnable runnableThread = (Runnable) architectureLoggerThread;
        executor.execute(runnableThread);
    }
    
    private String calcularUbicacion(Double latitude, Double longitude) {
        String ubicacion = "";
        ubicacion = "" + latitude + longitude;
        return ubicacion;
    }

    public static final void shutdown() {
        executor.shutdown();
    }
    
    public static final boolean isClosed() {
        return executor.isTerminated();
    }
}