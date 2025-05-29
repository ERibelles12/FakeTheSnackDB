package de.pecus.api.vo.category;


/**
 * 
 * @author Jose_Luis_Garcia
 *
 *	Clase con los parametros de entrada a la invocacion del metodo 
 *  create de la case EventType
 *
 */
public class FindDetailCategoryResponseVO {


	// Identificador de registro
	private Long id;

	// Identificador alfanumerico
	private String name;

	// indicador de categoria general
	private Boolean	generalIndicator;

	// indicador de categoria de leche
	private Boolean milkIndicator;

	// indicador de categoria de carne
	private boolean meatIndicator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


}

