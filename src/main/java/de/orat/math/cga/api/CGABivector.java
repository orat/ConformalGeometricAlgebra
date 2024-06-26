package de.orat.math.cga.api;

import org.jogamp.vecmath.Tuple3d;

/**
 * Bivectors describe points and pairs of points in OPNS representation.
 * 
 * Bivectors are linear combinations of Blades with grade 2. They are also called
 * timelike.<p>
 * 
 * Inheritage from this class typicall not possible because inheritage from round
 * or flat is needed. Thatś why additional interface iCGABivector is available.<p>
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGABivector extends CGAKVector implements iCGABivector {
    
    public CGABivector(CGAMultivector m){
        super(m);
    }
    
    /**
     * Create a bivector from two euclidian vectors.
     * 
     * is testet for basis vectors!
     * 
     * TODO
     * aber ist das auch korrekt wenn a,b keine Basisvektoren sind
     * vermutlich ja, dann gibts nur mehrere Summanden aus k-basis-blades
     * 
     * @param a first vector
     * @param b second vector
     */
    public CGABivector(Tuple3d a, Tuple3d b){
        this((new CGAEuclideanVector(a)).op(
             (new CGAEuclideanVector(b))));
    }
    
    /**
     * Create a bivector from an euclidian vector and the point at infinity.
     * 
     * @param a the euclidian vector
     */
    public CGABivector(Tuple3d a){
        this((new CGAEuclideanVector(a)).op(inf));
    }
    
    public CGATrivector dual(){
        return new CGATrivector(super.dual().compress());
    }
    public CGATrivector undual(){
        return new CGATrivector(super.undual().compress());
    }
}
