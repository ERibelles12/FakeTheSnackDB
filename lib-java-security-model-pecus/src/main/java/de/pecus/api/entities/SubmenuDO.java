package de.pecus.api.entities;

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
@Table(name = "SUBMENU")
public class SubmenuDO extends AuditBase<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4393515439749140296L;

	@Id
	@SequenceGenerator(name="SUBMENU_ID_GENERATOR", sequenceName="SEQ_PK_SUBMENU_ID",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SUBMENU_ID_GENERATOR")
	@Column(name="PK_ID")
	private Long id;
	
	@Column(name =  "DX_ID_NOMBRE" )
	private String idNombre;
	
	@Column(name =  "DX_DESCRIPCION" )
	private String descripcion;
	
	@Column(name =  "DX_URL_PATH" )
	private String path;

	@Column(name =  "DX_IMAGE_PATH" )
	private String imagePath;

	/****************************  RELACION 1..N ******************************/
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_MENU_ID" , referencedColumnName = "PK_ID")
	private MenuDO menu;
	
	
	public SubmenuDO(){

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public MenuDO getMenu() {
		return menu;
	}

	public void setMenu(MenuDO menu) {
		this.menu = menu;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	
}