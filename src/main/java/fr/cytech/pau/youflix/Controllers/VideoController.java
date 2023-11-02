package fr.cytech.pau.youflix.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class VideoController {

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    CategorieRepository categorieRepository;

    @GetMapping(path = "/like-remove")
    public String removeLike(HttpServletRequest request, HttpServletResponse response){
        User user = UserUtil.getCurrentUser(request.getSession());

		if(user != null){
            String codeVideo = request.getParameter("codeVideo");
            List<Video> videos = videoRepository.findAll();
            Video video = null;
            for(Video v : videos){
                if(v.getCodeVideo().equals(codeVideo)){
                    video = v;
                }
            }
            if(video == null){
                response.setStatus(404);
            } else {
                boolean hasLike = false;
                for(User usr : video.getLikes()){
                    if(usr.getMail().equals(user.getMail())){
                        hasLike = true;
                    }
                }
                if(hasLike){
                    Set<User> uUsers = new HashSet<>();
                    for(User usr : video.getLikes()){
                        if(!usr.getMail().equals(user.getMail())){
                            uUsers.add(usr);
                        }
                    }
                    video.setLikes(uUsers);
                    video.getLikes().remove(user);
                    videoRepository.save(video);
                    response.setStatus(200);
                } else {
                    response.setStatus(400);
                }
            }
        } else {
            response.setStatus(400);
        }
        return "404";
    }

    @GetMapping(path = "/fav-remove")
    public String removeFav(HttpServletRequest request, HttpServletResponse response){
        User user = UserUtil.getCurrentUser(request.getSession());

		if(user != null){
            String codeVideo = request.getParameter("codeVideo");
            List<Video> videos = videoRepository.findAll();
            Video video = null;
            for(Video v : videos){
                if(v.getCodeVideo().equals(codeVideo)){
                    video = v;
                }
            }
            if(video == null){
                response.setStatus(404);
            } else {
                boolean hasFav = false;
                for(User usr : video.getFavoris()){
                    if(usr.getMail().equals(user.getMail())){
                        hasFav = true;
                    }
                }
                if(hasFav){
                    Set<User> uUsers = new HashSet<>();
                    for(User usr : video.getFavoris()){
                        if(!usr.getMail().equals(user.getMail())){
                            uUsers.add(usr);
                        }
                    }
                    video.setFavoris(uUsers);
                    video.getFavoris().remove(user);
                    videoRepository.save(video);
                    response.setStatus(200);
                } else {
                    response.setStatus(400);
                }
            }
        } else {
            response.setStatus(400);
        }
        return "404";
    }

    @GetMapping(path = "/like-add")
    public String addLike(HttpServletRequest request, HttpServletResponse response){
        User user = UserUtil.getCurrentUser(request.getSession());

		if(user != null){
            String codeVideo = request.getParameter("codeVideo");
            List<Video> videos = videoRepository.findAll();
            Video video = null;
            for(Video v : videos){
                if(v.getCodeVideo().equals(codeVideo)){
                    video = v;
                }
            }
            if(video == null){
                response.setStatus(404);
            } else {
                boolean hasLike = false;
                for(User usr : video.getLikes()){
                    if(usr.getMail().equals(user.getMail())){
                        hasLike = true;
                    }
                }
                if(hasLike){
                    response.setStatus(400);
                } else {
                    video.getLikes().add(user);
                    videoRepository.save(video);
                    response.setStatus(200);
                }
            }
        } else {
            response.setStatus(400);
        }
        return "404";
    }

    @GetMapping(path = "/fav-add")
    public String addFav(HttpServletRequest request, HttpServletResponse response){
        User user = UserUtil.getCurrentUser(request.getSession());

		if(user != null){
            String codeVideo = request.getParameter("codeVideo");
            List<Video> videos = videoRepository.findAll();
            Video video = null;
            for(Video v : videos){
                if(v.getCodeVideo().equals(codeVideo)){
                    video = v;
                }
            }
            if(video == null){
                response.setStatus(404);
            } else {
                boolean hasFav = false;
                for(User usr : video.getFavoris()){
                    if(usr.getMail().equals(user.getMail())){
                        hasFav = true;
                    }
                }
                if(hasFav){
                    response.setStatus(400);
                } else {
                    video.getFavoris().add(user);
                    videoRepository.save(video);
                    response.setStatus(200);
                }
            }
        } else {
            response.setStatus(400);
        }
        return "404";
    }

    @GetMapping(path = "/video")
    public String video(HttpServletRequest request, Model model){

        User user = UserUtil.getCurrentUser(request.getSession());

		if(user != null){
            String code = request.getParameter("q");
            if(code == null){
                code = "";
            }
            Video video = null;
            List<Video> videos = videoRepository.findAll();
            for(Video v : videos){
                if(v.getCodeVideo().equals(code)){
                    video = v;
                }
            }
            if(video == null){
                return "404";
            }

            video.getVues().add(user);
            video.incrementVues();
            videoRepository.save(video);
				
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
					Video vvideo = catVideos.get(RandomUtil.getRandomInt(catVideos.size()));
					catVideos.remove(vvideo);
					if(j < videosRecommandees.length){
						videosRecommandees[j] = vvideo;
					}
					j++;
				}
			}
				
			for(int i = j; i < videosRecommandees.length ; i++){
				videosRecommandees[i] = videosNonVues.get(RandomUtil.getRandomInt(videosNonVues.size()));
			}

            boolean hasLikedVideo = false;
            boolean hasFavVideo = false;
            for(User usr : video.getLikes()){
                if(usr.getMail().equals(user.getMail())){
                    hasLikedVideo = true;
                }
            }
            for(User usr : video.getFavoris()){
                if(usr.getMail().equals(user.getMail())){
                    hasFavVideo = true;
                }
            }

            String styleButtonLike = (hasLikedVideo) ? " background-color: rgb(18, 132, 155);" : " background-color: white;";
            String styleButtonFav = (hasFavVideo) ? " background-color: rgb(193, 162, 38);" : " background-color: white;";

            model.addAttribute("Video", video);
            model.addAttribute("styleButtonLike", styleButtonLike);
            model.addAttribute("styleButtonFav", styleButtonFav);
            model.addAttribute("videosRecommandees", videosRecommandees);
        }
        List<Categorie> listeCategoriesBdd = categorieRepository.findAll();
        model.addAttribute("listeCategoriesBdd", listeCategoriesBdd);
        return RedirectionUtil.getReturnForContent(request.getSession(), "video");
    }

}