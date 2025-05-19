package de.pecus.api.security.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.pecus.api.config.SecurityRolMenuProperties;
import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.entities.RelUsuarioRolDO;
import de.pecus.api.entities.RolFuncionMenuDO;
import de.pecus.api.security.repositories.RelUsuarioRolRepository;
import de.pecus.api.security.repositories.RolFuncionMenuBaseRepository;
import de.pecus.api.security.services.ValidateMenuService;
import de.pecus.api.util.ServiceUtil;
import de.pecus.api.util.StringUtil;
import de.pecus.api.util.ValidatorUtil;

/**
 * 
 * Clase de logica de negocio para autorizacion del rol en las funciones
 *
 * @author Luis Enrique Sanchez Santiago
 *
 */
@Service
public class ValidateMenuServiceImpl implements ValidateMenuService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateMenuServiceImpl.class);

	@Autowired
	private RelUsuarioRolRepository usuarioCondoRolRepository;

	@Autowired
	private RolFuncionMenuBaseRepository rolFuncionMenuRepository;

	@Autowired
	private SecurityRolMenuProperties securityRolMenuProperties; 

	/**
	 * Metodo para la validacion de la funcion contra el rol en la peticion.
	 * 
	 * @param token      token de la peticion para obtener el rol del usuario.
	 * @param requestURI endpoint de la peticion.
	 * 
	 * @return Boolean verdadero 
	 * 						+ si la bandera isSecurityRolMenuvalidation esta activa.
	 * 						+ si el rol-usuario y la funcion son validos para la peticion.
	 * si no, regresa falso.
	 */
	public Boolean validateRolFuncionByRequestURI(String token, String uri) {
		LOGGER.debug("validacion de menu con el rol : " + securityRolMenuProperties.isRolmenuValidation());
		if(!securityRolMenuProperties.isRolmenuValidation()) {
			return true;
		}

		String idUsuario = ServiceUtil.extractFieldValueFromToken(token, GeneralConstants.USER_ID);
		String idNombreAplicacion = ServiceUtil.extractFieldValueFromToken(token, GeneralConstants.APLICACION);
		if (StringUtil.isNullOrEmpty(idUsuario)) {
			LOGGER.error(GeneralConstants.USER_ID + " vacio.");
			return false;
		}
		RelUsuarioRolDO usuarioCondoRolDO = usuarioCondoRolRepository.findByIdUsuarioAndAplicacion(Long.valueOf(idUsuario), idNombreAplicacion);
		if (ValidatorUtil.isNull(usuarioCondoRolDO) || ValidatorUtil.isNull(usuarioCondoRolDO.getRol())
				|| ValidatorUtil.isNullOrZero(usuarioCondoRolDO.getRol().getId())) {
			LOGGER.error("El usuario no tiene un rol asociado.");
			return false;
		}
		RolFuncionMenuDO rolFuncionMenuDO = rolFuncionMenuRepository
				.findByMenuAndRolFuncion(usuarioCondoRolDO.getRol().getId(), uri);
		
		
		return rolFuncionMenuDO != null;

	}

}
