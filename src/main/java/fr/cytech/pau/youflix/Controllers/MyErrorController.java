package fr.cytech.pau.youflix.Controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

    @GetMapping(path = "/404")
    public String error(){
        return "404";
    }

    private final static String PATH = "/error";

    @RequestMapping(PATH)
    public String getErrorPath() {
        return "404";
    }

}
