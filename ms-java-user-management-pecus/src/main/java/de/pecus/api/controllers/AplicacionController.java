package de.pecus.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.funciones.CreateAplicacionRequestVO;
import de.pecus.api.vo.funciones.FindDetailAplicacionResponseVO;
import de.pecus.api.vo.funciones.FindListAplicacionResponseVO;
import de.pecus.api.vo.funciones.UpdateAplicacionRequestVO;

public interface AplicacionController {

	/**
	 * Servicio para crear un registro.
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param idNombre		Id alfanumerico del registro
	 * @param descripcion	Descripcion del registro
	 * 
	 * @return Responde una entidad de tipo Response el registro creado
	 */
	ResponseEntity<ResponseVO<Long>> createAplicacion(Map<String, String> headers, CreateAplicacionRequestVO body);

	/**
	 * Servicio para actualiza un registro.
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param id			Id del registro a actualizar
	 * @param idNombre		Id alfanumerico del registro
	 * @param descripcion	Descripcion del registro
	 * 
	 * @return Responde una entidad de tipo Response el registro actualizado
	 */
	ResponseEntity<ResponseVO<Long>> updateAplicacion(Map<String, String> headers, Long id, UpdateAplicacionRequestVO body);

	/**
	 * Servicio para elimina un registro.
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param id			Id del registro a actualizar
	 * 
	 * @return Responde una entidad de tipo Response el booleano del resultado
	 */
	ResponseEntity<ResponseVO<Boolean>> deleteAplicacion(Map<String, String> headers, Long id);

	/**
	 * Servicio consulta un registro
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param id			Id del registro a actualizar
	 * @param idNombre		Id alfanumerico del registro
	 * 
	 * @return Responde una entidad de tipo Response con los datos del registro
	 */
	ResponseEntity<ResponseVO<FindDetailAplicacionResponseVO>> findDetailAplicacion(Map<String, String> headers, Long id, String idNombre);

	
	/**
	 * Servicio para consulta de los datos de la tabla con filtros
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param idNombre		Id alfanumerico del registro
	 * 
	 * @return Responde una lista de registros encontrados
	 */
	ResponseEntity<ResponseVO<List<FindListAplicacionResponseVO>>> findListAplicacion(Map<String, String> headers,Integer page,
			Integer size, String orderBy,String orderType,  String idNombre);



}
