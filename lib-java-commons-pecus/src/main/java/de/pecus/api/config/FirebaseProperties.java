package de.pecus.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Archivo de propiedades que lee la configuracion del application.properties de
 * Spring para obtener las propiedades de firebase como la URL de la BD de
 * firebase
 * 
 * Dentro del archivo application.properties, las propiedades relacionadas a las
 * conexiones con firebase, deben tener el prefijo "firebase"
 * 
 * @author Juan Carlos Contreras
 */
@ConfigurationProperties(prefix = "firebase")
public class FirebaseProperties {

    /**
     * URL de la base de datos de firebase, se obtiene desde la consola de
     * administracion
     */
    private String databaseURL;

    public String getDatabaseURL() {
        return databaseURL;
    }

    public void setDatabaseURL(String databaseURL) {
        this.databaseURL = databaseURL;
    }

}
