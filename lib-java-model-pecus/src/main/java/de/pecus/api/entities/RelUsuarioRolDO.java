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
 * Relacion de los roles de los usuarios * 
 * @author NTT DATA
 * @version 1.0
 * @created 15-Ago-2022 11:00:00 a. m.
 */
@Entity
@Table(name = "REL_USUARIO_ROL")
public class RelUsuarioRolDO extends AuditBase<Long> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7135642411942463673L;

	@Id
	@SequenceGenerator(name="ROL_USUARIO_ID_GENERATOR", sequenceName = "REL_USUARIO_ROL_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ROL_USUARIO_ID_GENERATOR")
	@Column(name="PK_ID")
	private Long id;
	
	/****************************  RELACION 1..N ******************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_USER_ID", referencedColumnName = "PK_ID")
	private UsuarioDO usuario;
	
	/****************************  RELACION 1..N ******************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ROL_ID", referencedColumnName = "PK_ID")
	private RolDO rol;
	
	@Column(name="DD_FECHA_INICIO")
	private Date fechaInicio;
	
	@Column(name="DD_FECHA_FIN")
	private Date fechaFin;
	
	public RelUsuarioRolDO() {
		
	}

	public void finalize() throws Throwable {

	}
	
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

	public RolDO getRol() {
		return rol;
	}

	public void setRol(RolDO rol) {
		this.rol = rol;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
}