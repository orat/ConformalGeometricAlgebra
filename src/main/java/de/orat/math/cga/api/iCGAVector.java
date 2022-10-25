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
    
    @Override
    default void testGrade(){
         if (grade() != 1) throw new IllegalArgumentException("The given multivector is not not grade 1! grade()="
                 +String.valueOf(grade()));
    }
}
