package fr.cytech.pau.youflix.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VideoController {
    
    @GetMapping(path = "/video")
    public String video(){
        return "video";
    }

}