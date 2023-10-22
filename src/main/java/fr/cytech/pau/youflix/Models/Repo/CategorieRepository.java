package fr.cytech.pau.youflix.Models.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.cytech.pau.youflix.Models.Categorie;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long>{

    List<Categorie> findCategorieByName(String nom);

}
