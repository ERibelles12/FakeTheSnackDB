package de.pecus.api.security.services;

import de.pecus.api.security.services.vo.ValidateTokenResponseVO;

public interface ValidateTokenService {

	/**
	 * Llama al servicio de validacion de token
	 * @param token Token a validar
	 * @return true si el token es valido y esta activo
	 */
	public ValidateTokenResponseVO validateToken(String token);
}
