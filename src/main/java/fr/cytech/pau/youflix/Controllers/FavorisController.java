package fr.cytech.pau.youflix.Controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class FavorisController {

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    CategorieRepository categorieRepository;
    
    @GetMapping(path = "/favoris")
    public String favoris(HttpServletRequest request, Model model){

        User user = UserUtil.getCurrentUser(request.getSession());

		if(user != null){

            Set<Video> favoris = new HashSet<>();
            ArrayList<String> favFunc = new ArrayList<>();
            List<Video> videos = videoRepository.findAll();
            for(Video video : videos){
                for(User usr : video.getFavoris()){
                    if(usr.getMail().equals(user.getMail())){
                        favoris.add(video);
                        favFunc.add(video.getCodeVideo());
                    }
                }
            }
            model.addAttribute("favFunc", favFunc);
            model.addAttribute("favoris", favoris);
        }

        List<Categorie> listeCategoriesBdd = categorieRepository.findAll();
        model.addAttribute("listeCategoriesBdd", listeCategoriesBdd);
        return RedirectionUtil.getReturnForContent(request.getSession(), "favoris");
    }

}
