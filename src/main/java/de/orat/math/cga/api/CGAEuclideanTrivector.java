package de.orat.math.cga.api;

import org.jogamp.vecmath.Tuple3d;

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
    public CGAEuclideanTrivector(Tuple3d v1, Tuple3d v2, Tuple3d v3){
        super((new CGAEuclideanVector(v1)).op(new CGAEuclideanVector(v2)).
                                               op(new CGAEuclideanVector(v3)));
    }
}
