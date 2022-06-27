package de.orat.math.cga.api;

/**
 * Direction/attitude Bivector of grad 3.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeBivector extends CGAAttitude implements iCGATrivector {
    
    public CGAAttitudeBivector(CGAMultivector m){
        super(m);
    }
    
    public CGAAttitudeBivector(CGABivector B){
        this(B.gp(createInf(1.0)));
    }
}
