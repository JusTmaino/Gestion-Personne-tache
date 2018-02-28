/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import Entity.Personne;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import session.GestionnairePersonnes;
import session.GestionnaireTaches;

/**
 *
 * @author justmaino
 */
@Named(value = "personneMbean")
@ViewScoped
public class PersonneMbean implements Serializable {

    @EJB
    private GestionnairePersonnes gp;
    private GestionnaireTaches gt;
    private Long id;
    private int id1;
    private String nom;
    private String prenom;
    private List<Personne> personneList = new ArrayList<>();
    private String msg;

    public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
        this.id = id;
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

    public int getIdTache() {
        return idTache;
    }

    public void setIdTache(int idTache) {
        this.idTache = idTache;
    }

    public List<Personne> getPersonneList() {
        return personneList;
    }

    public void setPersonneList(List<Personne> personneList) {
        this.personneList = personneList;
    }
    private int idTache;
    private LazyDataModel <Personne> modele;
    
     public LazyDataModel<Personne> getModele() {
        return modele;
    }

    public void setModele(LazyDataModel<Personne> modele) {
        this.modele = modele;
    }

    /**
     * Creates a new instance of PersonneMbean
     */
    public PersonneMbean() {
           modele = new LazyDataModel<Personne>() {
               @Override 
            public List load(int start, int nb, String nomColonne, SortOrder ordreTri,Map map) {
               //recup comptes correspondant params recus
                System.out.println("Depart " + start + " nb " + nb );
                return gp.findRange(start, nb,nomColonne,ordreTri.toString());
            }

            @Override
            public int getRowCount() {
                return gp.count();
            }
            
            
        };

    }

    ///fonction 

    public List<Personne> getPersonne() {
        if (personneList.isEmpty()) {
            raffraichiCache();
        } else {
            System.out.println("@j'utilise le cache");
            
        }
        return personneList;
    }

    public void raffraichiCache() {
        personneList = gp.findAll();

    }

    public String creerPersonne() {
        gp.creerPersonne(nom, prenom);
        raffraichiCache();
        return "index?faces-redirect=true";
    }

    public String creerPersonneTest() {
        System.out.println("creation personne test");
        gp.creerPersonneTest();
        raffraichiCache();
        return "index?faces-redirect=true";

    }

    public String supprimerPersonne(Personne p) {
        FacesContext context = FacesContext.getCurrentInstance();
       try {
            gp.supprimerPersonneParId(p.getId());
            raffraichiCache();
            msg = "Personne supprimmée";
            context.addMessage(null, new FacesMessage("Successful", msg));

        } catch (Exception e) {
            msg = "Aucune personne n'a pour id : " + idTache;
            context.addMessage(null, new FacesMessage("Successful", msg));
        }

        return ("index?faces-redirect=true");
    }

    public String ajouterTache() {
        gp.addTache(gt.find(id1), id);
        raffraichiCache();
        return "index?faces-redirect=true";
    }

    public String updatePersonne() {
      FacesContext context = FacesContext.getCurrentInstance();
  
        try {
            
            gp.ModifierPersonneParId(id, prenom, nom);
            raffraichiCache();
            msg = "Personne modifiee";
            context.addMessage(null, new FacesMessage("Successful", msg));

        } catch (Exception e) {
            msg = "Aucune personne n'a pour id : " + id;
            context.addMessage(null, new FacesMessage("Successful", msg));
        }

        return ("index?faces-redirect=true");
    
}
}
