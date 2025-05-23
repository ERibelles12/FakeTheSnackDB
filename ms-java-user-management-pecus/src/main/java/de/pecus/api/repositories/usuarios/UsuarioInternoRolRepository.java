package de.pecus.api.repositories.usuarios;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.pecus.api.entities.RelUsuarioInternoRolDO;
/**
 * Auxiliar para el acceso a la base de datos
 * @author Luis Enrique Sanchez Santiago
 *
 */
public interface UsuarioInternoRolRepository extends JpaRepository<RelUsuarioInternoRolDO, Serializable> {

	@Query(" SELECT ur FROM RelUsuarioInternoRolDO ur WHERE ur.usuario.id = :userId AND ur.rol.id = :rolId AND ur.active = :active ")
	List<RelUsuarioInternoRolDO> findByUserIdAndRolIdAndActive(Long userId, Long rolId, Boolean active);
	
	@Query(value="SELECT ur"
			+ " FROM RelUsuarioInternoRolDO ur"
			+ " WHERE ur.usuario.id = :idUsuario"
			+ " AND ur.rol.id = :idRol"
			+ " AND ur.active = 1")
	RelUsuarioInternoRolDO findUsuarioRolActivo(
			@Param("idUsuario") Long userId, 
			@Param("idRol") Long rolId);
}