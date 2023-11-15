package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.inf;
import org.jogamp.vecmath.Tuple3d;

/**
 * A trivector describe lines, circles, bivector-tangents and bivector-attitudes
 * in OPNS representation and also the following blades in IPNS representation:
 * point-pairs, flat-points, ...
 * 
 * Trivectors are linear combinations of blades with grade 3 (e123).
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
interface iCGATrivector extends iCGAkVector {
     
    default boolean isTrivector(){
        return true;
    }
    
    default iCGATrivector create(Tuple3d a, Tuple3d b, Tuple3d c){
        return (iCGATrivector) (new CGAEuclideanVector(a)).op(
                (new CGAEuclideanVector(b)).op(
                (new CGAEuclideanVector(c))));
    }
    default iCGATrivector create(Tuple3d a, Tuple3d b){
        return (iCGATrivector) (new CGAEuclideanVector(a)).op(
                (new CGAEuclideanVector(b)).op(inf));
    }
    
    public double decomposeScalar();
    
    @Override
    default void testGrade(){
        int grade = grade();
        if (grade != 3 && grade != 0) throw new IllegalArgumentException("The given multivector is not of grade 3 and also not 0!");
        if (grade == 0 && decomposeScalar() != 0) throw new IllegalArgumentException("The given multivector is of grade 0 but the scalar part is != 0!");
    }
}
