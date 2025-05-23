package de.pecus.api.services.usuarios;

import java.util.List;

import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.funciones.CreateFuncionRequestVO;
import de.pecus.api.vo.funciones.DeleteFuncionRequestVO;
import de.pecus.api.vo.funciones.FindDetailFuncionRequestVO;
import de.pecus.api.vo.funciones.FindDetailFuncionResponseVO;
import de.pecus.api.vo.funciones.FindListFuncionRequestVO;
import de.pecus.api.vo.funciones.FindListFuncionResponseVO;
import de.pecus.api.vo.funciones.UpdateFuncionRequestVO;

public interface FuncionService {

	/**
	 * Crea un nuevo registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id generado
	 */
	ResponseVO<Long> createFuncion (RequestVO<CreateFuncionRequestVO> request);

	/**
	 * Actualiza un registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id actualizado
	 */
	ResponseVO<Long> updateFuncion(RequestVO<UpdateFuncionRequestVO> request);

	/**
	 * Marca un registro como eliminado un registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id eliminado
	 */
	ResponseVO<Boolean> deleteFuncion(RequestVO<DeleteFuncionRequestVO> request);

	/**
	 * Consulta un registro
	 * 
	 * @param numero Nombre  buscado
	 * 
	 * @return Objeto VO con los datos encontrados
	 */
	ResponseVO<FindDetailFuncionResponseVO> findDetailFuncion(RequestVO<FindDetailFuncionRequestVO> request);

	
	/**
	 * Consulta la base de datos en base a varios parametros
	 * 
	 * @return ReponseVO con los datos encontrados
	 * 
	 * @param request Objeto con parametros de entrada 
	 */
	ResponseVO<List<FindListFuncionResponseVO>> findListFuncionAssing(RequestVO<FindListFuncionRequestVO> request);
	
	/**
	 * Consulta la base de datos en base a varios parametros
	 * 
	 * @return ReponseVO con los datos encontrados
	 * 
	 * @param request Objeto con parametros de entrada 
	 */
	ResponseVO<List<FindListFuncionResponseVO>> findListFuncion(RequestVO<FindListFuncionRequestVO> request);

}
