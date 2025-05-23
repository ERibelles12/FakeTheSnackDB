package de.pecus.api.vo.usuarios;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * VO para sesion
 * 
 */
@JsonInclude(Include.NON_NULL)
public class LoginSesionResponseVO {

	/** Token de sesion generado */
	private String token;

	/** Aplicacion en la que hizo login el usuario **/
	private AplicacionVO aplicacion;

	/** Bandera que indica si es el primer Login de un usuario **/
	private Boolean firstLogin;

	/** Lista de roles de la aplicacion del usuario en sesion */
	private List<RolVO> listaRoles;

	/** Lista de paginas */
	private List<MenuVO> listaMenus;

	/** Listado de funciones que tiene permitidas el usuario **/
	private List<FuncionVO> listaFunciones;

	/**
	 * Constructor default
	 */
	public LoginSesionResponseVO() {
		super();
	}

	/**
	 * Constructor con token
	 * 
	 * @param token Token de sesion
	 */
	public LoginSesionResponseVO(String token) {
		super();
		this.token = token;
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
	 * @return the firstLogin
	 */
	public Boolean getFirstLogin() {
		return firstLogin;
	}

	/**
	 * @param firstLogin the firstLogin to set
	 */
	public void setFirstLogin(Boolean firstLogin) {
		this.firstLogin = firstLogin;
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

	/**
	 * @return the aplicacion
	 */
	public AplicacionVO getAplicacion() {
		return aplicacion;
	}

	/**
	 * @param aplicacion the aplicacion to set
	 */
	public void setAplicacion(AplicacionVO aplicacion) {
		this.aplicacion = aplicacion;
	}

//	public FindDetailPersonaResponseVO getPersona() {
//		return persona;
//	}

//	public void setPersona(FindDetailPersonaResponseVO persona) {
//		this.persona = persona;
//	}
	
}
