/**
 * 
 */
package de.pecus.api.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author 
 *
 */
@Entity
@Table(name="BITACORA_ACCESO")
public class BitacoraAccesoDO extends Base<Long> implements Serializable {

	/**
	 * Serial Version UID de la clase
	 */
	private static final long serialVersionUID = 956069623997285241L;
	@Id
	@SequenceGenerator(name="BITACORA_ACCESO_ID_GENERATOR", sequenceName="SEQ_PK_ID_BITACORA_ACCESO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BITACORA_ACCESO_ID_GENERATOR")
	@Column(name="PK_ID")
	private Long id;
	
	@Column(name="DX_USER_ID")
	private String usuarioId;
	
	@Column(name="DX_APLICACION")
	private String aplicacion;
	
	@Column(name="DX_ID_CLIENT_INVOKE")
	private String clienteInvocacion;
	
	@Column(name="DX_CLIENT_OPERATION_CODE")
	private String clienteOperacion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DD_FECHA_ACCESO")
	private Date fechaAcceso;
	
	@Column(name="DN_ESTATUS_ACCESO")
	private Integer estatusAcceso;
	
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the usuarioId
	 */
	public String getUsuarioId() {
		return usuarioId;
	}

	/**
	 * @param usuarioId the usuarioId to set
	 */
	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

	/**
	 * @return the aplicacion
	 */
	public String getAplicacion() {
		return aplicacion;
	}

	/**
	 * @param aplicacion the aplicacion to set
	 */
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	/**
	 * @return the clienteInvocacion
	 */
	public String getClienteInvocacion() {
		return clienteInvocacion;
	}

	/**
	 * @param clienteInvocacion the clienteInvocacion to set
	 */
	public void setClienteInvocacion(String clienteInvocacion) {
		this.clienteInvocacion = clienteInvocacion;
	}

	/**
	 * @return the clienteOperacion
	 */
	public String getClienteOperacion() {
		return clienteOperacion;
	}

	/**
	 * @param clienteOperacion the clienteOperacion to set
	 */
	public void setClienteOperacion(String clienteOperacion) {
		this.clienteOperacion = clienteOperacion;
	}

	/**
	 * @return the fechaAcceso
	 */
	public Date getFechaAcceso() {
		return fechaAcceso;
	}

	/**
	 * @param fechaAcceso the fechaAcceso to set
	 */
	public void setFechaAcceso(Date fechaAcceso) {
		this.fechaAcceso = fechaAcceso;
	}

	/**
	 * @return the estatusAcceso
	 */
	public Integer getEstatusAcceso() {
		return estatusAcceso;
	}

	/**
	 * @param estatusAcceso the estatusAcceso to set
	 */
	public void setEstatusAcceso(Integer estatusAcceso) {
		this.estatusAcceso = estatusAcceso;
	}

}
