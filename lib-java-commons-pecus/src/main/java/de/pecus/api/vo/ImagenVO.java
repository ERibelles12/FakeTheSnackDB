package de.pecus.api.vo;

import java.io.File;

/**
 * Clase utilizada para cargar y descargar imagenes
 * 
 * @author Juan Carlos Contreras Vazquez
 */
public class ImagenVO {

    /**
     * Identificador del objeto
     */
    private Long id;

    /** Nombre del archivo */
    private String fileName;

    /** Nombre con el que se guardo en el provider(Ej. AWS)*/
    private String nombreMD5;
    
    /** Tamanio del archivo */
    private Long size;
    
    /** Contenido del archivo */
    private File file;
    
    /** Tipo de contenido */
    private String contentType;
    
    /** Estatus de la imagen */
    private Long estatus;
    
    /** Identificador unico del componente al que pertenece el registro */
    private Long componente;
    
    /** Nombre del tipo de imagen asignado */
    private String nombreTipoImagen;
    
    /** Nombre del tipo de componente al que se asignar\u00e1 */
    private Long tipoComponente;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the nombreMD5
	 */
	public String getNombreMD5() {
		return nombreMD5;
	}

	/**
	 * @param nombreMD5 the nombreMD5 to set
	 */
	public void setNombreMD5(String nombreMD5) {
		this.nombreMD5 = nombreMD5;
	}

	/**
	 * @return the size
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the estatus
	 */
	public Long getEstatus() {
		return estatus;
	}

	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(Long estatus) {
		this.estatus = estatus;
	}

	/**
	 * @return the componente
	 */
	public Long getComponente() {
		return componente;
	}

	/**
	 * @param componente the componente to set
	 */
	public void setComponente(Long componente) {
		this.componente = componente;
	}

	/**
	 * @return the nombreTipoImagen
	 */
	public String getNombreTipoImagen() {
		return nombreTipoImagen;
	}

	/**
	 * @param nombreTipoImagen the nombreTipoImagen to set
	 */
	public void setNombreTipoImagen(String nombreTipoImagen) {
		this.nombreTipoImagen = nombreTipoImagen;
	}

	/**
	 * @return the nombreTipoComponente
	 */
	public Long getTipoComponente() {
		return tipoComponente;
	}

	/**
	 * @param nombreTipoComponente the nombreTipoComponente to set
	 */
	public void setTipoComponente(Long nombreTipoComponente) {
		this.tipoComponente = nombreTipoComponente;
	}	
}
