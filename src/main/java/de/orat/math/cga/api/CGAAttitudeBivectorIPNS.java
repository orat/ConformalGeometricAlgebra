package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeBivectorIPNS extends CGAAttitudeIPNS {
    
    public CGAAttitudeBivectorIPNS(CGAMultivector m){
        super(m);
    }
    
    protected CGAAttitudeBivectorIPNS(iCGAMultivector impl){
        super(impl);
    }
    
    // decomposition
    
    @Override
    public Vector3d direction(){
        return extractAttitudeFromBivectorEinfRepresentation();
    }
}
