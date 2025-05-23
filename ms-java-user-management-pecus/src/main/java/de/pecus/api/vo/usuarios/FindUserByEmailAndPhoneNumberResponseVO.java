/**
 * 
 */
package de.pecus.api.vo.usuarios;

/**
 * @author Luis Enrique Sanchez Santiago
 *
 */
public class FindUserByEmailAndPhoneNumberResponseVO {

    /** Identificador del usuario **/
    private Long idUsuario;
    /** Identificador del medico **/
    private Long idMedico;
    /** Identificador del paciente **/
    private Long idPaciente;
    /** Correo del usuario **/
    private String email;
    /** Numero de telefono **/
    private String phoneNumber;
    /** Token para notificaciones push **/
    private String tokenFirebase;

    /**
     * @return the idUsuario
     */
    public Long getIdUsuario() {
        return idUsuario;
    }
    
    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    /**
	 * @return the idMedico
	 */
	public Long getIdMedico() {
		return idMedico;
	}
	
	/**
	 * @param idMedico the idMedico to set
	 */
	public void setIdMedico(Long idMedico) {
		this.idMedico = idMedico;
	}
	
	/**
	 * @return the idPaciente
	 */
	public Long getIdPaciente() {
		return idPaciente;
	}
	
	/**
	 * @param idPaciente the idPaciente to set
	 */
	public void setIdPaciente(Long idPaciente) {
		this.idPaciente = idPaciente;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the tokenFirebase
	 */
	public String getTokenFirebase() {
		return tokenFirebase;
	}

	/**
	 * @param tokenFirebase the tokenFirebase to set
	 */
	public void setTokenFirebase(String tokenFirebase) {
		this.tokenFirebase = tokenFirebase;
	}

}
