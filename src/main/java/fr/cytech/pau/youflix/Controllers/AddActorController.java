package fr.cytech.pau.youflix.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class AddActorController {
    
    @GetMapping(path = "/add-actor")
    public String addActor(){
        return "add_actor";
    }

    @PostMapping(path = "/add-actor")
    public String postAddActor(WebRequest request) {
        
        String nomActeur = request.getParameter("nom-acteur");
        String prenomActeur = request.getParameter("prenom-acteur");

        System.out.println("Nom de l'acteur : " + nomActeur + "\nPrénom de l'acteur : " + prenomActeur);

        return "redirect:/";

    }

}
