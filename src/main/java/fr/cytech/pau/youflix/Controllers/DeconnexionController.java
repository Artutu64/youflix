package fr.cytech.pau.youflix.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class DeconnexionController {
    
    @GetMapping("/deconnexion")
    public String disconnect(HttpServletRequest request){
        request.getSession().setAttribute("user", null);
        return "redirect:/connexion";
    }

}
