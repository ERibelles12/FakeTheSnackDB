package de.pecus.api.security.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.security.services.ValidateTokenService;
import de.pecus.api.security.services.vo.ValidateTokenResponseVO;
import de.pecus.api.util.StringUtil;
import de.pecus.api.util.ValidatorUtil;

@Service
public class ValidateTokenServiceImpl implements ValidateTokenService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateTokenServiceImpl.class);

	private static final String URL_VALIDATE_TOKEN = "url.validateToken";

	@Autowired
	private Environment env;

	/**
	 * Llama al servicio de validacion de token
	 * @param token Token a validar
	 * @return true si el token es valido y esta activo
	 */
	public ValidateTokenResponseVO validateToken(String token) {
	    
	    ValidateTokenResponseVO response = new ValidateTokenResponseVO();
		if (StringUtil.isNullOrEmpty(token)) {
		    LOGGER.error("Token vacio");
		    response.setSuccess(false);
		}
		RestTemplate restTemplate = getRestTemplate();
		
		String url = env.getProperty(URL_VALIDATE_TOKEN);
		
		HttpHeaders headers = new HttpHeaders();
	    headers.set(GeneralConstants.AUTHORIZATION, token);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
	    
		try {
		    ResponseEntity<String> responseService = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

	        ObjectMapper mapper = new ObjectMapper();
	        JsonNode root;
	        
			root = mapper.readTree(responseService.getBody());
			JsonNode successNode = root.get(GeneralConstants.SUCCESS_FIELD);
			if(!ValidatorUtil.isNull(successNode)) {
			    response.setSuccess(successNode.asBoolean());
			}
			if(root.get(GeneralConstants.NEW_TOKEN) != null) {
			    response.setNewToken(root.get(GeneralConstants.NEW_TOKEN).asText());
			}
			
		} catch (Exception e) {
			LOGGER.error("Error al llamar al servicio de validacion de token" + e.getMessage(), e);
			response.setSuccess(false);
		} 
		return response;
	}


	private RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
