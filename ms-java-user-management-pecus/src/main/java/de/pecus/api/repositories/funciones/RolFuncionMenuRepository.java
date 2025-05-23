package de.pecus.api.repositories.funciones;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.pecus.api.entities.RolFuncionMenuDO;

public interface RolFuncionMenuRepository extends JpaRepository<RolFuncionMenuDO, Serializable> {

	/**
	 * Consulta por aplicacion. Y se prepara para paginacion
	 * 
	 * @return List<Objeto> con el resultado
	 * 
	 * @param idNombre cadena descriptiva de registro buscado
	 * @param pageable
	 */
	@Query(value = " SELECT r" 
			+ " FROM  RolFuncionMenuDO r"
			+ " WHERE r.active = 1 "
			+ " AND   r.id = :id")
	RolFuncionMenuDO findById(
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
			+ " FROM  RolFuncionMenuDO r"
			+ " WHERE r.active = 1 "
			+ " AND   r.rolFuncion.id = :idRolFuncion" 
			+ " AND   r.menu.id = :idMenu" 
			)
	RolFuncionMenuDO findByIdRolIdFuncionMenu(
			@Param("idRolFuncion") Long idRolFuncion,
			@Param("idMenu") Long idMenu);

	
	/**
	 * Consulta por aplicacion. Y se prepara para paginacion
	 * 
	 * @return List<Objeto> con el resultado
	 * 
	 * @param idNombre cadena descriptiva de registro buscado
	 * @param pageable
	 */
	@Query(value = " SELECT r" 
			+ " FROM  RolFuncionMenuDO r"
			+ " JOIN FETCH r.rolFuncion rf "
			+ " JOIN FETCH r.menu rm "
			+ " WHERE r.active = 1 "
			+ " AND (:idRolFuncion IS NULL OR (r.rolFuncion.id = :idRolFuncion))"
			+ " AND (:idMenu IS NULL OR (r.menu.id = :idMenu))",
			countQuery = " SELECT r" 
					+ " FROM  RolFuncionMenuDO r"
					+ " WHERE r.active = 1 "
					+ " AND (:idRolFuncion IS NULL OR (r.rolFuncion.id = :idRolFuncion))"
					+ " AND (:idMenu IS NULL OR (r.menu.id = :idMenu))")
	Page<RolFuncionMenuDO> findList(
			@Param("idRolFuncion") Long idRolFuncion, 
			@Param("idMenu") Long idMenu, 
			Pageable pageable);
	
	@Query(value = " SELECT r" 
			+ " FROM  RolFuncionMenuDO r"
			+ " WHERE r.active = 1 "
			+ " AND   r.rolFuncion.id = :idRolFuncion" 
			)
	List<RolFuncionMenuDO> findByIdRolFuncion(@Param("idRolFuncion") Long idRolFuncion);
	
}
