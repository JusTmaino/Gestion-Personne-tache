/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import Entity.Status;
import Entity.Tache;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.DragDropEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import session.GestionnaireTaches;
/**
 *
 * @author justmaino
 */
@Named(value = "tacheMbean")
@ViewScoped
public class tacheMbean implements Serializable {

    @EJB
    private GestionnaireTaches gt;
    private List<Tache> tacheList = new ArrayList<>();



    private long idPersonne;
    private String nomTache;
    private String msg;
    private long idTache;
    private String status;
    
    private LazyDataModel<Tache> modele;
    
    private LazyDataModel<Tache> modeleEncours;
    private LazyDataModel<Tache> modeleNonAttribue;
    private LazyDataModel<Tache> modeleComplete;
    
    private Tache tacheDrag = null;

    public LazyDataModel<Tache> getModeleEncours() {
        return modeleEncours;
    }

    public void setModeleEncours(LazyDataModel<Tache> modeleEncours) {
        this.modeleEncours = modeleEncours;
    }

    public LazyDataModel<Tache> getModeleNonAttribue() {
        return modeleNonAttribue;
    }

    public void setModeleNonAttribue(LazyDataModel<Tache> modeleNonAttribue) {
        this.modeleNonAttribue = modeleNonAttribue;
    }

    public LazyDataModel<Tache> getModeleComplete() {
        return modeleComplete;
    }

    public void setModeleComplete(LazyDataModel<Tache> modeleComplete) {
        this.modeleComplete = modeleComplete;
    }
    
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getIdTache() {
        return idTache;
    }

    public void setIdTache(long idTache) {
        this.idTache = idTache;
    }

    public List<Tache> getTacheList() {
        if (tacheList.isEmpty()) {
            raffraichitCache();
        } else {
            System.out.println("@j'utilise le cache");

        }

        return tacheList;

    }

    public void setTacheList(List<Tache> tacheList) {
        this.tacheList = tacheList;
    }

    public long getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(long idPersonne) {
        this.idPersonne = idPersonne;
    }

    public String getNomTache() {
        return nomTache;
    }

    public void setNomTache(String nomTache) {
        this.nomTache = nomTache;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    
    
    public LazyDataModel<Tache> getModele() {
        return modele;
    }
    /**
     * Creates a new instance of tacheMbean
     */
    public tacheMbean() {
        
        modele = new LazyDataModel<Tache>() {

            @Override
            public List load(int start, int nb, String nomColonne, SortOrder ordreTri,Map map) {
               //recup comptes correspondant params recus
                System.out.println("Depart " + start + " nb " + nb );
                return gt.findRange(start, nb,nomColonne,ordreTri.toString());
            }
            @Override
            public int getRowCount() {
                return gt.count();
            }
        };
        //////////un lazy data modele par type de status
        modeleNonAttribue = new LazyDataModel<Tache>() {

            @Override
            public List load(int start, int nb, String nomColonne, SortOrder ordreTri,Map map) {
               //recup comptes correspondant params recus
                System.out.println("Depart " + start + " nb " + nb );
                return gt.findRangeNonAttribue(start, nb,nomColonne,ordreTri.toString());
            }
            @Override
            public int getRowCount() {
                return gt.count();
            }
        };
        
        modeleEncours = new LazyDataModel<Tache>() {

            @Override
            public List load(int start, int nb, String nomColonne, SortOrder ordreTri,Map map) {
               //recup comptes correspondant params recus
                System.out.println("Depart " + start + " nb " + nb );
                return gt.findRangeEnCours(start, nb,nomColonne,ordreTri.toString());
            }
            @Override
            public int getRowCount() {
                return gt.count();
            }
        };
        
        modeleComplete = new LazyDataModel<Tache>() {

            @Override
            public List load(int start, int nb, String nomColonne, SortOrder ordreTri,Map map) {
               //recup comptes correspondant params recus
                System.out.println("Depart " + start + " nb " + nb );
                return gt.findRangeComplete(start, nb,nomColonne,ordreTri.toString());
            }
            @Override
            public int getRowCount() {
                return gt.count();
            }
        };

    }
    ///fonction 

    public void raffraichitCache() {
        tacheList = gt.findAll();
       
    }

    public List<Tache> getTache() {
        return gt.findAll();
    }

    public String creerTacheTest() {
        System.out.println("@@creation tache test");
        gt.creerTacheTest();
        return "index?faces-redirect=true";

    }

    public String creerTache() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            gt.creerNouvelleTache(idPersonne, nomTache);
            raffraichitCache();
            msg = "Tache créée";
            context.addMessage(null, new FacesMessage("Successful", msg));
        } catch (Exception e) {
            msg = "Personne n'a pour id : " + idPersonne;
            context.addMessage(null, new FacesMessage("Successful", msg));
        }

        return ("index?faces-redirect=true");

    }

    public String supprimerTache() {
         
        FacesContext context = FacesContext.getCurrentInstance();
        
        try {
            gt.supprimerTacheParId(idTache);
           // raffraichitCache();
            msg = "Tache supprimmée";
            context.addMessage(null, new FacesMessage("Successful", msg));

        } catch (Exception e) {
            msg = "Aucune tache n'a pour id : " + idTache;
            context.addMessage(null, new FacesMessage("Successful", msg));
        }

        return ("index?faces-redirect=true");
    }
    
    
        public String supprimerTache(Tache t) {
        FacesContext context = FacesContext.getCurrentInstance();
           
        try {
            gt.supprimerTacheParId(t.getId());
         //   raffraichitCache();
            msg = "Tache supprimmée";
            context.addMessage(null, new FacesMessage("Successful", msg));

        } catch (Exception e) {
            msg = "Aucune tache n'a pour id : " + t.getId();
            context.addMessage(null, new FacesMessage("Successful", msg));
        }

        return ("index?faces-redirect=true");
    }
    
    
    

    public String modifierTache() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
      

            gt.ModifierTacheParId(idTache, idPersonne, nomTache, status);
            raffraichitCache();
            msg = "Tache modifiee";
            context.addMessage(null, new FacesMessage("Successful", msg));

        } catch (Exception e) {
            msg = "Aucune tache n'a pour id : " + idTache;
            context.addMessage(null, new FacesMessage("Successful", msg));
        }

        return ("index?faces-redirect=true");
    }

    //change le status de la tache, de en cours vers non attribue et inversement
    public void onEncoursDrop(DragDropEvent ddEvent) {

        Tache t = tacheDrag;
        //on recupere status complet par defaut
        String statusName = Status.complete.getName();
        long idContributeur = 0;
        //si bien un contributeur on recupere son id
        if (t.getContributeur() != null) {
            idContributeur = t.getContributeur().getId();
        }
      
        //si status de la tache deja a complet alors le drag aura ete fait vers le statut en cours
        if(t.getStatus().getName().equals(statusName)){
            statusName = Status.en_cours.getName();
        }
       //modification status tache
        gt.ModifierTacheParId(t.getId(), idContributeur, t.getNomTache(), statusName);
      //  raffraichitCache();
    }
    
    //drop vers datatable non attribue
    public void onEncoursDropNonAttribue(DragDropEvent ddEvent) {

        Tache t = tacheDrag;
        long idContributeur = 0;
  
        gt.ModifierTacheParId(t.getId(), idContributeur, t.getNomTache(), Status.non_attribuée.getName());
       // raffraichitCache();
    }

    //clique sur le drag, enregistrement tache sur laquelle on a clique
    public void setTacheDrag(Tache t){
        //recuperation du context
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String,String> params = context.getExternalContext().getRequestParameterMap();
        //recuperation du paramatre
        String myName1 = params.get("myName1");
    
        //atribution en recherchant l'id passe en parametre
        tacheDrag = gt.find(Integer.parseInt(myName1));
        
    }
}
