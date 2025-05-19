package de.pecus.api.security.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.error.GeneralBusinessErrors;
import de.pecus.api.security.services.ValidateTokenService;
import de.pecus.api.security.services.vo.ValidateTokenResponseVO;
import de.pecus.api.util.JsonUtil;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.util.ValidatorUtil;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.SmartLoggerVO;

/**
 * Clase interceptor para ejecucion de acciones previas y posteriores al 
 * servicio, como validacion de token de sesion o guardado de bitacora
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
public class RestInterceptor extends HandlerInterceptorAdapter {
    
    @Autowired
	private ValidateTokenService validateTokenService;
    
    @Autowired
	private Environment env;
    
    private static final String SWAGGER_ENABLED = "swagger.enabled";
    
    /**
     * Metodo que se ejeucta antes del metodo del controller. 
     * Usado para validacion de token.
     * Validacion del endpoint solicitado contra los permisos en BD del rol-usuario que lo solicita.
     * 
     * @param request HTTP request
     * @param response HTTP response
     * @handler objecto manejador.
     * 
     * @return boolean, true:
     * 						para las peticiones por default(swagger, login, validate y error) metodo OPTIONS.
     * 						para las peticiones con un token valido.
     * 					false:
     * 						para las peticiones con token invalido/expirado.
     * 						para las peticiones sin permisos del rol-usuario.
     * 
     * 
     */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("Authorization");
		
		//asignar headers por default para las peticiones.
		response.setHeader( "Access-Control-Allow-Origin", "*" );
        response.setHeader( "Access-Control-Allow-Headers", "Content-Type,Access-Control-Allow-Headers,Authorization,X-Requested-With,Authorization");
        response.setHeader( "Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
        response.setHeader( "Content-Type", "application/json;charset=UTF-8" );
        
        if(!Boolean.valueOf(env.getProperty(SWAGGER_ENABLED)) && request.getRequestURI().contains("swagger")) {
        	response.setHeader("Content-Type", "application/json; charset=UTF-8");
        	response.setStatus(HttpStatus.UNAUTHORIZED.value());
        	return false;
        }
        
        //Respuesta por default de exito para los recursos de swagger, login, validate, error y metodo OPTIONS.
        if(request.getMethod().equals("OPTIONS") || 
                request.getRequestURI().contains("swagger") ||
                request.getRequestURI().contains("error") ||
                request.getRequestURI().contains("session-management/session/login") ||
                request.getRequestURI().contains("session-management/session/refresh-token") ||
                request.getRequestURI().contains("session-management/session/validate") ||
                request.getRequestURI().contains("session-management/session/application-token") ||
                request.getRequestURI().contains("user-management/user/mobile") ||
                request.getRequestURI().contains("user-management/usuario") ||
                request.getRequestURI().contains("person-management/person") ||
                request.getRequestURI().contains("order-management/orders") ||
                request.getRequestURI().contains("result-management/order") ||
                request.getRequestURI().contains("result-management/result-gab") ||
                request.getRequestURI().contains("result-management/result") ||
                request.getRequestURI().contains("session-management/session/user-config") 
                ){
            return true;
        }
        
        ResponseVO<Void> responseVO = new ResponseVO<Void>();
        RequestVO<Void> requestVO = new RequestVO<Void>();
        ValidateTokenResponseVO validateTokenResponse = validateTokenService.validateToken(token);
        
        //validar si el token es valido.
        if(validateTokenResponse.getSuccess()) {
        	if(!ValidatorUtil.isNullOrEmpty(validateTokenResponse.getNewToken())) {
        	    response.setHeader(GeneralConstants.NEW_TOKEN_HEADER_NAME, validateTokenResponse.getNewToken());
        	}
        	return true;
        } else {
        	//respuesta para el token invalido.
            requestVO.setToken(token);
	        requestVO.setSmartLoggerVO(new SmartLoggerVO());
	        requestVO.getSmartLoggerVO().setMethodName(StringUtils.substringAfterLast(request.getRequestURI(), "/"));
	        ResponseUtil.addError(requestVO, responseVO, GeneralBusinessErrors.INVALID_TOKEN_ERROR);    
	        response.setHeader("Content-Type", "application/json; charset=UTF-8");
	        response.getWriter().append(JsonUtil.generateStringJson(responseVO));
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
		return false;
	}

	/**
	 * Metodo que se ejecuta despues del metodo del controller
	 */
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }
}
