package de.pecus.api.vo.usuarios;

import java.io.Serializable;

public class DeleteUsuarioRolRequestVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1748738578950336075L;

	private Long idUsuario;
	
	private Long idRol;

	/**
	 * @return id Usuario
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return id Rol
	 */
	public Long getIdRol() {
		return idRol;
	}

	/**
	 * @param idRol
	 */
	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	
}
