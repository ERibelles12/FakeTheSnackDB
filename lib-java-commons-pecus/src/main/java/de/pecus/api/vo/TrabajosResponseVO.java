package de.pecus.api.vo;

import java.util.Date;


public class TrabajosResponseVO {
	

	private Long   id;
	private String idNombre;
	private String descripcion;
	private String cliente;
	private String estatus;
	private Date   fechaInicioProcesamiento;
	private Date   fechaFinProcesamiento;
	private Long   idArqComponente;
	
	private Long   totalReg;
	private Long   regOk;
	private Long   regError;
	
	public TrabajosResponseVO(Long id, String idNombre, String descripcion, String cliente, String estatus,
			Date fechaInicioProcesamiento, Date fechaFinProcesamiento, Long idArqComponente, 
			Long  totalReg, Long regOk, Long regError) {
		super();
		this.id = id;
		this.idNombre = idNombre;
		this.descripcion = descripcion;
		this.cliente = cliente;
		this.estatus = estatus;
		this.fechaInicioProcesamiento = fechaInicioProcesamiento;
		this.fechaFinProcesamiento = fechaFinProcesamiento;
		this.idArqComponente = idArqComponente;
		this.totalReg = totalReg;
		this.regOk = regOk;
		this.regError = regError;
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
	 * @return the estatus
	 */
	public String getEstatus() {
		return estatus;
	}

	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
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

	public TrabajosResponseVO() {
		super();
	}
	
	public TrabajosResponseVO(Long id) {
		super();
		this.id = id;
	}

	/**
	 * @return the totalReg
	 */
	public Long getTotalReg() {
		return totalReg;
	}

	/**
	 * @param totalReg the totalReg to set
	 */
	public void setTotalReg(Long totalReg) {
		this.totalReg = totalReg;
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
	

}
