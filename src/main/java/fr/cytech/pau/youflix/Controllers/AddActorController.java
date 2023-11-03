package fr.cytech.pau.youflix.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import fr.cytech.pau.youflix.Models.Acteur;
import fr.cytech.pau.youflix.Models.Categorie;
import fr.cytech.pau.youflix.Models.Repo.ActeurRepository;
import fr.cytech.pau.youflix.Models.Repo.CategorieRepository;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AddActorController {

    @Autowired
    ActeurRepository acteurRepository;

    @Autowired
    CategorieRepository categorieRepository;
    
    @GetMapping(path = "/add-actor")
    public String addActor(Model model){

        List<Categorie> listeCategoriesBdd = categorieRepository.findAll();
        model.addAttribute("listeCategoriesBdd", listeCategoriesBdd);

        return "add_actor";
    }

    @GetMapping(path = "delete-actor")
    public void deleteActor(WebRequest request, HttpServletResponse response){
        String id = request.getParameter("idActeur");
        Long idActeur = -1L;
        try {
            idActeur = Long.parseLong(id);
        } catch(NumberFormatException e){
            e.printStackTrace();
        }

        Acteur acteur = null;
        for(Acteur a : acteurRepository.findAll()){
            if(a.getIdActeur().equals(idActeur)){
                acteur = a;
            }
        }
        if(acteur == null){
            response.setStatus(404);
        } else {
            try {
                acteurRepository.delete(acteur);
                response.setStatus(200);
            } catch(Exception e){
                response.setStatus(400);
            }
        }

    }

    @PostMapping(path = "edit-actor")
    public String editPostActor(WebRequest request, Model model){

        String nomActeur = request.getParameter("nom-acteur");
        String prenomActeur = request.getParameter("prenom-acteur");
        String id = request.getParameter("idActeur");
        Long idActeur = -1L;
        try {
            idActeur = Long.parseLong(id);
        } catch(NumberFormatException e){
            e.printStackTrace();
        }

        Acteur acteur = null;
        for(Acteur a : acteurRepository.findAll()){
            if(a.getIdActeur().equals(idActeur)){
                acteur = a;
            }
        }
        if(acteur == null){
            return "404";
        }
        if(nomActeur != null && !(nomActeur.equals(""))){
           if(prenomActeur != null && !(prenomActeur.equals(""))){
                acteur.setNom(nomActeur);
                acteur.setPrenom(prenomActeur);
                acteurRepository.save(acteur);
                return "redirect:/admin";
            } 
        }
        model.addAttribute("nom", acteur.getNom());
        model.addAttribute("prenom", acteur.getPrenom());
        return "edit_actor";
    }

    @GetMapping(path = "edit-actor")
    public String editActor(WebRequest request, Model model){

        String id = request.getParameter("idActeur");
        Long idActeur = -1L;
        try {
            idActeur = Long.parseLong(id);
        } catch(NumberFormatException e){
            e.printStackTrace();
        }

        Acteur acteur = null;
        for(Acteur a : acteurRepository.findAll()){
            if(a.getIdActeur().equals(idActeur)){
                acteur = a;
            }
        }

        if(acteur == null){
            return "404";
        }

        model.addAttribute("nom", acteur.getNom());
        model.addAttribute("prenom", acteur.getPrenom());

        return "edit_actor";
    }

    @PostMapping(path = "/add-actor")
    public String postAddActor(WebRequest request) {
        
        // récupération de l'adresse mail et du mot de passe
        String nomActeur = request.getParameter("nom-acteur");
        String prenomActeur = request.getParameter("prenom-acteur");

        if (nomActeur != null && prenomActeur != null) {
            List<Acteur> acteurs = acteurRepository.findActorByNom(nomActeur);
            boolean exists = false;
            for(Acteur acteur : acteurs){
                if (acteur.getPrenom().equals(prenomActeur)) {
                    exists = true;
                }
            }
            if (!exists) {
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
