package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.ComputerBuild;
import it.uniroma3.siw.service.ComputerBuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ComputerBuildValidator implements Validator {

    @Autowired
    private ComputerBuildService computerBuildService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ComputerBuild.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");

        if(this.computerBuildService.alreadyExists((ComputerBuild) target)) {
            errors.reject("computerBuild.duplicate");
        }
    }
}
