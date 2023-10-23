package fr.cytech.pau.youflix.Models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "Video")
public class Video  implements Serializable{
    
    @Column(name = "codeVideo", unique = true)
    @Id
    private String codeVideo;

    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Temporal(TemporalType.DATE)
    private Date dateSortie;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Likes", 
        joinColumns = {@JoinColumn(name = "VCODE", referencedColumnName = "codeVideo")},
        inverseJoinColumns = {@JoinColumn(name = "UID", referencedColumnName = "userId")})
    private Set<User> likes = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Vues", 
        joinColumns = {@JoinColumn(name = "VCODE", referencedColumnName = "codeVideo")},
        inverseJoinColumns = {@JoinColumn(name = "UID", referencedColumnName = "userId")})
    private Set<User> vues = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Favoris", 
        joinColumns = {@JoinColumn(name = "VCODE", referencedColumnName = "codeVideo")},
        inverseJoinColumns = {@JoinColumn(name = "UID", referencedColumnName = "userId")})
    private Set<User> favoris = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ActeurJoueDansVideo", 
        joinColumns = {@JoinColumn(name = "VCODE", referencedColumnName = "codeVideo")},
        inverseJoinColumns = {@JoinColumn(name = "AID", referencedColumnName = "idActeur")})
    private Set<Acteur> joueDans = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "LienCatVideo", 
        joinColumns = {@JoinColumn(name = "VCODE", referencedColumnName = "codeVideo")},
        inverseJoinColumns = {@JoinColumn(name = "CNOM", referencedColumnName = "nom")})
    private Set<Categorie> categories = new HashSet<>();

    @Override
    public boolean equals(Object o){
        if(o instanceof Video){
            Video video = (Video)o;
            return video.codeVideo == this.codeVideo && this.titre == video.titre;
        }
        return false;
    }

    public String getCodeVideo() {
        return codeVideo;
    }
    public void setCodeVideo(String codeVideo) {
        this.codeVideo = codeVideo;
    }

    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateSortie() {
        return dateSortie;
    }
    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    public Set<User> getLikes() {
        return likes;
    }
    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    public Set<User> getFavoris() {
        return favoris;
    }
    public void setFavoris(Set<User> favoris) {
        this.favoris = favoris;
    }

    public Set<User> getVues() {
        return vues;
    }
    public void setVues(Set<User> vues) {
        this.vues = vues;
    }

    public Set<Categorie> getCategories() {
        return categories;
    }
    public void setCategories(Set<Categorie> categories) {
        this.categories = categories;
    }

    public Set<Acteur> getJoueDans() {
        return joueDans;
    }
    public void setJoueDans(Set<Acteur> joueDans) {
        this.joueDans = joueDans;
    }
}
