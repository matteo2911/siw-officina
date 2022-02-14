package it.uniroma3.siw.spring.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Meccanico;
import it.uniroma3.siw.spring.model.Prenotazione;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {

	public List<Prenotazione> findById(String idPrenotazione);
	
	public List<Prenotazione> findByDataPrenotazioneAndOraPrenotazione(Date dataPrenotazione, int oraPrenotazione);
	
	//public List<Prenotazione> findByDataAndOraInizioAndOraFineAndMaestro(LocalDate data, int oraInizio, int oraFine, Maestro m);
}