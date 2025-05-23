package de.pecus.api.repositories.usuarios;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.pecus.api.entities.CodigoActivacionDO;

/**
 * Interfaz de acceso a datos para la entidad codigo activacion
 * 
 * @author NTT DATA
 *
 */

public interface CodigoRepository extends JpaRepository<CodigoActivacionDO, Serializable>{
	
	/**
	 * Consulta por id sin implementacion de query especifico
	 * 
	 * @return Objeto de mapeo a la entidad
	 * 
	 * @param id
	 *            Identificador de registro buscado
	 */
	@Query(" SELECT u FROM CodigoActivacionDO u WHERE u.userId = :data")
	CodigoActivacionDO findByUserId(@Param("data") String data);
	

}
