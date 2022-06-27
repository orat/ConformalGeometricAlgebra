package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATangentTrivector extends CGATangent implements iCGAQuadvector {
    
    public CGATangentTrivector(CGAMultivector m){
        super(m);
    }
    
    public CGATangentTrivector(Vector3d a, Vector3d b, Vector3d c){
        // das soll o I3 sein
        this((createOrigin(1.0).gp(new CGATrivector(a,b,c))));
    }
}
