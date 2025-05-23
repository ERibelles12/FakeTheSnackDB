package de.pecus.api.vo.arqcomponentes;

public class FindListArqComponenteResponseVO {


	/**
	 * Identificador unico de registro
	 */
	private Long id;
	/**
	 * Referencia al tipo de componente
	 */
	private Long idArqTipoComponente;
	private String idNombreArqTipoComponente;
	/**
	 * Identificador alfanumerico del registro.  El identificador debe ser unico.
	 */
	private String idNombre;
	/**
	 * Descripcion general del componente
	 */
	private String descripcion;
	/**
	 * Campo flexible para especificar parametros en funcion del tipo de componente.
	 */
	private String param1;
	/**
	 * Campo flexible para especificar parametros en funcion del tipo de componente.
	 */
	private String param2;
	/**
	 * Campo flexible para especificar parametros en funcion del tipo de componente.
	 */
	private String param3;
	/**
	 * Campo flexible para especificar parametros en funcion del tipo de componente.
	 */
	private String param4;
	/**
	 * Campo flexible para especificar parametros en funcion del tipo de componente.
	 */
	private String param5;
	/**
	 * Campo flexible para especificar parametros en funcion del tipo de componente.
	 */
	private String param6;
	/**
	 * Campo flexible para especificar parametros en funcion del tipo de componente.
	 */
	private String param7;
	private Long idEstatus;
	private String idNombreEstatus;

	public FindListArqComponenteResponseVO(){

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdArqTipoComponente() {
		return idArqTipoComponente;
	}

	public void setIdArqTipoComponente(Long idArqTipoComponente) {
		this.idArqTipoComponente = idArqTipoComponente;
	}

	public String getIdNombreArqTipoComponente() {
		return idNombreArqTipoComponente;
	}

	public void setIdNombreArqTipoComponente(String idNombreArqTipoComponente) {
		this.idNombreArqTipoComponente = idNombreArqTipoComponente;
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

	public Long getIdEstatus() {
		return idEstatus;
	}

	public void setIdEstatus(Long idEstatus) {
		this.idEstatus = idEstatus;
	}

	public String getIdNombreEstatus() {
		return idNombreEstatus;
	}

	public void setIdNombreEstatus(String idNombreEstatus) {
		this.idNombreEstatus = idNombreEstatus;
	}

	
}
