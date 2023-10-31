package fr.cytech.pau.youflix.Models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Acteur")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Acteur implements Serializable{
    
    @Column(unique = true)
    @Id
    @JsonProperty("idActeur")
    private Long idActeur;

    @JsonProperty("nom")
    private String nom;

    @JsonProperty("prenom")
    private String prenom;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ActeurJoueDansVideo", 
        joinColumns = {@JoinColumn(name = "AID", referencedColumnName = "idActeur")},
        inverseJoinColumns = {@JoinColumn(name = "VCODE", referencedColumnName = "codeVideo")})
    @JsonIgnore
    private Set<Video> joueDans = new HashSet<>();

    @Override
    public boolean equals(Object o){
        if(o instanceof Acteur){
            Acteur acteur = (Acteur)o;
            return acteur.idActeur == this.idActeur || (this.nom == acteur.nom && this.prenom == acteur.prenom);
        }
        return false;
    }

    @JsonProperty("idActeur")
    public Long getIdActeur() {
        return idActeur;
    }
    public void setIdActeur(Long idActeur) {
        this.idActeur = idActeur;
    }

    public String getFullName(){
        return getPrenom() + " " + getNom();
    }

    @JsonProperty("nom")
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    @JsonProperty("prenom")
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @JsonIgnore
    public Set<Video> getJoueDans() {
        return joueDans;
    }
    public void setJoueDans(Set<Video> joueDans) {
        this.joueDans = joueDans;
    }

}

