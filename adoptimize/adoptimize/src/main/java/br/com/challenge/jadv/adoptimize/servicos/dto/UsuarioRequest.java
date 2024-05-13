package br.com.challenge.jadv.adoptimize.servicos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
		
		@NotNull
	    @NotBlank
	    @Size(min = 2, max = 255)
	    String nome,
	    @NotNull
	    @NotBlank
	    @Size(min = 2, max = 255)
	    String email,
	    @NotNull
	    @NotBlank
	    @Size(min = 2, max = 255)
	    String senha,
	    @NotNull
	    @NotBlank
	    @Size(min = 2, max = 255)
	    String chaveGoogleAds
		
		) {

	
}
