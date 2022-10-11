package de.orat.math.cga.api;

/**
 * Direction/attitude Bivector of grad 3.
 * 
 * A 2-dimensional direction element. Drawn sippled at the origin. 
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeBivector extends AbstractCGAAttitude implements iCGABivector {
    
    public CGAAttitudeBivector(CGAMultivector m){
        super(m);
    }
    
    public CGAAttitudeBivector(CGABivector B){
        this(B.gp(createInf(1.0)));
    }
}
