package fr.cytech.pau.youflix.Utils.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fr.cytech.pau.youflix.Models.Categorie;
import fr.cytech.pau.youflix.Models.Video;

public class CategorieUtil {
    
    public static List<Video> getVideosByVues(Categorie categorie, int amount){
        List<Video> videos = new ArrayList<>();
        Set<Video> films = categorie.getLinkedvideos();
        for(Video v : films) videos.add(v);
        videos.sort((a, v) -> {
            if(a.getVues().size() > v.getVues().size()) return -1;
            else return 1;
        });
        return videos.subList(0, amount);
    }

    public static List<Video> filterFilmByCat(List<Video> videos, Categorie categorie){
        List<Video> returnVideos = new ArrayList<>();
        for(Video video : videos){
            if(video.getCategories().contains(categorie)){
                returnVideos.add(video);
            }
        }
        return returnVideos;
    }

}
