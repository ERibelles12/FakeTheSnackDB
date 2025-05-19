package de.pecus.api.entities;

/******************** SECCION IMPORTS ***************************************/
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * La funcion determinara que capacidades del sistema pueden ser accedidas por
 * el rol
 * 
 * @author jose_
 * @version 1.0
 * @created 24-jul.-2019 11:27:45 a. m.
 */
@Entity
@Table(name = "FUNCION")
public class FuncionDO extends AuditBase<Long> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2923329749134816615L;

	@Id
	@SequenceGenerator(name="FUNCION_ID_GENERATOR", sequenceName="SEQ_PK_FUNCION_ID",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FUNCION_ID_GENERATOR")
	@Column(name="PK_ID")
    private Long id;

	@Column(name = "DX_ID_NOMBRE")
    private String idNombre;
    
	@Column(name = "DX_DESCRIPCION")
    private String descripcion;
    
	@Column(name = "DX_PATH")
    private String path;
    
	/****************************  RELACION 1..N ******************************/
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_APLICACION_ID" , referencedColumnName = "PK_ID")
	private AplicacionDO aplicacion;

    public FuncionDO() {

    }

    public AplicacionDO getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(AplicacionDO aplicacion) {
		this.aplicacion = aplicacion;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}