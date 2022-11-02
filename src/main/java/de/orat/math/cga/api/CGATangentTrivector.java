package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * A tangend tri-vector is a multivector of grade 4 (e1230).
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATangentTrivector extends CGATangentOPNS implements iCGAQuadvector {
    
    public CGATangentTrivector(CGAMultivector m){
        super(m);
    }
    
    public CGATangentTrivector(Vector3d a, Vector3d b, Vector3d c){
        // das soll o I3 sein
        //FIXME
        this((createOrigin(1.0).gp(new CGATrivector(a,b,c))));
    }
}
