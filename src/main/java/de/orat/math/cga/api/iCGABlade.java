package de.orat.math.cga.api;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
interface iCGABlade {
    
    default void testGrade(){
        if (grade() < 0) throw new IllegalArgumentException("The given multivector is no blade!");
    }
    public int grade();
}
