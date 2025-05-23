package de.pecus.api.vo.usuarios;

import java.io.Serializable;

public class UpdateRolUserRequestVO implements Serializable {

	/**
	 * Serial Version UID de la clase
	 */
	private static final long serialVersionUID = -8142805770804498997L;
	
	private Long userId;
	
	private String rolName;
	
	private String aplication;
	
	private Long doctorId;

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the rolName
	 */
	public String getRolName() {
		return rolName;
	}

	/**
	 * @param rolName the rolName to set
	 */
	public void setRolName(String rolName) {
		this.rolName = rolName;
	}

	public String getAplication() {
		return aplication;
	}

	public void setAplication(String aplication) {
		this.aplication = aplication;
	}

	/**
	 * @return the doctorId
	 */
	public Long getDoctorId() {
		return doctorId;
	}

	/**
	 * @param doctorId the doctorId to set
	 */
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

}
