package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.repository.CollezioneRepository;

@Service
public class CollezioneService {
	
	@Autowired
	private CollezioneRepository collezioneRepository; 
	
	@Autowired
	private OperaService operaService;
	
	@Autowired
	private CuratoreService curatoreService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	public CredentialsService getCredentialsService() {
		return credentialsService;
	}

	public OperaService getOperaService() {
		return operaService;
	}

	public CuratoreService getCuratoreService() {
		return curatoreService;
	}

	@Transactional
	public Collezione inserisci(Collezione collezione) {
		return collezioneRepository.save(collezione);
	}
	
	@Transactional
	public List<Collezione> collezionePerNomeAndDescrizione(String nome, String descrizione) {
		return collezioneRepository.findByNomeAndDescrizione(nome, descrizione);
	}

	@Transactional
	public List<Collezione> tutti() {
		return (List<Collezione>) collezioneRepository.findAll();
	}

	@Transactional
	public Collezione collezionePerId(Long id) {
		Optional<Collezione> optional = collezioneRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Collezione collezione) {
		List<Collezione> collezioni = this.collezioneRepository.findByNomeAndDescrizione(collezione.getNome(), collezione.getDescrizione());
		if (collezioni.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public void eliminaCollezione(Collezione c) {
		for(Opera o:c.getOpere()) {
			o.setCollezione(null);
			operaService.inserisci(o);
		}
		collezioneRepository.delete(c);
	}
	

}
