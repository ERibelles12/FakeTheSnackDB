package de.pecus.api.vo;

public class DownloadImagenRequestVO {
    /**
     * idNombre del tipo_componente.
     */
    private Long tipoComponente;

    /**
     * id del componente;
     * id del registro a buscar.
     */
    private Long componente;

    /**
     * String del idNombre de tipo de imagen.
     */
    private String tipoImagen;

	public Long getTipoComponente() {
		return tipoComponente;
	}

	public void setTipoComponente(Long tipoComponente) {
		this.tipoComponente = tipoComponente;
	}

	public Long getComponente() {
		return componente;
	}

	public void setComponente(Long componente) {
		this.componente = componente;
	}

	public String getTipoImagen() {
		return tipoImagen;
	}

	public void setTipoImagen(String tipoImagen) {
		this.tipoImagen = tipoImagen;
	}
    
}
