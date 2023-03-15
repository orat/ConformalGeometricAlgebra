package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * Direction/attitude/free vector,  trivector (grade 4).
 * 
 * TODO
 * Hier fehlt noch eine korrespondierende Klasse mit der IPNS Darstellung
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
        //FIXME
        // muss statt gp nicht op stehen?
        this((new CGAEuclideanVector(a)).op(
                new CGAEuclideanVector(b)).op(
                new CGAEuclideanVector(c)).op(createInf(1.0)));
    }
    
    
    // decomposition
    
    @Override
    public Vector3d direction(){
        return new Vector3d(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
    }
}