/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author justmaino
 */
@Entity
public class Personne implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String prenom;
    private List<Tache> listeTaches;
    
    //////////////////// constructeur//////////////////////////////////

    public Personne() {
    }

    public Personne(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }
    public Personne(Long id,String nom, String prenom){
        this.id=id; 
         this.nom = nom;
        this.prenom = prenom;
    }
    //fonction 
    public void addTache(Tache t){
        listeTaches.add(t);
        
    }
    public void removeTache(Tache t){
        listeTaches.remove(t);
    }
    
    
    //getter et setter 

   

    /**
     * Get the value of listeTaches
     *
     * @return the value of listeTaches
     */
    public List<Tache> getListeTaches() {
        return listeTaches;
    }
    
    
    

    /**
     * Set the value of listeTaches
     *
     * @param listeTaches new value of listeTaches
     */
    public void setListeTaches(List<Tache> listeTaches) {
        this.listeTaches = listeTaches;
    }

    /**
     * Get the value of prenom
     *
     * @return the value of prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Set the value of prenom
     *
     * @param prenom new value of prenom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    /**
     * Get the value of nom
     *
     * @return the value of nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Set the value of nom
     *
     * @param nom new value of nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personne)) {
            return false;
        }
        Personne other = (Personne) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Personne[ id=" + id + " ]";
    }
    
}
