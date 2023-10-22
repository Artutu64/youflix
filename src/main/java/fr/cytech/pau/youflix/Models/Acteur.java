package fr.cytech.pau.youflix.Models;

import java.io.Serializable;
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

@Entity
@Table(name = "Acteur")
public class Acteur implements Serializable{
    
    @Column(unique = true)
    @Id
    private Long idActeur;

    private String nom;

    private String prenom;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ActeurJoueDansVideo", 
        joinColumns = {@JoinColumn(name = "AID", referencedColumnName = "idActeur")},
        inverseJoinColumns = {@JoinColumn(name = "VCODE", referencedColumnName = "codeVideo")})
    private Set<Video> joueDans = new HashSet<>();

    @Override
    public boolean equals(Object o){
        if(o instanceof Acteur){
            Acteur acteur = (Acteur)o;
            return acteur.idActeur == this.idActeur || (this.nom == acteur.nom && this.prenom == acteur.prenom);
        }
        return false;
    }

    public Long getIdActeur() {
        return idActeur;
    }
    public void setIdActeur(Long idActeur) {
        this.idActeur = idActeur;
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

    public Set<Video> getJoueDans() {
        return joueDans;
    }
    public void setJoueDans(Set<Video> joueDans) {
        this.joueDans = joueDans;
    }

}
