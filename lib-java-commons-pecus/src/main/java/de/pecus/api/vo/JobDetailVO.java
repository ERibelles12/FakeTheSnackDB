package de.pecus.api.vo;

public class JobDetailVO {
	
	private String jobName;
	private Boolean status;
	
	public JobDetailVO() {
		super();
	}
	
	
	public JobDetailVO(Boolean status) {
		super();
		this.status = status;
	}
	
	
	public JobDetailVO(String jobName, Boolean status) {
		super();
		this.jobName = jobName;
		this.status = status;
	}
	
	
	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	

}
