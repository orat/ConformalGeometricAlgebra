package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.inf;
import org.jogamp.vecmath.Tuple3d;

/**
 * A trivector describes lines and circles.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
interface iCGATrivector extends iCGABlade {
     
    default boolean isTrivector(){
        return true;
    }
    
    default iCGATrivector createCGATrivector(Tuple3d a, Tuple3d b){
        return (iCGATrivector) (new CGAEuclideanVector(a)).op(
                (new CGAEuclideanVector(b)).op(inf));
    }
    
    public double decomposeScalar();
    
    @Override
    default void testGrade(){
        int grade = grade();
        if (grade != 3 && grade != 0) throw new IllegalArgumentException("The given multivector m is not of grade 3!");
        if (grade == 0 && decomposeScalar() != 0) throw new IllegalArgumentException("The given multivector is of grade 0 but the scalar part is != 0!");
    }
}
