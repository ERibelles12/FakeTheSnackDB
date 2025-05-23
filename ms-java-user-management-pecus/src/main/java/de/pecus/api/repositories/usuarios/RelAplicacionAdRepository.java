/**
 * 
 */
package de.pecus.api.repositories.usuarios;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.pecus.api.entities.RelAplicacionAdDO;

/**
 * @author LuisEnriqueSánchezSa
 *
 */
public interface RelAplicacionAdRepository extends JpaRepository<RelAplicacionAdDO, Serializable> {

	@Query(value = "SELECT rad FROM RelAplicacionAdDO rad "
			+ " WHERE rad.active = true "
			+ " AND rad.aplicacion.id = :idAplicacion ")
	RelAplicacionAdDO findByIdAplicacion(@Param("idAplicacion") Long idAplicacion);
}
