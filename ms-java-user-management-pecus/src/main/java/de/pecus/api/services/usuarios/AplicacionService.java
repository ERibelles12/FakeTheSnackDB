package de.pecus.api.services.usuarios;

import java.util.List;

import de.pecus.api.entities.AplicacionDO;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.funciones.CreateAplicacionRequestVO;
import de.pecus.api.vo.funciones.DeleteAplicacionRequestVO;
import de.pecus.api.vo.funciones.FindDetailAplicacionRequestVO;
import de.pecus.api.vo.funciones.FindDetailAplicacionResponseVO;
import de.pecus.api.vo.funciones.FindListAplicacionRequestVO;
import de.pecus.api.vo.funciones.FindListAplicacionResponseVO;
import de.pecus.api.vo.funciones.UpdateAplicacionRequestVO;

/**
 * Clase de logica de negocio para administracion de Aplicaciones
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
public interface AplicacionService {

	/**
	 * Crea un nuevo registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id generado
	 */
	ResponseVO<Long> create (RequestVO<CreateAplicacionRequestVO> request);

	/**
	 * Actualiza un registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id actualizado
	 */
	ResponseVO<Long> update(RequestVO<UpdateAplicacionRequestVO> request);

	/**
	 * Marca un registro como eliminado un registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id eliminado
	 */
	ResponseVO<Boolean> delete(RequestVO<DeleteAplicacionRequestVO> request);

	/**
	 * Consulta un registro
	 * 
	 * @param numero Nombre  buscado
	 * 
	 * @return Objeto VO con los datos encontrados
	 */
	ResponseVO<FindDetailAplicacionResponseVO> findDetail(RequestVO<FindDetailAplicacionRequestVO> request);

	
	/**
	 * Consulta la base de datos en base a varios parametros
	 * 
	 * @return ReponseVO con los datos encontrados
	 * 
	 * @param request Objeto con parametros de entrada 
	 */
	ResponseVO<List<FindListAplicacionResponseVO>> findList(RequestVO<FindListAplicacionRequestVO> request);
	
	/**
	 * Servicio para validar la existencia de una aplicacion por id, nombre o ambos
	 * @param id
	 * @param idNombre
	 * @return
	 */
	AplicacionDO exists(Long id, String idNombre);


}
