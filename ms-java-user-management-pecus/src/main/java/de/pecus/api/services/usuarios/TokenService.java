package de.pecus.api.services.usuarios;

import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.messaging.AsignarTokenFirebaseRequestVO;
import de.pecus.api.vo.messaging.SendActivationCodeRequestVO;
import de.pecus.api.vo.messaging.SendActivationCodeResponseVO;
import de.pecus.api.vo.messaging.ValidateCodeRequestVO;
import de.pecus.api.vo.messaging.ValidateCodeResponseVO;


/**
 * Interfaz para administracionde de token/codigos
 * @author NTT DATA
 */
public interface TokenService {
	
	/** Crea un nuevo codigo de activacion y envia la notificacion
	 * @param request Objeto con parametros de entrada para la generacion del codigo
	 * @return codigo de activacion
	 */
	ResponseVO<SendActivationCodeResponseVO> createCodigoActivacion(RequestVO<SendActivationCodeRequestVO> request);
	
	/**
	 * Valida un codigo de activacion y devuelve el estatus
	 * @param request
	 * @return
	 */
	ResponseVO<ValidateCodeResponseVO> validateCodigoActivacion(RequestVO<ValidateCodeRequestVO> request);
	
	/**
	 * Actualiza el token de firebase de un usuario por medio del telefono o email
	 * @param requestVO con los parametros de entrada 
	 * @return Objeto response bandera que indica si se actualizo el token
	 * 
	 */
	ResponseVO<Boolean> asignarTokenFirebase(RequestVO<AsignarTokenFirebaseRequestVO> request);

}
