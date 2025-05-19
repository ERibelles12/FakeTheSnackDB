package de.pecus.api.entities;

public enum RolEnum {
	
	ADMINISTRADOR(1L, "ADMINISTRADOR"),
	CONTADOR_MASTER(2L, "CONTADOR MASTER"),
	CONTADOR(3L, "CONTADOR"),
	SEGURIDAD(4L, "SEGURIDAD"),
	AUXILIAR_ADMINISTRATIVO(5L, "AUXILIAR ADMINISTRATIVO"),
	MANTENIMIENTO(6L, "MANTENIMIENTO"),
	LIMPIEZA(7L, "LIMPIEZA"),
	COMITE_VIGILANCIA(8L, "COMITÉ VIGILANCIA"),
	RESIDENTE(9L, "RESIDENTE"),
	INQUILINO(10L, "INQUILINO"),
	PUBLICO(11L, "PUBLICO"),
	ADMINISTRADOR_BACK_PECUS(12L, "ADMINISTRADOR BACK PECUS"),
	OPERACIONES(13L, "OPERACIONES"),
	TECNOLOGIA(14L, "TECNOLOGIA"),
	ADMINISTRADOR_DE_CUENTA(15L, "ADMINISTRADOR DE CUENTA"),
	USUARIO_ALEXA(16L, "USUARIO ALEXA"),
	SEGURIDAD_MASTER(17L, "SEGURIDAD MASTER"),
	PROPIETARIO(18L, "PROPIETARIO"),
	PROPIETARIO_CON_INQ(19L, "PROPIETARIO CON INQ"),
	CONTACTO_ADICIONAL(20L, "CONTACTO ADICIONAL"),
	ADMINISTRADOR_TARJETAS_ACCESO(21L, "ADMINISTRADOR_TARJETAS_ACCESO");

	private final Long id;
    private final String descripcion;

    private RolEnum(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Long getId() {
        return id;
    }

    public static String descripcionId(Long id) {
        for (RolEnum tc : RolEnum.values()) {
            if (tc.id.equals(id)) {
                return tc.getDescripcion();
            }
        }
        throw new IllegalArgumentException();
    }

    public static RolEnum findById(Long id) {
        for (RolEnum obj : RolEnum.values()) {
            if (obj.id.equals(id)) {
                return obj;
            }
        }
        throw new IllegalArgumentException();
    }
	
}
