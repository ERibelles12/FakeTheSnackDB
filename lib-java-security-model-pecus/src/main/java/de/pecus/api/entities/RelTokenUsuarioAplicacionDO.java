package de.pecus.api.entities;

/******************** SECCION IMPORTS ***************************************/
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "REL_TOKEN_USUARIO_APLICACION")
public class RelTokenUsuarioAplicacionDO implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 3736839829404032486L;

	@Id
	@SequenceGenerator(name="TOKEN_USR_APP_ID_GENERATOR", sequenceName="SEQ_PK_TOKEN_USER_APP_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TOKEN_USR_APP_ID_GENERATOR")
	@Column(name="PK_ID")
	private Long id;
    
    @ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_USUARIO_ID" , referencedColumnName = "PK_ID")
    private UsuarioDO usuario;
    
    @Column(name = "DX_TOKEN")
    private String token;
    
    @Column(name = "DD_TOKEN_EXPIRATION_DATE")
    private Date tokenExpirationDate;
    
    @Column(name = "DX_REFRESH_TOKEN")
    private String refreshToken;
    
    
    @ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_APLICACION_ID" , referencedColumnName = "PK_ID")
    private AplicacionDO aplicacion;
    
    @Column(name = "DN_ACTIVO")
    private Boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UsuarioDO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDO usuario) {
		this.usuario = usuario;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenExpirationDate() {
		return tokenExpirationDate;
	}

	public void setTokenExpirationDate(Date tokenExpirationDate) {
		this.tokenExpirationDate = tokenExpirationDate;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public AplicacionDO getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(AplicacionDO aplicacion) {
		this.aplicacion = aplicacion;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}