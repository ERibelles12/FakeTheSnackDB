package de.pecus.api.security.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.pecus.api.entities.RolFuncionMenuDO;

public interface RolFuncionMenuBaseRepository extends JpaRepository<RolFuncionMenuDO, Serializable>{

	/**
     * Consulta por URI y rol asociado al usuario para encontrar la relacion del menu-funcion-rol.
	 * @param uri endpoint de la peticion.
     * @param rolId rol del usuario
     * @return RolFuncionMenuDO rolFuncionMenuDO encontrado.
     *
     */
    @Query(value = " SELECT rfm" +
            " FROM MenuDO m, RolFuncionMenuDO rfm, FuncionDO f, RolFuncionDO rf" +
            " WHERE m.id = rfm.menu " +
            " AND rfm.rolFuncion = rf.id" +
            " AND f.id = rf.funcion" +
            " AND rf.rol.id = :rolId" + 
            " AND :uri like concat(m.path, f.path ,'%')")
    RolFuncionMenuDO findByMenuAndRolFuncion(@Param("rolId") Long rolId, @Param("uri") String uri);

}
