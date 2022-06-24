package de.orat.math.cga.api;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iCGAScalar extends iCGABlade {
    
    @Override
    default void testGrade(){
         if (grade() != 0) throw new IllegalArgumentException("The given multivector is not not grade 0!");
    }
}
