package de.orat.math.cga.api;

import org.jogamp.vecmath.Tuple3d;

/**
 * Bivectors describe points and pairs of points in OPNS representation.
 * 
 * Bivectors are linear combinations of Blades with grade 2. They are also called
 * timelike.
 * 
 * Inheritage from this class typicall not possible because inheritage from round
 * or flat is needed. Thatś why additional interface iCGABivector is available.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGABivector extends CGABlade implements iCGABivector {
    
    public CGABivector(CGAMultivector m){
        super(m);
    }
    
    /**
     * Create a bivector from two euclidian vectors.
     * 
     * @param a first vector
     * @param b second vector
     */
    public CGABivector(Tuple3d a, Tuple3d b){
        this((new CGANormalVector(a)).op(
             (new CGANormalVector(b))));
    }
    
    /**
     * Create a bivector from an euclidian vector and the point at infinity.
     * 
     * @param a the euclidian vector
     */
    public CGABivector(Tuple3d a){
        this((new CGANormalVector(a)).op(
             CGAMultivector.createInf(1d)));
    }
}
