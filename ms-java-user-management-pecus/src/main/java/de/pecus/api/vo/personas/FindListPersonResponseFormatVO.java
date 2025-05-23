package de.pecus.api.vo.personas;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FindListPersonResponseFormatVO {
	
	@JsonProperty("success")
	private boolean success;
	
	@JsonProperty("data")
	private List<FindListPersonResponseVO> data;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<FindListPersonResponseVO> getData() {
		return data;
	}

	public void setData(List<FindListPersonResponseVO> data) {
		this.data = data;
	}
	
}
