package de.pecus.api.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.type.CollectionType;

import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;

/**
 * Clase de utileria para manejo de mensajes Json
 * 
 */
public final class JsonUtil {

    /**
     * Constructor privado para evitar instanciacion de clase
     */
    private JsonUtil() {

    }

    /**
     * Obtiene una cadena en formato Json a partir de un objeto
     * @param object Objeto a convertir
     * @return Cadena Json
     * @throws JsonProcessingException 
     */
    public static <T> String generateStringJson(RequestVO<T> request) throws JsonProcessingException {
        ObjectMapper jacksonMapper = new ObjectMapper();
        return request == null ? null: jacksonMapper.writeValueAsString(request);
    }
    
    /**     
     * Obtiene una cadena en formato Json a partir de un objeto
     * @param object Objeto a convertir
     * @return Cadena Json
     * @throws JsonProcessingException 
     */
    public static <T> String generateStringJson(ResponseVO<T> response) throws JsonProcessingException {
        ObjectMapper jacksonMapper = new ObjectMapper();
        return jacksonMapper.writeValueAsString(response);
    }
    
    /**
     * Obtiene un objeto a partir de un Json dentro de un archivo
     * @param <T> Tipo de la clase de respuesta
     * @param jsonFile Archivo que contiene el mensaje json
     * @param responseClass Clase de respuesta
     * @return Objeto objeto obtenid 
     * @throws IOException
     */
    public static <T> T generateObjectFromJsonFile(File jsonFile, Class<T> responseClass) throws IOException {
        ObjectMapper jacksonMapper = new ObjectMapper();
        JsonNode node = jacksonMapper.readTree(jsonFile);
        
        ObjectReader readerData = jacksonMapper.readerFor(responseClass);
        
        T response = readerData.readValue(node);
        
        return response;
    }
    
    /**
     * Obtiene un ResponseVO a partir de una cadena Json
     * @param <T> Tipo de la clase de respuesta
     * @param json Mensaje json
     * @param responseClass Clase de respuesta
     * @return Objeto responseVO 
     * @throws IOException
     */
    public static <T> ResponseVO<T> generateResponseFromJson(String json, Class<T> responseClass) throws IOException {
        ObjectMapper jacksonMapper = new ObjectMapper();
        JsonNode node = jacksonMapper.readTree(json);
        JsonNode data = node.get("data");
        JsonNode success = node.get("success");
        
        ObjectReader readerData = jacksonMapper.readerFor(responseClass);
        ObjectReader readerResponse = jacksonMapper.readerFor(ResponseVO.class);
        
        ResponseVO<T> response = readerResponse.readValue(node);
        
        T dataObj = data != null ? readerData.readValue(data) : null;
        
        response.setData(dataObj);
        response.setSuccess(success.asBoolean());
        
        return response;
    }
    
    /**
     * Obtiene un ResponseVO a partir de una cadena Json
     * @param <T> Tipo de la clase de respuesta
     * @param json Mensaje json
     * @param responseClass Clase de respuesta
     * @return Objeto responseVO 
     * @throws IOException
     */
    public static <T> ResponseVO<List<T>> generateResponseListFromJson(String json, Class<T> responseClass) throws IOException {
        ObjectMapper jacksonMapper = new ObjectMapper();
        JsonNode node = jacksonMapper.readTree(json);
        JsonNode data = node.get("data");
        JsonNode success = node.get("success");
        
        CollectionType listType = jacksonMapper.getTypeFactory().constructCollectionType(ArrayList.class, responseClass);
        
        ObjectReader readerData = jacksonMapper.readerFor(listType);
        ObjectReader readerResponse = jacksonMapper.readerFor(ResponseVO.class);
        
        ResponseVO<List<T>> response = readerResponse.readValue(node);
        
        List<T> dataList = data != null ? readerData.readValue(data) : null;
        
        response.setData(dataList);
        response.setSuccess(success.asBoolean());
        
        return response;
    }
    
    public static String escapeBraces(String str) {
    	return ValidatorUtil.isNull(str) ? str : str.replaceAll("\\{", "'\\{");
    }
    
}
