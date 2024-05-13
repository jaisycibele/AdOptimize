package br.com.challenge.jadv.adoptimize.servicos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CampanhaRequest(
		@NotNull
	    @NotBlank
	    @Size(min = 2, max = 255)
	    String nomeCampanha,
	    @NotNull
	    @NotBlank
	    @Size(min = 2, max = 255)
	    String orcamento,
	    @NotNull
	    @NotBlank
	    @Size(min = 2, max = 255)
	    String dataInicio,
	    @NotNull
	    @NotBlank
	    @Size(min = 2, max = 255)
	    String dataTermino,
	    @NotNull
	    @NotBlank
	    @Size(min = 2, max = 255)
	    String status,
	    @NotNull
	    @NotBlank
	    @Size(min = 2, max = 255)
	    String tipoCampanha,
	    
	    @Valid
        @NotNull
        AbstractRequest anuncio
		) {

}
