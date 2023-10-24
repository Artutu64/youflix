package fr.cytech.pau.youflix.Utils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import fr.cytech.pau.youflix.Models.Acteur;
import fr.cytech.pau.youflix.Models.Categorie;
import fr.cytech.pau.youflix.Models.Video;

public class RechercheUtil {
 
    // normaliser du texte : supprimer les accents, les majuscules, les espaces inutiles...
    public static String normaliserChaine(String input) {

        // création de la nouvelle chaîne
        String nouvelleChaine = Normalizer.normalize(input, Normalizer.Form.NFD);
        
        // suppression des accents
        nouvelleChaine = nouvelleChaine.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        
        // gestion de la casse : mise en minuscules
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

    // récupérer l'acteur le plus proche d'une chaîne de caractères
    public static Acteur identifierActeur(String champActeur, List<Acteur> listeActeurs) {

        // initialisation
        Acteur acteurConsidere = null;

        // calcul des distances de Levenshtein entre l'acteur renseigné et chacun des acteurs de la BDD
        HashMap<Acteur, Integer> mapDistancesActeurs = new HashMap<>();
        for (Acteur acteur : listeActeurs) {
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

        return (acteurConsidere);

    }

    // savoir si un film admet une certaine catégorie
    public static boolean categorieExisteFilm(String champGenre, Video video, Set<Categorie> set) {
        boolean genreFilmCorrespond = false;
        for (Categorie categorie : video.getCategories()) {
            if (champGenre.equals(categorie.getNom())) {
                genreFilmCorrespond = true;
                break;
            }
        }
        return (genreFilmCorrespond);
    }

    // savoir si un acteur joue dans un film ou non
    public static boolean acteurJoueDansFilm(Acteur acteurConsidere, Video video) {
        boolean acteurConsidereJoueDansFilm = false;
        for (Acteur acteur : video.getJoueDans()) {
            if ((acteurConsidere.getNom().equals(acteur.getNom())) && (acteurConsidere.getPrenom().equals(acteur.getPrenom()))) {
                acteurConsidereJoueDansFilm = true;
                break;
            }
        }
        return (acteurConsidereJoueDansFilm);
    }

    // récupération des résultats de recherche les plus pertinents
    public static List<Video> recupererResultatsRecherche(String champRecherche, List<Video> listeVideosFiltrees, int nbrResultatsVoulus) {

        // liste des vidéos à afficher en guise de résultats de recherche
        List<Video> listeVideosAAfficher = new ArrayList<>();

        // calcul des distances de Levenshtein entre la recherche et chacun des films
        HashMap<Video, Integer> mapDistancesFilms = new HashMap<>();
        for (Video video : listeVideosFiltrees) {
            int dist = distanceLevenshtein(normaliserChaine(champRecherche), normaliserChaine(video.getTitre()));
            mapDistancesFilms.put(video, dist);
        }

        // récupération des nbrResultatsVoulus résultats les plus pertinents
        int nbrResultatsActuel = 0;
        int i = 0;
        while ((nbrResultatsActuel <= nbrResultatsVoulus) && (nbrResultatsActuel < mapDistancesFilms.size())) {
            for (Video video : mapDistancesFilms.keySet()) {
                int dist = mapDistancesFilms.get(video);
                if (dist == i) {
                    listeVideosAAfficher.add(video);
                    nbrResultatsActuel++;
                    if (nbrResultatsActuel >= nbrResultatsVoulus) {
                        break;
                    }
                }
            }
            if (nbrResultatsActuel >= nbrResultatsVoulus) {
                break;
            }
            i++;
        }

        return (listeVideosAAfficher);

    }

}
