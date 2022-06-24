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
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADirectionVector extends CGABlade implements iCGABivector {
    
    public CGADirectionVector(CGAMultivector m){
        super(m);
    }
    
    public CGADirectionVector(Vector3d t){
        super((CGAVectorE3.createCGAVectorE3(t)).gp(createInf(1.0)));
    }
}
