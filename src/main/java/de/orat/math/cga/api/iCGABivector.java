package de.orat.math.cga.api;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
interface iCGABivector extends iCGABlade {
    
    @Override
    default void testGrade(){
        if (grade() != 2) throw new IllegalArgumentException("The given multivector is not of grade 2!");
    }
    
}
