package de.pecus.api.vo.pacientes;

import java.util.List;

public class PatientDataResponseDO {

    private String name;
    private String paternalLastName;
    private String maternalLastName;
    private String birthday;
    private String gender;
    private List<PatientDataContactInfoResponseDO> contactInfo;
    private Long idPerson;


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

    public String getMaternalLastName() {
        return this.maternalLastName;
    }

    public void setMaternalLastName(String maternalLastName) {
        this.maternalLastName = maternalLastName;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<PatientDataContactInfoResponseDO> getContactInfo() {
        return this.contactInfo;
    }

    public void setContactInfo(List<PatientDataContactInfoResponseDO> contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Long getIdPerson() {
        return this.idPerson;
    }

    public void setIdPerson(Long idPerson) {
        this.idPerson = idPerson;
    }

    // to string function that returns the object as a string
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name: ").append(name)
          .append(", paternalLastName: ").append(paternalLastName)
          .append(", maternalLastName: ").append(maternalLastName)
          .append(", birthday: ").append(birthday)
          .append(", gender: ").append(gender)
          .append(", idPerson: ").append(idPerson);
        // iterate over contact list
        if (contactInfo != null) {
            sb.append(", contactInfo:");
            for (PatientDataContactInfoResponseDO contact : contactInfo) {
                sb.append(contact.getType()).append(":"+contact.getValue()+", ");
            }
            sb.append("]");
        }
        return sb.toString();
    }

}
