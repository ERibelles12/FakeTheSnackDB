package de.pecus.api.vo.personas;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(Include.NON_NULL)
public class CreateMlPersonRequestVO {

    private String birthday;
    private int cltv;
    private String email;
	private String phoneNumber;
    private int esperanzaVida;
    private int gender;
    
    @JsonProperty("idPaciente")
    private Long idPaciente;
    
    @JsonProperty("idSegmentoEdad")
    private int idSegmentoEdad;
    
    @JsonProperty("idTipoPersona")
    private int idTipoPersona;
    
    private String maternalLastName;
    private String name;
    private String paternalLastName;
    private int privacy;
    private String taxId;


	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getCltv() {
		return this.cltv;
	}

	public void setCltv(int cltv) {
		this.cltv = cltv;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEsperanzaVida() {
		return this.esperanzaVida;
	}

	public void setEsperanzaVida(int esperanzaVida) {
		this.esperanzaVida = esperanzaVida;
	}

	public int getGender() {
		return this.gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public Long getIdPaciente() {
		return this.idPaciente;
	}

	public void setIdPaciente(Long idPaciente) {
		this.idPaciente = idPaciente;
	}

	public int getIdSegmentoEdad() {
		return this.idSegmentoEdad;
	}

	public void setIdSegmentoEdad(int idSegmentoEdad) {
		this.idSegmentoEdad = idSegmentoEdad;
	}

	public int getIdTipoPersona() {
		return this.idTipoPersona;
	}

	public void setIdTipoPersona(int idTipoPersona) {
		this.idTipoPersona = idTipoPersona;
	}

	public String getMaternalLastName() {
		return this.maternalLastName;
	}

	public void setMaternalLastName(String maternalLastName) {
		this.maternalLastName = maternalLastName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPaternalLastName() {
		return this.paternalLastName;
	}

	public void setPaternalLastName(String paternalLastName) {
		this.paternalLastName = paternalLastName;
	}

	public int getPrivacy() {
		return this.privacy;
	}

	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}

	public String getTaxId() {
		return this.taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	
	
}