package fr.cytech.pau.youflix.Utils;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class VerifsUtil {

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
    
    // vérification du code de la vidéo
    // format : 11 caractères parmi des majuscules, des minuscules, des tirets et des underscores
    public static boolean verifCodeVideo(String codeVideo) {
        Pattern pattern = Pattern.compile("[\\d\\w-]{11}");
        Matcher matcher = pattern.matcher(codeVideo);
        return matcher.matches();
    }

    // vérification de la date de sortie
    // il faut une date de la forme DD-MM-YYYY en paramètre
    public static boolean verifDateSortieVideo(String dateSortie) {
        Pattern pattern = Pattern.compile("^(0?[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(19\\d{2}|20\\d{2})$");
        Matcher matcher = pattern.matcher(dateSortie);
        return matcher.matches();
    }

}
