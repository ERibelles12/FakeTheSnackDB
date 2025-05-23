package de.pecus.api.vo.messaging;

/**
 * Mensaje de la notificacion
 */

public class notificationSMSresponseVO   {
	private String messageId ;
	private String successful ;
	private String exception ;
	
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getSuccessful() {
		return successful;
	}
	public void setSuccessful(String successful) {
		this.successful = successful;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}



}
