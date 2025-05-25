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
 * @author jose.ribelles
 * @version 1.0
 * @created 24-jul.-2019 11:27:45 a. m.
 */
@Entity
@Table(name = "MENU")
public class MenuDO extends AuditBase<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6052628826914190255L;
	
	@Id
	@SequenceGenerator(name="MENU_ID_GENERATOR", sequenceName="SEQ_PK_MENU_ID",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MENU_ID_GENERATOR")
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
	
	@Column(name =  "DN_ORDEN")
	private Long orden;
	
	@Column(name = "DN_PASO_CONFIG")
	private Double pasoConfig;

	public MenuDO(){

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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public Double getPasoConfig() {
		return pasoConfig;
	}

	public void setPasoConfig(Double pasoConfig) {
		this.pasoConfig = pasoConfig;
	}

 	
}