package de.pecus.api.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.error.BusinessError;
import de.pecus.api.error.BusinessErrorUtil;
import de.pecus.api.error.GeneralBusinessErrors;
import de.pecus.api.exception.EnvironmentVariableNotFoundException;

/**
 * Singleton de utiler\u00eda para obtener el valor de la variables de entorno solicitada.
 */
public final class EnvironmentVariableUtil {

    public static final Logger LOG = LoggerFactory.getLogger( EnvironmentVariableUtil.class );

    private static  Map<String, String> varMap = new HashMap<>(  );

    private static EnvironmentVariableUtil  instance = null;

    /** Se ofusca el contructor */
    private EnvironmentVariableUtil() {
        // No debe instanciarse
    }

    /**
     * Metodo estatico que devuelve una sola instancia de la clase
     *
     * @return singleton instance
     */
    public static EnvironmentVariableUtil getInstance(){

        if (null == instance){
            instance = new EnvironmentVariableUtil();
        }

        return instance;
    }

    /**
     * Metodo que devuelve el valor de la variable de entorno requerida
     * @param variable
     * @return
     */
    public static String getVariable(String variable) throws EnvironmentVariableNotFoundException {

        if (!varMap.containsKey( variable )) {

            varMap.put( variable, System.getenv( variable ) );

            // En caso de no encontrarse la vairiable de entorno
            // retorna una excepci\u00f3n
            if (ValidatorUtil.isNull( varMap.get( variable ) )) {
            	BusinessError error = BusinessErrorUtil.getBusinessError(GeneralBusinessErrors.NOT_FOUND_ENVIRONMENT_VAR, GeneralConstants.IDIOMA_ES_MX);
               throw new EnvironmentVariableNotFoundException( error, variable);
            }

            return varMap.get( variable );
        }

        return  varMap.get( variable );
    }
}
