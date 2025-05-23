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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.pecus.api.controllers.UsuarioAplicacionController;
import de.pecus.api.services.usuarios.UsuarioAplicacionService;
import de.pecus.api.util.RequestVOUtil;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.util.UsuarioMobileResponseBuilder;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.usuarios.DeleteUsuarioRequestVO;
import de.pecus.api.vo.usuarios.FindListUsuarioAplicacionResponseVO;
import de.pecus.api.vo.usuarios.FindListUsuarioRequestVO;
import de.pecus.api.vo.usuarios.UpdateUsuarioAplicacionRequestVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;


/**
 * Clase de controlador para servicio Rest de Usuarios Aplicacion
 * 
 * @author Erick Jerzain
 * @version 1.0
 * @created 01-junio-2019 11:34:31 a. m.
 */
@RestController
@RequestMapping("/usuario-aplicacion")
public class UsuarioAplicacionControllerImpl implements UsuarioAplicacionController {
	
	@Autowired
	private UsuarioAplicacionService usuarioAplicacionService;


	/**
	 * Operation: updateUsuario, Method: PUT actualiza un usuario de aplicacion
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
			@PathVariable(name = "id", required = false) Long idUsuario, @RequestBody UpdateUsuarioAplicacionRequestVO body) {
		// Declarar variable de salida
				ResponseEntity<ResponseVO<Long>> response = null;
				
				try {
					body.setId(idUsuario);
					RequestVO<UpdateUsuarioAplicacionRequestVO> request = RequestVOUtil.setNewRequestVO(headers, body);
					ResponseVO<Long> serviceResponse = usuarioAplicacionService.updateUsuario(request);
					response = UsuarioMobileResponseBuilder.buildCreateUserResponse(serviceResponse);
				} catch (Exception exception) {
					// Excepcion no controlada
					ResponseVO<Long> exceptionResponse = ResponseUtil.getErrorResponse(exception);
					response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
				}
				
				return response;
	}

	/**
	 * Operation: deleteUsuario, Method: DELETE Borra un usuario de aplicacion
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
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Boolean>> deleteUsuario(
			@RequestHeader Map<String, String> headers, @PathVariable("id") Long idUsuario) {
		// Declarar variable de salida
				ResponseEntity<ResponseVO<Boolean>> response = null;
				
				try {
					DeleteUsuarioRequestVO deleteUsuarioRequestVO = new DeleteUsuarioRequestVO();
					deleteUsuarioRequestVO.setIdUsuario(idUsuario);
					RequestVO<DeleteUsuarioRequestVO> request = RequestVOUtil.setNewRequestVO(headers, deleteUsuarioRequestVO);
					ResponseVO<Boolean> serviceResponse = usuarioAplicacionService.deleteUsuario(request);
					response = UsuarioMobileResponseBuilder.buildDeleteUsuarioResponse(serviceResponse);
				} catch (Exception exception) {
					// Excepcion no controlada
					ResponseVO<Boolean> exceptionResponse = ResponseUtil.getErrorResponse(exception);
					response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
				}
				
				return response;
	}

	/**
	 * Operation: findListUsuarioAplicacion, GET: DELETE Obtiene un listado de usuarios de aplicacion
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
	public ResponseEntity<ResponseVO<List<FindListUsuarioAplicacionResponseVO>>> findListUsuarioAplicacion(Map<String, String> headers,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderType", required = false) String orderType,
			@RequestParam(value = "email", required = false) String email) {
		// Declarar variable de salida
		ResponseEntity<ResponseVO<List<FindListUsuarioAplicacionResponseVO>>> response = null;
		
		try {
			FindListUsuarioRequestVO findListUsuarioRequestVO = new FindListUsuarioRequestVO();
			findListUsuarioRequestVO.setEmail(email);
			RequestVO<FindListUsuarioRequestVO> request = RequestVOUtil.setNewRequestVO(headers, page, size, orderBy, orderType, findListUsuarioRequestVO);
			ResponseVO<List<FindListUsuarioAplicacionResponseVO>> serviceResponse = usuarioAplicacionService.findListUsuarioApplicacion(request);
			response = UsuarioMobileResponseBuilder.buildFindListUsuarioAppResponse(serviceResponse);
		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<List<FindListUsuarioAplicacionResponseVO>> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		
		return response;
	}

}
