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
@Table(name = "ROL_FUNCION_MENU")
public class RolFuncionMenuDO extends AuditBase<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1594935079384875003L;

	@Id
	@SequenceGenerator(name="ROL_FUN_MENU_ID_GENERATOR", sequenceName="SEQ_PK_REL_ROL_FUNCION_MENU_ID",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ROL_FUN_MENU_ID_GENERATOR")
	@Column(name="PK_ID")
	private Long id;
	 
	/****************************  RELACION 1..N ******************************/
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ROL_FUNCION_ID" , referencedColumnName = "PK_ID")
	private RolFuncionDO rolFuncion;
	 
	/****************************  RELACION 1..N ******************************/
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_MENU_ID" , referencedColumnName = "PK_ID")
	private MenuDO menu;

	public RolFuncionMenuDO(){

	}

	public void finalize() throws Throwable {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RolFuncionDO getRolFuncion() {
		return rolFuncion;
	}

	public void setRolFuncion(RolFuncionDO rolFuncion) {
		this.rolFuncion = rolFuncion;
	}

	public MenuDO getMenu() {
		return menu;
	}

	public void setMenu(MenuDO menu) {
		this.menu = menu;
	}

}