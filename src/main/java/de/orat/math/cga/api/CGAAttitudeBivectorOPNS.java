package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Tuple3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Direction/attitude bivector of grade 3.
 * 
 * A 2-dimensional direction element. Drawn sippled at the origin. 
 * 
 * TODO
 * hier fehlt noch eine korrespondierende Klasse mit der IPNS Darstellung.
 * 
 * e1^e2^ni, e2^e3^ni, e3^e1^ni
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeBivectorOPNS extends CGAAttitudeOPNS implements iCGATrivector {
    
    public CGAAttitudeBivectorOPNS(CGAMultivector m){
        super(m);
    }
    
    protected CGAAttitudeBivectorOPNS(iCGAMultivector impl){
        super(impl);
    }
    
    // composition 
    
    public CGAAttitudeBivectorOPNS(CGAEuclideanBivector B){
        // muss statt gp nicht op stehen
        //FIXME
        this(B.op(inf));
    }
    
    public CGAAttitudeBivectorOPNS(Tuple3d v1, Tuple3d v2){
        super((new CGAEuclideanVector(v1)).op(new CGAEuclideanVector(v2)).op(inf));
    }
    
    // decomposition
    
    @Override
    public Vector3d direction(){
        return extractAttitudeFromBivectorEinfRepresentation();
    }
    
    
    // etc
    
    @Override
    public CGAAttitudeBivectorIPNS dual(){
        return new CGAAttitudeBivectorIPNS(impl.dual());
    }
}
