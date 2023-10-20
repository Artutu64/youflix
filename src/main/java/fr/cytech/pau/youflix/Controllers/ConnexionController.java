package fr.cytech.pau.youflix.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConnexionController {
    
    @GetMapping(path = "/connexion")
    public String connexion(){
        return "login";
    }

}
