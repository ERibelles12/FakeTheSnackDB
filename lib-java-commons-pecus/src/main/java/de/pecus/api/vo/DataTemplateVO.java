package de.pecus.api.vo;

import java.util.List;
import java.util.Map;

public class DataTemplateVO {
	
	private List<Object> listData;
	private Map<String, String> parmsReport;
	
	public DataTemplateVO() {
		super();
	}
	
	
	public DataTemplateVO(List<Object> listData, Map<String, String> parmsReport) {
		super();
		this.listData = listData;
		this.parmsReport = parmsReport;
	}
	/**
	 * @return the listData
	 */
	public List<Object> getListData() {
		return listData;
	}
	/**
	 * @param listData the listData to set
	 */
	public void setListData(List<Object> listData) {
		this.listData = listData;
	}
	/**
	 * @return the parmsReport
	 */
	public Map<String, String> getParmsReport() {
		return parmsReport;
	}
	/**
	 * @param parmsReport the parmsReport to set
	 */
	public void setParmsReport(Map<String, String> parmsReport) {
		this.parmsReport = parmsReport;
	}
	
}
