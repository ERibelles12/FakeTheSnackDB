package de.pecus.api.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ScheduleVO {
	
	
	private String jobName;
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm") 
	private Date jobScheduleTime;
	
	private String cronExpression;

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
	 * @return the jobScheduleTime
	 */
	public Date getJobScheduleTime() {
		return jobScheduleTime;
	}

	/**
	 * @param jobScheduleTime the jobScheduleTime to set
	 */
	public void setJobScheduleTime(Date jobScheduleTime) {
		this.jobScheduleTime = jobScheduleTime;
	}

	/**
	 * @return the cronExpression
	 */
	public String getCronExpression() {
		return cronExpression;
	}

	/**
	 * @param cronExpression the cronExpression to set
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
	

}
