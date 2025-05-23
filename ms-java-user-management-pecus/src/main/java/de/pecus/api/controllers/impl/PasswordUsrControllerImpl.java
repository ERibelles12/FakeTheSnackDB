package de.pecus.api.controllers.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.pecus.api.controllers.PasswordUsrController;
import de.pecus.api.log.SmartLogger;
import de.pecus.api.log.SmartLoggerFactory;
import de.pecus.api.services.usuarios.PasswordUsrService;
import de.pecus.api.util.RequestVOUtil;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.util.UsuarioMobileResponseBuilder;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.usuarios.ResetPasswordRequestVO;
import de.pecus.api.vo.usuarios.UpdatePasswordRequestVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
/**
 * Clase de controlador para la gestión de contraseñas
 * 
 * @author NTT DATA
 * @version 1.0
 * @created 07-julio-2022
 */
@RestController
@RequestMapping("/usuario/password")
public class PasswordUsrControllerImpl implements PasswordUsrController {
	
	/** Logger */
	private static final SmartLogger LOGGER = SmartLoggerFactory.getLogger(PasswordUsrControllerImpl.class);
	
	@Autowired
	private PasswordUsrService passwordUsrService;

	/**
	 * Metodo que actualiza la constrasena de usuario.
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
	@PutMapping(value = "/actualizar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<String>> updatePassword(@RequestHeader Map<String, String> headers,
			@RequestBody UpdatePasswordRequestVO updatePasswordRequestVO) {

		// Declarar variable de salida
		ResponseEntity<ResponseVO<String>> response = null;

		try {
			// Generamos el objeto requestVO
			RequestVO<UpdatePasswordRequestVO> request = RequestVOUtil.setNewRequestVO(headers,
					updatePasswordRequestVO);

			LOGGER.debug(request, "Actualizando contraseña de usuario");
			ResponseVO<String> serviceResponse = passwordUsrService.updatePassword(request);
			response = UsuarioMobileResponseBuilder.buildUpdatePasswordResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<String> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}

	/**
	 * Metodo que actualiza la constrasena de usuario.
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
	@PutMapping(value = "/reestablecer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<String>> resetPassword(@RequestHeader Map<String, String> headers,
			@RequestBody ResetPasswordRequestVO resetPasswordRequestVO) {

		// Declarar variable de salida
		ResponseEntity<ResponseVO<String>> response = null;

		try {
			// Generamos el objeto requestVO
			RequestVO<ResetPasswordRequestVO> request = RequestVOUtil.setNewRequestVO(headers, resetPasswordRequestVO);

			LOGGER.debug(request, "Reseteando contraseña de usuario");
			ResponseVO<String> serviceResponse = passwordUsrService.resetPassword(request);
			response = UsuarioMobileResponseBuilder.buildResetPasswordResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<String> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}

}
