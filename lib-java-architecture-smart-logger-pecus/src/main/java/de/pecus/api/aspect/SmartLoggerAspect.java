package de.pecus.api.aspect;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Configurable;

import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.log.SmartLogger;
import de.pecus.api.log.SmartLoggerFactory;
import de.pecus.api.util.ValidatorUtil;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseErrorVO;
import de.pecus.api.vo.ResponseVO;

/**
 * Spring AOP class created to prepare the requestVO object for the SmartLogger,
 * this intercepts the original request to extract all the information required
 * by the SmartLogger
 * 
 * @author Juan Carlos Contreras Vázquez
 */
@Aspect
@Configurable
public class SmartLoggerAspect {

    private static final SmartLogger LOG = SmartLoggerFactory.getLogger(SmartLoggerAspect.class);

	/**
	 * Set the method name, request date and token for each request that have a
	 * RequestVO to persist it in the database with SmartLogger
	 * 
	 * If the token is present, it is obtained automatically and configured for the
	 * requestVO and SmartLogger objects
	 * 
	 * If there is no requestVO present in the request arguments, a warning message
	 * is triggered to prevent the programmer
	 * 
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Around("de.pecus.api.aspect.SystemArchitecture.businessService() || de.pecus.api.aspect.SystemArchitecture.businessServiceImpl()")
	public Object setSmartLoggerValues(final ProceedingJoinPoint joinPoint) throws Throwable {
	    
	    // Measure execution time
	    StopWatch stopWatch = new StopWatch();
	    stopWatch.start();
	    RequestVO<?> requestVO = null;
	    
		Object[] args = joinPoint.getArgs();
		boolean hasRequestVO = false;
		String token = LoggerAspectUtils.getToken(joinPoint);
		for (int count = 0; count < args.length; count++) {
			// If the the argument is a requestVO object
			hasRequestVO = LoggerAspectUtils.hasRequestVO(args, count) ? Boolean.TRUE : hasRequestVO;
			// Set the requestVO object
			if (args[count] instanceof RequestVO) {
				requestVO = LoggerAspectUtils.setSmartLoggerValues(args, count, joinPoint, token);
				LoggerAspectUtils.validatePaginationFields(requestVO, joinPoint.getSignature().getName());
			}
		}
		if (!hasRequestVO) {
		    if(!"validate".equals(joinPoint.getSignature().getName())) {
		        LOG.warning(requestVO, String.format("Method %s is not auditable by SmartLogger, it requires a RequestVO in the signature", joinPoint.getSignature().getName()));
		    }
		}
		
		Object proceed = null;
		ResponseVO<?> responseVO = null;
		
		try {
		    proceed = joinPoint.proceed(args);
		    
		    // Log errors
	        if(!ValidatorUtil.isNull(requestVO)) {
	            // Log errors from response
	            if (proceed instanceof ResponseVO) {
	                responseVO = (ResponseVO<?>) proceed;
	                logErrors(requestVO, responseVO, token);
	            }
	        }
		} catch(Exception exception) {
		    responseVO = new ResponseVO<>();
		    // Si no se cachó una excepcion adecuadamente, se genera una excepcion general
		    List<ResponseErrorVO> errors = new ArrayList<>();
		    // Se genero una excepcion de cualquier otro tipo y no se controlo
            errors.add( new ResponseErrorVO(GeneralConstants.GENERAL_ERROR_CODE, GeneralConstants.GENERAL_ERROR_KEY, GeneralConstants.GENERAL_ERROR_MESSAGE, exception) );
		    responseVO.setErrors(errors);
		    // Logeamos los errores
		    logErrors(requestVO, responseVO, token);
		}
		
		// Log measure time
		stopWatch.stop();
		long timeElapsed = stopWatch.getTime();
		
		// If no requestoVO present
		if(ValidatorUtil.isNull(requestVO)) {
		    // Setting the information of the request to the SmartLogger object
		    requestVO = LoggerAspectUtils.createRequestVO(requestVO, joinPoint, token);
		}
		
		// Omitimos medir el tiempo del validate
		// Descomentar las siguientes lineas en caso de habilitar la tabla MEASURE
		/*if(!"validate".equals(joinPoint.getSignature().getName())) {
		    LOG.measure(joinPoint.getSignature().getDeclaringTypeName(), requestVO, responseVO, String.valueOf(timeElapsed), null);
		}*/
		
		return proceed;
	}
	
	@Around("de.pecus.api.aspect.SystemArchitecture.auditable()")
    public Object logAuditableMethods(final ProceedingJoinPoint joinPoint) throws Throwable {
		
		// Measure execution time
		long startTime = System.currentTimeMillis();
	    
	    Object[] args = joinPoint.getArgs();
	    RequestVO<?> requestVO = null;
	    ResponseVO<?> responseVO = null;
	    String token = LoggerAspectUtils.getToken(joinPoint);
	    
	    for (int count = 0; count < args.length; count++) {
            // Set the requestVO object
            if (args[count] instanceof RequestVO) {
                requestVO = LoggerAspectUtils.setSmartLoggerValues(args, count, joinPoint, token);
                LoggerAspectUtils.validatePaginationFields(requestVO, joinPoint.getSignature().getName());
            }
        }
	    
	    Object proceed = joinPoint.proceed(args);
	    
	    // Log errors from response
        if (proceed instanceof ResponseVO) {
            responseVO = (ResponseVO<?>) proceed;
        }
        
        // Log measure time
        long endTime = System.currentTimeMillis();        
        long timeElapsed = endTime - startTime;
        
        // Audit log
        LOG.audit(joinPoint.getSignature().getDeclaringTypeName(), requestVO, responseVO, "", null, timeElapsed);
	    
	    return proceed;
	}
	
	/**
     * Log error messages generated within the service consumption
     * @param requestVO
     * @param args
     * @param count
     * @param joinPoint
     * @param token
     */
    private void logErrors(RequestVO<?> requestVO, ResponseVO<?> response, String token) {
        if(!ValidatorUtil.isNullOrEmpty(response.getErrors())) {
            for(ResponseErrorVO responseError : response.getErrors()) {
                LOG.error(requestVO, response, responseError.getCode() + ": " + responseError.getMessage(), responseError.getException());
            }
        }
    }
}