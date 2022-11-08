package de.orat.math.cga.api;

/**
 * Euclidean tri-vector (e123).
 * 
 * TODO implement test-classes for the basisblades.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAEuclideanTrivector extends AbstractEuclideanKVector implements iCGATrivector {
    public CGAEuclideanTrivector(CGAMultivector m){
        super(m);
    }
}
