package fr.cytech.pau.youflix.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import fr.cytech.pau.youflix.Models.Acteur;
import fr.cytech.pau.youflix.Models.Categorie;
import fr.cytech.pau.youflix.Models.Video;

public class RechercheUtil {

    // récupérer l'acteur le plus proche d'une chaîne de caractères
    public static Acteur identifierActeur(String champActeur, List<Acteur> listeActeurs) {

        // initialisation
        Acteur acteurConsidere = null;

        // calcul des distances de Levenshtein entre l'acteur renseigné et chacun des acteurs de la BDD
        HashMap<Acteur, Integer> mapDistancesActeurs = new HashMap<>();
        for (Acteur acteur : listeActeurs) {
            int dist = StringUtil.distanceLevenshtein(
                StringUtil.normaliserChaine(champActeur), 
                StringUtil.normaliserChaine(acteur.getPrenom() + " " + acteur.getNom())
            );
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

        return acteurConsidere;

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
        return genreFilmCorrespond;
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
        return acteurConsidereJoueDansFilm;
    }

    // récupération des résultats de recherche les plus pertinents
    public static List<Video> recupererResultatsRecherche(String champRecherche, List<Video> listeVideosFiltrees, int nbrResultatsVoulus) {

        // liste des vidéos à afficher en guise de résultats de recherche
        List<Video> listeVideosAAfficher = new ArrayList<>();

        // calcul des distances de Levenshtein entre la recherche et chacun des films
        HashMap<Video, Integer> mapDistancesFilms = new HashMap<>();

        // parcours des vidéos filtrées
        for (Video video : listeVideosFiltrees) {

            // longueur du titre du film
            int longueurTitrefilm = video.getTitre().length();

            // tableau qui va contenir les valeurs des distances de Levenshtein entre la recherche et chacune des
            // sous-chaînes du titre du film
            // par exemple, tabLevenshteinSsChaines[i][j] contiendra la distance de Levenshtein entre la recherche et
            // la sous-chaîne du titre du film allant du caractère i (inclus) au caractère j (exclus)
            int[][] tabLevenshteinSsChaines = new int[longueurTitrefilm][longueurTitrefilm];
            for (int i = 0; i < longueurTitrefilm; i++) {
                for (int j = 0; j < longueurTitrefilm; j++) {
                    tabLevenshteinSsChaines[i][j] = Integer.MAX_VALUE;
                }
            }

            // parcours des sous-chaînes du titre du film
            for (int i = 0; i < longueurTitrefilm; i++) {
                for (int j = i; j < longueurTitrefilm; j++) {
                    String ssChaine = video.getTitre().substring(i, j+1);
                    tabLevenshteinSsChaines[i][j] = StringUtil.distanceLevenshtein(
                        StringUtil.normaliserChaine(champRecherche), 
                        StringUtil.normaliserChaine(ssChaine)
                    );
                }
            }

            // recherche du minimum dans le tableau
            int minimum = tabLevenshteinSsChaines[0][0];
            for (int i = 0; i < tabLevenshteinSsChaines.length; i++) {
                if (Arrays.stream(tabLevenshteinSsChaines[i]).min().getAsInt() < minimum) {
                    minimum = Arrays.stream(tabLevenshteinSsChaines[i]).min().getAsInt();
                }
            }

            mapDistancesFilms.put(video, minimum);

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

        return listeVideosAAfficher;

    }

}
