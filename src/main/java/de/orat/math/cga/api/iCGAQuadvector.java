package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.inf;
import org.jogamp.vecmath.Tuple3d;

/**
 * A Quadvector describes spheres and planes, round-points, trivector-attitudes
 * and trivector-tangents in OPNS representation and also the following blades
 * in IPNS representation: euclidean-vector? scalar-attitude?
 * 
 * Quadvectors are linear combinations of blades with grade 4.
 * 
 * Trivector-attitudes and and trivector-tangents are also of grade 4 but
 * one of its composing points is infinite/origin.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
interface iCGAQuadvector extends iCGABlade {
    
    default boolean isQuadvector(){
        return true;
    }
    
    default iCGAQuadvector create(Tuple3d a,Tuple3d b, Tuple3d c, Tuple3d d){
         return (iCGAQuadvector) (new CGAEuclideanVector(a)).op(
                (new CGAEuclideanVector(b)).op(
                (new CGAEuclideanVector(c))).op(new CGAEuclideanVector(d)));
    }
    default iCGAQuadvector create(Tuple3d a, Tuple3d b, Tuple3d c){
        return (iCGAQuadvector) (new CGAEuclideanVector(a)).op(
                (new CGAEuclideanVector(b)).op(
                (new CGAEuclideanVector(c))).op(inf));
    }
    
    public double decomposeScalar();
    
    @Override
    default void testGrade(){
        int grade = grade();
        if (grade() != 4 && grade != 0) throw new IllegalArgumentException("The given multivector is not of grade 4 or a null vector!");
        if (grade == 0 && decomposeScalar() != 0) throw new IllegalArgumentException("The given multivector is of grade 0 but the scalar part is != 0!");
    }
}
