package de.pecus.api.security.services.vo;

import java.io.Serializable;

public class ValidateTokenResponseVO implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -3866482080238599309L;
    
    private Boolean success;
    private String newToken;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getNewToken() {
        return newToken;
    }

    public void setNewToken(String newToken) {
        this.newToken = newToken;
    }

}
