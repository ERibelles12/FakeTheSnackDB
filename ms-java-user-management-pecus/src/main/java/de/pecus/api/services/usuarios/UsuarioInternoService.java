package de.pecus.api.services.usuarios;

import java.util.List;

import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.usuarios.AddRolUserRequestVO;
import de.pecus.api.vo.usuarios.CrearUsuarioRequestVO;
import de.pecus.api.vo.usuarios.DeleteUsuarioRequestVO;
import de.pecus.api.vo.usuarios.FindDetailUsuarioRequestVO;
import de.pecus.api.vo.usuarios.FindDetailUsuarioResponseVO;
import de.pecus.api.vo.usuarios.FindListUsuarioRequestVO;
import de.pecus.api.vo.usuarios.FindListUsuarioResponseVO;
import de.pecus.api.vo.usuarios.UpdateUsuarioRequestVO;

/**
 * Clase de logica de negocio para administracionde Tipos de Rol
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
public interface UsuarioInternoService {
	 
	/**
	 * Metodo para crear un usuario Interno desde el backoffice
	 * @param request
	 * @return
	 */
	ResponseVO<Long> crearUsuarioInterno(RequestVO<CrearUsuarioRequestVO> request);
	
	
	/**
	 * Metodo para consultar el detalle de un usuario por id
	 * @param request
	 * @return
	 */
	ResponseVO<FindDetailUsuarioResponseVO> findDetailUsuario(RequestVO<FindDetailUsuarioRequestVO> request);
	
	/**
	 * Metodo para actualizar un usuario desde el backoffice
	 * @param request
	 * @return
	 */
	ResponseVO<Long> updateUsuario(RequestVO<UpdateUsuarioRequestVO> request);
	
	
	/**
	 * Metodo para realizar el borrado logico de un usuario
	 * @param request
	 * @return
	 */
	ResponseVO<Boolean> deleteUsuario(RequestVO<DeleteUsuarioRequestVO> request);
	
	/**
	 * Metodo que agrega un rol a un usuario existente
	 * @param request
	 * @return
	 */
	ResponseVO<Long> addRolUser(RequestVO<AddRolUserRequestVO> request);
	
	
	/**
	 * Metodo que consulta un listado de usuarios
	 * @param request
	 * @return
	 */
	ResponseVO<List<FindListUsuarioResponseVO>> findListUsuario(RequestVO<FindListUsuarioRequestVO> request);
	
	/**
	 * Metodo que consulta un listado de usuarios
	 * @param request
	 * @return
	 */
	ResponseVO<List<FindListUsuarioResponseVO>> findListUsuarioApplicacion(RequestVO<FindListUsuarioRequestVO> request);
	
	
	
	
	
}