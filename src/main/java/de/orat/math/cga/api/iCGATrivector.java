package de.orat.math.cga.api;

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
        return (iCGATrivector) (new CGAE3Vector(a)).op(
                (new CGAE3Vector(b)).op(
                CGAMultivector.createInf(1d)));
    }
    
    public double scalarPart();
    
    @Override
    default void testGrade(){
        int grade = grade();
        if (grade != 3 && grade != 0) throw new IllegalArgumentException("The given multivector m is not of grade 3!");
        if (grade == 0 && scalarPart() != 0) throw new IllegalArgumentException("The given multivector is of grade 0 but the scalar part is != 0!");
    }
}
