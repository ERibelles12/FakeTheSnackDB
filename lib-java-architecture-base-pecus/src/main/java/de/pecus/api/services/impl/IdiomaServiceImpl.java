/**
 * 
 */
package de.pecus.api.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.entities.IdiomaDO;
import de.pecus.api.enums.WildcardTypeEnum;
import de.pecus.api.error.GeneralBusinessErrors;
import de.pecus.api.error.IdiomaBusinessErrors;
import de.pecus.api.exception.PecusException;
import de.pecus.api.security.repositories.IdiomaRepository;
import de.pecus.api.services.IdiomaService;
import de.pecus.api.util.CriteriaUtil;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.util.ServiceUtil;
import de.pecus.api.util.StringUtil;
import de.pecus.api.util.ValidatorArqUtil;
import de.pecus.api.util.ValidatorUtil;
import de.pecus.api.vo.CreateIdiomaRequestVO;
import de.pecus.api.vo.FindDetailIdiomaRequestVO;
import de.pecus.api.vo.FindDetailIdiomaResponseVO;
import de.pecus.api.vo.FindListIdiomaRequestVO;
import de.pecus.api.vo.FindListIdiomaResponseVO;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.UpdateIdiomaRequestVO;

/**
 * Clase de logica de negocio para administracion de Idiomas
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
@Service
public class IdiomaServiceImpl implements IdiomaService {

	@Autowired
	private IdiomaRepository idiomaRepository;

	/**
	 * Crea un nuevo registro de idioma
	 * 
	 * @param request Objeto con parametros de entrada de idioma
	 * 
	 * @return Id generado
	 */
	public ResponseVO<Long> create(RequestVO<CreateIdiomaRequestVO> request) {
		//Declarar variables
		ResponseVO<Long> response = new ResponseVO<>();

		//Validar parametros de entrada
		if (validateParametersCreate(request, response)) {

			IdiomaDO idiomaDO = new IdiomaDO();
			//Preparar registro
			idiomaDO.setDescripcion(request.getParameters().getDescripcion());
			idiomaDO.setIdNombre(request.getParameters().getIdNombre());

			// ServiceUtil.setLenguage(idiomaDO, request.getToken());
			
			ServiceUtil.setAuditFields(idiomaDO, request.getToken());

			idiomaDO = idiomaRepository.saveAndFlush(idiomaDO);

			response.setSuccess(true);
			response.setData(idiomaDO.getId());
		}
		return response;
	}

	/**
	 * Actualiza un registro de idioma
	 * 
	 * @param request Objeto con parametros de entrada de idioma
	 * 
	 * @return Id actualizado
	 */
	public ResponseVO<Long> update(RequestVO<UpdateIdiomaRequestVO> request) {
		//Declarar variables
		ResponseVO<Long> response = new ResponseVO<>();

		//Validar parametros de entradad
		if (validateParametersUpdate(request, response)) {

			IdiomaDO idiomaDO = exists(request.getParameters().getId(), null, request.getIdioma());
			
			//Preparar registro a insertar
			idiomaDO.setDescripcion(request.getParameters().getDescripcion());
			idiomaDO.setIdNombre(request.getParameters().getIdNombre());
			
			//Actualizar campos de auditoria
			ServiceUtil.setAuditFields(idiomaDO, request.getToken());
			
			//Insertar en Base de datos
			idiomaDO = idiomaRepository.saveAndFlush(idiomaDO);

			response.setSuccess(true);
			response.setData(idiomaDO.getId());
		}
		return response;
	}

	/**
	 * Marca un registro como eliminado un registro de idioma
	 * 
	 * @param request Objeto con parametros de entrada de idioma
	 * 
	 * @return Id eliminado
	 */
	public ResponseVO<Boolean> delete(RequestVO<Long> request) {
		//Declarar variables
		ResponseVO<Boolean> response = new ResponseVO<>();

		//Validar parametros de entrada
		if (validateParametersDelete(request, response)) {

			IdiomaDO idiomaDO = exists(request.getParameters(), null, request.getIdioma());
			//Actualizar atributos de auditoria
			ServiceUtil.setDisabledEntity(idiomaDO, request.getToken());

			//Actualizar base de datos
			idiomaDO = idiomaRepository.saveAndFlush(idiomaDO);

			response.setSuccess(true);
			response.setData(true);
		}
		return response;
	}

	/**
	 * Consulta un idioma por Id
	 * 
	 * @return Objeto VO con los datos encontrados
	 * @param Id      Identificador del registro a buscar
	 * 
	 * @param request Objeto con los datos de busqueda
	 */
	public ResponseVO<FindDetailIdiomaResponseVO> findDetail(RequestVO<FindDetailIdiomaRequestVO> request) {
		// declaracion de varables
		ResponseVO<FindDetailIdiomaResponseVO> response = new ResponseVO<>();

		// validar que se cumplen las condiciones para realizar la consulta
		if (validateParametersFindDetail(request, response)) {
			
			IdiomaDO idiomaDO = exists(request.getParameters().getId(), null, request.getIdioma());
			
			// regresar la respuesta correcta con los registros obtenidos.
			response.setSuccess(true);
			response.setData(transformDO(idiomaDO));
		}
		return response;
	}

	/**
	 * Consulta la base de datos en base a varios parametros
	 * 
	 * @return ReponseVO con los datos encontrados
	 * 
	 * @param request Objeto con parametros de entrada de idioma
	 */
	public ResponseVO<List<FindListIdiomaResponseVO>> findList(RequestVO<FindListIdiomaRequestVO> request) {
		// declaracion de varables
		ResponseVO<List<FindListIdiomaResponseVO>> response = new ResponseVO<>();

		Page<IdiomaDO> listaIdiomaDO = null;
		String orderbyCriteria = request.getOrderBy();
		String ordertypeCriteria = request.getOrderType();

		String orderBy = ValidatorUtil.isNullOrEmpty(orderbyCriteria) ? "idNombre" : orderbyCriteria;
		Direction orderType = ValidatorUtil.isNullOrEmpty(ordertypeCriteria) || ordertypeCriteria.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

		// validar que se cumplen las condiciones para realizar la consulta
		if (validateParametersFindList(request, response)) {

			// Crear el objeto Pageable pasando como parametro page, size y parametros de
			// ordenamiento.
			Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by(orderType, orderBy));
			
			String idNombre = request.getParameters().getIdNombre();
			// asignar % al parametro SearchQuery %text%
			
			String name = CriteriaUtil.validateNullLike(idNombre, WildcardTypeEnum.BOTH_SIDES);
			// ejecucion de la busqueda por el parametro recibido
			
			listaIdiomaDO = idiomaRepository.findList(name, pageable);
			// Si no se encontro ningun registro que cumpla la condicion generar error.
			if (listaIdiomaDO.getContent().isEmpty()) {
				ResponseUtil.addError(request, response, IdiomaBusinessErrors.NOT_FOUND_IDIOMA_ERROR);
			} else {

				// regresar la respuesta correcta con los registros obtenidos.
				response.setSuccess(true);
				response.setData(transformListDO(listaIdiomaDO.getContent()));
			}
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
	private boolean validateParametersCreate(RequestVO<CreateIdiomaRequestVO> request, ResponseVO<Long> response) {

		// Obtener parametros de entrada
		CreateIdiomaRequestVO parameters = request.getParameters();

		// Validar que se informaron los campos de entrada
		if (ValidatorUtil.isNull(parameters)) {
			// Si no se ha informado regresar el error y no seguir validando
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
			return false;
		}

		// Validaciones de campos obligatorios
		if (StringUtil.isNullOrEmpty(parameters.getIdNombre())) {
			ResponseUtil.addError(request, response, IdiomaBusinessErrors.REQUIRED_ID_NOMBRE_ERROR, request);
		} else {
			// Validacion de tamano maximo
			String idNombre = StringUtil.substring(parameters.getIdNombre(), GeneralConstants.MAX_SIZE_ID_NOMBRE);

			// Validacion de formato
			parameters.setIdNombre(StringUtil.toUpperCase(idNombre));

			// Se valida id nombre no repetido
			IdiomaDO idiomaDO = idiomaRepository.findByIdNombreAndActive(parameters.getIdNombre(), Boolean.TRUE);

			if (!ValidatorUtil.isNull(idiomaDO)) {
				ResponseUtil.addError(request, response, IdiomaBusinessErrors.DUPLICATED_IDIOMA_ERROR, request);
				return false;
			}

			// validacion del tamaño minimo
			if (!ValidatorUtil.minLength(idNombre, GeneralConstants.MIN_SIZE_ID_NOMBRE)) {
				ResponseUtil.addError(request, response, IdiomaBusinessErrors.MIN_SIZE_ID_NOMBRE_ERROR);
			}
		}

		// Validaciones de campos obligatorios
		if (StringUtil.isNullOrEmpty(parameters.getDescripcion())) {
			ResponseUtil.addError(request, response, IdiomaBusinessErrors.REQUIRED_DESCRIPCION_ERROR, request);
		} else {
			// Validacion de tamano
			String descripcion = StringUtil.substring(parameters.getDescripcion(), GeneralConstants.MAX_SIZE_DESCRIPCION);

			if (!ValidatorUtil.minLength(descripcion, GeneralConstants.MIN_SIZE_DESCRIPCION)) {
				ResponseUtil.addError(request, response, IdiomaBusinessErrors.MIN_SIZE_DESCRIPCION_ERROR);
			}
		}
		return ValidatorUtil.isSuccessfulResponse(response);
	}

	/**
	 * Valida que los parametros para la operacion de actualizacion sean correctos
	 * 
	 * @param request  Objeto con los parametros de entrada
	 * @param response Respuesta donde se agregan los errores
	 * @return true si todos los parametros son correctos
	 */
	private boolean validateParametersUpdate(RequestVO<UpdateIdiomaRequestVO> request, ResponseVO<Long> response) {
		// Obtener parametros de entrada
		UpdateIdiomaRequestVO parameters = request.getParameters();
		Long id = request.getParameters().getId();
		// Validar que se informaron los campos de entrada
		if (ValidatorUtil.isNull(parameters)) {
			// Si no se ha informado regresar el error y no seguir validando
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
			return false;
		}
		if (ValidatorUtil.isNull(id)) {
			ResponseUtil.addError(request, response, IdiomaBusinessErrors.REQUIRED_ID_ERROR);
			return false;
		}
		// Validaciones de campos obligatorios
		if (StringUtil.isNullOrEmpty(parameters.getIdNombre())) {
			ResponseUtil.addError(request, response, IdiomaBusinessErrors.REQUIRED_ID_NOMBRE_ERROR, request);
		} else {
			// Validacion de tamano maximo
			String idNombre = StringUtil.substring(parameters.getIdNombre(), GeneralConstants.MAX_SIZE_ID_NOMBRE);

			// Validacion de formato
			parameters.setIdNombre(StringUtil.toUpperCase(idNombre));

			// validacion del tamaño minimo
			if (!ValidatorUtil.minLength(idNombre, GeneralConstants.MIN_SIZE_ID_NOMBRE)) {
				ResponseUtil.addError(request, response, IdiomaBusinessErrors.MIN_SIZE_ID_NOMBRE_ERROR);
			}
		}

		// Validaciones de campos obligatorios
		if (StringUtil.isNullOrEmpty(parameters.getDescripcion())) {
			ResponseUtil.addError(request, response, IdiomaBusinessErrors.REQUIRED_DESCRIPCION_ERROR, request);
		} else {
			// Validacion de tamano
			String descripcion = StringUtil.substring(parameters.getDescripcion(), GeneralConstants.MAX_SIZE_DESCRIPCION);

			if (!ValidatorUtil.minLength(descripcion, GeneralConstants.MIN_SIZE_DESCRIPCION)) {
				ResponseUtil.addError(request, response, IdiomaBusinessErrors.MIN_SIZE_DESCRIPCION_ERROR);
			}
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
	private boolean validateParametersDelete(RequestVO<Long> request, ResponseVO<Boolean> response) {
		
		//Validar parametros de entrada
		if (ValidatorUtil.isNull(request.getParameters())) {
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
			return false;
		}
		//Validar parametro ID
		Long id = request.getParameters();
		if (ValidatorUtil.isNull(id)) {
			ResponseUtil.addError(request, response, IdiomaBusinessErrors.REQUIRED_ID_ERROR);
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

	private boolean validateParametersFindDetail(RequestVO<FindDetailIdiomaRequestVO> request, ResponseVO<FindDetailIdiomaResponseVO> response) {
		
		// validar que el campo obligatorio
		if (ValidatorUtil.isNull(request.getParameters())) {
			ResponseUtil.addError(request, response, IdiomaBusinessErrors.REQUIRED_ID_ERROR);
			return false;
		}

		//Validar campos obligatorios
		if (ValidatorUtil.isNull(request.getParameters().getId())) {
			ResponseUtil.addError(request, response, IdiomaBusinessErrors.REQUIRED_ID_ERROR);
		} else {
		
			//Buscar el registro
			IdiomaDO idiomaDO = idiomaRepository.findByIdAndActive(request.getParameters().getId(), Boolean.TRUE);
			
			//Validar respuesta
			if (ValidatorUtil.isNull(idiomaDO) || ValidatorUtil.isNull(idiomaDO.getId())) {
				ResponseUtil.addError(request, response, IdiomaBusinessErrors.NOT_FOUND_IDIOMA_ERROR);
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
	private boolean validateParametersFindList(RequestVO<FindListIdiomaRequestVO> request, ResponseVO<List<FindListIdiomaResponseVO>> response) {
		
		// Validar campos obligatorios
		if (ValidatorUtil.isNull(request.getParameters())) {
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR, request);
			return false;
		}
		
		// validar los parametros de la paginacion
		ValidatorArqUtil.validatePaginatonParameters(request, response);
		return ValidatorUtil.isSuccessfulResponse(response);

	}

	/**
	 * Obtiene un idiomaVO a partir de un DO
	 * 
	 * @param idiomaDO Objeto DO a transformar
	 * @return Objeto de transporte VO para retorno de resultados
	 */
	private FindDetailIdiomaResponseVO transformDO(IdiomaDO idiomaDO) {
		//Declarar variables
		FindDetailIdiomaResponseVO idiomaVO = new FindDetailIdiomaResponseVO();

		idiomaVO.setId(idiomaDO.getId());
		idiomaVO.setIdNombre(idiomaDO.getIdNombre());
		idiomaVO.setDescripcion(idiomaDO.getDescripcion());

		return idiomaVO;
	}

	/**
	 * Obtiene una lista de objetos idiomaVO a partir de una lista de DO
	 * 
	 * @return Lista VO para retorno de resultados
	 * 
	 * @param listaIdiomaDO a transformar
	 */
	private List<FindListIdiomaResponseVO> transformListDO(List<IdiomaDO> listaIdiomaDO) {
		//Declarar variables
		List<FindListIdiomaResponseVO> listaIdiomaVO = new ArrayList<>();

		// recorrer el objeto origen
		for (IdiomaDO idiomaDO : listaIdiomaDO) {
			FindListIdiomaResponseVO idiomaVO = new FindListIdiomaResponseVO();
			// asignar los valores
			idiomaVO.setId(idiomaDO.getId());
			idiomaVO.setIdNombre(idiomaDO.getIdNombre());
			idiomaVO.setDescripcion(idiomaDO.getDescripcion());
			
			listaIdiomaVO.add(idiomaVO);
		}

		return listaIdiomaVO;
	}
    
    /**
     * Metodo encargado de validar la existencia del idioma por su identificador
     * 
     * @param idIdioma Id del idioma
     * @param idNombre Id del idioma
     * @param idioma Idioma enviado para consulta
     * 
     * @return Objeto DO con los datos de la entidad encontrada
     * @throws PecusException con objeto BusinessError, en caso de no encontrar el registro
     */
    public IdiomaDO exists(Long idIdioma, String idNombre, String idioma) {

        //Validacion de datos de entrada
        if (ValidatorUtil.isNullOrZero(idIdioma) && ValidatorUtil.isNullOrEmpty(idNombre)) {
            throw new PecusException(IdiomaBusinessErrors.REQUIRED_IDIOMA_ERROR, idioma);
        } 
        //Consulta
        IdiomaDO idiomaDO = null;
        if(!ValidatorUtil.isNull(idIdioma)) {
        	idiomaDO = idiomaRepository.findByIdAndActive(idIdioma, Boolean.TRUE);
        } else {
        	idiomaDO = idiomaRepository.findByIdNombreAndActive(idNombre, Boolean.TRUE);
        }

        //Validacion de existencia
        if (ValidatorUtil.isNull(idiomaDO)) {
            //Genera error
            throw new PecusException(IdiomaBusinessErrors.NOT_FOUND_IDIOMA_ERROR, idioma);
        }
        return idiomaDO;
    }

}
