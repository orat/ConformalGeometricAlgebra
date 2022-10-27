package de.orat.math.cga.api;

/**
 * Direction/attitude bivector of grade 3.
 * 
 * A 2-dimensional direction element. Drawn sippled at the origin. 
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeBivectorOPNS extends AbstractCGAAttitude implements iCGATrivector {
    
    public CGAAttitudeBivectorOPNS(CGAMultivector m){
        super(m);
    }
    
    public CGAAttitudeBivectorOPNS(CGABivector B){
        // muss statt gp nicht op stehen
        //FIXME
        this(B.op(createInf(1.0)));
    }
}
