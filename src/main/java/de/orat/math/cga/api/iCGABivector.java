package de.orat.math.cga.api;

import org.jogamp.vecmath.Tuple3d;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
interface iCGABivector extends iCGABlade {
    
    /**
     * Create a bivector from two euclidian vectors.
     * 
     * @param a first vector
     * @param b second vector
     * @return bivector
     */
    default iCGABivector createCGABivector(Tuple3d a, Tuple3d b){
        return (iCGABivector) (new CGANormalVector(a)).op(
             (new CGANormalVector(b)));
    }
    /**
     * Create a bivector from an euclidian vector and the point at infinity.
     * 
     * @param a the euclidian vector
     * @return bivector
     */
    default iCGABivector createCGAInfBivector(Tuple3d a){
        return (iCGABivector) (new CGANormalVector(a)).op(
             CGAMultivector.createInf(1d));
    }
    
    default boolean isBivector(){
        return true;
    }
    @Override
    default void testGrade(){
        if (grade() != 2) throw new IllegalArgumentException("The given multivector is not of grade 2!");
    }
}
