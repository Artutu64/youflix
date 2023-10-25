package fr.cytech.pau.youflix.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.cytech.pau.youflix.Models.Categorie;
import fr.cytech.pau.youflix.Models.User;
import fr.cytech.pau.youflix.Models.Video;
import fr.cytech.pau.youflix.Models.Repo.CategorieRepository;
import fr.cytech.pau.youflix.Models.Repo.VideoRepository;
import fr.cytech.pau.youflix.Utils.RandomUtil;
import fr.cytech.pau.youflix.Utils.RedirectionUtil;
import fr.cytech.pau.youflix.Utils.Models.UserUtil;
import fr.cytech.pau.youflix.Utils.Models.VideoUtil;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainPageController {

	@Autowired
	VideoRepository videoRepository;

	@Autowired
	CategorieRepository categorieRepository;

		@GetMapping(path = "/")
		public String mainPage(HttpServletRequest request, Model model){

			User user = UserUtil.getCurrentUser(request.getSession());

			if(user != null){

				List<Video> videos = videoRepository.findAll();
				
				Video[] videosRecommandees = new Video[10];
				List<Video> videosNonVues = new ArrayList<>();
				for(Video v : videos){
					videosNonVues.add(v);
				}
				
				for(int i = 0; i < videosRecommandees.length ; i++){
					videosRecommandees[i] = videosNonVues.get(RandomUtil.getRandomInt(videosNonVues.size()));
				}

				Categorie cat = UserUtil.favoriteCategorie(user);
				if(cat == null){
					List<Categorie> categories = categorieRepository.findAll();
					cat = categories.get(RandomUtil.getRandomInt(categories.size()));
				}

				Video[] videos2 = new Video[10];
				List<Video> goodCatVideos = new ArrayList<>();
				for(Video v : videos){
					if(v.getCategories().contains(cat)){
						goodCatVideos.add(v);
					}
				}
				for(int i = 0; i < videos2.length; i++){
					videos2[i] = goodCatVideos.get(RandomUtil.getRandomInt(goodCatVideos.size()));
				}

				List<Video> videosRecentes = VideoUtil.getNewVideos(videoRepository, 10);
				List<Video> videosPlusVues = VideoUtil.getVideosMostSeen(videoRepository, 10);
				List<Categorie> listeCategoriesBdd = categorieRepository.findAll();

				model.addAttribute("VideoRecommandees", videosRecommandees);
				model.addAttribute("VideoCat", videos2);
				model.addAttribute("CategorieFavorite", cat.getNom());
				model.addAttribute("VideoRecentes", videosRecentes);
				model.addAttribute("VideoMostSeen", videosPlusVues);
				model.addAttribute("listeCategoriesBdd", listeCategoriesBdd);

			}

        	return RedirectionUtil.getReturnForContent(request.getSession(), "index");
		}

}