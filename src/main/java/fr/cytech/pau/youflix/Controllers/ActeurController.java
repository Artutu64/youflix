package fr.cytech.pau.youflix.Controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.cytech.pau.youflix.Models.Acteur;
import fr.cytech.pau.youflix.Models.Categorie;
import fr.cytech.pau.youflix.Models.Video;
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
        if (!acteurExiste) {
            return RedirectionUtil.getReturnForContent(request.getSession(), "404");
        }

        // récupération de tous les films dans lesquels joue l'acteur considéré
        Set<Video> listeAutresFilmsActeur = acteurConsidere.getJoueDans();

        // [à faire] recherche du film le plus vu de l'acteur
        int nbrVuesMax = -1;
        Video filmLePlusVu = null;
        for (Video video : listeAutresFilmsActeur) {
            if (video.getNbVues() > nbrVuesMax) {
                nbrVuesMax = video.getNbVues();
                filmLePlusVu = video;
            }
        }

        // suppression de la vidéo la plus vue de la liste pour qu'elle ne soit pas affichée en doublon sur la page
        listeAutresFilmsActeur.remove(filmLePlusVu);

        // ajout des attributs au modèle
        model.addAttribute("acteurConsidere", acteurConsidere);
        model.addAttribute("listeAutresFilmsActeur", listeAutresFilmsActeur);
        model.addAttribute("filmLePlusVu", filmLePlusVu);

        return RedirectionUtil.getReturnForContent(request.getSession(), "acteur");

    }

}
