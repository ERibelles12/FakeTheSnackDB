package de.pecus.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.usuarios.FindListUsuarioAplicacionResponseVO;
import de.pecus.api.vo.usuarios.UpdateUsuarioAplicacionRequestVO;

public interface UsuarioAplicacionController {

	
	/**
	 * Metodo para actualizar un usuario desde el backoffice
	 * @param headers
	 * @param idUsuario
	 * @param body
	 * @return
	 */
	ResponseEntity<ResponseVO<Long>> updateUsuario(Map<String, String> headers, Long idUsuario, UpdateUsuarioAplicacionRequestVO body);
	
	/**
	 * Metodo para realizar el borrado logico de un usuario
	 * @param headers
	 * @param idUsuario
	 * @return
	 */
	ResponseEntity<ResponseVO<Boolean>> deleteUsuario(Map<String, String> headers, Long idUsuario);
	

	/**
	 * Metodo que consulta un listado de usuarios
	 * @param headers
	 * @param page
	 * @param size
	 * @param orderBy
	 * @param email
	 * @return
	 */
	ResponseEntity<ResponseVO<List<FindListUsuarioAplicacionResponseVO>>> findListUsuarioAplicacion(Map<String, String> headers,
			Integer page, Integer size, String orderBy, String orderType, String email);
	
	
	


}