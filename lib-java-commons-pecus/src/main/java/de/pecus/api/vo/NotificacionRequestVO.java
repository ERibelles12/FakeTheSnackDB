package de.pecus.api.vo;

import java.util.List;
import java.util.Map;

public class NotificacionRequestVO {

    /** Lista de usuarios para enviar la notificacion */
    private List<Long> usuarios;

    /** Lista de correos para enviar notificaciones por email */
    private List<EmailVO> emailList;

    /** Bandera para saber si cada mail debe salir en un request diferente */
    private Boolean multipleMailRequests;

    /** Lista de telefonos para enviar notificaciones por SMS */
    private List<String> telefonoList;

    /** Lista de tokens para enviar push notificacions */
    private List<String> tokenPushList;

    /** Lista de topicos para enviar la notificacion */
    private List<String> topicos;

    /** IdNombre de la Notificacion que se debe enviar */
    private String idNombreNotificacion;
    
    /** Mapa del arreglo de datos para Firebase */
    private Map<String, String> pushNotificationsData;

    /** Mapa de parametros que seran reemplazados en la plantilla */
    private Map<String, String> parametrosPlantillaMap;

    /** Lista de imagenes basadas en el componente de gestor de imagenes */
    private List<ImagenEmailVO> imagenList;

    /** Lista de imagenes en formato base64 */
    private List<ImagenBase64EmailVO> imagenBase64List;
    
    
    /** Lista de imagenes en formato base64 */
    private List<FileBase64EmailVO> fileBase64List;
    

    public List<Long> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Long> usuarios) {
        this.usuarios = usuarios;
    }

    public List<EmailVO> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<EmailVO> emailList) {
        this.emailList = emailList;
    }

    public Boolean getMultipleMailRequests() {
        return multipleMailRequests;
    }

    public void setMultipleMailRequests(Boolean multipleMailRequests) {
        this.multipleMailRequests = multipleMailRequests;
    }

    public List<String> getTelefonoList() {
        return telefonoList;
    }

    public void setTelefonoList(List<String> telefonoList) {
        this.telefonoList = telefonoList;
    }

    public List<String> getTokenPushList() {
        return tokenPushList;
    }

    public void setTokenPushList(List<String> tokenPushList) {
        this.tokenPushList = tokenPushList;
    }

    public List<String> getTopicos() {
        return topicos;
    }

    public void setTopicos(List<String> topicos) {
        this.topicos = topicos;
    }

    public String getIdNombreNotificacion() {
        return idNombreNotificacion;
    }

    public void setIdNombreNotificacion(String tipoNotificacionVO) {
        this.idNombreNotificacion = tipoNotificacionVO;
    }

    public Map<String, String> getPushNotificationsData() {
        return pushNotificationsData;
    }

    public void setPushNotificationsData(Map<String, String> pushNotificationsData) {
        this.pushNotificationsData = pushNotificationsData;
    }

    public Map<String, String> getParametrosPlantillaMap() {
        return parametrosPlantillaMap;
    }

    public void setParametrosPlantillaMap(Map<String, String> parametrosPlantillaMap) {
        this.parametrosPlantillaMap = parametrosPlantillaMap;
    }

    public List<ImagenEmailVO> getImagenList() {
        return imagenList;
    }

    public void setImagenList(List<ImagenEmailVO> imagenList) {
        this.imagenList = imagenList;
    }

    public List<ImagenBase64EmailVO> getImagenBase64List() {
        return imagenBase64List;
    }

    public void setImagenBase64List(List<ImagenBase64EmailVO> imagenBase64List) {
        this.imagenBase64List = imagenBase64List;
    }

	/**
	 * @return the fileBase64List
	 */
	public List<FileBase64EmailVO> getFileBase64List() {
		return fileBase64List;
	}

	/**
	 * @param fileBase64List the fileBase64List to set
	 */
	public void setFileBase64List(List<FileBase64EmailVO> fileBase64List) {
		this.fileBase64List = fileBase64List;
	}

}