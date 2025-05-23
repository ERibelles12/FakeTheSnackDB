package de.pecus.api.vo.messaging;

public class ValidateCodeResponseVO {
	
	private int intentos;
	
	private boolean verificacion;
	
	private String estadoToken;

	public int getIntentos() {
		return intentos;
	}

	public void setIntentos(int intentos) {
		this.intentos = intentos;
	}

	public boolean isVerificacion() {
		return verificacion;
	}

	public void setVerificacion(boolean verificacion) {
		this.verificacion = verificacion;
	}

	public String getEstadoToken() {
		return estadoToken;
	}

	public void setEstadoToken(String estadoToken) {
		this.estadoToken = estadoToken;
	}
	
	

}
