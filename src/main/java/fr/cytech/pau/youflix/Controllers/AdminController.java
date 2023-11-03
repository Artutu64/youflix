package fr.cytech.pau.youflix.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.cytech.pau.youflix.Models.Acteur;
import fr.cytech.pau.youflix.Models.Categorie;
import fr.cytech.pau.youflix.Models.Video;
import fr.cytech.pau.youflix.Models.Repo.ActeurRepository;
import fr.cytech.pau.youflix.Models.Repo.CategorieRepository;
import fr.cytech.pau.youflix.Models.Repo.VideoRepository;

@Controller
public class AdminController {

    @Autowired
    CategorieRepository categorieRepository;

    @Autowired
    ActeurRepository acteurRepository;

    @Autowired
    VideoRepository videoRepository;
    
    @GetMapping(path = "/admin")
    public String admin(Model model){

        List<Categorie> listeCategoriesBdd = categorieRepository.findAll();
        model.addAttribute("listeCategoriesBdd", listeCategoriesBdd);

        List<Acteur> acteursBdd = acteurRepository.findAll();
        acteursBdd.sort((a, b) -> {
            String sa = a.getNom() + " " + a.getPrenom();
            String sb = b.getNom() + " " + b.getPrenom();
            return sa.compareTo(sb);
        });
        model.addAttribute("acteurs", acteursBdd);

        List<Video> videos = videoRepository.findAll();
        videos.sort((v1, v2) -> {
            return v1.getTitre().compareTo(v2.getTitre());
        });
        model.addAttribute("videos", videos);

        return "admin";
    }

}