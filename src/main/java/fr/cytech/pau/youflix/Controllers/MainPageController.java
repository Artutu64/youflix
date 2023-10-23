package fr.cytech.pau.youflix.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.cytech.pau.youflix.Models.User;
import fr.cytech.pau.youflix.Models.Video;
import fr.cytech.pau.youflix.Models.Repo.VideoRepository;
import fr.cytech.pau.youflix.Utils.RandomUtil;
import fr.cytech.pau.youflix.Utils.RedirectionUtil;
import fr.cytech.pau.youflix.Utils.Models.UserUtil;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainPageController {

	@Autowired
	VideoRepository videoRepository;

		@GetMapping(path = "/")
		public String mainPage(HttpServletRequest request, Model model){

			User user = UserUtil.getCurrentUser(request.getSession());

			if(user != null){

				System.out.println(user.getMail());

				List<Video> videos = videoRepository.findAll();
				
				Video[] videosRecommandees = new Video[10];
				List<Video> videosNonVues = new ArrayList<>();
				for(Video v : videos){
					videosNonVues.add(v);
				}
				
				for(int i = 0; i < videosRecommandees.length ; i++){
					videosRecommandees[i] = videosNonVues.get(RandomUtil.getRandomInt(videosNonVues.size()));
				}

				model.addAttribute("VideoRecommandees", videosRecommandees);

			}

        	return RedirectionUtil.getReturnForContent(request.getSession(), "index");
		}

}