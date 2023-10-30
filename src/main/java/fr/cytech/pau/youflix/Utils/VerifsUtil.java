package fr.cytech.pau.youflix.Utils;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class VerifsUtil {
    
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
