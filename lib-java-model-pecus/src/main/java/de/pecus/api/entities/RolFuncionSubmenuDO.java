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
 * @author jose.ribelles
 * @version 1.0
 * @created 24-jul.-2019 11:27:46 a. m.
 */
@Entity
@Table(name = "ROL_FUNCION_SUBMENU")
public class RolFuncionSubmenuDO extends AuditBase<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3097698319290177827L;

	@Id
	@SequenceGenerator(name="ROL_FUN_SUBMENU_ID_GENERATOR", sequenceName="SEQ_PK_REL_RFUN_SUBMENU_ID",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ROL_FUN_SUBMENU_ID_GENERATOR")
	@Column(name="PK_ID")
	private Long id;
	 
	/****************************  RELACION 1..N ******************************/
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_SUBMENU_ID" , referencedColumnName = "PK_ID")
	private SubmenuDO submenu;
	 
	/****************************  RELACION 1..N ******************************/
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_FUNCION_ID" , referencedColumnName = "PK_ID")
	private FuncionDO funcion;

	public RolFuncionSubmenuDO(){

	}

	public void finalize() throws Throwable {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SubmenuDO getSubmenu() {
		return submenu;
	}

	public void setSubmenu(SubmenuDO submenu) {
		this.submenu = submenu;
	}

	public FuncionDO getFuncion() {
		return funcion;
	}

	public void setFuncion(FuncionDO funcion) {
		this.funcion = funcion;
	}
}