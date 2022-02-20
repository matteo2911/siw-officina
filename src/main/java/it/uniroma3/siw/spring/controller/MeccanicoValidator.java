package it.uniroma3.siw.spring.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Meccanico;
import it.uniroma3.siw.spring.service.MeccanicoService;


@Component
public class MeccanicoValidator implements Validator {
	@Autowired
	private MeccanicoService meccanicoService;
	
    private static final Logger logger = LoggerFactory.getLogger(MeccanicoValidator.class);

	@Override
	public void validate(Object m, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cognome", "required");

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.meccanicoService.alreadyExists((Meccanico)m)) {
				logger.debug("e' un duplicato");
				errors.reject("meccanicoEsiste");
			}
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Meccanico.class.equals(aClass);
	}
}
