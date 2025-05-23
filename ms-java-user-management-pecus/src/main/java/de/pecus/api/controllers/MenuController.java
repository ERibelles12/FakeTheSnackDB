package de.pecus.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.menu.CreateMenuRequestVO;
import de.pecus.api.vo.menu.CreateSubMenuRequestVO;
import de.pecus.api.vo.menu.FindDetailMenuResponseVO;
import de.pecus.api.vo.menu.FindDetailSubMenuResponseVO;
import de.pecus.api.vo.menu.FindListMenuResponseVO;
import de.pecus.api.vo.menu.FindListSubMenuResponseVO;
import de.pecus.api.vo.menu.UpdateMenuRequestVO;
import de.pecus.api.vo.menu.UpdateSubMenuRequestVO;

public interface MenuController {

	/**
	 * Servicio para crear un registro.
	 * 
	 * @param headers     Cabeceras de la solicitud.
	 * @param idNombre    Id alfanumerico del registro
	 * @param descripcion Descripcion del registro
	 * 
	 * @return Responde una entidad de tipo Response el registro creado
	 */
	ResponseEntity<ResponseVO<Long>> createMenu(Map<String, String> headers, CreateMenuRequestVO body);

	/**
	 * Servicio para actualiza un registro.
	 * 
	 * @param headers     Cabeceras de la solicitud.
	 * @param id          Id del registro a actualizar
	 * @param idNombre    Id alfanumerico del registro
	 * @param descripcion Descripcion del registro
	 * 
	 * @return Responde una entidad de tipo Response el registro actualizado
	 */
	ResponseEntity<ResponseVO<Long>> updateMenu(Map<String, String> headers, Long id, UpdateMenuRequestVO body);

	/**
	 * Servicio para elimina un registro.
	 * 
	 * @param headers Cabeceras de la solicitud.
	 * @param id      Id del registro a actualizar
	 * 
	 * @return Responde una entidad de tipo Response el booleano del resultado
	 */
	ResponseEntity<ResponseVO<Boolean>> deleteMenu(Map<String, String> headers, Long id);

	/**
	 * Servicio consulta un registro
	 * 
	 * @param headers  Cabeceras de la solicitud.
	 * @param id       Id del registro a actualizar
	 * @param idNombre Id alfanumerico del registro
	 * 
	 * @return Responde una entidad de tipo Response con los datos del registro
	 */
	ResponseEntity<ResponseVO<FindDetailMenuResponseVO>> findDetailMenu(Map<String, String> headers, Long id);

	/**
	 * Servicio para consulta de los datos de la tabla con filtros
	 * 
	 * @param headers  Cabeceras de la solicitud.
	 * @param idNombre Id alfanumerico del registro
	 * 
	 * @return Responde una lista de registros encontrados
	 */
	ResponseEntity<ResponseVO<List<FindListMenuResponseVO>>> findListMenu(Map<String, String> headers, Integer page,
			Integer size, String orderBy, String orderType, String idNombre);

	/**
	 * Servicio para crear un registro.
	 * 
	 * @param headers     Cabeceras de la solicitud.
	 * @param idNombre    Id alfanumerico del registro
	 * @param descripcion Descripcion del registro
	 * 
	 * @return Responde una entidad de tipo Response el registro creado
	 */
	ResponseEntity<ResponseVO<Long>> createSubMenu(Map<String, String> headers, CreateSubMenuRequestVO body);

	/**
	 * Servicio para actualiza un registro.
	 * 
	 * @param headers     Cabeceras de la solicitud.
	 * @param id          Id del registro a actualizar
	 * @param idNombre    Id alfanumerico del registro
	 * @param descripcion Descripcion del registro
	 * 
	 * @return Responde una entidad de tipo Response el registro actualizado
	 */
	ResponseEntity<ResponseVO<Long>> updateSubMenu(Map<String, String> headers, Long id, UpdateSubMenuRequestVO body);

	/**
	 * Servicio para elimina un registro.
	 * 
	 * @param headers Cabeceras de la solicitud.
	 * @param id      Id del registro a actualizar
	 * 
	 * @return Responde una entidad de tipo Response el booleano del resultado
	 */
	ResponseEntity<ResponseVO<Boolean>> deleteSubMenu(Map<String, String> headers, Long id);

	/**
	 * Servicio consulta un registro
	 * 
	 * @param headers  Cabeceras de la solicitud.
	 * @param id       Id del registro a actualizar
	 * @param idNombre Id alfanumerico del registro
	 * 
	 * @return Responde una entidad de tipo Response con los datos del registro
	 */
	ResponseEntity<ResponseVO<FindDetailSubMenuResponseVO>> findDetailSubMenu(Map<String, String> headers, Long id);

	/**
	 * Servicio para consulta de los datos de la tabla con filtros
	 * 
	 * @param headers  Cabeceras de la solicitud.
	 * @param idNombre Id alfanumerico del registro
	 * 
	 * @return Responde una lista de registros encontrados
	 */
	ResponseEntity<ResponseVO<List<FindListSubMenuResponseVO>>> findListSubMenu(Map<String, String> headers,
			Integer page, Integer size, String orderBy, String orderType, Long idMenu, String idNombre);

	/**
	 * Servicio para consulta de los datos de la tabla con filtros
	 * 
	 * @param headers  Cabeceras de la solicitud.
	 * @param idNombre Id alfanumerico del registro
	 * 
	 * @return Responde una lista de registros encontrados
	 */
	ResponseEntity<ResponseVO<List<FindListMenuResponseVO>>> findListMenuUnassigned(Map<String, String> headers,
			Integer page, Integer size, String orderBy, String orderType);

	/**
	 * Servicio para consulta de los datos de la tabla con filtros
	 * 
	 * @param headers  Cabeceras de la solicitud.
	 * @param idNombre Id alfanumerico del registro
	 * 
	 * @return Responde una lista de registros encontrados
	 */
	ResponseEntity<ResponseVO<List<FindListSubMenuResponseVO>>> findListSubMenuUnassigned(Map<String, String> headers,
			Integer page, Integer size, String orderBy, String orderType, Long idRolFuncion);

}
