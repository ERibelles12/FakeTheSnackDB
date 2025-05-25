package de.pecus.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Archivo de propiedades que lee la configuracion del
 * application.properties de Spring para  obtener la
 * bandera de validacion por menu-rol
 * 
 * Dentro del archivo application.properties, las propiedades
 * relacionadas al envio de notificaciones, debe tener el
 * prefijo security
 *  
 * @author Luis Enrique Sanchez Santiago
 */

@ConfigurationProperties(prefix = "security")
public class SecurityRolMenuProperties {
	/**
	 * Bandera para activar la validacion del rol - menu - funcion.
	 *   
	 */
	private boolean rolmenuValidation;

	public boolean isRolmenuValidation() {
		return rolmenuValidation;
	}

	public void setRolmenuValidation(boolean rolmenuValidation) {
		this.rolmenuValidation = rolmenuValidation;
	}
	
}
