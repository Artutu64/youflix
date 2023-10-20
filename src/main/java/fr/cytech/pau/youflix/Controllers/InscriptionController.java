package fr.cytech.pau.youflix.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InscriptionController {
    
    @GetMapping(path = "/inscription")
    public String inscription(){
        return "register";
    }

}

