package de.orat.math.cga.api;

/**
 * All vectors seems to be weighted spheres.
 * 
 * Vectors are points (CGARoundPoint), dual planes (CGAPlaneIPNS), dual real spheres and 
 * dual imaginary spheres (CGASphereIPNS) corresponding to Dorst2007.
 * 
 * implemented by CGANormalVector
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
interface iCGAVector extends iCGABlade {
    
    default boolean isVector(){
        return true;
    }
    
    public double decomposeScalar();
    
    @Override
    default void testGrade(){
        int grade = grade();
        if (grade != 1 && grade != 0) throw new IllegalArgumentException("The given multivector is not not grade 1 or 0! grade()="
                 +String.valueOf(grade));
        // allows null vectors
        if (grade == 0 && decomposeScalar() != 0) throw new IllegalArgumentException("The given multivector is of grade 0 but the scalar part is != 0!");
                 
    }
}
