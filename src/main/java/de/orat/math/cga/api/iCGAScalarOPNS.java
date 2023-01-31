package de.orat.math.cga.api;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iCGAScalarOPNS extends iCGABlade {
    
    default boolean isScalar(){
        return true;
    }
    
    @Override
    default void testGrade(){
         if (grade() != 0) throw new IllegalArgumentException("The given multivector is not not grade 0!");
    }
}
