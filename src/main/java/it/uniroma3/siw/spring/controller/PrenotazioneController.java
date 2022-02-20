package it.uniroma3.siw.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Intervento;
import it.uniroma3.siw.spring.model.Meccanico;
import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.TipologiaIntervento;
import it.uniroma3.siw.spring.repository.MeccanicoRepository;
import it.uniroma3.siw.spring.service.MeccanicoService;
import it.uniroma3.siw.spring.service.PrenotazioneService;

@Controller

public class PrenotazioneController {
	

	@Autowired
	private PrenotazioneService prenotazioneService;
	
    @Autowired
    private PrenotazioneValidator prenotazioneValidator;

    @RequestMapping(value="/default/addPrenotazione", method = RequestMethod.GET)
    public String addPrenotazione(Model model) {
    	model.addAttribute("prenotazione", new Prenotazione());
    	model.addAttribute("interventi", prenotazioneService.getInterventoService().tutti());
        return "prenotazioneForm.html";
    }
    
    
    @RequestMapping(value = "/prenotazione/{id}", method = RequestMethod.GET)
    public String getPrenotazione(@PathVariable("id") Long id, Model model) {
    	
    	Prenotazione p=prenotazioneService.prenotazionePerId(id);
    	model.addAttribute("prenotazione", p);
    	model.addAttribute("intervento",p.getIntervento());
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.prenotazioneService.getCredentialsService().getCredentials(userDetails.getUsername());
    	model.addAttribute("credentials", credentials);
        if(p.getCliente()==credentials.getUser()) {     	
        	model.addAttribute("prenotazione",p);
        	model.addAttribute("intervento",p.getIntervento());
        	model.addAttribute("credentials", credentials);
        	
        	return "prenotazione.html";
        }
        else
        
    	return "error.html";
    }   
    
    
    @RequestMapping(value = "/admin/prenotazioneClienti/{id}", method = RequestMethod.GET)
    public String getPrenotazioneClienti(@PathVariable("id") Long id, Model model) {
    	Prenotazione p=prenotazioneService.prenotazionePerId(id);
    	model.addAttribute("prenotazione", p);
    	model.addAttribute("intervento",p.getIntervento());
    	model.addAttribute("cliente",p.getCliente());
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.prenotazioneService.getCredentialsService().getCredentials(userDetails.getUsername());
    	model.addAttribute("credentials", credentials);
    	return "prenotazione.html";
    }
    
    @RequestMapping(value = "/default/prenotazioni", method = RequestMethod.GET)
    public String getPrenotazioni(Model model) {
    		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	Credentials credentials = prenotazioneService.getCredentialsService().getCredentials(userDetails.getUsername());
    		model.addAttribute("prenotazioni", this.prenotazioneService.prenotazioniPerUser(credentials.getUser()));
        	model.addAttribute("credentials", credentials);
        	model.addAttribute("prenotazione", new Prenotazione());
    		return "prenotazioni.html";
    }
    
    
    @RequestMapping(value = "/admin/tuttePrenotazioni", method = RequestMethod.GET)
    public String gettuttePrenotazioni(Model model) {
    		model.addAttribute("prenotazioni", this.prenotazioneService.tutti());
    		return "tuttePrenotazioni.html";
    }
    
    @RequestMapping(value = "/prenotazione", method = RequestMethod.POST)
    public String newPrenotazione(@ModelAttribute("prenotazione") Prenotazione prenotazione, 
    									Model model, BindingResult bindingResult) {
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = prenotazioneService.getCredentialsService().getCredentials(userDetails.getUsername());
    	model.addAttribute("credentials", credentials);
    	
    	this.prenotazioneValidator.validate(prenotazione, bindingResult);
  
    	if (!bindingResult.hasErrors()) {
    		if(prenotazione.getOraPrenotazione()> 8 && prenotazione.getOraPrenotazione()< 18) {
        	
        	
        	Meccanico m= prenotazione.getIntervento().getMeccanico();
        	List<Intervento> interventi= m.getInterventi();
        	for(Intervento i: interventi) {
        		for(Prenotazione p: i.getPrenotazioni()) {
        			
        			if(p.getOraPrenotazione()== prenotazione.getOraPrenotazione() && p.getDataPrenotazione().equals (prenotazione.getDataPrenotazione())) {
        				
            	    	model.addAttribute("errore", "il meccanico in questa ora e' occupato");
            		return "error.html";
            	    }
        		}
        	}
                prenotazione.setCliente(credentials.getUser());
        		this.prenotazioneService.inserisci(prenotazione);
        		model.addAttribute("prenotazioni", this.prenotazioneService.prenotazioniPerUser(credentials.getUser()));
        		return "prenotazioni.html";
        	}
    		else 
    			model.addAttribute("errore","orario errato");
    	}
         
        else
        	model.addAttribute("errore", "il meccanico in questa ora e' occupato");
        return "error.html";
    }
       
       
    
    
    @RequestMapping(value = "/eliminaPrenotazione/{id}", method = RequestMethod.POST)
    public String eliminaPrenotazione(Model model, @PathVariable("id") Long idPrenotazione) {
    		Prenotazione p=prenotazioneService.prenotazionePerId(idPrenotazione);
    		prenotazioneService.eliminaPrenotazione(p);
    		model.addAttribute("Prenotazioni", this.prenotazioneService.tutti());
    		
    		return "prenotazioni.html";
    }
}

