package de.pecus.api.vo.usuarios;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class FindListUsuarioFirebaseResponseVO implements Serializable {

    /**
	 * Serial version UID de la clase
	 */
	private static final long serialVersionUID = -4647630949071070212L;
	private Long id;
    private String userIdEmail;
    private String userIdMobileNumber;
    private String firebaseId;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserIdEmail() {
		return userIdEmail;
	}
	public void setUserIdEmail(String userIdEmail) {
		this.userIdEmail = userIdEmail;
	}
	public String getUserIdMobileNumber() {
		return userIdMobileNumber;
	}
	public void setUserIdMobileNumber(String userIdMobileNumber) {
		this.userIdMobileNumber = userIdMobileNumber;
	}
	public String getFirebaseId() {
		return firebaseId;
	}
	public void setFirebaseId(String firebaseId) {
		this.firebaseId = firebaseId;
	}
    
    

}
