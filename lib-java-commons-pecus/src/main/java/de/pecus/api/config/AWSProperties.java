package de.pecus.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Archivo de propiedades que lee la configuracion del
 * application.properties de Spring para  obtener las
 * cadena de conexión de firebase
 * 
 * Dentro del archivo application.properties, las propiedades
 * relacionadas al envio de notificaciones, debe tener el
 * prefijo ntoficion
 *  
 * @author Juan Carlos Contreras
 */
@Component
@ConfigurationProperties(prefix = "aws")
public class AWSProperties {
    
    /**
     * Nombre del bucket utilizado para las imagenes
     */
    private String imagesBucket;

    /**
     * Nombre del bucket utilizado para los archivos
     */
    private String archivosBucket;
    
    /**
     * Nombre del bucket utilizado para los archivos
     */
    private String reportesBucket;
    
    /**
     * Nombre del bucket utilizado para los documentos del devPortal
     */
    private String docsDevportal;
    
    /**
     * Nombre del bucket utilizado para los documentos asoacioados a una api del devPortal
     */
    private String apidocsDevportal;

    /**
     * Numero de cuenta de Amazon
     */
    private String account;
    
    /**
     * Perfil de la cuenta 
     */
    private String profile;
    
    /**
     * Region de la cuenta
     */
    private String region;

    public String getArchivosBucket() {
		return archivosBucket;
	}

	public void setArchivosBucket(String archivosBucket) {
		this.archivosBucket = archivosBucket;
	}

	public String getImagesBucket() {
        return imagesBucket;
    }

    public void setImagesBucket(String imagesBucket) {
        this.imagesBucket = imagesBucket;
    }

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getReportesBucket() {
		return reportesBucket;
	}

	public void setReportesBucket(String reportesBucket) {
		this.reportesBucket = reportesBucket;
	}

	public String getDocsDevportal() {
		return docsDevportal;
	}

	public void setDocsDevportal(String docsDevportal) {
		this.docsDevportal = docsDevportal;
	}

	public String getApidocsDevportal() {
		return apidocsDevportal;
	}

	public void setApidocsDevportal(String apidocsDevportal) {
		this.apidocsDevportal = apidocsDevportal;
	}
	
	

}