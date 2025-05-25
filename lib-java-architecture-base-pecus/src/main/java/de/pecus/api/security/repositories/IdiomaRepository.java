package de.pecus.api.security.repositories;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.pecus.api.entities.IdiomaDO;

public interface IdiomaRepository extends JpaRepository<IdiomaDO, Serializable>{

    /**
     * Consulta por idNombre sin implementacion de query especifico
     * @param idNombre Identificador de nombre buscado
     * @return Objeto de mapeo a la entidad
     */
	IdiomaDO findByIdNombreAndActive(String idNombre, Boolean active);
	
	/**
     * Consulta por id sin implementacion de query especifico
     * @param id Identificador de nombre buscado
     * @return Objeto de mapeo a la entidad
     */
	IdiomaDO findByIdAndActive(Long id, Boolean active);
	
	/**
	 * Consulta por nombre. Y se prepara para paginacion
	 * 
	 * @return List<Objeto> con el resultado
	 * 
	 * @param idNombre cadena descriptiva de registro buscado
	 */
	@Query(value = " SELECT idioma" 
			+ " FROM  IdiomaDO idioma "
			+ " WHERE   ( :idNombre IS NULL OR idioma.idNombre like :idNombre ) "
			+ " AND idioma.active = 1  ")
	Page<IdiomaDO> findList(@Param("idNombre") String idNombre, Pageable pageable);

}
