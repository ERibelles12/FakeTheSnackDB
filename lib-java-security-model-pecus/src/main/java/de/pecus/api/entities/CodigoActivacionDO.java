package de.pecus.api.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the CODIGO_ACTIVACION database table.
 * 
 */
@Entity
@Table(name="CODIGO_ACTIVACION")
@NamedQuery(name="CodigoActivacionDO.findAll", query="SELECT c FROM CodigoActivacionDO c")
public class CodigoActivacionDO extends AuditBase<Long> {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CODIGO_ACTIVACION_ID_GENERATOR", sequenceName="SEQ_PK_ID_COD_ACT", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CODIGO_ACTIVACION_ID_GENERATOR")
	@Column(name="PK_ID")
	private Long id;
	
	@Column(name="DX_USER_ID")
	private String userId;

	@Column(name="DX_CODIGO_ACTIVACION")
	private String codigoActivacion;

	@Column(name="DN_ESTATUS_CODIGO_ACTIVACION")
	private Integer estatusCodigoActivacion;

	@Column(name="DN_INTENTOS_CODIGO_ACTIVACION")
	private Integer intentosCodigoActivacion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DD_FECHA_CREACION_CODIGO_ACTIV")
	private Date fechaCreacionCodAct;



	public CodigoActivacionDO() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaCreacionCodAct() {
		return this.fechaCreacionCodAct;
	}

	public void setFechaCreacionCodAct(Date fechaCreacionCodAct) {
		this.fechaCreacionCodAct = fechaCreacionCodAct;
	}

	public String getCodigoActivacion() {
		return this.codigoActivacion;
	}

	public void setCodigoActivacion(String codigoActivacion) {
		this.codigoActivacion = codigoActivacion;
	}

	public Integer getEstatusCodigoActivacion() {
		return this.estatusCodigoActivacion;
	}

	public void setEstatusCodigoActivacion(Integer estatusCodigoActivacion) {
		this.estatusCodigoActivacion = estatusCodigoActivacion;
	}

	public Integer getIntentosCodigoActivacion() {
		return this.intentosCodigoActivacion;
	}

	public void setIntentosCodigoActivacion(Integer intentosCodigoActivacion) {
		this.intentosCodigoActivacion = intentosCodigoActivacion;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}



	
	

}