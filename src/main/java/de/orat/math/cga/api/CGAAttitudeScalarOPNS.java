package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * The attitude of a flat point contains only the component inf.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeScalarOPNS extends CGAAttitude {
    
    public CGAAttitudeScalarOPNS(CGAMultivector m) {
        super(m);
    }
    
    @Override
    public void testGrade(){
        // test sollte noch darauf erfolgen dass nur die component inf != 0 ist
        //TODO
        int grade = grade();
        if (grade != 1 && grade != 0) throw new IllegalArgumentException("The given multivector is not of grade 1 or a null vector:");
    }
    
    @Override
    public Vector3d direction(){
        return new Vector3d(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
    
}
