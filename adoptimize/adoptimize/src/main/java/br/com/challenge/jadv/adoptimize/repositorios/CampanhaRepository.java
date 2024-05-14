package br.com.challenge.jadv.adoptimize.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.challenge.jadv.adoptimize.entity.Campanha;

@Repository
public interface CampanhaRepository extends JpaRepository<Campanha, Long> {

}
