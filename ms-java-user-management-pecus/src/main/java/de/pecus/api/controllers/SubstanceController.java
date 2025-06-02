package de.pecus.api.controllers;

import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.substance.CreateSubstanceRequestVO;
import de.pecus.api.vo.substance.FindDetailSubstanceResponseVO;
import de.pecus.api.vo.substance.FindListSubstanceResponseVO;
import de.pecus.api.vo.substance.UpdateSubstanceRequestVO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface SubstanceController {

	/**
	 * Servicio para crear un registro.
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param name		Id alfanumerico del registro
	 * @param descripcion	Descripcion del registro
	 * 
	 * @return Responde una entidad de tipo Response el registro creado
	 */
	ResponseEntity<ResponseVO<Long>> createSubstance(Map<String, String> headers, CreateSubstanceRequestVO body);

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
	ResponseEntity<ResponseVO<Long>> updateSubstance(Map<String, String> headers, Long id, UpdateSubstanceRequestVO body);

	/**
	 * Servicio para elimina un registro.
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param id			Id del registro a actualizar
	 * 
	 * @return Responde una entidad de tipo Response el booleano del resultado
	 */
	ResponseEntity<ResponseVO<Boolean>> deleteSubstance(Map<String, String> headers, Long id);

	/**
	 * Servicio consulta un registro
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param id			Id del registro a actualizar
	 * @param name		Id alfanumerico del registro
	 * 
	 * @return Responde una entidad de tipo Response con los datos del registro
	 */
	ResponseEntity<ResponseVO<FindDetailSubstanceResponseVO>> findDetailSubstance(Map<String, String> headers, Long id, String name);

	
	/**
	 * Servicio para consulta de los datos de la tabla con filtros
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param name		Id alfanumerico del registro
	 * 
	 * @return Responde una lista de registros encontrados
	 */
	ResponseEntity<ResponseVO<List<FindListSubstanceResponseVO>>> findListSubstance(Map<String, String> headers, Integer page,
                                                                                Integer size, String orderBy, String orderType, String name);


	
	
}
