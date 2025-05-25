package de.pecus.api.util;

import java.util.Date;

import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.SmartLoggerVO;

public class SmartLoggerUtil {

    private SmartLoggerUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 
     * @param request
     * @param token
     * @param methodName
     */
    @SuppressWarnings("rawtypes")
    public static void fillLoggerData(RequestVO request, String token, Class clazz, String methodName,
            Date date) {
        request.setSmartLoggerVO(new SmartLoggerVO());
        request.getSmartLoggerVO().setClazz(clazz);
        request.getSmartLoggerVO().setToken(token);
        request.getSmartLoggerVO().setMethodName(methodName);
        request.getSmartLoggerVO().setRequestDateTime(date);
    }

    /**
     * Inicializa requestVO necesario para el funcionamiento del SmartLogger
     * 
     * @param request
     * @param token
     * @param methodName
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static RequestVO fillRequestData(RequestVO request, String token, Class clazz, String methodName) {
        RequestVO requestVO = new RequestVO();
        requestVO.setParameters(request);
        requestVO.setToken(token);
        fillLoggerData(request, token, clazz, methodName, ServiceUtil.getCurrentDate());
        return requestVO;
    }
}