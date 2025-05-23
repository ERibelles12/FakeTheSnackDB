package de.pecus.api.repositories.funciones;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.pecus.api.entities.RolFuncionDO;

public interface RolFuncionRepository extends JpaRepository<RolFuncionDO, Serializable> {
	 

	/**
	 * Consulta por aplicacion. Y se prepara para paginacion
	 * 
	 * @return List<Objeto> con el resultado
	 * 
	 * @param idNombre cadena descriptiva de registro buscado
	 * @param pageable
	 */
	@Query(value = " SELECT r" 
			+ " FROM  RolFuncionDO r"
			+ " WHERE r.active = 1 "
			+ " AND   r.id = :id")
	RolFuncionDO findById(
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
			+ " FROM  RolFuncionDO r"
			+ " WHERE r.active = 1 "
			+ " AND   r.rol.id = :idRol" 
			+ " AND   r.funcion.id = :idFuncion" 
			)
	RolFuncionDO findByIdRolIdFuncion(
			@Param("idRol") Long idRol,
			@Param("idFuncion") Long idFuncion);
	
	/**
	 * Consulta por idRol
	 * 
	 * @param idRol identificador del rol
	 * @return List<Objeto> con el resultado
	 */
	@Query(value= " SELECT r"
			+ " FROM RolFuncionDO r"
			+ " WHERE r.active = 1 "
			+ " AND r.rol.id = :idRol"
			)
	Page<RolFuncionDO> findByIdRol(
			@Param("idRol") Long idRol,
			Pageable pageable);
	

	/**
		 * Consulta por aplicacion. Y se prepara para paginacion
		 * 
		 * @return List<Objeto> con el resultado
		 * 
		 * @param idNombre cadena descriptiva de registro buscado
		 * @param pageable
		 */
		@Query(value = " SELECT r" 
				+ " FROM  RolFuncionDO r"
				+ " JOIN FETCH r.rol rr "
				+ " JOIN FETCH r.funcion rf "
				+ " WHERE r.active = 1 "
				+ " AND   r.rol.id = :idRol",
				countQuery = " SELECT count(r)" 
						+ " FROM  RolFuncionDO r"
						+ " WHERE r.active = 1 "
						+ " AND   r.rol.id = :idRol"
						)
		Page<RolFuncionDO> findList(
				@Param("idRol") Long idRol, 
				Pageable pageable);
	
}
