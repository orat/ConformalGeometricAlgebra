package de.orat.math.cga.api;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
interface iCGAQuadvector extends iCGABlade {
    
    default boolean isQuadvector(){
        return true;
    }
    @Override
    default void testGrade(){
        if (grade() != 4) throw new IllegalArgumentException("The given multivector is not of grade 4!");
    }
}
