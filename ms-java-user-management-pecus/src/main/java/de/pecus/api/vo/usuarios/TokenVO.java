package de.pecus.api.vo.usuarios;

import java.util.Date;

/**
 * Clase que contiene los atributos del token
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
public class TokenVO {

	/**
	 * Id de usuario
	 */
	private Long idUsuario;
	
	/**
	 * Id de la persona
	 */
	private Long idPersona;

	/**
	 * Nombre de usuario
	 */
	private String nombreUsuario;

	/**
	 * Nombre
	 */
	private String nombre;
	
	/**
	 * Aplicacion
	 */
	 private String aplicacion;


	/**
	 * Apellido paterno
	 */
	private String apellidoPaterno;

	/**
	 * Apellido materno
	 */
	private String apellidoMaterno;

	/**
	 * Fecha de creacion
	 */
	private Date fechaCreacion;

	/**
	 * Fecha de expiracion
	 */
	private Date fechaExpiracion;

	/**
	 * Flag para saber si es Mobile
	 */
	private Boolean isMobile;
	
	/**
	 * Flag para saber si el Token es publico ó no
	 */
	private Boolean isPublic;

	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @param nombreUsuario the nombreUsuario to set
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellidoPaterno
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * @param apellidoPaterno the apellidoPaterno to set
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * @return the apellidoMaterno
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * @param apellidoMaterno the apellidoMaterno to set
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the fechaExpiracion
	 */
	public Date getFechaExpiracion() {
		return fechaExpiracion;
	}

	/**
	 * @param fechaExpiracion the fechaExpiracion to set
	 */
	public void setFechaExpiracion(Date fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}
	
	public String getAplicacion() {
		return aplicacion;
	}
	
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	/**
	 * @return the idUsuario
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Regresa el id de la persona
	 * @return
	 */
	public Long getIdPersona() {
        return idPersona;
    }
	
	/**
	 * Asigna el id de la persona
	 * @param idPersona
	 */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
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

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

}
