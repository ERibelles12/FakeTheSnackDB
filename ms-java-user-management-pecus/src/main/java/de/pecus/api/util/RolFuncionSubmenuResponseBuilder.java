package de.pecus.api.util;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import de.pecus.api.error.FuncionesBusinessError;
import de.pecus.api.error.GeneralBusinessErrors;
import de.pecus.api.vo.ResponseErrorVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.funciones.FindListRolFuncionMenuResponseVO;
import de.pecus.api.vo.funciones.FindListRolFuncionSubmenuResponseVO;

public class RolFuncionSubmenuResponseBuilder {

	/**
	 * Método para analizar la respuesta del servicio y transformarla a la respuesta
	 * del protocolo de salida
	 * 
	 */
	public static final ResponseEntity<ResponseVO<List<Long>>> buildCreateRolFuncionMenuResponse(
			ResponseVO<List<Long>> serviceResponse) {
		ResponseEntity<ResponseVO<List<Long>>> response = null;
		if (ValidatorUtil.isSuccessfulResponse(serviceResponse)) {
			response = ResponseEntity.status(HttpStatus.CREATED).body(serviceResponse);
		} else {
			for (ResponseErrorVO responseErrorVO : serviceResponse.getErrors()) {
				if (responseErrorVO.getKey().equals(GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR)
						|| responseErrorVO.getKey().equals(FuncionesBusinessError.NOT_FOUND_FUNCION_ID_ERROR)
						|| responseErrorVO.getKey().equals(FuncionesBusinessError.NOT_FOUND_SUBMENU_ERROR)
						|| responseErrorVO.getKey().equals(FuncionesBusinessError.DUPLICATED_ERROR)) {
					response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(serviceResponse);
					break;
				} else {
					response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(serviceResponse);
					break;
				}
			}
		}
		return response;
	}
	
	public static final ResponseEntity<ResponseVO<Boolean>> buildDeleteResponse(ResponseVO<Boolean> serviceResponse) {

		ResponseEntity<ResponseVO<Boolean>> response = null;

		if (ValidatorUtil.isSuccessfulResponse(serviceResponse)) {
			response = ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
		} else {
			for (ResponseErrorVO responseErrorVO : serviceResponse.getErrors()) {
				if (responseErrorVO.getKey().equals(GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR)
						|| responseErrorVO.getKey().equals(FuncionesBusinessError.REQUIRED_ID_ERROR)) {
					response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(serviceResponse);
					break;
				} else if(responseErrorVO.getKey().equals(FuncionesBusinessError.NOT_FOUND_ERROR)) {
					response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(serviceResponse);
					break;
				} 
				else {
					response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(serviceResponse);
					break;
				}
			}
		}
		return response;
	}
	
	public static final ResponseEntity<ResponseVO<List<FindListRolFuncionSubmenuResponseVO>>> buildFindListRolFuncionSubmenuResponse
	(ResponseVO<List<FindListRolFuncionSubmenuResponseVO>> serviceResponse) {
		
		ResponseEntity<ResponseVO<List<FindListRolFuncionSubmenuResponseVO>>> response = null;
		
		if (ValidatorUtil.isSuccessfulResponse(serviceResponse)) {
			response = ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
		} else {
			for (ResponseErrorVO responseErrorVO : serviceResponse.getErrors()) {
				if (responseErrorVO.getKey().equals(GeneralBusinessErrors.REQUIRED_PAGE_NUMBER_ERROR)
						|| responseErrorVO.getKey().equals(GeneralBusinessErrors.MIN_VALUE_PAGE_NUMBER_ERROR)
						|| responseErrorVO.getKey().equals(GeneralBusinessErrors.REQUIRED_PAGE_SIZE_ERROR)
						|| responseErrorVO.getKey().equals(GeneralBusinessErrors.MIN_VALUE_PAGE_SIZE_ERROR)
						|| responseErrorVO.getKey().equals(FuncionesBusinessError.NOT_FOUND_REGISTER_LIST_ERROR)
						|| responseErrorVO.getKey().equals(FuncionesBusinessError.REQUIRED_APPLICATION_ID_ERROR)
						|| responseErrorVO.getKey().equals(FuncionesBusinessError.REQUIRED_ID_ERROR)) {
					response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(serviceResponse);
					break;
				} else if (responseErrorVO.getKey().equals(FuncionesBusinessError.NOT_FOUND_REGISTER_LIST_ERROR)) {
					response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(serviceResponse);
					break;
				} else {
					response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(serviceResponse);
					break;
				}
			}
		}
		return response;
	}
}
