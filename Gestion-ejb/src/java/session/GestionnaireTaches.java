/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import Entity.Personne;
import Entity.Tache;
import Entity.Status;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 *
 * @author justmaino
 */
@Stateless
@LocalBean
public class GestionnaireTaches {

    @PersistenceContext()
    @Enumerated(EnumType.STRING)
    private EntityManager em;
    @EJB
    GestionnairePersonnes gp;

    public void persist(Object object) {
        em.persist(object);
    }

    private void creerTache(Tache t) {
        em.persist(t);
        Personne p = t.getContributeur();
        //ajout tache au contributeur
        if (p != null) {
            p.addTache(t);
            em.merge(p);
        }

    }

    public Tache find(int id) {
        Query q = em.createQuery("select c from Tache  c where c.id =:id")
                .setParameter("id", Integer.toUnsignedLong(id));

        return (Tache) q.getSingleResult();
    }

    public void creerTacheTest() {
        System.err.println("@@On est dans le manager");
        List<Personne> p = gp.findAll();

        creerTache(new Tache("Projet JEE"));
        creerTache(new Tache("Projet Android"));
        creerTache(new Tache("Projet SQL"));
        creerTache(new Tache("Projet IOS"));
        creerTache(new Tache("Projet TPT"));

        int j = 0;
        for (int i = 1; i < 300; i++) {
            if (j >= p.size()) {
                j = 0;
            }
            creerTache(new Tache("Projet JEE" + i, p.get(j), Status.en_cours));
            creerTache(new Tache("Projet Android" + i, p.get(j), Status.en_cours));
            creerTache(new Tache("Projet SQL" + i, p.get(j), Status.en_cours));
            creerTache(new Tache("Projet IOS" + i, p.get(j), Status.en_cours));
            creerTache(new Tache("Projet TPT" + i, p.get(j), Status.en_cours));

            j++;
        }

        for (int i = 300; i < 500; i++) {
            if (j >= p.size()) {
                j = 0;
            }
            creerTache(new Tache("Projet JEE" + i, p.get(j), Status.complete));
            creerTache(new Tache("Projet Android" + i, p.get(j), Status.complete));
            creerTache(new Tache("Projet SQL" + i, p.get(j), Status.complete));
            creerTache(new Tache("Projet IOS" + i, p.get(j), Status.complete));
            creerTache(new Tache("Projet TPT" + i, p.get(j), Status.complete));
            j++;
        }
    }

    public List<Tache> findAll() {
        Query q = em.createQuery("select c from Tache  c");
        return q.getResultList();
    }

    public List<Tache> findRange(int start, int nb, String nomColonne, String so) {
        System.out.println("nom colonne " + nomColonne + "sort order = " + so);
        Query q = em.createQuery("select c from Tache  c");

        q.setFirstResult(start);
        q.setMaxResults(nb);
        return q.getResultList();
    }

    public List<Tache> findRangeNonAttribue(int start, int nb, String nomColonne, String so) {
        System.out.println("nom colonne " + nomColonne + "sort order = " + so);
        Query q = em.createQuery("select c from Tache  c where c.status =:status")
                .setParameter("status", Status.non_attribuée);

        q.setFirstResult(start);
        q.setMaxResults(nb);
        return q.getResultList();
    }

    public List<Tache> findRangeEnCours(int start, int nb, String nomColonne, String so) {
        System.out.println("nom colonne " + nomColonne + "sort order = " + so);
        Query q = em.createQuery("select c from Tache  c where c.status =:status")
                .setParameter("status", Status.en_cours);

        q.setFirstResult(start);
        q.setMaxResults(nb);
        return q.getResultList();
    }

    public List<Tache> findRangeComplete(int start, int nb, String nomColonne, String so) {
        System.out.println("nom colonne " + nomColonne + "sort order = " + so);
        Query q = em.createQuery("select c from Tache  c where c.status =:status")
                .setParameter("status", Status.complete);

        q.setFirstResult(start);
        q.setMaxResults(nb);
        return q.getResultList();
    }

    public int count() {
        Query q = em.createQuery("select count(c) from Tache  c");
        Number c = (Number) q.getSingleResult();
        return c.intValue();
    }

    public void supprimer(Tache c) {
        //supprimer tache de l'utilisateur
        if (c != null) {
            if (c.getContributeur() != null) {
                c.getContributeur().removeTache(c);
            }
            em.remove(c);

        }
    }

    public Tache update(Tache t) {
        return em.merge(t);
    }

    public void creerNouvelleTache(long idP, String nomTache) {
        Personne p = gp.getPersonne(idP);
        if (p != null) {
            Tache t = new Tache(nomTache, p, Status.en_cours);
            creerTache(t);

        } else {
            creerTache(new Tache(nomTache));
        }
    }

    public void supprimerTacheParId(long idTache) {
        supprimer(em.find(Tache.class, idTache));
    }

    public void ModifierTacheParId(long idTache, long idPersonne, String nomTache, String status) {
        Tache t = em.find(Tache.class, idTache);

        if (t != null) {
            if (t.getContributeur() != null) {
                t.getContributeur().removeTache(t);
                em.merge(t.getContributeur());

            }

            t.setNomTache(nomTache);
            Status stValue = Status.en_cours;

            for (Status st : Status.values()) {
                if (st.getName().equals(status)) {

                    stValue = st;
                }
            }

            t.setStatus(stValue);

            Personne p = em.find(Personne.class, idPersonne);
            if (p != null && !status.equals("non attribué")) {
                t.setContributeur(p);
                p.addTache(t);
            } else {
                stValue = Status.non_attribuée;
                t.setContributeur(null);
                t.setStatus(stValue);
            }

            this.update(t);
        }

    }
}
