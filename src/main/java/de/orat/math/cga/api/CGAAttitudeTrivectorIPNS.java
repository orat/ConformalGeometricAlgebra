package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * Direction/attitude/free vector,  trivector (grade 4).
 *  
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeTrivectorIPNS extends CGAAttitudeIPNS /*implements iCGAQuadvector*/ {
    
    public CGAAttitudeTrivectorIPNS(CGAMultivector m){
        super(m);
    }
    
    protected CGAAttitudeTrivectorIPNS(iCGAMultivector impl){
        super(impl);
    }
    
    
    // composition
    
    
    
    // decomposition
    
    //???
    @Override
    public Vector3d direction(){
        return new Vector3d(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
    }
}