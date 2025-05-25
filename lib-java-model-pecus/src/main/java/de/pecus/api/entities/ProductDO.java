package de.pecus.api.entities;

/******************** SECCION IMPORTS ***************************************/
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * El tipo de rol determinara ciertos comportamientos, por ejemplo,
 * 
 * - El tipo de Rol "Admin", "adminCondo",  no son editable.
 * 
 * - En cambio el administrador puede generar diferentes perfiles que si pueden
 * ser modificables.
 * 
 * Tipo:
 * Publico
 * KrisnaGarcia
 * @author jose.ribelles
 * @version 1.0
 * @created 24-jul.-2019 11:27:46 a. m.
 */
@Entity
@Table(name = "PRODUCT")
public class ProductDO extends AuditBase<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5382607608047169433L;
	
	@Id
	@Column(name="PK_ID")
	private Long id;
	@Column(name =  "DX_NAME" )
	private String name;
	@Column(name =  "DX_DESCRIPTION" )
	private String descripcion;
	

	public ProductDO(){

	}

	public void finalize() throws Throwable {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}