package fr.cytech.pau.youflix.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddVideoController {
    
    @GetMapping(path = "/add-video")
    public String addVideo(){
        return "add_video";
    }

}
