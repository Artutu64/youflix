package fr.cytech.pau.youflix.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class ConnexionController {

    @GetMapping(path = "/connexion")
    public String connexion() {
        return "login";
    }

    @PostMapping(path = "/connexion")
    public String postConnexion(WebRequest request) {
        
        // récupération de l'adresse mail et du mot de passe
        String mail = request.getParameter("mail");
        String password = request.getParameter("password");

        System.out.println("Mail : " + mail + "\nMot de passe : " + password);

        // [to do] vérification que l'adresse mail existe bien dans la BDD

        // [to do] vérification du mot de passe
        
        // les champs sont corrects, la connexion s'est bien passée
        return "redirect:/";

    }

}