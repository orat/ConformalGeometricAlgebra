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
interface iCGAVector extends iCGAkVector {
    
    default boolean isVector(){
        return true;
    }
    
    public double decomposeScalar();
    
    @Override
    default void testGrade(){
        int grade = grade();
        if (grade != 1 && grade != 0) throw new IllegalArgumentException("The given multivector has the grade "
                 +String.valueOf(grade)+" but only 0 or 1 is allowed!");
        // allows null vectors
        if (grade == 0 && Math.abs(decomposeScalar()) > CGAMultivector.eps) throw new IllegalArgumentException("The given multivector is of grade 0 but the scalar part is != 0!");
                 
    }
}
