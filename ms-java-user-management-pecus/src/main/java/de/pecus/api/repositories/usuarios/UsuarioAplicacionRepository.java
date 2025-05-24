package de.pecus.api.repositories.usuarios;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.pecus.api.entities.UsuarioAplicacionDO;

/**
 * Interfaz de acceso a datos para la entidad de tipos de telefono
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
public interface UsuarioAplicacionRepository extends JpaRepository<UsuarioAplicacionDO, Serializable> {

	/**
	 * Consulta por id sin implementacion de query especifico
	 * 
	 * @return Objeto de mapeo a la entidad
	 * 
	 * @param id
	 *            Identificador de registro buscado
	 */
	@Query(" SELECT u FROM UsuarioAplicacionDO u WHERE u.id = :id AND u.active = :active ")
	UsuarioAplicacionDO findByIdAndActive(@Param("id") Long id, @Param("active") Boolean active);

	/**
	 * Consulta por id sin implementacion de query especifico
	 * 
	 * @return Objeto de mapeo a la entidad
	 * 
	 * @param id
	 *            Identificador de registro buscado
	 */
	UsuarioAplicacionDO findByUserIdEmail(String userIdEmail);

	/**
	 * Consulta por usuario y password
	 * 
	 * @param usuario
	 *            Usuario buscado
	 * @param password
	 *            Password buscado
	 * @return ObjetoDO encontrado
	 *
	 */
	@Query(" SELECT usuario FROM UsuarioAplicacionDO usuario WHERE usuario.active = 1 AND usuario.id = :id")
	UsuarioAplicacionDO findById(@Param("id") Long id);

}