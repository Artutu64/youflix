package fr.cytech.pau.youflix.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddActorController {
    
    @GetMapping(path = "/add-actor")
    public String addActor(){
        return "add_actor";
    }

}
