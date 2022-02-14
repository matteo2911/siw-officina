package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Meccanico;

public interface MeccanicoRepository extends CrudRepository<Meccanico, Long> {

	public List<Meccanico> findByNome(String nome);

	public List<Meccanico> findByNomeAndCognome(String nome, String cognome);

	public List<Meccanico> findByNomeOrCognome(String nome, String cognome);
}