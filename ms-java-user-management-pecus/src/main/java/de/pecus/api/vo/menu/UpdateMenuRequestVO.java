package de.pecus.api.vo.menu;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import de.pecus.api.entities.AplicacionDO;

/**
 * 
 * @author Jose_Luis_Garcia
 *
 *         Clase con los parametros de entrada a la invocacion del metodo update
 *         de la case IdType
 *
 */
public class UpdateMenuRequestVO {
	
		// id
		private Long id;
		
		// Identificador alfanumerico
		private String idNombre;

		private String descripcion;
		
		private String path;
		
		private String imagePath;
		
		private Long orden;
		
		private Double pasoConfig;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getIdNombre() {
			return idNombre;
		}

		public void setIdNombre(String idNombre) {
			this.idNombre = idNombre;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getImagePath() {
			return imagePath;
		}

		public void setImagePath(String imagePath) {
			this.imagePath = imagePath;
		}

		public Long getOrden() {
			return orden;
		}

		public void setOrden(Long orden) {
			this.orden = orden;
		}

		public Double getPasoConfig() {
			return pasoConfig;
		}

		public void setPasoConfig(Double pasoConfig) {
			this.pasoConfig = pasoConfig;
		}

		
}
