package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * Direction/attitude bivector of grade 3.
 * 
 * A 2-dimensional direction element. Drawn sippled at the origin. 
 * 
 * e1^e2^ni, e2^e3^ni, e3^e1^ni
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeBivectorOPNS extends CGAAttitude implements iCGATrivector {
    
    public CGAAttitudeBivectorOPNS(CGAMultivector m){
        super(m);
    }
    
    
    // composition 
    
    public CGAAttitudeBivectorOPNS(CGABivector B){
        // muss statt gp nicht op stehen
        //FIXME
        this(B.op(createInf(1.0)));
    }
    
    
    // decomposition
    
    @Override
    public Vector3d direction(){
        return extractAttitudeFromBivectorEinfRepresentation();
    }
}
