package de.orat.math.cga.api;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */

interface iCGATrivector extends iCGABlade {
     
    @Override
    default void testGrade(){
        if (grade() != 3) throw new IllegalArgumentException("The given multivector m is not of grade 3!");
    }
}
