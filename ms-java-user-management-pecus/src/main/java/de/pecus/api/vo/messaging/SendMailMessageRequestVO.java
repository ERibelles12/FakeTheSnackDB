package de.pecus.api.vo.messaging;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import de.pecus.api.vo.EmailVO;
import de.pecus.api.vo.FileBase64EmailVO;
import de.pecus.api.vo.ImagenBase64EmailVO;
import de.pecus.api.vo.ImagenEmailVO;

@JsonInclude(Include.NON_NULL)
public class SendMailMessageRequestVO   {
	/**
	 * Email emisor que se mostr\u00e1 al receptor del email.
	 */
	private EmailVO fromEmail;
	
	/**
	 * Asunto del email a enviar.
	 */
	private String subject;
	
	/**
	 * Listado de emails a los que se enviar\u00e1 el email
	 */
	private List<EmailVO> to;
	
	/** 
	 * Bandera para saber si cada mail debe salir en un request diferente
	 */
    private boolean multipleMailRequests;
	
	/**
	 * Contenido con el mensaje a enviar en el email. 
	 */
	private String content;

	/**
	 * Contiene los 2 listados de imagenes: Attachment - Inline
	 */
//	private ImagenListEmailVO imagenesList;
	
	/**
	 * Lista de imagenes en formato base64
	 * */
    private List<ImagenBase64EmailVO> imagenBase64List;
    
    /**
	 * Lista de archivos en formato base64
	 * */
   private List<FileBase64EmailVO> filesBase64List;
   
   /** IdNombre de la Notificacion que se debe enviar */
   private String idNombreNotificacion;
   
   /** Mapa de parametros que seran reemplazados en la plantilla */
   private Map<String, String> parametrosPlantillaMap;
   
   /** Lista de imagenes basadas en el componente de gestor de imagenes */
   private List<ImagenEmailVO> imagenList;
   
	/**
	 * @return the fromEmail
	 */
	public EmailVO getFromEmail() {
		return fromEmail;
	}

	/**
	 * @param fromEmail the fromEmail to set
	 */
	public void setFromEmail(EmailVO fromEmail) {
		this.fromEmail = fromEmail;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the to
	 */
	public List<EmailVO> getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(List<EmailVO> to) {
		this.to = to;
	}
	
	/**
	 * @return obtiene la bandera de multiples peticiones
	 */
	public boolean isMultipleMailRequests() {
        return multipleMailRequests;
    }

	/**
	 * @param multipleMailRequests bandera para saber si se deben enviar los correos en multiples peticiones
	 */
    public void setMultipleMailRequests(boolean multipleMailRequests) {
        this.multipleMailRequests = multipleMailRequests;
    }

    /**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the imagenes
	 */
//	public ImagenListEmailVO getImagenesList() {
//		return imagenesList;
//	}

	/**
	 * @param imagenesList the imagenes to set
	 */
//	public void setImagenesList(ImagenListEmailVO imagenesList) {
//		this.imagenesList = imagenesList;
//	}

	/**
	 * @return lista de imagenes en formato base64
	 */
    public List<ImagenBase64EmailVO> getImagenBase64List() {
        return imagenBase64List;
    }

    /** Asigna la lista de imagenes en formato base64 */
    public void setImagenBase64List(List<ImagenBase64EmailVO> imagenBase64List) {
        this.imagenBase64List = imagenBase64List;
    }

	/**
	 * @return the filesBase64List
	 */
	public List<FileBase64EmailVO> getFilesBase64List() {
		return filesBase64List;
	}

	/**
	 * @param filesBase64List the filesBase64List to set
	 */
	public void setFilesBase64List(List<FileBase64EmailVO> filesBase64List) {
		this.filesBase64List = filesBase64List;
	}

	public String getIdNombreNotificacion() {
		return idNombreNotificacion;
	}

	public void setIdNombreNotificacion(String idNombreNotificacion) {
		this.idNombreNotificacion = idNombreNotificacion;
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
	
	
}
