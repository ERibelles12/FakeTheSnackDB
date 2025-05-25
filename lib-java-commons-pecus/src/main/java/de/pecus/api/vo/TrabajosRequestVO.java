package de.pecus.api.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TrabajosRequestVO {
	
	private Long   id;
	private String idNombre;
	private String descripcion;
	private String cliente;
	private Long   estado;
	private Date   fechaInicioProcesamiento;
	private Date   fechaFinProcesamiento;
	private Long   idArqComponente;
	private String cronExpression;
	private Long  regProcesados;
	private Long  regOk;
	private Long  regError;
	private List<List<String>> errores;
	
	private Boolean isSucces;
	
	
	public TrabajosRequestVO() {
		super();
	}
	
	public TrabajosRequestVO(Long id) {
		super();
		this.id = id;
	}
	
	
	public TrabajosRequestVO(Long id, String idNombre, String descripcion, String cliente, Long estado,
			Date fechaInicioProcesamiento, Date fechaFinProcesamiento) {
		super();
		this.id = id;
		this.idNombre = idNombre;
		this.descripcion = descripcion;
		this.cliente = cliente;
		this.estado = estado;
		this.fechaInicioProcesamiento = fechaInicioProcesamiento;
		this.fechaFinProcesamiento = fechaFinProcesamiento;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the idNombre
	 */
	public String getIdNombre() {
		return idNombre;
	}
	/**
	 * @param idNombre the idNombre to set
	 */
	public void setIdNombre(String idNombre) {
		this.idNombre = idNombre;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the cliente
	 */
	public String getCliente() {
		return cliente;
	}
	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	/**
	 * @return the estado
	 */
	public Long getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Long estado) {
		this.estado = estado;
	}
	/**
	 * @return the fechaInicioProcesamiento
	 */
	public Date getFechaInicioProcesamiento() {
		return fechaInicioProcesamiento;
	}
	/**
	 * @param fechaInicioProcesamiento the fechaInicioProcesamiento to set
	 */
	public void setFechaInicioProcesamiento(Date fechaInicioProcesamiento) {
		this.fechaInicioProcesamiento = fechaInicioProcesamiento;
	}
	/**
	 * @return the fechaFinProcesamiento
	 */
	public Date getFechaFinProcesamiento() {
		return fechaFinProcesamiento;
	}
	/**
	 * @param fechaFinProcesamiento the fechaFinProcesamiento to set
	 */
	public void setFechaFinProcesamiento(Date fechaFinProcesamiento) {
		this.fechaFinProcesamiento = fechaFinProcesamiento;
	}

	/**
	 * @return the idArqComponente
	 */
	public Long getIdArqComponente() {
		return idArqComponente;
	}

	/**
	 * @param idArqComponente the idArqComponente to set
	 */
	public void setIdArqComponente(Long idArqComponente) {
		this.idArqComponente = idArqComponente;
	}

	/**
	 * @return the regProcesados
	 */
	public Long getRegProcesados() {
		return regProcesados;
	}

	/**
	 * @param regProcesados the regProcesados to set
	 */
	public void setRegProcesados(Long regProcesados) {
		this.regProcesados = regProcesados;
	}

	/**
	 * @return the regOk
	 */
	public Long getRegOk() {
		return regOk;
	}

	/**
	 * @param regOk the regOk to set
	 */
	public void setRegOk(Long regOk) {
		this.regOk = regOk;
	}

	/**
	 * @return the regError
	 */
	public Long getRegError() {
		return regError;
	}

	/**
	 * @param regError the regError to set
	 */
	public void setRegError(Long regError) {
		this.regError = regError;
	}

	/**
	 * @return the errores
	 */
	public List<List<String>> getErrores() {
		return errores;
	}

	/**
	 * @param errores the errores to set
	 */
	public void setErrores(List<List<String>> errores) {
		this.errores = errores;
	}

	/**
	 * @return the isSucces
	 */
	public Boolean getIsSucces() {
		return isSucces;
	}

	/**
	 * @param isSucces the isSucces to set
	 */
	public void setIsSucces(Boolean isSucces) {
		this.isSucces = isSucces;
	}

	/**
	 * @return the cronExpression
	 */
	public String getCronExpression() {
		return cronExpression;
	}

	/**
	 * @param cronExpression the cronExpression to set
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

}
