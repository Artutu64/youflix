package fr.cytech.pau.youflix.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FavorisController {
    
    @GetMapping(path = "/favoris")
    public String favoris(){
        return "favoris";
    }

}
