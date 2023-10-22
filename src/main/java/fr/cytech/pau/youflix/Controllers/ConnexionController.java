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
import jakarta.servlet.http.HttpSession;

@Controller
public class ConnexionController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/connexion")
    public String connexion(HttpServletRequest request) {
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
        return RedirectionUtil.getReturnForFacade(request.getSession(), "login");
    }

    @PostMapping(path = "/connexion")
    public String postConnexion(WebRequest request, HttpServletRequest request2) {
        
        String mail = request.getParameter("mail");
        String password = request.getParameter("password");

        if(mail != null && password != null){
            List<User> users = userRepository.findAll();
            User user = null;
            for(User u : users){
                if(u.getPassword().equals(password)){
                    user = u;
                }
            }
            System.out.println(users.size() + " " + mail + " " + password);
            if(user != null){
                HttpSession session = request2.getSession();
                session.setAttribute("user", user);
                return "redirect:/";
            }
        }
        return "login";

    }

}