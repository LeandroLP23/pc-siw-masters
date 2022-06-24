package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.Hardware;
import it.uniroma3.siw.service.HardwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

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

        if(this.hardwareService.alreadyExists((Hardware) target)) {
            errors.reject("hardware.duplicate");
        }
    }

    public void validateUpdate(Object target, MultipartFile image, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
        if(image.isEmpty() && this.hardwareService.alreadyExists((Hardware) target))
        {
            errors.reject("hardware.duplicate");
        }
    }
}
