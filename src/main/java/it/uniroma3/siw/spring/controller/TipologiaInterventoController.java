package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import it.uniroma3.siw.spring.model.TipologiaIntervento;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Intervento;
import it.uniroma3.siw.spring.service.TipologiaInterventoService;


@Controller
public class TipologiaInterventoController {
	
	@Autowired
	private TipologiaInterventoService tipologiaInterventoService;
	
	
    @Autowired
    private TipologiaInterventoValidator tipologiaInterventoValidator;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    

    @RequestMapping(value="/admin/addTipologiaIntervento", method = RequestMethod.GET)
    public String addTipologiaIntervento(Model model) {
    	logger.debug("addTipologiaIntervento");
    	model.addAttribute("tipologiaIntervento", new TipologiaIntervento());
        return "tipologiaInterventoForm.html";
    }

    @RequestMapping(value = "/tipologiaIntervento/{id}", method = RequestMethod.GET)
    public String getTipologiaIntervento(@PathVariable("id") Long id, Model model) {
    	TipologiaIntervento t = this.tipologiaInterventoService.tipologiaInterventoPerId(id);
    	model.addAttribute("tipologiaIntervento", t);
    	model.addAttribute("intervento", new Intervento());
    	model.addAttribute("interventi",tipologiaInterventoService.getInterventoService().getInterventiFiltered());
    	model.addAttribute("interventiTipologiaIntervento",t.getInterventi());
   
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.tipologiaInterventoService.getCredentialsService().getCredentials(userDetails.getUsername());
    	model.addAttribute("credentials", credentials);
    	return "tipologiaIntervento.html";
    }

    @RequestMapping(value = "/tipologiaIntervento", method = RequestMethod.GET)
    public String getTipologiaInterventi(Model model) {
    		model.addAttribute("tipologiaInterventi", this.tipologiaInterventoService.tutti());
    		return "tipologiaInterventi.html";
    }
    
    @RequestMapping(value = "/admin/tipologiaIntervento", method = RequestMethod.POST)
    public String newTipologiaIntervento(@ModelAttribute("tipologiaIntervento") TipologiaIntervento tipologiaIntervento, 
    									Model model, BindingResult bindingResult) {
    	this.tipologiaInterventoValidator.validate(tipologiaIntervento, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.tipologiaInterventoService.inserisci(tipologiaIntervento);
            model.addAttribute("tipologiaInterventi", this.tipologiaInterventoService.tutti());
            return "tipologiaInterventi.html";
        }
        return "tipologiaInterventoForm.html";
    }
    
    @RequestMapping(value = "/admin/addInterventoATipologiaIntervento/{id}", method = RequestMethod.POST)
    public String aggiungiIntervento(@RequestParam("intervento") Long idIntervento, 
    									Model model, @PathVariable("id") Long idTipologiaIntervento) {
    	
    	Intervento i=tipologiaInterventoService.getInterventoService().interventoPerId(idIntervento);
    	TipologiaIntervento t = this.tipologiaInterventoService.tipologiaInterventoPerId(idTipologiaIntervento);
    	i.setTipologiaIntervento(t);
    	tipologiaInterventoService.getInterventoService().inserisci(i);
    	model.addAttribute("tipologiaIntervento", this.tipologiaInterventoService.tipologiaInterventoPerId(idTipologiaIntervento));
    	model.addAttribute("interventi",tipologiaInterventoService.getInterventoService().getInterventiFiltered());
    	model.addAttribute("interventiTipologiaIntervento",t.getInterventi());
    	
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.tipologiaInterventoService.getCredentialsService().getCredentials(userDetails.getUsername());
    	model.addAttribute("credentials", credentials);
    	return "tipologiaIntervento.html";
    }
    
    
    @RequestMapping(value = "/admin/rimuoviIntervento/{id}", method = RequestMethod.POST)
    public String rimuoviIntervento(@RequestParam("intervento") Long idIntervento, 
    									Model model, @PathVariable("id") Long idTipologiaIntervento) {
    	TipologiaIntervento t = this.tipologiaInterventoService.tipologiaInterventoPerId(idTipologiaIntervento);
    	Intervento i=tipologiaInterventoService.getInterventoService().interventoPerId(idIntervento);
    	i.setTipologiaIntervento(null);
    	tipologiaInterventoService.getInterventoService().inserisci(i);
    	model.addAttribute("tipologiaIntervento", t);
    	model.addAttribute("interventi",tipologiaInterventoService.getInterventoService().getInterventiFiltered());
    	model.addAttribute("interventiTipologiaIntervento",t.getInterventi());
    	
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.tipologiaInterventoService.getCredentialsService().getCredentials(userDetails.getUsername());
    	model.addAttribute("credentials", credentials);
    	return "tipologiaIntervento.html";
    }
    
    @RequestMapping(value = "/admin/eliminaTipologiaIntervento/{id}", method = RequestMethod.POST)
    public String eliminaTipologiaIntervento(Model model, @PathVariable("id") Long idTipologiaIntervento) {
    		
    		TipologiaIntervento t=tipologiaInterventoService.tipologiaInterventoPerId(idTipologiaIntervento);
    		tipologiaInterventoService.eliminaTipologiaIntervento(t);
    		model.addAttribute("tipologiaInterventi", this.tipologiaInterventoService.tutti());
    		return "tipologiaInterventi.html";
    } 
}
