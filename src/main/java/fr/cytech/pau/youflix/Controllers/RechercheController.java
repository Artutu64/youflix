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

        // récupération les champs potentiellement remplis : recherche, genre (catégorie) et acteur
        String champGenre = request.getParameter("genre");
        String champActeur = request.getParameter("acteur");
        String champRecherche = request.getParameter("contenu-recherche");







        

        // récupération de tous les films de la base de données
        List<Video> listeVideosBdd = videoRepository.findAll();

        // récupération de chacun des acteurs
        List<Acteur> listeActeursBdd = acteurRepository.findAll();

        // liste des vidéos qui vont devoir être supprimées (recherche + application des filtres)
        List<Video> videosASupprimer = new ArrayList<>();

        // liste des vidéos qui vont correspondre aux filtres de recherches
        List<Video> videosFiltrees = new ArrayList<>();
        for (Video video : listeVideosBdd) {
            videosFiltrees.add(video);
        }

        // liste des vidéos qui vont être affichées pour l'utilisateur dans les résultats de recherche
        List<Video> videosAGarder = new ArrayList<>();

        // genre
        if ((champGenre != null) && (!champGenre.equals(""))) {

            // on ne garde que les films contenant cette catégorie
            for (Video video : listeVideosBdd) {
                boolean catExiste = false;
                for (Categorie cat : video.getCategories()) {
                    if (champGenre.equals(cat.getNom())) {
                        catExiste = true;
                        break;
                    }
                }
                if (!catExiste) {
                    videosASupprimer.add(video);
                }
            }

        }

        // acteur
        if ((champActeur != null) && (!champActeur.equals(""))) {

            // calcul des distances de Levenshtein
            HashMap<Acteur, Integer> mapDistancesActeurs = new HashMap<>();
            for (Acteur acteur : listeActeursBdd) {
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
            for (Video video : listeVideosBdd) {
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

        // suppression des films ne respectant pas les conditions des filtres de recherche
        videosFiltrees.removeAll(videosASupprimer);

        // contenu de la recherche
        if (champRecherche != null) {

            // calcul des distances de Levenshtein entre la recherche et chacun des films
            HashMap<Video, Integer> mapDistancesFilms = new HashMap<>();
            for (Video video : videosFiltrees) {
                int dist = distanceLevenshtein(normaliserChaine(champRecherche), normaliserChaine(video.getTitre()));
                mapDistancesFilms.put(video, dist);
            }

            // récupération des cinq résultats les plus pertinents
            int n_resultats_voulus = 5;
            int n_resultats_en_cours = 0;
            int i = 0;
            while ((n_resultats_en_cours <= n_resultats_voulus) && (n_resultats_en_cours < mapDistancesFilms.size())) {
                for (Video video : mapDistancesFilms.keySet()) {
                    int dist = mapDistancesFilms.get(video);
                    if (dist == i) {
                        videosAGarder.add(video);
                        n_resultats_en_cours++;
                        if (n_resultats_en_cours >= n_resultats_voulus) {
                            break;
                        }
                    }
                }
                if (n_resultats_en_cours >= n_resultats_voulus) {
                    break;
                }
                i++;
            }

        } else {
            videosAGarder = videosFiltrees;
        }

        System.out.println("=========================================================="); 
        System.out.println("---------------------- Films valides ---------------------");
        for (Video video : videosAGarder) {
            System.out.println(video.getTitre());
        }
        System.out.println("------------------------- Critères -----------------------");
        System.out.println("Recherche : " + champRecherche);
        System.out.println("Genre     : " + champGenre);
        System.out.println("Acteur    : " + champActeur);
        System.out.println("=========================================================="); 

        return "redirect:/search";

    }

}

/*
 * A FINIR SUR CETTE PAGE :
 *      - PROBLEME BDD : "Ana de" au lieu de "Ana de Armas" --> vérifier que c'est bon une fois résolu
 *      - faire en sorte qu'on puisse mettre recherche + filtres
 *      - améliorer le code (le découper en fonction notamment)
 *      - faire en sorte d'afficher les bonnes vidéos plutôt que celles de Squeezie...
 *      - améliorer l'algo de la distance de Levenshtein / en ajouter un autre en complément
 *      - genres : mettre ceux de la BDD
 *      - faire en sorte de mettre la recherche dans l'URL
 *      - faire en sorte que la recherche depuis une autre page redirige automatiquement vers /search?q=...
 *      - BONUS : ajouter un champ demandant le nombre de résultats de recherche
 */

// 