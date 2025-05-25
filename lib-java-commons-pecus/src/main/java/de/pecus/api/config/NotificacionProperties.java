package de.pecus.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Archivo de propiedades que lee la configuracion del application.properties de
 * Spring para obtener las propiedades de configuracion para notificaciones.
 * 
 * Dentro del archivo application.properties, las propiedades relacionadas a la configuracion
 * para las notificaciones, deben tener el prefijo "notification"
 * 
 * @author Alfredo Martinez Ramirez
 */
@ConfigurationProperties
public class NotificacionProperties {

    /** Email a mostrar como emisor de las notifiaciones, por email */
	@Value("${notification.email.sender-mail}")
    private String senderEmail;

    /** Nombre del emisor a mostrar en el env\u00edo del email */
	@Value("${notification.email.sender-name}")
    private String senderName;

    /** @return senderEmail */
    public String getSenderEmail() {

        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {

        this.senderEmail = senderEmail;
    }

    /** @return senderName */
    public String getSenderName() {

        return senderName;
    }

    public void setSenderName(String senderName) {

        this.senderName = senderName;
    }
}
