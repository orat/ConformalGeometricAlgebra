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
    
    @Override
    default void testGrade(){
        if (grade() != 3) throw new IllegalArgumentException("The given multivector m is not of grade 3!");
    }
}
