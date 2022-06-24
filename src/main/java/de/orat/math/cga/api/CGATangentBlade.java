package de.orat.math.cga.api;

/**
 * The tangent balde/vector t has a position and a direction. 
 * 
 * It can be seen as the formula e 0 ∧ n, and than translated to the desired
 * location.
 * 
 * TODO
 * was ist mit position vector (grade 3) und normal vector (grade 1)?
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGATangentBlade extends CGABlade implements iCGABivector {
    
    CGATangentBlade(CGAMultivector m){
        super(m);
    }
}
