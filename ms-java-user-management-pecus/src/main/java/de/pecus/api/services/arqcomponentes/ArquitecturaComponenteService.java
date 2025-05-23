package de.pecus.api.services.arqcomponentes;

import java.util.List;

import de.pecus.api.vo.RequestVO;
import de.pecus.api.vo.ResponseVO;
import de.pecus.api.vo.arqcomponentes.FindListArqByTypeComponenteRequestVO;
import de.pecus.api.vo.arqcomponentes.FindListArqComponenteResponseVO;

public interface ArquitecturaComponenteService {

    /**
	 * Consulta la base de datos en base a varios parametros
	 * 
	 * @return ReponseVO con los datos encontrados
	 * 
	 * @param request Objeto con parametros de entrada de ArquitecturaComponente
	 */
	ResponseVO<List<FindListArqComponenteResponseVO>> findListByType(RequestVO<FindListArqByTypeComponenteRequestVO> request);
}
