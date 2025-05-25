package de.pecus.api.vo;

public class FileBase64EmailVO {
    
    /** Nombre del archivo */
    private String nombreFile;
    
    /** Cadena con el archivo en base64 */
    private String fileBase64;
  
    
    /** Content-type del archivo, por ejemplo application/pdf, application/vnd.ms-excel, application/zip */
    private String contentType;
    
    /**
     * Constructor vacio.
     */
    public FileBase64EmailVO() {
    	super();
    }
    


    /**
     * Constructor con parametros FileBase64EmailVO
     * @param nombreFile
     * @param fileBase64
     * @param contentType
     */
	public FileBase64EmailVO(String nombreFile, String fileBase64, String contentType) {
		super();
		this.nombreFile = nombreFile;
		this.fileBase64 = fileBase64;
		this.contentType = contentType;
	}


	/**
	 * @return the nombreFile
	 */
	public String getNombreFile() {
		return nombreFile;
	}


	/**
	 * @param nombreFile the nombreFile to set
	 */
	public void setNombreFile(String nombreFile) {
		this.nombreFile = nombreFile;
	}


	/**
	 * @return the fileBase64
	 */
	public String getFileBase64() {
		return fileBase64;
	}


	/**
	 * @param fileBase64 the fileBase64 to set
	 */
	public void setFileBase64(String fileBase64) {
		this.fileBase64 = fileBase64;
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


    
}