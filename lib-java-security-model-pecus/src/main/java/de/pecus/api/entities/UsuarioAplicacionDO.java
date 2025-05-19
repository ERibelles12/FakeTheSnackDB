package de.pecus.api.entities;


/******************** SECCION IMPORTS ***************************************/
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/******************** FIN SECCION IMPORTS ************************************/

/**
 * Usuario podria ser el mail o el telefono.
 * 
 * 
 * Con el telefono podriamos empatar con la agenda de contactos del usuario para
 * que al seleccionar algun usuario supieramos si la otra persona tiene PECUS o
 * no.
 * 
 * @author jose_
 * @version 1.0
 * @created 24-jul.-2019 11:27:46 a. m.
 */
@Entity

@Table(name = "USUARIO_APLICACION")
public class UsuarioAplicacionDO extends AuditBase<Long> implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 3736839829404032486L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PK_ID_USUARIO_APLICACION")
    @SequenceGenerator(name = "SEQ_PK_ID_USUARIO_APLICACION", sequenceName = "SEQ_PK_ID_USUARIO_APLICACION", allocationSize = 1)
    @Column(name="PK_ID")
    private Long id;
    @Column(name = "DX_USER_ID_EMAIL")
    private String userIdEmail;
    @Column(name = "DX_PASSWORD")
    private String password;
    @Column(name = "DD_LAST_LOGIN")
    private Date lastLogin;
    @Column(name = "DD_REFRESH_TOKEN_DATE")
    private Date refreshTokenExpeditionTime;
    @Column(name = "DX_REFRESH_PREVIOUS_TOKEN")
    private String refreshPreviousToken;
    @Column(name = "DX_REFRESH_TOKEN_TOKEN")
    private String refreshTokenToken;
    @Column(name = "DX_SECRET")
    private String secret;
    @Column(name = "DX_CLIENT_ID")
    private String clientID;
    
    public UsuarioAplicacionDO() {

    }

    public void finalize() throws Throwable {

    }

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getRefreshTokenExpeditionTime() {
		return refreshTokenExpeditionTime;
	}

	public void setRefreshTokenExpeditionTime(Date refreshTokenExpeditionTime) {
		this.refreshTokenExpeditionTime = refreshTokenExpeditionTime;
	}

	public String getRefreshPreviousToken() {
		return refreshPreviousToken;
	}

	public void setRefreshPreviousToken(String refreshPreviousToken) {
		this.refreshPreviousToken = refreshPreviousToken;
	}

	public String getRefreshTokenToken() {
		return refreshTokenToken;
	}

	public void setRefreshTokenToken(String refreshTokenToken) {
		this.refreshTokenToken = refreshTokenToken;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	
	

}