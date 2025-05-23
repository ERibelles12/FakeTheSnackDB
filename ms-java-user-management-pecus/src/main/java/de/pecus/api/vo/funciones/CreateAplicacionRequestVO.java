package de.pecus.api.vo.funciones;

import java.io.Serializable;

/**
 * 
 * @author Jose_Luis_Garcia
 *
 *	Clase con los parametros de entrada a la invocacion del metodo 
 *  create de la case EventType
 *
 */
public class CreateAplicacionRequestVO implements Serializable{


	/**
	 * Serial version UID de la clase
	 */
	private static final long serialVersionUID = -6788305149503378511L;

	// Identificador alfanumerico
	private String idNombre;

	// Descripcion del registro
	private String tipoAutenticacion;
	
	private String azureAppScope;

	private String azureAppTenantDns;

	private String azureClientId;
	
	private String azureClientSecret;

	public String getIdNombre() {
		return idNombre;
	}

	public void setIdNombre(String idNombre) {
		this.idNombre = idNombre;
	}

	public String getTipoAutenticacion() {
		return tipoAutenticacion;
	}

	public void setTipoAutenticacion(String tipoAutenticacion) {
		this.tipoAutenticacion = tipoAutenticacion;
	}

	/**
	 * @return the azureAppScope
	 */
	public String getAzureAppScope() {
		return azureAppScope;
	}

	/**
	 * @param azureAppScope the azureAppScope to set
	 */
	public void setAzureAppScope(String azureAppScope) {
		this.azureAppScope = azureAppScope;
	}

	/**
	 * @return the azureAppTenantDns
	 */
	public String getAzureAppTenantDns() {
		return azureAppTenantDns;
	}

	/**
	 * @param azureAppTenantDns the azureAppTenantDns to set
	 */
	public void setAzureAppTenantDns(String azureAppTenantDns) {
		this.azureAppTenantDns = azureAppTenantDns;
	}

	/**
	 * @return the azureClientId
	 */
	public String getAzureClientId() {
		return azureClientId;
	}

	/**
	 * @param azureClientId the azureClientId to set
	 */
	public void setAzureClientId(String azureClientId) {
		this.azureClientId = azureClientId;
	}

	/**
	 * @return the azureClientSecret
	 */
	public String getAzureClientSecret() {
		return azureClientSecret;
	}

	/**
	 * @param azureClientSecret the azureClientSecret to set
	 */
	public void setAzureClientSecret(String azureClientSecret) {
		this.azureClientSecret = azureClientSecret;
	}
}
