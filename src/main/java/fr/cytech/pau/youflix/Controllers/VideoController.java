package fr.cytech.pau.youflix.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.cytech.pau.youflix.Models.Categorie;
import fr.cytech.pau.youflix.Models.User;
import fr.cytech.pau.youflix.Models.Video;
import fr.cytech.pau.youflix.Models.Repo.CategorieRepository;
import fr.cytech.pau.youflix.Models.Repo.VideoRepository;
import fr.cytech.pau.youflix.Utils.RedirectionUtil;
import fr.cytech.pau.youflix.Utils.Models.UserUtil;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class VideoController {

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    CategorieRepository categorieRepository;
    
    @GetMapping(path = "/video")
    public String video(HttpServletRequest request, Model model){

        User user = UserUtil.getCurrentUser(request.getSession());

		if(user != null){
            String code = request.getParameter("q");
            if(code == null){
                code = "";
            }
            Video video = null;
            List<Video> videos = videoRepository.findAll();
            for(Video v : videos){
                if(v.getCodeVideo().equals(code)){
                    video = v;
                }
            }
            if(video == null){
                return "404";
            }
            model.addAttribute("Video", video);
        }
        List<Categorie> listeCategoriesBdd = categorieRepository.findAll();
        model.addAttribute("listeCategoriesBdd", listeCategoriesBdd);
        return RedirectionUtil.getReturnForContent(request.getSession(), "video");
    }

}