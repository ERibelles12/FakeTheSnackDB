package de.pecus.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.product.AssociateProductIngredientRequestVO;
import de.pecus.api.vo.product.CreateProductRequestVO;
import de.pecus.api.vo.product.FindDetailProductResponseVO;
import de.pecus.api.vo.product.FindListProductRecipeResponseVO;
import de.pecus.api.vo.product.FindListProductResponseVO;
import de.pecus.api.vo.product.UpdateProductRequestVO;

public interface ProductController {

	/**
	 * Servicio para crear un registro.
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param name		Id alfanumerico del registro
	 * @param descripcion	Descripcion del registro
	 * 
	 * @return Responde una entidad de tipo Response el registro creado
	 */
	ResponseEntity<ResponseVO<Long>> createProduct(Map<String, String> headers, CreateProductRequestVO body);

	/**
	 * Servicio para actualiza un registro.
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param id			Id del registro a actualizar
	 * @param name		Id alfanumerico del registro
	 * @param descripcion	Descripcion del registro
	 * 
	 * @return Responde una entidad de tipo Response el registro actualizado
	 */
	ResponseEntity<ResponseVO<Long>> updateProduct(Map<String, String> headers, Long id, UpdateProductRequestVO body);

	/**
	 * Servicio para elimina un registro.
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param id			Id del registro a actualizar
	 * 
	 * @return Responde una entidad de tipo Response el booleano del resultado
	 */
	ResponseEntity<ResponseVO<Boolean>> deleteProduct(Map<String, String> headers, Long id);

	/**
	 * Servicio consulta un registro
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param id			Id del registro a actualizar
	 * @param name		Id alfanumerico del registro
	 * 
	 * @return Responde una entidad de tipo Response con los datos del registro
	 */
	ResponseEntity<ResponseVO<FindDetailProductResponseVO>> findDetailProduct(Map<String, String> headers, Long id, String name);

	
	/**
	 * Servicio para consulta de los datos de la tabla con filtros
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param name		Id alfanumerico del registro
	 * 
	 * @return Responde una lista de registros encontrados
	 */
	ResponseEntity<ResponseVO<List<FindListProductResponseVO>>> findListProduct(Map<String, String> headers,Integer page,
			Integer size, String orderBy,String orderType,  String name);



	/**
	 * Servicio para crear un registro.
	 *
	 * @param headers 		Cabeceras de la solicitud.
	 * @param name		Id alfanumerico del registro
	 * @param descripcion	Descripcion del registro
	 *
	 * @return Responde una entidad de tipo Response el registro creado
	 */
	ResponseEntity<ResponseVO<Long>> associateProductIngredient(Map<String, String> headers, AssociateProductIngredientRequestVO body);

	/**
	 * Servicio para elimina un registro.
	 *
	 * @param headers 		Cabeceras de la solicitud.
	 * @param id			Id del registro a actualizar
	 *
	 * @return Responde una entidad de tipo Response el booleano del resultado
	 */
	ResponseEntity<ResponseVO<Boolean>> deleteProductIngredient(Map<String, String> headers, Long id);

	/**
	 * Servicio para consulta de los datos de la tabla con filtros
	 *
	 * @param headers 		Cabeceras de la solicitud.
	 * @param id			id del producto
	 *
	 * @return Responde una lista de registros encontrados
	 */
	ResponseEntity<ResponseVO<List<FindListProductRecipeResponseVO>>> findListProductRecipe(Map<String, String> headers,Integer page,
																				Integer size, String orderBy,String orderType,  Long id);


}
