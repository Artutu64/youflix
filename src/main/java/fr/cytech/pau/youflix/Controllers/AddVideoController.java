package fr.cytech.pau.youflix.Controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import fr.cytech.pau.youflix.Models.Acteur;
import fr.cytech.pau.youflix.Models.Repo.VideoRepository;

@Controller
public class AddVideoController {
    
    @GetMapping(path = "/add-video")
    public String addVideo(){
        return "add_video";
    }

    @PostMapping(path = "/add-video")
    public String postAddVideo(WebRequest request) {
        
        // récupération de l'adresse mail et du mot de passe
        String titreVideo = request.getParameter("titre-video");
        String lienVideo = request.getParameter("lien-video");
        String descriptionVideo = request.getParameter("description-video");
        String listeActeurs = request.getParameter("liste-acteurs");
        String listeGenres = request.getParameter("liste-genres");

        System.out.println("Titre de la vidéo             : " + titreVideo);
        System.out.println("Lien de la vidéo              : " + lienVideo);
        System.out.println("Description de la vidéo       : " + descriptionVideo);
        System.out.println("Liste des genres de la vidéo  : " + listeActeurs);
        System.out.println("Liste des acteurs de la vidéo : " + listeGenres);

        return "redirect:/";

    }

}