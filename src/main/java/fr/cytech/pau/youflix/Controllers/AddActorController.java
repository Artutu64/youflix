package fr.cytech.pau.youflix.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import fr.cytech.pau.youflix.Models.Acteur;
import fr.cytech.pau.youflix.Models.Repo.ActeurRepository;

@Controller
public class AddActorController {

    @Autowired
    ActeurRepository acteurRepository;
    
    @GetMapping(path = "/add-actor")
    public String addActor(){
        return "add_actor";
    }

    @PostMapping(path = "/add-actor")
    public String postAddActor(WebRequest request) {
        
        // récupération de l'adresse mail et du mot de passe
        String nomActeur = request.getParameter("nom-acteur");
        String prenomActeur = request.getParameter("prenom-acteur");

        if(nomActeur != null && prenomActeur != null){
            List<Acteur> acteurs = acteurRepository.findActorByNom(nomActeur);
            boolean exists = false;
            for(Acteur acteur : acteurs){
                if(acteur.getPrenom().equals(prenomActeur)){
                    exists = true;
                }
            }
            if(exists == false){
                Acteur acteur = new Acteur();
                acteur.setIdActeur(5L);
                acteur.setNom(nomActeur);
                acteur.setPrenom(prenomActeur);
                acteurRepository.save(acteur);
                return "redirect:/admin";
            }
        }

        System.out.println("Nom de l'acteur : " + nomActeur + "\nPrénom de l'acteur : " + prenomActeur);

        return "redirect:/";

    }

}
