package de.pecus.api.repositories.usuarios;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.pecus.api.entities.ResultItemDO;

public interface ResultItemRepository extends JpaRepository<ResultItemDO, Serializable> {

	/**
	 * Consulta por id sin implementacion de query especifico
	 * 
	 * @return Objeto de mapeo a la entidad
	 * 
	 * @param id Identificador de registro buscado
	 */
	@Query(value = " SELECT r" 
			+ " FROM  ResultItemDO r"
			+ " WHERE r.active = true "
			+ " AND r.id = :id")
	ResultItemDO findById(@Param("id") Long id);
	
	/**
	 * Consulta por nombre . Y se prepara para paginacion
	 * 
	 * @return List<Objeto> con el resultado
	 * 
	 * @param pageable
	 */

	@Query(value = " SELECT r" 
			+ " FROM  ResultItemDO r"
			+ " WHERE r.active = true "
			+ " AND r.recipe.id	= :idRecipe",
			countQuery="SELECT COUNT(r) "
					+ " FROM  ResultItemDO r "
					+ " WHERE r.active = true "
					+ " AND r.recipe.id =:idRecipe")
	Page<ResultItemDO> findList(@Param("idRecipe") Long idRecipe,
                               Pageable pageable);

}
