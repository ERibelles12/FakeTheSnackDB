package de.pecus.api.vo.pacientes;

import java.util.List;

public class ResponseDO {
    private Boolean success;
    private List<ErrorDO> errors;
    private PatientDataResponseDO data;

    public Boolean isSuccess() {
        return this.success;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<ErrorDO> getErrors() {
        return this.errors;
    }

    public void setErrors(List<ErrorDO> errors) {
        this.errors = errors;
    }

    public PatientDataResponseDO getData() {
        return this.data;
    }

    public void setData(PatientDataResponseDO data) {
        this.data = data;
    }


}

