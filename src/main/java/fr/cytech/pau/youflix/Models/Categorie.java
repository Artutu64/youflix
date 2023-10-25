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

@Entity
@Table(name = "Categorie")
public class Categorie implements Serializable {

    @Column(unique = true)
    @Id
    private String nom;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "LienCatVideo", 
        joinColumns = {@JoinColumn(name = "CNOM", referencedColumnName = "nom")},
        inverseJoinColumns = {@JoinColumn(name = "VCODE", referencedColumnName = "codeVideo")})
    private Set<Video> linkedvideos = new HashSet<>();
    

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Categorie){
            Categorie cat = (Categorie)o;
            return cat.getNom() == this.getNom();
        }
        return false;
    }

    public String getSearch(){
        return "/search?categorie=" + getNom();
    }

    public Set<Video> getLinkedvideos() {
        return linkedvideos;
    }
    public void setLinkedvideos(Set<Video> linkedvideos) {
        this.linkedvideos = linkedvideos;
    }
}
