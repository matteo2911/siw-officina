package it.uniroma3.siw.spring.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.TipologiaIntervento;
import it.uniroma3.siw.spring.service.TipologiaInterventoService;


@Component
public class TipologiaInterventoValidator implements Validator {
	@Autowired
	private TipologiaInterventoService tipologiaInterventoService;
	
    private static final Logger logger = LoggerFactory.getLogger(TipologiaInterventoValidator.class);

	@Override
	public void validate(Object t, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codice", "required");

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.tipologiaInterventoService.alreadyExists((TipologiaIntervento)t)) {
				logger.debug("e' un duplicato");
				errors.reject("tipologiaEsiste");
			}
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return TipologiaIntervento.class.equals(aClass);
	}
}
