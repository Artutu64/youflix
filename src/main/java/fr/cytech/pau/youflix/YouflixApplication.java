package fr.cytech.pau.youflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.cytech.pau.youflix.Models.Categorie;

@SpringBootApplication
public class YouflixApplication {

	@Deprecated
	public static void main(String[] args) {
		SpringApplication.run(YouflixApplication.class, args);
		Categorie cat = new Categorie();
		cat.setNom("Test");
	}

}
