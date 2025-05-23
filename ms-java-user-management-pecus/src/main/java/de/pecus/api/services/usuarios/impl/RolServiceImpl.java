package de.pecus.api.services.usuarios.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import de.pecus.api.annotation.Auditable;
import de.pecus.api.constant.DataConstants;
import de.pecus.api.entities.AplicacionDO;
import de.pecus.api.entities.FuncionDO;
import de.pecus.api.entities.MenuDO;
import de.pecus.api.entities.RelUsuarioInternoRolDO;
import de.pecus.api.entities.RolDO;
import de.pecus.api.entities.RolFuncionDO;
import de.pecus.api.entities.RolFuncionMenuDO;
import de.pecus.api.entities.RolFuncionSubmenuDO;
import de.pecus.api.entities.SubmenuDO;
import de.pecus.api.entities.TipoRolDO;
import de.pecus.api.error.FuncionesBusinessError;
import de.pecus.api.error.GeneralBusinessErrors;
import de.pecus.api.error.UsuarioBusinessErrors;
import de.pecus.api.exception.PecusException;
import de.pecus.api.repositories.funciones.AplicacionRepository;
import de.pecus.api.repositories.funciones.FuncionRepository;
import de.pecus.api.repositories.funciones.MenuRepository;
import de.pecus.api.repositories.funciones.RolFuncionMenuRepository;
import de.pecus.api.repositories.funciones.RolFuncionRepository;
import de.pecus.api.repositories.funciones.RolFuncionSubmenuRepository;
import de.pecus.api.repositories.funciones.SubMenuRepository;
import de.pecus.api.repositories.usuarios.RolRepository;
import de.pecus.api.repositories.usuarios.TipoRolRepository;
import de.pecus.api.services.usuarios.RolService;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.util.ServiceUtil;
import de.pecus.api.util.StringUtil;
import de.pecus.api.util.ValidatorArqUtil;
import de.pecus.api.util.ValidatorUtil;
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
import de.pecus.api.vo.funciones.SimpleIdVO;
import de.pecus.api.vo.roles.CreateRolRequestVO;
import de.pecus.api.vo.roles.DeleteRolRequestVO;
import de.pecus.api.vo.roles.FindDetailRolRequestVO;
import de.pecus.api.vo.roles.FindDetailRolResponseVO;
import de.pecus.api.vo.roles.FindListRolRequestVO;
import de.pecus.api.vo.roles.FindListRolResponseVO;
import de.pecus.api.vo.roles.FuncionMenuVO;
import de.pecus.api.vo.roles.UpdateRolRequestVO;

/**
 * Clase de logica de negocio para administracion de Rol
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
@Service
public class RolServiceImpl implements RolService {
	
	@Autowired
	private AplicacionRepository aplicacionRepository;

	@Autowired
	private RolRepository rolRepository;
	
	@Autowired
	private RolFuncionRepository rolFuncionRepository;
	
	@Autowired
	private RolFuncionMenuRepository rolFuncionMenuRepository;
	
	@Autowired
	private TipoRolRepository tipoRolRepository;
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private FuncionRepository funcionRepository;
	
	@Autowired
	private SubMenuRepository subMenuRepository;
	
	@Autowired
	private RolFuncionSubmenuRepository rolFuncionSubmenuRepository;
	
	@Autowired
	private EntityManager entityManager;

	/**
	 * Metodo encargado de validar la existencia del rol por su identificador
	 * 
	 * @param idRol
	 */
	public RolDO exists(Long idRol, String idioma) {

		// Validacion de datos de entrada
		if (ValidatorUtil.isNullOrZero(idRol)) {
			throw new PecusException(UsuarioBusinessErrors.REQUIRED_ROL_ERROR, idioma);
		}

		// Consulta
		RolDO rol = rolRepository.findByIdAndActive(idRol, Boolean.TRUE);

		// Validacion de existencia
		if (ValidatorUtil.isNull(rol)) {
			// Genera error
			throw new PecusException(UsuarioBusinessErrors.NOT_FOUND_ROL_ERROR, idioma);
		}
		return rol;
	}
	
	/**
	 * Metodo encargado de validar la existencia del rol por su nombre
	 * 
	 * @param idRol
	 */
	public List<RolDO> exists(String rolName, String idioma) {

		// Validacion de datos de entrada
		if (ValidatorUtil.isNullOrEmpty(rolName)) {
			throw new PecusException(UsuarioBusinessErrors.REQUIRED_ROL_ERROR, idioma);
		}
		
		// Consulta
		List<RolDO> rolList = rolRepository.findByrolNameAndActive(rolName, Boolean.TRUE);

		// Validacion de existencia
		if (ValidatorUtil.isNullOrEmpty(rolList)) {
			// Genera error
			throw new PecusException(UsuarioBusinessErrors.NOT_FOUND_ROL_ERROR, idioma);
		}
		return rolList;
	}

	/**
	 * Asocia una rol a un aplicacion
	 * 
	 * @param request Objeto con parametros de entrada
	 * 
	 * @return Id generado
	 */
	@Auditable
	public ResponseVO<Long> createRol(RequestVO<CreateRolRequestVO> request) {

		// Declarar variables
		ResponseVO<Long> response = new ResponseVO<>();
		
			// Validar los parametros de entrada
			if (validateParametersCreateRol(request, response)) {
		
				// Preparar los datos para actualizar la BB.DD.
				AplicacionDO aplicacionDO = new AplicacionDO();
				RolDO rolDO = new RolDO();
				TipoRolDO tipoRolDO = new TipoRolDO();
				
				aplicacionDO.setId(request.getParameters().getIdAplicacion());
				tipoRolDO.setId(request.getParameters().getIdTipoRol());
				
				rolDO.setAplicacion(aplicacionDO);
				rolDO.setTipoRol(tipoRolDO);
				rolDO.setIdNombre(request.getParameters().getIdNombre());
				rolDO.setDescripcion(request.getParameters().getDescripcion());
				
				// Actualizar los parametros de auditoria
				ServiceUtil.setAuditFields(rolDO, request.getToken());

				// Insertar el registro
				rolDO = rolRepository.saveAndFlush(rolDO);

				// Regresar la respuesta correcta y el objeto a regresar
				response.setSuccess(true);
				response.setData(rolDO.getId());
			}
		return response;
	}

	/**
	 * Actualiza un registro de una Rol
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id actualizado
	 */
	@Auditable
	public ResponseVO<Long> updateRol(RequestVO<UpdateRolRequestVO> request) {

		// Declarar variables
		ResponseVO<Long> response = new ResponseVO<>();
		RolDO registroDO = new RolDO();
		TipoRolDO tipoRolDO = new TipoRolDO();
		AplicacionDO aplicacionDO = new AplicacionDO();
		
		// Validar los campos de entrada
		if (validateParametersUpdateRol(request, response)) {
			
			UpdateRolRequestVO parameters = request.getParameters();

			registroDO.setId(parameters.getId());
			registroDO.setIdNombre(parameters.getIdNombre());
			registroDO.setDescripcion(parameters.getDescripcion());

			tipoRolDO.setId(parameters.getIdTipoRol());
			registroDO.setTipoRol(tipoRolDO);
			
			aplicacionDO.setId(parameters.getIdAplicacion());
			registroDO.setAplicacion(aplicacionDO);
			
			// Actualizar parametros de auditoria
			ServiceUtil.setAuditFields(registroDO, request.getToken());

			// Actualizar el registro en BB.DD.
			registroDO = rolRepository.saveAndFlush(registroDO);

			// Preparar respuesta y objeto actualizado
			response.setSuccess(true);
			response.setData(registroDO.getId());
		}
		return response;
	}

	/**
	 * Marca un registro como eliminado un registro de rol
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id eliminado
	 */
	@Auditable
	public ResponseVO<Boolean> deleteRol(RequestVO<DeleteRolRequestVO> request) {

		// Declarar variables
		ResponseVO<Boolean> response = new ResponseVO<>();

		// Validar campos de entrada
		if (validateParametersDeleteRol(request, response)) {

			RolDO rolDO = this.existsRol(null, request.getParameters().getId(), null);
			
			if (ValidatorUtil.isNull(rolDO)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_ERROR, request);
			}
			else {
			// Actualizar la informacion
				ServiceUtil.setDisabledEntity(rolDO, request.getToken());
			
				// Actualizar la BB.DD.
				rolRepository.saveAndFlush(rolDO);
	
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
	public ResponseVO<FindDetailRolResponseVO> findDetailRol(RequestVO<FindDetailRolRequestVO> request) {

		// declaracion de varables
		ResponseVO<FindDetailRolResponseVO> response = new ResponseVO<>();
		FindDetailRolResponseVO salida = new FindDetailRolResponseVO();
		
		// validar que se cumplen las condiciones para realizar la consulta
		if (validateParametersFindDetailRol(request, response)) {

			RolDO rolDO = this.existsRol(null, request.getParameters().getId(), null);

			if (ValidatorUtil.isNull(rolDO)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_ERROR, request);
			} else {
				salida.setId(rolDO.getId());
				salida.setIdNombre(rolDO.getIdNombre());
				salida.setDescripcion(rolDO.getDescripcion());
				salida.setIdAplicacion(rolDO.getAplicacion().getId());
				salida.setIdTipoRol(rolDO.getTipoRol().getId());
				salida.setIdNombreTipoRol(rolDO.getTipoRol().getIdNombre());
				
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
	public ResponseVO<List<FindListRolResponseVO>> findListRol(RequestVO<FindListRolRequestVO> request) {

		// declaracion de varables
		ResponseVO<List<FindListRolResponseVO>> response = new ResponseVO<>();
		
		Page<RolDO> lista = null;
		
		if (validateParametersFindByListRol(request, response)) {
	
			FindListRolRequestVO parameters = request.getParameters();
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
			if(parameters.getIdUsuario() != null) {
				lista = rolRepository.findListByUser(parameters.getIdAplicacion(), parameters.getIdNombre(), parameters.getIdUsuario(), pageable);
			}else {
				lista = rolRepository.findList(parameters.getIdAplicacion(), parameters.getIdNombre(), pageable);
			}

			// Si no se encontro ningun registro que cumpla la condicion generar error.
			if (ValidatorUtil.isNullOrEmpty(lista.getContent())) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_REGISTER_LIST_ERROR);
			} else {
				// Regresar la respuesta correcta con los registros obtenidos.
				response.setSuccess(true);
				response.setTotalRows(lista.getTotalElements());
				response.setData(transformListRolDO(lista.getContent()));
			}
		}
		return response;
	}

	/**
	 * Crea una relación entre una funcion y un rol de una aplicacion
	 * 
	 * @param request Objeto con parametros de entrada
	 * 
	 * @return Id generado
	 */
	@Auditable
	public ResponseVO<CreateRolFuncionResponseVO> createRolFuncion(RequestVO<CreateRolFuncionRequestVO> request) {

		// Declarar variables
		ResponseVO<CreateRolFuncionResponseVO> response = new ResponseVO<>();
		
			// Validar los parametros de entrada y validar que las funciones en la peticion existan
			if (validateParametersCreateRolFuncion(request, response) && validateFunctionExists(request, response)) {
				RolDO rolDO = new RolDO();				
				rolDO.setId(request.getParameters().getIdRol());
				
				Integer size = ValidatorUtil.isNullOrZero(request.getSize()) ? 100 : request.getSize();
				Integer page = ValidatorUtil.isNullOrZero(request.getPage()) ? 1 : request.getPage();
				Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Direction.DESC, "id"));
				Page<RolFuncionDO> rolFuncionActual = rolFuncionRepository.findByIdRol(request.getParameters().getIdRol(), pageable);
				ArrayList<FuncionMenuVO> listaMenus =  new ArrayList<FuncionMenuVO>();
				
				for (RolFuncionDO registro : rolFuncionActual.getContent()) {
					List<RolFuncionMenuDO> rolFuncionMenuExistList = rolFuncionMenuRepository
							.findByIdRolFuncion(registro.getId());
					if (!ValidatorUtil.isNullOrEmpty(rolFuncionMenuExistList)) {
						RolFuncionMenuDO rolFuncionMenuElement = rolFuncionMenuExistList.get(0);
						
						//Borrar registro de la relacion RolFuncionMenu
						rolFuncionMenuRepository.delete(rolFuncionMenuElement);
						
						//Crear una instancia con la funcion y el menu y agregarlo a un arreglo
						listaMenus.add(new FuncionMenuVO(registro.getFuncion().getId(),rolFuncionMenuExistList.get(0).getMenu()));
						
					}	
				}
				
				
				//Limpia las relaciones previas de rol funcion
				cleanRolFuncion(request);
				
				// Se crean un registro nuevo por cada id y se prepara para actualizar la BBDD
				int registros = 0;
				for(SimpleIdVO funcion : request.getParameters().getFunciones()) {
					
					// Validar duplicados, si la relacion es duplicada se salta a la sig iteracion
					RolFuncionDO registroDuplicadoDO = this.existsRolFuncion(rolDO.getId(), funcion.getId(), null);
					if (!ValidatorUtil.isNull(registroDuplicadoDO)) {
						registros++;
						continue;
					}
					
					// Se crea nuevo registro para insertar en la B.B.D.D.
					FuncionDO funcionDO = new FuncionDO();
					RolFuncionDO rolFuncionDO = new RolFuncionDO();
					
					funcionDO.setId(funcion.getId());
					rolFuncionDO.setRol(rolDO);
					rolFuncionDO.setFuncion(funcionDO);
					
					// Actualizar los parametros de auditoria
					ServiceUtil.setAuditFields(rolFuncionDO, request.getToken());
					
					// Insertar el registro
					rolFuncionDO = rolFuncionRepository.saveAndFlush(rolFuncionDO);
					registros++;
					
					//Crear de nuevo la relacion rolFuncionMenu
					for(FuncionMenuVO item : listaMenus) {
						if(funcion.getId() == item.getIdFuncion()) {
							RolFuncionMenuDO rolFuncionMenuDO = new RolFuncionMenuDO();
							
							rolFuncionMenuDO.setRolFuncion(rolFuncionDO);
							rolFuncionMenuDO.setMenu(item.getMenu());
							
							ServiceUtil.setAuditFields(rolFuncionMenuDO, request.getToken());
							rolFuncionMenuDO = rolFuncionMenuRepository.saveAndFlush(rolFuncionMenuDO);
						}
					}	
				}

				// Regresar la respuesta correcta y el objeto a regresar
				CreateRolFuncionResponseVO  responseVO = new CreateRolFuncionResponseVO();
				responseVO.setTotalRegistros(registros);
				
				response.setSuccess(true);
				response.setData(responseVO);
			}
		return response;
	}

	/**
	 * Marca un registro como eliminado un registro de rol
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id eliminado
	 */
	@Auditable
	public ResponseVO<Boolean> deleteRolFuncion(RequestVO<DeleteRolFuncionRequestVO> request) {

		// Declarar variables
		ResponseVO<Boolean> response = new ResponseVO<>();

		// Validar campos de entrada
		if (validateParametersDeleteRolFuncion(request, response)) {

			RolFuncionDO registroDO = rolFuncionRepository.findById(request.getParameters().getId());
			
			if (ValidatorUtil.isNull(registroDO)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_ERROR, request);
			}
			else {
			// Actualizar la informacion
				ServiceUtil.setDisabledEntity(registroDO, request.getToken());
			
				// Actualizar la BB.DD.
				rolFuncionRepository.saveAndFlush(registroDO);
	
				// Preparar respuesta y objeto eliminado
				response.setSuccess(true);
				response.setData(Boolean.TRUE);
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
	public ResponseVO<List<FindListRolFuncionResponseVO>> findListRolFuncion(RequestVO<FindListRolFuncionRequestVO> request) {

		// declaracion de varables
		ResponseVO<List<FindListRolFuncionResponseVO>> response = new ResponseVO<>();
		
		Page<RolFuncionDO> lista = null;
		
		if (validateParametersFindByListRolFuncion(request, response)) {
	
			FindListRolFuncionRequestVO parameters = request.getParameters();
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
			lista = rolFuncionRepository.findList(parameters.getIdRol(), pageable);

			// Si no se encontro ningun registro que cumpla la condicion generar error.
			if (ValidatorUtil.isNullOrEmpty(lista.getContent())) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_REGISTER_LIST_ERROR);
			} else {
				// Regresar la respuesta correcta con los registros obtenidos.
				response.setSuccess(true);
				response.setTotalRows(lista.getTotalElements());
				response.setData(transformListRolFuncionDO(lista.getContent()));
			}
		}
		return response;
	}

	/**
	 * Crea una relación entre una rol-funcion-menu
	 * 
	 * @param request Objeto con parametros de entrada
	 * 
	 * @return Id generado
	 */
	@Auditable
	public ResponseVO<List<Long>> createRolFuncionMenu(RequestVO<CreateRolFuncionMenuRequestVO> request) {

		// Declarar variables
		ResponseVO<List<Long>> response = new ResponseVO<>();

		// Validar los parametros de entrada
		if (validateParametersCreateRolFuncionMenu(request, response)) {

			// Limpiamos los registros existentes para realizar la carga de los nuevos
			cleanRolFuncionMenu(request.getParameters());

			List<RolFuncionMenuDO> rolFuncionMenuDOs = new ArrayList<RolFuncionMenuDO>();
			for (SimpleIdVO menu : request.getParameters().getMenus()) {
				// Preparar los datos para actualizar la BB.DD.
				RolFuncionMenuDO rolFuncionMenuDO = new RolFuncionMenuDO();
				RolFuncionDO rolFuncionDO = new RolFuncionDO();
				MenuDO menuDO = new MenuDO();

				rolFuncionDO.setId(request.getParameters().getIdRolFuncion());
				menuDO.setId(menu.getId());

				rolFuncionMenuDO.setMenu(menuDO);
				rolFuncionMenuDO.setRolFuncion(rolFuncionDO);

				// Actualizar los parametros de auditoria
				ServiceUtil.setAuditFields(rolFuncionMenuDO, request.getToken());

				rolFuncionMenuDOs.add(rolFuncionMenuDO);
			}

			List<Long> listData = null;
			if(!ValidatorUtil.isNullOrEmpty(rolFuncionMenuDOs)) {
				// Insertar el registro
				List<RolFuncionMenuDO> resultadoRolFuncionMenuDOs = rolFuncionMenuRepository.saveAll(rolFuncionMenuDOs);
				if (!resultadoRolFuncionMenuDOs.isEmpty()) {
					listData = new ArrayList<Long>();
					for (RolFuncionMenuDO rolFuncionMenuDO : resultadoRolFuncionMenuDOs) {
						listData.add(rolFuncionMenuDO.getId());
					}
				}
			}

			// Regresar la respuesta correcta y el objeto a regresar
			response.setData(listData);
			response.setSuccess(Boolean.TRUE);
		}
		return response;
	}

	/**
	 * Marca un registro como eliminado un registro de rol
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id eliminado
	 */
	@Auditable
	public ResponseVO<Boolean> deleteRolFuncionMenu(RequestVO<DeleteRolFuncionMenuRequestVO> request) {

		// Declarar variables
		ResponseVO<Boolean> response = new ResponseVO<>();

		// Validar campos de entrada
		if (validateParametersDeleteRolFuncionMenu(request, response)) {

			RolFuncionMenuDO registroDO = rolFuncionMenuRepository.findById(request.getParameters().getId());
			
			if (ValidatorUtil.isNull(registroDO)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_ERROR, request);
			}
			else {
			
				// Actualizar la BB.DD.
				rolFuncionMenuRepository.delete(registroDO);
	
				// Preparar respuesta y objeto eliminado
				response.setSuccess(true);
				response.setData(Boolean.TRUE);
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
	public ResponseVO<List<FindListRolFuncionMenuResponseVO>> findListRolFuncionMenu(RequestVO<FindListRolFuncionMenuRequestVO> request) {

		// declaracion de varables
		ResponseVO<List<FindListRolFuncionMenuResponseVO>> response = new ResponseVO<>();
		
		Page<RolFuncionMenuDO> lista = null;
		
		if (validateParametersFindByListRolFuncionMenu(request, response)) {
	
			FindListRolFuncionMenuRequestVO parameters = request.getParameters();
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
			lista = rolFuncionMenuRepository.findList(parameters.getIdRolFuncion(), parameters.getIdMenu(), pageable);

			// Si no se encontro ningun registro que cumpla la condicion generar error.
			if (ValidatorUtil.isNullOrEmpty(lista.getContent())) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_REGISTER_LIST_ERROR);
			} else {
				// Regresar la respuesta correcta con los registros obtenidos.
				response.setSuccess(true);
				response.setTotalRows(lista.getTotalElements());
				response.setData(transformListRolFuncionMenuDO(lista.getContent()));
			}
		}
		return response;
	}
	
	/////
	
	/**
	 * Valida que los parametros para la operacion de insercion sean correctos
	 * 
	 * @param request  Objeto con los parametros de entrada
	 * @param response Respuesta donde se agregan los errores
	 * @return true si todos los parametros son correctos
	 */
	private boolean validateParametersCreateRol(RequestVO<CreateRolRequestVO> request, ResponseVO<Long> response) {
		
		// Obtener los parametros de entrada
		CreateRolRequestVO parameters = request.getParameters();

		
		if (ValidatorUtil.isNullOrZero(parameters.getIdAplicacion())) {
			ResponseUtil.addError(request, response, 
					FuncionesBusinessError.REQUIRED_APPLICATION_ID_ERROR, request);					
		}  else {
			
			//************************************
			//validar que existe la aplicacion
			//************************************
			AplicacionDO aplicacionDO = this.existsAplicacion(parameters.getIdAplicacion(), null);
			
			if (ValidatorUtil.isNull(aplicacionDO)) {
				ResponseUtil.addError(request, response, 
						FuncionesBusinessError.REQUIRED_NOT_FOUND_APPLICATION_ID_ERROR, request);									
				return false;
			} 
			
			//************************************
			//validar que existe el tipo de Rol
			//************************************
			TipoRolDO tipoRolDO = tipoRolRepository.findById(parameters.getIdTipoRol());
			
			if (ValidatorUtil.isNull(tipoRolDO)) {
				ResponseUtil.addError(request, response, 
						FuncionesBusinessError.REQUIRED_NOT_FOUND_TIPO_ROL_ID_ERROR, request);									
				return false;
			}

			//************************************
			// Validaciones de campos obligatorios
			//************************************
			if (StringUtil.isNullOrEmpty(parameters.getIdNombre())) {
				ResponseUtil.addError(request, response, 
						FuncionesBusinessError.REQUIRED_ID_NOMBRE_ERROR, request);
			} else {
				// Validacion de tamano
				String idNombre = StringUtil.substring(parameters.getIdNombre(), DataConstants.MAX_SIZE_ID_NOMBRE);
	
				// Validacion de formato
				parameters.setIdNombre(StringUtil.toUpperCase(idNombre));
	
				RolDO registroB = this.existsRol(parameters.getIdAplicacion(),null,parameters.getIdNombre());
					
					if (!ValidatorUtil.isNull(registroB)) {
						ResponseUtil.addError(request, response, 
								FuncionesBusinessError.DUPLICATED_ERROR, request);					
				}
			}
			
			if (StringUtil.isNullOrEmpty(parameters.getDescripcion())) {
				ResponseUtil.addError(request, response, 
						FuncionesBusinessError.REQUIRED_DESCRIPCION_ERROR, request);
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
	private boolean validateParametersUpdateRol(RequestVO<UpdateRolRequestVO> request, ResponseVO<Long> response) {
		// Recuperar parametros de entrada
		UpdateRolRequestVO parameters = request.getParameters();
		RolDO registroUpdate = new RolDO();
		
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
			
				registroUpdate = this.existsRol(null,parameters.getId(),null);
			
				if (ValidatorUtil.isNull(registroUpdate)) {
					ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_ERROR, request);
					return false;
				} else {
					parameters.setIdAplicacion(registroUpdate.getAplicacion().getId());
				}
		}
		
		//Validar el tipo de Rol
		if (!ValidatorUtil.isNullOrZero(parameters.getIdTipoRol())) {
			
			TipoRolDO tipoRolDO = tipoRolRepository.findById(parameters.getIdTipoRol());
			
			if (ValidatorUtil.isNull(tipoRolDO)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_NOT_FOUND_TIPO_ROL_ID_ERROR, request);				
			}
			
		} else {
			parameters.setIdTipoRol(registroUpdate.getTipoRol().getId());
		}
		
		// Validaciones de campos obligatorios: NOMBRE
		if (!StringUtil.isNullOrEmpty(parameters.getIdNombre())) {
			// Validacion de tamano
			String idNombre = StringUtil.substring(parameters.getIdNombre(), DataConstants.MAX_SIZE_ID_NOMBRE);

			// Validacion de formato
			parameters.setIdNombre(StringUtil.toUpperCase(idNombre));
			
			//Validar la posible duplicidad del idNombre
			FuncionDO funcionBusqueda = this.existsFuncion( registroUpdate.getAplicacion().getId(), null, request.getParameters().getIdNombre());
				
			if (!ValidatorUtil.isNull(funcionBusqueda) && registroUpdate.getId().equals(funcionBusqueda.getId())) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.DUPLICATED_ERROR, request);
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
	private boolean validateParametersDeleteRol(RequestVO<DeleteRolRequestVO> request, ResponseVO<Boolean> response) {

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
	private boolean validateParametersFindDetailRol(RequestVO<FindDetailRolRequestVO> request, ResponseVO<FindDetailRolResponseVO> response) {

		// Recuperar los parametros de entrada
		FindDetailRolRequestVO parameters = request.getParameters();

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
	private boolean validateParametersFindByListRol(RequestVO<FindListRolRequestVO> request,
			ResponseVO<List<FindListRolResponseVO>> response) {
		
		
		if (ValidatorUtil.isNullOrZero(request.getParameters().getIdAplicacion())) {
		
			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_APPLICATION_ID_ERROR);			

		} else {
			
			// Validar campos obligatorios
		    ValidatorArqUtil.validateParameters(request, response);
			
			// validar los parametros de la paginacion
		    ValidatorArqUtil.validatePaginatonParameters(request, response);
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
	private List<FindListRolResponseVO> transformListRolDO(List<RolDO> lista) {

		// Declarar variables
		List<FindListRolResponseVO> listaVO = new ArrayList<>();

		// recorrer el objeto origen
		for (RolDO rolDO : lista) {
			// Se hace la declaracion de variables necesarias
			FindListRolResponseVO rolVO = new FindListRolResponseVO();
			
			rolVO.setId(rolDO.getId());
			rolVO.setIdAplicacion(rolDO.getAplicacion().getId());
			rolVO.setIdNombre(rolDO.getIdNombre());
			rolVO.setDescripcion(rolDO.getDescripcion());
			rolVO.setIdTipoRol(rolDO.getTipoRol().getId());
			rolVO.setIdNombreTipoRol(rolDO.getTipoRol().getIdNombre());
			
			listaVO.add(rolVO);
		}

		return listaVO;
	}
	
	/**
	 * Valida que los parametros para la operacion de insercion sean correctos
	 * 
	 * @param request  Objeto con los parametros de entrada
	 * @param response Respuesta donde se agregan los errores
	 * @return true si todos los parametros son correctos
	 */
	private boolean validateParametersCreateRolFuncion(RequestVO<CreateRolFuncionRequestVO> request, ResponseVO<CreateRolFuncionResponseVO> response) {
		
		// Obtener los parametros de entrada
		CreateRolFuncionRequestVO parameters = request.getParameters();

		
		if (ValidatorUtil.isNullOrZero(parameters.getIdAplicacion())) {
			ResponseUtil.addError(request, response, 
					FuncionesBusinessError.REQUIRED_APPLICATION_ID_ERROR, request);					
		}  else {
			
			//************************************
			//validar que existe la aplicacion
			//************************************
			AplicacionDO aplicacionDO = this.existsAplicacion(parameters.getIdAplicacion(), null);
			
			if (ValidatorUtil.isNull(aplicacionDO)) {
				ResponseUtil.addError(request, response, 
						FuncionesBusinessError.REQUIRED_NOT_FOUND_APPLICATION_ID_ERROR, request);									
				return false;
			} 
			
			//************************************
			//validar que existe el Rol
			//************************************
			RolDO rolDO = this.existsRol(null, parameters.getIdRol(), null);
			
			if (ValidatorUtil.isNull(rolDO)) {
				ResponseUtil.addError(request, response, 
						FuncionesBusinessError.REQUIRED_NOT_FOUND_ROL_ID_ERROR, request);									
				return false;
			}
		}
		// Regresar el resultado de la validacion
		return ValidatorUtil.isSuccessfulResponse(response);
	}
	
	/**
	 *  Valida si la funcion dentro de la peticion existe en BBDD
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean validateFunctionExists(RequestVO<CreateRolFuncionRequestVO> request,
			ResponseVO<CreateRolFuncionResponseVO> response) {
		
		for (SimpleIdVO funcion : request.getParameters().getFunciones()) {
			FuncionDO funcionDO = this.existsFuncion(null, funcion.getId(), null);

			if (ValidatorUtil.isNull(funcionDO)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_NOT_FOUND_FUNCTION_ID_ERROR,
						request);
			}
		}
		// Regresar el resultado de la validacion
		return ValidatorUtil.isSuccessfulResponse(response);
	}
	
	/**
	 * Deshabilita las relaciones rolFuncion
	 * @param request
	 */
	private void cleanRolFuncion(RequestVO<CreateRolFuncionRequestVO> request) {
		// Se limpian las funciones asociadas a un rol
		Integer size = ValidatorUtil.isNullOrZero(request.getSize()) ? 100 : request.getSize();
		Integer page = ValidatorUtil.isNullOrZero(request.getPage()) ? 1 : request.getPage();
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Direction.DESC, "id"));
		Page<RolFuncionDO> rolFuncionActual = rolFuncionRepository.findByIdRol(request.getParameters().getIdRol(), pageable);
		
		for (RolFuncionDO registro : rolFuncionActual.getContent()) {
			// Deshabilitar la relacion.
			rolFuncionRepository.delete(registro);					
		}
	}
	
	/**
	 * Valida que los parametros para la operacion de eliminacion sean correctos
	 * 
	 * @param request  Objeto con los parametros de entrada
	 * @param response Respuesta donde se agregan los errores
	 * @return true si todos los parametros son correctos
	 */
	private boolean validateParametersDeleteRolFuncion(RequestVO<DeleteRolFuncionRequestVO> request, ResponseVO<Boolean> response) {

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
	 * Valida que los parametros para la operacion de consulta por parametros sean
	 * correctos
	 * 
	 * @return true si el nombre no esta vacio
	 * 
	 * @param request  Objeto con los criterios a buscar
	 * @param response Respuesta donde se agregan los errores
	 */
	private boolean validateParametersFindByListRolFuncion(RequestVO<FindListRolFuncionRequestVO> request,
			ResponseVO<List<FindListRolFuncionResponseVO>> response) {
		
		
		if (ValidatorUtil.isNullOrZero(request.getParameters().getIdAplicacion())) {
		
			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_APPLICATION_ID_ERROR);			

		} else {
			
			if (!ValidatorUtil.isNullOrZero(request.getParameters().getIdRol())) {
				//Validar que el rol solicitado pertenece a la aplicación 
				RolDO registroDO = rolRepository.findByIdAndActive(request.getParameters().getIdRol(), true);
				
				if (!ValidatorUtil.isNull(registroDO) && !registroDO.getAplicacion().getId().equals(request.getParameters().getIdAplicacion())) {
					ResponseUtil.addError(request, response, FuncionesBusinessError.ROL_NOT_APPLICATION);								
				}
			} else {
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
	private List<FindListRolFuncionResponseVO> transformListRolFuncionDO(List<RolFuncionDO> lista) {

		// Declarar variables
		List<FindListRolFuncionResponseVO> listaVO = new ArrayList<>();

		// recorrer el objeto origen
		for (RolFuncionDO rolFuncionDO : lista) {
			// Se hace la declaracion de variables necesarias
			FindListRolFuncionResponseVO rolFuncionVO = new FindListRolFuncionResponseVO();
			
			rolFuncionVO.setId(rolFuncionDO.getId());
			rolFuncionVO.setIdFuncion(rolFuncionDO.getFuncion().getId());
			rolFuncionVO.setIdNombreFuncion(rolFuncionDO.getFuncion().getIdNombre());
			rolFuncionVO.setIdRol(rolFuncionDO.getRol().getId());
			rolFuncionVO.setIdNombreRol(rolFuncionDO.getRol().getIdNombre());
			
			listaVO.add(rolFuncionVO);
		}

		return listaVO;
	}
	
	/**
	 * Valida que los parametros para la operacion de insercion sean correctos
	 * 
	 * @param request  Objeto con los parametros de entrada
	 * @param response Respuesta donde se agregan los errores
	 * @return true si todos los parametros son correctos
	 */
	private boolean validateParametersCreateRolFuncionMenu(RequestVO<CreateRolFuncionMenuRequestVO> request,
			ResponseVO<List<Long>> response) {

		if (ValidatorUtil.isNull(request.getParameters())) {
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
		} else {
			// Obtener los parametros de entrada
			CreateRolFuncionMenuRequestVO parameters = request.getParameters();
			if (ValidatorUtil.isNullOrZero(parameters.getIdRolFuncion())) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_ROL_FUNCION_ID_ERROR, request);
			} else {
				// ************************************
				// validar que existe la relacion existe
				// ************************************
				RolFuncionDO rolFuncionDO = this.existsRolFuncion(null, null, parameters.getIdRolFuncion());

				if (ValidatorUtil.isNull(rolFuncionDO)) {
					ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_ROL_FUNCION_ID_ERROR,
							request);
					return false;
				}
			}

			if (ValidatorUtil.isNullOrEmpty(parameters.getMenus())) {
				for (SimpleIdVO menu : parameters.getMenus()) {

					// ************************************
					// validar que existe el menu
					// ************************************
					MenuDO menuDO = menuRepository.findById(menu.getId());

					if (ValidatorUtil.isNull(menuDO)) {
						ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_MENU_ERROR, request);
						return false;
					}
				}
			}
		}

		// Regresar el resultado de la validacion
		return ValidatorUtil.isSuccessfulResponse(response);
	}

	/**
	 * 	Se limpian los registros ecistentes para rol funcion menu
	 * @param rolFuncionMenuVO
	 */
	private void cleanRolFuncionMenu(CreateRolFuncionMenuRequestVO createRolFuncionMenuRequestVO) {
		// ************************************
		// Limpiamos los registros existentes para cargar los nuevos
		// ************************************
		List<RolFuncionMenuDO> rolFuncionMenuExistList = rolFuncionMenuRepository
				.findByIdRolFuncion(createRolFuncionMenuRequestVO.getIdRolFuncion());
		if (!ValidatorUtil.isNullOrEmpty(rolFuncionMenuExistList)) {
			rolFuncionMenuRepository.deleteAll(rolFuncionMenuExistList);
		}
	}
	
	/**
	 * Valida que los parametros para la operacion de eliminacion sean correctos
	 * 
	 * @param request  Objeto con los parametros de entrada
	 * @param response Respuesta donde se agregan los errores
	 * @return true si todos los parametros son correctos
	 */
	private boolean validateParametersDeleteRolFuncionMenu(RequestVO<DeleteRolFuncionMenuRequestVO> request, ResponseVO<Boolean> response) {

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
	 * Valida que los parametros para la operacion de consulta por parametros sean
	 * correctos
	 * 
	 * @return true si el nombre no esta vacio
	 * 
	 * @param request  Objeto con los criterios a buscar
	 * @param response Respuesta donde se agregan los errores
	 */
	private boolean validateParametersFindByListRolFuncionMenu(RequestVO<FindListRolFuncionMenuRequestVO> request,
			ResponseVO<List<FindListRolFuncionMenuResponseVO>> response) {
		
		
		if (ValidatorUtil.isNullOrZero(request.getParameters().getIdRolFuncion()) &&
				ValidatorUtil.isNullOrZero(request.getParameters().getIdMenu())) {
		
			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_PARAMETERS);			

		} else {
			
			// Validar campos obligatorios
		    ValidatorArqUtil.validateParameters(request, response);
			
			// validar los parametros de la paginacion
		    ValidatorArqUtil.validatePaginatonParameters(request, response);
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
	private List<FindListRolFuncionMenuResponseVO> transformListRolFuncionMenuDO(List<RolFuncionMenuDO> lista) {

		// Declarar variables
		List<FindListRolFuncionMenuResponseVO> listaVO = new ArrayList<>();

		// recorrer el objeto origen
		for (RolFuncionMenuDO rolFuncionMenuDO : lista) {
			// Se hace la declaracion de variables necesarias
			FindListRolFuncionMenuResponseVO rolFuncionMenuVO = new FindListRolFuncionMenuResponseVO();
			
			rolFuncionMenuVO.setId(rolFuncionMenuDO.getId());
			rolFuncionMenuVO.setIdRolFuncion(rolFuncionMenuDO.getRolFuncion().getId());
			rolFuncionMenuVO.setIdNombreMenu(rolFuncionMenuDO.getMenu().getIdNombre());
			rolFuncionMenuVO.setIdMenu(rolFuncionMenuDO.getMenu().getId());
			
			listaVO.add(rolFuncionMenuVO);
		}

		return listaVO;
	}
	
	/*************************************************************************
	 * Metodo que busca un registro por su id, idNombre
	 * Regresa el objeto de la base de datos o una excepcion con el error
	 * 
	 *************************************************************************/
	public RolDO existsRol(Long idAplicacion, Long id, String idNombre){

		RolDO registro = null;
		try {
			//Validacion de datos de entrada
			if (ValidatorUtil.isNullOrZero(id)) {
				if (ValidatorUtil.isNullOrEmpty(idNombre)) {
					registro = null;
				} else {
					//Buscamos por nombre
					registro = rolRepository.findByIdNombre(idAplicacion, idNombre);
				}
			} else {
				//Consulta
				registro = rolRepository.findByIdAndActive(id,true);
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
	public FuncionDO existsFuncion(Long idAplicacion, Long id, String idNombre){

		FuncionDO registro = null;
		try {
			//Validacion de datos de entrada
			if (ValidatorUtil.isNullOrZero(id)) {
				if (ValidatorUtil.isNullOrEmpty(idNombre)) {
					registro = null;
				} else {
					//Buscamos por nombre
					registro = funcionRepository.findByIdNombre(idAplicacion, idNombre);
				}
			} else {
				//Consulta
				registro = funcionRepository.findById(id);
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
	public RolFuncionDO existsRolFuncion(Long idRol, Long idFuncion, Long idRolFuncion){

		RolFuncionDO registro = null;
		try {
			
			if (ValidatorUtil.isNullOrZero(idRolFuncion)) {
				//Validacion de datos de entrada
				if (ValidatorUtil.isNullOrZero(idRol) ||
					ValidatorUtil.isNullOrZero(idFuncion)) {
						registro = null;
					} else {
						//Buscamos por nombre
						registro = rolFuncionRepository.findByIdRolIdFuncion(idRol, idFuncion);
					}
				//Validacion de existencia
				if (ValidatorUtil.isNull(registro)) {
					//Genera error
					registro = null;
				}
			} else {
				registro = rolFuncionRepository.findById(idRolFuncion);
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
	public RolFuncionMenuDO existsRolFuncionMenu(Long idRolFuncionMenu, Long idRolFuncion, Long idMenu){

		RolFuncionMenuDO registro = null;
		try {
			
			if (ValidatorUtil.isNullOrZero(idRolFuncionMenu)) {
				//Validacion de datos de entrada
				if (ValidatorUtil.isNullOrZero(idRolFuncion) ||
					ValidatorUtil.isNullOrZero(idMenu)) {
						registro = null;
					} else {
						//Buscamos por nombre
						registro = rolFuncionMenuRepository.findByIdRolIdFuncionMenu(idRolFuncion, idMenu);
					}
				//Validacion de existencia
				if (ValidatorUtil.isNull(registro)) {
					//Genera error
					registro = null;
				}
			} else {
				registro = rolFuncionMenuRepository.findById(idRolFuncionMenu);
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
	public AplicacionDO existsAplicacion(Long id, String idNombre){

		AplicacionDO registro = null;
		try {
			//Validacion de datos de entrada
			if (ValidatorUtil.isNullOrZero(id)) {
				if (ValidatorUtil.isNullOrEmpty(idNombre)) {
					registro = null;
				} else {
					//Buscamos por nombre
					registro = aplicacionRepository.findByIdNombre(idNombre);
				}
			} else {
				//Consulta
				registro = aplicacionRepository.findById(id);
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

	@Override
	@Auditable
	public ResponseVO<List<Long>> createRolFuncionSubmenu(RequestVO<CreateRolFuncionSubmenuRequestVO> request) {
		// Declarar variables
		ResponseVO<List<Long>> response = new ResponseVO<>();

		// Validar los parametros de entrada
		if (validateParametersCreateRolFuncionSubmenu(request, response)) {

			// Limpiamos los registros existentes
			cleanRolFuncionSubmenu(request.getParameters());

			List<RolFuncionSubmenuDO> rolFuncionSubmenuDOs = new ArrayList<RolFuncionSubmenuDO>();
			for (SimpleIdVO simpleIdVO : request.getParameters().getSubmenus()) {
				// Preparar los datos para actualizar la BB.DD.
				RolFuncionSubmenuDO rolFuncionSubmenuDO = new RolFuncionSubmenuDO();
				FuncionDO funcionDO = new FuncionDO();
				SubmenuDO submenuDO = new SubmenuDO();

				funcionDO.setId(request.getParameters().getIdFuncion());
				submenuDO.setId(simpleIdVO.getId());

				rolFuncionSubmenuDO.setSubmenu(submenuDO);
				rolFuncionSubmenuDO.setFuncion(funcionDO);

				// Actualizar los parametros de auditoria
				ServiceUtil.setAuditFields(rolFuncionSubmenuDO, request.getToken());

				rolFuncionSubmenuDOs.add(rolFuncionSubmenuDO);
			}

			List<Long> listData = null;
			if(!ValidatorUtil.isNullOrEmpty(rolFuncionSubmenuDOs)) {
				// Insertar el registro
				List<RolFuncionSubmenuDO> resultadoRolFuncionSubmenuDOs = rolFuncionSubmenuRepository
						.saveAll(rolFuncionSubmenuDOs);
				if (!resultadoRolFuncionSubmenuDOs.isEmpty()) {
					listData = new ArrayList<Long>();
					for (RolFuncionSubmenuDO rolFuncionSubmenuDO : resultadoRolFuncionSubmenuDOs) {
						listData.add(rolFuncionSubmenuDO.getId());
					}
				}
			}
			// Regresar la respuesta correcta y el objeto a regresar
			response.setData(listData);
			response.setSuccess(Boolean.TRUE);
		}
		return response;
	}
	
	/**
	 * Valida que los parametros para la operacion de insercion sean correctos
	 * 
	 * @param request  Objeto con los parametros de entrada
	 * @param response Respuesta donde se agregan los errores
	 * @return true si todos los parametros son correctos
	 */
	private boolean validateParametersCreateRolFuncionSubmenu(RequestVO<CreateRolFuncionSubmenuRequestVO> request, ResponseVO<List<Long>> response) {
		
		if (ValidatorUtil.isNull(request.getParameters())) {
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
		}else {
			// Obtener los parametros de entrada
			CreateRolFuncionSubmenuRequestVO parameters = request.getParameters();
			
			if (ValidatorUtil.isNullOrZero(parameters.getIdFuncion())) {
				ResponseUtil.addError(request, response, 
						FuncionesBusinessError.REQUIRED_ROL_FUNCION_ID_ERROR, request);					
			}  else {
				
				//************************************
				//validar que existe la funcion existe
				//************************************
				FuncionDO funcionDO = funcionRepository.findById(parameters.getIdFuncion());
				
				if (ValidatorUtil.isNull(funcionDO)) {
					ResponseUtil.addError(request, response, 
							FuncionesBusinessError.NOT_FOUND_FUNCION_ID_ERROR, request);									
					return false;
				} 
				
				if(!ValidatorUtil.isNullOrEmpty(parameters.getSubmenus())) {
					for(SimpleIdVO simpleIdVO : parameters.getSubmenus()) {
						//************************************
						//validar que existe el submenu
						//************************************
						SubmenuDO submenuDO = subMenuRepository.findById(simpleIdVO.getId());
						
						if (ValidatorUtil.isNull(submenuDO)) {
							ResponseUtil.addError(request, response, 
									FuncionesBusinessError.NOT_FOUND_SUBMENU_ERROR, request);									
							return false;
						}
					}
				}
			}
		}
		
		// Regresar el resultado de la validacion
		return ValidatorUtil.isSuccessfulResponse(response);
	}

	/**
	 * Metodo que elimina los registros de submenu asociados a una funcion
	 * @param rolFuncionSubmenuVOs
	 */
	private void cleanRolFuncionSubmenu(CreateRolFuncionSubmenuRequestVO createRolFuncionSubmenuRequestVO) {
		List<RolFuncionSubmenuDO> rolFuncionSubmenuDOs = rolFuncionSubmenuRepository
				.findByIdFuncion(createRolFuncionSubmenuRequestVO.getIdFuncion());

		if (!ValidatorUtil.isNullOrEmpty(rolFuncionSubmenuDOs)) {
			rolFuncionSubmenuRepository.deleteAll(rolFuncionSubmenuDOs);
		}
	}
	
	/*************************************************************************
	 * Metodo que busca un registro por su id, idNombre
	 * Regresa el objeto de la base de datos o una excepcion con el error
	 * 
	 *************************************************************************/
	public RolFuncionSubmenuDO existsRolFuncionSubmenu(Long idRolFuncionSubmenu, Long idFuncion, Long idSubmenu){

		RolFuncionSubmenuDO registro = null;
		try {
			
			if (ValidatorUtil.isNullOrZero(idRolFuncionSubmenu)) {
				//Validacion de datos de entrada
				if (ValidatorUtil.isNullOrZero(idFuncion) ||
					ValidatorUtil.isNullOrZero(idSubmenu)) {
						registro = null;
					} else {
						//Buscamos por nombre
						registro = rolFuncionSubmenuRepository.findByIdFuncionSubmenu(idFuncion, idSubmenu);
					}
				//Validacion de existencia
				if (ValidatorUtil.isNull(registro)) {
					//Genera error
					registro = null;
				}
			} else {
				registro = rolFuncionSubmenuRepository.findById(idRolFuncionSubmenu);
			}
		} catch (Exception e) {
			registro = null;
		}
		
		return registro;
	}

	@Override
	@Auditable
	public ResponseVO<Boolean> deleteRolFuncionSubmenu(RequestVO<DeleteRolFuncionSubmenuRequestVO> request) {
		// Declarar variables
				ResponseVO<Boolean> response = new ResponseVO<>();

				// Validar campos de entrada
				if (validateParametersDeleteRolFuncionSubmenu(request, response)) {

					RolFuncionSubmenuDO registroDO = rolFuncionSubmenuRepository.findById(request.getParameters().getId());
					
					if (ValidatorUtil.isNull(registroDO)) {
						ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_ERROR, request);
					}
					else {
						// Actualizar la BB.DD.
						rolFuncionSubmenuRepository.delete(registroDO);
			
						// Preparar respuesta y objeto eliminado
						response.setSuccess(Boolean.TRUE);
						response.setData(Boolean.TRUE);
					}
				}
				return response;
	}
	
	/**
	 * Valida que los parametros para la operacion de eliminacion sean correctos
	 * 
	 * @param request  Objeto con los parametros de entrada
	 * @param response Respuesta donde se agregan los errores
	 * @return true si todos los parametros son correctos
	 */
	private boolean validateParametersDeleteRolFuncionSubmenu(RequestVO<DeleteRolFuncionSubmenuRequestVO> request, ResponseVO<Boolean> response) {

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

	@Override
	@Auditable
	public ResponseVO<List<FindListRolFuncionSubmenuResponseVO>> findListRolFuncionSubmenu(
			RequestVO<FindListRolFuncionSubmenuRequestVO> request) {
		// declaracion de varables
		ResponseVO<List<FindListRolFuncionSubmenuResponseVO>> response = new ResponseVO<>();
		
		Page<RolFuncionSubmenuDO> lista = null;
		
		if (validateParametersFindByListRolFuncionSubmenu(request, response)) {
	
			FindListRolFuncionSubmenuRequestVO parameters = request.getParameters();
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
			lista = rolFuncionSubmenuRepository.findList(parameters.getIdFuncion(), parameters.getIdSubmenu(), pageable);

			// Si no se encontro ningun registro que cumpla la condicion generar error.
			if (ValidatorUtil.isNullOrEmpty(lista.getContent())) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_REGISTER_LIST_ERROR);
			} else {
				// Regresar la respuesta correcta con los registros obtenidos.
				response.setSuccess(true);
				response.setTotalRows(lista.getTotalElements());
				response.setData(transformListRolFuncionSubmenuDO(lista.getContent()));
			}
		}
		return response;
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
	private boolean validateParametersFindByListRolFuncionSubmenu(RequestVO<FindListRolFuncionSubmenuRequestVO> request,
			ResponseVO<List<FindListRolFuncionSubmenuResponseVO>> response) {
		
		
		if (ValidatorUtil.isNullOrZero(request.getParameters().getIdFuncion()) &&
				ValidatorUtil.isNullOrZero(request.getParameters().getIdSubmenu())) {
		
			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_PARAMETERS);			

		} else {
			
			// Validar campos obligatorios
		    ValidatorArqUtil.validateParameters(request, response);
			
			// validar los parametros de la paginacion
		    ValidatorArqUtil.validatePaginatonParameters(request, response);
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
	private List<FindListRolFuncionSubmenuResponseVO> transformListRolFuncionSubmenuDO(List<RolFuncionSubmenuDO> lista) {

		// Declarar variables
		List<FindListRolFuncionSubmenuResponseVO> listaVO = new ArrayList<>();

		// recorrer el objeto origen
		for (RolFuncionSubmenuDO rolFuncionSubmenuDO : lista) {
			// Se hace la declaracion de variables necesarias
			FindListRolFuncionSubmenuResponseVO rolFuncionSubmenuVO = new FindListRolFuncionSubmenuResponseVO();
			
			rolFuncionSubmenuVO.setId(rolFuncionSubmenuDO.getId());
			rolFuncionSubmenuVO.setIdFuncion(rolFuncionSubmenuDO.getFuncion().getId());
			rolFuncionSubmenuVO.setIdNombreSubmenu(rolFuncionSubmenuDO.getSubmenu().getIdNombre());
			rolFuncionSubmenuVO.setIdSubmenu(rolFuncionSubmenuDO.getSubmenu().getId());
			
			listaVO.add(rolFuncionSubmenuVO);
		}

		return listaVO;
	}

    
	
	/**
	 * Consulta la base de datos en base a varios parametros
	 * 
	 * @return ReponseVO con los datos encontrados
	 * 
	 * @param request Objeto con parametros de entrada de banner
	 */
	@Auditable
	public ResponseVO<List<FindListRolResponseVO>> findInternalListRol(RequestVO<FindListRolRequestVO> request) {
	    // Declaración de variables
	    ResponseVO<List<FindListRolResponseVO>> response = new ResponseVO<>();

	    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<RolDO> criteriaQuery = criteriaBuilder.createQuery(RolDO.class);
	    Root<RolDO> root = criteriaQuery.from(RolDO.class);
	    
	    Predicate predicate = criteriaBuilder.equal(root.get("active"), 1);

	    FindListRolRequestVO parameters = request.getParameters();

	    if (parameters != null) {
	        if (parameters.getIdAplicacion() != null) {
	            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("aplicacion").get("id"), parameters.getIdAplicacion()));
	        }

	        if (parameters.getIdNombre() != null) {
	            String idNombrePattern = "%" + parameters.getIdNombre().toUpperCase().replace("áéíóú", "aeiou") + "%";
	            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.upper(root.get("idNombre")), idNombrePattern));
	        }

	        if (parameters.getIdUsuario() != null) {
	            Subquery<RolDO> subquery = criteriaQuery.subquery(RolDO.class);
	            Root<RelUsuarioInternoRolDO> subqueryRoot = subquery.from(RelUsuarioInternoRolDO.class);
	            subquery.select(subqueryRoot.get("rol"));
	            Predicate subqueryPredicate = criteriaBuilder.and(
	                criteriaBuilder.equal(subqueryRoot.get("usuario").get("id"), parameters.getIdUsuario()),
	                criteriaBuilder.equal(subqueryRoot.get("active"), 1)
	            );
	            subquery.where(subqueryPredicate);
	            predicate = criteriaBuilder.and(predicate, criteriaBuilder.in(root).value(subquery));
	        }
	    }

	    criteriaQuery.select(root)
	        .distinct(true)
	        .where(predicate);

	    String orderby = request.getOrderBy();
	    String ordertype = request.getOrderType();
	    String orderBy = ValidatorUtil.isNullOrEmpty(orderby) ? "id" : orderby;
	    Direction orderType = ValidatorUtil.isNullOrEmpty(ordertype) || ordertype.equals("asc") ? Direction.ASC : Direction.DESC;
	    Integer size = ValidatorUtil.isNullOrZero(request.getSize()) ? 10 : request.getSize();
	    Integer page = ValidatorUtil.isNullOrZero(request.getPage()) ? 1 : request.getPage();
	    Pageable pageable = PageRequest.of(page - 1, size, Sort.by(orderType, orderBy));

	    TypedQuery<RolDO> typedQuery = entityManager.createQuery(criteriaQuery);
	    typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
	    typedQuery.setMaxResults(pageable.getPageSize());
	    List<RolDO> resultList = typedQuery.getResultList();
	    Page<RolDO> lista = new PageImpl<>(resultList, pageable, resultList.size());

	   


	    if (ValidatorUtil.isNullOrEmpty(lista.getContent())) {
	        ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_REGISTER_LIST_ERROR);
	    } else {
	        response.setSuccess(true);
	        response.setTotalRows(lista.getTotalElements());
	        response.setData(transformListRolDO(lista.getContent()));
	    }

	    return response;
	}


}
