package de.pecus.api.vo;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Objeto utilizado para obtener las peticiones para envio de archivos, este
 * objeto nos permite inicilizar los elementos que requiere el SmartLogger
 * 
 * @author Juan Carlos Contreras Vazquez
 */
public class MultipartRequestVO {

    /** Headers que se reciben en una peticion de tipo multipart */
    private MultipartHttpServletRequest multipartHttpServletRequest;

    /** Arreglo de archivos que se están recibiendo */
    private MultipartFile[] multipartFileArray;

    public MultipartHttpServletRequest getMultipartHttpServletRequest() {
        return multipartHttpServletRequest;
    }

    public void setMultipartHttpServletRequest(MultipartHttpServletRequest multipartHttpServletRequest) {
        this.multipartHttpServletRequest = multipartHttpServletRequest;
    }

    public MultipartFile[] getMultipartFileArray() {
        return multipartFileArray;
    }

    public void setMultipartFileArray(MultipartFile[] multipartFileArray) {
        this.multipartFileArray = multipartFileArray;
    }

}