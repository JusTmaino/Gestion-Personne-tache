/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author justmaino
 */
public enum Status implements Serializable {
    non_attribuée ("non attribué"),
    en_cours ("en cours"),
    complete ("complète");
    
   private String name = "";
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 

    private Status() {
    }
    
   
    Status(String name) {
        this.name=name;
    }
    
    public String getName(){
        return this.toString();
    }
    
    @Override
    public String toString(){
        return name;
    }
}
