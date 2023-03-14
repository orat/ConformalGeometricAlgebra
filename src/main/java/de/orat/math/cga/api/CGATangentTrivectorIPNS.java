package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * An ipns tangend tri-vector is a multivector of grade 1.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATangentTrivectorIPNS extends CGATangentIPNS implements iCGAVector {
    
    public CGATangentTrivectorIPNS(CGAMultivector m){
        super(m);
    }
 
    public CGATangentTrivectorOPNS undual(){
        return new CGATangentTrivectorOPNS(super.undual().compress());
    }
}
