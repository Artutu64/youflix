package fr.cytech.pau.youflix.Utils;

import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;

public class VerifsUtil {

    // vérifier qu'une chaîne de caractères matche bien avec un pattern regex (true) ou non (false)
    public static boolean chaineCorrespondRegex(String chaine, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(chaine);
        return matcher.matches();
    }

    // vérifier le titre ou la description d'une vidéo (ne doit juste pas être "null")
    public static boolean verifTitreDescription(String chaine) {
        return ((chaine != null) && (!chaine.equals("")));
    }
    
    // vérifier le code de la vidéo
    // format : 11 caractères parmi des majuscules, des minuscules, des tirets et des underscores
    public static boolean verifCodeVideo(String codeVideo) {
        return chaineCorrespondRegex(codeVideo, "[\\d\\w-]{11}");
    }

    // vérifier la date de sortie de la vidéo
    // il faut une date de la forme DD-MM-YYYY en paramètre
    public static boolean verifDateSortieVideo(String dateSortie) {

        // première vérification : format textuel de la date (regex)
        // on vérifie que la date est bien de la forme DD-MM-YYYY et que les nombres semblent être corrects
        // par exemple, il ne faut pas avoir entré "42-23-1841"
        boolean formatDateOK = chaineCorrespondRegex(dateSortie, "^(0?[1-9]|[1-2][0-9]|3[0-1])-(0?[1-9]|1[0-2])-(19\\d{2}|20\\d{2})$");
        if (!formatDateOK) {
            return false;
        }

        // seconde vérification : la date doit exister (pas de "31-02-1992" par exemple)
        String[] partiesDate = dateSortie.split("-");
        int jour = Integer.parseInt(partiesDate[0]);
        int mois = Integer.parseInt(partiesDate[1]);
        int annee = Integer.parseInt(partiesDate[2]);
        boolean anneeBissextile = (annee % 4 == 0);
        int nbrJoursMois;
        switch (mois) {
            case 2 :
                if (anneeBissextile) {
                    nbrJoursMois = 29;
                } else {
                    nbrJoursMois = 28;
                }
                break;
            case 4 :
                nbrJoursMois = 30;
                break;
            case 6 :
                nbrJoursMois = 30;
                break;
            case 9 :
                nbrJoursMois = 30;
                break;
            case 11 :
                nbrJoursMois = 30;
                break;
            default :
                nbrJoursMois = 31;
        }
        if (jour > nbrJoursMois) {
            return false;
        }

        // troisième vérification : la date doit être comprise entre le 1er janvier 1900 et la date du jour
        DateTimeFormatter formatteur = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dateSortieLocalDate = LocalDate.parse(dateSortie, formatteur);
        LocalDate dateMinimale = LocalDate.of(1900, 1, 1);
        LocalDate dateDuJour = LocalDate.now();
        return (dateSortieLocalDate.isAfter(dateMinimale) && dateSortieLocalDate.isBefore(dateDuJour));
        
    }

    // vérifier que le champ "acteurs" renseigné pour la vidéo est correct
    // pour être correct, le paramètre en entrée doit être de la forme "nom_prenom, nom_prenom, nom_prenom"
    public static boolean verifChampActeurs(String champActeur) {
        return chaineCorrespondRegex(champActeur, "^[^,_]+_[^,_]+(\\s*,\\s*[^,_]+_[^,_]+)*$");
    }

    // vérifier que le champ "genres" renseigné pour la vidéo est correct
    // pour être correct, le paramètre en entrée doit être de la forme "genre, genre, genre"
    public static boolean verifChampGenres(String champGenre) {
        return chaineCorrespondRegex(champGenre, "^[^,]+(\\s*,\\s*[^,]+)*$");
    }

    // découper une chaîne de la forme "nom1_prenom1, nom2_prenom2, nom3_prenom3" 
    // pour stocker les noms/prénoms des acteurs dans un tableau
    public static String[][] creerTableauActeurs(String champActeurs) {

        // division de la chaîne en fonction des acteurs
        String[] listeActeursSplit = champActeurs.split(",");

        // création du tableau final qui va accueillir leurs nom/prénom
        String[][] listeActeurs = new String[listeActeursSplit.length][2];

        // ajout de leurs nom/prénom dans le tableau, pour chacun d'entre eux
        String[] nomPrenom;
        for (int i = 0; i < listeActeursSplit.length; i++) {
            nomPrenom = listeActeursSplit[i].split("_");
            if (nomPrenom.length == 2) {
                listeActeurs[i][0] = StringUtil.conversionTitleCase(nomPrenom[0]);
                listeActeurs[i][1] = StringUtil.conversionTitleCase(nomPrenom[1]);
            }
        }

        return listeActeurs;
    }

    // découper une chaîne de la forme "categorie1, categorie2, categorie3" 
    // pour stocker les catégories dans un tableau
    public static String[] creerTableauGenres(String champGenres) {

        // division de la chaîne en fonction des catégories
        String[] listeGenres = champGenres.split(",");

        // parcours des catégories
        for (int i = 0; i < listeGenres.length; i++) {
            listeGenres[i] = StringUtil.conversionTitleCase(listeGenres[i]);
        }

        return listeGenres;
    }

}
