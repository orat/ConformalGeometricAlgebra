package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Rounds are RoundPoints, circle, spheres and point-pairs, all in inner product
 * null space representation.
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
        double[] vector = result.impl.extractCoordinates(1);
        int index = result.impl.getEStartIndex();
        return new Point3d(vector[index++], vector[index++], vector[index]);
    }
        
    /**
     * following Hildenbrand1998
     * 
     * @return location of the round
     */
    public Point3d location2(){
        CGAMultivector result = this.gp(CGAMultivector.createInf(1d)).gp(this);
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
        //gp(2) only in the Hildebrand2004 paper but not in Dorst2007 p.407
        CGAMultivector result = m.gp(m.gradeInversion()).div((createInf(1d).ip(m)).sqr().gp(2d)).gp(-1d);
        System.out.println("squaredSize/radiusSquared="+result.toString());
        return result.scalarPart();
    }
    public RoundAndTangentParameters decompose(){
       return new RoundAndTangentParameters(attitude(), 
                location(), squaredSize());
    }
}
