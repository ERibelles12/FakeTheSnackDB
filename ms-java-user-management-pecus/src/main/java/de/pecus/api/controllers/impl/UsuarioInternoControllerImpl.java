package de.pecus.api.controllers.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.pecus.api.controllers.UsuarioInternoController;
import de.pecus.api.log.SmartLogger;
import de.pecus.api.log.SmartLoggerFactory;
import de.pecus.api.services.usuarios.UsuarioInternoRolService;
import de.pecus.api.services.usuarios.UsuarioInternoService;
import de.pecus.api.util.RequestVOUtil;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.util.UsuarioMobileResponseBuilder;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.usuarios.AddRolUserRequestVO;
import de.pecus.api.vo.usuarios.CrearUsuarioRequestVO;
import de.pecus.api.vo.usuarios.DeleteUsuarioRequestVO;
import de.pecus.api.vo.usuarios.DeleteUsuarioRolRequestVO;
import de.pecus.api.vo.usuarios.FindDetailUsuarioRequestVO;
import de.pecus.api.vo.usuarios.FindDetailUsuarioResponseVO;
import de.pecus.api.vo.usuarios.FindListUsuarioRequestVO;
import de.pecus.api.vo.usuarios.FindListUsuarioResponseVO;
import de.pecus.api.vo.usuarios.UpdateUsuarioRequestVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * Clase de controlador para servicio Rest de Usuarios Internos
 * 
 * @author Jorge Serrano
 * @version 1.0
 * @created 21-junio-2019 11:34:31 a. m.
 */
@RestController
@RequestMapping("/usuario-interno")
public class UsuarioInternoControllerImpl implements UsuarioInternoController {

	/** Logger */
	private static final SmartLogger LOGGER = SmartLoggerFactory.getLogger(UsuarioInternoControllerImpl.class);
	
	@Autowired
	private UsuarioInternoService usuarioInternoService;
	
	@Autowired
	private UsuarioInternoRolService usuarioInternoRolService;
	

	public UsuarioInternoControllerImpl() {
		// Constructor por defecto
	}

	/**
	 * Operation: create, Method: POST Crea un usuario interno
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
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Long>> crearUsuario(@RequestHeader Map<String, String> headers,
			@RequestBody CrearUsuarioRequestVO body) {
		// Declarar variable de salida
		ResponseEntity<ResponseVO<Long>> response = null;

		try {
			// Generamos el objeto requestVO
			RequestVO<CrearUsuarioRequestVO> request = RequestVOUtil.setNewRequestVO(headers, body);

			LOGGER.debug(request, "Registrando un nuevo usuario interno  sin rol");
			ResponseVO<Long> serviceResponse = usuarioInternoService.crearUsuarioInterno(request);
			response = UsuarioMobileResponseBuilder.buildCreateUserResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<Long> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}

	/**
	 * Operation: updateUsuario, Method: PUT actualiza un usuario interno
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
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Long>> updateUsuario(@RequestHeader Map<String, String> headers,
			@PathVariable(name = "id", required = false) Long idUsuario, @RequestBody UpdateUsuarioRequestVO body) {
		// Declarar variable de salida
		ResponseEntity<ResponseVO<Long>> response = null;
		
		try {
			body.setId(idUsuario);
			RequestVO<UpdateUsuarioRequestVO> request = RequestVOUtil.setNewRequestVO(headers, body);
			ResponseVO<Long> serviceResponse = usuarioInternoService.updateUsuario(request);
			response = UsuarioMobileResponseBuilder.buildCreateUserResponse(serviceResponse);
		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<Long> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		
		return response;
	}
	
	/**
	 * Operation: findDetailUsuario, Method: GET obtiene el detalle de usuario interno
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
	@GetMapping(value = "/detail", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<FindDetailUsuarioResponseVO>> findDetailUsuario(
			@RequestHeader Map<String, String> headers, @RequestParam(value = "id", required = false) Long id) {
		// Declarar variable de salida
		ResponseEntity<ResponseVO<FindDetailUsuarioResponseVO>> response = null;

		try {
			FindDetailUsuarioRequestVO findDetailUsuarioRequestVO = new FindDetailUsuarioRequestVO();
			findDetailUsuarioRequestVO.setIdUsuario(id);
			RequestVO<FindDetailUsuarioRequestVO> request = RequestVOUtil.setNewRequestVO(headers,
					findDetailUsuarioRequestVO);
			ResponseVO<FindDetailUsuarioResponseVO> serviceResponse = usuarioInternoService.findDetailUsuario(request);
			response = UsuarioMobileResponseBuilder.buildFindDetailUsuarioResponse(serviceResponse);
		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<FindDetailUsuarioResponseVO> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}

		return response;
	}

	
	/**
	 * Operation: deleteUsuario, Method: DELETE Borra a un usuario interno
	 * 
	 */
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
	public ResponseEntity<ResponseVO<Boolean>> deleteUsuario(@RequestHeader Map<String, String> headers, @PathVariable("id") Long idUsuario) {
		// Declarar variable de salida
		ResponseEntity<ResponseVO<Boolean>> response = null;
		
		try {
			DeleteUsuarioRequestVO deleteUsuarioRequestVO = new DeleteUsuarioRequestVO();
			deleteUsuarioRequestVO.setIdUsuario(idUsuario);
			RequestVO<DeleteUsuarioRequestVO> request = RequestVOUtil.setNewRequestVO(headers, deleteUsuarioRequestVO);
			ResponseVO<Boolean> serviceResponse = usuarioInternoService.deleteUsuario(request);
			response = UsuarioMobileResponseBuilder.buildDeleteUsuarioResponse(serviceResponse);
		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<Boolean> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		
		return response;
	}

	/**
	 * Operation: addRolUser, Method: POST Asigna un rol a usuario interno
	 * 
	 */
	@PostMapping(value = "/addRolUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
	public ResponseEntity<ResponseVO<Long>> addRolUser(@RequestHeader Map<String, String> headers,
			@RequestBody AddRolUserRequestVO addRolUserRequestVO) {

		// Declarar variable de salida
		ResponseEntity<ResponseVO<Long>> response = null;

		try {
			// Generamos el objeto requestVO
			RequestVO<AddRolUserRequestVO> request = RequestVOUtil.setNewRequestVO(headers, addRolUserRequestVO);

			LOGGER.debug(request, "Agragando rol a un usuario interno");
			ResponseVO<Long> serviceResponse = usuarioInternoService.addRolUser(request);
			response = UsuarioMobileResponseBuilder.buildAddRolUserResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<Long> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}
	
	
	/**
	 * Operation: deleteRolUsuario, Method: DELETE Borra un rol asignado a usuario interno
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
	@DeleteMapping(value = "/{idUsuario}/rol/{idRol}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Boolean>> deleteRolUsuario(Map<String, String> headers,
			@PathVariable("idUsuario") Long idUsuario, @PathVariable("idRol") Long idRol) {

		// Declarar variable de salida
		ResponseEntity<ResponseVO<Boolean>> response = null;

		try {
			DeleteUsuarioRolRequestVO deleteUsuarioRequestVO = new DeleteUsuarioRolRequestVO();
			deleteUsuarioRequestVO.setIdUsuario(idUsuario);
			deleteUsuarioRequestVO.setIdRol(idRol);
			
			RequestVO<DeleteUsuarioRolRequestVO> request = RequestVOUtil.setNewRequestVO(headers, deleteUsuarioRequestVO);
			ResponseVO<Boolean> serviceResponse = usuarioInternoRolService.deleteUsuarioRol(request);
			response = UsuarioMobileResponseBuilder.buildDeleteUsuarioResponse(serviceResponse);
		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<Boolean> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}

		return response;
	}


	/**
	 * Operation: findListUsuarioInterno, Method: GET Obtiene listado de usuario interno
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
	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<List<FindListUsuarioResponseVO>>> findListUsuarioInterno(Map<String, String> headers,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderType", required = false) String orderType,
			@RequestParam(value = "email", required = false) String email) {
		// Declarar variable de salida
		ResponseEntity<ResponseVO<List<FindListUsuarioResponseVO>>> response = null;
		
		try {
			FindListUsuarioRequestVO findListUsuarioRequestVO = new FindListUsuarioRequestVO();
			findListUsuarioRequestVO.setEmail(email);
			RequestVO<FindListUsuarioRequestVO> request = RequestVOUtil.setNewRequestVO(headers, page, size, orderBy, orderType, findListUsuarioRequestVO);
			ResponseVO<List<FindListUsuarioResponseVO>> serviceResponse = usuarioInternoService.findListUsuario(request);
			response = UsuarioMobileResponseBuilder.buildFindListUsuarioResponse(serviceResponse);
		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<List<FindListUsuarioResponseVO>> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		
		return response;
	}
	

}