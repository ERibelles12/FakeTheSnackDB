package de.pecus.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.evaluation.CreateEvaluationRequestVO;
import de.pecus.api.vo.evaluation.FindDetailEvaluationResponseVO;
import de.pecus.api.vo.evaluation.FindListEvaluationResponseVO;

public interface EvaluationController {

	/**
	 * Servicio para crear un registro.
	 * 
	 * @param headers 		Cabeceras de la solicitud.*
	 * @return Responde una entidad de tipo Response el registro creado
	 */
	ResponseEntity<ResponseVO<Long>> createEvaluation(Map<String, String> headers, CreateEvaluationRequestVO body);

	/**
	 * Servicio consulta un registro
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param id			Id del registro a actualizar
	 * @param name		Id alfanumerico del registro
	 * 
	 * @return Responde una entidad de tipo Response con los datos del registro
	 */
	ResponseEntity<ResponseVO<FindDetailEvaluationResponseVO>> findDetailEvaluation(Map<String, String> headers, Long id, String name);

	
	/**
	 * Servicio para consulta de los datos de la tabla con filtros
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param name		Id alfanumerico del registro
	 * 
	 * @return Responde una lista de registros encontrados
	 */
	ResponseEntity<ResponseVO<List<FindListEvaluationResponseVO>>> findListEvaluation(Map<String, String> headers, Integer page,
                                                                                Integer size, String orderBy, String orderType, String name);


	
	
}
