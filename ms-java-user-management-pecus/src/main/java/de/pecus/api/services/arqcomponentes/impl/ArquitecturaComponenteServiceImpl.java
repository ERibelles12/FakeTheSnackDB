package de.pecus.api.services.arqcomponentes.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import de.pecus.api.entities.ArqComponenteDO;
import de.pecus.api.enums.WildcardTypeEnum;
import de.pecus.api.enums.arqcomponentes.ArqEstadoComponenteEnum;
import de.pecus.api.error.ComponentesBusinessErrors;
import de.pecus.api.error.GeneralBusinessErrors;
import de.pecus.api.repositories.arqcomponentes.ArquitecturaComponenteRepository;
import de.pecus.api.services.arqcomponentes.ArquitecturaComponenteService;
import de.pecus.api.util.CriteriaUtil;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.util.ServiceUtil;
import de.pecus.api.util.ValidatorArqUtil;
import de.pecus.api.util.ValidatorUtil;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.arqcomponentes.FindListArqByTypeComponenteRequestVO;
import de.pecus.api.vo.arqcomponentes.FindListArqComponenteResponseVO;

/**
 * * @author Juan Carlos Contreras
 *
 *
 * Descripcion: Esta libreria permite gestionar los diferentes componentes de
 * software de la arquitectura. Las funcionalidades principales son:
 * 
 * a) Catalogo de Tipo de Componentes: 1 Reportes, 2 Pantallas, 3 Colas, 4
 * Servidores b) Catalogo de los componentes: Inventario de Reportes, Pantallas,
 * Colas, Aplicaciones c) Metodos de acceso a Componentes. d) Funciones de
 * arquitectura (Reportes, Excel, Error)
 * 
 */

@Service
public class ArquitecturaComponenteServiceImpl implements ArquitecturaComponenteService {

    @Autowired
    private ArquitecturaComponenteRepository arquitecturaComponenteRepository;

    /****************************************************************
     * Consulta la base de datos en base a varios parametros
     * 
     * @return ReponseVO con los datos encontrados
     * 
     * @param request Objeto con parametros para realizar la busqueda
     *
     *******************************************************************/
    @Override
    public ResponseVO<List<FindListArqComponenteResponseVO>> findListByType(RequestVO<FindListArqByTypeComponenteRequestVO> request) {

    	
        // declaracion de varables
        ResponseVO<List<FindListArqComponenteResponseVO>> response = new ResponseVO<>();

        Page<ArqComponenteDO> listaArquitecturaComponenteDO = null;
        String orderBy = request.getOrderBy();
        String orderTypeCriteria = request.getOrderType();

        // validar que se cumplen las condiciones para realizar la consulta
        if (validateParametersFindListByList(request, response)) {

            orderBy = ValidatorUtil.isNullOrEmpty(orderBy) ? "idNombre" : orderBy;
            Direction orderType = ValidatorUtil.isNullOrEmpty(orderTypeCriteria) || orderTypeCriteria.equals("asc") ? Direction.ASC : Direction.DESC;

            // Crear el objeto Pageable pasando como parametro page, size y parametros de
            // ordenamiento.
            Pageable pageable = PageRequest.of(ServiceUtil.getRealPage(request), request.getSize(),
    				Sort.by(orderType, orderBy));

            String idNombre = null;
            Long idTipoComponente = null;
            String idNombreTipoComponente = null;

            // ejecucion de la busqueda por el parametro recibido
            idNombre = CriteriaUtil.validateNullLike(request.getParameters().getIdNombre(), WildcardTypeEnum.BOTH_SIDES);
            idTipoComponente = CriteriaUtil.validateNull(request.getParameters().getIdTipoArqComponente());
            idNombreTipoComponente = CriteriaUtil.validateNullLike(request.getParameters().getIdNombreTipoArqComponente(),  WildcardTypeEnum.BOTH_SIDES);

            listaArquitecturaComponenteDO = arquitecturaComponenteRepository.findLisByType(idNombre, idTipoComponente, idNombreTipoComponente , pageable);

            // Si no se encontro ningun registro que cumpla la condicion generar error.
            if (listaArquitecturaComponenteDO.getContent().isEmpty()) {
                ResponseUtil.addError(request, response, ComponentesBusinessErrors.NOT_FOUND_ARQ_COMPONENTE_ERROR, idNombre, idTipoComponente);
            } else {
                response.setTotalRows(listaArquitecturaComponenteDO.getTotalElements());
                response.setData(findByListTransformListDO(listaArquitecturaComponenteDO.getContent()));
                response.setSuccess(true);
            }
        }
        return response;
    }
    
    /*******************************************************************************
     * Valida que los parametros para la operacion de consulta por parametros sean
     * correctos
     * 
     * @return true si el nombre no esta vacio
     * 
     * @param request  Objeto con los criterios a buscar
     * @param response Respuesta donde se agregan los errores
     *******************************************************************************/
    private boolean validateParametersFindListByList(RequestVO<FindListArqByTypeComponenteRequestVO> request, ResponseVO<List<FindListArqComponenteResponseVO>> response) {

        // ****************************************************
        // Validar campos obligatorios
        // ****************************************************
        if (ValidatorUtil.isNull(request.getParameters())) {
            ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR, request);
            return false;
        }

        // ****************************************************
        // validar los parametros de la paginacion
        // ****************************************************
        ValidatorArqUtil.validatePaginatonParameters(request, response);
        return ValidatorUtil.isSuccessfulResponse(response);

    }
    
    /****************************************************************************
     * Obtiene una lista de objetos ArquitecturaComponenteVO a partir de una lista
     * de DO
     * 
     * @return Lista VO para retorno de resultados
     * 
     * @param Objeto DO a transformar
     *****************************************************************************/
    public List<FindListArqComponenteResponseVO> findByListTransformListDO(
            List<ArqComponenteDO> listaArquitecturaComponenteDO) {

        // ****************************************************
        // Declarar variables
        // ****************************************************
        List<FindListArqComponenteResponseVO> listaArquitecturaComponenteVO = new ArrayList<>();

        // ****************************************************
        // Recorrer el objeto origen
        // ****************************************************
        for (ArqComponenteDO arquitecturaComponenteDO : listaArquitecturaComponenteDO) {
            FindListArqComponenteResponseVO fondoVO = transformDO(arquitecturaComponenteDO);

            listaArquitecturaComponenteVO.add(fondoVO);
        }

        return listaArquitecturaComponenteVO;
    }
    
    /******************************************************************
     * Metodo que busca un registro individual
     * 
     * @param ArquitecturaComponenteDO Objeto DO a transformar
     * @return Objeto de transporte VO para retorno de resultados
     *****************************************************************/
    public FindListArqComponenteResponseVO transformDO(ArqComponenteDO arqComponenteDO) {

        // Declarar variables
        FindListArqComponenteResponseVO arqComponenteVO = new FindListArqComponenteResponseVO();

        // asignar los valores
        arqComponenteVO.setId(arqComponenteDO.getId());
        arqComponenteVO.setIdNombre(arqComponenteDO.getIdNombre());
        arqComponenteVO.setDescripcion(arqComponenteDO.getDescripcion());
        if (!ValidatorUtil.isNull(arqComponenteDO.getArqTipoComponente())) {
            arqComponenteVO.setIdArqTipoComponente(arqComponenteDO.getArqTipoComponente().getId());
            arqComponenteVO.setIdNombreArqTipoComponente(arqComponenteDO.getArqTipoComponente().getIdNombre());
        }
        arqComponenteVO.setIdEstatus(arqComponenteDO.getEstado());
        arqComponenteVO.setIdNombreEstatus(ArqEstadoComponenteEnum.descripcionId(arqComponenteDO.getEstado()));
        arqComponenteVO.setParam1(arqComponenteDO.getParam1());
        arqComponenteVO.setParam2(arqComponenteDO.getParam2());
        arqComponenteVO.setParam3(arqComponenteDO.getParam3());
        arqComponenteVO.setParam4(arqComponenteDO.getParam4());
        arqComponenteVO.setParam5(arqComponenteDO.getParam5());
        arqComponenteVO.setParam6(arqComponenteDO.getParam6());
        arqComponenteVO.setParam7(arqComponenteDO.getParam7());

        return arqComponenteVO;
    }
    
}
