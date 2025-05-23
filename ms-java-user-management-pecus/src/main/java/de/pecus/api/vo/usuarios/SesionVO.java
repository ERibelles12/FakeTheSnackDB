package de.pecus.api.vo.usuarios;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * VO para sesion
 * 
 */
@JsonInclude(Include.NON_NULL)
public class SesionVO {

	/** Nombre de usuario (email, clave) */
	private String usuario;

	/** Password del usuario */
	private String password;

	/** Token de sesion generado */
	private String token;

	/** Lista de roles del usuario en sesion */
	private List<RolVO> listaRoles;

	/** Lista de paginas */
	private List<MenuVO> listaMenus;
	
	/** Lista de funciones permitidas al usuario **/
	private List<FuncionVO> listaFunciones;

	/** Flag para saber si es Mobile */
	private Boolean isMobile;

	/** Aplicacion en la que inicio sesion el usuario **/
	private String aplicacion;

	/**
	 * Constructor default
	 */
	public SesionVO() {
		super();
	}

	/**
	 * Constructor con token
	 * 
	 * @param token Token de sesion
	 */
	public SesionVO(String token) {
		super();
		this.token = token;
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
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the listaMenus
	 */
	public List<MenuVO> getListaMenus() {
		return listaMenus;
	}

	/**
	 * @param listaMenus the listaMenus to set
	 */
	public void setListaMenus(List<MenuVO> listaMenus) {
		this.listaMenus = listaMenus;
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

	/**
	 * @return the aplicacion
	 */
	public String getAplicacion() {
		return aplicacion;
	}

	/**
	 * @param aplicacion the aplicacion to set
	 */
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	/**
	 * @return the listaRoles
	 */
	public List<RolVO> getListaRoles() {
		return listaRoles;
	}

	/**
	 * @param listaRoles the listaRoles to set
	 */
	public void setListaRoles(List<RolVO> listaRoles) {
		this.listaRoles = listaRoles;
	}

	/**
	 * @return the listaFunciones
	 */
	public List<FuncionVO> getListaFunciones() {
		return listaFunciones;
	}

	/**
	 * @param listaFunciones the listaFunciones to set
	 */
	public void setListaFunciones(List<FuncionVO> listaFunciones) {
		this.listaFunciones = listaFunciones;
	}
}
