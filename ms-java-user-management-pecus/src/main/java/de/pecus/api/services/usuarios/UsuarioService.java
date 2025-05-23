package de.pecus.api.services.usuarios;

import java.util.List;

import de.pecus.api.entities.UsuarioDO;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.usuarios.CrearUsuarioRequestVO;
import de.pecus.api.vo.usuarios.DeleteUsuarioRequestVO;
import de.pecus.api.vo.usuarios.FindDetailUsuarioRequestVO;
import de.pecus.api.vo.usuarios.FindDetailUsuarioResponseVO;
import de.pecus.api.vo.usuarios.FindListUsuarioRequestVO;
import de.pecus.api.vo.usuarios.FindListUsuarioResponseVO;
import de.pecus.api.vo.usuarios.FindUserByEmailAndPhoneNumberRequestVO;
import de.pecus.api.vo.usuarios.FindUserByEmailAndPhoneNumberResponseVO;
import de.pecus.api.vo.usuarios.UpdateUsuarioRequestVO;

/**
 * Clase de logica de negocio para administracionde Tipos de Rol
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
public interface UsuarioService {
	 

	/**
	 * Metodo encargado de validar la existencia del usuario por su identificador
	 * @param idUsuario
	 */
	UsuarioDO exists(Long idUsuario, String idioma);
	
	/**
	 * Metodo que consulta un usuario por medio del email y telefono
	 * @param request
	 * @return
	 */
	ResponseVO<FindUserByEmailAndPhoneNumberResponseVO> findByEmailAndPhone(RequestVO<FindUserByEmailAndPhoneNumberRequestVO> request);
	
	
	/**
	 * Metodo que consulta un listado de usuarios
	 * @param request
	 * @return
	 */
	ResponseVO<List<FindListUsuarioResponseVO>> findListUsuario(RequestVO<FindListUsuarioRequestVO> request);
	
	/**
	 * Metodo para realizar el borrado logico de un usuario
	 * @param request
	 * @return
	 */
	ResponseVO<Boolean> deleteUsuario(RequestVO<DeleteUsuarioRequestVO> request);
	
	/**
	 * Metodo para crear un usuario desde el backoffice
	 * @param request
	 * @return
	 */
	ResponseVO<Long> crearUsuario(RequestVO<CrearUsuarioRequestVO> request);
	
	/**
	 * Metodo para actualizar un usuario desde el backoffice
	 * @param request
	 * @return
	 */
	ResponseVO<Long> updateUsuario(RequestVO<UpdateUsuarioRequestVO> request);
	
	/**
	 * Metodo para consultar el detalle de un usuario por id
	 * @param request
	 * @return
	 */
	ResponseVO<FindDetailUsuarioResponseVO> findDetailUsuario(RequestVO<FindDetailUsuarioRequestVO> request);


}

