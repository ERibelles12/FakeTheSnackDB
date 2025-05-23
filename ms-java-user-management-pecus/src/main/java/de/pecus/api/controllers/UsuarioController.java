package de.pecus.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.usuarios.CrearUsuarioRequestVO;
import de.pecus.api.vo.usuarios.FindDetailUsuarioResponseVO;
import de.pecus.api.vo.usuarios.FindListUsuarioResponseVO;
import de.pecus.api.vo.usuarios.UpdateUsuarioRequestVO;

public interface UsuarioController {

	
	/**
	 * Metodo que consulta un listado de usuarios
	 * @param headers
	 * @param page
	 * @param size
	 * @param orderBy
	 * @param email
	 * @param telefono
	 * @param idMedico
	 * @param idPersona
	 * @param idPaciente
	 * @return
	 */
	ResponseEntity<ResponseVO<List<FindListUsuarioResponseVO>>> findListUsuario(Map<String, String> headers,
			Integer page, Integer size, String orderBy, String orderType, String email, String telefono);
	
	/**
	 * Metodo para realizar el borrado logico de un usuario
	 * @param headers
	 * @param idUsuario
	 * @return
	 */
	ResponseEntity<ResponseVO<Boolean>> deleteUsuario(Map<String, String> headers, Long idUsuario);
	
	/**
	 * Metodo para crear un usuario desde el backoffice
	 * @param headers
	 * @param body
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
	

}
