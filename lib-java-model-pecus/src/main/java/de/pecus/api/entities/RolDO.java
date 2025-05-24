package de.pecus.api.entities;

/******************** SECCION IMPORTS ***************************************/
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/******************** FIN SECCION IMPORTS ************************************/

/**
 * El rol determinara las funciones de los usuarios.  El rol da acceso y limita el
 * acceso a las fuciones del sistema
 * 
 * Ejem:
 * Publico
 * Propietario
 * Residente
 * Invitado
 * Inquilino
 * AIRBNB
 * Seguridad
 * Finanzas
 * @author jose_
 * @version 1.0
 * @created 24-jul.-2019 11:27:46 a. m.
 */
@Entity
@Table(name = "ROL")
public class RolDO extends AuditBase<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2718948502170246588L;

	@Id
	@SequenceGenerator(name="ROL_ID_GENERATOR", sequenceName="SEQ_PK_ROL_ID",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ROL_ID_GENERATOR")
	@Column(name="PK_ID")
	private Long id;
	
	@Column(name =  "DX_ID_NOMBRE" )
	private String idNombre;
	@Column(name =  "DX_DESCRIPCION" )
	private String descripcion;
	 
		/****************************  RELACION 1..N ******************************/
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPO_ROL_ID" , referencedColumnName = "PK_ID")
	private TipoRolDO tipoRol;
	
	/****************************  RELACION 1..N ******************************/
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_APLICACION_ID" , referencedColumnName = "PK_ID")
	private AplicacionDO aplicacion;


	public RolDO(){

	}

	public void finalize() throws Throwable {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoRolDO getTipoRol() {
		return tipoRol;
	}

	public void setTipoRol(TipoRolDO tipoRol) {
		this.tipoRol = tipoRol;
	}

	public String getIdNombre() {
		return idNombre;
	}

	public void setIdNombre(String idNombre) {
		this.idNombre = idNombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public AplicacionDO getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(AplicacionDO aplicacion) {
		this.aplicacion = aplicacion;
	}

}