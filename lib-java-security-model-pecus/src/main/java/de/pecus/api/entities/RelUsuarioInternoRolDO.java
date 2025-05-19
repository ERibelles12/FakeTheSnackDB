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
@Table(name = "REL_USUARIO_INTERNO_ROL")
public class RelUsuarioInternoRolDO extends AuditBase<Long> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7135642411942463673L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PK_ID_REL_USUARIO_INTERNO_ROL")
	@SequenceGenerator(name = "SEQ_PK_ID_REL_USUARIO_INTERNO_ROL", sequenceName = "SEQ_PK_ID_REL_USUARIO_INTERNO_ROL", allocationSize = 1)
	@Column(name = "PK_ID")
	private Long id;

	/****************************  RELACION 1..N ******************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_USUARIO_INTERNO_ID", referencedColumnName = "PK_ID")
	private UsuarioInternoDO usuario;
	
	/****************************  RELACION 1..N ******************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ROL_ID", referencedColumnName = "PK_ID")
	private RolDO rol;
	
	@Column(name="DD_FECHA_INICIO")
	private Date fechaInicio;
	
	@Column(name="DD_FECHA_FIN")
	private Date fechaFin;
	
	public RelUsuarioInternoRolDO() {
		
	}

	public void finalize() throws Throwable {

	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UsuarioInternoDO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioInternoDO usuario) {
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