package de.pecus.api.repositories.usuarios;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.pecus.api.entities.RelUsuarioRolDO;
/**
 * Auxiliar para el acceso a la base de datos
 * @author Luis Enrique Sanchez Santiago
 *
 */
public interface UsuarioRolRepository extends JpaRepository<RelUsuarioRolDO, Serializable> {

	@Query(" SELECT ur FROM RelUsuarioRolDO ur WHERE ur.usuario.id = :userId AND ur.rol.id = :rolId AND ur.active = :active ")
	List<RelUsuarioRolDO> findByUserIdAndRolIdAndActive(Long userId, Long rolId, Boolean active);
	
	@Query(value="SELECT ur"
			+ " FROM RelUsuarioRolDO ur"
			+ " WHERE ur.usuario.id = :idUsuario"
			+ " AND ur.rol.id = :idRol"
			+ " AND ur.active = 1")
	RelUsuarioRolDO findUsuarioRolActivo(
			@Param("idUsuario") Long userId, 
			@Param("idRol") Long rolId);
	
	
	@Query(value="SELECT ur"
			+ " FROM RelUsuarioRolDO ur"
			+ " WHERE ur.usuario.id = :idUsuario"
			+ " AND ur.rol.aplicacion.idNombre = :aplicacion"
			+ " AND ur.active = 1")
	RelUsuarioRolDO findUsuarioRolActivoAplicacion(
			@Param("idUsuario") Long userId, 
			@Param("aplicacion") String aplicacion);
}
