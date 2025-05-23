package de.pecus.api.controllers.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.pecus.api.controllers.TokenController;
import de.pecus.api.log.SmartLogger;
import de.pecus.api.log.SmartLoggerFactory;
import de.pecus.api.services.usuarios.TokenService;
import de.pecus.api.util.RequestVOUtil;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.util.UsuarioMobileResponseBuilder;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.messaging.AsignarTokenFirebaseRequestVO;
import de.pecus.api.vo.messaging.SendActivationCodeRequestVO;
import de.pecus.api.vo.messaging.SendActivationCodeResponseVO;
import de.pecus.api.vo.messaging.ValidateCodeRequestVO;
import de.pecus.api.vo.messaging.ValidateCodeResponseVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * Clase de controlador para la gestión de token/codigos
 * 
 * @author NTT DATA
 * @version 1.0
 * @created 07-julio-2022
 */
@RestController
@RequestMapping("/usuario")
public class TokenControllerImpl implements TokenController {
	
	/** Logger */
	private static final SmartLogger LOGGER = SmartLoggerFactory.getLogger(TokenControllerImpl.class);

	@Autowired
	private TokenService tokenService;

	public TokenControllerImpl() {
		// Constructor por defecto
	}

	/**
	 * Operation: Create, Method: POST, Description: Envia un código de activacion
	 * 
	 */
	@Override
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "eyJhbGciOiJIUzI1NiJ9.eyJUT0tFTl9EQVRBIjp7ImFwZWxs", paramType = "header", dataTypeClass = String.class),
		@ApiImplicitParam(name = "mac-address", value = " 2C:54:91:88:C9:E3 or 2c-54-91-88-c9-e3", paramType = "header", dataTypeClass = String.class),
		@ApiImplicitParam(name = "request-date", value = "2021-09-07 17:29:25.443", paramType = "header", dataTypeClass = String.class, required = true),
		@ApiImplicitParam(name = "latitude", value = "-74.00898606", paramType = "header", dataTypeClass = String.class),
		@ApiImplicitParam(name = "longitude", value = "40.71727401", paramType = "header", dataTypeClass = String.class),
		@ApiImplicitParam(name = "language", value = "es_MX", paramType = "header", dataTypeClass = String.class, required = true),
		@ApiImplicitParam(name = "time-zone", value = "America/Mexico City", paramType = "header", dataTypeClass = String.class),
		@ApiImplicitParam(name = "id-client-invoke", value = "21", paramType = "header", dataTypeClass = String.class, required = true),
		@ApiImplicitParam(name = "client-operation-code", value = "qWERGFDSRSGfsdertRTRe2345RTd", paramType = "header", dataTypeClass = String.class, required = true) })
	@PostMapping(value = "/codigoActivacion", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<SendActivationCodeResponseVO>> crearCodigoActivacion(@RequestHeader Map<String, String> headers,
			@RequestBody SendActivationCodeRequestVO sendActivationCodeRequestVO) {

		// Declarar variable de salida
		ResponseEntity<ResponseVO<SendActivationCodeResponseVO>> response = null;

		try {
			// Generamos el objeto requestVO		
			RequestVO<SendActivationCodeRequestVO> request = RequestVOUtil.setNewRequestVO(headers, sendActivationCodeRequestVO);
			
			LOGGER.debug(request, "Generando codigo de activacion");
			ResponseVO<SendActivationCodeResponseVO> serviceResponse = tokenService.createCodigoActivacion(request);
			response = UsuarioMobileResponseBuilder.buildcreateCodeActivationResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<SendActivationCodeResponseVO> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}

	/**
	 * Operation: Update, Method: PUT, Description: Valida un codigo de activacion
	 * 
	 */
	@Override
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "eyJhbGciOiJIUzI1NiJ9.eyJUT0tFTl9EQVRBIjp7ImFwZWxs", paramType = "header", dataTypeClass = String.class),
		@ApiImplicitParam(name = "mac-address", value = " 2C:54:91:88:C9:E3 or 2c-54-91-88-c9-e3", paramType = "header", dataTypeClass = String.class),
		@ApiImplicitParam(name = "request-date", value = "2021-09-07 17:29:25.443", paramType = "header", dataTypeClass = String.class, required = true),
		@ApiImplicitParam(name = "latitude", value = "-74.00898606", paramType = "header", dataTypeClass = String.class),
		@ApiImplicitParam(name = "longitude", value = "40.71727401", paramType = "header", dataTypeClass = String.class),
		@ApiImplicitParam(name = "language", value = "es_MX", paramType = "header", dataTypeClass = String.class, required = true),
		@ApiImplicitParam(name = "time-zone", value = "America/Mexico City", paramType = "header", dataTypeClass = String.class),
		@ApiImplicitParam(name = "id-client-invoke", value = "21", paramType = "header", dataTypeClass = String.class, required = true),
		@ApiImplicitParam(name = "client-operation-code", value = "qWERGFDSRSGfsdertRTRe2345RTd", paramType = "header", dataTypeClass = String.class, required = true) })
	@PutMapping(value = "/validarCodigo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<ValidateCodeResponseVO>> validarCodigoActivacion(@RequestHeader Map<String, String> headers,
			@RequestBody ValidateCodeRequestVO validateCodeRequestVO) {
		
		// Declarar la variable de salida
		ResponseEntity<ResponseVO<ValidateCodeResponseVO>> response = null;
		
		try {
			// Se genera el objeto requestVO
			RequestVO<ValidateCodeRequestVO> request = RequestVOUtil.setNewRequestVO(headers, validateCodeRequestVO);
			
			LOGGER.debug(request, "Validando codigo de activacion");
			ResponseVO<ValidateCodeResponseVO> serviceResponse = tokenService.validateCodigoActivacion(request);
			response = UsuarioMobileResponseBuilder.buildValidateCodeResponse(serviceResponse);
			
		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<ValidateCodeResponseVO> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		
		return response;
	}

	/**
	 * Metodo que actualiza el token para las notificaciones PUSH.
	 * 
	 */
	@Override
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "eyJhbGciOiJIUzI1NiJ9.eyJUT0tFTl9EQVRBIjp7ImFwZWxs", paramType = "header", dataTypeClass = String.class),
		@ApiImplicitParam(name = "mac-address", value = " 2C:54:91:88:C9:E3 or 2c-54-91-88-c9-e3", paramType = "header", dataTypeClass = String.class),
		@ApiImplicitParam(name = "request-date", value = "2021-09-07 17:29:25.443", paramType = "header", dataTypeClass = String.class, required = true),
		@ApiImplicitParam(name = "latitude", value = "-74.00898606", paramType = "header", dataTypeClass = String.class),
		@ApiImplicitParam(name = "longitude", value = "40.71727401", paramType = "header", dataTypeClass = String.class),
		@ApiImplicitParam(name = "language", value = "es_MX", paramType = "header", dataTypeClass = String.class, required = true),
		@ApiImplicitParam(name = "time-zone", value = "America/Mexico City", paramType = "header", dataTypeClass = String.class),
		@ApiImplicitParam(name = "id-client-invoke", value = "21", paramType = "header", dataTypeClass = String.class, required = true),
		@ApiImplicitParam(name = "client-operation-code", value = "qWERGFDSRSGfsdertRTRe2345RTd", paramType = "header", dataTypeClass = String.class, required = true) })
	@PutMapping(value = "/asignarTokenFirebase", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Boolean>> asignarTokenFirebase(@RequestHeader Map<String, String> headers,
			@RequestBody AsignarTokenFirebaseRequestVO asignarTokenFirebaseRequestVO) {

		// Declarar variable de salida
		ResponseEntity<ResponseVO<Boolean>> response = null;

		try {
			// Generamos el objeto requestVO
			RequestVO<AsignarTokenFirebaseRequestVO> request = RequestVOUtil.setNewRequestVO(headers,
					asignarTokenFirebaseRequestVO);

			LOGGER.debug(request, "Actualizando token de notificaciones push de usuario");
			ResponseVO<Boolean> serviceResponse = tokenService.asignarTokenFirebase(request);
			response = UsuarioMobileResponseBuilder.buildAsignarTokenResponse(serviceResponse);

		} catch (Exception exception) {
			ResponseVO<Boolean> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}

}
