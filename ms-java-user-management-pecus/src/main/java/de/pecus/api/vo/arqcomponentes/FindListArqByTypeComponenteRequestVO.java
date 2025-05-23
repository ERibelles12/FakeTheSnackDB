package de.pecus.api.vo.arqcomponentes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/******************** FIN SECCION IMPORTS ************************************/

/**
 * Esta entidad es el diccionario de componentes de la plataforma. En esta
 * entidad se gestionan los datos de los componentes y abstraen los
 * comportamiento de los mismos. Algunos componentes son: - Pantallas - Colas -
 * Impresoras Los campos parametros permiten establecer valores que las
 * funciones de arquitectura pueden utilizar para la invocacion y comportamiento
 * del componente.
 * 
 * @author jose.ribelles
 * @version 1.0
 * @created 15-ago.-2019 12:58:50 p. m.
 */
@JsonInclude(Include.NON_NULL)
public class FindListArqByTypeComponenteRequestVO {

    private Long idTipoArqComponente;
    private String idNombre;
    private String idNombreTipoArqComponente;

    public FindListArqByTypeComponenteRequestVO() {

    }

    public Long getIdTipoArqComponente() {
        return idTipoArqComponente;
    }

    public void setIdTipoArqComponente(Long idTipoArqComponente) {
        this.idTipoArqComponente = idTipoArqComponente;
    }

    public String getIdNombre() {
        return idNombre;
    }

    public void setIdNombre(String idNombre) {
        this.idNombre = idNombre;
    }

	/**
	 * @return the idNombreTipoArqComponente
	 */
	public String getIdNombreTipoArqComponente() {
		return idNombreTipoArqComponente;
	}

	/**
	 * @param idNombreTipoArqComponente the idNombreTipoArqComponente to set
	 */
	public void setIdNombreTipoArqComponente(String idNombreTipoArqComponente) {
		this.idNombreTipoArqComponente = idNombreTipoArqComponente;
	}

}