package de.orat.math.cga.api;

/**
 * Dilation rotors about the origin are generated by the timelike Minkowski 
 * Plane E = o ∧ ∞ with E 2 > 0 and weighted base. 
 * 
 * A dilatation around any point in space can be constructed by translating 
 * the Dilator D.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADilator extends CGAMultivector {
    
    public CGADilator(CGAMultivector m){
        super(m.impl);
    }
    
    //TODO
}