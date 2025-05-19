package de.pecus.api.entities;

/******************** SECCION IMPORTS ***************************************/
import java.io.Serializable;

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
 * Esta entidad es el diccionario de componentes de la plataforma. En esta entidad
 * se gestionan los datos de los componentes y abstraen los comportamiento de los
 * mismos.  Algunos componentes son:  - Pantallas - Colas - Impresoras  Los campos
 * parametros permiten establecer valores que las funciones de arquitectura pueden
 * utilizar para la invocacion y comportamiento del componente.
 * @author jose.ribelles
 * @version 1.0
 * @created 15-ago.-2019 05:26:48 p. m.
 */
@Entity
@Table(name = "ARQ_COMPONENTE")
public class ArqComponenteDO extends AuditBase<Long> implements Serializable {

	/**
     * Serial Version
     */
    private static final long serialVersionUID = -5027484090555041067L;

    /**
	 * Identificador unico de registro
	 */
	@Id
	@SequenceGenerator(name="ARQ_COMPONENTE_ID_GENERATOR", sequenceName="ARQ_COMPONENTE_PK_ID_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ARQ_COMPONENTE_ID_GENERATOR")
	@Column(name="PK_ID")
	private Long id;
	/**
	 * Referencia al tipo de componente
	 */
	 
	/****************************  RELACION 1..N ******************************/
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ARQ_TIPO_COMPONENTE_ID" , referencedColumnName = "PK_ID")
	private ArqTipoComponenteDO arqTipoComponente;
	/**
	 * Identificador alfanumerico del registro.  El identificador debe ser unico.
	 */
	@Column(name =  "DX_ID_NOMBRE" )
	private String idNombre;
	/**
	 * Descripcion general del componente
	 */
	@Column(name =  "DX_DESCRIPCION" )
	private String descripcion;
	/**
	 * Campo flexible para especificar parametros en funcion del tipo de componente.
	 */
	@Column(name =  "DX_PARAM1" )
	private String param1;
	/**
	 * Campo flexible para especificar parametros en funcion del tipo de componente.
	 */
	@Column(name =  "DX_PARAM2" )
	private String param2;
	/**
	 * Campo flexible para especificar parametros en funcion del tipo de componente.
	 */
	@Column(name =  "DX_PARAM3" )
	private String param3;
	/**
	 * Campo flexible para especificar parametros en funcion del tipo de componente.
	 */
	@Column(name =  "DX_PARAM4" )
	private String param4;
	/**
	 * Campo flexible para especificar parametros en funcion del tipo de componente.
	 */
	@Column(name =  "DX_PARAM5" )
	private String param5;
	/**
	 * Campo flexible para especificar parametros en funcion del tipo de componente.
	 */
	@Column(name =  "DX_PARAM6" )
	private String param6;
	/**
	 * Campo flexible para especificar parametros en funcion del tipo de componente.
	 */
	@Column(name =  "DX_PARAM7" )
	private String param7;
	/**
	 * Indicador de estado Activo, Debug, Inactivo
	 */
	@Column(name =  "DN_ESTADO" )
	private Long estado;

	public ArqComponenteDO(Long id){
		this.id = id;

	}
	
	public ArqComponenteDO(){

	}

	public void finalize() throws Throwable {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArqTipoComponenteDO getArqTipoComponente() {
		return arqTipoComponente;
	}

	public void setArqTipoComponente(ArqTipoComponenteDO arqTipoComponente) {
		this.arqTipoComponente = arqTipoComponente;
	}

	public String getIdNombre() {
		return idNombre;
	}

	public void setIdNombre(String idNombre) {
		this.idNombre = idNombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public String getParam4() {
		return param4;
	}

	public void setParam4(String param4) {
		this.param4 = param4;
	}

	public String getParam5() {
		return param5;
	}

	public void setParam5(String param5) {
		this.param5 = param5;
	}

	public String getParam6() {
		return param6;
	}

	public void setParam6(String param6) {
		this.param6 = param6;
	}

	public String getParam7() {
		return param7;
	}

	public void setParam7(String param7) {
		this.param7 = param7;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

}