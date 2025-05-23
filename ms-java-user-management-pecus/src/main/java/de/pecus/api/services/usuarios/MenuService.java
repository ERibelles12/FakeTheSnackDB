package de.pecus.api.services.usuarios;

import java.util.List;

import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.menu.CreateMenuRequestVO;
import de.pecus.api.vo.menu.CreateSubMenuRequestVO;
import de.pecus.api.vo.menu.DeleteMenuRequestVO;
import de.pecus.api.vo.menu.DeleteSubMenuRequestVO;
import de.pecus.api.vo.menu.FindDetailMenuRequestVO;
import de.pecus.api.vo.menu.FindDetailMenuResponseVO;
import de.pecus.api.vo.menu.FindDetailSubMenuRequestVO;
import de.pecus.api.vo.menu.FindDetailSubMenuResponseVO;
import de.pecus.api.vo.menu.FindListMenuRequestVO;
import de.pecus.api.vo.menu.FindListMenuResponseVO;
import de.pecus.api.vo.menu.FindListSubMenuRequestVO;
import de.pecus.api.vo.menu.FindListSubMenuResponseVO;
import de.pecus.api.vo.menu.FindListUnassignedSubmenuRequestVO;
import de.pecus.api.vo.menu.UpdateMenuRequestVO;
import de.pecus.api.vo.menu.UpdateSubMenuRequestVO;

/**
 * Clase de logica de negocio para administracion de Menu
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
public interface MenuService {

	/**
	 * Crea un nuevo registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id generado
	 */
	ResponseVO<Long> create (RequestVO<CreateMenuRequestVO> request);

	/**
	 * Actualiza un registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id actualizado
	 */
	ResponseVO<Long> update(RequestVO<UpdateMenuRequestVO> request);

	/**
	 * Marca un registro como eliminado un registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id eliminado
	 */
	ResponseVO<Boolean> delete(RequestVO<DeleteMenuRequestVO> request);

	/**
	 * Consulta un registro
	 * 
	 * @param numero Nombre  buscado
	 * 
	 * @return Objeto VO con los datos encontrados
	 */
	ResponseVO<FindDetailMenuResponseVO> findDetail(RequestVO<FindDetailMenuRequestVO> request);

	
	/**
	 * Consulta la base de datos en base a varios parametros
	 * 
	 * @return ReponseVO con los datos encontrados
	 * 
	 * @param request Objeto con parametros de entrada 
	 */
	ResponseVO<List<FindListMenuResponseVO>> findList(RequestVO<FindListMenuRequestVO> request);
	
	/**
	 * Consulta la base de datos en base a varios parametros
	 * 
	 * @return ReponseVO con los datos encontrados
	 * 
	 * @param request Objeto con parametros de entrada 
	 */
	ResponseVO<List<FindListMenuResponseVO>> findListMenuUnassigned(RequestVO<FindListMenuRequestVO> request);
	

	/**
	 * Crea un nuevo registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id generado
	 */
	ResponseVO<Long> createSubMenu (RequestVO<CreateSubMenuRequestVO> request);

	/**
	 * Actualiza un registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id actualizado
	 */
	ResponseVO<Long> updateSubMenu(RequestVO<UpdateSubMenuRequestVO> request);

	/**
	 * Marca un registro como eliminado un registro 
	 * 
	 * @param request Objeto con parametros de entrada 
	 * 
	 * @return Id eliminado
	 */
	ResponseVO<Boolean> deleteSubMenu(RequestVO<DeleteSubMenuRequestVO> request);

	/**
	 * Consulta un registro
	 * 
	 * @param numero Nombre  buscado
	 * 
	 * @return Objeto VO con los datos encontrados
	 */
	ResponseVO<FindDetailSubMenuResponseVO> findDetailSubMenu(RequestVO<FindDetailSubMenuRequestVO> request);

	
	/**
	 * Consulta la base de datos en base a varios parametros
	 * 
	 * @return ReponseVO con los datos encontrados
	 * 
	 * @param request Objeto con parametros de entrada 
	 */
	ResponseVO<List<FindListSubMenuResponseVO>> findListSubMenu(RequestVO<FindListSubMenuRequestVO> request);
	
	/**
	 * Consulta la base de datos en base a varios parametros
	 * 
	 * @return ReponseVO con los datos encontrados
	 * 
	 * @param request Objeto con parametros de entrada 
	 */
	ResponseVO<List<FindListSubMenuResponseVO>> findListSubMenuUnassigned(RequestVO<FindListUnassignedSubmenuRequestVO> request);

}
