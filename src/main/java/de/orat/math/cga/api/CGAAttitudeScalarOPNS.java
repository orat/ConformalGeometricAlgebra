package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * The attitude of a flat point contains only the component inf.
 * 
 * TODO
 * gibt es dazu auch eine IPNS Darstellung?
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeScalarOPNS extends CGAAttitudeOPNS {
    
    public CGAAttitudeScalarOPNS(CGAMultivector m) {
        super(m.compress());
    }
    
    protected CGAAttitudeScalarOPNS(iCGAMultivector impl){
        super(impl);
    }
    
    
    // composition
    
    public CGAAttitudeScalarOPNS(double scalar){
        super(createInf(scalar));
    }
    
    // etc
    
    @Override
    public void testGrade(){
        // test sollte noch darauf erfolgen dass nur die component inf != 0 ist
        //TODO
        int grade = grade();
        if (grade != 1 && grade != 0) throw new IllegalArgumentException("The given multivector is not of grade 1 or a null vector:");
    }
    
    
    // decomposition
    
    @Override
    public Vector3d direction(){
        return new Vector3d(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
}
