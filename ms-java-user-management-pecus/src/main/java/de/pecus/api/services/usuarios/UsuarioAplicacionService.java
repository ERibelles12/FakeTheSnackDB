package de.pecus.api.services.usuarios;

import java.util.List;

import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.usuarios.DeleteUsuarioRequestVO;
import de.pecus.api.vo.usuarios.FindListUsuarioAplicacionResponseVO;
import de.pecus.api.vo.usuarios.FindListUsuarioRequestVO;
import de.pecus.api.vo.usuarios.UpdateUsuarioAplicacionRequestVO;

/**
 * Clase de logica de negocio para administracionde Tipos de Rol
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
public interface UsuarioAplicacionService {
	 
	
	
	/**
	 * Metodo para actualizar un usuario desde el backoffice
	 * @param request
	 * @return
	 */
	ResponseVO<Long> updateUsuario(RequestVO<UpdateUsuarioAplicacionRequestVO> request);
	
	
	/**
	 * Metodo para realizar el borrado logico de un usuario
	 * @param request
	 * @return
	 */
	ResponseVO<Boolean> deleteUsuario(RequestVO<DeleteUsuarioRequestVO> request);
	
	
	/**
	 * Metodo que consulta un listado de usuarios
	 * @param request
	 * @return
	 */
	ResponseVO<List<FindListUsuarioAplicacionResponseVO>> findListUsuarioApplicacion(RequestVO<FindListUsuarioRequestVO> request);
	
	
	
	
	
}