package fr.cytech.pau.youflix.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import fr.cytech.pau.youflix.Utils.RedirectionUtil;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RechercheController {
    
    @GetMapping(path = "/search")
    public String search(HttpServletRequest request){
        return RedirectionUtil.getReturnForContent(request.getSession(), "resultats_recherche");
    }

    @PostMapping(path = "/search")
    public String postRecherche(WebRequest request) {

        // note : il y a deux formulaires sur cette page : 
        //     - un pour faire une recherche
        //     - un pour affiner la recherche

        // récupération de l'adresse mail et du mot de passe
        String contenuRecherche = request.getParameter("contenu-recherche");
        String genre = request.getParameter("genre");
        String acteur = request.getParameter("acteur");

        System.out.println("Recherche : " + contenuRecherche);
        System.out.println("Genre     : " + genre);
        System.out.println("Acteur    : " + acteur);

        return "redirect:/search";

    }

}
