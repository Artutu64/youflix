package fr.cytech.pau.youflix.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class InscriptionController {
    
    @GetMapping(path = "/inscription")
    public String inscription(){
        return "register";
    }

    @PostMapping(path = "/inscription")
    public String postInscription(WebRequest request) {
        
        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String mail = request.getParameter("mail");
        String password = request.getParameter("password");

        System.out.println("Prénom       : " + prenom);
        System.out.println("Nom          : " + nom);
        System.out.println("Mail         : " + mail);
        System.out.println("Mot de passe : " + password);

        return "redirect:/";

    }

}

