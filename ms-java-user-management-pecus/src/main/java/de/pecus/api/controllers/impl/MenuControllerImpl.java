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

import de.pecus.api.controllers.MenuController;
import de.pecus.api.log.SmartLogger;
import de.pecus.api.log.SmartLoggerFactory;
import de.pecus.api.services.usuarios.MenuService;
import de.pecus.api.util.MenuServicesResponseBuilder;
import de.pecus.api.util.RequestVOUtil;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.menu.CreateMenuRequestVO;
import de.pecus.api.vo.menu.CreateSubMenuRequestVO;
import de.pecus.api.vo.menu.DeleteMenuRequestVO;
import de.pecus.api.vo.menu.DeleteSubMenuRequestVO;
import de.pecus.api.vo.menu.FindDetailMenuRequestVO;
import de.pecus.api.vo.menu.FindDetailMenuResponseVO;
import de.pecus.api.vo.menu.FindDetailSubMenuRequestVO;
import de.pecus.api.vo.menu.FindDetailSubMenuResponseVO;
import de.pecus.api.vo.menu.FindListMenuRequestVO;
import de.pecus.api.vo.menu.FindListMenuResponseVO;
import de.pecus.api.vo.menu.FindListSubMenuRequestVO;
import de.pecus.api.vo.menu.FindListSubMenuResponseVO;
import de.pecus.api.vo.menu.FindListUnassignedSubmenuRequestVO;
import de.pecus.api.vo.menu.UpdateMenuRequestVO;
import de.pecus.api.vo.menu.UpdateSubMenuRequestVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * Clase de contmenuador para servicio Rest de Menues
 * 
 * @author Jorge Serrano
 * @version 1.0
 * @created 21-junio-2019 11:34:31 a. m.
 */
@RestController
@RequestMapping("")
public class MenuControllerImpl implements MenuController {

	public static final SmartLogger LOGGER = SmartLoggerFactory.getLogger(MenuController.class);

	@Autowired
	private MenuService menuService;

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
	@PostMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Long>> createMenu(@RequestHeader Map<String, String> headers,
			@RequestBody CreateMenuRequestVO body) {

		// Generamos el objeto requestVO
		RequestVO<CreateMenuRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, body);

		ResponseEntity<ResponseVO<Long>> response = null;
		try {
			// Invocar al servicio
			ResponseVO<Long> serviceResponse = menuService.create(requestVO);

			response = MenuServicesResponseBuilder.buildCreateOrUpdateResponse(serviceResponse);

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
	@PutMapping(value = "/menu/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Long>> updateMenu(@RequestHeader Map<String, String> headers,
			@PathVariable(name = "id", required = false) Long id, @RequestBody UpdateMenuRequestVO body) {

		// Declaracion de variable
		ResponseEntity<ResponseVO<Long>> response = null;

		body.setId(id);

		// Generamos el objeto requestVO
		RequestVO<UpdateMenuRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, body);

		try {
			// Invocar al servicio
			ResponseVO<Long> serviceResponse = menuService.update(requestVO);

			response = MenuServicesResponseBuilder.buildCreateOrUpdateResponse(serviceResponse);

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
	@DeleteMapping(value = "/menu/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Boolean>> deleteMenu(@RequestHeader Map<String, String> headers,
			@PathVariable("id") Long id) {
		// Declaracion de variables
		ResponseEntity<ResponseVO<Boolean>> response = null;

		// Generamos el objeto requestVO
		DeleteMenuRequestVO deleteMenuRequestVO = new DeleteMenuRequestVO();
		deleteMenuRequestVO.setId(id);
		RequestVO<DeleteMenuRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, deleteMenuRequestVO);

		try {

			ResponseVO<Boolean> serviceResponse = menuService.delete(requestVO);

			response = MenuServicesResponseBuilder.buildDeleteResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<Boolean> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}

	/**
	 * Operation: findDetail, Method: Busca el detalle de un registro en la base de
	 * datos.
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
	@GetMapping(value = "/menu/detail", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<FindDetailMenuResponseVO>> findDetailMenu(
			@RequestHeader Map<String, String> headers, @RequestParam(value = "id", required = false) Long id) {

		// Declaracion de variables
		ResponseEntity<ResponseVO<FindDetailMenuResponseVO>> response = null;

		// Crear el objeto requestVO
		FindDetailMenuRequestVO findDetailMenuRequestVO = new FindDetailMenuRequestVO();
		findDetailMenuRequestVO.setId(id);
		RequestVO<FindDetailMenuRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, findDetailMenuRequestVO);

		try {

			// Invocar al metodo de busqueda
			ResponseVO<FindDetailMenuResponseVO> serviceResponse = menuService.findDetail(requestVO);

			response = MenuServicesResponseBuilder.buildFindDetailResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<FindDetailMenuResponseVO> exceptionResponse = ResponseUtil.getErrorResponse(exception);
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
	@GetMapping(value = "/menu/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<List<FindListMenuResponseVO>>> findListMenu(
			@RequestHeader Map<String, String> headers, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderType", required = false) String orderType,
			@RequestParam(value = "idNombre", required = false) String idNombre) {
		// Declarar variables
		ResponseEntity<ResponseVO<List<FindListMenuResponseVO>>> response = null;

		FindListMenuRequestVO findListMenuRequestVO = new FindListMenuRequestVO();
		findListMenuRequestVO.setIdNombre(idNombre);
		RequestVO<FindListMenuRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, page, size, orderBy,
				orderType, findListMenuRequestVO);

		try {

			ResponseVO<List<FindListMenuResponseVO>> serviceResponse = menuService.findList(requestVO);
			response = MenuServicesResponseBuilder.buildFindListMenuResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<List<FindListMenuResponseVO>> exceptionResponse = ResponseUtil.getErrorResponse(exception);
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
	@PostMapping(value = "/submenu", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Long>> createSubMenu(@RequestHeader Map<String, String> headers,
			@RequestBody CreateSubMenuRequestVO body) {

		// Generamos el objeto requestVO
		RequestVO<CreateSubMenuRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, body);

		ResponseEntity<ResponseVO<Long>> response = null;
		try {
			// Invocar al servicio
			ResponseVO<Long> serviceResponse = menuService.createSubMenu(requestVO);

			response = MenuServicesResponseBuilder.buildCreateOrUpdateResponse(serviceResponse);

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
	@PutMapping(value = "/submenu/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Long>> updateSubMenu(@RequestHeader Map<String, String> headers,
			@PathVariable(name = "id", required = false) Long id, @RequestBody UpdateSubMenuRequestVO body) {

		// Declaracion de variable
		ResponseEntity<ResponseVO<Long>> response = null;

		body.setId(id);

		// Generamos el objeto requestVO
		RequestVO<UpdateSubMenuRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, body);

		try {
			// Invocar al servicio
			ResponseVO<Long> serviceResponse = menuService.updateSubMenu(requestVO);

			response = MenuServicesResponseBuilder.buildCreateOrUpdateResponse(serviceResponse);

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
	@DeleteMapping(value = "/submenu/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Boolean>> deleteSubMenu(@RequestHeader Map<String, String> headers,
			@PathVariable("id") Long id) {
		// Declaracion de variables
		ResponseEntity<ResponseVO<Boolean>> response = null;

		// Generamos el objeto requestVO
		DeleteSubMenuRequestVO deleteSubMenuRequestVO = new DeleteSubMenuRequestVO();
		deleteSubMenuRequestVO.setId(id);
		RequestVO<DeleteSubMenuRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, deleteSubMenuRequestVO);

		try {

			ResponseVO<Boolean> serviceResponse = menuService.deleteSubMenu(requestVO);

			response = MenuServicesResponseBuilder.buildDeleteResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<Boolean> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}

	/**
	 * Operation: findDetail, Method: Busca el detalle de un registro en la base de
	 * datos.
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
	@GetMapping(value = "/submenu/detail", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<FindDetailSubMenuResponseVO>> findDetailSubMenu(
			@RequestHeader Map<String, String> headers, @RequestParam(value = "id", required = false) Long id) {

		// Declaracion de variables
		ResponseEntity<ResponseVO<FindDetailSubMenuResponseVO>> response = null;

		// Crear el objeto requestVO
		FindDetailSubMenuRequestVO findDetailSubMenuRequestVO = new FindDetailSubMenuRequestVO();
		findDetailSubMenuRequestVO.setId(id);
		RequestVO<FindDetailSubMenuRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers,
				findDetailSubMenuRequestVO);

		try {

			// Invocar al metodo de busqueda
			ResponseVO<FindDetailSubMenuResponseVO> serviceResponse = menuService.findDetailSubMenu(requestVO);

			response = MenuServicesResponseBuilder.buildFindDetailSubMenuResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<FindDetailSubMenuResponseVO> exceptionResponse = ResponseUtil.getErrorResponse(exception);
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
	@GetMapping(value = "/submenu/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<List<FindListSubMenuResponseVO>>> findListSubMenu(
			@RequestHeader Map<String, String> headers, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderType", required = false) String orderType,
			@RequestParam(value = "idMenu", required = false) Long idMenu,
			@RequestParam(value = "idNombre", required = false) String idNombre) {

		// Declarar variables
		ResponseEntity<ResponseVO<List<FindListSubMenuResponseVO>>> response = null;

		FindListSubMenuRequestVO findListSubMenuRequestVO = new FindListSubMenuRequestVO();
		findListSubMenuRequestVO.setIdMenu(idMenu);
		findListSubMenuRequestVO.setIdNombre(idNombre);
		RequestVO<FindListSubMenuRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, page, size, orderBy,
				orderType, findListSubMenuRequestVO);

		try {

			ResponseVO<List<FindListSubMenuResponseVO>> serviceResponse = menuService.findListSubMenu(requestVO);
			response = MenuServicesResponseBuilder.buildFindListSubMenuResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<List<FindListSubMenuResponseVO>> exceptionResponse = ResponseUtil.getErrorResponse(exception);
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
	@GetMapping(value = "/menu/unassigned/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<List<FindListMenuResponseVO>>> findListMenuUnassigned(
			@RequestHeader Map<String, String> headers, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderType", required = false) String orderType) {
		// Declarar variables
		ResponseEntity<ResponseVO<List<FindListMenuResponseVO>>> response = null;

		FindListMenuRequestVO findListMenuRequestVO = new FindListMenuRequestVO();
		RequestVO<FindListMenuRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, page, size, orderBy,
				orderType, findListMenuRequestVO);

		try {

			ResponseVO<List<FindListMenuResponseVO>> serviceResponse = menuService.findListMenuUnassigned(requestVO);
			response = MenuServicesResponseBuilder.buildFindListMenuResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<List<FindListMenuResponseVO>> exceptionResponse = ResponseUtil.getErrorResponse(exception);
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
	@GetMapping(value = "/submenu/unassigned/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<List<FindListSubMenuResponseVO>>> findListSubMenuUnassigned(
			@RequestHeader Map<String, String> headers, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "orderType", required = false) String orderType,
			@RequestParam(value = "idRolFuncion", required = false) Long idRolFuncion) {

		// Declarar variables
		ResponseEntity<ResponseVO<List<FindListSubMenuResponseVO>>> response = null;

		FindListUnassignedSubmenuRequestVO findListSubMenuRequestVO = new FindListUnassignedSubmenuRequestVO();
		findListSubMenuRequestVO.setIdRolFuncion(idRolFuncion);
		RequestVO<FindListUnassignedSubmenuRequestVO> requestVO = RequestVOUtil.setNewRequestVO(headers, page, size, orderBy,
				orderType, findListSubMenuRequestVO);

		try {

			ResponseVO<List<FindListSubMenuResponseVO>> serviceResponse = menuService.findListSubMenuUnassigned(requestVO);
			response = MenuServicesResponseBuilder.buildFindListSubMenuResponse(serviceResponse);

		} catch (Exception exception) {
			// Excepcion no controlada
			ResponseVO<List<FindListSubMenuResponseVO>> exceptionResponse = ResponseUtil.getErrorResponse(exception);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
		}
		return response;
	}

}
