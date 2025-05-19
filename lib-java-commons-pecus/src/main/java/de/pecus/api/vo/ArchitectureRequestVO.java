package de.pecus.api.vo;

public class ArchitectureRequestVO<T> extends RequestVO<T> {

    private Long userId;
    private Long arqComponenteId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArqComponenteId() {
        return arqComponenteId;
    }

    public void setArqComponenteId(Long arqComponenteId) {
        this.arqComponenteId = arqComponenteId;
    }

}