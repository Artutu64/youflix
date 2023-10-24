package fr.cytech.pau.youflix.Utils.Models;

import java.util.HashMap;
import java.util.Map;

import fr.cytech.pau.youflix.Models.Categorie;
import fr.cytech.pau.youflix.Models.User;
import fr.cytech.pau.youflix.Models.Video;
import jakarta.servlet.http.HttpSession;

public class UserUtil {

        public static Categorie favoriteCategorie(User user){
            Map<Categorie, Integer> countCat = new HashMap<>();
            for(Video video : user.getFavoris()){
                for(Categorie categorie : video.getCategories()){
                    if(countCat.containsKey(categorie)){
                        int value = countCat.get(categorie) + 1;
                        countCat.replace(categorie, value);
                    } else {
                        countCat.put(categorie, 1);
                    }
                }
            }
            Categorie categorie = null;
            int value = -1;
            for(Categorie cat : countCat.keySet()){
                int v = countCat.get(categorie);
                if(v > value){
                    value = v;
                    categorie = cat;
                }
            }
            return categorie;
        }

    public static User getCurrentUser(HttpSession session){
        Object uuser = session.getAttribute("user");
        if(uuser == null){
            return null;
        } else {
            return (User) uuser;
        }
    }
    
}
