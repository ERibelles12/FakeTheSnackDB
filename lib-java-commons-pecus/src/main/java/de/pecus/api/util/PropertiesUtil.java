package de.pecus.api.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utileria para cargar properties fuera del contexto del proyecto
 * 
 * @author Juan Carlos Contreras
 */
public final class PropertiesUtil {

    /**
     * Constructor privado para evitar instanciacion
     */
    private PropertiesUtil() {
    }
    
    /**
     * Genera un objeto Properties a partir de la ruta del archivo recibida
     * 
     * @param filePath - ruta del archivo que se va a leer
     * @return Properties con las propiedades cargadas
     * @throws IOException 
     */
    public static final Properties loadProperties(String filePath) throws IOException {
        Properties properties = null;
        
        // Abrimos el input stream
        InputStream input = new FileInputStream(filePath);
        properties = new Properties();
        
        // Cargamos el properties
        properties.load(input);
        
        // Cerramos el input stream
        input.close();
        
        return properties;
    }
}