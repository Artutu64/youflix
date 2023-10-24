package fr.cytech.pau.youflix.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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