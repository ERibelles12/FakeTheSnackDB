package de.pecus.api.vo.usuarios;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class FindListUsuarioAplicacionResponseVO implements Serializable {

    /**
	 * Serial version UID de la clase
	 */
	private static final long serialVersionUID = -4647630949071070212L;
	private Long id;
    private String userIdEmail;
    private String clientID;
    private String secret;
    private String token;
	
    
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
	
	
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	
	
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
