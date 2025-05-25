package de.pecus.api.vo;

public class EmailVO {

    private static final String SPACE_SEPARATOR = " ";

    /**
     * Nombre del emisor que se mostr\u00e1 al receptor del email.
     */
    private String nombre;

    /**
     * Email emisor que se mostr\u00e1 al receptor del email.
     */
    private String email;

    /**
     * Si esta bandera se encuentra activa, el correo sale como copia invisible
     * 
     * NOTA: Se requiere que por lo menos un correo NO sea BCC
     */
    private Boolean bcc;

    public EmailVO(String nombre, String appPat, String appMat, String email) {
        StringBuilder sb = new StringBuilder();
        this.nombre = sb.append(nombre).append(SPACE_SEPARATOR).append(appPat).append(SPACE_SEPARATOR).append(appMat)
                .toString();
        this.email = email;
    }

    public EmailVO(String email) {
        this.email = email;
    }

    public EmailVO() {
    }

    /**
     * @return the name
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the name to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     * @return si esta activa la bandera de bcc
     */
    public Boolean getBcc() {
        return bcc;
    }

    /**
     * @param bcc si debe ser una copia invisible
     */
    public void setBcc(Boolean bcc) {
        this.bcc = bcc;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        EmailVO other = (EmailVO) obj;
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals(other.email)) {
            return false;
        }
        return true;
    }
    
    
}
