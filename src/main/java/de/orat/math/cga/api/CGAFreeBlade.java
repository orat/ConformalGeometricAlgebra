package de.orat.math.cga.api;

/**
 * Also called free or direction vector, elements without position. 
 * 
 * This means there is no e0 -component in its formula.
 * 
 * A free vector does not have a position. Given the normal vector n, it can be 
 * calculated as follows: n ∧ e ∞ .
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAFreeBlade extends CGABlade implements iCGABivector {
    
    CGAFreeBlade(CGAMultivector m){
        super(m);
    }
}
