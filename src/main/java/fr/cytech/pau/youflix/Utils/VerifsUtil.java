package fr.cytech.pau.youflix.Utils;

import java.util.regex.Pattern;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;

public class VerifsUtil {
    
    // vérifier le code de la vidéo
    // format : 11 caractères parmi des majuscules, des minuscules, des tirets et des underscores
    public static boolean verifCodeVideo(String codeVideo) {
        Pattern pattern = Pattern.compile("[\\d\\w-]{11}");
        Matcher matcher = pattern.matcher(codeVideo);
        return matcher.matches();
    }

    // vérifier la date de sortie de la vidéo
    // il faut une date de la forme DD-MM-YYYY en paramètre
    public static boolean verifDateSortieVideo(String dateSortie) {

        // première vérification : format textuel de la date (regex)
        // on vérifie que la date est bien de la forme DD-MM-YYYY et que les nombres semblent être corrects
        // par exemple, il ne faut pas avoir entré "42-23-1841"
        String regex = "^(0?[1-9]|[1-2][0-9]|3[0-1])-(0?[1-9]|1[0-2])-(19\\d{2}|20\\d{2})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dateSortie);
        if (!matcher.matches()) {
            return false;
        }

        // seconde vérification : format temporel de la date
        // la date doit être comprise entre le 01-01-1900 et la date du jour
        // elle doit également exister (pas de "31-02-1992" par exemple)
        try {
            DateTimeFormatter formatteur = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate dateSortieLocalDate = LocalDate.parse(dateSortie, formatteur);
            LocalDate dateMinimale = LocalDate.of(1900, 1, 1);
            LocalDate dateDuJour = LocalDate.now();
            return (dateSortieLocalDate.isAfter(dateMinimale) && dateSortieLocalDate.isBefore(dateDuJour));
        }
        catch (DateTimeException e) {
            return false;
        }

    }

    // vérifier que le champ "acteurs" renseigné pour la vidéo est correct
    // pour être correct, le paramètre en entrée doit être de la forme "nom_prenom, nom_prenom, nom_prenom"
    public static boolean verifChampActeur(String champActeur) {
        String regex = "^[^,_]+_[^,_]+(\s?,\s?[^,_]+_[^,_]+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(champActeur);
        return matcher.matches();
    }

}
