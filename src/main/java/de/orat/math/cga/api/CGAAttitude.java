package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * Attitudes, also called free or direction vectors or free k-blades are elements 
 * without position. 
 * 
 * They are made by wedging any Euclidean element (vector, bivector, or 
 * trivector) with ∞. Directions are invariant under translations 
 * (they do not change if moved), but they can of course be rotated.

 * They represent directions without a location. They are translation 
 * invariant but rotation covariant.
 * 
 * This means there is no e0-component in its formulae.
 * 
 * This is the base class for all attitude classes (Vector, Bivector, TreeVector).
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGAAttitude extends CGAKVector {
    
    CGAAttitude(CGAMultivector m){
        super(m.impl);
    }
    
    protected CGAAttitude(iCGAMultivector impl){
        super(impl);
    }
    
    
    // decomposition
    
    public Vector3d direction(){
        throw new RuntimeException("Implementation only for derviced classes available!");
    }
    
    
    // etc
    
    @Override
    public CGATangentOPNS inverse(){
        throw new RuntimeException("An attitude has no inverse!");
    }
}