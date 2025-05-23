package de.pecus.api.vo.messaging;

/**
 * Mensaje de la notificacion
 */

public class notificationSMS   {
	private String phoneNumber ;
	private String message ;

	

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



}
