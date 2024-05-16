package br.com.challenge.jadv.adoptimize.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.challenge.jadv.adoptimize.entity.Campanha;
import br.com.challenge.jadv.adoptimize.repositorios.CampanhaRepository;
import br.com.challenge.jadv.adoptimize.servicos.dto.CampanhaRequest;
import br.com.challenge.jadv.adoptimize.servicos.dto.CampanhaResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class CampanhaService implements ServiceDTO<Campanha, CampanhaRequest, CampanhaResponse> {
	   @Autowired
	   private CampanhaRepository repo;

	   @Autowired
	   private AnuncioService anuncioService;
	   
	   @PersistenceContext
	   private EntityManager entityManager;

	   @Override
	    public Collection<Campanha> findAll() {
	        return repo.findAll();
	    }

	    @Override
	    public Collection<Campanha> findAll(Example<Campanha> example) {
	        return repo.findAll(example);
	    }

	    @Override
	    public Campanha findById(Long idCampanha) {
	        return repo.findById(idCampanha).orElse(null);
	    }

	    @Override
	    public Campanha save(Campanha e) {
	        return repo.save(e);
	    }

	    @Override
	    public Campanha toEntity(CampanhaRequest dto) {

	        var anuncio = anuncioService.findById(dto.anuncio().id());

	        return Campanha.builder()
	                .nomeCampanha(dto.nomeCampanha())
	                .orcamento(dto.orcamento())
	                .dataInicio(dto.dataInicio())
	                .dataTermino(dto.dataTermino())
	                .status(dto.status())
	                .tipoCampanha(dto.tipoCampanha())
	                .anuncio(anuncio)
	                .build();
	    }

	    @Override
	    public CampanhaResponse toResponse(Campanha e) {

	        var anuncio = anuncioService.toResponse(e.getAnuncio());

	        return CampanhaResponse.builder()
	                .nomeCampanha(e.getNomeCampanha())
	                .orcamento(e.getOrcamento())
	                .dataInicio(e.getDataInicio())
	                .dataTermino(e.getDataTermino())
	                .status(e.getStatus())
	                .tipoCampanha(e.getTipoCampanha())
	                .idCampanha(e.getIdCampanha())
	                .anuncio(anuncio)
	                .build();
	    }

}
