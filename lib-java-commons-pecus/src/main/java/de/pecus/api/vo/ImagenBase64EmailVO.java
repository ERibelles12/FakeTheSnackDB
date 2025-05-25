package de.pecus.api.vo;

import de.pecus.api.enums.EmailDispositionEnum;

public class ImagenBase64EmailVO {
    
    /** Nombre de la imagen */
    private String nombreImagen;
    
    /** Cadena con la imagen en base64 */
    private String imagenBase64;
    
    /** Enum para decidir si la imagen va adjunta o inline dentro del correo */
    private EmailDispositionEnum emailDispositionEnum;
    
    /** Content-type de la imagen, por ejemplo image/jpeg */
    private String contentType;

    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

    public String getImagenBase64() {
        return imagenBase64;
    }

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

    public EmailDispositionEnum getEmailDispositionEnum() {
        return emailDispositionEnum;
    }

    public void setEmailDispositionEnum(EmailDispositionEnum emailDispositionEnum) {
        this.emailDispositionEnum = emailDispositionEnum;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
}