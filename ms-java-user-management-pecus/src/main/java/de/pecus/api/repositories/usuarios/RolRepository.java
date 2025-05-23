package de.pecus.api.repositories.usuarios;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.pecus.api.entities.RolDO;

public interface RolRepository extends JpaRepository<RolDO, Serializable> {

	/**
	 * Consulta por id sin implementacion de query especifico
	 * 
	 * @return Objeto de mapeo a la entidad
	 * 
	 * @param id Identificador de registro buscado
	 */
	@Query(" SELECT r FROM RolDO r "
			+ "JOIN FETCH r.tipoRol tr "
			+ "JOIN FETCH r.aplicacion ap "
			+ "WHERE r.id = :id "
			+ "AND r.active = :active ")
	RolDO findByIdAndActive(@Param("id") Long id, @Param("active") Boolean active);

	/**
	 * Consulta por un rol por su nombre y la bandera de activo
	 * 
	 * @return Objeto de mapeo a la entidad
	 * 
	 * @param id Identificador de registro buscado
	 */
	@Query(" SELECT r FROM RolDO r "
			+ "JOIN FETCH r.tipoRol tr "
			+ "WHERE r.idNombre = :rolName "
			+ "AND r.active = :active ")
	List<RolDO> findByrolNameAndActive(@Param("rolName") String rolName, @Param("active") Boolean active);
	
	
	/**
	 * Consulta por idNombre y aplicacion 
	 * @param idNombre Identificador de nombre buscado
	 * @param aplication nombre de la aplicacion asociada al rol que se busca
	 * @return Objeto de mapeo a la entidad
	 */
	@Query(" SELECT r FROM RolDO r "
			+ "JOIN FETCH r.tipoRol tr "
			+ "WHERE r.idNombre = :rolName "
			+ "AND r.aplicacion.idNombre = :aplication "
			+ "AND r.active = 1 ")
	RolDO findByrolNameAndAplicationAndActive(@Param("rolName") String rolName, @Param("aplication") String aplication);
	
	/**
	 * Consulta por idNombre sin implementacion de query especifico
	 * @param idNombre Identificador de nombre buscado
	 * @return Objeto de mapeo a la entidad
	 */
	@Query(value = " SELECT r" 
			+ " FROM  RolDO r"
			+ " JOIN FETCH r.tipoRol tr "
			+ " JOIN FETCH r.aplicacion ra "
			+ " WHERE r.active = 1 "
			+ " AND r.idNombre = :idNombre"
			+ " AND r.aplicacion.id = :idAplicacion"
			)
	RolDO findByIdNombre(
			@Param("idAplicacion") Long idAplicacion,
			@Param("idNombre") String idNombre);
	
	
    /**
	 * Consulta por nombre . Y se prepara para paginacion
	 * 
	 * @return List<Objeto> con el resultado
	 * 
	 * @param idNombre cadena descriptiva de registro buscado
	 * @param pageable
	 */
	@Query(value = " SELECT r" 
			+ " FROM  RolDO r"
			+ " JOIN FETCH r.tipoRol tr "
			+ " JOIN FETCH r.aplicacion ra "
			+ " WHERE r.active = 1 "
			+ " AND   r.aplicacion.id = :idAplicacion"
			+ " AND (:idNombre IS NULL OR (TRANSLATE(UPPER(r.idNombre),'áéíóú','aeiou') LIKE %:idNombre%))",
			countQuery = " SELECT r" 
					+ " FROM  RolDO r"
					+ " WHERE r.active = 1 "
					+ " AND   r.aplicacion.id = :idAplicacion"
					+ " AND (:idNombre IS NULL OR (TRANSLATE(UPPER(r.idNombre),'áéíóú','aeiou') LIKE %:idNombre%))")
	Page<RolDO> findList(
			@Param("idAplicacion") Long idAplicacion, 
			@Param("idNombre") String idNombre, 
			Pageable pageable);

	/**
	 * Consulta los roles asociados a un usuario
	 * 
	 * @param idAplicacion
	 * @param idNombre
	 * @param idUsuario
	 * @param pageable
	 * 
	 * @return List<Objeto> con el resultado
	 */
	@Query(value = " SELECT r"
			+ " FROM RolDO r"
			+ " JOIN FETCH r.tipoRol tr "
			+ " JOIN FETCH r.aplicacion ra "
			+ " WHERE r.active = 1 "
			+ " AND   r.aplicacion.id = :idAplicacion"
			+ " AND (:idNombre IS NULL OR (TRANSLATE(UPPER(r.idNombre),'áéíóú','aeiou') LIKE %:idNombre%))"
			+ " AND r IN (SELECT ur.rol FROM RelUsuarioRolDO ur WHERE ur.usuario.id = :idUsuario and ur.active = 1)",
			countQuery = " SELECT r" 
					+ " FROM  RolDO r"
					+ " WHERE r.active = 1 "
					+ " AND   r.aplicacion.id = :idAplicacion"
					+ " AND (:idNombre IS NULL OR (TRANSLATE(UPPER(r.idNombre),'áéíóú','aeiou') LIKE %:idNombre%))"
					+ " AND r IN (SELECT ur.rol FROM RelUsuarioRolDO ur WHERE ur.usuario.id = :idUsuario and ur.active = 1)")
	Page<RolDO> findListByUser(
			@Param("idAplicacion") Long idAplicacion,
			@Param("idNombre") String idNombre,
			@Param("idUsuario") Long idUsuario,
			Pageable pageable);
}
