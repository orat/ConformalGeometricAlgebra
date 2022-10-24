package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import de.orat.math.cga.util.Decomposition3d;
import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * (Oriented) Rounds are roundPoints, point-pairs, circles and spheres/hyper-spheres; here given in
 * inner product null space representation corresponding to direct round in Dorst2007.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGAOrientedRoundOPNS extends CGABlade {
    
    CGAOrientedRoundOPNS(CGAMultivector m){
        super(m.impl);
    }
    protected CGAOrientedRoundOPNS(iCGAMultivector impl){
        super(impl);
    }
    
    
    public Vector3d attitude(){
        CGAMultivector result = attitudeIntern();
        System.out.println("attitude(dualRound/dualTangent)="+result.toString());
        return result.extractE3ToVector3d();
    }
    @Override
    protected CGAMultivector attitudeIntern(){
        return attitudeFromTangentAndRound2(this);
        //return attitudeFromDualTangentAndDualRound();
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
        //return -CGAOrientedRoundIPNS.squaredSize(this);
        return squaredSize(this);
    }
    /**
     * Determination of the squared size. 
     * 
     * @param m round or dual round object represented by a multivector
     * @return squared size/radius squared
     */
    static double squaredSize(CGABlade m){
        // unklar ob sqr() überhaupt richtig implementiert ist
        CGAMultivector result = m.gp(m.gradeInversion()).div((createInf(1d).lc(m)).sqr());
        //System.out.println("squaredSize/radiusSquared="+result.toString());
        
        // https://github.com/pygae/clifford/blob/master/clifford/cga.py
        // hier findet sich eine leicht andere Formel, mit der direkt die size/radius
        // also nicht squaredSize bestimmt werden kann
        
        return result.scalarPart();
    }
    @Override
    public Point3d location(Point3d probe){
        throw new RuntimeException("Not available. Use location() without argument instead!");
    }
    @Override
    public Point3d location(){
        CGAMultivector result = locationFromRoundAsNormalizedSphere(); //locationFromTangendAndRound();
        return result.extractE3ToPoint3d();
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
     * @return the projected point = -point^ni<<sphere<<sphere
     */
    public CGAPointPairOPNS project(CGARoundPointIPNS point){
        return new CGAPointPairOPNS(point.op(CGAMultivector.createInf(1d)).lc(this).lc(this).negate());
    }
}