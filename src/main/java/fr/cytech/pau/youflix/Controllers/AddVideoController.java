package fr.cytech.pau.youflix.Controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.HashSet;

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
import fr.cytech.pau.youflix.Utils.RandomUtil;

@Controller
public class AddVideoController {

    @Autowired
    CategorieRepository genreRepository;

    @Autowired
    ActeurRepository acteurRepository;

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    CategorieRepository categorieRepository;
    
    @GetMapping(path = "/add-video")
    public String addVideo(Model model){

        List<Categorie> listeCategoriesBdd = categorieRepository.findAll();
        model.addAttribute("listeCategoriesBdd", listeCategoriesBdd);
        return "add_video";
    }

    @PostMapping(path = "/add-video")
    public String postAddVideo(WebRequest request) throws ParseException {
        
        // récupération des informations liées à la vidéo
        String titreVideo = request.getParameter("titre-video");
        String lienVideo = request.getParameter("lien-video");
        String dateSortieVideo = request.getParameter("date-sortie-video");
        String descriptionVideo = request.getParameter("description-video");
        String acteurs = request.getParameter("liste-acteurs");
        String genres = request.getParameter("liste-genres");

        // set qui va contenir tous les acteurs associés au film
        Set<Acteur> setActeursFilm = new HashSet<>();

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
                        setActeursFilm.add(acteur);
                    }
                }

                // si l'acteur n'existe pas encore, on l'ajoute à la BDD
                if (!acteurExiste) {
                    Acteur acteur = new Acteur();
                    acteur.setIdActeur(RandomUtil.getRandomId());
                    acteur.setNom(nomActeur);
                    acteur.setPrenom(prenomActeur);
                    acteurRepository.save(acteur);
                    setActeursFilm.add(acteur);
                }
            }
        }

        // set qui va contenir tous les genres associés au film
        Set<Categorie> setGenresFilm = new HashSet<>();
        
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
                for (Categorie genre : genresExistants) { // faire plutôt un "if genresExistants == null" ?
                    if (listeGenres[i].equals(genre.getNom())) {
                        genreExiste = true;
                        setGenresFilm.add(genre);
                    }
                }

                // si le genre n'existe pas encore, on l'ajoute à la BDD
                if (!genreExiste) {
                    Categorie genre = new Categorie();
                    genre.setNom(listeGenres[i]);
                    genreRepository.save(genre);
                    setGenresFilm.add(genre);
                }

            }
        }

        // cast de la chaîne de caractères en date
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatDate.parse(dateSortieVideo);
        Date dateSortieVideoSQL = new Date(date.getTime());

        // ajout de la vidéo à la BDD
        Video video = new Video();
        video.setCodeVideo(lienVideo);
        video.setDescription(descriptionVideo);
        video.setTitre(titreVideo);
        video.setCategories(setGenresFilm);
        video.setDateSortie(dateSortieVideoSQL); 
        video.setJoueDans(setActeursFilm);
        videoRepository.save(video);

        // date de sortie --> champ créé sur la page mais du coup ça dépasse

        return "redirect:/";

    }

}

