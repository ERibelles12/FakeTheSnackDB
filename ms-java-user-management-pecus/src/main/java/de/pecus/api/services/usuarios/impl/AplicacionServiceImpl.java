package de.pecus.api.services.usuarios.impl;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.pecus.api.annotation.Auditable;
import de.pecus.api.constant.DataConstants;
import de.pecus.api.constant.UsuariosDataConstants;
import de.pecus.api.entities.AplicacionDO;
import de.pecus.api.entities.FuncionDO;
import de.pecus.api.entities.RelAplicacionAdDO;
import de.pecus.api.error.FuncionesBusinessError;
import de.pecus.api.error.GeneralBusinessErrors;
import de.pecus.api.error.UsuarioBusinessErrors;
import de.pecus.api.repositories.funciones.AplicacionRepository;
import de.pecus.api.repositories.funciones.FuncionRepository;
import de.pecus.api.repositories.usuarios.RelAplicacionAdRepository;
import de.pecus.api.services.usuarios.AplicacionService;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.util.ServiceUtil;
import de.pecus.api.util.StringUtil;
import de.pecus.api.util.ValidatorArqUtil;
import de.pecus.api.util.ValidatorUtil;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.funciones.CreateAplicacionRequestVO;
import de.pecus.api.vo.funciones.DeleteAplicacionRequestVO;
import de.pecus.api.vo.funciones.FindDetailAplicacionRequestVO;
import de.pecus.api.vo.funciones.FindDetailAplicacionResponseVO;
import de.pecus.api.vo.funciones.FindListAplicacionRequestVO;
import de.pecus.api.vo.funciones.FindListAplicacionResponseVO;
import de.pecus.api.vo.funciones.UpdateAplicacionRequestVO;

/**
 * Clase de logica de negocio para administracion de aplicaciones
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
@Service
public class AplicacionServiceImpl implements AplicacionService {

	@Autowired
	private AplicacionRepository aplicacionRepository;

	@Autowired
	private FuncionRepository funcionRepository;
	
	@Autowired
	private RelAplicacionAdRepository relAplicacionAdRepository;

	/**
	 * Crea un nuevo registro de aplicacion
	 * 
	 * @param request Objeto con parametros de entrada de aplicacion
	 * 
	 * @return Id generado
	 */
	@Transactional
	@Auditable
	public ResponseVO<Long> create(RequestVO<CreateAplicacionRequestVO> request) {

		// Declarar variables
		ResponseVO<Long> response = new ResponseVO<>();
		
		// Validar los parametros de entrada
		if (validateParametersCreate(request, response)) {
		
			// Preparar los datos para actualizar la BB.DD.
			de.pecus.api.entities.AplicacionDO aplicacionDO = new AplicacionDO();
				
			aplicacionDO.setIdNombre(request.getParameters().getIdNombre());
			aplicacionDO.setTipoAutenticacion(request.getParameters().getTipoAutenticacion());
				
			// Actualizar los parametros de auditoria
			ServiceUtil.setAuditFields(aplicacionDO, request.getToken());

			// Insertar el registro
			aplicacionDO = aplicacionRepository.saveAndFlush(aplicacionDO);
			
			// Validamos si la aplicaicon que se creara autentica por AD
			if (UsuariosDataConstants.AUTHENTICATION_TYPE_AZURE_AD.equals(aplicacionDO.getTipoAutenticacion())) {
				// Guardamos la configuración de AD en la BD de seguridad
				RelAplicacionAdDO relAplicacionAdDO = new RelAplicacionAdDO();
				relAplicacionAdDO.setAzureAppScope(request.getParameters().getAzureAppScope());
				relAplicacionAdDO.setAzureAppTenantDns(request.getParameters().getAzureAppTenantDns());
				relAplicacionAdDO.setAzureClientId(request.getParameters().getAzureClientId());
				relAplicacionAdDO.setAzureClientSecret(request.getParameters().getAzureClientSecret());
				relAplicacionAdDO.setAplicacion(aplicacionDO);
				
				// Actualizar los parametros de auditoria
				ServiceUtil.setAuditFields(relAplicacionAdDO, request.getToken());
				
				relAplicacionAdRepository.saveAndFlush(relAplicacionAdDO);
			}

			// Regresar la respuesta correcta y el objeto a regresar
			response.setSuccess(true);
			response.setData(aplicacionDO.getId());
				
		}
		return response;
	}

	/**
	 * Actualiza un registro de aplicacion
	 * 
	 * @param request Objeto con parametros de entrada de aplicacion
	 * 
	 * @return Id actualizado
	 */
	@Transactional
	@Auditable
	public ResponseVO<Long> update(RequestVO<UpdateAplicacionRequestVO> request) {

		// Declarar variables
		ResponseVO<Long> response = new ResponseVO<>();
		AplicacionDO registroDO = new AplicacionDO();

		// Validar los campos de entrada
		if (validateParametersUpdate(request, response)) {
			
			UpdateAplicacionRequestVO parameters = request.getParameters();

			registroDO.setId(parameters.getId());
			registroDO.setIdNombre(parameters.getIdNombre());
			registroDO.setTipoAutenticacion(parameters.getTipoAutenticacion());
			
			// Actualizar parametros de auditoria
			ServiceUtil.setAuditFields(registroDO, request.getToken());

			// Actualizar el registro en BB.DD.
			registroDO = aplicacionRepository.saveAndFlush(registroDO);
			
			// Validamos si la aplicaicon que se creara autentica por AD
			if (UsuariosDataConstants.AUTHENTICATION_TYPE_AZURE_AD.equals(registroDO.getTipoAutenticacion())) {
				RelAplicacionAdDO relAplicacionAdDO = null;
				
				if(!ValidatorUtil.isNullOrZero(parameters.getIdRelAplicacionAd())) {
					//Buscamos si existe una configuracion
					Optional<RelAplicacionAdDO> relApExist = relAplicacionAdRepository.findById(parameters.getIdRelAplicacionAd());
					
					if(!ValidatorUtil.isNull(relApExist) && !ValidatorUtil.isNull(relApExist.get())) {
						// Actualizamos la configuración de AD en la BD de seguridad
						relAplicacionAdDO = relApExist.get();
					} else {
						relAplicacionAdDO = new RelAplicacionAdDO();
					}					
				}else {
					relAplicacionAdDO = new RelAplicacionAdDO();
				}
				
				relAplicacionAdDO.setAplicacion(registroDO);
				relAplicacionAdDO.setAzureAppScope(request.getParameters().getAzureAppScope());
				relAplicacionAdDO.setAzureAppTenantDns(request.getParameters().getAzureAppTenantDns());
				relAplicacionAdDO.setAzureClientId(request.getParameters().getAzureClientId());
				relAplicacionAdDO.setAzureClientSecret(request.getParameters().getAzureClientSecret());
				
				// Actualizar parametros de auditoria
				ServiceUtil.setAuditFields(relAplicacionAdDO, request.getToken());
				
				relAplicacionAdRepository.saveAndFlush(relAplicacionAdDO);
			}

			// Preparar respuesta y objeto actualizado
			response.setSuccess(Boolean.TRUE);
			response.setData(registroDO.getId());
		}
		return response;
	}

	/**
	 * Marca un registro como eliminado un registro de aplicacion
	 * 
	 * @param request Objeto con parametros de entrada de aplicacion
	 * 
	 * @return Id eliminado
	 */
	@Transactional
	@Auditable
	public ResponseVO<Boolean> delete(RequestVO<DeleteAplicacionRequestVO> request) {

		// Declarar variables
		ResponseVO<Boolean> response = new ResponseVO<>();

		// Validar campos de entrada
		if (validateParametersDelete(request, response)) {

			AplicacionDO aplicacionDO = this.exists(request.getParameters().getId(), null);
			if (ValidatorUtil.isNull(aplicacionDO)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_ERROR, request);
			} else {
				// Actualizar la informacion
				ServiceUtil.setDisabledEntity(aplicacionDO, request.getToken());
			
				// Actualizar la BB.DD.
				aplicacionDO = aplicacionRepository.saveAndFlush(aplicacionDO);

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
	public ResponseVO<FindDetailAplicacionResponseVO> findDetail(RequestVO<FindDetailAplicacionRequestVO> request) {

		// declaracion de varables
		ResponseVO<FindDetailAplicacionResponseVO> response = new ResponseVO<>();
		FindDetailAplicacionResponseVO salida = new FindDetailAplicacionResponseVO();
		
		// validar que se cumplen las condiciones para realizar la consulta
		if (validateParametersFindDetail(request, response)) {

			AplicacionDO aplicacionDO = this.exists(request.getParameters().getId(),
					request.getParameters().getIdNombre());

			if (ValidatorUtil.isNull(aplicacionDO)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_ERROR, request);
			} else {
				salida.setId(aplicacionDO.getId());
				salida.setIdNombre(aplicacionDO.getIdNombre());
				salida.setTipoAutenticacion(aplicacionDO.getTipoAutenticacion());
				if (UsuariosDataConstants.AUTHENTICATION_TYPE_AZURE_AD.equals(aplicacionDO.getTipoAutenticacion())) {
					RelAplicacionAdDO relAplicacionAdDO = relAplicacionAdRepository.findByIdAplicacion(salida.getId());
					salida.setAzureAppScope(relAplicacionAdDO.getAzureAppScope());
					salida.setAzureAppTenantDns(relAplicacionAdDO.getAzureAppTenantDns());
					salida.setAzureClientId(relAplicacionAdDO.getAzureClientId());
					salida.setAzureClientSecret(relAplicacionAdDO.getAzureClientSecret());
					salida.setIdRelAplicacionAd(relAplicacionAdDO.getId());
				}

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
	public ResponseVO<List<FindListAplicacionResponseVO>> findList(RequestVO<FindListAplicacionRequestVO> request) {

		// declaracion de varables
		ResponseVO<List<FindListAplicacionResponseVO>> response = new ResponseVO<>();
		
		Page<AplicacionDO> listaAplicacion = null;
		
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
			
			String normalizedIdNombre = this.limpiarAcentos(request.getParameters().getIdNombre());
			
			// ejecucion de la busqueda por el parametro recibido
			listaAplicacion = aplicacionRepository.findList(this.cleanString(normalizedIdNombre), pageable);

			// Si no se encontro ningun registro que cumpla la condicion generar error.
			if (ValidatorUtil.isNullOrEmpty(listaAplicacion.getContent())) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_REGISTER_LIST_ERROR);
			} else {
				// Regresar la respuesta correcta con los registros obtenidos.
				response.setSuccess(true);
				response.setTotalRows(listaAplicacion.getTotalElements());
				response.setData(transformListDO(listaAplicacion.getContent()));
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
	private boolean validateParametersCreate(RequestVO<CreateAplicacionRequestVO> request, ResponseVO<Long> response) {
		
		// Obtener los parametros de entrada
		CreateAplicacionRequestVO parameters = request.getParameters();

		// Validaciones de campos obligatorios
		if (StringUtil.isNullOrEmpty(parameters.getIdNombre())) {
			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_ID_NOMBRE_ERROR, request);
		} else {
			// Validacion de tamano
			String idNombre = StringUtil.substring(parameters.getIdNombre(), DataConstants.MAX_SIZE_ID_NOMBRE);

			// Validacion de formato
			parameters.setIdNombre(StringUtil.toUpperCase(idNombre));

			AplicacionDO registroB = this.exists(null, parameters.getIdNombre());
				
			if (!ValidatorUtil.isNull(registroB)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.DUPLICATED_ERROR, request);
			}
		}
		
		// Validaciones de campos obligatorios
		if (StringUtil.isNullOrEmpty(parameters.getTipoAutenticacion())) {
			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_TIPO_AUTENTICACION_ERROR, request);
		} else {
			// Validacion de tamano
			parameters.setTipoAutenticacion(
					StringUtil.substring(parameters.getTipoAutenticacion(), DataConstants.MAX_SIZE_DESCRIPCION));
			
			if (UsuariosDataConstants.AUTHENTICATION_TYPE_AZURE_AD.equals(parameters.getTipoAutenticacion())) {
				if (ValidatorUtil.isNullOrEmpty(parameters.getAzureAppScope())) {
					ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_AZURE_APP_SCOPE_ERROR);
				}
				
				if (ValidatorUtil.isNullOrEmpty(parameters.getAzureAppTenantDns())) {
					ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_AZURE_APP_TENANT_DNS_ERROR);
				}
				
				if (ValidatorUtil.isNullOrEmpty(parameters.getAzureClientId())) {
					ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_AZURE_CLIENT_ID_ERROR);
				}
				
				if (ValidatorUtil.isNullOrEmpty(parameters.getAzureClientSecret())) {
					ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_AZURE_CLIENT_SECRET_ERROR);
				}				
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
	private boolean validateParametersUpdate(RequestVO<UpdateAplicacionRequestVO> request, ResponseVO<Long> response) {
		// Recuperar parametros de entrada
		UpdateAplicacionRequestVO parameters = request.getParameters();
		AplicacionDO registroUpdate = new AplicacionDO();
		
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
			
			registroUpdate = this.exists(parameters.getId(), null);
			
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
			
			// Validar la posible duplicidad del idNombre
			AplicacionDO aplicacionBusqueda = this.exists(null, request.getParameters().getIdNombre());
				
			if (!ValidatorUtil.isNull(aplicacionBusqueda)) {
				// Si se encuentra el registro validamos que no sea el mismo Id
				if (registroUpdate.getId() != aplicacionBusqueda.getId()) {
					ResponseUtil.addError(request, response, FuncionesBusinessError.DUPLICATED_ERROR, request);
						
				}
			}
		} else {
			parameters.setIdNombre(registroUpdate.getIdNombre());
		}
		
		// Validaciones de campos obligatorios: DESCRIPCION
		if (!StringUtil.isNullOrEmpty(parameters.getTipoAutenticacion())) {
			// Validacion de tamano
			parameters.setTipoAutenticacion(
					StringUtil.substring(parameters.getTipoAutenticacion(), DataConstants.MAX_SIZE_DESCRIPCION));

		} else {
			parameters.setTipoAutenticacion(registroUpdate.getTipoAutenticacion());
		}
		
		if (!ValidatorUtil.isNullOrZero(parameters.getIdRelAplicacionAd())) {
			if (ValidatorUtil.isNullOrEmpty(parameters.getAzureAppScope())) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_AZURE_APP_SCOPE_ERROR);
			}

			if (ValidatorUtil.isNullOrEmpty(parameters.getAzureAppTenantDns())) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_AZURE_APP_TENANT_DNS_ERROR);
			}

			if (ValidatorUtil.isNullOrEmpty(parameters.getAzureClientId())) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_AZURE_CLIENT_ID_ERROR);
			}

			if (ValidatorUtil.isNullOrEmpty(parameters.getAzureClientSecret())) {
				ResponseUtil.addError(request, response, UsuarioBusinessErrors.REQUIRED_AZURE_CLIENT_SECRET_ERROR);
			}
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
	private boolean validateParametersDelete(RequestVO<DeleteAplicacionRequestVO> request,
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
	private boolean validateParametersFindDetail(RequestVO<FindDetailAplicacionRequestVO> request,
			ResponseVO<FindDetailAplicacionResponseVO> response) {

		// Recuperar los parametros de entrada
		FindDetailAplicacionRequestVO parameters = request.getParameters();

		// validar que el campo obligatorio
		if (ValidatorUtil.isNullOrZero(parameters.getId())) {
			
			// Buscar por criterio: IdNombre
			if (ValidatorUtil.isNullOrEmpty(parameters.getIdNombre())) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_ID_ERROR);
			} 
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
	private boolean validateParametersFindByList(RequestVO<FindListAplicacionRequestVO> request,
			ResponseVO<List<FindListAplicacionResponseVO>> response) {
		
		// Validar campos obligatorios
		ValidatorArqUtil.validateParameters(request, response);
		
		// validar los parametros de la paginacion
		ValidatorArqUtil.validatePaginatonParameters(request, response);
		return ValidatorUtil.isSuccessfulResponse(response);

	}

	/**
	 * Obtiene una lista de objetos aplicacionVO a partir de una lista de DO
	 * 
	 * @return Lista VO para retorno de resultados
	 * 
	 * @param listaAplicacion a transformar
	 */
	private List<FindListAplicacionResponseVO> transformListDO(List<AplicacionDO> listaAplicacion) {

		// Declarar variables
		List<FindListAplicacionResponseVO> listaAplicacionVO = new ArrayList<>();

		// recorrer el objeto origen
		for (AplicacionDO aplicacionDO : listaAplicacion) {
			// Se hace la declaracion de variables necesarias
			FindListAplicacionResponseVO aplicacionVO = new FindListAplicacionResponseVO();
			
			aplicacionVO.setId(aplicacionDO.getId());
			aplicacionVO.setIdNombre(aplicacionDO.getIdNombre());
			aplicacionVO.setTipoAutenticacion(aplicacionDO.getTipoAutenticacion());
			
			if (UsuariosDataConstants.AUTHENTICATION_TYPE_AZURE_AD.equals(aplicacionDO.getTipoAutenticacion())) {
				RelAplicacionAdDO relAplicacionAdDO = relAplicacionAdRepository.findByIdAplicacion(aplicacionDO.getId());
				if(!ValidatorUtil.isNull(relAplicacionAdDO)) {
					aplicacionVO.setAzureAppScope(relAplicacionAdDO.getAzureAppScope());
					aplicacionVO.setAzureAppTenantDns(relAplicacionAdDO.getAzureAppTenantDns());
					aplicacionVO.setAzureClientId(relAplicacionAdDO.getAzureClientId());
					aplicacionVO.setAzureClientSecret(relAplicacionAdDO.getAzureClientSecret());
					aplicacionVO.setIdRelAplicacionAd(relAplicacionAdDO.getId());					
				}
			}
			
			listaAplicacionVO.add(aplicacionVO);
		}

		return listaAplicacionVO;
	}
	
	/*************************************************************************
	 * Metodo que busca un registro por su id, idNombre Regresa el objeto de la base
	 * de datos o una excepcion con el error
	 * 
	 *************************************************************************/
	public AplicacionDO exists(Long id, String idNombre) {

		AplicacionDO registro = null;
		try {
			// Validacion de datos de entrada
			if (ValidatorUtil.isNullOrZero(id)) {
				if (ValidatorUtil.isNullOrEmpty(idNombre)) {
					registro = null;
				} else {
					// Buscamos por nombre
					registro = aplicacionRepository.findByIdNombre(idNombre);
				}
			} else {
				// Consulta
				registro = aplicacionRepository.findById(id);
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

	public String cleanString(String strInput) {
		if (!ValidatorUtil.isNullOrEmpty(strInput)) {
			strInput = strInput.trim().toUpperCase();
		}
		return strInput; 
	}
	
	public String limpiarAcentos(String cadena) {
		String limpio = null;
		if (cadena != null) {
			String valor = cadena;
			valor = valor.toUpperCase();
			// Normalizar texto para eliminar acentos, dieresis, cedillas y tildes
			limpio = Normalizer.normalize(valor, Normalizer.Form.NFD);
			// Quitar caracteres no ASCII excepto la enie, interrogacion que abre,
			// exclamacion que abre, grados, U con dieresis.
			limpio = limpio.replaceAll("[^\\p{ASCII}(N\u0303)(n\u0303)(\u00A1)(\u00BF)(\u00B0)(U\u0308)(u\u0308)]", "");
			// Regresar a la forma compuesta, para poder comparar la enie con la tabla de
			// valores
			limpio = Normalizer.normalize(limpio, Normalizer.Form.NFC);
		}
		return limpio;
	}
	
	
}
