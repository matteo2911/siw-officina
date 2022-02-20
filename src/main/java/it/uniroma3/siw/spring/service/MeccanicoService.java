package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Intervento;
import it.uniroma3.siw.spring.model.Meccanico;
import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.repository.MeccanicoRepository;



@Service
public class MeccanicoService {
	
	
	@Autowired 
	private CredentialsService credentialsService;
	
	@Autowired
	private MeccanicoRepository MeccanicoRepository; 
	
	@Autowired
	private InterventoService interventoService;
	
	@Autowired
	private PrenotazioneService prenotazioneService;
	
	@Transactional
	public Meccanico inserisci(Meccanico meccanico) {
		return MeccanicoRepository.save(meccanico);
	}
	
	@Transactional
	public List<Meccanico> meccaniciPerNomeAndCognome(String nome, String cognome) {
		return MeccanicoRepository.findByNomeAndCognome(nome, cognome);
	}

	@Transactional
	public List<Meccanico> tutti() {
		return (List<Meccanico>) MeccanicoRepository.findAll();
	}

	@Transactional
	public Meccanico meccanicoPerId(Long id) {
		Optional<Meccanico> optional = MeccanicoRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Meccanico meccanico) {
		List<Meccanico> meccanici = this.MeccanicoRepository.findByNomeAndCognome(meccanico.getNome(), meccanico.getCognome());
		if (meccanici.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public void eliminaMeccanico(Meccanico m) {
		MeccanicoRepository.delete(m);
}

	public CredentialsService getCredentialsService() {
		return credentialsService;
	}

	public void setCredentialsService(CredentialsService credentialsService) {
		this.credentialsService = credentialsService;
	}
	
	@Transactional
	public List<Intervento> getInterventiMeccanico(Meccanico m){
		
		
		List<Intervento> lista = new ArrayList<>();
		
		for(Intervento i: interventoService.tutti()) {
			if(i.getMeccanico()== m)
				lista.add(i);
		}
		return lista;
	}
	
	public InterventoService getInterventoService() {
		return interventoService;
	}
	
	@Transactional
	public Boolean isMeccanicoLibero(Prenotazione p) {
		Meccanico m= p.getIntervento().getMeccanico();
		Boolean libero= true;
		
			for(Intervento i:m.getInterventi()) {
				
				for(Prenotazione o: i.getPrenotazioni()) {
					
					if(o.getDataPrenotazione()==p.getDataPrenotazione() && o.getOraPrenotazione()==p.getOraPrenotazione())
						libero=false;
				}
			}
		
	return libero;
	}
	
	@Transactional
	public List<Prenotazione> getPrenotazioniMeccanico(Meccanico m){
		
		
		List<Prenotazione> lista = new ArrayList<>();
		
		for(Prenotazione p: prenotazioneService.tutti()) {
			if(p.getIntervento().getMeccanico()== m)
				lista.add(p);
		}
		return lista;
	}
	
	public PrenotazioneService getPrenotazioneService() {
		return prenotazioneService;
	}
}