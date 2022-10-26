package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;

/**
 * Also called free or direction vector, elements without position. 
 * 
 * It represents a direction without a location. It is translation 
 * invariant but rotation covariant.
 * 
 * This means there is no e0 -component in its formula.
 * 
 * A free vector does not have a position. Given the normal vector n, it can be 
 * calculated as follows: n ∧ e ∞ .
 * 
 * This is the base class for all attitude classes (Vector, Bivector, TreeVector).
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class AbstractCGAAttitude extends CGAkBlade {
    
    AbstractCGAAttitude(CGAMultivector m){
        super(m.impl);
    }
    
    protected AbstractCGAAttitude(iCGAMultivector impl){
        super(impl);
    }
    
    @Override
    public CGATangentOPNS inverse(){
        throw new RuntimeException("An attitude has no inverse!");
    }
}
