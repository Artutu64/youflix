package fr.cytech.pau.youflix.Controllers;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import fr.cytech.pau.youflix.Models.Acteur;
import fr.cytech.pau.youflix.Models.Categorie;
import fr.cytech.pau.youflix.Models.Video;
import fr.cytech.pau.youflix.Models.Repo.ActeurRepository;
import fr.cytech.pau.youflix.Models.Repo.VideoRepository;
import fr.cytech.pau.youflix.Utils.RedirectionUtil;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RechercheController {

    // normaliser du texte : supprimer les accents, les majuscules, les espaces inutiles...
    public static String normaliserChaine(String input) {

        // création de la nouvelle chaîne
        String nouvelleChaine = Normalizer.normalize(input, Normalizer.Form.NFD);
        
        // suppression des accents
        nouvelleChaine = nouvelleChaine.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        
        // gestion de la casse : mise en majuscules
        nouvelleChaine = nouvelleChaine.toLowerCase();

        // suppression des espaces inutiles
        nouvelleChaine = nouvelleChaine.trim();

        // remplacement des doubles espaces par des espaces valides
        nouvelleChaine = nouvelleChaine.replaceAll("\\s+", " ");
        
        return nouvelleChaine;
    }

    // calculer la distance de Levenshtein entre deux mots
    public static int distanceLevenshtein(String mot1, String mot2) {

        // récupération des longueurs des mots
        int m = mot1.length();
        int n = mot2.length();
        
        // création de la matrice qui va stocker les distances entre chacun des sous-mots
        int[][] matrice = new int[m + 1][n + 1];
        
        // initialisation de la première colonne de la matrice avec des valeurs de 0 à m
        for (int i = 0; i <= m; i++) {
            matrice[i][0] = i;
        }

        // initialisation de la première ligne de la matrice avec des valeurs de 0 à n
        for (int j = 0; j <= n; j++) {
            matrice[0][j] = j;
        }
        
        // calcul de la distance entre les mots
        // note : charAt récupère un caractère à une position spécifique
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cout = (mot1.charAt(i - 1) != mot2.charAt(j - 1)) ? 1 : 0;
                matrice[i][j] = Math.min(Math.min(matrice[i - 1][j] + 1, matrice[i][j - 1] + 1), matrice[i - 1][j - 1] + cout);
            }
        }
        
        return matrice[m][n];
    }

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    ActeurRepository acteurRepository;
    
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
        String champRecherche = request.getParameter("contenu-recherche");
        String champGenre = request.getParameter("genre");
        String champActeur = request.getParameter("acteur");

        // contenu de la recherche
        if (champRecherche != null) {
            
            // récupération de chacun des films
            List<Video> listeVideos = videoRepository.findAll();

            // calcul des distances de Levenshtein
            HashMap<Video, Integer> mapDistancesFilms = new HashMap<>();
            for (Video video : listeVideos) {
                int dist = distanceLevenshtein(normaliserChaine(champRecherche), normaliserChaine(video.getTitre()));
                mapDistancesFilms.put(video, dist);
            }

            // récupération des résultats de recherche : cinq résultats les plus pertinents
            int n_resultats_voulus = 5;
            int n_resultats_en_cours = 0;
            int i = 0;
            List<Video> videosAGarder = new ArrayList<>();
            while ((n_resultats_en_cours <= n_resultats_voulus) && (n_resultats_en_cours < mapDistancesFilms.size())) {
                for (Video video : mapDistancesFilms.keySet()) {
                    int dist = mapDistancesFilms.get(video);
                    if (dist == i) {
                        videosAGarder.add(video);
                        System.out.println(video.getTitre());
                        n_resultats_en_cours++;
                    }
                }
                i++;
            }

            // liste des vidéos qui vont devoir être supprimées des vidéos à garder (application des filtres)
            List<Video> videosASupprimer = new ArrayList<>();

            // acteur
            if (champActeur != null) {

                // récupération de chacun des acteurs
                List<Acteur> listeActeurs = acteurRepository.findAll();

                // calcul des distances de Levenshtein
                HashMap<Acteur, Integer> mapDistancesActeurs = new HashMap<>();
                for (Acteur acteur : listeActeurs) {
                    int dist = distanceLevenshtein(normaliserChaine(champActeur), normaliserChaine(acteur.getPrenom() + " " + acteur.getNom()));
                    mapDistancesActeurs.put(acteur, dist);
                }

                // récupération du résultat de recherche le plus pertinent
                int distMin = Integer.MAX_VALUE;
                Acteur acteurConsidere = null;
                for (Acteur acteur : mapDistancesActeurs.keySet()) {
                    int dist = mapDistancesActeurs.get(acteur);
                    if (dist < distMin) {
                        distMin = dist;
                        acteurConsidere = acteur;
                    }
                }

                // on ne garde que les films dans lesquels l'acteur considéré joue
                Set<Acteur> acteursFilm;
                for (Video video : videosAGarder) {
                    acteursFilm = video.getJoueDans();
                    boolean acteurJoueDansFilm = false;
                    for (Acteur acteur : acteursFilm) {
                        if ((acteurConsidere.getNom().equals(acteur.getNom())) && (acteurConsidere.getPrenom().equals(acteur.getPrenom()))) {
                            acteurJoueDansFilm = true;
                        }
                    }
                    if (!acteurJoueDansFilm) {
                        videosASupprimer.add(video);
                    }
                }
            }

            // genre
            if (champGenre != null) {

                // on ne garde que les films contenant cette catégorie
                for (Video video : videosAGarder) {
                    boolean catExiste = false;
                    for (Categorie cat : video.getCategories()) {
                        if (champGenre.equals(cat.getNom())) {
                            catExiste = true;
                        }
                    }
                    if (!catExiste) {
                        videosASupprimer.add(video);
                    }
                }

            }

            // suppression des vidéos ne répondant pas aux critères des filtres de recherches
            videosAGarder.removeAll(videosASupprimer);

        }
        
        return "redirect:/search";

    }

}
