package de.pecus.api.vo.usuarios;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author Irvin Aguilar
 *  VO para sesion
 */

@JsonInclude(Include.NON_NULL)
public class LoginSesionRequestVO {
   
    /** Nombre de usuario (email, clave) */
    private String usuario;

    /** Password del usuario */
    private String password;
    
    /** Flag para saber si es Mobile */
    private Boolean isMobile;
    
    /** Flag para saber en que aplicacion */
    private String aplicacion;
    
    public String getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	/**
     * Constructor default
     */
    public LoginSesionRequestVO() {
        super();
    }
    
    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the isMobile
     */
	
	public Boolean getIsMobile() { 
		return isMobile;
	}
	 
	/**
     * @param isMobile the isMobile to set
     */
	
	public void setIsMobile(Boolean isMobile) {
		this.isMobile = isMobile; 
	}
	 
    
}
