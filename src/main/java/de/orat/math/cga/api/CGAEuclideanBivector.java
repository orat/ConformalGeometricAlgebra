package de.orat.math.cga.api;

/**
 * An euclidean bivector (e12, e13, e23).
 * 
 * TODO implement test-classes for the basisblades.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAEuclideanBivector extends AbstractEuclideanKVector implements iCGABivector {
    public CGAEuclideanBivector(CGAMultivector m){
        super(m);
    }
}
