package fr.cytech.pau.youflix.Utils;

import java.text.Normalizer;

public class StringUtil {
    
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
        // - matrice[i-1][j] + 1      : ajout d'un caractère
        // - matrice[i][j-1] + 1      : suppression d'un caractère
        // - matrice[i-1][j-1] + cout : remplacement d'un caractère
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cout = (mot1.charAt(i-1) != mot2.charAt(j-1)) ? 1 : 0;
                matrice[i][j] = Math.min(Math.min(matrice[i-1][j] + 1, matrice[i][j-1] + 1), matrice[i-1][j-1] + cout);
            }
        }
        
        return matrice[m][n];
    }

    // mettre une chaîne de caractères au format "title case" (une majuscule à chaque début de mot)
    // exemple : "exemple DE Chaîne" --> "Exemple De Chaîne"
    public static String conversionTitleCase(String chaine) {

        // nouvelle chaîne, au format "title case"
        StringBuilder nouvelleChaine = new StringBuilder();

        // liste des mots de la chaîne (séparation en fonction des espaces ou des tirets)
        // note : tirets car "jean-marie" --> "Jean-Marie" et pas "Jean-marie"
        String[] listeMots = chaine.split("\\s+|-");

        // parcours de chacun des mots
        // on met la première lettre en majuscule et les autres en minuscule pour chacune d'eux
        // on ajoute aussi un espace à la fin
        for (String mot : listeMots) {
            if (!mot.isEmpty()) {
                nouvelleChaine.append(mot.substring(0, 1).toUpperCase());
                nouvelleChaine.append(mot.substring(1).toLowerCase());
                nouvelleChaine.append(" ");
            }
        }

        // nouvelle chaîne finale : conversion du StringBuilder en String + suppression des espaces inutiles
        return nouvelleChaine.toString().trim();
    }

}
