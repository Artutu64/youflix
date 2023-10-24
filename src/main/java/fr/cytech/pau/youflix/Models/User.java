package fr.cytech.pau.youflix.Models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Utilisateur")
public class User implements Serializable {

    @Id
    @Column(name = "userId", unique = true)
    private Long userId;

    private String nom;

    private String prenom;

    private String mail;

    private String password;

    @ManyToMany
    @JoinTable(name = "Likes", 
        joinColumns = {@JoinColumn(name = "UID", referencedColumnName = "userId")},
        inverseJoinColumns = {@JoinColumn(name = "VCODE", referencedColumnName = "codeVideo")})
    private Set<Video> likes = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "Vues", 
        joinColumns = {@JoinColumn(name = "UID", referencedColumnName = "userId")},
        inverseJoinColumns = {@JoinColumn(name = "VCODE", referencedColumnName = "codeVideo")})
    private Set<Video> vues = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "Favoris", 
        joinColumns = {@JoinColumn(name = "UID", referencedColumnName = "userId")},
        inverseJoinColumns = {@JoinColumn(name = "VCODE", referencedColumnName = "codeVideo")})
    private Set<Video> favoris = new HashSet<>();

    @Override
    public boolean equals(Object o){
        if(o instanceof User){
            User user = (User)o;
            return user.userId == this.userId || (this.mail == user.mail && this.password == user.password);
        }
        return false;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Video> getLikes() {
        return likes;
    }
    public void setLikes(Set<Video> likes) {
        this.likes = likes;
    }

    public Set<Video> getFavoris() {
        return favoris;
    }
    public void setFavoris(Set<Video> favoris) {
        this.favoris = favoris;
    }

    public Set<Video> getVues() {
        return vues;
    }
    public void setVues(Set<Video> vues) {
        this.vues = vues;
    }

    
}
