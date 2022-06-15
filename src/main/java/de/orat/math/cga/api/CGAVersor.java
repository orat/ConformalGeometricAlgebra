package de.orat.math.cga.api;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAVersor extends CGAMultivector {
     
    public CGAVersor(CGAMultivector m){
        super(m.impl);
        //if (!m.isVersor()) throw new IllegalArgumentException("Construction of versor object from "
        //        +m.toString()+" failed!");
    }
    
    public boolean isVersor(){
       return true;
    }
}
