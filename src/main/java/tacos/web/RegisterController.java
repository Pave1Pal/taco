package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.data.UserRepository;
import tacos.security.RegistrationForm;


@Slf4j
@Controller
@RequestMapping("register")
public class RegisterController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    RegisterController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute(name = "form")
    public RegistrationForm form() {
        return new RegistrationForm();
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String registration(@ModelAttribute(name="form") RegistrationForm registrationForm) {
        if (userRepository.findByEmail(registrationForm.getEmail()).isEmpty())
            userRepository.save(registrationForm.toUser(passwordEncoder));
        return "redirect:/login";
    }

}
