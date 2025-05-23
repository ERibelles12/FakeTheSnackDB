package de.pecus.api.enums.arqcomponentes;

public enum ArqEstadoComponenteEnum {

	ACTIVO(1L,"Activo"), DEBUG(2L,"Debug"), INACTIVO(3L,"Inactivo");
	
	private Long id;
	private String descripcion;

	private ArqEstadoComponenteEnum(Long id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public static String descripcionId(Long id) {
        for (ArqEstadoComponenteEnum es : ArqEstadoComponenteEnum.values()) {
            if (es.id.equals(id)) {
                return es.getDescripcion();
            }
        }
     throw new IllegalArgumentException();
	}
}
