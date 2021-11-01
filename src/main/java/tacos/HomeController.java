package tacos;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        String str = "Hello World";
        model.addAttribute("hello", str);
        return "home.html";
    }
}
