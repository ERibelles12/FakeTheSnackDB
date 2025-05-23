package de.pecus.api.repositories.usuarios;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.pecus.api.entities.UsuarioDO;

/**
 * Interfaz de acceso a datos para la entidad de tipos de telefono
 * 
 * @author Luis Enrique Sanchez Santiago
 *
 */
public interface UsuarioRepository extends JpaRepository<UsuarioDO, Serializable> {

	/**
	 * Consulta por id sin implementacion de query especifico
	 * 
	 * @return Objeto de mapeo a la entidad
	 * 
	 * @param id
	 *            Identificador de registro buscado
	 */
	@Query(" SELECT u FROM UsuarioDO u WHERE u.id = :id AND u.active = :active ")
	UsuarioDO findByIdAndActive(@Param("id") Long id, @Param("active") Boolean active);

	/**
	 * Consulta por id sin implementacion de query especifico
	 * 
	 * @return Objeto de mapeo a la entidad
	 * 
	 * @param id
	 *            Identificador de registro buscado
	 */
	UsuarioDO findByUserIdEmail(String userIdEmail);

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
	@Query(" SELECT usuario FROM UsuarioDO usuario WHERE usuario.active = true AND usuario.id = :id")
	UsuarioDO findById(@Param("id") Long id);

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
	@Query(" SELECT usuario FROM UsuarioDO usuario WHERE usuario.id = :id")
	UsuarioDO findByIdNoActiveFilter(@Param("id") Long id);
	
	/**
	 * Consulta para validar si la combinacion lada + telefono ya sea han
	 * registrado y el usuario ya ha registrado una contrasena.
	 *
	 * @param lada
	 * @param mobileNumber
	 * @return
	 */
	@Query(value = "SELECT user " + " FROM UsuarioDO user " + " WHERE user.ladaPais LIKE :lada AND user.userIdMobileNumber LIKE :mobileNumber "
			+ " AND user.password IS NOT NULL ")
	UsuarioDO findByMobileNumberAndLadaPais(@Param("lada") String lada, @Param("mobileNumber") String mobileNumber);

}
