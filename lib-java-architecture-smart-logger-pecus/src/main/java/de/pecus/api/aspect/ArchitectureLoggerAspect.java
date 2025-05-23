package de.pecus.api.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Configurable;

import de.pecus.api.log.ArchitectureLogger;
import de.pecus.api.log.ArchitectureLoggerFactory;
import de.pecus.api.vo.ArchitectureRequestVO;
import de.pecus.api.vo.ResponseVO;

/**
 * Spring AOP class created to prepare the request for the ArchitectureLogger
 * 
 * @author Juan Carlos Contreras Vazquez
 */
@Aspect
@Configurable
public class ArchitectureLoggerAspect {
    
    private static final ArchitectureLogger LOG = ArchitectureLoggerFactory.getLogger(ArchitectureLoggerAspect.class);
    
    @Around("de.pecus.api.aspect.SystemArchitecture.architectureComponent()")
    public Object logArchitectureComponentMethods(final ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        ArchitectureRequestVO<?> requestVO = null;
        ResponseVO<?> responseVO = null;
        String token = LoggerAspectUtils.getToken(joinPoint);
        
        for (int count = 0; count < args.length; count++) {
            // Set the requestVO object
            if (args[count] instanceof ArchitectureRequestVO) {
                requestVO = LoggerAspectUtils.setArchitectureLoggerValues(args, count, joinPoint, token);
                LoggerAspectUtils.validatePaginationFields(requestVO, joinPoint.getSignature().getName());
            }
        }
        
        Object proceed = joinPoint.proceed(args);
        
        // Log errors from response
        if (proceed instanceof ResponseVO) {
            responseVO = (ResponseVO<?>) proceed;
        }
        
        // Architecture component log
        LOG.logArchitectureComponent(joinPoint.getSignature().getDeclaringTypeName(), requestVO, responseVO, "Audited by ArchitectureLogger", null);
        
        return proceed;
    }
}