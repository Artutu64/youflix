package fr.cytech.pau.youflix.Models.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.cytech.pau.youflix.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    List<User> findUserById(Long userId);

    List<User> findUserByMail(String mail);

    List<User> findUserByNom(String nom);

    List<User> findUserByPrenom(String prenom);

}
