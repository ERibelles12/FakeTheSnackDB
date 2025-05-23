package de.pecus.api.vo.personas;

public class CreatePersonaResponseVO {

	/**
	 * Identificador de la persona en APEX
	 */
	private Long patientId;
	
	private Long personOmnId;

	/**
	 * @return the patientId
	 */
	public Long getPatientId() {
		return patientId;
	}

	/**
	 * @param patientId the patientId to set
	 */
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	/**
	 * @return the personOmnId
	 */
	public Long getPersonOmnId() {
		return personOmnId;
	}

	/**
	 * @param personOmnId the personOmnId to set
	 */
	public void setPersonOmnId(Long personOmnId) {
		this.personOmnId = personOmnId;
	}
}
