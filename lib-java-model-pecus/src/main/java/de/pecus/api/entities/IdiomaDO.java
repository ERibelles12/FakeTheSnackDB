package de.pecus.api.entities;

/******************** SECCION IMPORTS ***************************************/
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad que contiene los idiomas
 * @author jose.ribelles
 * @version 1.0
 * @created 24-jul.-2019 11:27:26 a. m.
 */
@Entity
@Table(name = "IDIOMA")
public class IdiomaDO extends AuditBase<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7575436739156776212L;
	/**
	 * Identificador unico de registro
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name =  "PK_ID" )
	private Long id;
	/**
	 * Nombre del continente
	 */
	@Column(name =  "DX_ID_NOMBRE" )
	private String idNombre;
	@Column(name =  "DX_DESCRIPCION" )
	private String descripcion;

	public IdiomaDO(){

	}

	public void finalize() throws Throwable {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}