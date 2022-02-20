package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Meccanico;
import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.repository.PrenotazioneRepository;

@Service
public class PrenotazioneService {
	
	@Autowired
	private PrenotazioneRepository prenotazioneRepository; 
    
	@Autowired 
	private MeccanicoService meccanicoService;
	
	@Autowired
	private InterventoService interventoService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Transactional
	public Prenotazione inserisci(Prenotazione prenotazione) {
		return prenotazioneRepository.save(prenotazione);
	}
	
	@Transactional
	public List<Prenotazione> tutti() {
		return (List<Prenotazione>) prenotazioneRepository.findAll();
	}

	@Transactional
	public Prenotazione prenotazionePerId(Long id) {
		Optional<Prenotazione> optional = prenotazioneRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
	@Transactional
	public List<Prenotazione> prenotazioniPerUser(User user) {
		List<Prenotazione> prenotazioniUtente = new ArrayList<Prenotazione>();
		for(Prenotazione p: this.tutti()) {
			if(p.getCliente().equals(user))
				prenotazioniUtente.add(p);
		}
		return prenotazioniUtente;
	}
	
	@Transactional
	public List<Prenotazione> prenotazioniPerMeccanico(Meccanico meccanico) {
		List<Prenotazione> prenotazioniUtente = new ArrayList<Prenotazione>();
		for(Prenotazione p: this.tutti()) {
			if(p.getIntervento().getMeccanico().equals(meccanico))
				prenotazioniUtente.add(p);
		}
		return prenotazioniUtente;
	}
	
	
	@Transactional
	public boolean alreadyExists(Prenotazione prenotazione) {
		List<Prenotazione> prenotazioni = this.prenotazioneRepository.findByDataPrenotazioneAndOraPrenotazioneAndIntervento(prenotazione.getDataPrenotazione(),prenotazione.getOraPrenotazione(), prenotazione.getIntervento());
		
		if (prenotazioni.size() > 0 )//|| p.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public void eliminaPrenotazione(Prenotazione p) {
		prenotazioneRepository.delete(p);
	}
	
	 

	public MeccanicoService getMeccanicoService() {
		return meccanicoService;
	}

	public void setMeccanicoService(MeccanicoService meccanicoService) {
		this.meccanicoService = meccanicoService;
	}

	public CredentialsService getCredentialsService() {
		return credentialsService;
	}

	public void setCredentialsService(CredentialsService credentialsService) {
		this.credentialsService = credentialsService;
	}

	public InterventoService getInterventoService() {
		return interventoService;
	}

	public void setInterventoService(InterventoService intervantoService) {
		this.interventoService = interventoService;
	}
	
	
	
}