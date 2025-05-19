/**
 * 
 */
package de.pecus.api.services;

import java.util.List;

import de.pecus.api.entities.IdiomaDO;
import de.pecus.api.vo.CreateIdiomaRequestVO;
import de.pecus.api.vo.FindDetailIdiomaRequestVO;
import de.pecus.api.vo.FindDetailIdiomaResponseVO;
import de.pecus.api.vo.FindListIdiomaRequestVO;
import de.pecus.api.vo.FindListIdiomaResponseVO;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.UpdateIdiomaRequestVO;

/**
 * Clase de logica de negocio para administracionde Tipos de Telefonos
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
public interface IdiomaService {

	/**
	 * Crea un nuevo registro de tipo de telefono
	 * 
	 * @param request Objeto con< parametros de entrada de tipo de telefono
	 * 
	 * @return Id generado
	 */
	ResponseVO<Long> create(RequestVO<CreateIdiomaRequestVO> request);

	/**
	 * Actualiza un registro de tipo de telefono
	 * 
	 * @param request Objeto con parametros de entrada de tipo de telefono
	 * 
	 * @return Id actualizado
	 */
	ResponseVO<Long> update(RequestVO<UpdateIdiomaRequestVO> request);

	/**
	 * Marca un registro como eliminado un registro de tipo de telefono
	 * 
	 * @param request Objeto con parametros de entrada de tipo de telefono
	 * 
	 * @return Id eliminado
	 */
	ResponseVO<Boolean> delete(RequestVO<Long> request);

	/**
	 * Consulta un tipo de telefono por Id
	 * 
	 * @return Objeto VO con los datos encontrados
	 * @param Id      Identificador del registro a buscar
	 * 
	 * @param request Objeto con los datos de busqueda
	 */
	ResponseVO<FindDetailIdiomaResponseVO> findDetail(RequestVO<FindDetailIdiomaRequestVO> request);

	/**
	 * Consulta la base de datos en base a varios parametros
	 * 
	 * @return ReponseVO con los datos encontrados
	 * 
	 * @param request Objeto con parametros de entrada de tipo de telefono
	 */
	ResponseVO<List<FindListIdiomaResponseVO>> findList(RequestVO<FindListIdiomaRequestVO> request);
	
	/**
     * Metodo encargado de validar la existencia del tipo telefono por su identificador
     * 
     * @param idIdioma Id del idioma
     * @param idNombre Id nombre del idioma
     * @param idioma Idioma enviado para consulta
     * 
     * @return Objeto DO con los datos de la entidad encontrada
     * @throws PecusException con objeto BusinessError, en caso de no encontrar el registro
     */
    IdiomaDO exists(Long idIdioma, String idNombre, String idioma);

}
