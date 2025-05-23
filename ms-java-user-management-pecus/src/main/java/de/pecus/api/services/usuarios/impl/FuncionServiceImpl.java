package de.pecus.api.services.usuarios.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import de.pecus.api.annotation.Auditable;
import de.pecus.api.constant.DataConstants;
import de.pecus.api.entities.AplicacionDO;
import de.pecus.api.entities.FuncionDO;
import de.pecus.api.error.FuncionesBusinessError;
import de.pecus.api.error.GeneralBusinessErrors;
import de.pecus.api.repositories.funciones.FuncionRepository;
import de.pecus.api.services.usuarios.AplicacionService;
import de.pecus.api.services.usuarios.FuncionService;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.util.ServiceUtil;
import de.pecus.api.util.StringUtil;
import de.pecus.api.util.ValidatorArqUtil;
import de.pecus.api.util.ValidatorUtil;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.funciones.CreateFuncionRequestVO;
import de.pecus.api.vo.funciones.DeleteFuncionRequestVO;
import de.pecus.api.vo.funciones.FindDetailFuncionRequestVO;
import de.pecus.api.vo.funciones.FindDetailFuncionResponseVO;
import de.pecus.api.vo.funciones.FindListFuncionRequestVO;
import de.pecus.api.vo.funciones.FindListFuncionResponseVO;
import de.pecus.api.vo.funciones.UpdateFuncionRequestVO;

@Service
public class FuncionServiceImpl implements FuncionService {

	@Autowired
	private FuncionRepository funcionRepository;
	
	@Autowired
	private AplicacionService aplicacionService;
	
	/**
	 * Asocia una funcion a un aplicacion
	 * 
	 * @param request Objeto con parametros de entrada
	 * 
	 * @return Id generado
	 */
	@Auditable
	public ResponseVO<Long> createFuncion(RequestVO<CreateFuncionRequestVO> request) {

		// Declarar variables
		ResponseVO<Long> response = new ResponseVO<>();

		// Validar los parametros de entrada
		if (validateParametersCreateFuncion(request, response)) {

			// Preparar los datos para actualizar la BB.DD.
			AplicacionDO aplicacionDO = new AplicacionDO();
			FuncionDO funcionDO = new FuncionDO();

			aplicacionDO.setId(request.getParameters().getIdAplicacion());
			funcionDO.setAplicacion(aplicacionDO);
			funcionDO.setIdNombre(request.getParameters().getIdNombre());
			funcionDO.setDescripcion(request.getParameters().getDescripcion());
			funcionDO.setPath(request.getParameters().getPath());

			// Actualizar los parametros de auditoria
			ServiceUtil.setAuditFields(funcionDO, request.getToken());

			// Insertar el registro
			funcionDO = funcionRepository.saveAndFlush(funcionDO);

			// Regresar la respuesta correcta y el objeto a regresar
			response.setSuccess(true);
			response.setData(funcionDO.getId());

		}
		return response;
	}

	/**
	 * Actualiza un registro de una funcion
	 * 
	 * @param request Objeto con parametros de entrada
	 * 
	 * @return Id actualizado
	 */
	@Auditable
	public ResponseVO<Long> updateFuncion(RequestVO<UpdateFuncionRequestVO> request) {

		// Declarar variables
		ResponseVO<Long> response = new ResponseVO<>();
		FuncionDO registroDO = new FuncionDO();
		AplicacionDO aplicacionDO = new AplicacionDO();

		// Validar los campos de entrada
		if (validateParametersUpdateFuncion(request, response)) {

			UpdateFuncionRequestVO parameters = request.getParameters();

			aplicacionDO.setId(parameters.getIdAplicacion());
			registroDO.setAplicacion(aplicacionDO);
			registroDO.setId(parameters.getId());
			registroDO.setIdNombre(parameters.getIdNombre());
			registroDO.setDescripcion(parameters.getDescripcion());
			registroDO.setPath(parameters.getPath());

			// Actualizar parametros de auditoria
			ServiceUtil.setAuditFields(registroDO, request.getToken());

			// Actualizar el registro en BB.DD.
			registroDO = funcionRepository.saveAndFlush(registroDO);

			// Preparar respuesta y objeto actualizado
			response.setSuccess(true);
			response.setData(registroDO.getId());
		}
		return response;
	}

	/**
	 * Marca un registro como eliminado un registro de funcion
	 * 
	 * @param request Objeto con parametros de entrada
	 * 
	 * @return Id eliminado
	 */
	@Auditable
	public ResponseVO<Boolean> deleteFuncion(RequestVO<DeleteFuncionRequestVO> request) {

		// Declarar variables
		ResponseVO<Boolean> response = new ResponseVO<>();

		// Validar campos de entrada
		if (validateParametersDeleteFuncion(request, response)) {

			FuncionDO funcionDO = this.existsFuncion(null, request.getParameters().getId(), null);
			if (ValidatorUtil.isNull(funcionDO)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_ERROR, request);
			} else {
				// Actualizar la informacion
				ServiceUtil.setDisabledEntity(funcionDO, request.getToken());

				// Actualizar la BB.DD.
				funcionDO = funcionRepository.saveAndFlush(funcionDO);

				// Preparar respuesta y objeto eliminado
				response.setSuccess(true);
				response.setData(Boolean.TRUE);
			}
		}
		return response;
	}

	/**
	 * Consulta un aplicacion por Identificador unico
	 * 
	 * @return Objeto VO con los datos encontrados
	 * @param Id      Identificador del registro a buscar
	 * 
	 * @param request Objeto con los datos de busqueda
	 */
	@Auditable
	public ResponseVO<FindDetailFuncionResponseVO> findDetailFuncion(RequestVO<FindDetailFuncionRequestVO> request) {

		// declaracion de varables
		ResponseVO<FindDetailFuncionResponseVO> response = new ResponseVO<>();
		FindDetailFuncionResponseVO salida = new FindDetailFuncionResponseVO();

		// validar que se cumplen las condiciones para realizar la consulta
		if (validateParametersFindDetailFuncion(request, response)) {

			FuncionDO funcionDO = this.existsFuncion(null, request.getParameters().getId(), null);

			if (ValidatorUtil.isNull(funcionDO)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_ERROR, request);
			} else {
				salida.setId(funcionDO.getId());
				salida.setIdNombre(funcionDO.getIdNombre());
				salida.setDescripcion(funcionDO.getDescripcion());
				salida.setPath(funcionDO.getPath());
				salida.setIdAplicacion(funcionDO.getAplicacion().getId());

				response.setData(salida);
				// regresar la respuesta correcta con los registros obtenidos.
				response.setSuccess(true);
			}

		}

		return response;
	}

	/**
	 * Consulta la base de datos en base a varios parametros
	 * 
	 * @return ReponseVO con los datos encontrados
	 * 
	 * @param request Objeto con parametros de entrada de banner
	 */
	@Auditable
	public ResponseVO<List<FindListFuncionResponseVO>> findListFuncionAssing(RequestVO<FindListFuncionRequestVO> request) {

		// declaracion de varables
		ResponseVO<List<FindListFuncionResponseVO>> response = new ResponseVO<>();

		List<FindListFuncionResponseVO> lista = null;



		if (validateParametersFindByListFuncion(request, response) && validateFindListFuncionNoAsignados(request, response)) {

			FindListFuncionRequestVO parameters = request.getParameters();
			// Se obtiene el idioma

			// ejecucion de la busqueda por el parametro recibido
 			
 				List<FindListFuncionResponseVO> ListNA = transformListFuncionNotAssingDO(funcionRepository.findListNoAsignados(parameters.getIdAplicacion(), parameters.getIdNombre(), parameters.getIdRol()));	
 				List<FindListFuncionResponseVO> ListA = transformListFuncionDO(funcionRepository.findListAssing(parameters.getIdAplicacion(), parameters.getIdNombre(), parameters.getIdRol()));
 				(lista = new ArrayList<FindListFuncionResponseVO>(ListNA)).addAll(ListA);

			// Si no se encontro ningun registro que cumpla la condicion generar error.
			if (ValidatorUtil.isNullOrEmpty(lista)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_REGISTER_LIST_ERROR);
			} else {
				// Regresar la respuesta correcta con los registros obtenidos.
				response.setSuccess(true);
				response.setTotalRows((long) lista.size());
				response.setData(lista);
			}
		}
		return response;
	}
	
	/**
	 * Consulta la base de datos en base a varios parametros
	 * 
	 * @return ReponseVO con los datos encontrados
	 * 
	 * @param request Objeto con parametros de entrada de banner
	 */
	@Auditable
	public ResponseVO<List<FindListFuncionResponseVO>> findListFuncion(RequestVO<FindListFuncionRequestVO> request) {

		// declaracion de varables
		ResponseVO<List<FindListFuncionResponseVO>> response = new ResponseVO<>();

		Page<FuncionDO> lista = null;

		if (validateParametersFindByListFuncion(request, response) && validateFindListFuncionNoAsignados(request, response)) {

			FindListFuncionRequestVO parameters = request.getParameters();
			// Se obtiene el idioma

			// Preparamos el objeto para la paginacion
			String orderby = request.getOrderBy();
			String ordertype = request.getOrderType();
			String orderBy = ValidatorUtil.isNullOrEmpty(orderby) ? "id" : orderby;
			Direction orderType = ValidatorUtil.isNullOrEmpty(ordertype) || ordertype.equals("asc") ? Direction.ASC
					: Direction.DESC;
			Integer size = ValidatorUtil.isNullOrZero(request.getSize()) ? 10 : request.getSize();
			Integer page = ValidatorUtil.isNullOrZero(request.getPage()) ? 1 : request.getPage();
			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(orderType, orderBy));

			// ejecucion de la busqueda por el parametro recibido
 			
 				lista = funcionRepository.findList(parameters.getIdAplicacion(), parameters.getIdNombre(), parameters.getIdRol(), pageable);
	 		

			// Si no se encontro ningun registro que cumpla la condicion generar error.
			if (ValidatorUtil.isNullOrEmpty(lista.getContent())) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_REGISTER_LIST_ERROR);
			} else {
				// Regresar la respuesta correcta con los registros obtenidos.
				response.setSuccess(true);
				response.setTotalRows(lista.getTotalElements());
				response.setData(transformListFuncionDO(lista.getContent()));
			}
		}
		return response;
	}
	

	/*******************************************************************************************************
	 * 
	 * METODOS VALIDACION
	 * 
	 *******************************************************************************************************/

	/**
	 * Valida que los parametros para la operacion de insercion sean correctos
	 * 
	 * @param request  Objeto con los parametros de entrada
	 * @param response Respuesta donde se agregan los errores
	 * @return true si todos los parametros son correctos
	 */
	private boolean validateParametersCreateFuncion(RequestVO<CreateFuncionRequestVO> request,
			ResponseVO<Long> response) {

		// Obtener los parametros de entrada
		CreateFuncionRequestVO parameters = request.getParameters();

		if (ValidatorUtil.isNullOrZero(parameters.getIdAplicacion())) {
			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_APPLICATION_ID_ERROR, request);
		} else {

			// ************************************
			// validar que existe la aplicacion
			// ************************************
			AplicacionDO aplicacionDO = aplicacionService.exists(parameters.getIdAplicacion(), null);

			if (ValidatorUtil.isNull(aplicacionDO)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_NOT_FOUND_APPLICATION_ID_ERROR,
						request);
				return false;
			}

			// ************************************
			// Validaciones de campos obligatorios
			// ************************************
			if (StringUtil.isNullOrEmpty(parameters.getIdNombre())) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_ID_NOMBRE_ERROR, request);
			} else {
				// Validacion de tamano
				String idNombre = StringUtil.substring(parameters.getIdNombre(), DataConstants.MAX_SIZE_ID_NOMBRE);

				// Validacion de formato
				parameters.setIdNombre(StringUtil.toUpperCase(idNombre));

				FuncionDO registroB = this.existsFuncion(parameters.getIdAplicacion(), null, parameters.getIdNombre());

				if (!ValidatorUtil.isNull(registroB)) {
					ResponseUtil.addError(request, response, FuncionesBusinessError.DUPLICATED_ERROR, request);
				}
			}

			if (StringUtil.isNullOrEmpty(parameters.getDescripcion())) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_DESCRIPCION_ERROR, request);
			} else {

				// Validacion de formato
				parameters.setDescripcion(StringUtil.toUpperCase(parameters.getDescripcion()));

			}

		}
		// Regresar el resultado de la validacion
		return ValidatorUtil.isSuccessfulResponse(response);
	}

	/**
	 * Valida que los parametros para la operacion de actualizacion sean correctos
	 * 
	 * @param request  Objeto con los parametros de entrada
	 * @param response Respuesta donde se agregan los errores
	 * @return true si todos los parametros son correctos
	 */
	private boolean validateParametersUpdateFuncion(RequestVO<UpdateFuncionRequestVO> request,
			ResponseVO<Long> response) {
		// Recuperar parametros de entrada
		UpdateFuncionRequestVO parameters = request.getParameters();
		FuncionDO registroUpdate = new FuncionDO();

		// Validar que se informaron los campos de entrada
		if (ValidatorUtil.isNull(parameters)) {
			// Si no se ha informado regresar el error y no seguir validando
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
			return false;
		}

		// Validar que exista el registro a actualizar
		if (ValidatorUtil.isNull(parameters.getId())) {
			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_ID_ERROR, request);
			return false;
		} else {

			registroUpdate = this.existsFuncion(null, parameters.getId(), null);

			if (ValidatorUtil.isNull(registroUpdate)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_ERROR, request);
				return false;
			} else {
				parameters.setIdAplicacion(registroUpdate.getAplicacion().getId());
			}
		}

		// Validaciones de campos obligatorios: NOMBRE
		if (!StringUtil.isNullOrEmpty(parameters.getIdNombre())) {
			// Validacion de tamano
			String idNombre = StringUtil.substring(parameters.getIdNombre(), DataConstants.MAX_SIZE_ID_NOMBRE);

			// Validacion de formato
			parameters.setIdNombre(StringUtil.toUpperCase(idNombre));

			// Validar la posible duplicidad del idNombre
			FuncionDO funcionBusqueda = this.existsFuncion(registroUpdate.getAplicacion().getId(), null,
					request.getParameters().getIdNombre());

			if (!ValidatorUtil.isNull(funcionBusqueda)) {
				// Si se encuentra el registro validamos que no sea el mismo Id
				if (registroUpdate.getId() != funcionBusqueda.getId()) {
					ResponseUtil.addError(request, response, FuncionesBusinessError.DUPLICATED_ERROR, request);

				}
			}
		} else {
			parameters.setIdNombre(registroUpdate.getIdNombre());
		}

		// Validaciones de campos obligatorios: DESCRIPCION
		if (!StringUtil.isNullOrEmpty(parameters.getDescripcion())) {
			// Validacion de tamano
			parameters.setDescripcion(
					StringUtil.substring(parameters.getDescripcion(), DataConstants.MAX_SIZE_DESCRIPCION));

		} else {
			parameters.setDescripcion(registroUpdate.getDescripcion());
		}

		// Validaciones de campos obligatorios: PATH
		if (!StringUtil.isNullOrEmpty(parameters.getPath())) {
			// Validacion de tamano
			parameters.setPath(StringUtil.substring(parameters.getPath(), DataConstants.MAX_SIZE_DESCRIPCION));

		} else {
			parameters.setPath(registroUpdate.getPath());
		}

		// Retorna el resultado de la validacion.
		return ValidatorUtil.isSuccessfulResponse(response);

	}

	/**
	 * Valida que los parametros para la operacion de eliminacion sean correctos
	 * 
	 * @param request  Objeto con los parametros de entrada
	 * @param response Respuesta donde se agregan los errores
	 * @return true si todos los parametros son correctos
	 */
	private boolean validateParametersDeleteFuncion(RequestVO<DeleteFuncionRequestVO> request,
			ResponseVO<Boolean> response) {

		// Validar que se han informado los parametros de entrada
		if (ValidatorUtil.isNull(request.getParameters())) {
			// Si no se han informado generar error y terminar de validar
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
			return false;
		}

		// Validaciones de campos obligatorios
		if (ValidatorUtil.isNullOrZero(request.getParameters().getId())) {
			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_ID_ERROR);
		}

		return ValidatorUtil.isSuccessfulResponse(response);
	}

	/*************************************************************************
	 * Metodo que busca un registro por su id, idNombre Regresa el objeto de la base
	 * de datos o una excepcion con el error
	 * 
	 *************************************************************************/
	public FuncionDO existsFuncion(Long idAplicacion, Long id, String idNombre) {

		FuncionDO registro = null;
		try {
			// Validacion de datos de entrada
			if (ValidatorUtil.isNullOrZero(id)) {
				if (ValidatorUtil.isNullOrEmpty(idNombre)) {
					registro = null;
				} else {
					// Buscamos por nombre
					registro = funcionRepository.findByIdNombre(idAplicacion, idNombre);
				}
			} else {
				// Consulta
				registro = funcionRepository.findById(id);
			}
			// Validacion de existencia
			if (ValidatorUtil.isNull(registro)) {
				// Genera error
				registro = null;
			}
		} catch (Exception e) {
			registro = null;
		}

		return registro;
	}

	/**
	 * Valida que los parametros para la operacion de consulta por nombre sean
	 * correctos
	 * 
	 * @param Id Identificador del registro a buscar
	 * @return true si el nombre no esta vacio
	 * 
	 * @param request  Objeto con los parametros a valida
	 * @param response Respuesta donde se agregan los errores
	 */
	private boolean validateParametersFindDetailFuncion(RequestVO<FindDetailFuncionRequestVO> request,
			ResponseVO<FindDetailFuncionResponseVO> response) {

		// Recuperar los parametros de entrada
		FindDetailFuncionRequestVO parameters = request.getParameters();

		// validar que el campo obligatorio
		if (ValidatorUtil.isNullOrZero(parameters.getId())) {

			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_ID_ERROR);
		}

		return ValidatorUtil.isSuccessfulResponse(response);
	}

	/**
	 * Valida que los parametros para la operacion de consulta por parametros sean
	 * correctos
	 * 
	 * @return true si el nombre no esta vacio
	 * 
	 * @param request  Objeto con los criterios a buscar
	 * @param response Respuesta donde se agregan los errores
	 */
	private boolean validateParametersFindByListFuncion(RequestVO<FindListFuncionRequestVO> request,
			ResponseVO<List<FindListFuncionResponseVO>> response) {

		if (ValidatorUtil.isNullOrZero(request.getParameters().getIdAplicacion())) {

			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_APPLICATION_ID_ERROR);

		} else if (ValidatorUtil.isNullOrZero(request.getParameters().getIdRol())) {

			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_ROL_ID_ERROR);

		}else{

			// Validar campos obligatorios
			ValidatorArqUtil.validateParameters(request, response);

			// validar los parametros de la paginacion
			ValidatorArqUtil.validatePaginatonParameters(request, response);
		}
		return ValidatorUtil.isSuccessfulResponse(response);

	}
	
	/**
 	 * Valida si la peticion contiene idRol, siempre y cuando este activa la bandera de noAsignados
 	 * 
 	 * @param request Objeto con los criterios a evaluar
 	 * @param response Respuesta donde se agregan los errores
 	 * 
 	 * @return true si noAsignados es true y idRol no esta vacio
 	 */
 	private boolean validateFindListFuncionNoAsignados(RequestVO<FindListFuncionRequestVO> request,
 			ResponseVO<List<FindListFuncionResponseVO>> response) {
 		
 		if(!ValidatorUtil.isNull(request.getParameters().getAsignados())) {
 			if(ValidatorUtil.isNullOrZero(request.getParameters().getIdRol())) {
 				ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_ROL_ID);
 			}
 		}
 		
 		return ValidatorUtil.isSuccessfulResponse(response);
	 	}

 	/**
	 * Obtiene una lista de objetos aplicacionVO a partir de una lista de DO
	 * 
	 * @return Lista VO para retorno de resultados
	 * 
	 * @param listaAplicacion a transformar
	 */
	private List<FindListFuncionResponseVO> transformListFuncionDO(List<FuncionDO> lista) {

		// Declarar variables
		List<FindListFuncionResponseVO> listaVO = new ArrayList<>();

		// recorrer el objeto origen
		for (FuncionDO funcionDO : lista) {
			// Se hace la declaracion de variables necesarias
			FindListFuncionResponseVO funcionVO = new FindListFuncionResponseVO();

			funcionVO.setId(funcionDO.getId());
			funcionVO.setIdAplicacion(funcionDO.getAplicacion().getId());
			funcionVO.setIdNombre(funcionDO.getIdNombre());
			funcionVO.setDescripcion(funcionDO.getDescripcion());
			funcionVO.setPath(funcionDO.getPath());
			funcionVO.setChecked(true);

			listaVO.add(funcionVO);
		}

		return listaVO;
	}/**
	 * Obtiene una lista de objetos aplicacionVO a partir de una lista de DO
	 * 
	 * @return Lista VO para retorno de resultados
	 * 
	 * @param listaAplicacion a transformar
	 */
	private List<FindListFuncionResponseVO> transformListFuncionNotAssingDO(List<FuncionDO> lista) {

		// Declarar variables
		List<FindListFuncionResponseVO> listaVO = new ArrayList<>();

		// recorrer el objeto origen
		for (FuncionDO funcionDO : lista) {
			// Se hace la declaracion de variables necesarias
			FindListFuncionResponseVO funcionVO = new FindListFuncionResponseVO();

			funcionVO.setId(funcionDO.getId());
			funcionVO.setIdAplicacion(funcionDO.getAplicacion().getId());
			funcionVO.setIdNombre(funcionDO.getIdNombre());
			funcionVO.setDescripcion(funcionDO.getDescripcion());
			funcionVO.setPath(funcionDO.getPath());
			funcionVO.setChecked(false);

			listaVO.add(funcionVO);
		}

		return listaVO;
	}

	/*************************************************************************
	 * Metodo que busca un registro por su id, idNombre Regresa el objeto de la base
	 * de datos o una excepcion con el error
	 * 
	 *************************************************************************/
	public FuncionDO exists(Long id, String idNombre) {

		FuncionDO registro = null;
		try {
			// Validacion de datos de entrada
			if (ValidatorUtil.isNullOrZero(id)) {
				if (ValidatorUtil.isNullOrEmpty(idNombre)) {
					registro = null;
				} else {
					// Buscamos por nombre
					registro = funcionRepository.findByIdNombre(id, idNombre);
				}
			} else {
				// Consulta
				registro = funcionRepository.findById(id);
			}
			// Validacion de existencia
			if (ValidatorUtil.isNull(registro)) {
				// Genera error
				registro = null;
			}
		} catch (Exception e) {
			registro = null;
		}

		return registro;
	}
	
	

}
