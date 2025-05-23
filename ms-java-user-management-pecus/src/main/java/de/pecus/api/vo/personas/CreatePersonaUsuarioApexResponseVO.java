package de.pecus.api.vo.personas;

public class CreatePersonaUsuarioApexResponseVO {
    	/**
	 * Identificador de la persona en APEX
	 */
	private Long idPaciente;
	
	private Long idPersona;

    private Long idUsuario;


    public Long getIdPaciente() {
        return this.idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Long getIdPersona() {
        return this.idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Long getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }


}
