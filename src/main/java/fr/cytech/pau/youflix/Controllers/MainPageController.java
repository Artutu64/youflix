package fr.cytech.pau.youflix.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

				Map<String, ArrayList<Video>> stats = new HashMap<>();
				Map<String, ArrayList<Video>> videoSorted = new HashMap<>();
				for(Categorie cat : categorieRepository.findAll()){
					stats.put(cat.getNom(), new ArrayList<>());
					videoSorted.put(cat.getNom(), new ArrayList<>());
				}
				for(Video v : videos){
					boolean aVu = v.getVues().contains(user);
					for(User usr : v.getVues()){
						if(usr.getMail().equalsIgnoreCase(user.getMail())){
							aVu = true;
						}
					}
					for(Categorie cat : v.getCategories()){
						if(aVu){
							stats.get(cat.getNom()).add(v);
						}
						videoSorted.get(cat.getNom()).add(v);
					}
				}
				int N = 0;
				for(ArrayList<Video> videoss : stats.values()){
					N += videoss.size();
				}
				Map<String, Integer> countVideoByCat = new HashMap<>();
				for(String cat : stats.keySet()){
					int nombre = stats.get(cat).size();
					double ratio = (((double) nombre)/((double) N))*10;
					int k = (int) ratio;
					if(k > 0){
						countVideoByCat.putIfAbsent(cat, k);
					}
				}
				int j = 0;
				for(String cat : countVideoByCat.keySet()){
					ArrayList<Video> catVideos = videoSorted.get(cat);
					int k = 0;
					while(k < countVideoByCat.get(cat) && !catVideos.isEmpty()){
						k++;
						Video video = catVideos.get(RandomUtil.getRandomInt(catVideos.size()));
						catVideos.remove(video);
						if(j < videosRecommandees.length){
							videosRecommandees[j] = video;
						}
						j++;
					}
				}
				
				for(int i = j; i < videosRecommandees.length ; i++){
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
					for(Categorie c : v.getCategories()){
						if(c.getNom().equalsIgnoreCase(cat.getNom())){
							goodCatVideos.add(v);
						}
					}
				}
				int p = 0;
				while(p < videos2.length && !goodCatVideos.isEmpty()){
					Video gvideo = goodCatVideos.get(RandomUtil.getRandomInt(goodCatVideos.size()));
					goodCatVideos.remove(gvideo);
					videos2[p] = gvideo;
					p++;
				}
				for(int i = p; i < videos2.length; i++){
					videos2[i] = videos.get(RandomUtil.getRandomInt(videos.size()));
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