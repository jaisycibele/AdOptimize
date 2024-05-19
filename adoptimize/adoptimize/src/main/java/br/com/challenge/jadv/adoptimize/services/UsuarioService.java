package br.com.challenge.jadv.adoptimize.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.challenge.jadv.adoptimize.entity.Usuario;
import br.com.challenge.jadv.adoptimize.repositorios.UsuarioRepository;
import br.com.challenge.jadv.adoptimize.servicos.dto.UsuarioRequest;
import br.com.challenge.jadv.adoptimize.servicos.dto.UsuarioResponse;

@Service
public class UsuarioService implements ServiceDTO<Usuario, UsuarioRequest, UsuarioResponse> {

	 @Autowired
	    private UsuarioRepository repo;
	 
	 @Override
	    public Collection<Usuario> findAll() {
	        return repo.findAll();
	    }

	    @Override
	    public Collection<Usuario> findAll(Example<Usuario> example) {
	        return repo.findAll(example);
	    }

	    @Override
	    public Usuario findById(Long id) {
	        return repo.findById(id).orElse(null);
	    }

	    @Override
	    public Usuario save(Usuario e) {
	        return repo.save(e);
	    }

	    @Override
	    public Usuario toEntity(UsuarioRequest dto) {
	        return Usuario.builder()
	                .nome(dto.nome())
	                .email(dto.email())
	                .senha(dto.senha())
	                .chaveGoogleAds(dto.chaveGoogleAds())
	                .build();
	    }

	    @Override
	    public UsuarioResponse toResponse(Usuario e) {

	        return UsuarioResponse.builder()
	                .idUsuario(e.getIdUsuario())
	                .nome(e.getNome())
	                .email(e.getEmail())
	                .senha(e.getSenha())
	                .chaveGoogleAds(e.getChaveGoogleAds())
	                .build();

	    }
	    
	    public Usuario update(Long idUsuario, Usuario usuario) {
	        Optional<Usuario> usuarioOptional = repo.findById(idUsuario);
	        if (usuarioOptional.isPresent()) {
	            Usuario usuarioUpdate = usuarioOptional.get();
	            usuarioUpdate.setNome(usuario.getNome());
	            usuarioUpdate.setEmail(usuario.getEmail());
	            usuarioUpdate.setSenha(usuario.getSenha());
	            usuario = repo.save(usuarioUpdate);
	            return usuario;
	        }
	        return null;
	    }
	    
	    public void delete(Long idUsuario) {
	        repo.deleteById(idUsuario);
	    }

	    

}
