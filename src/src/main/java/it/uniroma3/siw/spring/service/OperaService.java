package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.repository.OperaRepository;
@Service
public class OperaService {
	
	
	@Autowired
	private OperaRepository operaRepository; 
	
	@Autowired
	private ArtistaService artistaService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	public ArtistaService getArtistaService() {
		return artistaService;
	}

	@Transactional
	public Opera inserisci(Opera opera) {
		return operaRepository.save(opera);
	}
	
	@Transactional
	public List<Opera> operaPerTitoloAndDescrizione(String titolo) {
		return operaRepository.findByTitolo(titolo);
	}

	@Transactional
	public List<Opera> tutti() {
		return (List<Opera>) operaRepository.findAll();
	}

	@Transactional
	public Opera operaPerId(Long id) {
		Optional<Opera> optional = operaRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Opera opera) {
		List<Opera> opere = this.operaRepository.findByTitolo(opera.getTitolo());
		if (opere.size() > 0)
			return true;
		else 
			return false;
	}
	
	
	@Transactional
	public List<Opera> filtraLista(List<Opera> lista) {
		List<Opera> opere=this.tutti();
		for(Opera o:lista) {	//rimuovo opere che appartengono già alla collezione
			opere.remove(o);
		}
		return opere;
	}
	
	@Transactional
	public void eliminaOpera(Opera o) {
		operaRepository.delete(o);
	}
	
	@Transactional
	public List<Opera> getOpereFiltered(){
		List<Opera> filtrato=new ArrayList<>();
		for(Opera o: this.tutti()) {
			if(o.getCollezione()==null)
				filtrato.add(o);
		}
		return filtrato;
	}

	public CredentialsService getCredentialsService() {
		return credentialsService;
	}

	public void setCredentialsService(CredentialsService credentialsService) {
		this.credentialsService = credentialsService;
	}

	public void setArtistaService(ArtistaService artistaService) {
		this.artistaService = artistaService;
	}


	

}
