package de.pecus.api.vo.roles;

import de.pecus.api.entities.MenuDO;

public class FuncionMenuVO {
	
	private Long idFuncion;
	
	private MenuDO menu;
	
	public FuncionMenuVO(Long idFuncion, MenuDO menu) {
		this.idFuncion = idFuncion;
		this.menu = menu;
	}

	public MenuDO getMenu() {
		return menu;
	}

	public void setMenu(MenuDO menu) {
		this.menu = menu;
	}

	public Long getIdFuncion() {
		return idFuncion;
	}

	public void setIdFuncion(Long idFuncion) {
		this.idFuncion = idFuncion;
	}


}
