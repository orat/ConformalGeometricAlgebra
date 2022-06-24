package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADirectionTrivector extends CGABlade implements iCGAQuadvector {
    
    public CGADirectionTrivector(CGAMultivector m){
        super(m);
    }
    
    public CGADirectionTrivector(Vector3d a, Vector3d b, Vector3d c){
        this((new CGATrivector(a,b,c)).gp(createInf(1.0)));
    }
}
