package de.orat.math.cga.api;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
interface iCGAVector extends iCGABlade {
    
    @Override
    default void testGrade(){
         if (grade() != 1) throw new IllegalArgumentException("The given multivector is not not grade 1!");
    }
}
