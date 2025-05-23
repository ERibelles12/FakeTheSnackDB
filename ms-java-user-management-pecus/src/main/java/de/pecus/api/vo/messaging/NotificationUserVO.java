/**
 * 
 */
package de.pecus.api.vo.messaging;

import java.io.Serializable;

/**
 * @author a18797
 *
 */
public class NotificationUserVO implements Serializable {

	/**
	 * 	Serial Version UID de la clase
	 */
	private static final long serialVersionUID = -3592580771894201997L;
	
	private Long userId;
	private String notificationToken;
	
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the notificationToken
	 */
	public String getNotificationToken() {
		return notificationToken;
	}
	/**
	 * @param notificationToken the notificationToken to set
	 */
	public void setNotificationToken(String notificationToken) {
		this.notificationToken = notificationToken;
	}

}
