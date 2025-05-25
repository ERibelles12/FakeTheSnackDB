package de.pecus.api.log;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.enums.LogTypeEnum;
import de.pecus.api.error.BusinessError;
import de.pecus.api.util.ValidatorUtil;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;

/**
 * SmartLogger class created to control in the database the log level, it can be
 * configured to log by component, in the database or stdout
 * 
 * In the database you can find the next log levels: 
 * SMART_LOGGER_AUDIT: Log all the methods annotated with Auditable annotation
 * SMART_LOGGER_BUSINESS_ERROR: Log all business errors
 * SMART_LOGGER_ERROR: Log all errors
 * SMART_LOGGER_MEASURE_TIME: Log the runtime of all services
 * SMART_LOGGER_DEBUG: Log SMART_LOGGER_DEBUG and SMART_LOGGER_INFO of the configured components
 * SMART_LOGGER_INFO: Log only SMART_LOGGER_INFO level of the configured components
 * 
 * @author Juan Carlos Contreras Vazquez Creation date: 04-25-2019
 */
public class SmartLogger {

	@SuppressWarnings("rawtypes")
	private Class clazz = null;

	@SuppressWarnings("rawtypes")
	protected SmartLogger(Class clazz) {
		this.clazz = clazz;
	}
	
	private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(GeneralConstants.SMART_LOGGER_THREAD_NUMBER);

	/**
	 * Log SMART_LOGGER_DEBUG and SMART_LOGGER_INFO of the configured components
	 * 
	 * @param request
	 * @param message
	 * @param params
	 */
	@SuppressWarnings("rawtypes")
	public void debug(RequestVO request, String message, Object... params) {
		log(request, message, LogTypeEnum.DEBUG, params);
	}

	/**
	 * Log only SMART_LOGGER_INFO level of the configured components
	 * 
	 * @param request
	 * @param message
	 * @param params
	 */
	@SuppressWarnings("rawtypes")
	public void info(RequestVO request, String message, Object... params) {
		log(request, message, LogTypeEnum.INFO, params);
	}

	/**
	 * Log WARNING and ERROR of the configured components
	 * 
	 * @param request
	 * @param message
	 * @param params
	 */
	@SuppressWarnings("rawtypes")
	public void warning(RequestVO request, String message, Object... params) {
		log(request, message, LogTypeEnum.WARN, params);
	}

	/**
	 * Log all business errors
	 * 
	 * @param token
	 * @param businessError
	 * @param throwable
	 * @param params
	 */
	@SuppressWarnings("rawtypes")
	public void businessError(RequestVO request, BusinessError businessError, Throwable throwable, Object... params) {
		log(request, null, businessError.getDescription(), LogTypeEnum.BUSINESS_ERROR, throwable, params);
	}

	/**
	 * Log all errors
	 * 
	 * @param request
	 * @param response
	 * @param message
	 * @param throwable
	 * @param params
	 */
	@SuppressWarnings("rawtypes")
	public void error(RequestVO request, ResponseVO response, String message, Throwable throwable, Object... params) {
		log(request, response, message, LogTypeEnum.ERROR, throwable, params);
	}
	
	/**
     * Log the runtime of all services
     * 
     * @param clazzName
     * @param request
     * @param response
     * @param message
     * @param throwable
     * @param params
     */
    @SuppressWarnings("rawtypes")
    public void measure(String className, RequestVO request, ResponseVO response, String message, Throwable throwable, Object... params) {
        Class originalClass = clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            clazz = originalClass;
        }
        log(request, response, message, LogTypeEnum.MEASURE_TIME, throwable, params);
    }
    
    /**
     * Log all the methods annotated with Auditable annotation
     * 
     * @param className
     * @param request
     * @param response
     * @param message
     * @param throwable
     * @param params
     */
    @SuppressWarnings("rawtypes")
    public void audit(String className, RequestVO request, ResponseVO response, String message, Throwable throwable, Long elapsedTime, Object... params) {
        Class originalClass = clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            clazz = originalClass;
        }
        log(request, response, message, LogTypeEnum.AUDIT, throwable, elapsedTime, params);
    }

	/**
	 * This operation is used to invoke the log operation without Throwable
	 * 
	 * @param request
	 * @param message
	 * @param logTypeEnum
	 * @param params
	 */
	@SuppressWarnings("rawtypes")
	private void log(RequestVO request, String message, LogTypeEnum logTypeEnum, Object... params) {
		log(request, null,  message, logTypeEnum, null, params);
	}

	/**
	 * Main operation with the SmartLogger logic
	 * 
	 * @param request
	 * @param response
	 * @param message
	 * @param logTypeEnum
	 * @param throwable
	 * @param params
	 * @throws JsonProcessingException
	 */
	@SuppressWarnings("rawtypes")
	private void log(RequestVO request, ResponseVO response, String message, LogTypeEnum logTypeEnum, Throwable throwable, Object... params) {
		// Creamos el objeto runnable
		SmartLoggerThread smartLoggerThread = new SmartLoggerThread();
		smartLoggerThread.setMessage(message);
        smartLoggerThread.setLogTypeEnum(logTypeEnum);
        smartLoggerThread.setThrowable(throwable);
        smartLoggerThread.setRequest(request);
        smartLoggerThread.setResponse(response);
        smartLoggerThread.setParams(params);
        smartLoggerThread.setClazz(clazz);
        if(!ValidatorUtil.isNull(request)) {
            smartLoggerThread.setToken(request.getToken());
            if(!ValidatorUtil.isNull(request.getSmartLoggerVO())) {
                smartLoggerThread.setToken(request.getSmartLoggerVO().getToken());
                smartLoggerThread.setMethodName(request.getSmartLoggerVO().getMethodName());
                smartLoggerThread.setRequestDate(request.getRequestDate());
                smartLoggerThread.setEventDate(request.getSmartLoggerVO().getEventDate());
                if (logTypeEnum.equals(LogTypeEnum.BUSINESS_ERROR)) {
                    smartLoggerThread.setClazz(request.getSmartLoggerVO().getClazz());
                }
            }
        }
        
        if(!smartLoggerThread.getLogTypeEnum().equals(LogTypeEnum.ERROR) && !ValidatorUtil.isNull(smartLoggerThread.getThrowable())) {
            error(request, response, message, throwable, params);
        }
		
		Runnable runnableThread = (Runnable) smartLoggerThread;
		executor.execute(runnableThread);
	}
	
	/**
	 * SmartLogger logic, adding execution time and response status
	 * 
	 * @param request
	 * @param response
	 * @param message
	 * @param logTypeEnum
	 * @param throwable
	 * @param elapsedTime
	 * @param params
	 * @throws JsonProcessingException
	 */
	@SuppressWarnings("rawtypes")
	private void log(RequestVO request, ResponseVO response, String message, LogTypeEnum logTypeEnum, Throwable throwable, Long elapsedTime, Object... params) {
		// Creamos el objeto runnable
		SmartLoggerThread smartLoggerThread = new SmartLoggerThread();
		smartLoggerThread.setMessage(message);
        smartLoggerThread.setLogTypeEnum(logTypeEnum);
        smartLoggerThread.setThrowable(throwable);
        smartLoggerThread.setRequest(request);
        smartLoggerThread.setResponse(response);
        smartLoggerThread.setParams(params);
        smartLoggerThread.setClazz(clazz);
        smartLoggerThread.setElapsedTime(String.valueOf(elapsedTime));
        if(!ValidatorUtil.isNull(response)) {
        	smartLoggerThread.setStatus(String.valueOf(response.getSuccess()));
        }else {
        	smartLoggerThread.setStatus(String.valueOf(false));
        }
        
        if(!ValidatorUtil.isNull(request)) {
            smartLoggerThread.setToken(request.getToken());
            if(!ValidatorUtil.isNull(request.getSmartLoggerVO())) {
                smartLoggerThread.setToken(request.getSmartLoggerVO().getToken());
                smartLoggerThread.setMethodName(request.getSmartLoggerVO().getMethodName());
                smartLoggerThread.setRequestDate(request.getRequestDate());
                smartLoggerThread.setEventDate(request.getSmartLoggerVO().getEventDate());
                if (logTypeEnum.equals(LogTypeEnum.BUSINESS_ERROR)) {
                    smartLoggerThread.setClazz(request.getSmartLoggerVO().getClazz());
                }
            }
        }
        
        if(!smartLoggerThread.getLogTypeEnum().equals(LogTypeEnum.ERROR) && !ValidatorUtil.isNull(smartLoggerThread.getThrowable())) {
            error(request, response, message, throwable, params);
        }
		
		Runnable runnableThread = (Runnable) smartLoggerThread;
		executor.execute(runnableThread);
	}
	
	public static final void shutdown() {
	    executor.shutdown();
	}
	
	public static final boolean isClosed() {
	    return executor.isTerminated();
	}
}