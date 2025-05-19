package de.pecus.api.util;

import java.util.List;

import org.springframework.data.domain.Page;

import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.vo.ResponseErrorVO;
import de.pecus.api.vo.ResponseVO;

public class PageableUtils {

	/**
	 * Llena objeto del controller.
	 * 
	 * @param listPage
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> ResponseVO<List<T>> validatePageList (Page<T> listPage, ResponseVO response)  {
		
		ResponseVO<List<T>> responseList = new ResponseVO<List<T>>();
		
		if (ValidatorUtil.isNullOrEmpty(response.getErrors())) {
			
			// Si no se encontro ningun registro que cumpla la condicion generar error.
		    if (!ValidatorUtil.isNullOrEmpty(listPage.getContent())) {
		    	responseList.setTotalRows(listPage.getTotalElements());
		    	responseList.setData(listPage.getContent());
		    } else {
		    	
		    	responseList.getErrors().add(fillGeneralErrorNotFound());
		    }
			
		}else{
			responseList.setErrors(response.getErrors());
		}
	
	    
		responseList.setSuccess(ValidatorUtil.isNullOrEmpty(responseList.getErrors()));
	    
	    return responseList;
		
	}

	/**
	 * Llena el Error generico not found
	 * 
	 * @return
	 */
	private static ResponseErrorVO fillGeneralErrorNotFound() {
		ResponseErrorVO responseErrorVO = new ResponseErrorVO() ;
		responseErrorVO.setCode(GeneralConstants.GENERIC_ERROR_CODE);
		responseErrorVO.setMessage(GeneralConstants.GENIERIC_NOT_FOUND);
		return responseErrorVO;
	}

}
