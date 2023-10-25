package fr.cytech.pau.youflix.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.cytech.pau.youflix.Models.Categorie;
import fr.cytech.pau.youflix.Models.Repo.CategorieRepository;
import fr.cytech.pau.youflix.Utils.RedirectionUtil;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class FavorisController {

    @Autowired
    CategorieRepository categorieRepository;
    
    @GetMapping(path = "/favoris")
    public String favoris(HttpServletRequest request, Model model){

        List<Categorie> listeCategoriesBdd = categorieRepository.findAll();
        model.addAttribute("listeCategoriesBdd", listeCategoriesBdd);
        return RedirectionUtil.getReturnForContent(request.getSession(), "favoris");
    }

}
