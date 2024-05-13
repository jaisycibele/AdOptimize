package br.com.challenge.jadv.adoptimize.servicos.dto;

import lombok.Builder;

@Builder
public record CampanhaResponse(
		Long idCampanha,
		String nomeCampanha,
		String orcamento,
		String dataInicio,
		String dataTermino,
		String status,
		String tipoCampanha,
		
		AnuncioResponse anuncio
		) {
	
	
}
