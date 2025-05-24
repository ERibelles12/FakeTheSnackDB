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
/******************** FIN SECCION IMPORTS ************************************/

/**
 * @author Krisna
 * @version 1.0
 * @created 08-ago.-2019 03:32:27 p. m.
 */
@Entity
@Table(name = "APLICACION")
public class AplicacionDO extends AuditBase<Long> implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1274345277763896245L;
	
	@Id
	@SequenceGenerator(name="APLICACION_ID_GENERATOR", sequenceName="SEQ_PK_APLICACION_ID",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="APLICACION_ID_GENERATOR")
	@Column(name="PK_ID")
	private Long id;

	@Column(name =  "DX_ID_NOMBRE" )
	private String idNombre;

	@Column(name =  "DX_TIPO_AUTENTICACION" )
	private String tipoAutenticacion;

	public AplicacionDO(){

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

	public String getTipoAutenticacion() {
		return this.tipoAutenticacion;
	}

	public void setTipoAutenticacion(String tipoAutenticacion) {
		this.tipoAutenticacion = tipoAutenticacion;
	}

}