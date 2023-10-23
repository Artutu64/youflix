package fr.cytech.pau.youflix.Utils.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fr.cytech.pau.youflix.Models.Acteur;
import fr.cytech.pau.youflix.Models.Video;

public class ActeurUtil {

    public static List<Video> getVideosByVues(Acteur acteur, int amount){
        List<Video> videos = new ArrayList<>();
        Set<Video> films = acteur.getJoueDans();
        for(Video v : films) videos.add(v);
        videos.sort((a, v) -> {
            if(a.getVues().size() > v.getVues().size()) return -1;
            else return 1;
        });
        return videos.subList(0, amount);
    }
    
}
