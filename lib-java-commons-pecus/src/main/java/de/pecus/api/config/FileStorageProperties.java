package de.pecus.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Archivo de propiedades que lee la configuracion del application.properties de
 * Spring para obtener las propiedades de configuracion para la carga de archivos a la nube.
 * 
 * Dentro del archivo application.properties, las propiedades relacionadas a la configuracion
 * para la carga de archivos a la nube, deben tener el prefijo "fileupload"
 * 
 * @author Alfredo Martinez Ramirez
 */
@Component
@ConfigurationProperties("fileupload")
public class FileStorageProperties {
	
	/**
	 * Indica el tama\u00f1o m\u00e1ximo permitido para las im\u00e1genes a guardar.
	 */
	private int maxSize;
	
	/**
	 * @return the maxSize
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * @param maxSize the maxSize to set
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

}
