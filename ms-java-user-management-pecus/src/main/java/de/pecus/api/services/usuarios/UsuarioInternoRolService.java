package de.pecus.api.services.usuarios;

import java.util.List;

import de.pecus.api.entities.RelUsuarioInternoRolDO;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.usuarios.CreateUsuarioRolRequestVO;
import de.pecus.api.vo.usuarios.DeleteUsuarioRolRequestVO;

/**
 * Clase auxiliar para servicios asociados a usuarios internos
 * @author Luis Enrique Sanchez Santiago
 *
 */
public interface UsuarioInternoRolService {
	
	/**
	 * Crea un nuevo registro de Usuario Condo Rol
	 * 
	 * @param request 
	 * @return Id generado
	 */
	ResponseVO<Long> create(RequestVO<List<CreateUsuarioRolRequestVO>> request);
	
	/**
	 * Servicio que valida si existe la combinacion entre un usuario y un Rol
	 * @param userId
	 * @param rolId
	 * @return
	 */
	List<RelUsuarioInternoRolDO> exist(Long userId, Long rolId, String idioma);
	
	/**
	 * Servicio que elimina un registro en la tabla de relacion Usuario Rol
	 * @param request
	 * @return
	 */
	ResponseVO<Boolean> deleteUsuarioRol(RequestVO<DeleteUsuarioRolRequestVO> request);
}