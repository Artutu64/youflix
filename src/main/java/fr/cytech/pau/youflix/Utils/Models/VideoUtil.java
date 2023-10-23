package fr.cytech.pau.youflix.Utils.Models;

import java.util.List;

import fr.cytech.pau.youflix.Models.Video;
import fr.cytech.pau.youflix.Models.Repo.VideoRepository;

public class VideoUtil {

    public static List<Video> getRecommandedVideos(VideoRepository repo, int amount){
        return repo.findAll().subList(0, amount);
    }

    public static List<Video> getNewVideos(VideoRepository repo, int amount){
        List<Video> videos = repo.findAll();
        videos.sort((Video arg0, Video arg1) -> {
            if(arg0.getDateSortie().after(arg1.getDateSortie())) return 1;
            else return -1;
        });
        return videos.subList(0, amount);
    }

    public static List<Video> getVideosMostSeen(VideoRepository repo, int amount){
        List<Video> videos = repo.findAll();
        videos.sort((Video arg0, Video arg1) -> {
            if(arg0.getVues().size() > arg1.getVues().size()) return 1;
            else return -1;
        });
        return videos.subList(0, amount);
    }
    
}