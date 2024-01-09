package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * Direction/attitude/free vector,  trivector (grade 4).
 * 
 * e1^e2^e3^einf
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeTrivectorOPNS extends CGAAttitudeOPNS implements iCGAQuadvector {
    
    public CGAAttitudeTrivectorOPNS(CGAMultivector m){
        super(m);
    }
    
    protected CGAAttitudeTrivectorOPNS(iCGAMultivector impl){
        super(impl);
    }
    
    
    // composition
    
    public CGAAttitudeTrivectorOPNS(Vector3d a, Vector3d b, Vector3d c){
        this((new CGAEuclideanVector(a)).op(
                new CGAEuclideanVector(b)).op(
                new CGAEuclideanVector(c)).op(inf));
    }
    
    
    // decomposition
    
    @Override
    public Vector3d direction(){
        //FIXME ist das so sinnvoll? oder wie ist die attitude eines round-point definiert?
        // CGARound.att()=16.0*e1234 + 16.0*e1235
        // Es macht also keinen Sinn hier ein Vector3d zurückzuliefern, das sollte ein double sein?
        // was bekomme ich bei Umstellung auf der Implementierung? Vielfaches von inf oder o?
        //return new Vector3d(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
        throw new RuntimeException("direction() not available for CGAAttitudeTriVector!");
    }
    
    public CGAAttitudeTrivectorIPNS dual(){
        return new CGAAttitudeTrivectorIPNS((new CGAAttitudeTrivectorIPNS(super.dual())).compress());
    }
    public CGAKVector undual(){
        throw new RuntimeException("undual() not supported for generic opns attitude trivector!");
    }
}