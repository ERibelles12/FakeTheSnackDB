package de.pecus.api.repositories.usuarios;

import de.pecus.api.entities.ProductDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;

public interface RecipeRepository extends JpaRepository<RecipeDO, Serializable> {

	@Query(value = " SELECT r"
			+ " FROM  RecipeDO r"
			+ " JOIN FETCH r.product p"
			+ " JOIN FETCH r.substance s"
			+ " WHERE r.active = true "
			+ " AND p.id = :idProduct",
			countQuery="SELECT r "
					+ " FROM  RecipeDO r"
					+ " JOIN FETCH r.product p"
					+ " JOIN FETCH r.substance s"
					+ " WHERE r.active = true "
				+ "	AND p.id = :idProduct")
	Page<ProductDO> findListByProduct(@Param("idProduct") Long idProduct,
							 Pageable pageable);
	/**
	 * Consulta por name sin implementacion de query especifico
	 *
	 * @return Objeto de mapeo a la entidad
	 */
	@Query(value = " SELECT r"
			+ " FROM  RecipeDO r"
			+ " JOIN FETCH r.product p"
			+ " JOIN FETCH r.substance s"
			+ " WHERE r.active = true "
			+ " AND s.id = :idSubstance",
			countQuery="SELECT r "
					+ " FROM  RecipeDO r"
					+ " JOIN FETCH r.product p"
					+ " JOIN FETCH r.substance s"
					+ " WHERE r.active = true "
					+ "	AND s.id = :idSubstance")
	Page<ProductDO> findListBySubstance(@Param("idSubstance") Long idSubstance,
									  Pageable pageable);

	/**
	 * Consulta por id sin implementacion de query especifico
	 * 
	 * @return Objeto de mapeo a la entidad
	 * 
	 * @param id Identificador de registro buscado
	 */
	@Query(value = " SELECT r"
			+ " FROM  RecipeDO r"
			+ " JOIN FETCH r.product p"
			+ " JOIN FETCH r.substance s"
			+ " WHERE r.active = true "
			+ " AND r.id = :id")
	ProductDO findById(@Param("id") Long id);

}
