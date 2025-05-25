package de.pecus.api.repositories.usuarios;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.pecus.api.entities.ProductDO;

public interface ProductRepository extends JpaRepository<ProductDO, Serializable> {

	/**
	 * Consulta por name sin implementacion de query especifico
	 * @param name Identificador de nombre buscado
	 * @return Objeto de mapeo a la entidad
	 */
	@Query(value = " SELECT r" 
			+ " FROM  ProductDO r"
			+ " WHERE r.active = 1 "
			+ " AND r.name = :name")
	ProductDO findByName(@Param("name") String name);
	
	/**
	 * Consulta por id sin implementacion de query especifico
	 * 
	 * @return Objeto de mapeo a la entidad
	 * 
	 * @param id Identificador de registro buscado
	 */
	@Query(value = " SELECT r" 
			+ " FROM  ProductDO r"
			+ " WHERE r.active = 1 "
			+ " AND r.id = :id")
	ProductDO findById(@Param("id") Long id);
	
    /**
	 * Consulta por nombre . Y se prepara para paginacion
	 * 
	 * @return List<Objeto> con el resultado
	 * 
	 * @param name cadena descriptiva de registro buscado
	 * @param pageable
	 */
	@Query(value = " SELECT r" 
			+ " FROM  ProductDO r"
			+ " WHERE r.active = 1 "
			+ " AND (:name IS NULL OR (TRANSLATE(UPPER(r.name),'áéíóú','aeiou') LIKE %:name%))")
	Page<ProductDO> findList(@Param("name") String name, 
			Pageable pageable);
	
}
