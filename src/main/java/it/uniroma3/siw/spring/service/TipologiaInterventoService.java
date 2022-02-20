package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.TipologiaIntervento;
import it.uniroma3.siw.spring.model.Intervento;
import it.uniroma3.siw.spring.repository.TipologiaInterventoRepository;

@Service
public class TipologiaInterventoService {
	
	@Autowired
	private TipologiaInterventoRepository tipologiaInterventoRepository; 
	
	@Autowired
	private InterventoService interventoService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	public CredentialsService getCredentialsService() {
		return credentialsService;
	}

	public InterventoService getInterventoService() {
		return interventoService;
	}
    
	@Transactional
	public TipologiaIntervento tipologiaInterventoPerId(int id) {
		return tipologiaInterventoRepository.findById(id);
	}
	
	
	@Transactional
	public TipologiaIntervento inserisci(TipologiaIntervento tipologiaIntervento) {
		return tipologiaInterventoRepository.save(tipologiaIntervento);
	}
	
	@Transactional
	public List<TipologiaIntervento> tipologiaInterventoPerNome(String nome) {
		return tipologiaInterventoRepository.findByNome(nome);
	}

	@Transactional
	public List<TipologiaIntervento> tutti() {
		return (List<TipologiaIntervento>) tipologiaInterventoRepository.findAll();
	}
	
	
	@Transactional
	public TipologiaIntervento tipologiaInterventoPerId(Long id) {
		Optional<TipologiaIntervento> optional = tipologiaInterventoRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(TipologiaIntervento tipologiaIntervento) {
		List<TipologiaIntervento> tipologiaInterventi = this.tipologiaInterventoRepository.findByNome(tipologiaIntervento.getNome());
		if (tipologiaInterventi.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public void eliminaTipologiaIntervento(TipologiaIntervento t) {
		for(Intervento i:t.getInterventi()) {
			i.setTipologiaIntervento(null);
			interventoService.inserisci(i);
		}
		tipologiaInterventoRepository.delete(t);
	}
}
