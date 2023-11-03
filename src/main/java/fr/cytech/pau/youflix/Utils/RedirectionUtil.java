package fr.cytech.pau.youflix.Utils;

import fr.cytech.pau.youflix.Models.User;
import jakarta.servlet.http.HttpSession;

public class RedirectionUtil {

    public static String getReturnForFacade(HttpSession session, String page){
        Object object = session.getAttribute("user");
        if(object != null && object instanceof User){
            return "redirect:/";
        }
        return page;
    }

    public static String getReturnForContent(HttpSession session, String page){
        Object object = session.getAttribute("user");
        if(object == null){
            return "redirect:/connexion";
        }
        return page;
    }

    public static String getReturnForContentAdmin(HttpSession session, String page){
        if(canSeePageAdmin(session)){
            return page;
        } else {
            return "redirect:/";
        }
    }

    public static boolean canSeePageAdmin(HttpSession session){
        Object object = session.getAttribute("user");
        if(object != null && object instanceof User){
            User user = (User)object;
            if(user.getMail().equals("root@youflix.fr")){
                return true;
            }
            return false;
        }
        return false;
    }
    
}
