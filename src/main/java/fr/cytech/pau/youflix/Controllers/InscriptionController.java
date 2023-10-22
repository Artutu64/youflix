package fr.cytech.pau.youflix.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import fr.cytech.pau.youflix.Models.User;
import fr.cytech.pau.youflix.Models.Repo.UserRepository;
import fr.cytech.pau.youflix.Utils.RandomUtil;
import fr.cytech.pau.youflix.Utils.RedirectionUtil;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class InscriptionController {
    
    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/inscription")
    public String inscription(HttpServletRequest request){
        List<User> users = userRepository.findUserByMail("root@youfix.fr");
        if(users.size() == 0){
            User user = new User();
            user.setUserId(RandomUtil.getRandomId());
            user.setMail("root@youfix.fr");
            user.setPassword("password");
            user.setPrenom("root");
            user.setNom("root");
            userRepository.save(user);
        }
        return RedirectionUtil.getReturnForFacade(request.getSession(), "register");
    }

    @PostMapping(path = "/inscription")
    public String postInscription(WebRequest request) {
        
        // récupération de l'adresse mail et du mot de passe
        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String mail = request.getParameter("mail");
        String password = request.getParameter("password");

        if(prenom != null && nom != null && mail != null && password != null){
            List<User> users = userRepository.findUserByMail(mail);
            boolean exists = false;
            for(User user : users){
                if(user.getPassword().equals(password)){
                    exists = true;
                }
            }
            if(exists == false){
                User user = new User();
                user.setMail(mail);
                user.setPrenom(prenom);
                user.setNom(nom);
                user.setPassword(password);
                user.setUserId(RandomUtil.getRandomId());
                userRepository.save(user);
                return "redirect:/";
            }
        }

        return "register";

    }

}

