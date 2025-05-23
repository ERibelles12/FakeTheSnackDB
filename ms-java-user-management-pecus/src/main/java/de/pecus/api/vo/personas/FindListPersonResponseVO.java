package de.pecus.api.vo.personas;

public class FindListPersonResponseVO {
	
	private long id;
	
	private String name;
	
	private String paternalLastName;
	
	private String maternalLastName;
	
	private String birthday;
	
	private String email;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPaternalLastName() {
		return paternalLastName;
	}

	public void setPaternalLastName(String paternalLastName) {
		this.paternalLastName = paternalLastName;
	}

	public String getMaternalLastName() {
		return maternalLastName;
	}

	public void setMaternalLastName(String maternalLastName) {
		this.maternalLastName = maternalLastName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
