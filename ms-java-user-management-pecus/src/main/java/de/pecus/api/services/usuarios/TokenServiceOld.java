/**
 * 
 */
package de.pecus.api.services.usuarios;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.util.ServiceUtil;
import de.pecus.api.vo.usuarios.TokenVO;
import net.minidev.json.JSONObject;

@Service
public class TokenServiceOld {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceOld.class);

	/**
	 * Genera un token para el usuario especificado
	 * 
	 * @param usuario Usuario autenticado
	 * 
	 * @return Cadena con token generado
	 */
	public String create(TokenVO tokenVO) {

		JSONObject json = new JSONObject();
		Boolean isPublic = false;

		tokenVO.setFechaCreacion(ServiceUtil.getCurrentDate());
		tokenVO.setFechaExpiracion(getExpirationDate(tokenVO.getFechaCreacion(), isPublic, tokenVO.getIsMobile()));
		
		tokenVO.setIsPublic(isPublic);

		json.put(GeneralConstants.TOKEN_DATA, tokenVO);

		JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(json));

		try {
			jwsObject.sign(new MACSigner(getSigningKey()));
		} catch (KeyLengthException e) {
			LOGGER.error("TokenServiceOld KeyLengthException ", e);

		} catch (JOSEException e) {
			LOGGER.error("TokenServiceOld JOSEException ", e);
		}

		return jwsObject.serialize();
	}
	
	/**
	 * Genera un token publico para el usuario especificado
	 * 
	 * @param usuario Usuario autenticado
	 * 
	 * @return Cadena con token generado
	 */
	public String createPublic(TokenVO tokenVO) {

		JSONObject json = new JSONObject();
		Boolean isPublic = true;

		tokenVO.setFechaCreacion(ServiceUtil.getCurrentDate());
		tokenVO.setFechaExpiracion(getExpirationDate(tokenVO.getFechaCreacion(), isPublic, false));

		tokenVO.setIsPublic(isPublic);
		
		json.put(GeneralConstants.TOKEN_DATA, tokenVO);

		JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(json));

		try {
			jwsObject.sign(new MACSigner(getSigningKey()));
		} catch (KeyLengthException e) {
			LOGGER.error("TokenServiceOld KeyLengthException ", e);

		} catch (JOSEException e) {
			LOGGER.error("TokenServiceOld JOSEException ", e);
		}

		return jwsObject.serialize();
	}

	/**
	 * Metodo para validacion del token
	 * @param refreshTokenExpeditionTime 
	 * @param previousToken 
	 * 
	 * @param <SesionVO> Request
	 * @return String valido ó renovar ó expirop
	 */
	public String validate(String token, boolean isPreviousToken, Date refreshTokenExpeditionTime) {
		String flag = null;

		try {
			JWSObject jwsObject = JWSObject.parse(token);
			JWSVerifier verifier = new MACVerifier(getSigningKey());

			Boolean isMobile = ServiceUtil.extractBooleanFieldFromToken(token, "isMobile");

			// Se valida que el token tenga la estructura correcta
			boolean valid = jwsObject.verify(verifier);
			if (valid) {
				// Se valida que el token no haya expirado
				boolean active = isActiveToken(
						(JSONObject) jwsObject.getPayload().toJSONObject().get(GeneralConstants.TOKEN_DATA));
				// Se valida que el token no haya expirado
                boolean inGracePeriodPreviousToken = false;
                if(isPreviousToken) {
                    isInGracePeriodPreviousToken(token, refreshTokenExpeditionTime);
                }
				
				// Si el token esta activo sigue siendo valido el token
				// Si el token esta expirado y es isMobile igual a TRUE, se renueva token
				// Si no esta activo y no es Mobile entonces expira el token
				if (!active && isMobile && !isPreviousToken) {
					flag = GeneralConstants.TOKEN_REFRESH;
				} else if (!isPreviousToken && active) {
					flag = GeneralConstants.TOKEN_VALID;
				} else if (inGracePeriodPreviousToken) {
                    flag = GeneralConstants.TOKEN_VALID;
                } else {
					flag = GeneralConstants.TOKEN_EXPIRED;
				}
			}
		} catch (Exception e) {
			LOGGER.error("TokenServiceOld Exception", e);
		}

		return flag;

	}

	private boolean isInGracePeriodPreviousToken(String token, Date refreshTokenExpeditionTime) {
	    boolean isActive = false;
        try {
            Date expirationDate = DateUtils.addMinutes(refreshTokenExpeditionTime, GeneralConstants.TOKEN_PREV_TOKEN_GRACE_PERIOD);
            isActive = !(ServiceUtil.getCurrentDate().compareTo(expirationDate) > GeneralConstants.ZERO);
            if(!isActive) {
                LOGGER.error("Periodo de gracia vencido para el token [" + token + "], refreshTokenExpeditionTime [" + refreshTokenExpeditionTime +"], expirationDate ["+ expirationDate +"], comparcion de ServiceUtil.getCurrentDate().compareTo(expirationDate)" + ServiceUtil.getCurrentDate().compareTo(expirationDate) + "]");
            }
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al validar el periodo de gracia del token [" + token + "]" , e);
        }
        return isActive;
    }

    /**
	 * Obtiene el shared key
	 * 
	 * @return Key leido
	 */
	private byte[] getSigningKey() {

		byte[] key = null;

		key = Base64.decode(findKey());

		return key;

	}

	private String findKey() {

		// obtener de tabla de parametros

		return GeneralConstants.TOKEN_KEY;

	}

	/**
	 * Metodo que obtiene la fecha de expiracion
	 * 
	 * @param creationDate Fecha de creacion
	 * @return Fecha de creacion mas el numero de minutos de timeout de sesion
	 */
	private Date getExpirationDate(Date creationDate, Boolean isPublic, Boolean isMobile) {		
		// Se obtiene el tiempo de expiracion
		Integer timeoutSession = GeneralConstants.TIMEOUT_SESSION;
		if(isMobile) {
		    timeoutSession = GeneralConstants.TIMEOUT_MOBILE_SESSION;
		} else if(isPublic) {
			timeoutSession = GeneralConstants.TIMEOUT_PUBLIC_SESSION;
		}

		if (creationDate != null) {
		    creationDate = DateUtils.addMinutes(creationDate, timeoutSession);
		}
		return creationDate;
	}

	/**
	 * Valida si el token no ha expirado.
	 * 
	 * @param json Objeto json con los datos del token
	 * @return True si la fecha de expiracion no se ha sobrepasado
	 */
    private Boolean isActiveToken(JSONObject json) {
        boolean isActive = false;
        try {
            String dateString = json.get(GeneralConstants.EXPIRATION_DATE).toString();
            
            Date expirationDate = GeneralConstants.FAST_DATE_TIME_FORMAT_TOKEN.parse(dateString);
            isActive = !(ServiceUtil.getCurrentDate().compareTo(expirationDate) > 0);
            if(!isActive) {
                LOGGER.error("La fecha de expiracion [" + json.get(GeneralConstants.EXPIRATION_DATE).toString() + "] es invalida");
            }
        } catch (Exception e) {
            LOGGER.error("Formato de fecha invalido [" + json.get(GeneralConstants.EXPIRATION_DATE).toString() + "]" , e);
        }
        return isActive;
    }

}
