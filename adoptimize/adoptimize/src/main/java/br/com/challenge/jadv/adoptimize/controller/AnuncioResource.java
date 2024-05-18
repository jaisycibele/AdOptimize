package br.com.challenge.jadv.adoptimize.controller;

import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.challenge.jadv.adoptimize.entity.Anuncio;
import br.com.challenge.jadv.adoptimize.entity.Usuario;
import br.com.challenge.jadv.adoptimize.services.AnuncioService;
import br.com.challenge.jadv.adoptimize.services.UsuarioService;
import br.com.challenge.jadv.adoptimize.servicos.dto.AnuncioRequest;
import br.com.challenge.jadv.adoptimize.servicos.dto.AnuncioResponse;
import br.com.challenge.jadv.adoptimize.servicos.dto.UsuarioRequest;
import br.com.challenge.jadv.adoptimize.servicos.dto.UsuarioResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/anuncio")
public class AnuncioResource implements ResourceDTO<AnuncioRequest, AnuncioResponse> {
	 	
		@Autowired
	    private AnuncioService service;

	    @Autowired
	    private UsuarioService usuarioService;



	    @GetMapping
	    public ResponseEntity<Collection<AnuncioResponse>> findAll(

	            @RequestParam(name = "titulo", required = false) String titulo,
	            @RequestParam(name = "textoAnuncio", required = false) String textoAnuncio,
	            @RequestParam(name = "urlAnuncio", required = false) String urlAnuncio,
	            @RequestParam(name = "tipoAnuncio", required = false) String tipoAnuncio,
	            @RequestParam(name = "dataCriacao", required = false) String dataCriacao,
	            @RequestParam(name = "impressoes", required = false) Long impressoes,
	            @RequestParam(name = "qtdCliques", required = false) Long qtdCliques,
	            @RequestParam(name = "custoAnuncio", required = false) Double custoAnuncio,
	            @RequestParam(name = "usuario.nome", required = false) String nome
	            
	    ) {
	        var usuario = Usuario.builder()
	                .nome( nome )
	                .build();

	        var anuncio = Anuncio.builder()
	                .titulo( titulo )
	                .textoAnuncio( textoAnuncio )
	                .urlAnuncio (urlAnuncio)
	                .tipoAnuncio(tipoAnuncio)
	                .dataCriacao(dataCriacao)
	                .impressoes(impressoes)
	                .qtdCliques(qtdCliques)
	                .custoAnuncio(custoAnuncio)
	                .usuario( usuario )
	                .build();

	        ExampleMatcher matcher = ExampleMatcher
	                .matchingAll()
	                .withMatcher( "titulo", ExampleMatcher.GenericPropertyMatchers.contains() )
	                .withMatcher( "textoAnuncio", ExampleMatcher.GenericPropertyMatchers.contains() )
	                .withMatcher( "urlAnuncio", ExampleMatcher.GenericPropertyMatchers.contains() )
	                .withMatcher( "tipoAnuncio", ExampleMatcher.GenericPropertyMatchers.contains() )
	                .withMatcher( "dataCriacao", ExampleMatcher.GenericPropertyMatchers.contains() )
	                .withMatcher( "impressoes", ExampleMatcher.GenericPropertyMatchers.contains() )
	                .withMatcher( "qtdCliques", ExampleMatcher.GenericPropertyMatchers.contains() )
	                .withMatcher( "custoAnuncio", ExampleMatcher.GenericPropertyMatchers.contains() )
	                .withMatcher( "usuario.nome", ExampleMatcher.GenericPropertyMatchers.contains() )
	                .withIgnoreNullValues()
	                .withIgnoreCase();

	        Example<Anuncio> example = Example.of(anuncio, matcher);

	        var anuncios = service.findAll(example);

	        if (Objects.isNull( anuncios ) || anuncios.isEmpty()) return ResponseEntity.notFound().build();

	        var resposta = anuncios.stream().map( service::toResponse ).toList();

	        return ResponseEntity.ok( resposta );

	    }


	    @GetMapping(value = "/{idAnuncio}")
	    @Override
	    public ResponseEntity<AnuncioResponse> findById(@PathVariable Long idAnuncio) {
	        var entity = service.findById( idAnuncio );
	        if (entity == null) return ResponseEntity.notFound().build();
	        var response = service.toResponse( entity );
	        return ResponseEntity.ok().body( response );
	    }

	    @Transactional
	    @PostMapping
	    @Override
	    public ResponseEntity<AnuncioResponse> save(@RequestBody @Valid AnuncioRequest r) {
	        var entity = service.toEntity( r );
	        entity = service.save( entity );
	        var response = service.toResponse( entity );

	        var uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
	                .path( "/{idAnuncio}" )
	                .buildAndExpand( entity.getIdAnuncio() )
	                .toUri();

	        return ResponseEntity.created( uri ).body( response );
	    }
	    
	    @PutMapping("/{idAnuncio}")
	    @Transactional
	    public ResponseEntity<AnuncioResponse> update(@PathVariable Long idAnuncio, @RequestBody @Valid AnuncioRequest request) {
	        Anuncio entity = service.findById(idAnuncio);
	        if (entity == null) {
	            return ResponseEntity.notFound().build();
	        }
	        Anuncio updatedEntity = service.update(idAnuncio, service.toEntity(request));
	        return ResponseEntity.ok(service.toResponse(updatedEntity));
	    }
	    
	    @DeleteMapping("/{idAnuncio}")
	    public ResponseEntity<Void> delete(@PathVariable Long idAnuncio) {
	        Anuncio entity = service.findById(idAnuncio);
	        if (entity == null) {
	            return ResponseEntity.notFound().build();
	        }
	        service.delete(idAnuncio);
	        return ResponseEntity.noContent().build();
	    }
}
