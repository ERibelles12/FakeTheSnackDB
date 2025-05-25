package de.pecus.api.vo;

import de.pecus.api.enums.EmailDispositionEnum;

/**
 * VO que sirve para obtener cualquier imagen de cualquier tipo del gestor de
 * imagenes
 * 
 * @author Juan Carlos Contreras
 */
public class ImagenEmailVO {

    /**
     * Id que tiene el registro en la propia entidad de negocio
     */
    private Long idComponente;

    /**
     * Id del catálogo TIPO_COMPONENTE con las entidades de negocio
     */
    private Long idTipoComponente;

    /**
     * idNombre del catálogo TIPO_IMAGEN
     */
    private String nombreTipoImagen;

    /**
     * Puede ser [EMAIL_INLINE | EMAIL_ATTACHMENT] dependiendo si va a estar
     * embebido o como adjunto
     */
    private EmailDispositionEnum emailDispositionEnum;

    public Long getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Long idComponente) {
        this.idComponente = idComponente;
    }

    public Long getIdTipoComponente() {
        return idTipoComponente;
    }

    public void setIdTipoComponente(Long idTipoComponente) {
        this.idTipoComponente = idTipoComponente;
    }

    public String getNombreTipoImagen() {
        return nombreTipoImagen;
    }

    public void setNombreTipoImagen(String nombreTipoImagen) {
        this.nombreTipoImagen = nombreTipoImagen;
    }

    public EmailDispositionEnum getEmailDispositionEnum() {
        return emailDispositionEnum;
    }

    public void setEmailDispositionEnum(EmailDispositionEnum emailDispositionEnum) {
        this.emailDispositionEnum = emailDispositionEnum;
    }

}