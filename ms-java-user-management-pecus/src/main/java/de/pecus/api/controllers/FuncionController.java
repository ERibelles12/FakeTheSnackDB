package de.pecus.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.funciones.CreateFuncionRequestVO;
import de.pecus.api.vo.funciones.FindDetailFuncionResponseVO;
import de.pecus.api.vo.funciones.FindListFuncionResponseVO;
import de.pecus.api.vo.funciones.UpdateFuncionRequestVO;

public interface FuncionController {

	/**
	 * Servicio para crear un registro.
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param idNombre		Id alfanumerico del registro
	 * @param descripcion	Descripcion del registro
	 * 
	 * @return Responde una entidad de tipo Response el registro creado
	 */
	ResponseEntity<ResponseVO<Long>> createFuncion(Map<String, String> headers, CreateFuncionRequestVO body);

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
	ResponseEntity<ResponseVO<Long>> updateFuncion(Map<String, String> headers, Long id, UpdateFuncionRequestVO body);

	/**
	 * Servicio para elimina un registro.
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param id			Id del registro a actualizar
	 * 
	 * @return Responde una entidad de tipo Response el booleano del resultado
	 */
	ResponseEntity<ResponseVO<Boolean>> deleteFuncion(Map<String, String> headers, Long id);

	/**
	 * Servicio consulta un registro
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param id			Id del registro a actualizar
	 * @param idNombre		Id alfanumerico del registro
	 * 
	 * @return Responde una entidad de tipo Response con los datos del registro
	 */
	ResponseEntity<ResponseVO<FindDetailFuncionResponseVO>> findDetailFuncion(Map<String, String> headers, Long id);

	
	/**
	 * Servicio para consulta de funciones asignadas y no asignadas
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param idNombre		Id alfanumerico del registro
	 * 
	 * @return Responde una lista de registros encontrados
	 */
	ResponseEntity<ResponseVO<List<FindListFuncionResponseVO>>> findListFuncionAssing(Map<String, String> headers,  Long idAplicacion, String idNombre, Long idRol);
	
	/**
	 * Servicio para consulta de los datos de la tabla con filtros
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param idNombre		Id alfanumerico del registro
	 * 
	 * @return Responde una lista de registros encontrados
	 */
	ResponseEntity<ResponseVO<List<FindListFuncionResponseVO>>> findListFuncion(Map<String, String> headers,Integer page,
			Integer size, String orderBy,String orderType,  Long idAplicacion, String idNombre, Boolean noAsignados, Long idRol);

}
