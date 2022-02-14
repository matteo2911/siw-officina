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

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Intervento;
import it.uniroma3.siw.spring.model.TipologiaIntervento;
import it.uniroma3.siw.spring.service.InterventoService;

@Controller
public class InterventoController {
	
	@Autowired
	private InterventoService interventoService;
	
    @Autowired
    private InterventoValidator interventoValidator;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    

    @RequestMapping(value="/admin/addIntervento", method = RequestMethod.GET)
    public String addIntervento(Model model) {
    	logger.debug("addIntervento");
    	model.addAttribute("intervento", new Intervento());
    	model.addAttribute("meccanici", interventoService.getMeccanicoService().tutti());
        return "interventoForm.html";
    }

    @RequestMapping(value = "/intervento/{id}", method = RequestMethod.GET)
    public String getIntervento(@PathVariable("id") Long id, Model model) {
    	Intervento i=interventoService.interventoPerId(id);
    	model.addAttribute("intervento", i);
    	model.addAttribute("meccanico",i.getMeccanico());
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.interventoService.getCredentialsService().getCredentials(userDetails.getUsername());
    	model.addAttribute("credentials", credentials);
    	return "intervento.html";
    }

    @RequestMapping(value = "/interventi", method = RequestMethod.GET)
    public String getInterventi(Model model) {
    		model.addAttribute("interventi", this.interventoService.tutti());
    		return "interventi.html";
    }
    
    @RequestMapping(value = "/admin/intervento", method = RequestMethod.POST)
    public String newIntervento(@ModelAttribute("intervento") Intervento intervento, 
    									Model model, BindingResult bindingResult) {
    	this.interventoValidator.validate(intervento, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.interventoService.inserisci(intervento);
            model.addAttribute("interventi", this.interventoService.tutti());
            return "interventi.html";
        }
        return "interventoForm.html";
    }
    
    @RequestMapping(value = "/admin/eliminaIntervento/{id}", method = RequestMethod.POST)
    public String eliminaIntervento(Model model, @PathVariable("id") Long idIntervento) {
    		
    		Intervento i=interventoService.interventoPerId(idIntervento);
    		interventoService.eliminaIntervento(i);
    		model.addAttribute("intervento", this.interventoService.tutti());
    		return "interventi.html";
    }
}
