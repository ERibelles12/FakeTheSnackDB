package de.pecus.api.services.usuarios.impl;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import de.pecus.api.entities.*;
import de.pecus.api.repositories.usuarios.*;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import de.pecus.api.constant.DataConstants;
import de.pecus.api.enums.WildcardTypeEnum;
import de.pecus.api.error.FuncionesBusinessError;
import de.pecus.api.error.GeneralBusinessErrors;
import de.pecus.api.services.usuarios.EvaluationService;
import de.pecus.api.util.CriteriaUtil;
import de.pecus.api.util.ResponseUtil;
import de.pecus.api.util.ServiceUtil;
import de.pecus.api.util.StringUtil;
import de.pecus.api.util.ValidatorArqUtil;
import de.pecus.api.util.ValidatorUtil;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.evaluation.CreateEvaluationRequestVO;
import de.pecus.api.vo.evaluation.FindDetailEvaluationRequestVO;
import de.pecus.api.vo.evaluation.FindDetailEvaluationResponseVO;
import de.pecus.api.vo.evaluation.FindListEvaluationRequestVO;
import de.pecus.api.vo.evaluation.FindListEvaluationResponseVO;

/**
 * Clase de logica de negocio para administracion de evaluationes
 * 
 * @author Proa
 *
 */
@Service
public class EvaluationServiceImpl implements EvaluationService {

	@Autowired
	private EvaluationRepository evaluationRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private ResultItemRepository resultItemRepository;

	/**
	 * Crea un nuevo registro de evaluation
	 * 
	 * @param request Objeto con parametros de entrada de evaluation
	 * 
	 * @return Id generado
	 */
	public ResponseVO<Long> create(RequestVO<CreateEvaluationRequestVO> request) {

		// Declarar variables
		ResponseVO<Long> response = new ResponseVO<>();
		BrandDO	brandDO = new BrandDO();
		CategoryDO categoryDO = new CategoryDO();
		SubCategoryDO subCategoryDO = new SubCategoryDO();
		ProductDO productDO = new ProductDO();
		IngredientDO ingredientDO = new IngredientDO();
		RecipeDO recipeDO = new RecipeDO();

		
			// Validar los parametros de entrada
			if (validateParametersCreate(request, response)) {
		
				// Preparar los datos para actualizar la BB.DD.
				EvaluationDO evaluationDO = new EvaluationDO();

				brandDO.setId(1L);
				categoryDO.setId(1L);
				subCategoryDO.setId(1L);
				productDO.setId(request.getParameters().getProductId());
				ingredientDO.setId(request.getParameters().getIngredientId());
				recipeDO.setId(request.getParameters().getRecipeId());

				evaluationDO.setBrand(brandDO);
				evaluationDO.setCategory(categoryDO);
				evaluationDO.setSubCategory(subCategoryDO);
				evaluationDO.setProduct(productDO);
				evaluationDO.setIngredient(ingredientDO);
				evaluationDO.setRecipe(recipeDO);
				evaluationDO.setEvaluationDate(request.getParameters().getEvaluationDate());
				evaluationDO.setIngredientPercentage(request.getParameters().getIngredientPercentaje());

				// Actualizar los parametros de auditoria
				ServiceUtil.setAuditFields(evaluationDO, request.getToken());

				// Insertar el registro
				evaluationDO = evaluationRepository.saveAndFlush(evaluationDO);

				// Regresar la respuesta correcta y el objeto a regresar
				response.setSuccess(true);
				response.setData(evaluationDO.getId());
				
			}
		return response;
	}


	/**
	 * Consulta un evaluation por Identificador unico
	 * 
	 * @return Objeto VO con los datos encontrados
	 *
	 * @param request Objeto con los datos de busqueda
	 */
	public ResponseVO<FindDetailEvaluationResponseVO> findDetail(RequestVO<FindDetailEvaluationRequestVO> request) {

		// declaracion de varables
		ResponseVO<FindDetailEvaluationResponseVO> response = new ResponseVO<>();
		FindDetailEvaluationResponseVO evaluationVO = new FindDetailEvaluationResponseVO();
		
		// validar que se cumplen las condiciones para realizar la consulta
		if (validateParametersFindDetail(request, response)) {

			EvaluationDO evaluationDO = this.exists(request.getParameters().getId());

			if (ValidatorUtil.isNull(evaluationDO)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_ERROR, request);
			} else {

				evaluationVO.setId(evaluationDO.getId());
				evaluationVO.setBrandId(evaluationDO.getBrand().getId());
				evaluationVO.setBrandName(evaluationDO.getBrand().getName());
				evaluationVO.setCategoryId(evaluationDO.getCategory().getId());
				evaluationVO.setCategoryName(evaluationDO.getCategory().getName());
				evaluationVO.setSubCategoryId(evaluationDO.getSubCategory().getId());
				evaluationVO.setSubCategoryName(evaluationDO.getSubCategory().getName());
				evaluationVO.setProductId(evaluationDO.getProduct().getId());
				evaluationVO.setProductName(evaluationDO.getProduct().getName());
				evaluationVO.setIngredientId(evaluationDO.getIngredient().getId());
				evaluationVO.setIngredientName(evaluationDO.getIngredient().getName());
				evaluationVO.setRecipeId(evaluationDO.getRecipe().getId());
				evaluationVO.setEvaluationDate(evaluationDO.getEvaluationDate());
				evaluationVO.setIngredientPercentaje(evaluationDO.getIngredientPercentaje());

				response.setData(evaluationVO);
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
	public ResponseVO<List<FindListEvaluationResponseVO>> findList(RequestVO<FindListEvaluationRequestVO> request) {

		// declaracion de varables
		ResponseVO<List<FindListEvaluationResponseVO>> response = new ResponseVO<>();
		
		Page<EvaluationDO> listaEvaluation = null;
		
		if (validateParametersFindByList(request, response)) {
	
			FindListEvaluationRequestVO parameters = request.getParameters();

			// Preparamos el objeto para la paginacion
			String orderby = request.getOrderBy();
			String ordertype = request.getOrderType();
			String orderBy = ValidatorUtil.isNullOrEmpty(orderby) ? "id" : orderby;
			Direction orderType = ValidatorUtil.isNullOrEmpty(ordertype) || ordertype.equals("asc") ? Direction.ASC
					: Direction.DESC;
			Integer size = ValidatorUtil.isNullOrZero(request.getSize()) ? 10 : request.getSize();
			Integer page = ValidatorUtil.isNullOrZero(request.getPage()) ? 1 : request.getPage();
			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(orderType, orderBy));

			// Database execution
			listaEvaluation = evaluationRepository.findList(request.getParameters().getProductId(), pageable);

			// Si no se encontro ningun registro que cumpla la condicion generar error.
 				if (ValidatorUtil.isNullOrEmpty(listaEvaluation.getContent())) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_REGISTER_LIST_ERROR);
			} else {
				// Regresar la respuesta correcta con los registros obtenidos.
				response.setSuccess(true);
				response.setTotalRows(listaEvaluation.getTotalElements());
				response.setData(transformListDO(listaEvaluation.getContent()));
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
	 * This method validate all the entry data before create the object in the database
	 * 
	 * @param request  Data entry
	 * @param response response
	 * @return true if everything it´s ok
	 */
	private boolean validateParametersCreate(RequestVO<CreateEvaluationRequestVO> request, ResponseVO<Long> response) {
		
		// Obtener los parametros de entrada
		CreateEvaluationRequestVO parameters = request.getParameters();
		ProductDO productRegister = new ProductDO();
		IngredientDO ingredientRegister = new IngredientDO();
		RecipeDO recipeRegister = new RecipeDO();

		// Validar que se informaron los campos de entrada
		if (ValidatorUtil.isNull(parameters)) {
			// Si no se ha informado regresar el error y no seguir validando
			ResponseUtil.addError(request, response, GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR);
			return false;
		}

		//Validar que exista el registro a actualizar
		if(ValidatorUtil.isNull(parameters.getProductId()))
		{
			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_ID_PRODUCT_ERROR, request);
			return false;
		} else {

			productRegister = productRepository.findById(parameters.getProductId());

			if (ValidatorUtil.isNull(productRegister)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_PRODUCT, request);
				return false;
			}
		}

		//Validar que exista el registro a actualizar
		if(ValidatorUtil.isNull(parameters.getIngredientId()))
		{
			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_ID_INGREDIENT_ERROR, request);
			return false;

		} else {

			ingredientRegister = ingredientRepository.findById(parameters.getIngredientId());

			if (ValidatorUtil.isNull(ingredientRegister)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_INGREDIENT, request);
				return false;
			}
		}

		//Validar que exista el registro a actualizar
		if(ValidatorUtil.isNull(parameters.getRecipeId()))
		{
			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_RECIPE_ID_ERROR, request);
			return false;
		} else {

			recipeRegister = recipeRepository.findById(parameters.getRecipeId());

			if (ValidatorUtil.isNull(recipeRegister)) {
				ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_FOUND_RECIPE_ID_ERROR, request);
				return false;
			} else {
				if (recipeRegister.getProduct().getId() != parameters.getProductId()) {
					ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_RECIPE_PRODUCT_ERROR, request);
					return false;

				} else {
					if (recipeRegister.getIngredient().getId() != parameters.getIngredientId()) {
						ResponseUtil.addError(request, response, FuncionesBusinessError.NOT_RECIPE_INGREDIENT_ERROR, request);
						return false;
					}
				}
			}
		}

		//Validar que exista el registro a actualizar
		if(ValidatorUtil.isNullOrZero(parameters.getIngredientPercentaje()))
		{
			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_INGREDIENT_PERCENTAJE_ERROR, request);
			return false;
		}

		//Validar que exista el registro a actualizar
		if(ValidatorUtil.isNull(parameters.getEvaluationDate()))
		{
			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_EVALUATION_DATE_ERROR, request);
			return false;
		}

		// Regresar el resultado de la validacion
		return ValidatorUtil.isSuccessfulResponse(response);
	}



	/**
	 * Valida que los parametros para la operacion de consulta por nombre sean
	 * correctos
	 * 
	 * @return true si el nombre no esta vacio
	 * 
	 * @param request  Objeto con los parametros a valida
	 * @param response Respuesta donde se agregan los errores
	 */
	private boolean validateParametersFindDetail(RequestVO<FindDetailEvaluationRequestVO> request, ResponseVO<FindDetailEvaluationResponseVO> response) {

		// Recuperar los parametros de entrada
		FindDetailEvaluationRequestVO parameters = request.getParameters();

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
	private boolean validateParametersFindByList(RequestVO<FindListEvaluationRequestVO> request,
			ResponseVO<List<FindListEvaluationResponseVO>> response) {
		
		// Validar campos obligatorios
	    ValidatorArqUtil.validateParameters(request, response);

		// validar los parametros de la paginacion
		ValidatorArqUtil.validatePaginatonParameters(request, response);

		// validar que el campo obligatorio
		if (ValidatorUtil.isNullOrZero(request.getParameters().getProductId())) {

			ResponseUtil.addError(request, response, FuncionesBusinessError.REQUIRED_ID_ERROR);
		}

		return ValidatorUtil.isSuccessfulResponse(response);

	}



	/**
	 * Obtiene una lista de objetos evaluationVO a partir de una lista de DO
	 * 
	 * @return Lista VO para retorno de resultados
	 * 
	 * @param listaEvaluation a transformar
	 */
	private List<FindListEvaluationResponseVO> transformListDO(List<EvaluationDO> listaEvaluation) {

		// Declarar variables
		List<FindListEvaluationResponseVO> listaEvaluationVO = new ArrayList<>();

		// recorrer el objeto origen
		for (EvaluationDO evaluationDO : listaEvaluation) {
			// Se hace la declaracion de variables necesarias
			FindListEvaluationResponseVO evaluationVO = new FindListEvaluationResponseVO();
			
			evaluationVO.setId(evaluationDO.getId());
			evaluationVO.setBrandId(evaluationDO.getBrand().getId());
			evaluationVO.setBrandName(evaluationDO.getBrand().getName());
			evaluationVO.setCategoryId(evaluationDO.getCategory().getId());
			evaluationVO.setCategoryName(evaluationDO.getCategory().getName());
			evaluationVO.setSubCategoryId(evaluationDO.getSubCategory().getId());
			evaluationVO.setSubCategoryName(evaluationDO.getSubCategory().getName());
			evaluationVO.setProductId(evaluationDO.getProduct().getId());
			evaluationVO.setProductName(evaluationDO.getProduct().getName());
			evaluationVO.setIngredientId(evaluationDO.getIngredient().getId());
			evaluationVO.setIngredientName(evaluationDO.getIngredient().getName());
			evaluationVO.setRecipeId(evaluationDO.getRecipe().getId());
			evaluationVO.setEvaluationDate(evaluationDO.getEvaluationDate());
			evaluationVO.setIngredientPercentaje(evaluationDO.getIngredientPercentaje());

			listaEvaluationVO.add(evaluationVO);
		}

		return listaEvaluationVO;
	}
	


	/*************************************************************************
	 * Metodo que busca un registro por su id, name
	 * Regresa el objeto de la base de datos o una excepcion con el error
	 * 
	 *************************************************************************/
	public EvaluationDO exists(Long id){

		EvaluationDO registro = null;

		try {
			//Validacion de datos de entrada
			if (ValidatorUtil.isNullOrZero(id)) {

				registro = evaluationRepository.findById(id);
			}

		} catch (Exception e) {
			registro = null;
		}
		
		return registro;
	}	


}
