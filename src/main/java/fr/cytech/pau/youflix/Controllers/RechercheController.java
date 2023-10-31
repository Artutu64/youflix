package fr.cytech.pau.youflix.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.cytech.pau.youflix.Models.Acteur;
import fr.cytech.pau.youflix.Models.Categorie;
import fr.cytech.pau.youflix.Models.Video;
import fr.cytech.pau.youflix.Models.Repo.ActeurRepository;
import fr.cytech.pau.youflix.Models.Repo.CategorieRepository;
import fr.cytech.pau.youflix.Models.Repo.VideoRepository;
import fr.cytech.pau.youflix.Utils.RechercheUtil;
import fr.cytech.pau.youflix.Utils.RedirectionUtil;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RechercheController {

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    ActeurRepository acteurRepository;

    @Autowired
    CategorieRepository categorieRepository;

    @GetMapping(path = "/search")
    public String search(HttpServletRequest request, Model model) {

        // récupération les champs potentiellement remplis : recherche, genre (catégorie) et acteur
        String champRecherche = request.getParameter("q");
        String champGenre = request.getParameter("categorie");
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

        // récupération de toutes les vidéos de la BDD
        List<Categorie> listeCategoriesBdd = categorieRepository.findAll();

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

        // ajout des principaux attributs du modèle
        model.addAttribute("listeVideosAAfficher", listeVideosAAfficher);
        model.addAttribute("champRecherche", champRecherche);
        model.addAttribute("champGenre", champGenre);
        if (acteurRenseigne) {
            model.addAttribute("champActeur", acteurConsidere.getPrenom() + " " + acteurConsidere.getNom());
        }

        // ajout de la base de données des genres pour afficher les menus déroulants correctement
        model.addAttribute("listeCategoriesBdd", listeCategoriesBdd);

        // return RedirectionUtil.getReturnForContent(request.getSession(), "resultats_recherche");
        return "resultats_recherche";
    }

    // test, à modifier
    // source : https://suntargets.com/send-from-javascript-fetch-and-reply-from-java-springboot/
    @RequestMapping(value = "/myTest", method = RequestMethod.POST)
    @ResponseBody
    public List<Acteur> myTest(@RequestBody String received_json_data){
        JSONObject jsonObject = new JSONObject();
        List<Acteur> reply_data = acteurRepository.findAll();
        reply_data.setField1("data_1");
        reply_data.setField2("data_2");

    return reply_data;
    
    }

}



