/**
 * 
 */
package de.pecus.api.vo.usuarios;

import java.io.Serializable;

/**
 * @author ejerzain
 *
 */
public class UpdateUsuarioAplicacionRequestVO implements Serializable {

	/**
	 * Serial version UID de la clase
	 */
	private static final long serialVersionUID = 531925153985191492L;
	
	private Long id;
    private String password;
    private String clientID;
    private String secret;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
  
    
    
}
