package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Rounds are points, circle, spheres and point-pairs.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGARound extends CGABlade  {
    
    CGARound(CGAMultivector m){
        super(m);
    }
    CGARound(iCGAMultivector impl){
        super(impl);
    }
    public Vector3d attitude(){
        CGAMultivector result = attitudeIntern();
        System.out.println("attitude="+result.toString());
        return result.extractEuclidianVector();
    }
    @Override
    protected CGAMultivector attitudeIntern(){
        return attitudeFromTangentAndRound();
    }
    @Override
    public Point3d location(Point3d probe){
        throw new RuntimeException("Not available. Use location() without argument instead!");
    }
    @Override
    public Point3d location(){
        CGAMultivector result = locationFromTangendAndRound();
        //TODO
        double[] vector = result.impl.extractCoordinates(1);
        int index = result.impl.getEStartIndex();
        return new Point3d(vector[index++], vector[index++], vector[index]);
    }
        
    /**
     * Squared size (=squared radius only for round, - squared radius for dual round).
     * 
     * @return squared size
     */
    public double squaredSize(){
        return squaredSize(this);
    }
    static double squaredSize(CGAMultivector m){
        CGAMultivector result = m.gp(m.gradeInversion()).div((new CGAScalar(2d)).gp((createInf(1d).ip(m)).sqr()));
        System.out.println("squaredSize/radiusSquared="+result.toString());
        return result.scalarPart();
    }
    public RoundAndTangentParameters decompose(){
       return new RoundAndTangentParameters(attitude(), 
                location(), squaredSize());
    }
    
    
    /*
     /**
     * Decompose round object.
     * 
     * Dorst2007
     * 
     * @return attitude, location and squared size for multivectors corresponding to rounds in
     * inner product null space representaton
     */
    /*protected RoundAndTangentParameters decomposeRound(){
        // (-) because the radius for dual round corresponding to Dorst2007 is needed to
        // get the value corresponding to inner product null space representation
        return new RoundAndTangentParameters(attitudeFromTangentAndRound(), 
                determineLocationFromTangentAndRound(), -roundSquaredSize());
    }*/
}
