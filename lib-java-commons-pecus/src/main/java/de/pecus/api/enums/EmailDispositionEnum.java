package de.pecus.api.enums;

public enum EmailDispositionEnum {
    EMAIL_INLINE("EMAIL_INLINE", "inline"), EMAIL_ATTACHMENT("EMAIL_ATTACHMENT", "attachment");

    private String idNombre;
    private String descripcion;

    private EmailDispositionEnum(String idNombre, String descripcion) {
        this.idNombre = idNombre;
        this.descripcion = descripcion;
    }

    public String getIdNombre() {
        return idNombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

}