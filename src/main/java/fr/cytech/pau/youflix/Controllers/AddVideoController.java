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
import fr.cytech.pau.youflix.Utils.VerifsUtil;
import fr.cytech.pau.youflix.Utils.StringUtil;

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
        String codeVideo = request.getParameter("lien-video");
        String dateSortieVideo = request.getParameter("date-sortie-video");
        String descriptionVideo = request.getParameter("description-video");
        String acteurs = request.getParameter("liste-acteurs");
        String genres = request.getParameter("liste-genres");

        // pré-traitement des chaînes de caractères
        dateSortieVideo = dateSortieVideo.replace("/", "-");
        acteurs = acteurs.replace(";", ",");
        genres = genres.replace(";", ",");

        // vérification de certaines informations
        boolean codeVideoCorrect = VerifsUtil.verifCodeVideo(codeVideo);
        boolean dateSortieVideoCorrecte = VerifsUtil.verifDateSortieVideo(dateSortieVideo);
        boolean champActeursCorrect = VerifsUtil.verifChampActeur(acteurs);

        System.out.println("====================================================");
        System.out.println("Code vidéo correct      : " + codeVideoCorrect);
        System.out.println("Date de sortie correcte : " + dateSortieVideoCorrecte);
        System.out.println("Champ acteurs correct   : " + champActeursCorrect);

        // si au moins une information est incorrecte, on redirige l'utilisateur vers une autre page
        // (il ne faut pas qu'il puisse ajouter la vidéo à la base de données)
        if (!codeVideoCorrect || !dateSortieVideoCorrecte || !champActeursCorrect) {
            return "404";
        }

        // stockage des acteurs dans un tableau
        String[] listeActeursSplit = acteurs.split(",");
        String[][] listeActeurs = new String[listeActeursSplit.length][2];
        String[] nomPrenom;
        for (int i = 0; i < listeActeursSplit.length; i++) {
            nomPrenom = listeActeursSplit[i].split("_");
            if (nomPrenom.length == 2) {
                listeActeurs[i][0] = StringUtil.conversionTitleCase(nomPrenom[0]);
                listeActeurs[i][1] = StringUtil.conversionTitleCase(nomPrenom[1]);
            }
        }

        // stockage des genres dans un tableau
        String[] listeGenres = genres.split(",");
        for (int i = 0; i < listeGenres.length; i++) {
            listeGenres[i] = StringUtil.conversionTitleCase(listeGenres[i]);
        }

        // cast de la chaîne de caractères en date
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
        Date date = formatDate.parse(dateSortieVideo);
        Date dateSortieVideoSQL = new Date(date.getTime());

        // vérification des acteurs liés à la vidéo
        Set<Acteur> setActeursFilm = new HashSet<>();
        for (int i = 0; i < listeActeurs.length; i++) {

            // récupération des informations de l'acteur
            String nomActeur = listeActeurs[i][0];
            String prenomActeur = listeActeurs[i][1];

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

        // vérification des genres de la vidéo et ajout éventuel d'un genre à la BDD s'il n'existe pas encore
        Set<Categorie> setGenresFilm = new HashSet<>();
        for (int i = 0; i < listeGenres.length; i++) {

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

        // ajout de la vidéo à la BDD
        Video video = new Video();
        video.setCodeVideo(codeVideo);
        video.setDescription(descriptionVideo);
        video.setTitre(titreVideo);
        video.setCategories(setGenresFilm);
        video.setDateSortie(dateSortieVideoSQL); 
        video.setJoueDans(setActeursFilm);
        videoRepository.save(video);

        return "redirect:/add-video";

    }

}
