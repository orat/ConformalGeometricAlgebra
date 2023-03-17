package de.orat.math.cga.api;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
interface iCGABivector extends iCGAkVector {
    
    default boolean isBivector(){
        return true;
    }
    @Override
    default void testGrade(){
        int grade = grade();
        if (grade != 2 && grade != 0) throw new IllegalArgumentException("The given multivector is not of grade 2 or a null vector:");
    }
}
