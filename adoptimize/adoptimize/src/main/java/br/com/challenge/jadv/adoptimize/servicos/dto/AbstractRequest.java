package br.com.challenge.jadv.adoptimize.servicos.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AbstractRequest(
		@NotNull
	    @Positive
	    Long id
		) {
	
}
