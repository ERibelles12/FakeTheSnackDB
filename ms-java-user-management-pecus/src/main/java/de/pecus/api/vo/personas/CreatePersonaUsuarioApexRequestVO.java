package de.pecus.api.vo.personas;

public class CreatePersonaUsuarioApexRequestVO {

    private Long idPaciente;

    private String email;

    private String telefono;


    public Long getIdPaciente() {
        return this.idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    

}
