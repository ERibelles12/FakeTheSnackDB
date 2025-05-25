package de.pecus.api.log;

import java.text.MessageFormat;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.ThreadContext;

import de.pecus.api.constant.ArchitectureLoggerConstants;
import de.pecus.api.enums.ArchitectureLogTypeEnum;
import de.pecus.api.util.ValidatorUtil;

/**
 * Hilo de SmartLogger, creado para poder persistir de forma asincrona los logs
 * del uso de los componentes de arquitectura.
 * 
 * Esta clase funciona con los appenders de log4j, configurados en el archivo
 * log4j.xml ubicado en el proyecto pecus-commons
 * 
 * Funciona con filtros de los appenders de tipo MarkerFilter, donde de acuerdo
 * a cada marca, elige si persiste el log en base de datos, o si no se cumplen
 * las reglas necesarias, simplemente manda el log a consola
 * 
 * @author Juan Carlos Contreras Vazquez
 */
public class ArchitectureLoggerThread implements Runnable {

    private static final Logger LOG = LogManager.getLogger(SmartLoggerThread.class);

    // Constantes utilizadas para identificar las llaves del mapa de Log4j
    private static final String USER_ID_KEY = "userId";
    private static final String ARQ_COMPONENTE_ID = "arqComponenteId";
    private static final String MESSAGE_KEY = "message";
    private static final String UBICATION_KEY = "ubicacion";
    private static final String EXCEPTION_KEY = "excepcion";
    private static final String USER_ID_NO_TOKEN = "No userId";

    // Declaramos las variables globales que se inicializan al generar un nuevo log
    private String token;
    private Long userId;
    private Long arqComponenteId;
    private String message;
    private ArchitectureLogTypeEnum logTypeEnum;
    private String ubicacion;
    private Throwable throwable;
    private String excpecion;
    private Object[] params;

    /**
     * Constructor publico del hilo
     */
    public ArchitectureLoggerThread() {
    }

    /**
     * Ejecutamos la tarea principal del thread, en la que analizamos si debemos
     * guardar el log en base de datos o simplemente mandarlo a consola, dependiendo
     * de si cumple las reglas en cada caso especifico
     */
    @Override
    public void run() {
        /**
         * Colocamos todas la variables en el contexto para poderlas persistir en base
         * de datos a traves del JDBC Appender
         */
        // Asignamos el campo userId que se guarda en la BD
        ThreadContext.put(USER_ID_KEY, this.getUserId().toString());
        // Asignamos el id del componente de arquitectura
        ThreadContext.put(ARQ_COMPONENTE_ID, this.getArqComponenteId().toString());
        // Asignamos el objeto message
        ThreadContext.put(MESSAGE_KEY, MessageFormat.format(this.getMessage(), this.getParams()));
        // Asignamos la ubicacion desde donde fue invocado
        ThreadContext.put(UBICATION_KEY, this.getUbicacion());
        // Asignamos la excepcion en caso de recibirla
        if (!ValidatorUtil.isNull(throwable)) {
            ThreadContext.put(EXCEPTION_KEY, ExceptionUtils.getStackTrace(this.getThrowable()));
        }

        /**
         * Procesamos el log de acuerdo a cada nivel de logeo
         */
        switch (this.getLogTypeEnum()) {
            case ARCHITECTURE_COMPONENT:
                // It is saved in audit database only if it complies with the rules
                if (validateArchitectureComponentLog(ThreadContext.get(USER_ID_KEY))) {
                    Marker markerArchitectureComponent = MarkerManager.getMarker(ArchitectureLoggerConstants.ARCHITECTURE_COMPONENT_LOGGER.name());
                    LOG.log(ArchitectureLoggerConstants.ARCHITECTURE_COMPONENT_LOGGER, markerArchitectureComponent, ThreadContext.get(MESSAGE_KEY),
                            this.getParams());
                } else {
                    LOG.log(ArchitectureLoggerConstants.ARCHITECTURE_COMPONENT_LOGGER, ThreadContext.get(MESSAGE_KEY), this.getParams());
                }
                break;
            default:
                // Recorded in error database
                LOG.error(ThreadContext.get(MESSAGE_KEY), this.getExcpecion());
                break;
        }
    }

    private boolean validateArchitectureComponentLog(String userId) {
        boolean isValid = true;
        
        if(ValidatorUtil.isNullOrEmpty(userId)) {
            ThreadContext.put(USER_ID_KEY, USER_ID_NO_TOKEN);
            isValid = false;
        }
        
        return isValid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArqComponenteId() {
        return arqComponenteId;
    }

    public void setArqComponenteId(Long arqComponenteId) {
        this.arqComponenteId = arqComponenteId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArchitectureLogTypeEnum getLogTypeEnum() {
        return logTypeEnum;
    }

    public void setLogTypeEnum(ArchitectureLogTypeEnum logTypeEnum) {
        this.logTypeEnum = logTypeEnum;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getExcpecion() {
        return excpecion;
    }

    public void setExcpecion(String excpecion) {
        this.excpecion = excpecion;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

}