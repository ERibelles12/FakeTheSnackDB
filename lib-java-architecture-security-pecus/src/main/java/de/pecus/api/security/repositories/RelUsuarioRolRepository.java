package de.pecus.api.security.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.pecus.api.entities.RelUsuarioRolDO;

public interface RelUsuarioRolRepository extends JpaRepository<RelUsuarioRolDO, Serializable> {
	
	/**
	 * Consulta por idUsuario para obtener el rol asociado.
	 * 
	 * @param idUsuario id del usuario
	 * @return RelUsuarioRolDO con el resultado
	 * 
	 */
	@Query(value = " SELECT rur"
            + " FROM RelUsuarioRolDO rur"
            + " WHERE rur.usuario.id = :idUsuario")
	RelUsuarioRolDO findByIdUsuario(@Param("idUsuario") Long idUsuario);
	
	/**
     * Consulta por idUsuario y idAplicacion para obtener el rol asociado.
     * 
     * @param idUsuario id del usuario
     * @param idAplicacion id de la aplicacion
     * @return RelUsuarioRolDO con el resultado
     * 
     */
    @Query(value = " SELECT rur"
            + " FROM RelUsuarioRolDO rur"
            + " WHERE rur.usuario.id = :idUsuario"
            + " AND rur.rol.aplicacion.idNombre = :idNombreAplicacion")
    RelUsuarioRolDO findByIdUsuarioAndAplicacion(@Param("idUsuario") Long idUsuario, @Param("idNombreAplicacion") String idNombreAplicacion);
}
