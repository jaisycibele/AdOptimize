package br.com.challenge.jadv.adoptimize.controller;

import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.challenge.jadv.adoptimize.entity.Anuncio;
import br.com.challenge.jadv.adoptimize.entity.Campanha;
import br.com.challenge.jadv.adoptimize.services.AnuncioService;
import br.com.challenge.jadv.adoptimize.services.CampanhaService;
import br.com.challenge.jadv.adoptimize.servicos.dto.CampanhaRequest;
import br.com.challenge.jadv.adoptimize.servicos.dto.CampanhaResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/campanha")
public class CampanhaResource implements ResourceDTO<CampanhaRequest, CampanhaResponse>{

	@Autowired
    private CampanhaService service;

    @Autowired
    private AnuncioService anuncioService;



    @GetMapping
    public ResponseEntity<Collection<CampanhaResponse>> findAll(

            @RequestParam(name = "nomeCampanha", required = false) String nomeCampanha,
            @RequestParam(name = "orcamento", required = false) String orcamento,
            @RequestParam(name = "dataInicio", required = false) String dataInicio,
            @RequestParam(name = "dataTermino", required = false) String dataTermino,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "tipoCampanha", required = false) String tipoCampanha,
            @RequestParam(name = "anuncio.titulo", required = false) String titulo
            
    ) {
        var anuncio = Anuncio.builder()
                .titulo( titulo )
                .build();

        var campanha = Campanha.builder()
                .nomeCampanha(nomeCampanha)
                .orcamento(orcamento)
                .dataInicio (dataInicio)
                .dataTermino(dataTermino)
                .status(status)
                .tipoCampanha(tipoCampanha)
                .anuncio(anuncio)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withMatcher( "nomeCampanha", ExampleMatcher.GenericPropertyMatchers.contains() )
                .withMatcher( "orcamento", ExampleMatcher.GenericPropertyMatchers.contains() )
                .withMatcher( "dataInicio", ExampleMatcher.GenericPropertyMatchers.contains() )
                .withMatcher( "dataTermino", ExampleMatcher.GenericPropertyMatchers.contains() )
                .withMatcher( "status", ExampleMatcher.GenericPropertyMatchers.contains() )
                .withMatcher( "tipoCampanha", ExampleMatcher.GenericPropertyMatchers.contains() )
                .withMatcher( "anuncio.titulo", ExampleMatcher.GenericPropertyMatchers.contains() )
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Campanha> example = Example.of(campanha, matcher);

        var campanhas = service.findAll(example);

        if (Objects.isNull( campanhas ) || campanhas.isEmpty()) return ResponseEntity.notFound().build();

        var resposta = campanhas.stream().map( service::toResponse ).toList();

        return ResponseEntity.ok( resposta );

    }


    @GetMapping(value = "/{idCampanha}")
    @Override
    public ResponseEntity<CampanhaResponse> findById(@PathVariable Long idCampanha) {
        var entity = service.findById( idCampanha );
        if (entity == null) return ResponseEntity.notFound().build();
        var response = service.toResponse( entity );
        return ResponseEntity.ok().body( response );
    }

    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<CampanhaResponse> save(@RequestBody @Valid CampanhaRequest r) {
        var entity = service.toEntity( r );
        entity = service.save( entity );
        var response = service.toResponse( entity );

        var uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path( "/{idCampanha}" )
                .buildAndExpand( entity.getIdCampanha() )
                .toUri();

        return ResponseEntity.created( uri ).body( response );
    }
}
