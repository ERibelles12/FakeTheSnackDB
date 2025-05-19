package de.pecus.api.security.services;

/**
 * 
 * Interfaz de logica de negocio para autorizacion de la funcion con rol.
 *
 * @author Luis Enrique Sanchez Santiago
 *
 */
public interface ValidateMenuService {
    
    /**
     * Metodo para la validacion de la funcion contra el rol en la peticion.
     * 
     * @param token token de la peticion para obtener el rol del usuario.
     * @param requestURI endpoint de la peticion.
     * 
     * @return Boolean verdadero si el rol y la funcion son validos para la peticion, si no, regresa falso.
     */
    public Boolean validateRolFuncionByRequestURI(String token, String uri);

}
