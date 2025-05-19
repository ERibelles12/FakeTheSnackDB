package de.pecus.api.log;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.ThreadContext;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.constant.SmartLoggerConstants;
import de.pecus.api.enums.LogTypeEnum;
import de.pecus.api.util.JsonUtil;
import de.pecus.api.util.ServiceUtil;
import de.pecus.api.util.ValidatorUtil;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;

/**
 * Hilo de SmartLogger, creado para poder persistir de forma asincrona los logs
 * configurados en base de datos, analizando las distintas reglas para cada nivel
 * de logeo.
 * 
 * Esta clase funciona con los appenders de log4j, configurados en el archivo log4j.xml
 * ubicado en el proyecto pecus-commons
 * 
 * Funciona con filtros de los appenders de tipo MarkerFilter, donde de acuerdo a cada
 * marca, elige si persiste el log en base de datos, o si no se cumplen las reglas
 * necesarias, simplemente manda el log a consola
 * 
 * El hilo no debe "comerse" los logs, en caso de no poder persisitirlo por informacion
 * incompleta o por configuracion de prendido y apagado del nivel de logeo, siempre
 * DEBE mandar los logs a consola, para no complicar la deteccion de incidentes en tiempo
 * real
 * 
 * @author Juan Carlos Contreras Vazquez
 */
public class SmartLoggerThread implements Runnable {

	private static final Logger LOG = LogManager.getLogger(SmartLoggerThread.class);
	
	// Constantes utilizadas para identificar las llaves del mapa de Log4j
	private static final String USER_ID_TOKEN_KEY = "userId";
	private static final String TOKEN_KEY = "token";
	private static final String MESSAGE_KEY = "message";
	private static final String CLASS_KEY = "class";
	private static final String METHOD_KEY = "method";
	private static final String REQUEST_DATE_KEY = "requestDate";
	private static final String EVENT_DATE_KEY = "eventDate";
	private static final String REQUEST_VO_KEY = "requestVO";
	private static final String RESPONSE_VO_KEY = "responseVO";
	private static final String STACKTRACE_KEY = "stacktrace";
	private static final String LOG_LEVEL_KEY = "logLevel";
	private static final String ID_CLIENT_INVOKE_KEY = "idClienteInvoke";
	private static final String CLIENT_OPERATION_CODE_KEY = "codigoOperacionCliente";
	private static final String USER_ID_NO_TOKEN = "No Token";
	private static final String ELAPSED_TIME_KEY = "elapsedTime";
	private static final String STATUS_KEY = "status";
	
	// Declaramos las variables globales que se inicializan al generar un nuevo log
	private String token;
	private String message;
	private LogTypeEnum logTypeEnum;
	private Throwable throwable;
	private RequestVO<?> request;
	private ResponseVO<?> response;
	private Class<?> clazz;
	private String methodName;
	private Date requestDate;
	private Date eventDate;
	private Object[] params;
	private String elapsedTime;
	private String status;

	/**
	 * Constructor publico del hilo
	 */
	public SmartLoggerThread() {
	}
	
	/**
	 * Ejecutamos la tarea principal del thread, en la que analizamos si debemos
	 * guardar el log en base de datos o simplemente mandarlo a consola, dependiendo
	 * de si cumple las reglas en cada caso especifico
	 */
	@Override
	public void run() {
	    // Generamos la cadena JSON a partir del RequestVO
	    String requestVO = null;
	    String responseVO = null;
	    try {
            requestVO = JsonUtil.generateStringJson(this.getRequest());
            responseVO = JsonUtil.generateStringJson(this.getResponse());
        } catch (JsonProcessingException e) {
            LOG.error("SmartLogger - El objeto no soporta conversion a formato JSON para ser persistido en la bitacora");
        }
	    
	    /**
	     * Colocamos todas la variables en el contexto para poderlas persistir en base de datos
	     * a traves del JDBC Appender
	     */
	    // Asignamos el token
	    ThreadContext.put(TOKEN_KEY, this.getToken());
	    // Asignamos el campo userId que se guarda en la BD
	    ThreadContext.put(USER_ID_TOKEN_KEY, ServiceUtil.extractFieldValueFromToken(this.getToken(), GeneralConstants.USER_FIELD));
	    // Asignamos el nombre de la clase
	    if(!ValidatorUtil.isNull(this.getClazz())) {
	        ThreadContext.put(CLASS_KEY, this.getClazz().getName());
	    }
	    // Asignamos el nombre del metodo
        ThreadContext.put(METHOD_KEY, this.getMethodName());
        // Asignamos la fecha de la peticion
        if(!ValidatorUtil.isNull(this.getRequestDate())) {
            ThreadContext.put(REQUEST_DATE_KEY, dateToStringWithFormat(this.getRequestDate()));
        }
        // Asignamos la fecha del log
        if(ValidatorUtil.isNull(this.getEventDate())) {
            ThreadContext.put(EVENT_DATE_KEY, dateToStringWithFormat(ServiceUtil.getCurrentDate()));
        } else {
            ThreadContext.put(EVENT_DATE_KEY, dateToStringWithFormat(this.getEventDate()));
        }
        // Asignamos el objeto RequestVO original
        ThreadContext.put(REQUEST_VO_KEY, requestVO);
        // Asignamos el objeto RequestVO original
        ThreadContext.put(RESPONSE_VO_KEY, responseVO);
        // Asignamos el objeto message
        ThreadContext.put(MESSAGE_KEY, MessageFormat.format(this.getMessage(), this.getParams()));
        // Asignamos el stacktrace
        if (!ValidatorUtil.isNull(throwable)) {
            ThreadContext.put(STACKTRACE_KEY, ExceptionUtils.getStackTrace(this.getThrowable()));
        }
        // Asignamos el nivel de logeo
        ThreadContext.put(LOG_LEVEL_KEY, this.getLogTypeEnum().name());
        // Asignamos el id cliente invoke
        if(!ValidatorUtil.isNull(this.request)) {
        	ThreadContext.put(ID_CLIENT_INVOKE_KEY, this.request.getIdClienteInvoke());        	
        }
        // Asignamos el codigo de operacion
        if(!ValidatorUtil.isNull(this.request)) {
        	ThreadContext.put(CLIENT_OPERATION_CODE_KEY, this.request.getCodigoOperacionCliente());
        }
        //Asignamos el tiempo transcurrido en la ejecucion
        ThreadContext.put(ELAPSED_TIME_KEY, this.elapsedTime);
        
        //Asignamos el estatus de la peticion
        ThreadContext.put(STATUS_KEY, this.status);
        
        /**
         * Procesamos el log de acuerdo a cada nivel de logeo
         */
        switch (this.getLogTypeEnum()) {
            case ALL:
                // NOT saved in any database
                LOG.trace(ThreadContext.get(MESSAGE_KEY), this.getParams());
                break;
            case DEBUG:
                // It is saved in audit database only if it complies with the rules 
                if (validateDebugLog(ThreadContext.get(USER_ID_TOKEN_KEY))) {
                    Marker markerDebug = MarkerManager.getMarker(SmartLoggerConstants.SMART_LOGGER_DEBUG.name());
                    LOG.log(SmartLoggerConstants.SMART_LOGGER_DEBUG, markerDebug, ThreadContext.get(MESSAGE_KEY), requestVO, this.getParams());
                } else {
                    LOG.log(SmartLoggerConstants.SMART_LOGGER_DEBUG, ThreadContext.get(MESSAGE_KEY), requestVO, this.getParams());
                }
                break;
            case INFO:
                // It is saved in audit database only if it complies with the rules 
                if (validateInfoLog(ThreadContext.get(USER_ID_TOKEN_KEY))) {
                    Marker markerInfo = MarkerManager.getMarker(SmartLoggerConstants.SMART_LOGGER_INFO.name());
                    LOG.log(SmartLoggerConstants.SMART_LOGGER_INFO, markerInfo, ThreadContext.get(MESSAGE_KEY), this.getParams());
                } else {
                    LOG.log(SmartLoggerConstants.SMART_LOGGER_INFO, ThreadContext.get(MESSAGE_KEY), this.getParams());
                }
                break;
            case WARN:
                // NOT saved in any database
                LOG.warn(ThreadContext.get(MESSAGE_KEY), this.getParams());
                break;
            case ERROR:
                // Recorded in error database 
                if (validateErrorLog(ThreadContext.get(USER_ID_TOKEN_KEY))) {
                    Marker markerError = MarkerManager.getMarker(SmartLoggerConstants.SMART_LOGGER_ERROR.name());
                    LOG.log(SmartLoggerConstants.SMART_LOGGER_ERROR, markerError, ThreadContext.get(MESSAGE_KEY), this.getThrowable());
                } else {
                    LOG.log(SmartLoggerConstants.SMART_LOGGER_ERROR, ThreadContext.get(MESSAGE_KEY), this.getThrowable());
                }
                break;
            case BUSINESS_ERROR:
                // Recorded in business_error database
                if (validateBusinessErrorLog(ThreadContext.get(USER_ID_TOKEN_KEY))) {
                    Marker markerBusinessError = MarkerManager.getMarker(SmartLoggerConstants.SMART_LOGGER_BUSINESS_ERROR.name());
                    LOG.log(SmartLoggerConstants.SMART_LOGGER_BUSINESS_ERROR, markerBusinessError, ThreadContext.get(MESSAGE_KEY), requestVO, this.getParams());
                } else {
                    LOG.log(SmartLoggerConstants.SMART_LOGGER_BUSINESS_ERROR, ThreadContext.get(MESSAGE_KEY), requestVO, this.getParams());
                }
                break;
            case AUDIT:
                // It is saved in audit database only if it complies with the rules 
                if (validateAuditLog(ThreadContext.get(USER_ID_TOKEN_KEY))) {
                    Marker markerAudit = MarkerManager.getMarker(SmartLoggerConstants.SMART_LOGGER_AUDIT.name());
                    LOG.log(SmartLoggerConstants.SMART_LOGGER_AUDIT, markerAudit, ThreadContext.get(MESSAGE_KEY), this.getParams());
                } else {
                    LOG.log(SmartLoggerConstants.SMART_LOGGER_AUDIT, ThreadContext.get(MESSAGE_KEY), this.getParams());
                }
                break;
            case MEASURE_TIME:
                // It is saved in audit database only if it complies with the rules 
                if (validateMeasureTimeLog(ThreadContext.get(USER_ID_TOKEN_KEY))) {
                    Marker markerMesaureTime = MarkerManager.getMarker(SmartLoggerConstants.SMART_LOGGER_MEASURE_TIME.name());
                    LOG.log(SmartLoggerConstants.SMART_LOGGER_MEASURE_TIME, markerMesaureTime, ThreadContext.get(MESSAGE_KEY), this.getParams());
                } else {
                    LOG.log(SmartLoggerConstants.SMART_LOGGER_MEASURE_TIME, ThreadContext.get(MESSAGE_KEY), this.getParams());
                }
                break;
            default:
                // Recorded in error database
                LOG.error(ThreadContext.get(MESSAGE_KEY), this.getThrowable());
                break;
        }
    }

    /**
	 * Validamos las reglas para que se pueda guardar un log de tipo 
	 * BUSINESS_ERROR en la base de datos
	 * @param userId
	 * @return
	 */
	private boolean validateBusinessErrorLog(String userId) {
	    boolean isValid = true;
        
	    if(ValidatorUtil.isNull(this.getClazz()) && ValidatorUtil.isNull(this.getMethodName())) {
	        isValid = false;
	    } else if(ValidatorUtil.isNullOrEmpty(userId)) {
            ThreadContext.put(USER_ID_TOKEN_KEY, USER_ID_NO_TOKEN);
        }
	    
        return isValid;
    }

	/**
     * Validamos las reglas para que se pueda guardar un log de tipo 
     * ERROR en la base de datos
     * @param userId
     * @return
     */
    private boolean validateErrorLog(String userId) {
	    boolean isValid = true;
        
	    if(ValidatorUtil.isNullOrEmpty(userId)) {
            ThreadContext.put(USER_ID_TOKEN_KEY, USER_ID_NO_TOKEN);
        }
        
        return isValid;
    }

    /**
     * Validamos las reglas para que se pueda guardar un log de tipo 
     * MEASURE_TIME en la base de datos
     * @param userId
     * @return
     */
    private boolean validateMeasureTimeLog(String userId) {
	    boolean isValid = true;
        
        if(ValidatorUtil.isNullOrEmpty(userId)) {
            ThreadContext.put(USER_ID_TOKEN_KEY, USER_ID_NO_TOKEN);
        }
        
        return isValid;
    }

    /**
     * Validamos las reglas para que se pueda guardar un log de tipo 
     * AUDIT en la base de datos
     * @param userId
     * @return
     */
    private boolean validateAuditLog(String userId) {
	    boolean isValid = true;
        
        if(ValidatorUtil.isNullOrEmpty(userId)) {
            isValid = false;
        }
        
        // TODO: Validar si el log esta activo para el usuario en sesion
        
        return isValid;
    }

    /**
     * Validamos las reglas para que se pueda guardar un log de tipo 
     * INFO en la base de datos
     * @param userId
     * @return
     */
    private boolean validateInfoLog(String userId) {
        boolean isValid = true;
        
        if(ValidatorUtil.isNullOrEmpty(userId)) {
            isValid = false;
        }
        
        // TODO: Validar si el log esta activo para el usuario en sesion
        
        return isValid;
    }

    /**
     * Validamos las reglas para que se pueda guardar un log de tipo 
     * DEBUG en la base de datos
     * @param userId
     * @return
     */
    private boolean validateDebugLog(String userId) {
	    boolean isValid = true;
	    
	    if(ValidatorUtil.isNullOrEmpty(userId)) {
	        isValid = false;
	    }
	    
	    // TODO: Validar si el log esta activo para el usuario en sesion
//	    smartLoggerValidatorService.validateDebugEnabled();
	    
        return isValid;
    }

    public String dateToStringWithFormat(Date date) {
        String response = null;
        if(!ValidatorUtil.isNull(date)) {
            FastDateFormat fastDateFormat = FastDateFormat.getInstance(GeneralConstants.DATE_TIME_FORMAT_YYYY_MM_DD, TimeZone.getTimeZone("UTC"), Locale.ENGLISH);
    		response = fastDateFormat.format(date);
        }
		return response;
	}

    public synchronized String getToken() {
        return token;
    }

    public synchronized void setToken(String token) {
        this.token = token;
    }

    public synchronized String getMessage() {
        return message;
    }

    public synchronized void setMessage(String message) {
        this.message = message;
    }

    public synchronized LogTypeEnum getLogTypeEnum() {
        return logTypeEnum;
    }

    public synchronized void setLogTypeEnum(LogTypeEnum logTypeEnum) {
        this.logTypeEnum = logTypeEnum;
    }

    public synchronized Throwable getThrowable() {
        return throwable;
    }

    public synchronized void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public synchronized RequestVO<?> getRequest() {
        return request;
    }

    public synchronized void setRequest(RequestVO<?> request) {
        this.request = request;
    }

    public ResponseVO<?> getResponse() {
        return response;
    }

    public void setResponse(ResponseVO<?> response) {
        this.response = response;
    }

    public synchronized Class<?> getClazz() {
        return clazz;
    }

    public synchronized void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public synchronized String getMethodName() {
        return methodName;
    }

    public synchronized void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public synchronized Date getRequestDate() {
        return requestDate;
    }

    public synchronized void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public synchronized Date getEventDate() {
        return eventDate;
    }

    public synchronized void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public synchronized Object[] getParams() {
        return params;
    }

    public synchronized void setParams(Object[] params) {
        this.params = params;
    }

	public String getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	
}