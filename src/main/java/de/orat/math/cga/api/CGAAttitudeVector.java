package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * Just as tangents support round elements, so do directions support flat elements. 
 * grade 2 element
 * 
 * Directions are made by wedging any Euclidean element (vector, bivector, or 
 * trivector) with ∞. Directions are invariant under translations 
 * (they do not change if moved), but they can of course be rotated.
 * 
 * Drawn dashed at origin.
 * 
 * It is a one-dimensional attitude, e.g. a direction vector. A line is build from 
 * this by outer product with a point. 
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeVector extends CGAAttitude implements iCGAVector {
    
    public CGAAttitudeVector(CGAMultivector m){
        super(m);
    }
    
    public CGAAttitudeVector(Vector3d t){
        super((new CGAVectorE3(t)).gp(createInf(1.0)));
    }
}
