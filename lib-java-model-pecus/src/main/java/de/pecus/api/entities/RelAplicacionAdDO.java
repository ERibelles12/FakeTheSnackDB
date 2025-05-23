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
 * @author acuevas
 * @version 1.0
 * @created 21-ago.-2022 03:32:27 p. m.
 */
@Entity
@Table(name = "REL_APLICACION_AD")
public class RelAplicacionAdDO extends AuditBase<Long> implements Serializable {

	/**
	 * Serial version UID de la clase
	 */
	private static final long serialVersionUID = 1274345277763896245L;

	@Id
	@SequenceGenerator(name="REL_APLICACION_AD_ID_GENERATOR", sequenceName="SEQ_PK_REL_APLICACION_AD_ID",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="REL_APLICACION_AD_ID_GENERATOR")
	@Column(name = "PK_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_APLICACION_ID", referencedColumnName = "PK_ID")
	private AplicacionDO aplicacion;
	
	@Column(name = "DX_AZURE_APP_SCOPE")
	private String azureAppScope;

	@Column(name = "DX_AZURE_APP_TENANT_DNS")
	private String azureAppTenantDns;

	@Column(name = "DX_AZURE_CLIENT_ID")
	private String azureClientId;
	
	@Column(name = "DX_AZURE_CLIENT_SECRET")
	private String azureClientSecret;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAzureAppScope() {
		return azureAppScope;
	}

	public void setAzureAppScope(String azureAppScope) {
		this.azureAppScope = azureAppScope;
	}

	public String getAzureAppTenantDns() {
		return azureAppTenantDns;
	}

	public void setAzureAppTenantDns(String azureAppTenantDns) {
		this.azureAppTenantDns = azureAppTenantDns;
	}

	public AplicacionDO getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(AplicacionDO aplicacion) {
		this.aplicacion = aplicacion;
	}

	/**
	 * @return the azureAppClientId
	 */
	public String getAzureClientId() {
		return azureClientId;
	}

	/**
	 * @param azureClientId the azureAppClientId to set
	 */
	public void setAzureClientId(String azureClientId) {
		this.azureClientId = azureClientId;
	}

	/**
	 * @return the azureAppClientSecret
	 */
	public String getAzureClientSecret() {
		return azureClientSecret;
	}

	/**
	 * @param azureClientSecret the azureAppClientSecret to set
	 */
	public void setAzureClientSecret(String azureClientSecret) {
		this.azureClientSecret = azureClientSecret;
	}

}