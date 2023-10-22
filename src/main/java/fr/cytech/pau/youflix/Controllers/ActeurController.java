package fr.cytech.pau.youflix.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import fr.cytech.pau.youflix.Utils.RedirectionUtil;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ActeurController {
    
    @GetMapping(path = "/acteur")
    public String acteur(HttpServletRequest request){
        return RedirectionUtil.getReturnForContent(request.getSession(), "acteur");
    }

}
