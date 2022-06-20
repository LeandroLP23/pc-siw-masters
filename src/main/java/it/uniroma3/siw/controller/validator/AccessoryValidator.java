package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.Accessory;
import it.uniroma3.siw.service.AccessoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AccessoryValidator implements Validator {

    @Autowired
    private AccessoryService accessoryService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Accessory.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");

        if(this.accessoryService.alreadyExists((Accessory) target)) {
            errors.reject("accessory.duplicate");
        }
    }
}
