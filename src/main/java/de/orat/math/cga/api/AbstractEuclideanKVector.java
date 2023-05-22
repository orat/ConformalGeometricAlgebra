package de.orat.math.cga.api;

/**
 * Euclidean k-vectors, also called euclidean blades are vectors, bivectors or
 * tri-vectors, which can be used to represent metric characteristics of geometrical
 * objects (e.g. area, volume).
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
abstract class AbstractEuclideanKVector extends CGAKVector {
    
    AbstractEuclideanKVector(CGAMultivector m){
        super(m);
    }
    AbstractEuclideanKVector(double[] values){
        super(values);
    }
}
