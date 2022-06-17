package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.ComputerCase;
import it.uniroma3.siw.service.ComputerCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ComputerCaseValidator implements Validator {

    @Autowired
    private ComputerCaseService computerCaseService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ComputerCase.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "required");

        if(this.computerCaseService.alreadyExists((ComputerCase) target)) {
            errors.reject("computerCase.duplicate");
        }
    }
}
