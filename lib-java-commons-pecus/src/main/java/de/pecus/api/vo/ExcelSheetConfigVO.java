package de.pecus.api.vo;

import java.util.List;

public class ExcelSheetConfigVO {
	
	private String sheetName;
	
	private Integer initRowData;
	
	private Integer initColData;
	
	private List<String>  fieldNames;
	
	private List<?> data;

	/**
	 * @return the sheetName
	 */
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * @param sheetName the sheetName to set
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	/**
	 * @return the initRowData
	 */
	public Integer getInitRowData() {
		return initRowData;
	}

	/**
	 * @param initRowData the initRowData to set
	 */
	public void setInitRowData(Integer initRowData) {
		this.initRowData = initRowData;
	}

	/**
	 * @return the data
	 */
	public List<?> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<?> data) {
		this.data = data;
	}

	/**
	 * @return the fieldNames
	 */
	public List<String> getFieldNames() {
		return fieldNames;
	}

	/**
	 * @param fieldNames the fieldNames to set
	 */
	public void setFieldNames(List<String> fieldNames) {
		this.fieldNames = fieldNames;
	}

	/**
	 * @return the initColData
	 */
	public Integer getInitColData() {
		return initColData;
	}

	/**
	 * @param initColData the initColData to set
	 */
	public void setInitColData(Integer initColData) {
		this.initColData = initColData;
	} 
	
}