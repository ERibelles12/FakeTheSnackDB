package de.pecus.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.usuarios.AddRolUserRequestVO;
import de.pecus.api.vo.usuarios.CrearUsuarioRequestVO;
import de.pecus.api.vo.usuarios.FindDetailUsuarioResponseVO;
import de.pecus.api.vo.usuarios.FindListUsuarioResponseVO;
import de.pecus.api.vo.usuarios.UpdateUsuarioRequestVO;

public interface UsuarioInternoController {

	/**
	 * Operation: create, Method: POST Crea el registro Usuario Interno.	 
	 *  
	 * @param request
	 * @param token
	 * @return
	 */
	ResponseEntity<ResponseVO<Long>> crearUsuario(Map<String, String> headers, CrearUsuarioRequestVO body);
	
	/**
	 * Metodo para actualizar un usuario desde el backoffice
	 * @param headers
	 * @param idUsuario
	 * @param body
	 * @return
	 */
	ResponseEntity<ResponseVO<Long>> updateUsuario(Map<String, String> headers, Long idUsuario, UpdateUsuarioRequestVO body);
	
	/**
	 * Metodo para consultar el detalle de un usuario por id
	 * @param headers
	 * @param idUsuario
	 * @return
	 */
	ResponseEntity<ResponseVO<FindDetailUsuarioResponseVO>> findDetailUsuario(Map<String, String> headers, Long idUsuario);
	
	/**
	 * Operacion para agregar un Rol a un usuario
	 * 
	 * @param headers
	 * @param addRolUserRequestVO
	 * @return
	 */
	ResponseEntity<ResponseVO<Long>> addRolUser(Map<String, String> headers, AddRolUserRequestVO addRolUserRequestVO);
	
	/**
	 * Metodo para borrar un rol a un usuario por medio de su id
	 * @param headers
	 * @param idUsuario
	 * @param idRol
	 * @return
	 */
	ResponseEntity<ResponseVO<Boolean>> deleteRolUsuario(Map<String, String> headers, Long idUsuario, Long idRol);
	
	/**
	 * Metodo que consulta un listado de usuarios
	 * @param headers
	 * @param page
	 * @param size
	 * @param orderBy
	 * @param email
	 * @return
	 */
	ResponseEntity<ResponseVO<List<FindListUsuarioResponseVO>>> findListUsuarioInterno(Map<String, String> headers,
			Integer page, Integer size, String orderBy, String orderType, String email);
	
	
}