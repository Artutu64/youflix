package fr.cytech.pau.youflix.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import fr.cytech.pau.youflix.Utils.RedirectionUtil;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class FavorisController {
    
    @GetMapping(path = "/favoris")
    public String favoris(HttpServletRequest request){
        return RedirectionUtil.getReturnForContent(request.getSession(), "favoris");
    }

}
