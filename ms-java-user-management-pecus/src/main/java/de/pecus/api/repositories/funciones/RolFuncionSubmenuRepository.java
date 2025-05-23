package de.pecus.api.repositories.funciones;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.pecus.api.entities.RolFuncionSubmenuDO;

public interface RolFuncionSubmenuRepository extends JpaRepository<RolFuncionSubmenuDO, Serializable> {

	/**
	 * Consulta por aplicacion. Y se prepara para paginacion
	 * 
	 * @return List<Objeto> con el resultado
	 * 
	 * @param idNombre cadena descriptiva de registro buscado
	 * @param pageable
	 */
	@Query(value = " SELECT r" 
			+ " FROM  RolFuncionSubmenuDO r"
			+ " WHERE r.active = 1 "
			+ " AND   r.id = :id")
	RolFuncionSubmenuDO findById(
			@Param("id") Long id);

	
	/**
	 * Consulta por aplicacion. Y se prepara para paginacion
	 * 
	 * @return List<Objeto> con el resultado
	 * 
	 * @param idNombre cadena descriptiva de registro buscado
	 * @param pageable
	 */
	@Query(value = " SELECT r" 
			+ " FROM  RolFuncionSubmenuDO r"
			+ " WHERE r.active = 1 "
			+ " AND   r.funcion.id = :idFuncion" 
			+ " AND   r.submenu.id = :idSubmenu" 
			)
	RolFuncionSubmenuDO findByIdFuncionSubmenu(
			@Param("idFuncion") Long idFuncion,
			@Param("idSubmenu") Long idSubmenu);

	
	/**
	 * Consulta por aplicacion. Y se prepara para paginacion
	 * 
	 * @return List<Objeto> con el resultado
	 * 
	 * @param idNombre cadena descriptiva de registro buscado
	 * @param pageable
	 */
	@Query(value = " SELECT r" 
			+ " FROM  RolFuncionSubmenuDO r"
			+ " JOIN FETCH r.funcion f "
			+ " JOIN FETCH r.submenu rm "
			+ " WHERE r.active = 1 "
			+ " AND (:idFuncion IS NULL OR (r.funcion.id = :idFuncion))"
			+ " AND (:idSubmenu IS NULL OR (r.submenu.id = :idSubmenu))",
			countQuery = " SELECT r" 
					+ " FROM  RolFuncionSubmenuDO r"
					+ " WHERE r.active = 1 "
					+ " AND (:idFuncion IS NULL OR (r.funcion.id = :idFuncion))"
					+ " AND (:idSubmenu IS NULL OR (r.submenu.id = :idSubmenu))")
	Page<RolFuncionSubmenuDO> findList(
			@Param("idFuncion") Long idFuncion, 
			@Param("idSubmenu") Long idSubmenu, 
			Pageable pageable);
	
	@Query(value = " SELECT r" 
			+ " FROM  RolFuncionSubmenuDO r"
			+ " WHERE r.active = 1 "
			+ " AND   r.funcion.id = :idFuncion"  
			)
	List<RolFuncionSubmenuDO> findByIdFuncion(
			@Param("idFuncion") Long idFuncion);
}
