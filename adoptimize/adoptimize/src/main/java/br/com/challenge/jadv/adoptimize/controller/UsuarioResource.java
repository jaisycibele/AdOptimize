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

import br.com.challenge.jadv.adoptimize.entity.Usuario;
import br.com.challenge.jadv.adoptimize.services.UsuarioService;
import br.com.challenge.jadv.adoptimize.servicos.dto.UsuarioRequest;
import br.com.challenge.jadv.adoptimize.servicos.dto.UsuarioResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioResource implements ResourceDTO<UsuarioRequest, UsuarioResponse>  {

	 	@Autowired
	    private UsuarioService service;


	    @GetMapping
	    public ResponseEntity<Collection<UsuarioResponse>> findAll(

	            @RequestParam(name = "nome", required = false) String nome,
	            @RequestParam(name = "email", required = false) String email,
	            @RequestParam(name = "senha", required = false) String senha,
	            @RequestParam(name = "chaveGoogleAds", required = false) String chaveGoogleAds
	            
	    ) {

	        var usuario = Usuario.builder()
	                .nome( nome )
	                .email(email)
	                .senha(senha)
	                .chaveGoogleAds(chaveGoogleAds)
	                .build();

	        ExampleMatcher matcher = ExampleMatcher
	                .matchingAll()
	                .withMatcher( "nome", ExampleMatcher.GenericPropertyMatchers.contains() )
	                .withMatcher( "email", ExampleMatcher.GenericPropertyMatchers.contains() )
	                .withMatcher( "senha", ExampleMatcher.GenericPropertyMatchers.contains() )
	                .withMatcher( "chaveGoogleAds", ExampleMatcher.GenericPropertyMatchers.contains() )
	                .withIgnoreNullValues()
	                .withIgnoreCase();

	        Example<Usuario> example = Example.of( usuario, matcher );

	        Collection<Usuario> usuarios = service.findAll( example );

	        if (Objects.isNull( usuarios ) || usuarios.isEmpty()) return ResponseEntity.notFound().build();

	        var resposta = usuarios.stream().map( service::toResponse ).toList();

	        return ResponseEntity.ok( resposta );}

	    


	    @GetMapping(value = "/{id}")
	    @Override
	    public ResponseEntity<UsuarioResponse> findById(@PathVariable Long idUsuario) {
	        var entity = service.findById( idUsuario );
	        if (Objects.isNull( entity )) return ResponseEntity.notFound().build();
	        var response = service.toResponse( entity );
	        return ResponseEntity.ok( response );
	    }

	    @Override
	    @PostMapping
	    @Transactional
	    public ResponseEntity<UsuarioResponse> save(@RequestBody @Valid UsuarioRequest r) {
	        var entity = service.toEntity( r );
	        var saved = service.save( entity );
	        var response = service.toResponse( saved );
	        var uri = ServletUriComponentsBuilder
	                .fromCurrentRequestUri()
	                .path( "/{idUsuario}" )
	                .buildAndExpand( saved.getIdUsuario() ).toUri();
	        return ResponseEntity.created( uri ).body( response );

	    }
	    
	    @PutMapping("/{id}")
	    public ResponseEntity<UsuarioResponse> update(@PathVariable Long id, @RequestBody @Valid UsuarioRequest request) {
	        Usuario entity = service.findById(id);
	        if (entity == null) {
	            return ResponseEntity.notFound().build();
	        }
	        Usuario updatedEntity = service.update(id, service.toEntity(request));
	        return ResponseEntity.ok(service.toResponse(updatedEntity));
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> delete(@PathVariable Long id) {
	        Usuario entity = service.findById(id);
	        if (entity == null) {
	            return ResponseEntity.notFound().build();
	        }
	        service.delete(id);
	        return ResponseEntity.noContent().build();
	    }
}
