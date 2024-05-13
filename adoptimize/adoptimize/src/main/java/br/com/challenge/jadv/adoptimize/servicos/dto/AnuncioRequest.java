package br.com.challenge.jadv.adoptimize.servicos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AnuncioRequest(
		@NotNull
	    @NotBlank
	    @Size(min = 2, max = 255)
	    String titulo,
	    @NotNull
	    @NotBlank
	    @Size(min = 2, max = 255)
	    String textoAnuncio,
	    @NotNull
	    @NotBlank
	    @Size(min = 2, max = 255)
	    String urlAnuncio,
	    @NotNull
	    @NotBlank
	    @Size(min = 2, max = 255)
	    String tipoAnuncio,
	    @NotNull
	    @NotBlank
	    @Size(min = 2, max = 255)
	    String dataCriacao,
	    @Positive
	    @NotNull
	    Long impressoes,
	    @Positive
	    @NotNull
	    Long qtdCliques,
	    @Positive
	    @NotNull
	    Double custoAnuncio,
	    
	    @Valid
        @NotNull
        AbstractRequest usuario
		
		) {

}
