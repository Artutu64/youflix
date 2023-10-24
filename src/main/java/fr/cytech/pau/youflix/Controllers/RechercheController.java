package fr.cytech.pau.youflix.Controllers;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
import fr.cytech.pau.youflix.Models.Repo.VideoRepository;
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
    public String search(HttpServletRequest request) {
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

            // calcul des distances de Levenshtein entre l'acteur renseigné et chacun des acteurs de la BDD
            HashMap<Acteur, Integer> mapDistancesActeurs = new HashMap<>();
            for (Acteur acteur : listeActeursBdd) {
                int dist = distanceLevenshtein(normaliserChaine(champActeur), normaliserChaine(acteur.getPrenom() + " " + acteur.getNom()));
                mapDistancesActeurs.put(acteur, dist);
            }

            // récupération du résultat de recherche le plus pertinent
            int distMin = Integer.MAX_VALUE;
            for (Acteur acteur : mapDistancesActeurs.keySet()) {
                int dist = mapDistancesActeurs.get(acteur);
                if (dist < distMin) {
                    distMin = dist;
                    acteurConsidere = acteur;
                }
            }
        }

        // parcours de chacun des films de la BDD
        for (Video video : listeVideosFiltrees) {

            // initialisation des variables
            boolean genreFilmCorrespond = false;
            boolean acteurConsidereJoueDansFilm = false;
            Set<Acteur> acteursFilm = video.getJoueDans();

            // le genre correspond-il ?
            if (genreRenseigne) {
                for (Categorie cat : video.getCategories()) {
                    if (champGenre.equals(cat.getNom())) {
                        genreFilmCorrespond = true;
                        break;
                    }
                }
            }

            // l'acteur joue-t-il dans ce film ?
            if (acteurRenseigne) {
                for (Acteur acteur : acteursFilm) {
                    if ((acteurConsidere.getNom().equals(acteur.getNom())) && (acteurConsidere.getPrenom().equals(acteur.getPrenom()))) {
                        acteurConsidereJoueDansFilm = true;
                        break;
                    }
                }
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
        // note : la recherche étant requise dans le formulaire HTML, c'est à priori toujours le cas
        if (champRecherche != null) {

            // calcul des distances de Levenshtein entre la recherche et chacun des films
            HashMap<Video, Integer> mapDistancesFilms = new HashMap<>();
            for (Video video : listeVideosFiltrees) {
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
                        listeVideosAAfficher.add(video);
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

        return "resultats_recherche";

    }

}

/*
 * A FINIR SUR CETTE PAGE :
 *      - PROBLEME BDD : "Ana de" au lieu de "Ana de Armas" --> vérifier que c'est bon une fois résolu
 *      - faire en sorte de ne pouvoir utiliser que les filtres de recherche, sans la barre de recherche
 *      - faire en sorte d'afficher les bonnes vidéos plutôt que celles de Squeezie...
 *      - améliorer l'algo de la distance de Levenshtein / en ajouter un autre en complément
 *      - genres : mettre ceux de la BDD
 *      - faire en sorte de mettre la recherche dans l'URL
 *      - faire en sorte que la recherche depuis une autre page redirige automatiquement vers /search?q=...
 *      - BONUS : ajouter un champ demandant le nombre de résultats de recherche
 *      - BONUS : afficher des acteurs
 * 
 *      - faire en sorte que la page ne s'affiche que si l'utilisateur est connecté
 *      
 */

// 