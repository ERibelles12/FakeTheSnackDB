/**
 * 
 */
package de.pecus.api.util;

import de.pecus.api.vo.ResponseVO;

/**
 * Una utileria para transformar un objeto ResponseVO de un tipo a otro
 * 
 * @author Fancisco Casales
 *
 */
public class ResponseVOUtil {


    @SuppressWarnings({ "unchecked" })
    public static final <T, K> ResponseVO<K> setNewResponseVO(ResponseVO<T> originalResponseVO, Object parameters){
    	ResponseVO<K> newResponseVO = new ResponseVO<K>();
    	newResponseVO.setErrors(originalResponseVO.getErrors());
    	newResponseVO.setSuccess(originalResponseVO.getSuccess());
    	newResponseVO.setTotalRows(originalResponseVO.getTotalRows());
    	newResponseVO.setData((K)parameters);
    	return newResponseVO;
    }
	
}
