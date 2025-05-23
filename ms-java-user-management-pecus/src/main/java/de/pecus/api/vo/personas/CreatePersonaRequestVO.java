package de.pecus.api.vo.personas;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/******************** FIN SECCION IMPORTS ************************************/

/**
 * Entidad que tiene los datos de la persona.
 * @author jose_
 * @version 1.0
 * @created 08-ago.-2019 11:04:23 a. m.
 */
@JsonInclude(Include.NON_NULL)
public class CreatePersonaRequestVO {

	private String name;
	private String paternalLastName;
	private String maternalLastName;
	private String birthday;
	private String gender;
	private List<ContactInfoVO> contactInfo;
	private Long idPerson; 
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the paternalLastName
	 */
	public String getPaternalLastName() {
		return paternalLastName;
	}
	/**
	 * @param paternalLastName the paternalLastName to set
	 */
	public void setPaternalLastName(String paternalLastName) {
		this.paternalLastName = paternalLastName;
	}
	/**
	 * @return the maternalLastName
	 */
	public String getMaternalLastName() {
		return maternalLastName;
	}
	/**
	 * @param maternalLastName the maternalLastName to set
	 */
	public void setMaternalLastName(String maternalLastName) {
		this.maternalLastName = maternalLastName;
	}
	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the contactInfo
	 */
	public List<ContactInfoVO> getContactInfo() {
		return contactInfo;
	}
	/**
	 * @param contactInfo the contactInfo to set
	 */
	public void setContactInfo(List<ContactInfoVO> contactInfo) {
		this.contactInfo = contactInfo;
	}
	/**
	 * @return IdPerson
	 */
	public Long getIdPerson() {
		return idPerson;
	}
	/**
	 * @param idPerson
	 */
	public void setIdPerson(Long idPerson) {
		this.idPerson = idPerson;
	}
	
}