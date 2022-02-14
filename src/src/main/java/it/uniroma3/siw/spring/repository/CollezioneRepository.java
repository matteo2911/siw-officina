package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Collezione;

public interface CollezioneRepository extends CrudRepository<Collezione,Long>{
	
	public List<Collezione> findByNome(String nome);

	public List<Collezione> findByNomeAndDescrizione(String nome, String descrizione);

	public List<Collezione> findByNomeOrDescrizione(String nome, String descrizione);

}
