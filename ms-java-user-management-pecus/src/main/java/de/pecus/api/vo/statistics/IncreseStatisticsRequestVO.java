package de.pecus.api.vo.statistics;

public class IncreseStatisticsRequestVO {

    private Integer idTipoComponente;
    private Integer idComponente;
    private String idNombre;
    private String fecha;
    private Integer valor;
    private String datos;


    public Integer getIdTipoComponente() {
        return this.idTipoComponente;
    }

    public void setIdTipoComponente(Integer idTipoComponente) {
        this.idTipoComponente = idTipoComponente;
    }

    public Integer getIdComponente() {
        return this.idComponente;
    }

    public void setIdComponente(Integer idComponente) {
        this.idComponente = idComponente;
    }

    public String getIdNombre() {
        return this.idNombre;
    }

    public void setIdNombre(String idNombre) {
        this.idNombre = idNombre;
    }

    //date need to be in this format: yyyy-MM-dd
    public String getFecha() {
        return this.fecha;
    }

    //date need to be in this format: yyyy-MM-dd
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getValor() {
        return this.valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getDatos() {
        return this.datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

}
