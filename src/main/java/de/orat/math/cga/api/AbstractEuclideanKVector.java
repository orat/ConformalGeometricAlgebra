package de.orat.math.cga.api;

/**
 * Euclidean k-vectors are vectors, bivectors, tri-vectors or 4-vectors, which 
 * can be used to represent metric characteristics of geometrical objects 
 * (e.g. area, volume).<p>
 * 
 * 1-vectors: points, dual-planes, dual-spheres, euclidean vectors
 * bi-vectors: point-pairs, flat-points, dual-lines, free-vectors, tangent-vectors
 * tri-vectors: lines, circles, free-bivectors, tangent-bivectors
 * 4-vectors: planes, spheres, free-trivectors<p>
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
