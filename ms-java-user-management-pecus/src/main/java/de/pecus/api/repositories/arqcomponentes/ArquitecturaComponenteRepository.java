package de.pecus.api.repositories.arqcomponentes;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.pecus.api.entities.ArqComponenteDO;

public interface ArquitecturaComponenteRepository extends JpaRepository<ArqComponenteDO, Serializable>{

	
	/**
	 * Consulta por id sin implementacion de query especifico
	 * 
	 * @return Objeto de mapeo a la entidad
	 * 
	 * @param id Identificador de registro buscado
	 */
	 @Query(value = " SELECT arqComponente"
             + " FROM  ArqComponenteDO arqComponente "
             + " WHERE arqComponente.active = true "
             + " AND   arqComponente.id = :id ")
	 ArqComponenteDO findByIdAndActive(@Param("id") Long id);
	
	/**
	 * Consulta por idNombre sin implementacion de query especifico
	 * 
	 * @return Objeto de mapeo a la entidad
	 * 
	 * @param idNombre Identificador de registro buscado
	 */
	 @Query(value = " SELECT arqComponente"
             + " FROM  ArqComponenteDO arqComponente "
             + " WHERE arqComponente.active = true "
             + " AND   arqComponente.idNombre = :idNombre")
	ArqComponenteDO findByIdNombreAndActive(@Param("idNombre") String idNombre);
	
	/**
     * Consulta por id o idNombre sin implementacion de query especifico
     * 
     * @return Objeto de mapeo a la entidad
     * 
     * @param id Identificador de registro buscado
     */
	 @Query(value = " SELECT arqComponente"
             + " FROM  ArqComponenteDO arqComponente "
             + " WHERE arqComponente.active = true "
             + " AND   (arqComponente.id = :id OR arqComponente.idNombre = :idNombre) ")
    ArqComponenteDO findByIdOrIdNombreAndActive(@Param("id") Long id, @Param("idNombre") String idNombre);

	
	 /**
     * Consulta por descripcion y se prepara para paginacion
     * @return List<Objeto> con el resultado
     * 
     * @param idNombre    cadena descriptiva de registro buscado
     */
    @Query(value = " SELECT arqComponente"
                + " FROM  ArqComponenteDO arqComponente "
    		    + " JOIN FETCH arqComponente.arqTipoComponente tc"
                + " WHERE arqComponente.active = true "
                + " AND ( :idNombre IS NULL OR arqComponente.idNombre LIKE :idNombre ) "
                + " AND ( :idArqTipoComponente IS NULL OR tc.id = :idArqTipoComponente ) ",
                countQuery = " SELECT COUNT(arqComponente) FROM  ArqComponenteDO arqComponente "
                        + " WHERE arqComponente.active = true "
                        + " AND ( :idNombre IS NULL OR arqComponente.idNombre LIKE :idNombre ) "
                        + " AND ( :idArqTipoComponente IS NULL OR arqComponente.arqTipoComponente.id = :idArqTipoComponente ) "
                )
    Page<ArqComponenteDO> findList(
    		@Param("idNombre") String idNombre,
    		@Param("idArqTipoComponente") Long idArqTipoComponente,
    		Pageable pageable);

    
    
	 /**
     * Consulta por descripcion y se prepara para paginacion
     * @return List<Objeto> con el resultado
     * 
     * @param idNombre    cadena descriptiva de registro buscado
     */
    @Query(value = " SELECT arqComponente"
                + " FROM  ArqComponenteDO arqComponente "
    		    + " JOIN FETCH arqComponente.arqTipoComponente tc"
                + " WHERE arqComponente.active = true "
                + " AND ( :idNombre IS NULL OR arqComponente.idNombre LIKE :idNombre ) "
                + " AND ( :idNombreArqTipoComponente IS NULL OR tc.idNombre LIKE :idNombreArqTipoComponente ) "
                + " AND ( :idArqTipoComponente IS NULL OR tc.id = :idArqTipoComponente ) ",
                countQuery = " SELECT COUNT(arqComponente) FROM  ArqComponenteDO arqComponente "
                        + " WHERE arqComponente.active = true "
                        + " AND ( :idNombre IS NULL OR arqComponente.idNombre LIKE :idNombre ) "
                        + " AND ( :idNombreArqTipoComponente IS NULL OR arqComponente.arqTipoComponente.idNombre LIKE :idNombreArqTipoComponente ) "
                        + " AND ( :idArqTipoComponente IS NULL OR arqComponente.arqTipoComponente.id = :idArqTipoComponente ) ")
    Page<ArqComponenteDO> findLisByType(
    		@Param("idNombre") String idNombre,
    		@Param("idArqTipoComponente") Long idArqTipoComponente,
    		@Param("idNombreArqTipoComponente") String idNombreArqTipoComponente,
    		Pageable pageable);
    
}
