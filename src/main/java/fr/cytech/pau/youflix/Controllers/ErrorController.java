package fr.cytech.pau.youflix.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping(path = "/404")
    public String error(){
        return "404";
    }

}
