package de.pecus.api.util;

import java.util.List;

import de.pecus.api.vo.product.FindListProductRecipeRequestVO;
import de.pecus.api.vo.product.FindListProductRecipeResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import de.pecus.api.error.FuncionesBusinessError;
import de.pecus.api.error.GeneralBusinessErrors;
import de.pecus.api.vo.ResponseErrorVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.funciones.FindDetailFuncionResponseVO;
import de.pecus.api.vo.funciones.FindListFuncionResponseVO;
import de.pecus.api.vo.product.FindDetailProductResponseVO;
import de.pecus.api.vo.product.FindListProductResponseVO;

public class ProductServicesResponseBuilder {

	/**
	 * Método para analizar la respuesta del servicio y transformarla a la respuesta
	 * del protocolo de salida
	 * 
	 */
	public static final ResponseEntity<ResponseVO<Long>> buildCreateOrUpdateResponse(ResponseVO<Long> serviceResponse) {
		ResponseEntity<ResponseVO<Long>> response = null;
		if (ValidatorUtil.isSuccessfulResponse(serviceResponse)) {
			response = ResponseEntity.status(HttpStatus.CREATED).body(serviceResponse);
		} else {
			for (ResponseErrorVO responseErrorVO : serviceResponse.getErrors()) {
				if (responseErrorVO.getKey().equals(GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR)
						|| responseErrorVO.getKey().equals(FuncionesBusinessError.MIN_SIZE_ID_NOMBRE_ERROR)
						|| responseErrorVO.getKey().equals(FuncionesBusinessError.REQUIRED_ID_NOMBRE_ERROR)
						|| responseErrorVO.getKey().equals(FuncionesBusinessError.REQUIRED_DESCRIPCION_ERROR)
						|| responseErrorVO.getKey().equals(FuncionesBusinessError.MIN_SIZE_DESCRIPCION_ERROR)
						|| responseErrorVO.getKey().equals(FuncionesBusinessError.DUPLICATED_ERROR)
						|| responseErrorVO.getKey().equals(FuncionesBusinessError.NOT_FOUND_ERROR)
						) {
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


	/**
	 * Método para analizar la respuesta del servicio y transformarla a la respuesta
	 * del protocolo de salida
	 * 
	 */
	public static final ResponseEntity<ResponseVO<Boolean>> buildDeleteResponse(ResponseVO<Boolean> serviceResponse) {

		ResponseEntity<ResponseVO<Boolean>> response = null;

		if (ValidatorUtil.isSuccessfulResponse(serviceResponse)) {
			response = ResponseEntity.status(HttpStatus.NO_CONTENT).body(serviceResponse);
		} else {
			for (ResponseErrorVO responseErrorVO : serviceResponse.getErrors()) {
				if (responseErrorVO.getKey().equals(GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR)
						|| responseErrorVO.getKey().equals(FuncionesBusinessError.REQUIRED_ID_ERROR)) {
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

	

	/**
	 * Método para analizar la respuesta del servicio y transformarla a la respuesta
	 * del protocolo de salida
	 * 
	 */
	public static final ResponseEntity<ResponseVO<FindDetailProductResponseVO>> buildFindDetailResponse(
			ResponseVO<FindDetailProductResponseVO> serviceResponse) {

		ResponseEntity<ResponseVO<FindDetailProductResponseVO>> response = null;

		if (ValidatorUtil.isSuccessfulResponse(serviceResponse)) {
			response = ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
		} else {
			for (ResponseErrorVO responseErrorVO : serviceResponse.getErrors()) {
				if (responseErrorVO.getKey().equals(GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR)
						|| responseErrorVO.getKey().equals(FuncionesBusinessError.REQUIRED_ID_ERROR)) {
					response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(serviceResponse);
					break;
				} else if (responseErrorVO.getKey().equals(FuncionesBusinessError.NOT_FOUND_ERROR)) {
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


	/**
	 * Método para analizar la respuesta del servicio y transformarla a la respuesta
	 * del protocolo de salida
	 * 
	 */
	public static final ResponseEntity<ResponseVO<FindDetailFuncionResponseVO>> buildFindDetailFuncionResponse(
			ResponseVO<FindDetailFuncionResponseVO> serviceResponse) {

		ResponseEntity<ResponseVO<FindDetailFuncionResponseVO>> response = null;

		if (ValidatorUtil.isSuccessfulResponse(serviceResponse)) {
			response = ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
		} else {
			for (ResponseErrorVO responseErrorVO : serviceResponse.getErrors()) {
				if (responseErrorVO.getKey().equals(GeneralBusinessErrors.REQUIRED_PARAMETERS_ERROR)
						|| responseErrorVO.getKey().equals(FuncionesBusinessError.REQUIRED_ID_ERROR)) {
					response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(serviceResponse);
					break;
				} else if (responseErrorVO.getKey().equals(FuncionesBusinessError.NOT_FOUND_ERROR)) {
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

	/**
	 * Método para analizar la respuesta del servicio y transformarla a la respuesta
	 * del protocolo de salida
	 * 
	 */
	public static final ResponseEntity<ResponseVO<List<FindListProductResponseVO>>> buildFindListProductResponse
	(ResponseVO<List<FindListProductResponseVO>> serviceResponse) {
		
		ResponseEntity<ResponseVO<List<FindListProductResponseVO>>> response = null;
		
		if (ValidatorUtil.isSuccessfulResponse(serviceResponse)) {
			response = ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
		} else {
			for (ResponseErrorVO responseErrorVO : serviceResponse.getErrors()) {
				if (responseErrorVO.getKey().equals(GeneralBusinessErrors.REQUIRED_PAGE_NUMBER_ERROR)
						|| responseErrorVO.getKey().equals(GeneralBusinessErrors.MIN_VALUE_PAGE_NUMBER_ERROR)
						|| responseErrorVO.getKey().equals(GeneralBusinessErrors.REQUIRED_PAGE_SIZE_ERROR)
						|| responseErrorVO.getKey().equals(GeneralBusinessErrors.MIN_VALUE_PAGE_SIZE_ERROR)
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

	/**
	 * Método para analizar la respuesta del servicio y transformarla a la respuesta
	 * del protocolo de salida
	 *
	 */
	public static final ResponseEntity<ResponseVO<List<FindListProductRecipeResponseVO>>> buildFindListProductRecipeResponse
	(ResponseVO<List<FindListProductRecipeResponseVO>> serviceResponse) {

		ResponseEntity<ResponseVO<List<FindListProductRecipeResponseVO>>> response = null;

		if (ValidatorUtil.isSuccessfulResponse(serviceResponse)) {
			response = ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
		} else {
			for (ResponseErrorVO responseErrorVO : serviceResponse.getErrors()) {
				if (responseErrorVO.getKey().equals(GeneralBusinessErrors.REQUIRED_PAGE_NUMBER_ERROR)
						|| responseErrorVO.getKey().equals(GeneralBusinessErrors.MIN_VALUE_PAGE_NUMBER_ERROR)
						|| responseErrorVO.getKey().equals(GeneralBusinessErrors.REQUIRED_PAGE_SIZE_ERROR)
						|| responseErrorVO.getKey().equals(GeneralBusinessErrors.MIN_VALUE_PAGE_SIZE_ERROR)
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
