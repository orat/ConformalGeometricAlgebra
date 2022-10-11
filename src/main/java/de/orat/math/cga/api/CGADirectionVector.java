package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * A direction vector (grade 2).
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADirectionVector extends CGABlade implements iCGABivector {
     
    public CGADirectionVector(CGAMultivector m){
        super(m);
    }
    
    public CGADirectionVector (Vector3d t){
        this((new CGAMultivector(t)).gp(createInf(1.0)));
    }
}
