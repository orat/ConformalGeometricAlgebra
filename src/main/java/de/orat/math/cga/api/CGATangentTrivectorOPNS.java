package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * A tangend tri-vector is a multivector of grade 4 (e1230).
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATangentTrivectorOPNS extends CGATangentOPNS implements iCGAQuadvector {
    
    public CGATangentTrivectorOPNS(CGAMultivector m){
        super(m);
    }
    
    //FIXME
    // macht ein solcher Konstruktor überhaupt Sinn? Vermutlich kommt dabei
    // unabhängig von den Argumenten immer e0^e1^e2^e3 heraus?
    // ein Konstruktor ohne Argumente? oder ein Skalar?
    public CGATangentTrivectorOPNS(Vector3d a, Vector3d b, Vector3d c){
        // das soll o I3 sein
        //FIXME
        // warum ist gp() hier das gleiche wie o^I3?
        //FIXME
        this((createOrigin(1.0).op(new CGAEuclideanTrivector(a,b,c))));
    }
    
    public CGATangentTrivectorIPNS dual(){
        return new CGATangentTrivectorIPNS(super.dual().compress());
    }
    public CGAKVector undual(){
        throw new RuntimeException("undual() not supported for opns tangent trivector!");
    }
}
