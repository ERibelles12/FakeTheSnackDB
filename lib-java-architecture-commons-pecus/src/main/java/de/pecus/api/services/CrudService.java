/**
 * 
 */
package de.pecus.api.services;

import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;

/**
 * 
 * Interfaz de logica de negocio para administracion de entidades
 *
 * @author Luis Enrique Sanchez Santiago
 *
 * @param <T>  Clase entity
 * @param <ID> Clase id de entity
 * @param <V>  Clase VO que mapea entity
 */
public interface CrudService<T, I, V> {

	/**
	 * Crea un nuevo registro de telefono
	 * 
	 * @param request Objeto con parametros de entrada de telefono
	 * 
	 * @return Objeto VO con Id generado
	 */
	ResponseVO<V> create(RequestVO<V> request);

	/**
	 * Actualiza un registro de telefono
	 * 
	 * @param request Objeto con parametros de entrada de telefono
	 * 
	 * @return Objeto VO actualizado
	 */
	ResponseVO<V> update(RequestVO<V> request);

	/**
	 * Elimina un registro de telefono
	 * 
	 * @param request Objeto con parametros de entrada de telefono
	 * 
	 * @return Objeto VO eliminado
	 */
	ResponseVO<V> delete(RequestVO<V> request);

	/**
	 * Valida que los parametros para la operacion de insercion
	 * sean correctos
	 * 
	 * @param request  Objeto con los parametros de entrada
	 * @param response Respuesta donde se agregan los errores
	 * @return true si todos los parametros son correctos
	 */
	boolean validateParametersCreate(RequestVO<V> request, ResponseVO<V> response);

	/**
	 * Valida que los parametros para la operacion de actualizacion sean correctos
	 * 
	 * @param request  Objeto con los parametros de entrada
	 * @param response Respuesta donde se agregan los errores
	 * @return true si todos los parametros son correctos
	 */
	boolean validateParametersUpdate(RequestVO<V> request, ResponseVO<V> response);

	/**
	 * Valida que los parametros para la operacion de eliminacion sean correctos
	 * 
	 * @param request  Objeto con los parametros de entrada
	 * @param response Respuesta donde se agregan los errores
	 * @return true si todos los parametros son correctos
	 */
	boolean validateParametersDelete(RequestVO<V> request, ResponseVO<V> response);

	/**
	 * Obtiene un telefonoDO a partir de un VO
	 * 
	 * @param telefonoVO Objeto VO a transformar
	 * @return Objeto de entidad para actualizacion
	 */
	T transformVO(V telefonoVO);

	/**
	 * Obtiene un telefonoVO a partir de un DO
	 * 
	 * @param telefonoDO Objeto DO a transformar
	 * @return Objeto de transporte VO para retorno de resultados
	 */
	V transformDO(T entity);

	/**
	 * Asigna los valores de un objeto VO a un objeto DO para su registro o
	 * actualizacion
	 * 
	 * @param entity  Objeto de entidad a modificar o crear
	 * @param request Objeto con los datos a actualizar
	 */
	T copyValues(T entity, V request);
	
	/**
	 * Revisa si existe el registre con el ID obtenido
	 * 
	 * @param request del objeto a buscar
	 * @return true si el registro existe en la BD, false si no existe el registro
	 */
	ResponseVO<Boolean> exists(RequestVO<Long> request);
}
