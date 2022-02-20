package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.TipologiaIntervento;

public interface TipologiaInterventoRepository extends CrudRepository<TipologiaIntervento, Long> {

	public List<TipologiaIntervento> findByNome(String nome);

	public List<TipologiaIntervento> findByNomeOrCodice(String nome, String codice);
	
	public TipologiaIntervento findById(int id);

	

}