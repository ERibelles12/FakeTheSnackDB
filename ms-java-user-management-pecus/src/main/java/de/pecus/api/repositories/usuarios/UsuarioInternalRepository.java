package de.pecus.api.repositories.usuarios;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.pecus.api.entities.UsuarioInternoDO;

/**
 * Interfaz de acceso a datos para la entidad de tipos de telefono
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
public interface UsuarioInternalRepository extends JpaRepository<UsuarioInternoDO, Serializable> {

	/**
	 * Consulta por id sin implementacion de query especifico
	 * 
	 * @return Objeto de mapeo a la entidad
	 * 
	 * @param id
	 *            Identificador de registro buscado
	 */
	@Query(" SELECT u FROM UsuarioInternoDO u WHERE u.id = :id AND u.active = :active ")
	UsuarioInternoDO findByIdAndActive(@Param("id") Long id, @Param("active") Boolean active);

	/**
	 * Consulta por id sin implementacion de query especifico
	 * 
	 * @return Objeto de mapeo a la entidad
	 * 
	 * @param id
	 *            Identificador de registro buscado
	 */
	UsuarioInternoDO findByUserIdEmail(String userIdEmail);

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
	@Query(" SELECT usuario FROM UsuarioInternoDO usuario WHERE usuario.active = true AND usuario.id = :id")
	UsuarioInternoDO findById(@Param("id") Long id);

}