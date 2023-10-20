package fr.cytech.pau.youflix.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ActeurController {
    
    @GetMapping(path = "/acteur")
    public String acteur(){
        return "acteur";
    }

}
