package de.pecus.api.entities;

public enum UsuarioRolAplicacionEnum {

	PACIENTE(1L, "PACIENTE"), 
	MEDICO(2L, "MEDICO"), 
	GERENTE_SUCURSAL(3L, "GERENTE SUCURSAL"), 
	EMPLEADO(4L, "EMPLEADO"),
	;
	
	private String descripcion;
	private Long id;
	
	private UsuarioRolAplicacionEnum (Long id, String descripcion) {
		this.descripcion = descripcion;
		this.id = id;
	}
	
	public static String descripcionId(Long id) {
		for (UsuarioRolAplicacionEnum item : UsuarioRolAplicacionEnum.values()) {
			if (item.id.equals(id)) {
				return item.descripcion;
			}
		}
		throw new IllegalArgumentException();
	}
	
	public static UsuarioRolAplicacionEnum findById(Long id) {
		for (UsuarioRolAplicacionEnum obj : UsuarioRolAplicacionEnum.values()) {
			if (obj.id.equals(id)) {
				return obj;
			}
		}
		throw new IllegalArgumentException();
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Long getId() {
		return id;
	}
}
