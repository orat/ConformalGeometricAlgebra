package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import de.orat.math.cga.util.Decomposition3d;
import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGADualRound extends CGABlade {
    
    CGADualRound(CGAMultivector m){
        super(m.impl);
    }
    protected CGADualRound(iCGAMultivector impl){
        super(impl);
    }
    public Vector3d attitude(){
        CGAMultivector result = attitudeIntern();
        System.out.println("attitude(dualRound/dualTangent)="+result.toString());
        return result.extractE3ToVector3d();
    }
    @Override
    protected CGAMultivector attitudeIntern(){
        return attitudeFromDualTangentAndDualRound();
    }
    
    /**
     * Determination of the squared size. This is the radiusSquared for a sphere.
     * 
     * ok für dualSphere
     * 
     * @param m round object represented by a multivector
     * @return squared size/radius squared
     */
    public double squaredSize(){
        return -CGARound.squaredSize(this);
    }
    
    @Override
    public Point3d location(Point3d probe){
        throw new RuntimeException("Not available. Use location() without argument instead!");
    }
    @Override
    public Point3d location(){
        CGAMultivector result = locationFromTangendAndRoundAsNormalizedSphere(); //locationFromTangendAndRound();
        return extractE3ToPoint3d();
        //double[] vector = result.impl.extractCoordinates(1);
        //int index = result.impl.getEStartIndex();
        //return new Point3d(vector[index++], vector[index++], vector[index]);
    }
    public Decomposition3d.RoundAndTangentParameters decompose(){
       return new RoundAndTangentParameters(attitude(), 
                location(), squaredSize());
    }
    
    // var project_point_on_round = (point,sphere)=>-point^ni<<sphere<<sphere
    // - hat 1
    // << und ^ hat 3
    /**
     * Projection of a point onto a round (sphere or circle).
     * 
     * @param point if the round is a sphere or the conjugate of the point if the round is a circle
     * 
     * @return the projected point
     */
    public CGARoundPoint project(CGARoundPoint point){
        CGAMultivector m = point.op(CGAMultivector.createInf(1d)).lc(this).lc(this).negate();
        System.out.println("project="+m.toString());
        // vermutlich hat m grade 2 statt grade 1
        return new CGARoundPoint(m);
    }
}