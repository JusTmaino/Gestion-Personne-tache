/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import Entity.Personne;
import Entity.Tache;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 *
 * @author justmaino
 */
@Stateless
@LocalBean
public class GestionnairePersonnes {

    @PersistenceContext()
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    private void creerPersonne(Personne t) {
        em.persist(t);

    }

    public void creerPersonneTest() {

        for (int i = 1; i < 21; i++) {
            creerPersonne(new Personne("Deschamps"+i, "aurore"+i));
            creerPersonne(new Personne("Nicolleti"+i, "sebatien"+i));
            creerPersonne(new Personne("Martin"+i, "léo-paul"+i));
            creerPersonne(new Personne("Micallef"+i, "david"+i));
        }

    }

    public List<Personne> findAll() {
        Query q = em.createQuery("select c from Personne  c");
        return q.getResultList();
    }

    public List<Personne> findRange(int start, int nb, String nomColonne, String so) {
        System.out.println("nom colonne " + nomColonne + "sort order = " + so);
        Query q = em.createQuery("select c from Personne  c");
        q.setFirstResult(start);
        q.setMaxResults(nb);
        return q.getResultList();
    }

    public int count() {
        Query q = em.createQuery("select count(c) from Personne  c");
        Number c = (Number) q.getSingleResult();
        return c.intValue();
    }

    public List<Tache> getTaches(int id) {
        Personne c = em.find(Personne.class, id);
        return c.getListeTaches();

    }

    public void addTache(Tache t, Long id) {
        Personne c = em.find(Personne.class, id);
        c.addTache(t);

    }

    public void removeTache(Tache t, int id) {
        Personne c = em.find(Personne.class, id);
        c.removeTache(t);
    }

    public void creerPersonne(String nom, String prenom) {
        Personne p = new Personne(nom, prenom);
        creerPersonne(p);
    }

    public Personne getPersonne(long id) {
        Long idP = id;
        Personne p = em.find(Personne.class, idP);
        return p;
    }

    public Personne update(Personne c) {
        return em.merge(c);
    }

    public void supprimerPersonneParId(Long id) {
        supprimer(em.find(Personne.class, id));
    }

    public void supprimer(Personne c) {
        //supprimer l'utilisateur
        if (c != null) {

            em.remove(c);

        }
    }

    public void ModifierPersonneParId(Long id, String prenom, String nom) {
        Personne t = em.find(Personne.class, id);

        if (t != null) {
            t.setNom(nom);
            t.setPrenom(prenom);
            this.update(t);
        }

    }

}
