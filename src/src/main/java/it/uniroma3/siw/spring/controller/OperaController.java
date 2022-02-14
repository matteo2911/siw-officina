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
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class OperaController {
	
	@Autowired
	private OperaService operaService;
	
    @Autowired
    private OperaValidator operaValidator;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    

    @RequestMapping(value="/admin/addOpera", method = RequestMethod.GET)
    public String addOpera(Model model) {
    	logger.debug("addOpera");
    	model.addAttribute("opera", new Opera());
    	model.addAttribute("artisti", operaService.getArtistaService().tutti());
        return "operaForm.html";
    }

    @RequestMapping(value = "/opera/{id}", method = RequestMethod.GET)
    public String getOpera(@PathVariable("id") Long id, Model model) {
    	Opera o=operaService.operaPerId(id);
    	model.addAttribute("opera", o);
    	model.addAttribute("artista",o.getArtista());
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.operaService.getCredentialsService().getCredentials(userDetails.getUsername());
    	model.addAttribute("credentials", credentials);
    	return "opera.html";
    }

    @RequestMapping(value = "/opere", method = RequestMethod.GET)
    public String getOpere(Model model) {
    		model.addAttribute("opere", this.operaService.tutti());
    		return "opere.html";
    }
    
    @RequestMapping(value = "/admin/opera", method = RequestMethod.POST)
    public String newOpera(@ModelAttribute("opera") Opera opera, 
    									Model model, BindingResult bindingResult) {
    	this.operaValidator.validate(opera, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.operaService.inserisci(opera);
            model.addAttribute("opere", this.operaService.tutti());
            return "opere.html";
        }
        return "operaForm.html";
    }
    
    @RequestMapping(value = "/admin/eliminaOpera/{id}", method = RequestMethod.POST)
    public String eliminaOpera(Model model, @PathVariable("id") Long idOpera) {
    		
    		Opera o=operaService.operaPerId(idOpera);
    		operaService.eliminaOpera(o);
    		model.addAttribute("opere", this.operaService.tutti());
    		return "opere.html";
    }
}
