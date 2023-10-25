package fr.cytech.pau.youflix.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.cytech.pau.youflix.Models.Acteur;
import fr.cytech.pau.youflix.Models.Categorie;
import fr.cytech.pau.youflix.Models.Repo.ActeurRepository;
import fr.cytech.pau.youflix.Models.Repo.CategorieRepository;
import fr.cytech.pau.youflix.Utils.RedirectionUtil;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ActeurController {

    @Autowired
    CategorieRepository categorieRepository;

    @Autowired
    ActeurRepository acteurRepository;
    
    @GetMapping(path = "/acteur")
    public String acteur(HttpServletRequest request, Model model) {

        // liste des catégories de la BDD + ajout au modèle
        List<Categorie> listeCategoriesBdd = categorieRepository.findAll();
        model.addAttribute("listeCategoriesBdd", listeCategoriesBdd);

        // récupération de l'identifiant de l'acteur de la page
        String idActeur = request.getParameter("q");

        // récupération de la liste des acteurs de la BDD
        List<Acteur> listeActeursBdd = acteurRepository.findAll();

        // on regarde si l'ID de l'acteur souhaité existe dans la BDD
        boolean acteurExiste = false;
        Acteur acteurConsidere = null;
        for (Acteur acteur : listeActeursBdd) {
            if (acteur.getIdActeur().toString().equals(idActeur)) {
                acteurExiste = true;
                acteurConsidere = acteur;
                break;
            }
        }

        // si l'acteur n'existe pas, on redirige l'utilisateur vers la page 404
        // s'il existe, on l'ajoute au modèle
        if (!acteurExiste) {
            return "404";
        } else {
            model.addAttribute("acteurConsidere", acteurConsidere);
        }

        return RedirectionUtil.getReturnForContent(request.getSession(), "acteur");

    }

}
