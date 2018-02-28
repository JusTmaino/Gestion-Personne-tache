/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author justmaino
 */
@Entity
public class Tache implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 
    private String nomTache;
    @ManyToOne
    private Personne contributeur;
     @Enumerated(EnumType.STRING)
    private Status status;

   
    
    //*******************constructeur******************

    public Tache() {
    }

    public Tache( String nomTache) {
        System.err.println("@@Je suis ici");
        this.nomTache = nomTache;
        this.status = status.non_attribuée;
    }
    
    public Tache( String nomTache, Personne contributeur, Status status) {
        this.nomTache = nomTache;
        this.contributeur = contributeur;
        this.status=status;
    }
    

    /**
     * Get the value of contributeur
     *
     * @return the value of contributeur
     */
    public Personne getContributeur() {
        return contributeur;
    }

    /**
     * Set the value of contributeur
     *
     * @param contributeur new value of contributeur
     */
    public void setContributeur(Personne contributeur) {
        this.contributeur = contributeur;
    }


    /**
     * Get the value of nomTache
     *
     * @return the value of nomTache
     */
    public String getNomTache() {
        return nomTache;
    }

    /**
     * Set the value of nomTache
     *
     * @param nomTache new value of nomTache
     */
    public void setNomTache(String nomTache) {
        this.nomTache = nomTache;
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
        if (!(object instanceof Tache)) {
            return false;
        }
        Tache other = (Tache) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.t\u00e2che[ id=" + id + " ]";
    }
     public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public void removeContributeur(){
        setContributeur(null);
        setStatus(Status.non_attribuée);
    }
    
}
