package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * Direction/attitude/free vector,  trivector (grade 4).
 * 
 * e1^e2^e3^einf
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeTrivectorOPNS extends CGAAttitude implements iCGAQuadvector {
    
    public CGAAttitudeTrivectorOPNS(CGAMultivector m){
        super(m);
    }
    
    public CGAAttitudeTrivectorOPNS(Vector3d a, Vector3d b, Vector3d c){
        //FIXME
        // muss statt gp nicht op stehen?
        this((new CGATrivector(a,b,c)).op(createInf(1.0)));
    }
    
}
