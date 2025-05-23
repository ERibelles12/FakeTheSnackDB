package de.pecus.api.vo.usuarios;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * VO para roles del usuario
 */
@JsonInclude(Include.NON_NULL)
public class RolVO {

	
	private Long id;
    private String idNombre;
    private String descripcion;
    
    
	private TipoRolVO tipoRolVO;
    private List<FuncionVO> funcionList;
	
	/**
	 * Default constructor of the RoleVO
	 */
	public RolVO() {
		// Metodo vacio por default
	}
	
	public RolVO(Long id, String idNombre, String descripcion) {
		this.id = id;
		this.idNombre = idNombre;
		this.descripcion = descripcion;
	}
	
	

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return el nombre clave de la pagina
	 */
	public String getIdNombre() {
		return idNombre;
	}
	
	/**
	 * @param el nombre clave de la pagina
	 */
	public void setIdNombre(String idNombre) {
		this.idNombre = idNombre;
	}
	
	/**
	 * @return el nombre de la pagina
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/** 
	 * @param nombre de la pagina
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
    /**
     * @return the TipoRolDO
     */
    public TipoRolVO getTipoRolVO() {
        return tipoRolVO;
    }

    /**
     * @param TipoRolVO the TipoRolDO to set
     */
    public void setTipoRolVO(TipoRolVO tipoRolVO) {
        this.tipoRolVO = tipoRolVO;
    }

	/**
	 * @return the funcionList
	 */
	public List<FuncionVO> getFuncionList() {
		return funcionList;
	}

	/**
	 * @param funcionList the funcionList to set
	 */
	public void setFuncionList(List<FuncionVO> funcionList) {
		this.funcionList = funcionList;
	}
    
}