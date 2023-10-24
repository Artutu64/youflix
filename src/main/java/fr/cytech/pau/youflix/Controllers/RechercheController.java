package fr.cytech.pau.youflix.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import fr.cytech.pau.youflix.Models.Acteur;
import fr.cytech.pau.youflix.Models.Categorie;
import fr.cytech.pau.youflix.Models.Video;
import fr.cytech.pau.youflix.Models.Repo.ActeurRepository;
import fr.cytech.pau.youflix.Models.Repo.CategorieRepository;
import fr.cytech.pau.youflix.Models.Repo.VideoRepository;
import fr.cytech.pau.youflix.Utils.RechercheUtil;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RechercheController {

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    ActeurRepository acteurRepository;

    @Autowired
    CategorieRepository categorieRepository;

    List<Categorie> listeCategoriesBdd;
    
    @GetMapping(path = "/search")
    public String search(HttpServletRequest request, Model model) {

        // ajout des genres au modèle
        listeCategoriesBdd = categorieRepository.findAll();
        model.addAttribute("listeCategoriesBdd", listeCategoriesBdd);

        // return RedirectionUtil.getReturnForContent(request.getSession(), "resultats_recherche");
        return "resultats_recherche";
    }

    @PostMapping(path = "/search")
    public String postRecherche(WebRequest request, Model model) {

        // récupération les champs potentiellement remplis : recherche, genre (catégorie) et acteur
        String champRecherche = request.getParameter("contenu-recherche");
        String champGenre = request.getParameter("genre");
        String champActeur = request.getParameter("acteur");

        // initialisation des variables pour savoir si le genre et l'acteur ont été renseignés
        boolean genreRenseigne = ((champGenre != null) && (!champGenre.equals("")));
        boolean acteurRenseigne = ((champActeur != null) && (!champActeur.equals("")));
        boolean rechercheRenseignee = ((champRecherche != null) && (!champRecherche.equals("")));

        // liste des vidéos à afficher en guise de résultats de recherche
        List<Video> listeVideosAAfficher = new ArrayList<>();

        // liste des vidéos à supprimer car ne correspondant pas aux filtres de recherche
        List<Video> listeVideosASupprimer = new ArrayList<>();

        // liste qui va devenir la liste des vidéos correspondant aux filtres de recherche
        List<Video> listeVideosFiltrees = videoRepository.findAll();

        // récupération de tous les acteurs de la BDD
        List<Acteur> listeActeursBdd = acteurRepository.findAll();

        // le champ "acteur" renseigné par l'utilisateur peut contenir des fautes
        // on va donc ici rechercher quel acteur correspond à celui qu'il a voulu écrire
        Acteur acteurConsidere = null;
        if (acteurRenseigne) {
            acteurConsidere = RechercheUtil.identifierActeur(champActeur, listeActeursBdd);
        }

        // parcours de chacun des films de la BDD
        for (Video video : listeVideosFiltrees) {

            // initialisation des variables
            boolean genreFilmCorrespond = false;
            boolean acteurConsidereJoueDansFilm = false;

            // le genre correspond-il ?
            if (genreRenseigne) {
                genreFilmCorrespond = RechercheUtil.categorieExisteFilm(champGenre, video, video.getCategories());
            }

            // l'acteur joue-t-il dans ce film ?
            if (acteurRenseigne) {
                acteurConsidereJoueDansFilm = RechercheUtil.acteurJoueDansFilm(acteurConsidere, video);
            }

            // suppression du film s'il ne correspond pas à aux filtres de recherche
            if (
                (((!genreFilmCorrespond) || (!acteurConsidereJoueDansFilm)) && genreRenseigne && acteurRenseigne)
                || 
                (!genreFilmCorrespond && genreRenseigne && !acteurRenseigne)
                ||
                (!acteurConsidereJoueDansFilm && !genreRenseigne && acteurRenseigne)
            ) {
                listeVideosASupprimer.add(video);
            }

        }

        // suppression des vidéos ne correspondant pas aux filtres de recherche
        listeVideosFiltrees.removeAll(listeVideosASupprimer);

        // contenu de la recherche
        if (rechercheRenseignee) {
            listeVideosAAfficher = RechercheUtil.recupererResultatsRecherche(champRecherche, listeVideosFiltrees, 5);
        } else {
            listeVideosAAfficher = listeVideosFiltrees;
        }

        // [temporaire] affichage des variables
        System.out.println("=========================================================="); 
        System.out.println("---------------------- Films valides ---------------------");
        for (Video video : listeVideosAAfficher) {
            System.out.println(video.getTitre());
        }
        System.out.println("------------------------- Critères -----------------------");
        System.out.println("Recherche : " + champRecherche);
        System.out.println("Genre     : " + champGenre);
        System.out.println("Acteur    : " + champActeur);
        System.out.println("=========================================================="); 

        // ajout de la liste en tant qu'attribut du modèle
        model.addAttribute("listeVideosAAfficher", listeVideosAAfficher);
        listeCategoriesBdd = categorieRepository.findAll();
        model.addAttribute("listeCategoriesBdd", listeCategoriesBdd);

        return "resultats_recherche";

    }

}

/*
 * A FINIR SUR CETTE PAGE :
 *      - PROBLEMES BDD : 
 *          - "Ana de" au lieu de "Ana de Armas" --> vérifier que c'est bon une fois résolu
 *          - une catégorie "vide" pour les genres de film
 *      - améliorer l'algo de la distance de Levenshtein / en ajouter un autre en complément
 *      - faire en sorte de mettre la recherche dans l'URL
 *      - faire en sorte que la recherche depuis une autre page redirige automatiquement vers /search?q=...
 *      
 *      - BONUS : ajouter un champ demandant le nombre de résultats de recherche
 *      - BONUS : afficher des acteurs en guise de résultats
 * 
 *      - FIN : faire en sorte que la page ne s'affiche que si l'utilisateur est connecté
 *      
 * 
 */

// 