package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.Hardware;
import it.uniroma3.siw.service.HardwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class HardwareValidator implements Validator {

    @Autowired
    private HardwareService hardwareService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Hardware.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "required");

        if(this.hardwareService.alreadyExists((Hardware) target)) {
            errors.reject("hardware.duplicate");
        }
    }
}
