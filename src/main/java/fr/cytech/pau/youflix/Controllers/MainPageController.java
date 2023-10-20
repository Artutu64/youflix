package fr.cytech.pau.youflix.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

		@GetMapping(path = "/")
		public String mainPage() {
			return "index";
		}

}