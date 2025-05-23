package de.pecus.api.services.usuarios.impl;

import java.text.Normalizer;
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
import de.pecus.api.entities.MenuDO;
import de.pecus.api.entities.SubmenuDO;
import de.pecus.api.enums.WildcardTypeEnum;
import de.pecus.api.error.FuncionesBusinessError;
import de.pecus.api.error.GeneralBusinessErrors;
import de.pecus.api.repositories.funciones.MenuRepository;
import de.pecus.api.repositories.funciones.SubMenuRepository;
import de.pecus.api.services.usuarios.MenuService;
import de.pecus.api.util.CriteriaUtil;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.util.ServiceUtil;
import de.pecus.api.util.StringUtil;
import de.pecus.api.util.ValidatorArqUtil;
import de.pecus.api.util.ValidatorUtil;
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

/**
 * Clase de logica de negocio para administracion de menu
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private SubMenuRepository subMenuRepository;

	/**
	 * Crea un nuevo registro de menu
	 * 
	 * @param request Objeto con parametros de entrada de menu
	 * 
	 * @return Id generado
	 */
	@Auditable
	public ResponseVO<Long> create(RequestVO<CreateMenuRequestVO> request) {

		// Declarar variables
		ResponseVO<Long> response = new ResponseVO<>();
		
			// Validar los parametros de entrada
			if (validateParametersCreate(request, response)) {
		
				// Preparar los datos para actualizar la BB.DD.
				MenuDO menuDO = new MenuDO();
				
				menuDO.setIdNombre(request.getParameters().getIdNombre());
				menuDO.setDescripcion(request.getParameters().getDescripcion());
				menuDO.setPath(request.getParameters().getPath());
				menuDO.setImagePath(request.getParameters().getImagePath());
				menuDO.setOrden(request.getParameters().getOrden());
				menuDO.setPasoConfig(request.getParameters().getPasoConfig());
				
				// Actualizar los parametros de auditoria
				ServiceUtil.setAuditFields(menuDO, request.getToken());

				// Insertar el registro
				menuDO = menuRepository.saveAndFlush(menuDO);

				// Regresar la respuesta correcta y el objeto a regresar
				response.setSuccess(true);
				response.setData(menuDO.getId());
				
			}
		return response;
	}

	/**
	 * Crea un nuevo registro de menu
	 * 
	 * @param request Objeto con parametros de entrada de menu
	 * 
	 * @return Id generado
	 */
	@Auditable
	public ResponseVO<Long> createSubMenu(RequestVO<CreateSubMenuRequestVO> request) {

		// Declarar variables
		ResponseVO<Long> response = new ResponseVO<>();
		
			// Validar los parametros de entrada
			if (validateParametersCreateSubMenu(request, response)) {
		
				// Preparar los datos para actualizar la BB.DD.
				MenuDO menuDO = new MenuDO();
				SubmenuDO subMenuDO = new SubmenuDO();
				
				menuDO.setId(request.getParameters().getIdMenu());
				
				subMenuDO.setMenu(menuDO);
				
				subMenuDO.setIdNombre(request.getParameters().getIdNombre());
				subMenuDO.setDescripcion(request.getParameters().getDescripcion());
				subMenuDO.setPath(request.getParameters().getPath());
				subMenuDO.setImagePath(request.getParameters().getImagePath());
				
				// Actualizar los parametros de auditoria
				ServiceUtil.setAuditFields(subMenuDO, request.getToken());

				// Insertar el registro
				subMenuDO = subMenuRepository.saveAndFlush(subMenuDO);

				// Regresar la respuesta correcta y el objeto a regresar
				response.setSuccess(true);
				response.setData(menuDO.getId());
				
			}
		return response;
	}

	/**
	 * Actualiza un registro de menu
	 * 
	 * @param request Objeto con parametros de entrada de menu
	 * 
	 * @return Id actualizado
	 */
	@Auditable
	public ResponseVO<Long> update(RequestVO<UpdateMenuRequestVO> request) {

		// Declarar variables
		ResponseVO<Long> response = new ResponseVO<>();
		MenuDO registroDO = new MenuDO();

		// Validar los campos de entrada
		if (validateParametersUpdate(request, response)) {
			
			UpdateMenuRequestVO parameters = request.getParameters();

			registroDO.setId(parameters.getId());
			registroDO.setIdNombre(request.getParameters().getIdNombre());
			registroDO.setDescripcion(request.getParameters().getDescripcion());
			registroDO.setPath(request.getParameters().getPath());
			registroDO.setImagePath(request.getParameters().getImagePath());
			registroDO.setOrden(request.getParameters().getOrden());
			registroDO.setPasoConfig(request.getParameters().getPasoConfig());
			
			// Actualizar parametros de auditoria
			ServiceUtil.setAuditFields(registroDO, request.getToken());

			// Actualizar el registro en BB.DD.
			registroDO = menuRepository.saveAndFlush(registroDO);

			// Preparar respuesta y objeto actualizado
			response.setSuccess(true);
			response.setData(registroDO.getId());
		}
		return response;
	}

	/**
	 * Actualiza un registro de menu
	 * 
	 * @param request Objeto con parametros de entrada de menu
	 * 
	 * @return Id actualizado
	 */
	@Auditable
	public ResponseVO<Long> updateSubMenu(RequestVO<UpdateSubMenuRequestVO> request) {

		// Declarar variables
		ResponseVO<Long> response = new ResponseVO<>();
		SubmenuDO registroDO = new SubmenuDO();
		MenuDO menuDO = new MenuDO();

		// Validar los campos de entrada
		if (validateParametersUpdateSubMenu(request, response)) {
			
			UpdateSubMenuRequestVO parameters = request.getParameters();

			menuDO.setId(request.getParameters().getIdMenu());
			registroDO.setMenu(menuDO);
			registroDO.setId(parameters.getId());
			registroDO.setIdNombre(request.getParameters().getIdNombre());
			registroDO.setDescripcion(request.getParameters().getDescripcion());
			registroDO.setPath(request.getParameters().getPath());
			registroDO.setImagePath(request.getParameters().getImagePath());
			
			// Actualizar parametros de auditoria
			ServiceUtil.setAuditFields(registroDO, request.getToken());

			// Actualizar el registro en BB.DD.
			registroDO = subMenuRepository.saveAndFlush(registroDO);

			// Preparar respuesta y objeto actualizado
			response.setSuccess(true);
			response.setData(registroDO.getId());
		}
		return response;
	}

	
	/**
	 * Marca un registro como eliminado un registro de menu
	 * 
	 * @param request Objeto con parametros de entrada de menu
	 * 
	 * @return Id eliminado
	 */
	@Auditable
	public ResponseVO<Boolean> delete(RequestVO<DeleteMenuRequestVO> request) {

		// Declarar variables
		ResponseVO<Boolean> response = new ResponseVO<>();

		// Validar campos de entrada
		if (validateParametersDelete(request, response)) {

			MenuDO menuDO = this.exists(request.getParameters().getId(), null);
			
			if (ValidatorUtil.isNull(menuDO)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_ERROR, request);
			}
			else {
				// Actualizar la informacion
				ServiceUtil.setDisabledEntity(menuDO, request.getToken());
				
				// Actualizar la BB.DD.
				menuDO = menuRepository.saveAndFlush(menuDO);
	
				// Preparar respuesta y objeto eliminado
				response.setSuccess(true);
				response.setData(Boolean.TRUE);
			}
		}
		return response;
	}

	/**
	 * Marca un registro como eliminado un registro de menu
	 * 
	 * @param request Objeto con parametros de entrada de menu
	 * 
	 * @return Id eliminado
	 */
	@Auditable
	public ResponseVO<Boolean> deleteSubMenu(RequestVO<DeleteSubMenuRequestVO> request) {

		// Declarar variables
		ResponseVO<Boolean> response = new ResponseVO<>();

		// Validar campos de entrada
		if (validateParametersDeleteSubMenu(request, response)) {

			SubmenuDO subMenuDO = this.existsSubMenu(null, request.getParameters().getId(), null);
			
			if (ValidatorUtil.isNull(subMenuDO)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_ERROR, request);
			}
			else {
				// Actualizar la informacion
				ServiceUtil.setDisabledEntity(subMenuDO, request.getToken());
				
				// Actualizar la BB.DD.
				subMenuDO = subMenuRepository.saveAndFlush(subMenuDO);
	
				// Preparar respuesta y objeto eliminado
				response.setSuccess(true);
				response.setData(Boolean.TRUE);
			}
		}
		return response;
	}


	/**
	 * Consulta un menu por Identificador unico
	 * 
	 * @return Objeto VO con los datos encontrados
	 * @param Id      Identificador del registro a buscar
	 * 
	 * @param request Objeto con los datos de busqueda
	 */
	@Auditable
	public ResponseVO<FindDetailMenuResponseVO> findDetail(RequestVO<FindDetailMenuRequestVO> request) {

		// declaracion de varables
		ResponseVO<FindDetailMenuResponseVO> response = new ResponseVO<>();
		FindDetailMenuResponseVO salida = new FindDetailMenuResponseVO();
		
		// validar que se cumplen las condiciones para realizar la consulta
		if (validateParametersFindDetail(request, response)) {

			MenuDO menuDO = this.exists(request.getParameters().getId(), request.getParameters().getIdNombre());

			if (ValidatorUtil.isNull(menuDO)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_ERROR, request);
			} else {
				salida.setId(menuDO.getId());
				salida.setIdNombre(menuDO.getIdNombre());
				salida.setDescripcion(menuDO.getDescripcion());
				salida.setOrden(menuDO.getOrden());
				salida.setPath(menuDO.getPath());
				salida.setImagePath(menuDO.getImagePath());
				salida.setPasoConfig(menuDO.getPasoConfig());

				response.setData(salida);
				// regresar la respuesta correcta con los registros obtenidos.
				response.setSuccess(true);
			}
				
		}
			
		return response;
	}

	/**
	 * Consulta un menu por Identificador unico
	 * 
	 * @return Objeto VO con los datos encontrados
	 * @param Id      Identificador del registro a buscar
	 * 
	 * @param request Objeto con los datos de busqueda
	 */
	@Auditable
	public ResponseVO<FindDetailSubMenuResponseVO> findDetailSubMenu(RequestVO<FindDetailSubMenuRequestVO> request) {

		// declaracion de varables
		ResponseVO<FindDetailSubMenuResponseVO> response = new ResponseVO<>();
		FindDetailSubMenuResponseVO salida = new FindDetailSubMenuResponseVO();
		
		// validar que se cumplen las condiciones para realizar la consulta
		if (validateParametersFindDetailSubMenu(request, response)) {

			SubmenuDO subMenuDO = this.existsSubMenu(null, request.getParameters().getId(), null);

			if (ValidatorUtil.isNull(subMenuDO)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_ERROR, request);
			} else {
				salida.setId(subMenuDO.getId());
				salida.setIdNombre(subMenuDO.getIdNombre());
				salida.setDescripcion(subMenuDO.getDescripcion());
				salida.setPath(subMenuDO.getPath());
				salida.setImagePath(subMenuDO.getImagePath());

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
	public ResponseVO<List<FindListMenuResponseVO>> findList(RequestVO<FindListMenuRequestVO> request) {

		// declaracion de varables
		ResponseVO<List<FindListMenuResponseVO>> response = new ResponseVO<>();
		
		Page<MenuDO> listaMenu = null;
		
		if (validateParametersFindByList(request, response)) {
	
			FindListMenuRequestVO parameters = request.getParameters();
			// Se obtiene el idioma
			
			CriteriaUtil.validateNullLike(parameters.getIdNombre(), WildcardTypeEnum.BOTH_SIDES);
			
			// Preparamos el objeto para la paginacion
			String orderby = request.getOrderBy();
			String ordertype = request.getOrderType();
			String orderBy = ValidatorUtil.isNullOrEmpty(orderby) ? "id" : orderby;
			Direction orderType = ValidatorUtil.isNullOrEmpty(ordertype) || ordertype.equals("asc") ? Direction.ASC
					: Direction.DESC;
			Integer size = ValidatorUtil.isNullOrZero(request.getSize()) ? 10 : request.getSize();
			Integer page = ValidatorUtil.isNullOrZero(request.getPage()) ? 1 : request.getPage();
			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(orderType, orderBy));
			
			String normalizedIdNombre = this.limpiarAcentos(request.getParameters().getIdNombre());
			
			// ejecucion de la busqueda por el parametro recibido
			listaMenu = menuRepository.findList(this.cleanString(normalizedIdNombre), pageable);

			// Si no se encontro ningun registro que cumpla la condicion generar error.
			if (ValidatorUtil.isNullOrEmpty(listaMenu.getContent())) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_REGISTER_LIST_ERROR);
			} else {
				// Regresar la respuesta correcta con los registros obtenidos.
				response.setSuccess(true);
				response.setTotalRows(listaMenu.getTotalElements());
				response.setData(transformListDO(listaMenu.getContent()));
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
	public ResponseVO<List<FindListMenuResponseVO>> findListMenuUnassigned(RequestVO<FindListMenuRequestVO> request) {

		// declaracion de varables
		ResponseVO<List<FindListMenuResponseVO>> response = new ResponseVO<>();
		
		Page<MenuDO> listaMenu = null;
		
		if (validateParametersFindByList(request, response)) {
	
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
			listaMenu = menuRepository.findListUnassigned(pageable);

			// Si no se encontro ningun registro que cumpla la condicion generar error.
			if (ValidatorUtil.isNullOrEmpty(listaMenu.getContent())) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_REGISTER_LIST_ERROR);
			} else {
				// Regresar la respuesta correcta con los registros obtenidos.
				response.setSuccess(true);
				response.setTotalRows(listaMenu.getTotalElements());
				response.setData(transformListDO(listaMenu.getContent()));
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
	public ResponseVO<List<FindListSubMenuResponseVO>> findListSubMenu(RequestVO<FindListSubMenuRequestVO> request) {

		// declaracion de varables
		ResponseVO<List<FindListSubMenuResponseVO>> response = new ResponseVO<>();
		
		Page<SubmenuDO> lista = null;
		
		if (validateParametersFindByListSubMenu(request, response)) {
	
			FindListSubMenuRequestVO parameters = request.getParameters();
			// Se obtiene el idioma
			
			CriteriaUtil.validateNullLike(parameters.getIdNombre(), WildcardTypeEnum.BOTH_SIDES);
			
			// Preparamos el objeto para la paginacion
			String orderby = request.getOrderBy();
			String ordertype = request.getOrderType();
			String orderBy = ValidatorUtil.isNullOrEmpty(orderby) ? "id" : orderby;
			Direction orderType = ValidatorUtil.isNullOrEmpty(ordertype) || ordertype.equals("asc") ? Direction.ASC
					: Direction.DESC;
			Integer size = ValidatorUtil.isNullOrZero(request.getSize()) ? 10 : request.getSize();
			Integer page = ValidatorUtil.isNullOrZero(request.getPage()) ? 1 : request.getPage();
			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(orderType, orderBy));
			
			String normalizedIdNombre = this.limpiarAcentos(request.getParameters().getIdNombre());
			
			// ejecucion de la busqueda por el parametro recibido
			lista = subMenuRepository.findList(request.getParameters().getIdMenu(),this.cleanString(normalizedIdNombre), pageable);

			// Si no se encontro ningun registro que cumpla la condicion generar error.
			if (ValidatorUtil.isNullOrEmpty(lista.getContent())) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_REGISTER_LIST_ERROR);
			} else {
				// Regresar la respuesta correcta con los registros obtenidos.
				response.setSuccess(true);
				response.setTotalRows(lista.getTotalElements());
				response.setData(transformListSubMenuDO(lista.getContent()));
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
	@Override
	@Auditable
	public ResponseVO<List<FindListSubMenuResponseVO>> findListSubMenuUnassigned(RequestVO<FindListUnassignedSubmenuRequestVO> request) {

		// declaracion de varables
		ResponseVO<List<FindListSubMenuResponseVO>> response = new ResponseVO<>();
		
		Page<SubmenuDO> lista = null;
		
		if (validateParametersFindByListUnassignedSubMenu(request, response)) {
	
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
			lista = subMenuRepository.findListUnassigned(request.getParameters().getIdRolFuncion(), pageable);

			// Si no se encontro ningun registro que cumpla la condicion generar error.
			if (ValidatorUtil.isNullOrEmpty(lista.getContent())) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_REGISTER_LIST_ERROR);
			} else {
				// Regresar la respuesta correcta con los registros obtenidos.
				response.setSuccess(true);
				response.setTotalRows(lista.getTotalElements());
				response.setData(transformListSubMenuDO(lista.getContent()));
			}
		}
		return response;
	}

	/*******************************************************************************************************
	 * 
	 * FIN METODOS PUBLICOS
	 * 
	 *******************************************************************************************************/

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
	private boolean validateParametersCreate(RequestVO<CreateMenuRequestVO> request, ResponseVO<Long> response) {
		
		// Obtener los parametros de entrada
		CreateMenuRequestVO parameters = request.getParameters();

		// Validaciones de campos obligatorios
		if (StringUtil.isNullOrEmpty(parameters.getIdNombre())) {
			ResponseUtil.addError(request, response, 
					FuncionesBusinessError.REQUIRED_ID_NOMBRE_ERROR, request);
		} else {
			// Validacion de tamano
			String idNombre = StringUtil.substring(parameters.getIdNombre(), DataConstants.MAX_SIZE_ID_NOMBRE);

			// Validacion de formato
			parameters.setIdNombre(StringUtil.toUpperCase(idNombre));

				MenuDO registroB = this.exists(null,parameters.getIdNombre());
				
				if (!ValidatorUtil.isNull(registroB)) {
					ResponseUtil.addError(request, response, 
							FuncionesBusinessError.DUPLICATED_ERROR, request);					
			}
		}
		
		// Validaciones de campos obligatorios
		if (StringUtil.isNullOrEmpty(parameters.getDescripcion())) {
			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_DESCRIPCION_ERROR,request);
		} else {
			// Validacion de tamano
			parameters.setDescripcion(StringUtil.substring(parameters.getDescripcion(), DataConstants.MAX_SIZE_DESCRIPCION));
		}
		
		if (ValidatorUtil.isNullOrZero(parameters.getOrden())) {
			parameters.setOrden(0L);
		}
		
		if (ValidatorUtil.isNullOrZero(parameters.getPasoConfig())) {
			parameters.setPasoConfig(0D);
		}
				
		// Regresar el resultado de la validacion
		return ValidatorUtil.isSuccessfulResponse(response);
	}

	/**
	 * Valida que los parametros para la operacion de insercion sean correctos
	 * 
	 * @param request  Objeto con los parametros de entrada
	 * @param response Respuesta donde se agregan los errores
	 * @return true si todos los parametros son correctos
	 */
	private boolean validateParametersCreateSubMenu(RequestVO<CreateSubMenuRequestVO> request, ResponseVO<Long> response) {
		
		// Obtener los parametros de entrada
		CreateSubMenuRequestVO parameters = request.getParameters();

		
		//Validar que el menu existe
		if (!ValidatorUtil.isNullOrZero(parameters.getIdMenu())) {
			
			MenuDO menuDO = new MenuDO();
			
			menuDO = this.exists(parameters.getIdMenu(), null);
			
			if (ValidatorUtil.isNull(menuDO)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_MENU_ERROR, request);			
			}
			
		} else {
			ResponseUtil.addError(request, response, 
					FuncionesBusinessError.REQUIRED_ID_MENU_ERROR, request);			
		}
		
		// Validaciones de campos obligatorios
		if (StringUtil.isNullOrEmpty(parameters.getIdNombre())) {
			ResponseUtil.addError(request, response, 
					FuncionesBusinessError.REQUIRED_ID_NOMBRE_ERROR, request);
		} else {
			// Validacion de tamano
			String idNombre = StringUtil.substring(parameters.getIdNombre(), DataConstants.MAX_SIZE_ID_NOMBRE);

			// Validacion de formato
			parameters.setIdNombre(StringUtil.toUpperCase(idNombre));

				SubmenuDO registroB = this.existsSubMenu(parameters.getIdMenu(), null,parameters.getIdNombre());
				
				if (!ValidatorUtil.isNull(registroB)) {
					ResponseUtil.addError(request, response, 
							FuncionesBusinessError.DUPLICATED_ERROR, request);					
			}
		}
		
		// Validaciones de campos obligatorios
		if (StringUtil.isNullOrEmpty(parameters.getDescripcion())) {
			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_DESCRIPCION_ERROR,request);
		} else {
			// Validacion de tamano
			parameters.setDescripcion(StringUtil.substring(parameters.getDescripcion(), DataConstants.MAX_SIZE_DESCRIPCION));
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
	private boolean validateParametersUpdate(RequestVO<UpdateMenuRequestVO> request, ResponseVO<Long> response) {
		// Recuperar parametros de entrada
		UpdateMenuRequestVO parameters = request.getParameters();
		MenuDO registroUpdate = new MenuDO();
		
		// Validar que se informaron los campos de entrada
		if (ValidatorUtil.isNull(parameters)) {
			// Si no se ha informado regresar el error y no seguir validando
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
			return false;
		}
		
		//Validar que exista el registro a actualizar
		if(ValidatorUtil.isNull(parameters.getId()))
		{
			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_ID_ERROR, request);
			return false;
		} else {
			
				registroUpdate = this.exists(parameters.getId(),null);
			
				if (ValidatorUtil.isNull(registroUpdate)) {
					ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_ERROR, request);
					return false;
				}
		}
		
		// Validaciones de campos obligatorios: NOMBRE
		if (!StringUtil.isNullOrEmpty(parameters.getIdNombre())) {
			// Validacion de tamano
			String idNombre = StringUtil.substring(parameters.getIdNombre(), DataConstants.MAX_SIZE_ID_NOMBRE);

			// Validacion de formato
			parameters.setIdNombre(StringUtil.toUpperCase(idNombre));
			
				//Validar la posible duplicidad del idNombre
				MenuDO menuBusqueda = this.exists(null, request.getParameters().getIdNombre());
				
				if (!ValidatorUtil.isNull(menuBusqueda)) {
					//Si se encuentra el registro validamos que no sea el mismo Id
					if (registroUpdate.getId() != menuBusqueda.getId()) {
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
		
		if (ValidatorUtil.isNullOrZero(parameters.getOrden())) {
			parameters.setOrden(registroUpdate.getOrden());
		}
		
		if (ValidatorUtil.isNullOrZero(parameters.getPasoConfig())) {
			parameters.setPasoConfig(registroUpdate.getPasoConfig());
		}
		
		if (StringUtil.isNullOrEmpty(parameters.getImagePath())) {
			parameters.setImagePath(registroUpdate.getImagePath());
		}
		
		if (StringUtil.isNullOrEmpty(parameters.getPath())) {
			parameters.setPath(registroUpdate.getPath());
		}
			
		// Retorna el resultado de la validacion.
		return ValidatorUtil.isSuccessfulResponse(response);
		
	}

	
	/**
	 * Valida que los parametros para la operacion de actualizacion sean correctos
	 * 
	 * @param request  Objeto con los parametros de entrada
	 * @param response Respuesta donde se agregan los errores
	 * @return true si todos los parametros son correctos
	 */
	private boolean validateParametersUpdateSubMenu(RequestVO<UpdateSubMenuRequestVO> request, ResponseVO<Long> response) {
		// Recuperar parametros de entrada
		UpdateSubMenuRequestVO parameters = request.getParameters();
		SubmenuDO registroUpdate = new SubmenuDO();
		
		// Validar que se informaron los campos de entrada
		if (ValidatorUtil.isNull(parameters)) {
			// Si no se ha informado regresar el error y no seguir validando
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
			return false;
		}
		
		//Validar que exista el registro a actualizar
		if(ValidatorUtil.isNull(parameters.getId()))
		{
			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_ID_ERROR, request);
			return false;
		} else {
			
			registroUpdate = this.existsSubMenu(null,parameters.getId(),null);
		
			if (ValidatorUtil.isNull(registroUpdate)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_ERROR, request);
				return false;
			} else {
				request.getParameters().setIdMenu(registroUpdate.getMenu().getId());
			}
		}
		
		// Validaciones de campos obligatorios: NOMBRE
		if (!StringUtil.isNullOrEmpty(parameters.getIdNombre())) {
			// Validacion de tamano
			String idNombre = StringUtil.substring(parameters.getIdNombre(), DataConstants.MAX_SIZE_ID_NOMBRE);

			// Validacion de formato
			parameters.setIdNombre(StringUtil.toUpperCase(idNombre));
			
				//Validar la posible duplicidad del idNombre
				SubmenuDO subMenuBusqueda = this.existsSubMenu(registroUpdate.getMenu().getId(), 
																null, 
																request.getParameters().getIdNombre());
				
				if (!ValidatorUtil.isNull(subMenuBusqueda)) {
					//Si se encuentra el registro validamos que no sea el mismo Id
					if (registroUpdate.getId() != subMenuBusqueda.getId()) {
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
		
		if (StringUtil.isNullOrEmpty(parameters.getImagePath())) {
			parameters.setImagePath(registroUpdate.getImagePath());
		}
		
		if (StringUtil.isNullOrEmpty(parameters.getPath())) {
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
	private boolean validateParametersDelete(RequestVO<DeleteMenuRequestVO> request, ResponseVO<Boolean> response) {

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

	/**
	 * Valida que los parametros para la operacion de eliminacion sean correctos
	 * 
	 * @param request  Objeto con los parametros de entrada
	 * @param response Respuesta donde se agregan los errores
	 * @return true si todos los parametros son correctos
	 */
	private boolean validateParametersDeleteSubMenu(RequestVO<DeleteSubMenuRequestVO> request, ResponseVO<Boolean> response) {

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
	private boolean validateParametersFindDetail(RequestVO<FindDetailMenuRequestVO> request, ResponseVO<FindDetailMenuResponseVO> response) {

		// Recuperar los parametros de entrada
		FindDetailMenuRequestVO parameters = request.getParameters();

		// validar que el campo obligatorio
		if (ValidatorUtil.isNullOrZero(parameters.getId())) {
			
			//Buscar por criterio: IdNombre
			if (ValidatorUtil.isNullOrEmpty(parameters.getIdNombre())) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_ID_ERROR);
			} 
		}
		
		return ValidatorUtil.isSuccessfulResponse(response);
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
	private boolean validateParametersFindDetailSubMenu(RequestVO<FindDetailSubMenuRequestVO> request, ResponseVO<FindDetailSubMenuResponseVO> response) {

		// Recuperar los parametros de entrada
		FindDetailSubMenuRequestVO parameters = request.getParameters();

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
	private boolean validateParametersFindByList(RequestVO<FindListMenuRequestVO> request,
			ResponseVO<List<FindListMenuResponseVO>> response) {
		
		// Validar campos obligatorios
	    ValidatorArqUtil.validateParameters(request, response);
		
		// validar los parametros de la paginacion
	    ValidatorArqUtil.validatePaginatonParameters(request, response);
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
	private boolean validateParametersFindByListSubMenu(RequestVO<FindListSubMenuRequestVO> request,
			ResponseVO<List<FindListSubMenuResponseVO>> response) {
		
		// Validar campos obligatorios
	    ValidatorArqUtil.validateParameters(request, response);
		
		// validar los parametros de la paginacion
	    ValidatorArqUtil.validatePaginatonParameters(request, response);
	    
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
	private boolean validateParametersFindByListUnassignedSubMenu(RequestVO<FindListUnassignedSubmenuRequestVO> request,
			ResponseVO<List<FindListSubMenuResponseVO>> response) {
		
		FindListUnassignedSubmenuRequestVO parameters = request.getParameters();
		
		// Validar campos obligatorios
	    ValidatorArqUtil.validateParameters(request, response);
		
		// validar los parametros de la paginacion
	    ValidatorArqUtil.validatePaginatonParameters(request, response);
	    
		if(ValidatorUtil.isNullOrZero(parameters.getIdRolFuncion())) {
			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_ROL_FUNCION_ID_ERROR);
		}
	    
		return ValidatorUtil.isSuccessfulResponse(response);

	}

	/**
	 * Obtiene una lista de objetos menuVO a partir de una lista de DO
	 * 
	 * @return Lista VO para retorno de resultados
	 * 
	 * @param listaMenu a transformar
	 */
	private List<FindListMenuResponseVO> transformListDO(List<MenuDO> listaMenu) {

		// Declarar variables
		List<FindListMenuResponseVO> listaMenuVO = new ArrayList<>();

		// recorrer el objeto origen
		for (MenuDO menuDO : listaMenu) {
			// Se hace la declaracion de variables necesarias
			FindListMenuResponseVO menuVO = new FindListMenuResponseVO();
			
			menuVO.setId(menuDO.getId());
			menuVO.setIdNombre(menuDO.getIdNombre());
			menuVO.setDescripcion(menuDO.getDescripcion());
			menuVO.setImagePath(menuDO.getImagePath());
			menuVO.setPath(menuDO.getPath());
			menuVO.setOrden(menuDO.getOrden());
			menuVO.setPasoConfig(menuDO.getPasoConfig());
			
			listaMenuVO.add(menuVO);
		}

		return listaMenuVO;
	}

	/**
	 * Obtiene una lista de objetos menuVO a partir de una lista de DO
	 * 
	 * @return Lista VO para retorno de resultados
	 * 
	 * @param listaMenu a transformar
	 */
	private List<FindListSubMenuResponseVO> transformListSubMenuDO(List<SubmenuDO> listaMenu) {

		// Declarar variables
		List<FindListSubMenuResponseVO> listaMenuVO = new ArrayList<>();

		// recorrer el objeto origen
		for (SubmenuDO subMenuDO : listaMenu) {
			// Se hace la declaracion de variables necesarias
			FindListSubMenuResponseVO subMenuVO = new FindListSubMenuResponseVO();
			
			subMenuVO.setId(subMenuDO.getId());
			subMenuVO.setIdNombre(subMenuDO.getIdNombre());
			subMenuVO.setDescripcion(subMenuDO.getDescripcion());
			subMenuVO.setImagePath(subMenuDO.getImagePath());
			subMenuVO.setPath(subMenuDO.getPath());
			
			listaMenuVO.add(subMenuVO);
		}

		return listaMenuVO;
	}

	/*************************************************************************
	 * Metodo que busca un registro por su id, idNombre
	 * Regresa el objeto de la base de datos o una excepcion con el error
	 * 
	 *************************************************************************/
	public MenuDO exists(Long id, String idNombre){

		MenuDO registro = null;
		try {
			//Validacion de datos de entrada
			if (ValidatorUtil.isNullOrZero(id)) {
				if (ValidatorUtil.isNullOrEmpty(idNombre)) {
					registro = null;
				} else {
					//Buscamos por nombre
					registro = menuRepository.findByIdNombre(idNombre);
				}
			} else {
				//Consulta
				registro = menuRepository.findById(id);
			}
			//Validacion de existencia
			if (ValidatorUtil.isNull(registro)) {
				//Genera error
				registro = null;
			}
		} catch (Exception e) {
			registro = null;
		}
		
		return registro;
	}	

	/*************************************************************************
	 * Metodo que busca un registro por su id, idNombre
	 * Regresa el objeto de la base de datos o una excepcion con el error
	 * 
	 *************************************************************************/
	public SubmenuDO existsSubMenu(Long idMenu, Long id, String idNombre){

		SubmenuDO registro = null;
		try {
			//Validacion de datos de entrada
			if (ValidatorUtil.isNullOrZero(id)) {
				if (ValidatorUtil.isNullOrEmpty(idNombre) ||
					ValidatorUtil.isNullOrZero(idMenu)) {
					registro = null;
				} else {
					//Buscamos por nombre
					registro = subMenuRepository.findByIdNombre(idMenu, idNombre);
				}
			} else {
				//Consulta
				registro = subMenuRepository.findById(id);
			}
			//Validacion de existencia
			if (ValidatorUtil.isNull(registro)) {
				//Genera error
				registro = null;
			}
		} catch (Exception e) {
			registro = null;
		}
		
		return registro;
	}	


	public String cleanString(String strInput) {
		if(!ValidatorUtil.isNullOrEmpty(strInput)) {
			strInput = strInput.trim().toUpperCase();
		}
		return strInput; 
	}
	
	public String limpiarAcentos(String cadena) {
	    String limpio =null;
	    if (cadena !=null) {
	        String valor = cadena;
	        valor = valor.toUpperCase();
	        // Normalizar texto para eliminar acentos, dieresis, cedillas y tildes
	        limpio = Normalizer.normalize(valor, Normalizer.Form.NFD);
	        // Quitar caracteres no ASCII excepto la enie, interrogacion que abre, exclamacion que abre, grados, U con dieresis.
	        limpio = limpio.replaceAll("[^\\p{ASCII}(N\u0303)(n\u0303)(\u00A1)(\u00BF)(\u00B0)(U\u0308)(u\u0308)]", "");
	        // Regresar a la forma compuesta, para poder comparar la enie con la tabla de valores
	        limpio = Normalizer.normalize(limpio, Normalizer.Form.NFC);
	    }
	    return limpio;
	}
	


}
