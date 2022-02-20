package it.uniroma3.siw.spring.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Intervento;
import it.uniroma3.siw.spring.service.InterventoService;


@Component
public class InterventoValidator implements Validator {
	@Autowired
	private InterventoService interventoService;
	
    private static final Logger logger = LoggerFactory.getLogger(InterventoValidator.class);

	@Override
	public void validate(Object i, Errors errors) { //da controllare//
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descrizione", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "meccanico", "required");
		
		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.interventoService.alreadyExists((Intervento)i)) {
				logger.debug("e' un duplicato");
				errors.reject("interventoEsiste");
			}
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Intervento.class.equals(aClass);
	}
}
