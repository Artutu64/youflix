package fr.cytech.pau.youflix.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import fr.cytech.pau.youflix.Models.Acteur;
import fr.cytech.pau.youflix.Models.Video;
import fr.cytech.pau.youflix.Models.Repo.UserRepository;
import fr.cytech.pau.youflix.Models.Repo.VideoRepository;
import fr.cytech.pau.youflix.Utils.RedirectionUtil;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class VideoController {

    @Autowired
    VideoRepository videoRepository;
    
    @GetMapping(path = "/video")
    public String video(HttpServletRequest request){
        return RedirectionUtil.getReturnForContent(request.getSession(), "video");
    }

}