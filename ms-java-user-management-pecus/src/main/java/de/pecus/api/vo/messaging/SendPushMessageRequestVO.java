package de.pecus.api.vo.messaging;

import java.io.Serializable;
import java.util.List;

public class SendPushMessageRequestVO implements Serializable {

	/**
	 * Serial version UID de la clase
	 */
	private static final long serialVersionUID = 5963353281191747100L;
	
	/**
	 * Lista de usuarios a los cuales se realizara la notificacion
	 */
	private List<NotificationUserVO> notificationUserVOs;
	
	/**
	 * Estructura del mensaje a enviar
	 */
	private Message message;

	/**
	 * @return the notificationUserVOs
	 */
	public List<NotificationUserVO> getNotificationUserVOs() {
		return notificationUserVOs;
	}

	/**
	 * @param notificationUserVOs the notificationUserVOs to set
	 */
	public void setNotificationUserVOs(List<NotificationUserVO> notificationUserVOs) {
		this.notificationUserVOs = notificationUserVOs;
	}

	/**
	 * @return the message
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(Message message) {
		this.message = message;
	}
}
