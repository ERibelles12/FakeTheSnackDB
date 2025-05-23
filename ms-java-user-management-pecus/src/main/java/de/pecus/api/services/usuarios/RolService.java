package de.pecus.api.services.usuarios;

import java.util.List;

import de.pecus.api.entities.RolDO;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.funciones.CreateRolFuncionMenuRequestVO;
import de.pecus.api.vo.funciones.CreateRolFuncionRequestVO;
import de.pecus.api.vo.funciones.CreateRolFuncionResponseVO;
import de.pecus.api.vo.funciones.CreateRolFuncionSubmenuRequestVO;
import de.pecus.api.vo.funciones.DeleteRolFuncionMenuRequestVO;
import de.pecus.api.vo.funciones.DeleteRolFuncionRequestVO;
import de.pecus.api.vo.funciones.DeleteRolFuncionSubmenuRequestVO;
import de.pecus.api.vo.funciones.FindListRolFuncionMenuRequestVO;
import de.pecus.api.vo.funciones.FindListRolFuncionMenuResponseVO;
import de.pecus.api.vo.funciones.FindListRolFuncionRequestVO;
import de.pecus.api.vo.funciones.FindListRolFuncionResponseVO;
import de.pecus.api.vo.funciones.FindListRolFuncionSubmenuRequestVO;
import de.pecus.api.vo.funciones.FindListRolFuncionSubmenuResponseVO;
import de.pecus.api.vo.roles.CreateRolRequestVO;
import de.pecus.api.vo.roles.DeleteRolRequestVO;
import de.pecus.api.vo.roles.FindDetailRolRequestVO;
import de.pecus.api.vo.roles.FindDetailRolResponseVO;
import de.pecus.api.vo.roles.FindListRolRequestVO;
import de.pecus.api.vo.roles.FindListRolResponseVO;
import de.pecus.api.vo.roles.UpdateRolRequestVO;

/**
 * Clase de logica de negocio para administracionde Tipos de Rol
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
public interface RolService {

	/**
	 * Metodo encargado de validar la existencia del rol por su identificador
	 * 
	 * @param idRol
	 */
	RolDO exists(Long idRol, String idioma);
	
	/**
	 * Metodo encargado de validar la existencia del rol por su nombre
	 * 
	 * @param idRol
	 */
	List<RolDO> exists(String rolName, String idioma);
	
	/**
	 * Crea un nuevo registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id generado
	 */
	ResponseVO<Long> createRol (RequestVO<CreateRolRequestVO> request);

	/**
	 * Actualiza un registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id actualizado
	 */
	ResponseVO<Long> updateRol(RequestVO<UpdateRolRequestVO> request);

	/**
	 * Marca un registro como eliminado un registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id eliminado
	 */
	ResponseVO<Boolean> deleteRol(RequestVO<DeleteRolRequestVO> request);

	/**
	 * Consulta un registro
	 * 
	 * @param numero Nombre  buscado
	 * 
	 * @return Objeto VO con los datos encontrados
	 */
	ResponseVO<FindDetailRolResponseVO> findDetailRol(RequestVO<FindDetailRolRequestVO> request);

	
	/**
	 * Consulta la base de datos en base a varios parametros
	 * 
	 * @return ReponseVO con los datos encontrados
	 * 
	 * @param request Objeto con parametros de entrada 
	 */
	ResponseVO<List<FindListRolResponseVO>> findListRol(RequestVO<FindListRolRequestVO> request);

	
	/**
	 * Crea un nuevo registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id generado
	 */
	ResponseVO<CreateRolFuncionResponseVO> createRolFuncion (RequestVO<CreateRolFuncionRequestVO> request);

	/**
	 * Marca un registro como eliminado un registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id eliminado
	 */
	ResponseVO<Boolean> deleteRolFuncion(RequestVO<DeleteRolFuncionRequestVO> request);

	
	/**
	 * Consulta la base de datos en base a varios parametros
	 * 
	 * @return ReponseVO con los datos encontrados
	 * 
	 * @param request Objeto con parametros de entrada 
	 */
	ResponseVO<List<FindListRolFuncionResponseVO>> findListRolFuncion(RequestVO<FindListRolFuncionRequestVO> request);

	/**
	 * Crea un nuevo registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Lista de Ids generado
	 */
	ResponseVO<List<Long>> createRolFuncionMenu (RequestVO<CreateRolFuncionMenuRequestVO> request);

	/**
	 * Marca un registro como eliminado un registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id eliminado
	 */
	ResponseVO<Boolean> deleteRolFuncionMenu(RequestVO<DeleteRolFuncionMenuRequestVO> request);

	
	/**
	 * Consulta la base de datos en base a varios parametros
	 * 
	 * @return ReponseVO con los datos encontrados
	 * 
	 * @param request Objeto con parametros de entrada 
	 */
	ResponseVO<List<FindListRolFuncionMenuResponseVO>> findListRolFuncionMenu(RequestVO<FindListRolFuncionMenuRequestVO> request);
	
	/**
	 * Crea un nuevo registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Lista de Ids generado
	 */
	ResponseVO<List<Long>> createRolFuncionSubmenu (RequestVO<CreateRolFuncionSubmenuRequestVO> request);

	/**
	 * Marca un registro como eliminado un registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id eliminado
	 */
	ResponseVO<Boolean> deleteRolFuncionSubmenu(RequestVO<DeleteRolFuncionSubmenuRequestVO> request);

	
	/**
	 * Consulta la base de datos en base a varios parametros
	 * 
	 * @return ReponseVO con los datos encontrados
	 * 
	 * @param request Objeto con parametros de entrada 
	 */
	ResponseVO<List<FindListRolFuncionSubmenuResponseVO>> findListRolFuncionSubmenu(RequestVO<FindListRolFuncionSubmenuRequestVO> request);

	
	/**
	 * Consulta la base de datos en base a varios parametros
	 * 
	 * @return ReponseVO con los datos encontrados
	 * 
	 * @param request Objeto con parametros de entrada 
	 */
	ResponseVO<List<FindListRolResponseVO>> findInternalListRol(RequestVO<FindListRolRequestVO> request);
	
}
