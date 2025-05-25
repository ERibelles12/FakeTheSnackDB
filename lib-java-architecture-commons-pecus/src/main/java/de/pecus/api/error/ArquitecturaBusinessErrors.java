package de.pecus.api.error;

/**
 * Enumeracion para manejo de errores.
 * 
 * @author Luis Enrique Sanchez Santiago
 */
public final class ArquitecturaBusinessErrors {

    private ArquitecturaBusinessErrors() {
        // No debe instanciarse
    }
    
    public static final String MIN_SIZE_DESCRIPCION_ERROR =  "MIN_SIZE_DESCRIPCION_ERROR" ;

    public static final String UPLOAD_IMAGEN_ERROR =  "UPLOAD_IMAGEN_ERROR" ;

    public static final String DOWNLOAD_ARCHIVO_ERROR =  "DOWNLOAD_ARCHIVO_ERROR" ;
    
    public static final String DOWNLOAD_ARCHIVO_NOT_FOUND_ERROR =  "DOWNLOAD_ARCHIVO_NOT_FOUND_ERROR" ;
    
    public static final String TOKEN_INVALIDO = "TOKEN_INVALIDO";
    
    public static final String FIREBASE_HOST_NO_CONNECTION = "FIREBASE_HOST_NO_CONNECTION";
    
    public static final String REQUIRED_ARQ_COMPONENTE_ERROR = "REQUIRED_ARQ_COMPONENTE_ERROR";
    
    public static final String NOT_ARQ_COMPONENTE_ERROR = "NOT_ARQ_COMPONENTE_ERROR";
    
	public static final String ACCESS_PROVIDER_AUTHENTICATION_ERROR = "ACCESS_PROVIDER_AUTHENTICATION_ERROR";
	
	public static final String ACCESS_PROVIDER_ASSIGN_CARD_ERROR = "ACCESS_PROVIDER_ASSIGN_CARD_ERROR";
	
	public static final String ACCESS_PROVIDER_REQUIRED_DEVICE_ID = "ACCESS_PROVIDER_REQUIRED_DEVICE_ID";
	
	public static final String  ACCESS_PROVIDER_NOT_CREDENTIAL_CONTAINER_ERROR = "ACCESS_PROVIDER_NOT_CREDENTIAL_CONTAINER_ERROR";
	
    public static final String REQUIRED_ID_COMPANIA_ERROR = "REQUIRED_ID_COMPANIA_ERROR";
    
    public static final String REQUIRED_ID_LOTE_ERROR = "REQUIRED_ID_LOTE_ERROR";
    
    public static final String REQUIRED_ID_USUARIO_ERROR = "REQUIRED_ID_USUARIO_ERROR";
    
    public static final String REQUIRED_ID_CLASIFICACION_ERROR = "REQUIRED_ID_CLASIFICACION_ERROR";
    
    public static final String REQUIRED_DESCRIPCION_CORTA_ERROR = "REQUIRED_DESCRIPCION_CORTA_ERROR";
    
    public static final String REQUIRED_ID_GARANTIA_ERROR = "REQUIRED_ID_GARANTIA_ERROR";
    
    public static final String REQUIRED_NOMBRE_IMAGEN_ERROR = "REQUIRED_NOMBRE_IMAGEN_ERROR";
    
    public static final String REQUIRED_PATH_IMAGEN_ERROR = "REQUIRED_PATH_IMAGEN_ERROR";
    
    public static final String REQUIRED_BASE64_ERROR = "REQUIRED_BASE64_ERROR";
    
    public static final String LIST_CONTENT_IMAGEN_ERROR = "LIST_CONTENT_IMAGEN_ERROR";

    public static final String DELETE_IMAGEN_ERROR = "DELETE_IMAGEN_ERROR";
    
    public static final String CREATE_GARANTIA_SALES_FORCE_ERROR = "ARQ_CREATE_GARANTIA_SALES_FORCE_ERROR";
    
    public static final String DROPBOX_DOWNLOAD_IMAGE_ERROR = "DROPBOX_DOWNLOAD_IMAGE_ERROR";
        
}
