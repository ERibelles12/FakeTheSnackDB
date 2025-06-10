package de.pecus.api.services.usuarios;

import java.util.List;

import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.product.AssociateProductIngredientRequestVO;
import de.pecus.api.vo.product.CreateProductRequestVO;
import de.pecus.api.vo.product.DeleteProductIngredientRequestVO;
import de.pecus.api.vo.product.DeleteProductRequestVO;
import de.pecus.api.vo.product.FindDetailProductRequestVO;
import de.pecus.api.vo.product.FindDetailProductResponseVO;
import de.pecus.api.vo.product.FindListProductRecipeRequestVO;
import de.pecus.api.vo.product.FindListProductRecipeResponseVO;
import de.pecus.api.vo.product.FindListProductRequestVO;
import de.pecus.api.vo.product.FindListProductResponseVO;
import de.pecus.api.vo.product.UpdateProductRequestVO;

/**
 * Clase de logica de negocio para administracion de Productes
 * 
 * @author Proa
 *
 */
public interface ProductService {

	/**
	 * Crea un nuevo registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id generado
	 */
	ResponseVO<Long> create (RequestVO<CreateProductRequestVO> request);

	/**
	 * Actualiza un registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id actualizado
	 */
	ResponseVO<Long> update(RequestVO<UpdateProductRequestVO> request);

	/**
	 * Marca un registro como eliminado un registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id eliminado
	 */
	ResponseVO<Boolean> delete(RequestVO<DeleteProductRequestVO> request);

	/**
	 * Consulta un registro
	 * 
	 * @param numero Nombre  buscado
	 * 
	 * @return Objeto VO con los datos encontrados
	 */
	ResponseVO<FindDetailProductResponseVO> findDetail(RequestVO<FindDetailProductRequestVO> request);

	
	/**
	 * Consulta la base de datos en base a varios parametros
	 * 
	 * @return ReponseVO con los datos encontrados
	 * 
	 * @param request Objeto con parametros de entrada 
	 */
	ResponseVO<List<FindListProductResponseVO>> findList(RequestVO<FindListProductRequestVO> request);

	/**
	 * Asocia una substancia a un producto
	 *
	 * @param request Objeto con parametros de entrada
	 *
	 * @return Id generado
	 */
	ResponseVO<Long> associateProductIngredient (RequestVO<AssociateProductIngredientRequestVO> request);

	/**
	 * Asocia una substancia a un producto
	 *
	 * @param request Objeto con parametros de entrada
	 *
	 * @return Id generado
	 */
	ResponseVO<Boolean> deleteProductIngredient (RequestVO<DeleteProductIngredientRequestVO> request);

	/**
	 * Consulta la base de datos en base a varios parametros
	 *
	 * @return ReponseVO con los datos encontrados
	 *
	 * @param request Objeto con parametros de entrada
	 */
	ResponseVO<List<FindListProductRecipeResponseVO>> findListRecipe(RequestVO<FindListProductRecipeRequestVO> request);



}
