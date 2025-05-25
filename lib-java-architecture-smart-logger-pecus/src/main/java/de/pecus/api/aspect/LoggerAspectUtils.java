package de.pecus.api.aspect;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.log.SmartLogger;
import de.pecus.api.log.SmartLoggerFactory;
import de.pecus.api.util.ServiceUtil;
import de.pecus.api.util.ValidatorUtil;
import de.pecus.api.vo.ArchitectureRequestVO;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.SmartLoggerVO;

public class LoggerAspectUtils {
    
    /**
     * List suffix to fulfill pagination objects
     */
    private static final String LIST_SUFFIX = "List";
    
    private static final SmartLogger LOG = SmartLoggerFactory.getLogger(SmartLoggerAspect.class);
    
    /**
     * If pagination parameters are null, this method fill them to avoid NPE
     * @param requestVO
     */
    protected static void validatePaginationFields(RequestVO<?> requestVO, String methodName) {
        if(!ValidatorUtil.isNull(requestVO) && StringUtils.contains(methodName, LIST_SUFFIX)) {
            if(ValidatorUtil.isNull(requestVO.getPage())) {
                requestVO.setPage(GeneralConstants.INITIAL_PAGE);
            }
            if(ValidatorUtil.isNull(requestVO.getSize())) {
                requestVO.setSize(GeneralConstants.INITIAL_SIZE);
            }
        }
    }

    /**
     * Method that extracts the value of the token to automatically assign it to the
     * RequestVO and SmartLoggerVO objects
     * 
     * @param joinPoint
     * @return
     */
    protected static String getToken(final ProceedingJoinPoint joinPoint) {
        String token = "";
        Object[] args = joinPoint.getArgs();
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String[] parameterNames = codeSignature.getParameterNames();

        for (int count = 0; count < parameterNames.length; count++) {
            if (parameterNames[count].equals("token")) {
                token = (String) args[count];
            }
        }
        return token;
    }

    /**
     * Set the values required by the SmartLogger
     * 
     * @param args
     * @param count
     * @param joinPoint
     * @param token
     */
    @SuppressWarnings("rawtypes")
    protected static RequestVO setSmartLoggerValues(Object[] args, int count, ProceedingJoinPoint joinPoint, String token) {
        RequestVO request = (RequestVO) args[count];
        return createRequestVO(request, joinPoint, token);
    }
    
    /**
     * Fill requestVO with joinPoint and token information, creating a new SmartLoggerVO
     * to hold the data
     * @param request
     * @param joinPoint
     * @param token
     * @return
     */
    protected static RequestVO<?> createRequestVO(RequestVO<?> request, ProceedingJoinPoint joinPoint, String token) {
        if(ValidatorUtil.isNull(request)) {
            request = new RequestVO<>();
        }
        if(ValidatorUtil.isNullOrEmpty(token) && !ValidatorUtil.isNullOrEmpty(request.getToken())) {
            token = request.getToken();
        }
        SmartLoggerVO smartLogger = new SmartLoggerVO();
        try {
            // Set SmartLogger to store logger information
            smartLogger.setClazz(Class.forName(joinPoint.getSignature().getDeclaringTypeName()));
            smartLogger.setMethodName(joinPoint.getSignature().getName());
            smartLogger.setRequestDateTime(request.getRequestDate());
            String timezone = ValidatorUtil.isNullOrEmpty(request.getTimeZoneUsuario()) ? "America/Mexico_City": request.getTimeZoneUsuario();
            smartLogger.setEventDate(ServiceUtil.getCurrentDate(timezone));
            smartLogger.setToken(token);
        } catch (ClassNotFoundException cnfe) {
            LOG.error(request, null, "Class not {0} found", cnfe, joinPoint.getSignature().getDeclaringTypeName());
        }
        request.setSmartLoggerVO(smartLogger);
        request.setToken(token);
        return request;
    }

    /**
     * Operation to verify if the request has a requestVO object
     * 
     * @param args
     * @param count
     * @return
     */
    protected static boolean hasRequestVO(Object[] args, int count) {
        boolean hasRequestVO = false;
        if (args[count] instanceof RequestVO) {
            hasRequestVO = Boolean.TRUE;
        }
        return hasRequestVO;
    }

    @SuppressWarnings("rawtypes")
    public static ArchitectureRequestVO<?> setArchitectureLoggerValues(Object[] args, int count,
            ProceedingJoinPoint joinPoint, String token) {
        RequestVO request = (RequestVO) args[count];
        ArchitectureRequestVO architectureRequestVO = (ArchitectureRequestVO) createRequestVO(request, joinPoint, token);
        return architectureRequestVO;
    }
}