package de.pecus.api.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.enums.CommonHeadersEnum;
import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseErrorVO;
import de.pecus.api.vo.ResponseVO;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

public class GenericExecuteWSUtils {

	/*
	 * Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(GenericExecuteWSUtils.class);

	/**
	 * Servicio que ejecuta el WS
	 * 
	 * @param token
	 * @param requetVO
	 * @param url
	 */
	@SuppressWarnings({ "deprecation" })
	public static <T> ResponseVO<JSONObject> executExchange(T params, String url, String method) {

		RestTemplate restTemplate = new RestTemplate();
		ResponseVO<JSONObject> response = new ResponseVO<JSONObject>();

		try {

			URI uri = new URI(url);

			ObjectMapper mapper = new ObjectMapper();
			String requestJson = generateJSON(params, mapper);

			HttpHeaders headers = buildBaseHeaders();
			HttpEntity<String> request = new HttpEntity<String>(requestJson, headers);

			ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.valueOf(method.toUpperCase()),
					request, String.class);

			if (result.getStatusCode().is2xxSuccessful()) {

				response.setData((JSONObject) new JSONParser().parse(result.getBody()));
				response.setSuccess(Boolean.TRUE);

			}

		} catch (Exception e) {
			response.setSuccess(Boolean.FALSE);
			response.getErrors().add(newResponseErrorVO(GeneralConstants.GENERIC_ERROR_CODE,
					GeneralConstants.GENERIC_ERROR_KEY, fillGenericMessage(e).toString()));
		}

		return response;

	}

	/**
	 * Servicio que ejecuta el WS
	 * 
	 * @param token
	 * @param requetVO
	 * @param url
	 */
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public static <T> ResponseVO<JSONObject> executExchange(String url, HttpMethod method, HttpEntity requestEntity) {

		RestTemplate restTemplate = new RestTemplate();
		ResponseVO<JSONObject> response = new ResponseVO<JSONObject>();

		try {

			URI uri = new URI(url);

			ResponseEntity<String> result = restTemplate.exchange(uri, method, requestEntity, String.class);

			if (result.getStatusCode().is2xxSuccessful()) {

				response.setData((JSONObject) new JSONParser().parse(result.getBody()));
				response.setSuccess(Boolean.TRUE);

			}

		} catch (Exception e) {
			response.setSuccess(Boolean.FALSE);
			response.getErrors().add(newResponseErrorVO(GeneralConstants.GENERIC_ERROR_CODE,
					GeneralConstants.GENERIC_ERROR_KEY, fillGenericMessage(e).toString()));
		}

		return response;

	}

	/**
	 * Servicio que ejecuta el WS
	 * 
	 * @param token
	 * @param requetVO
	 * @param url
	 */
	@SuppressWarnings({ "deprecation" })
	public static <T> ResponseVO<JSONArray> executExchangeList(T params, String url, String method) {

		RestTemplate restTemplate = new RestTemplate();
		ResponseVO<JSONArray> response = new ResponseVO<JSONArray>();

		try {

			URI uri = new URI(url);

			ObjectMapper mapper = new ObjectMapper();
			String requestJson = generateJSON(params, mapper);

			HttpHeaders headers = buildBaseHeaders();
			HttpEntity<String> request = new HttpEntity<String>(requestJson, headers);

			ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.valueOf(method.toUpperCase()),
					request, String.class);

			if (result.getStatusCode().is2xxSuccessful()) {

				response.setData((JSONArray) new JSONParser().parse(result.getBody()));
				response.setSuccess(Boolean.TRUE);

			}

		} catch (Exception e) {
			response.setSuccess(Boolean.FALSE);
			response.getErrors().add(newResponseErrorVO(GeneralConstants.GENERIC_ERROR_CODE,
					GeneralConstants.GENERIC_ERROR_KEY, fillGenericMessage(e).toString()));
		}

		return response;

	}

	/**
	 * Servicio que ejecuta el WS
	 * 
	 * @param token
	 * @param requetVO
	 * @param url
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ResponseVO executExchange(String token, RequestVO requetVO, String url, String method) {

		RestTemplate restTemplate = new RestTemplate();
		ResponseVO response = new ResponseVO();

		try {

			URI uri = new URI(url);

			ObjectMapper mapper = new ObjectMapper();
			String requestJson = generateJSON(requetVO, mapper);

			HttpHeaders headers = addAuthorizationHeaderJwt(token);
			HttpEntity<String> request = new HttpEntity<String>(requestJson, headers);

			ResponseEntity<ResponseVO> result = restTemplate.exchange(uri, HttpMethod.valueOf(method.toUpperCase()),
					request, ResponseVO.class);

			response.setData(result.getBody().getData());
			response.setErrors(result.getBody().getErrors());
			response.setSuccess(result.getBody().getSuccess());
			response.setTotalRows(result.getBody().getTotalRows());

		} catch (Exception e) {
			response.setSuccess(Boolean.FALSE);
			response.getErrors().add(newResponseErrorVO(GeneralConstants.GENERIC_ERROR_CODE,
					GeneralConstants.GENERIC_ERROR_KEY, fillGenericMessage(e).toString()));
		}

		return response;

	}

	/**
	 * Servicio que ejecuta el WS
	 * 
	 * @param token
	 * @param requetVO
	 * @param url
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ResponseVO executeRestService(String token, RequestVO requetVO, String url) {

		RestTemplate restTemplate = new RestTemplate();
		ResponseVO response = new ResponseVO();

		try {

			URI uri = new URI(url);

			ObjectMapper mapper = new ObjectMapper();
			String requestJson = generateJSON(requetVO, mapper);

			HttpHeaders headers = addAuthorizationHeaderJwt(token);
			HttpEntity<String> request = new HttpEntity<String>(requestJson, headers);

			ResponseEntity<ResponseVO> result = restTemplate.postForEntity(uri, request, ResponseVO.class);

			response.setData(result.getBody().getData());
			response.setErrors(result.getBody().getErrors());
			response.setSuccess(result.getBody().getSuccess());
			response.setTotalRows(result.getBody().getTotalRows());

		} catch (Exception e) {
			response.setSuccess(Boolean.FALSE);
			response.getErrors().add(newResponseErrorVO(GeneralConstants.GENERIC_ERROR_CODE,
					GeneralConstants.GENERIC_ERROR_KEY, fillGenericMessage(e).toString()));
		}

		return response;

	}

	/**
	 * Servicio que ejecuta el WS
	 * 
	 * @param token
	 * @param requetVO
	 * @param url
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ResponseVO executeRestService(String token, RequestVO requetVO, String url, ResponseVO response) {

		RestTemplate restTemplate = new RestTemplate();

		try {

			URI uri = new URI(url);

			ObjectMapper mapper = new ObjectMapper();
			String requestJson = generateJSON(requetVO, mapper);

			HttpHeaders headers = addAuthorizationHeaderJwt(token);
			HttpEntity<String> request = new HttpEntity<String>(requestJson, headers);

			ResponseEntity<ResponseVO> result = restTemplate.postForEntity(uri, request, ResponseVO.class);

			response.setData(result.getBody().getData());
			response.setErrors(result.getBody().getErrors());
			response.setSuccess(result.getBody().getSuccess());
			response.setTotalRows(result.getBody().getTotalRows());

		} catch (Exception e) {
			response.setSuccess(Boolean.FALSE);
			response.getErrors().add(newResponseErrorVO(GeneralConstants.GENERIC_ERROR_CODE,
					GeneralConstants.GENERIC_ERROR_KEY, fillGenericMessage(e).toString()));
		}

		return response;

	}

	/**
	 * Execute rest service with credentials.
	 *
	 * @param <T>         the generic type
	 * @param url         the url
	 * @param user        the user
	 * @param pwd         the pwd
	 * @param requestJson the request json
	 * @param type        the type
	 * @return the response entity
	 */
	public static <T> ResponseEntity<T> executeRestServiceWithCredentials(String url, String user, String pwd,
			String requestJson, Class<T> type) throws HttpStatusCodeException {
		ResponseEntity<T> response = null;
		URI uri = getUri(url);
		if (!ValidatorUtil.isNull(uri)) {
			RestTemplate restTemplate = new RestTemplate();
			String base64Creds = buildAuthBasicToken(user, pwd);
			HttpHeaders headers = addAuthorizationheaderBasicAuth(base64Creds);
			HttpEntity<String> request = new HttpEntity<String>(requestJson, headers);
			response = restTemplate.postForEntity(uri, request, type);
		} else {
			response = new ResponseEntity<T>(HttpStatus.NOT_FOUND);
			LOGGER.info("No se encontro la siguiente url: " + url);
		}
		return response;
	}

	/**
	 * Execute rest service with query params.
	 *
	 * @param <T>    the generic type
	 * @param url    the url
	 * @param user   the user
	 * @param pwd    the pwd
	 * @param params the params
	 * @param type   the type
	 * @return the response entity
	 * @throws HttpStatusCodeException the http status code exception
	 */
	public static <T> ResponseEntity<T> executeRestServiceWithQueryParams(String url, String user, String pwd,
			MultiValueMap<String, String> params, Class<T> type) throws HttpStatusCodeException {
		ResponseEntity<T> response = null;
		URI uri = getUri(url);
		if (!ValidatorUtil.isNull(uri)) {
			RestTemplate restTemplate = new RestTemplate();
			String base64Creds = buildAuthBasicToken(user, pwd);
			HttpHeaders headers = addAuthorizationheaderBasicAuth(base64Creds);
			HttpEntity<String> request = new HttpEntity<String>(headers);
			UriComponentsBuilder builder = UriComponentsBuilder.fromUri(uri).queryParams(params);
			response = restTemplate.exchange(builder.build().toUri(), HttpMethod.POST, request, type);
		} else {
			response = new ResponseEntity<T>(HttpStatus.NOT_FOUND);
			LOGGER.info("No se encontro la siguiente url: " + url);
		}
		return response;
	}

	/**
	 * @param stream
	 * @return
	 */
	public static String readStream(InputStream stream) {
		StringBuilder builder = new StringBuilder();
		char[] buffer = new char[4096];
		InputStreamReader reader = null;
		try {
			try {
				reader = new InputStreamReader(stream, "UTF-8");
				int charsRead;
				while ((charsRead = reader.read(buffer)) != -1) {
					builder.append(buffer, 0, charsRead);
				}
			} finally {
				if (!ValidatorUtil.isNull(reader)) {
					reader.close();
				}
			}
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al tratar de convertir la respuesta del servicio REST a JSON String");
		}
		return builder.toString();
	}

	/**
	 * Gets the uri.
	 *
	 * @param url the url
	 * @return the uri
	 */
	private static URI getUri(String url) {
		URI uri = null;
		try {
			if (!ValidatorUtil.isNull(url)) {
				uri = new URI(url);
			}
		} catch (URISyntaxException e) {
			LOGGER.error("La sintaxys de la URI es incorrecta.", e);
		}

		return uri;
	}

	/**
	 * Builds the auth basic token.
	 *
	 * @param user the user
	 * @param pwd  the pwd
	 * @return the string
	 */
	private static String buildAuthBasicToken(String user, String pwd) {
		String base64Creds = null;
		if (!ValidatorUtil.isNullOrEmpty(user) && !ValidatorUtil.isNullOrEmpty(pwd)) {
			String plainCreds = user + ":" + pwd;
			byte[] plainCredsBytes = plainCreds.getBytes();
			byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
			base64Creds = new String(base64CredsBytes);
		}
		return base64Creds;
	}

	/**
	 * New response error VO.
	 *
	 * @param code    the code
	 * @param keyName the key name
	 * @param message the message
	 * @return the response error VO
	 */
	public static ResponseErrorVO newResponseErrorVO(String code, String keyName, String message) {
		ResponseErrorVO responseErrorVO = new ResponseErrorVO(code, keyName, message, null);
		return responseErrorVO;
	}

	/**
	 * Fill generic message.
	 *
	 * @param e the e
	 * @return the string builder
	 */
	public static StringBuilder fillGenericMessage(Exception e) {

		StringBuilder genericError = new StringBuilder()
				.append(e.getCause() != null ? e.getCause().getMessage().concat(GeneralConstants.SCORE) : "")
				.append(e.getMessage() != null && !e.getMessage().isEmpty() ? e.getMessage() : "");
		return genericError;
	}

	/**
	 * Fill generic message.
	 *
	 * @param e the e
	 * @return the string builder
	 */
	public static StringBuilder fillGenericMessage(String e) {

		StringBuilder genericError = new StringBuilder().append(e);
		return genericError;
	}

	/**
	 * Agregga encabezado de autorizacion con un token (jwt).
	 * 
	 * @param token
	 * @return HttpHeaders autorization
	 */
	private static HttpHeaders addAuthorizationHeaderJwt(String token) {

		HttpHeaders headers = buildBaseHeaders();
		headers.set("Authorization", token);

		return headers;
	}

	/**
	 * Genera el objeto con los headers genericos.
	 *
	 * @return the http headers
	 */
	private static HttpHeaders buildBaseHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Control-Allow-Origin", "*");
		headers.set("Access-Control-Allow-Headers",
				"Content-Type,Access-Control-Allow-Headers,Authorization,X-Requested-With,Authorization");
		headers.set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
		headers.set("Content-Type", "application/json;charset=UTF-8");
		return headers;
	}

	/**
	 * Agrega encabezado de autorizacion con autenticacion basica
	 * 
	 * @param token
	 * @return HttpHeaders autorization
	 */
	private static HttpHeaders addAuthorizationheaderBasicAuth(String credentials) {

		HttpHeaders headers = buildBaseHeaders();
		headers.set("Authorization", "Basic " + credentials);

		return headers;
	}

	/**
	 * Genera el JSON que se envia a la peticion.
	 * 
	 * @param requestJson
	 * @param requetVO
	 * @param mapper
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	private static String generateJSON(RequestVO requetVO, ObjectMapper mapper) {

		String requestJson = new String();

		try {

			requestJson = mapper.writeValueAsString(requetVO);
			LOGGER.info("Request JSON: " + requestJson);

		} catch (JsonProcessingException e) {
			LOGGER.error("Ocurrio un error al generar el JSON request ", e);
		}

		return requestJson;
	}

	/**
	 * Genera el JSON que se envia a la peticion.
	 * 
	 * @param requestJson
	 * @param requetVO
	 * @param mapper
	 * @return String
	 */
	private static <T> String generateJSON(T params, ObjectMapper mapper) {

		String requestJson = new String();

		try {

			requestJson = mapper.writeValueAsString(params);
			LOGGER.info("Request JSON: " + requestJson);

		} catch (JsonProcessingException e) {
			LOGGER.error("Ocurrio un error al generar el JSON request ", e);
		}

		return requestJson;
	}

	/**
	 * Servicio que ejecuta el WS
	 * 
	 * @param token
	 * @param requetVO
	 * @param url
	 */
	public static <T, K> ResponseVO<K> executExchange(RequestVO<T> requestVO, String url, String method,
			Class<K> dataResponseType) {

		RestTemplate restTemplate = new RestTemplate();
		ResponseVO<K> response = new ResponseVO<K>();
		// Generamos instancia de GSON
		Gson gson = new Gson();

		try {

			URI uri = new URI(url);

			ObjectMapper mapper = new ObjectMapper();
			String requestJson = generateJSON(requestVO.getParameters(), mapper);

			HttpHeaders headers = buildBaseHeaders(requestVO);
			HttpEntity<String> request = new HttpEntity<String>(requestJson, headers);

			ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.valueOf(method.toUpperCase()),
					request, String.class);

			if (result.getStatusCode().is2xxSuccessful()) {
				JsonObject jsonObject = gson.fromJson(result.getBody(), JsonObject.class);
				JsonElement dataJson = jsonObject.get("data");
				if (dataJson.isJsonArray()) {
					Type collectionType = new TypeToken<Collection<K>>() {
					}.getType();
					response.setData(gson.fromJson(dataJson, collectionType));
				} else {
					response.setData(gson.fromJson(dataJson, dataResponseType));
				}
				response.setSuccess(Boolean.TRUE);
			} else {
				JsonObject jsonObject = gson.fromJson(result.getBody(), JsonObject.class);
				JsonElement jsonElementErros = jsonObject.get("errors");
				response.setErrors(gson.fromJson(jsonElementErros.toString(), new TypeToken<List<ResponseErrorVO>>() {
				}.getType()));
				response.setSuccess(Boolean.FALSE);
			}

		} catch (HttpServerErrorException e) {
			if(!ValidatorUtil.isNullOrEmpty(e.getResponseBodyAsString())) {
				buildResponseErrors(response, gson, e);
			} else {
				buildGeneralErrorResponse(response, e);
			}
			
		}catch (Exception e) {
			buildGeneralErrorResponse(response, e);
		}

		return response;

	}

	private static <K> void buildGeneralErrorResponse(ResponseVO<K> response, Exception e) {
		response.setSuccess(Boolean.FALSE);
		response.getErrors().add(new ResponseErrorVO(GeneralConstants.GENERAL_ERROR_CODE,
				GeneralConstants.GENERAL_ERROR_KEY, GeneralConstants.GENERAL_ERROR_MESSAGE, e));
	}

	private static <K> void buildResponseErrors(ResponseVO<K> response, Gson gson, HttpServerErrorException error) {
		JsonObject jsonObject = gson.fromJson(error.getResponseBodyAsString(), JsonObject.class);
		JsonElement jsonElementErrors = jsonObject.get("errors");
		if(!ValidatorUtil.isNull(jsonElementErrors)) {
			response.setErrors(gson.fromJson(jsonElementErrors.toString(), new TypeToken<List<ResponseErrorVO>>() {
			}.getType()));
		} else {
			JsonElement jsonElementError = jsonObject.get("error");
			response.getErrors().add(new ResponseErrorVO(GeneralConstants.GENERAL_ERROR_CODE,
					GeneralConstants.GENERAL_ERROR_KEY, jsonElementError.getAsString(), error));
		}
		response.setSuccess(Boolean.FALSE);
	}

	/**
	 * Genera el objeto con los headers genericos.
	 *
	 * @return the http headers
	 */
	private static <T> HttpHeaders buildBaseHeaders(RequestVO<T> requestVO) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Control-Allow-Origin", "*");
		headers.set("Access-Control-Allow-Headers",
				"Content-Type,Access-Control-Allow-Headers,Authorization,X-Requested-With,Authorization");
		headers.set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
		headers.set("Content-Type", "application/json;charset=UTF-8");
		headers.set(CommonHeadersEnum.AUTHORIZATION.getKey(), requestVO.getToken());
		headers.set(CommonHeadersEnum.CODIGO_OPERACION_CLIENTE.getKey(), requestVO.getCodigoOperacionCliente());
		headers.set(CommonHeadersEnum.ID_CLIENTE_INVOKE.getKey(), requestVO.getIdClienteInvoke());
		headers.set(CommonHeadersEnum.LANGUAGE.getKey(), requestVO.getIdioma());
		if (!ValidatorUtil.isNullOrZero(requestVO.getLatitude())) {
			headers.set(CommonHeadersEnum.LATITUDE.getKey(), String.valueOf(requestVO.getLatitude()));
		}
		if (!ValidatorUtil.isNullOrZero(requestVO.getLongitude())) {
			headers.set(CommonHeadersEnum.LONGITUDE.getKey(), String.valueOf(requestVO.getLongitude()));
		}
		headers.set(CommonHeadersEnum.MAC_ADDRESS.getKey(), requestVO.getMacAddress());
		headers.set(CommonHeadersEnum.REQUEST_DATE.getKey(), DateTimeUtil
				.requestDateToStringWithTimeZone(requestVO.getRequestDate(), requestVO.getTimeZoneUsuario()));
		headers.set(CommonHeadersEnum.TIME_ZONE.getKey(), requestVO.getTimeZoneUsuario());
		return headers;
	}

}
