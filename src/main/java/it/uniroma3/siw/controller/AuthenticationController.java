package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.CredentialsValidator;
import it.uniroma3.siw.controller.validator.UserValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsServices;
import it.uniroma3.siw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {

    @Autowired
    UserValidator userValidator;
    @Autowired
    CredentialsValidator credentialsValidator;

    @Autowired
    CredentialsServices credentialsService;
    @Autowired
    UserService userService;

    @GetMapping(path= {"/loginPage", "/login"})
    public String getLoginPage(Model model)
    {
        model.addAttribute("user", new User());
        model.addAttribute("credentials", new Credentials());
        return "loginPage";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user,
                               BindingResult userBindingResult,
                               @ModelAttribute("credentials") Credentials credentials,
                               BindingResult credentialsBindingResult,
                               Model model)
    {
        //Vengono validati Utente e Credenziali fornite
        this.userValidator.validate(user, userBindingResult);
        this.credentialsValidator.validate(credentials, credentialsBindingResult);

        if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            return "index";
        }
        else
        {
            return "loginPage";
        }
    }


}
