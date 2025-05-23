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
 * Entidad que gestiona los accesos de un determinado rol
 * @author jose.ribelles
 * @version 1.0
 * @created 24-jul.-2019 11:27:46 a. m.
 */
@Entity
@Table(name = "ROL_FUNCION")
public class RolFuncionDO extends AuditBase<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4564042390104081935L;

	@Id
	@SequenceGenerator(name="ROL_FUNCION_ID_GENERATOR", sequenceName="SEQ_PK_REL_ROL_FUNCION_ID",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ROL_FUNCION_ID_GENERATOR")
	@Column(name="PK_ID")
	private Long id;
	 
	/****************************  RELACION 1..N ******************************/
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ROL_ID" , referencedColumnName = "PK_ID")
	private RolDO rol;
	 
	/****************************  RELACION 1..N ******************************/
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_FUNCION_ID" , referencedColumnName = "PK_ID")
	private FuncionDO funcion;

	public RolFuncionDO(){

	}

	public void finalize() throws Throwable {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RolDO getRol() {
		return rol;
	}

	public void setRol(RolDO rol) {
		this.rol = rol;
	}

	public FuncionDO getFuncion() {
		return funcion;
	}

	public void setFuncion(FuncionDO funcion) {
		this.funcion = funcion;
	}

}