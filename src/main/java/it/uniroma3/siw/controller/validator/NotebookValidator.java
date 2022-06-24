package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.Notebook;
import it.uniroma3.siw.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class NotebookValidator implements Validator {

    @Autowired
    private NotebookService notebookService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Notebook.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");

        if(this.notebookService.alreadyExists((Notebook) target)) {
            errors.reject("notebook.duplicate");
        }
    }

    public void validateUpdate(Object target, MultipartFile image, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
        if(image.isEmpty() && this.notebookService.alreadyExists((Notebook) target))
        {
            errors.reject("notebook.duplicate");
        }
    }
}
