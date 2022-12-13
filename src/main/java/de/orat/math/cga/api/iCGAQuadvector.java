package de.orat.math.cga.api;

/**
 * Quadvectors describe spheres and planes.
 * 
 * Quadvectors are linear combinations of Blades with grade 4.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
interface iCGAQuadvector extends iCGABlade {
    
    default boolean isQuadvector(){
        return true;
    }
    
    public double decomposeScalar();
    
    @Override
    default void testGrade(){
        int grade = grade();
        if (grade() != 4 && grade != 0) throw new IllegalArgumentException("The given multivector is not of grade 4 or a null vector!");
        if (grade == 0 && decomposeScalar() != 0) throw new IllegalArgumentException("The given multivector is of grade 0 but the scalar part is != 0!");
    }
}
