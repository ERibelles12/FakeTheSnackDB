package de.pecus.api.vo.funciones;

import java.util.ArrayList;

/**
 * 
 * @author Jose_Luis_Garcia
 *
 *	Clase con los parametros de entrada a la invocacion del metodo 
 *  create de la case EventType
 *
 */
public class CreateRolFuncionRequestVO {


	private Long idAplicacion;

	private Long idFuncion;

	private Long idRol;
	
	private ArrayList<SimpleIdVO> funciones = new ArrayList<>();

	public Long getIdFuncion() {
		return idFuncion;
	}

	public void setIdFuncion(Long idFuncion) {
		this.idFuncion = idFuncion;
	}

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public Long getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(Long idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	public ArrayList<SimpleIdVO> getFunciones() {
		return funciones;
	}

	public void setFunciones(ArrayList<SimpleIdVO> funciones) {
		this.funciones = funciones;
	}	
}
