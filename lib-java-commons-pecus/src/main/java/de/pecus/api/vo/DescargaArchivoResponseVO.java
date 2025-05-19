package de.pecus.api.vo;

/**
 * Clase utilizada para la respuesta de la descarga de archivos
 * 
 * @author Alfredo Martinez Ramirez
 */
public class DescargaArchivoResponseVO {

    /**
     * Identificador unido de la imagen en BB.DD.
     * 
     */
    private Long id;

    /** Nombre del archivo */
    private String fileName;

    /** Archivo en Base64 */
    private String file;

    /** Tipo de archivo, ej. image/png */
    private String fileType;

    /**
     * - inline - results in the attached file being displayed automatically within
     * the message - attachment - results in the attached file requiring some action
     * to be taken before it is displayed (e.g. opening or downloading the file).
     */
    private String imageDisposition;

    /** Exito o error de la respuesta */
    private Boolean success;

    /** Mensaje de error en caso de fallar al obtener el objeto */
    private String error;
    /**
     * Orden, opcional
     */
    private Integer orden;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String imageType) {
        this.fileType = imageType;
    }

    public String getImageDisposition() {
        return imageDisposition;
    }

    public void setImageDisposition(String imageDisposition) {
        this.imageDisposition = imageDisposition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	/**
	 * Orden, opcional
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * Orden, opcional
	 * @param orden the orden to set
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}

}