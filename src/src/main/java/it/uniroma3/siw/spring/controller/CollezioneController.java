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

import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.CuratoreService;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class CollezioneController {
	
	@Autowired
	private CollezioneService collezioneService;
	
	
    @Autowired
    private CollezioneValidator collezioneValidator;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    

    @RequestMapping(value="/admin/addCollezione", method = RequestMethod.GET)
    public String addCollezione(Model model) {
    	logger.debug("addCollezione");
    	model.addAttribute("collezione", new Collezione());
    	model.addAttribute("curatori",collezioneService.getCuratoreService().tutti());
        return "collezioneForm.html";
    }

    @RequestMapping(value = "/collezione/{id}", method = RequestMethod.GET)
    public String getCollezione(@PathVariable("id") Long id, Model model) {
    	Collezione c = this.collezioneService.collezionePerId(id);
    	model.addAttribute("collezione", c);
    	model.addAttribute("opera", new Opera());
    	model.addAttribute("opere",collezioneService.getOperaService().getOpereFiltered());
    	model.addAttribute("opereCollezione",c.getOpere());
   
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.collezioneService.getCredentialsService().getCredentials(userDetails.getUsername());
    	model.addAttribute("credentials", credentials);
    	return "collezione.html";
    }

    @RequestMapping(value = "/collezione", method = RequestMethod.GET)
    public String getCollezioni(Model model) {
    		model.addAttribute("collezioni", this.collezioneService.tutti());
    		return "collezioni.html";
    }
    
    @RequestMapping(value = "/admin/collezione", method = RequestMethod.POST)
    public String newCollezione(@ModelAttribute("collezione") Collezione collezione, 
    									Model model, BindingResult bindingResult) {
    	this.collezioneValidator.validate(collezione, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.collezioneService.inserisci(collezione);
            model.addAttribute("collezioni", this.collezioneService.tutti());
            return "collezioni.html";
        }
        return "collezioneForm.html";
    }
    
    @RequestMapping(value = "/admin/addOperaACollezione/{id}", method = RequestMethod.POST)
    public String aggiungiOpera(@RequestParam("opera") Long idOpera, 
    									Model model, @PathVariable("id") Long idCollezione) {
    	
    	Opera o=collezioneService.getOperaService().operaPerId(idOpera);
    	Collezione c = this.collezioneService.collezionePerId(idCollezione);
    	o.setCollezione(c);
    	collezioneService.getOperaService().inserisci(o);
    	model.addAttribute("collezione", this.collezioneService.collezionePerId(idCollezione));
    	model.addAttribute("opere",collezioneService.getOperaService().getOpereFiltered());
    	model.addAttribute("opereCollezione",c.getOpere());
    	
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.collezioneService.getCredentialsService().getCredentials(userDetails.getUsername());
    	model.addAttribute("credentials", credentials);
    	return "collezione.html";
    }
    
    @RequestMapping(value = "/admin/rimuoviOpera/{id}", method = RequestMethod.POST)
    public String rimuoviOpera(@RequestParam("opera") Long idOpera, 
    									Model model, @PathVariable("id") Long idCollezione) {
    	Collezione c = this.collezioneService.collezionePerId(idCollezione);
    	Opera o=collezioneService.getOperaService().operaPerId(idOpera);
    	o.setCollezione(null);
    	collezioneService.getOperaService().inserisci(o);
    	model.addAttribute("collezione", c);
    	model.addAttribute("opere",collezioneService.getOperaService().getOpereFiltered());
    	model.addAttribute("opereCollezione",c.getOpere());
    	
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.collezioneService.getCredentialsService().getCredentials(userDetails.getUsername());
    	model.addAttribute("credentials", credentials);
    	return "collezione.html";
    }
    
    @RequestMapping(value = "/admin/eliminaCollezione/{id}", method = RequestMethod.POST)
    public String eliminaCollezione(Model model, @PathVariable("id") Long idCollezione) {
    		
    		Collezione c=collezioneService.collezionePerId(idCollezione);
    		collezioneService.eliminaCollezione(c);
    		model.addAttribute("collezioni", this.collezioneService.tutti());
    		return "collezioni.html";
    }
    
}
