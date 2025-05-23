package de.pecus.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.funciones.CreateRolFuncionMenuRequestVO;
import de.pecus.api.vo.funciones.CreateRolFuncionRequestVO;
import de.pecus.api.vo.funciones.CreateRolFuncionResponseVO;
import de.pecus.api.vo.funciones.CreateRolFuncionSubmenuRequestVO;
import de.pecus.api.vo.funciones.FindListRolFuncionMenuResponseVO;
import de.pecus.api.vo.funciones.FindListRolFuncionResponseVO;
import de.pecus.api.vo.funciones.FindListRolFuncionSubmenuResponseVO;
import de.pecus.api.vo.roles.CreateRolRequestVO;
import de.pecus.api.vo.roles.FindDetailRolResponseVO;
import de.pecus.api.vo.roles.FindListRolResponseVO;
import de.pecus.api.vo.roles.UpdateRolRequestVO;

public interface RolController {
	
	/**
	 * Servicio para crear un registro.
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param idNombre		Id alfanumerico del registro
	 * @param descripcion	Descripcion del registro
	 * 
	 * @return Responde una entidad de tipo Response el registro creado
	 */
	ResponseEntity<ResponseVO<Long>> createRol(Map<String, String> headers, CreateRolRequestVO body);
	
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
	ResponseEntity<ResponseVO<Long>> updateRol(Map<String, String> headers, Long id, UpdateRolRequestVO body);

	/**
	 * Servicio para elimina un registro.
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param id			Id del registro a actualizar
	 * 
	 * @return Responde una entidad de tipo Response el booleano del resultado
	 */
	ResponseEntity<ResponseVO<Boolean>> deleteRol(Map<String, String> headers, Long id);

	/**
	 * Servicio consulta un registro
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param id			Id del registro a actualizar
	 * @param idNombre		Id alfanumerico del registro
	 * 
	 * @return Responde una entidad de tipo Response con los datos del registro
	 */
	ResponseEntity<ResponseVO<FindDetailRolResponseVO>> findDetailRol(Map<String, String> headers, Long id);
	
	/**
	 * Servicio para consulta de los datos de la tabla con filtros
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param idNombre		Id alfanumerico del registro
	 * 
	 * @return Responde una lista de registros encontrados
	 */
	ResponseEntity<ResponseVO<List<FindListRolResponseVO>>> findListRol(Map<String, String> headers,Integer page,
			Integer size, String orderBy,String orderType,  Long idAplicacion, String idNombre, Long idUsuario);


	/**
	 * Servicio para crear un registro.
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param idNombre		Id alfanumerico del registro
	 * @param descripcion	Descripcion del registro
	 * 
	 * @return Responde una entidad de tipo Response el registro creado
	 */
	ResponseEntity<ResponseVO<CreateRolFuncionResponseVO>> createRolFuncion(Map<String, String> headers, CreateRolFuncionRequestVO body);

	/**
	 * Servicio para elimina un registro.
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param id			Id del registro a actualizar
	 * 
	 * @return Responde una entidad de tipo Response el booleano del resultado
	 */
	ResponseEntity<ResponseVO<Boolean>> deleteRolFuncion(Map<String, String> headers, Long id);

	/**
	 * Servicio para consulta de los datos de la tabla con filtros
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param idNombre		Id alfanumerico del registro
	 * 
	 * @return Responde una lista de registros encontrados
	 */
	ResponseEntity<ResponseVO<List<FindListRolFuncionResponseVO>>> findListRolFuncion(Map<String, String> headers,Integer page,
			Integer size, String orderBy,String orderType,  Long idAplicacion, Long idRol);


	
	/**
	 * Servicio para crear un registro.
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param idNombre		Id alfanumerico del registro
	 * @param descripcion	Descripcion del registro
	 * 
	 * @return Responde una entidad de tipo Response el registro creado
	 */
	ResponseEntity<ResponseVO<List<Long>>> createRolFuncionMenu(Map<String, String> headers, CreateRolFuncionMenuRequestVO body);

	/**
	 * Servicio para elimina un registro.
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param id			Id del registro a actualizar
	 * 
	 * @return Responde una entidad de tipo Response el booleano del resultado
	 */
	ResponseEntity<ResponseVO<Boolean>> deleteRolFuncionMenu(Map<String, String> headers, Long id);

	/**
	 * Servicio para consulta de los datos de la tabla con filtros
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param idNombre		Id alfanumerico del registro
	 * 
	 * @return Responde una lista de registros encontrados
	 */
	ResponseEntity<ResponseVO<List<FindListRolFuncionMenuResponseVO>>> findListRolFuncionMenu(Map<String, String> headers,Integer page,
			Integer size, String orderBy,String orderType, Long idRolFuncion, Long idMenu);
	
	/**
	 * Servicio para crear un registro.
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param idNombre		Id alfanumerico del registro
	 * @param descripcion	Descripcion del registro
	 * 
	 * @return Responde una entidad de tipo Response el registro creado
	 */
	ResponseEntity<ResponseVO<List<Long>>> createRolFuncionSubmenu(Map<String, String> headers, CreateRolFuncionSubmenuRequestVO body);

	/**
	 * Servicio para elimina un registro.
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param id			Id del registro a actualizar
	 * 
	 * @return Responde una entidad de tipo Response el booleano del resultado
	 */
	ResponseEntity<ResponseVO<Boolean>> deleteRolFuncionSubmenu(Map<String, String> headers, Long id);

	/**
	 * Servicio para consulta de los datos de la tabla con filtros
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param idNombre		Id alfanumerico del registro
	 * 
	 * @return Responde una lista de registros encontrados
	 */
	ResponseEntity<ResponseVO<List<FindListRolFuncionSubmenuResponseVO>>> findListRolFuncionSubmenu(Map<String, String> headers,Integer page,
			Integer size, String orderBy,String orderType, Long idFuncion, Long idSubmenu);
	
	
	/**
	 * Servicio para consulta de los datos de la tabla de usuarios internos con filtros
	 * 
	 * @param headers 		Cabeceras de la solicitud.
	 * @param idNombre		Id alfanumerico del registro
	 * 
	 * @return Responde una lista de registros encontrados
	 */
	ResponseEntity<ResponseVO<List<FindListRolResponseVO>>> findInternalListRol(Map<String, String> headers,Integer page,
			Integer size, String orderBy,String orderType,  Long idAplicacion, String idNombre, Long idUsuario);
}
