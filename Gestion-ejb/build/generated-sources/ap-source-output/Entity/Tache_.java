package Entity;

import Entity.Personne;
import Entity.Status;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-28T20:21:24")
@StaticMetamodel(Tache.class)
public class Tache_ { 

    public static volatile SingularAttribute<Tache, String> nomTache;
    public static volatile SingularAttribute<Tache, Personne> contributeur;
    public static volatile SingularAttribute<Tache, Long> id;
    public static volatile SingularAttribute<Tache, Status> status;

}