package de.pecus.api.entities;

/******************** SECCION IMPORTS ***************************************/
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Tipo de componente determinara el funcionamiento del mismo en su libreria de
 * invoacion. Algunos tipos:  - Aplicacion - Pantalla - Servicio - Cola de
 * mensajes
 * @author jose.ribelles
 * @version 1.0
 * @created 15-ago.-2019 05:26:48 p. m.
 */
@Entity
@Table(name = "ARQ_TIPO_COMPONENTE")
public class ArqTipoComponenteDO extends AuditBase<Long> implements Serializable {

	/**
     * Serial Version
     */
    private static final long serialVersionUID = 4368252933424520380L;
    /**
	 * Identificador unico de registro
	 */
	@Id
	@SequenceGenerator(name="ARQ_TPO_C_ID_GENERATOR", sequenceName="ARQ_TIPO_COMPONENTE_PK_ID_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ARQ_TPO_C_ID_GENERATOR")
	@Column(name="PK_ID")
	private Long id;
	/**
	 * Identificador alfanumerico
	 */
	@Column(name =  "DX_ID_NOMBRE" )
	private String idNombre;
	@Column(name =  "DX_DESCRIPCION" )
	private String descripcion;

	public ArqTipoComponenteDO(Long id){
		super();
		this.id = id;
	}

	public ArqTipoComponenteDO(){

	}
	
	
	public void finalize() throws Throwable {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}