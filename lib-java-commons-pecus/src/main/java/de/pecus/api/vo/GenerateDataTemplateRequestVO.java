package de.pecus.api.vo;

import java.util.List;
import java.util.Map;

public class GenerateDataTemplateRequestVO {
	
	private String templateBase64;
	
	private List<ExcelSheetConfigVO> sheetConfigList;
	
	private Map<String, Object> params;
	
	/**
	 * @return the templateBase64
	 */
	public String getTemplateBase64() {
		return templateBase64;
	}

	/**
	 * @param templateBase64 the templateBase64 to set
	 */
	public void setTemplateBase64(String templateBase64) {
		this.templateBase64 = templateBase64;
	}

	/**
	 * @return the sheetConfigList
	 */
	public List<ExcelSheetConfigVO> getSheetConfigList() {
		return sheetConfigList;
	}

	/**
	 * @param sheetConfigList the sheetConfigList to set
	 */
	public void setSheetConfigList(List<ExcelSheetConfigVO> sheetConfigList) {
		this.sheetConfigList = sheetConfigList;
	}

	/**
	 * @return the params
	 */
	public Map<String, Object> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
}
