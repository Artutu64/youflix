package fr.cytech.pau.youflix.Models.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.cytech.pau.youflix.Models.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    
    List<Video> findVideoByTitre(String titre);

    List<Video> findVideoByCodeVideo(String codeVideo);

}
