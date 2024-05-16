package br.com.challenge.jadv.adoptimize.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.challenge.jadv.adoptimize.entity.Anuncio;
import br.com.challenge.jadv.adoptimize.repositorios.AnuncioRepository;
import br.com.challenge.jadv.adoptimize.servicos.dto.AnuncioRequest;
import br.com.challenge.jadv.adoptimize.servicos.dto.AnuncioResponse;

@Service
public class AnuncioService implements ServiceDTO<Anuncio, AnuncioRequest, AnuncioResponse> {
	@Autowired
    private AnuncioRepository repo;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public Collection<Anuncio> findAll() {
        return repo.findAll();
    }

    @Override
    public Collection<Anuncio> findAll(Example<Anuncio> example) {
        return repo.findAll(example);
    }

    @Override
    public Anuncio findById(Long idAnuncio) {
        return repo.findById(idAnuncio).orElse(null);
    }

    @Override
    public Anuncio save(Anuncio e) {
        return repo.save(e);
    }

    @Override
    public Anuncio toEntity(AnuncioRequest dto) {

        var usuario = usuarioService.findById(dto.usuario().id());

        return Anuncio.builder()
                .titulo(dto.titulo())
                .textoAnuncio(dto.textoAnuncio())
                .urlAnuncio(dto.urlAnuncio())
                .tipoAnuncio(dto.tipoAnuncio())
                .dataCriacao(dto.dataCriacao())
                .impressoes(dto.impressoes())
                .qtdCliques(dto.qtdCliques())
                .custoAnuncio(dto.custoAnuncio())
                .usuario(usuario)
                .build();
    }

    @Override
    public AnuncioResponse toResponse(Anuncio e) {

        var usuario = usuarioService.toResponse(e.getUsuario());

        return AnuncioResponse.builder()
                .titulo(e.getTitulo())
                .textoAnuncio(e.getTextoAnuncio())
                .urlAnuncio(e.getUrlAnuncio())
                .tipoAnuncio(e.getTipoAnuncio())
                .dataCriacao(e.getDataCriacao())
                .impressoes(e.getImpressoes())
                .qtdCliques(e.getQtdCliques())
                .custoAnuncio(e.getCustoAnuncio())
                .idAnuncio(e.getIdAnuncio())
                .usuario(usuario)
                .build();
    }
}
