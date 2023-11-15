package de.orat.math.cga.api;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
interface iCGAkVector {
    
    default void testGrade(){
        if (grade() < 0) throw new IllegalArgumentException("The given multivector is no k-vector (testGrade() < 0)!");
    }
    public int grade();
}
