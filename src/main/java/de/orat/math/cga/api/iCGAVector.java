package de.orat.math.cga.api;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
interface iCGAVector extends iCGABlade {
    
    default boolean isVector(){
        return true;
    }
    
    @Override
    default void testGrade(){
         if (grade() != 1) throw new IllegalArgumentException("The given multivector is not not grade 1! grade()="
                 +String.valueOf(grade()));
    }
}
