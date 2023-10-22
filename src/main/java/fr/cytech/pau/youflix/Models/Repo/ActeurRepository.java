package fr.cytech.pau.youflix.Models.Repo;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.cytech.pau.youflix.Models.Acteur;

@Repository
public interface ActeurRepository extends JpaRepository<Acteur, Long>{

    List<Acteur> findActorByNom(String nom);

    List<Acteur> findActorByPrenom(String prenom);
    
    List<Acteur> findActorById(Long idActeur);
    
}
