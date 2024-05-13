package br.com.challenge.jadv.adoptimize.servicos.dto;

import lombok.Builder;

@Builder
public record UsuarioResponse(
		Long idUsuario,
        String nome,
        String email,
        String senha,
        String chaveGoogleAds
		
		) {

}
