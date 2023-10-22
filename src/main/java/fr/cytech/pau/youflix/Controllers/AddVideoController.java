package fr.cytech.pau.youflix.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import fr.cytech.pau.youflix.Models.Acteur;
import fr.cytech.pau.youflix.Models.Categorie;
import fr.cytech.pau.youflix.Models.Repo.ActeurRepository;
import fr.cytech.pau.youflix.Models.Repo.CategorieRepository;

@Controller
public class AddVideoController {

    @Autowired
    CategorieRepository genreRepository;

    @Autowired
    ActeurRepository acteurRepository;

    @GetMapping(path = "/add-video")
    public String addVideo() {
        return "add_video";
    }

    @PostMapping(path = "/add-video")
    public String postAddVideo(WebRequest request) {
        
        // récupération des informations liées à la vidéo
        String titreVideo = request.getParameter("titre-video");
        String lienVideo = request.getParameter("lien-video");
        String descriptionVideo = request.getParameter("description-video");
        String acteurs = request.getParameter("liste-acteurs");
        String genres = request.getParameter("liste-genres");

        /*
        // vérification des acteurs de la vidéo et ajout éventuel à la BDD s'il/elle n'existe pas encore
        if (acteurs != null) {

            // récupération des différents acteurs du film en question
            String[] listeActeurs = acteurs.split(",");

            // parcours des acteurs du film
            for (int i=0; i<listeActeurs.length; i++) {

                // suppression des espaces inutiles
                listeActeurs[i] = listeActeurs[i].trim();

                // récupération du nom et du prénom de l'acteur
                String[] nomPrenom = listeActeurs[i].split("_");
                String nomActeur = nomPrenom[0];
                String prenomActeur = nomPrenom[1];

                // on regarde si l'acteur existe déjà dans la BDD (acteurExiste = true) ou non (acteurExiste = false)
                List<Acteur> acteursExistants = acteurRepository.findActorByNom(nomActeur);
                boolean acteurExiste = false;
                for (Acteur acteur : acteursExistants) {
                    if (nomActeur.equals(acteur.getNom()) && prenomActeur.equals(acteur.getPrenom())) {
                        acteurExiste = true;
                    }
                }

                // si l'acteur n'existe pas encore, on l'ajoute à la BDD
                if (!acteurExiste) {
                    Acteur acteur = new Acteur();
                    acteur.setNom(nomActeur);
                    acteur.setPrenom(prenomActeur);
                    acteurRepository.save(acteur);
                }
            }
        }
        */


        // vérification des genres de la vidéo et ajout éventuel d'un genre à la BDD s'il n'existe pas encore
        if (genres != null) {

            // récupération des différents genres du film en question
            String[] listeGenres = genres.split(",");

            // parcours des genres du film
            for (int i=0; i<listeGenres.length; i++) {

                // suppression des espaces inutiles
                listeGenres[i] = listeGenres[i].trim();

                // on regarde si le genre existe déjà dans la BDD (genreExiste = true) ou non (genreExiste = false)
                List<Categorie> genresExistants = genreRepository.findCategorieByNom(listeGenres[i]);
                boolean genreExiste = false;
                for (Categorie genre : genresExistants) {
                    if (listeGenres[i].equals(genre)) {
                        genreExiste = true;
                    }
                }

                // si le genre n'existe pas encore, on l'ajoute à la BDD
                if (!genreExiste) {
                    Categorie genre = new Categorie();
                    genre.setNom(listeGenres[i]);
                    genreRepository.save(genre);
                }
            }
        }



        /* 
        // [temporaire] affichage des données
        System.out.println("Titre de la vidéo             : " + titreVideo);
        System.out.println("Lien de la vidéo              : " + lienVideo);
        System.out.println("Description de la vidéo       : " + descriptionVideo);
        System.out.println("Liste des genres de la vidéo  : " + acteurs);
        System.out.println("Liste des acteurs de la vidéo : " + genres);
        */

        return "redirect:/";

    }

}