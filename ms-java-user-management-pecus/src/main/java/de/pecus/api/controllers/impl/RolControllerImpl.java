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

import de.pecus.api.controllers.RolController;
import de.pecus.api.log.SmartLogger;
import de.pecus.api.log.SmartLoggerFactory;
import de.pecus.api.services.usuarios.RolService;
import de.pecus.api.util.AplicacionServicesResponseBuilder;
import de.pecus.api.util.RequestVOUtil;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.util.RolFuncionMenuResponseBuilder;
import de.pecus.api.util.RolFuncionSubmenuResponseBuilder;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.funciones.CreateRolFuncionMenuRequestVO;
import de.pecus.api.vo.funciones.CreateRolFuncionRequestVO;
import de.pecus.api.vo.funciones.CreateRolFuncionResponseVO;
import de.pecus.api.vo.funciones.CreateRolFuncionSubmenuRequestVO;
import de.pecus.api.vo.funciones.DeleteRolFuncionMenuRequestVO;
import de.pecus.api.vo.funciones.DeleteRolFuncionRequestVO;
import de.pecus.api.vo.funciones.DeleteRolFuncionSubmenuRequestVO;
import de.pecus.api.vo.funciones.FindListRolFuncionMenuRequestVO;
import de.pecus.api.vo.funciones.FindListRolFuncionMenuResponseVO;
import de.pecus.api.vo.funciones.FindListRolFuncionRequestVO;
import de.pecus.api.vo.funciones.FindListRolFuncionResponseVO;
import de.pecus.api.vo.funciones.FindListRolFuncionSubmenuRequestVO;
import de.pecus.api.vo.funciones.FindListRolFuncionSubmenuResponseVO;
import de.pecus.api.vo.roles.CreateRolRequestVO;
import de.pecus.api.vo.roles.DeleteRolRequestVO;
import de.pecus.api.vo.roles.FindDetailRolRequestVO;
import de.pecus.api.vo.roles.FindDetailRolResponseVO;
import de.pecus.api.vo.roles.FindListRolRequestVO;
import de.pecus.api.vo.roles.FindListRolResponseVO;
import de.pecus.api.vo.roles.UpdateRolRequestVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * Clase de controlador para servicio Rest de Aplicacion
 * 
 * @author Luis Enrique Sanchez Santiago
 * @version 1.0
 * @created 21-Julio-2022 11:34:31 a. m.
 */
@RestController
@RequestMapping("")
public class RolControllerImpl implements RolController {

	public static final SmartLogger LOGGER = SmartLoggerFactory.getLogger(RolControllerImpl.class);

	@Autowired
	private RolService rolService;

	/**
	 * Operation: Operation: create, Method: POST Crea un registro en la BB.DD.
	 * 
	 * @param headers Arreglo de objetos de tipo llave valor para almacenar los
	 *                headers
	 * @param body    Cuerpo de la peticion con la informacion del regsitro que se
	 *                desean insertar
	 * @return Ojeto con el cuerpo de respuesta (ong con el id del registro
	 *         actualizado) y le codigo de respuesta http corrspondiente a la
	 *         operacion
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
	@PostMapping(value = "/rol", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Long>> createRol(@RequestHeader Map<String, String> headers,
			@RequestBody CreateRolRequestVO body) {

		// Generamos el objeto requestVO
		RequestVO<CreateRolRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, body);

		ResponseEntity<ResponseVO<Long>> response = null;
		try {
			// Invocar al servicio
			ResponseVO<Long> serviceResponse = rolService.createRol(requestVO);

			response = AplicacionServicesResponseBuilder.buildCreateOrUpdateResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion controlada
			ResponseVO<Long> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;

	}

	/**
	 * Operation: Operation: update, Method: POST Actualiza un registro en la BB.DD.
	 * 
	 * @param headers Arreglo de objetos de tipo llave valor para almacenar los
	 *                headers
	 * @param id      Identificador del registro a actualizar
	 * @param body    Cuerpo de la peticion con la informacion del regsitro que se
	 *                desean insertar
	 * @return Ojeto con el cuerpo de respuesta (ong con el id del registro
	 *         actualizado) y le codigo de respuesta http corrspondiente a la
	 *         operacion
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
	@PutMapping(value = "/rol/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Long>> updateRol(@RequestHeader Map<String, String> headers,
			@PathVariable(name = "id", required = false) Long id, @RequestBody UpdateRolRequestVO body) {

		// Declaracion de variable
		ResponseEntity<ResponseVO<Long>> response = null;

		body.setId(id);

		// Generamos el objeto requestVO
		RequestVO<UpdateRolRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, body);

		try {
			// Invocar al servicio
			ResponseVO<Long> serviceResponse = rolService.updateRol(requestVO);

			response = AplicacionServicesResponseBuilder.buildCreateOrUpdateResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion controlada
			ResponseVO<Long> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;

	}

	/**
	 * Operation: delete, Method: DELETE Elimina un registro en la BB.DD.
	 * 
	 * @param headers Arreglo de objetos de tipo llave valor para almacenar los
	 *                headers
	 * @param id      Identificador del registro a actualizar
	 * @return Objeto con el resultado del borrado
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
	@DeleteMapping(value = "/rol/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Boolean>> deleteRol(@RequestHeader Map<String, String> headers,
			@PathVariable("id") Long id) {
		// Declaracion de variables
		ResponseEntity<ResponseVO<Boolean>> response = null;

		// Generamos el objeto requestVO
		DeleteRolRequestVO deleteRolRequestVO = new DeleteRolRequestVO();
		deleteRolRequestVO.setId(id);
		RequestVO<DeleteRolRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, deleteRolRequestVO);

		try {

			ResponseVO<Boolean> serviceResponse = rolService.deleteRol(requestVO);

			response = AplicacionServicesResponseBuilder.buildDeleteResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<Boolean> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}

	/**
	 * Operation: findDetailRol, Method: Busca el detalle de un registro en la base
	 * de datos.
	 * 
	 * @param headers Arreglo de objetos de tipo llave valor para almacenar los
	 *                headers
	 * @param id      Identificador del registro a buscar
	 * @return Objeto con el resultado del borrado
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
	@GetMapping(value = "/rol/detail", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<FindDetailRolResponseVO>> findDetailRol(@RequestHeader Map<String, String> headers,
			@RequestParam(value = "id", required = false) Long id) {

		// Declaracion de variables
		ResponseEntity<ResponseVO<FindDetailRolResponseVO>> response = null;

		// Crear el objeto requestVO
		FindDetailRolRequestVO findDetailRolRequestVO = new FindDetailRolRequestVO();
		findDetailRolRequestVO.setId(id);
		RequestVO<FindDetailRolRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, findDetailRolRequestVO);

		try {

			// Invocar al metodo de busqueda
			ResponseVO<FindDetailRolResponseVO> serviceResponse = rolService.findDetailRol(requestVO);

			response = AplicacionServicesResponseBuilder.buildFindDetailRolResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<FindDetailRolResponseVO> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}

	/**
	 * Operation: findList, Method: GET Consulta la lista de registros en base a
	 * varios parametros. El resultado se regresa como un arreglo de registros.
	 * 
	 * @return ResponseVO con la lista de registros encontrada
	 * 
	 * @param nombre Patron de nombre a buscar
	 * @param page   Numero de pagina
	 * @param size   Tamano de pagina
	 * @param token  Token de sesion
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
	@GetMapping(value = "/rol/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<List<FindListRolResponseVO>>> findListRol(
			@RequestHeader Map<String, String> headers, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderType", required = false) String orderType,
			@RequestParam(value = "idAplicacion", required = false) Long idAplicacion,
			@RequestParam(value = "idNombre", required = false) String idNombre,
			@RequestParam(value = "idUsuario", required = false) Long idUsuario) {
		// Declarar variables
		ResponseEntity<ResponseVO<List<FindListRolResponseVO>>> response = null;

		FindListRolRequestVO findListRolRequestVO = new FindListRolRequestVO();
		findListRolRequestVO.setIdNombre(idNombre);
		findListRolRequestVO.setIdAplicacion(idAplicacion);
		findListRolRequestVO.setIdUsuario(idUsuario);

		RequestVO<FindListRolRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, page, size, orderBy,
				orderType, findListRolRequestVO);

		try {

			ResponseVO<List<FindListRolResponseVO>> serviceResponse = rolService.findListRol(requestVO);
			response = AplicacionServicesResponseBuilder.buildFindListRolResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<List<FindListRolResponseVO>> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}

	/**
	 * Operation: Operation: create, Method: POST Crea un registro en la BB.DD.
	 * 
	 * @param headers Arreglo de objetos de tipo llave valor para almacenar los
	 *                headers
	 * @param body    Cuerpo de la peticion con la informacion del regsitro que se
	 *                desean insertar
	 * @return Ojeto con el cuerpo de respuesta (ong con el id del registro
	 *         actualizado) y le codigo de respuesta http corrspondiente a la
	 *         operacion
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
	@PostMapping(value = "/rolFuncion", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<CreateRolFuncionResponseVO>> createRolFuncion(
			@RequestHeader Map<String, String> headers, @RequestBody CreateRolFuncionRequestVO body) {

		// Generamos el objeto requestVO
		RequestVO<CreateRolFuncionRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, body);

		ResponseEntity<ResponseVO<CreateRolFuncionResponseVO>> response = null;
		try {
			// Invocar al servicio
			ResponseVO<CreateRolFuncionResponseVO> serviceResponse = rolService.createRolFuncion(requestVO);

			response = AplicacionServicesResponseBuilder.buildCreateRolFuncionResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion controlada
			ResponseVO<CreateRolFuncionResponseVO> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;

	}

	/**
	 * Operation: delete, Method: DELETE Elimina un registro en la BB.DD.
	 * 
	 * @param headers Arreglo de objetos de tipo llave valor para almacenar los
	 *                headers
	 * @param id      Identificador del registro a actualizar
	 * @return Objeto con el resultado del borrado
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
	@DeleteMapping(value = "/rolFuncion/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Boolean>> deleteRolFuncion(@RequestHeader Map<String, String> headers,
			@PathVariable("id") Long id) {
		// Declaracion de variables
		ResponseEntity<ResponseVO<Boolean>> response = null;

		// Generamos el objeto requestVO
		DeleteRolFuncionRequestVO deleteRolFuncionRequestVO = new DeleteRolFuncionRequestVO();
		deleteRolFuncionRequestVO.setId(id);
		RequestVO<DeleteRolFuncionRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers,
				deleteRolFuncionRequestVO);

		try {

			ResponseVO<Boolean> serviceResponse = rolService.deleteRolFuncion(requestVO);

			response = AplicacionServicesResponseBuilder.buildDeleteResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<Boolean> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}

	/**
	 * Operation: findList, Method: GET Consulta la lista de registros en base a
	 * varios parametros. El resultado se regresa como un arreglo de registros.
	 * 
	 * @return ResponseVO con la lista de registros encontrada
	 * 
	 * @param nombre Patron de nombre a buscar
	 * @param page   Numero de pagina
	 * @param size   Tamano de pagina
	 * @param token  Token de sesion
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
	@GetMapping(value = "/rolFuncion/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<List<FindListRolFuncionResponseVO>>> findListRolFuncion(
			@RequestHeader Map<String, String> headers, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderType", required = false) String orderType,
			@RequestParam(value = "idAplicacion", required = false) Long idAplicacion,
			@RequestParam(value = "idRol", required = false) Long idRol) {
		// Declarar variables
		ResponseEntity<ResponseVO<List<FindListRolFuncionResponseVO>>> response = null;

		FindListRolFuncionRequestVO findListRolFuncionRequestVO = new FindListRolFuncionRequestVO();
		findListRolFuncionRequestVO.setIdAplicacion(idAplicacion);
		findListRolFuncionRequestVO.setIdRol(idRol);

		RequestVO<FindListRolFuncionRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, page, size, orderBy,
				orderType, findListRolFuncionRequestVO);

		try {

			ResponseVO<List<FindListRolFuncionResponseVO>> serviceResponse = rolService.findListRolFuncion(requestVO);
			response = AplicacionServicesResponseBuilder.buildFindListRolFuncionResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<List<FindListRolFuncionResponseVO>> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}

	/**
	 * Operation: delete, Method: DELETE Elimina un registro en la BB.DD.
	 * 
	 * @param headers Arreglo de objetos de tipo llave valor para almacenar los
	 *                headers
	 * @param id      Identificador del registro a actualizar
	 * @return Objeto con el resultado del borrado
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
	@DeleteMapping(value = "/rolFuncionMenu/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Boolean>> deleteRolFuncionMenu(@RequestHeader Map<String, String> headers,
			@PathVariable("id") Long id) {
		// Declaracion de variables
		ResponseEntity<ResponseVO<Boolean>> response = null;

		// Generamos el objeto requestVO
		DeleteRolFuncionMenuRequestVO deleteRolFuncionMenuRequestVO = new DeleteRolFuncionMenuRequestVO();
		deleteRolFuncionMenuRequestVO.setId(id);
		RequestVO<DeleteRolFuncionMenuRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers,
				deleteRolFuncionMenuRequestVO);

		try {

			ResponseVO<Boolean> serviceResponse = rolService.deleteRolFuncionMenu(requestVO);

			response = AplicacionServicesResponseBuilder.buildDeleteResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<Boolean> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}

	/**
	 * Operation: findList, Method: GET Consulta la lista de registros en base a
	 * varios parametros. El resultado se regresa como un arreglo de registros.
	 * 
	 * @return ResponseVO con la lista de registros encontrada
	 * 
	 * @param nombre Patron de nombre a buscar
	 * @param page   Numero de pagina
	 * @param size   Tamano de pagina
	 * @param token  Token de sesion
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
	@GetMapping(value = "/rolFuncionMenu/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<List<FindListRolFuncionMenuResponseVO>>> findListRolFuncionMenu(
			@RequestHeader Map<String, String> headers, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderType", required = false) String orderType,
			@RequestParam(value = "idRolFuncion", required = false) Long idRolFuncion,
			@RequestParam(value = "idMenu", required = false) Long idMenu) {
		// Declarar variables
		ResponseEntity<ResponseVO<List<FindListRolFuncionMenuResponseVO>>> response = null;

		FindListRolFuncionMenuRequestVO findListRolFuncionMenuRequestVO = new FindListRolFuncionMenuRequestVO();
		findListRolFuncionMenuRequestVO.setIdRolFuncion(idRolFuncion);
		findListRolFuncionMenuRequestVO.setIdMenu(idMenu);

		RequestVO<FindListRolFuncionMenuRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, page, size,
				orderBy, orderType, findListRolFuncionMenuRequestVO);

		try {

			ResponseVO<List<FindListRolFuncionMenuResponseVO>> serviceResponse = rolService
					.findListRolFuncionMenu(requestVO);
			response = AplicacionServicesResponseBuilder.buildFindListRolFuncionMenuResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<List<FindListRolFuncionMenuResponseVO>> exceptionResponse = ResponseUtil
					.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}

	/**
	 * Operation: Operation: create, Method: POST Crea un registro en la BB.DD.
	 * 
	 * @param headers Arreglo de objetos de tipo llave valor para almacenar los
	 *                headers
	 * @param body    Cuerpo de la peticion con la informacion del regsitro que se
	 *                desean insertar
	 * @return Ojeto con el cuerpo de respuesta (ong con el id del registro
	 *         actualizado) y le codigo de respuesta http corrspondiente a la
	 *         operacion
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
	@PostMapping(value = "/rolFuncionMenu", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<List<Long>>> createRolFuncionMenu(@RequestHeader Map<String, String> headers,
			@RequestBody CreateRolFuncionMenuRequestVO body) {

		// Generamos el objeto requestVO
		RequestVO<CreateRolFuncionMenuRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, body);

		ResponseEntity<ResponseVO<List<Long>>> response = null;
		try {
			// Invocar al servicio
			ResponseVO<List<Long>> serviceResponse = rolService.createRolFuncionMenu(requestVO);

			response = RolFuncionMenuResponseBuilder.buildCreateRolFuncionMenuResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion controlada
			ResponseVO<List<Long>> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;

	}

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
	@PostMapping(value = "/rolFuncionSubmenu", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<List<Long>>> createRolFuncionSubmenu(@RequestHeader Map<String, String> headers,
			@RequestBody CreateRolFuncionSubmenuRequestVO body) {
		// Generamos el objeto requestVO
		RequestVO<CreateRolFuncionSubmenuRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, body);

		ResponseEntity<ResponseVO<List<Long>>> response = null;
		try {
			// Invocar al servicio
			ResponseVO<List<Long>> serviceResponse = rolService.createRolFuncionSubmenu(requestVO);

			response = RolFuncionSubmenuResponseBuilder.buildCreateRolFuncionMenuResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion controlada
			ResponseVO<List<Long>> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}

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
	@DeleteMapping(value = "/rolFuncionSubmenu/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Boolean>> deleteRolFuncionSubmenu(@RequestHeader Map<String, String> headers,
			@PathVariable("id") Long id) {
		// Declaracion de variables
		ResponseEntity<ResponseVO<Boolean>> response = null;

		// Generamos el objeto requestVO
		DeleteRolFuncionSubmenuRequestVO deleteRolFuncionSubmenuRequestVO = new DeleteRolFuncionSubmenuRequestVO();
		deleteRolFuncionSubmenuRequestVO.setId(id);
		RequestVO<DeleteRolFuncionSubmenuRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers,
				deleteRolFuncionSubmenuRequestVO);

		try {

			ResponseVO<Boolean> serviceResponse = rolService.deleteRolFuncionSubmenu(requestVO);

			response = RolFuncionSubmenuResponseBuilder.buildDeleteResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<Boolean> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}

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
	@GetMapping(value = "/rolFuncionSubmenu/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<List<FindListRolFuncionSubmenuResponseVO>>> findListRolFuncionSubmenu(
			@RequestHeader Map<String, String> headers, 
			@RequestParam(value = "page", required = false) Integer page, 
			@RequestParam(value = "size", required = false) Integer size, 
			@RequestParam(value = "orderBy", required = false) String orderBy, 
			@RequestParam(value = "orderType", required = false) String orderType, 
			@RequestParam(value = "idFuncion", required = false) Long idFuncion,
			@RequestParam(value = "idSubmenu", required = false) Long idSubmenu) {
		
		// Declarar variables
		ResponseEntity<ResponseVO<List<FindListRolFuncionSubmenuResponseVO>>> response = null;

		FindListRolFuncionSubmenuRequestVO findListRolFuncionSubmenuRequestVO = new FindListRolFuncionSubmenuRequestVO();
		findListRolFuncionSubmenuRequestVO.setIdFuncion(idFuncion);
		findListRolFuncionSubmenuRequestVO.setIdSubmenu(idSubmenu);

		RequestVO<FindListRolFuncionSubmenuRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, page, size,
				orderBy, orderType, findListRolFuncionSubmenuRequestVO);

		try {

			ResponseVO<List<FindListRolFuncionSubmenuResponseVO>> serviceResponse = rolService
					.findListRolFuncionSubmenu(requestVO);
			response = RolFuncionSubmenuResponseBuilder.buildFindListRolFuncionSubmenuResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<List<FindListRolFuncionSubmenuResponseVO>> exceptionResponse = ResponseUtil
					.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}

	
	/**
	 * Operation: findList, Method: GET Consulta la lista de registros en base a
	 * varios parametros. El resultado se regresa como un arreglo de registros.
	 * 
	 * @return ResponseVO con la lista de registros encontrada
	 * 
	 * @param nombre Patron de nombre a buscar
	 * @param page   Numero de pagina
	 * @param size   Tamano de pagina
	 * @param token  Token de sesion
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
	@GetMapping(value = "/rol/internal/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<List<FindListRolResponseVO>>> findInternalListRol(
			@RequestHeader Map<String, String> headers, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderType", required = false) String orderType,
			@RequestParam(value = "idAplicacion", required = false) Long idAplicacion,
			@RequestParam(value = "idNombre", required = false) String idNombre,
			@RequestParam(value = "idUsuario", required = false) Long idUsuario) {
		// Declarar variables
		ResponseEntity<ResponseVO<List<FindListRolResponseVO>>> response = null;

		FindListRolRequestVO findListRolRequestVO = new FindListRolRequestVO();
		findListRolRequestVO.setIdNombre(idNombre);
		findListRolRequestVO.setIdAplicacion(idAplicacion);
		findListRolRequestVO.setIdUsuario(idUsuario);

		RequestVO<FindListRolRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, page, size, orderBy,
				orderType, findListRolRequestVO);

		try {

			ResponseVO<List<FindListRolResponseVO>> serviceResponse = rolService.findInternalListRol(requestVO);
			response = AplicacionServicesResponseBuilder.buildFindListRolResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<List<FindListRolResponseVO>> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}


}
