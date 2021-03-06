package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.Vendor;
import it.uniroma3.siw.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class VendorValidator implements Validator {

    @Autowired
    private VendorService vendorService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Vendor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");

        if(this.vendorService.alreadyExists((Vendor) target)) {
            errors.reject("vendor.duplicate");
        }
    }

    public void validateUpdate(Object target, MultipartFile image, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
        if(image.isEmpty() && this.vendorService.alreadyExists((Vendor) target))
        {
            errors.reject("vendor.duplicate");
        }
    }
}
