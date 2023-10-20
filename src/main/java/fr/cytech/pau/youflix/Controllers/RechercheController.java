package fr.cytech.pau.youflix.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RechercheController {
    
    @GetMapping(path = "/search")
    public String search(){
        return "resultats_recherche";
    }

}