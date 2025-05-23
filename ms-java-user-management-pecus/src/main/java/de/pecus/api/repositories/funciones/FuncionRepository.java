package de.pecus.api.repositories.funciones;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.pecus.api.entities.FuncionDO;
public interface FuncionRepository extends JpaRepository<FuncionDO, Serializable> {
    /**
     * Consulta por idNombre sin implementacion de query especifico
     * @param idNombre Identificador de nombre buscado
     * @return Objeto de mapeo a la entidad
     */
    @Query(value = " SELECT r" 
            + " FROM  FuncionDO r"
            + " WHERE r.active = 1 "
            + " AND r.idNombre = :idNombre"
            + " AND r.aplicacion.id = :idAplicacion"
            )
    FuncionDO findByIdNombre(
            @Param("idAplicacion") Long idAplicacion,
            @Param("idNombre") String idNombre);
    /**
     * Consulta por id sin implementacion de query especifico
     * 
     * @return Objeto de mapeo a la entidad
     * 
     * @param id Identificador de registro buscado
     */
    @Query(value = " SELECT r" 
            + " FROM  FuncionDO r"
            + " WHERE r.active = 1 "
            + " AND r.id = :id")
    FuncionDO findById(@Param("id") Long id);
    /**
     * Consulta por nombre 
     * 
     * @return List<Objeto> con el resultado
     * 
     * @param idNombre cadena descriptiva de registro buscado
     * 
     */
    @Query(value = " SELECT r" 
            + " FROM  FuncionDO r"
            + " WHERE r.active = 1 "
            + " AND   r.aplicacion.id = :idAplicacion"
            + " AND (:idNombre IS NULL OR (TRANSLATE(UPPER(r.idNombre),'áéíóú','aeiou') LIKE %:idNombre%))"
            + " AND r IN (SELECT rf.funcion FROM RolFuncionDO rf WHERE rf.rol.id = :idRol)"
            + " ORDER BY r.idNombre")
    List<FuncionDO> findListAssing(
            @Param("idAplicacion") Long idAplicacion, 
            @Param("idNombre") String idNombre,
            @Param("idRol") Long idRol);
    /**
     * Consulta por nombre . Y se prepara para paginacion
     * 
     * @return List<Objeto> con el resultado
     * 
     * @param idNombre cadena descriptiva de registro buscado
     * @param pageable
     */
    @Query(value = " SELECT r" 
            + " FROM  FuncionDO r"
            + " WHERE r.active = 1 "
            + " AND   r.aplicacion.id = :idAplicacion"
            + " AND (:idNombre IS NULL OR (TRANSLATE(UPPER(r.idNombre),'áéíóú','aeiou') LIKE %:idNombre%))"
            + " AND r IN (SELECT rf.funcion FROM RolFuncionDO rf WHERE rf.rol.id = :idRol)")
    Page<FuncionDO> findList(
            @Param("idAplicacion") Long idAplicacion, 
            @Param("idNombre") String idNombre,
            @Param("idRol") Long idRol,
            Pageable pageable);
    /**
     * Cosulta aquellas funciones que no tienen relacion con un idRol dado
     * 
     * @param idAplicacion
     * @param idNombre
     * @param idRol
     * 
     * @return List<Objeto> con el resultado
     */
    @Query(value = "SELECT f"
            + " FROM FuncionDO f"
            + " WHERE f.active = 1"
            + " AND f.aplicacion.id = :idAplicacion"
            + " AND (:idNombre IS NULL OR (TRANSLATE(UPPER(f.idNombre),'áéíóú','aeiou') LIKE %:idNombre%))"
            + " AND f NOT IN (SELECT rf.funcion FROM RolFuncionDO rf WHERE rf.rol.id = :idRol)"
            + " ORDER BY f.idNombre")
    List<FuncionDO> findListNoAsignados(
            @Param("idAplicacion") Long idAplicacion,
            @Param("idNombre") String idNombre,
            @Param("idRol") Long idRol);
}