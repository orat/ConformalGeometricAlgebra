package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import de.orat.math.cga.util.Decomposition3d;
import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Oriented and weighted rounds are points, point-pairs, circles and spheres/hyper-spheres,
 * here given in inner product null space representation corresponding to direct round in Dorst2007.
 * 
 * Rounds are objects with finite areas/volumes/hyperolumes.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGAOrientedFiniteRoundOPNS extends CGAKVector {
    
    CGAOrientedFiniteRoundOPNS(CGAMultivector m){
        super(m.impl);
    }
    protected CGAOrientedFiniteRoundOPNS(iCGAMultivector impl){
        super(impl);
    }
    
    
    // decompose
    
    public Vector3d attitude(){
        CGAMultivector result = attitudeIntern();
        System.out.println("attitude(dualRound/dualTangent)="+result.toString());
        return result.extractE3ToVector3d();
    }
    /**
     * @return attitude
     */
    @Override
    protected CGAAttitude attitudeIntern(){
        // z.B. -1.9999999999999982*e1^e2^e3^ei also grade 4 und nicht grade 2
        // wenn das von einem CGASphereOPNS aufgerufen wird
        return attitudeFromTangentAndRound2(this);
        //return attitudeFromDualTangentAndDualRound();
    }
    
    /**
     * Determination of the squared size. This is the radiusSquared for a sphere.
     * 
     * ok for dualSphere?
     * false for pointPair
     * false für OPNS_Sphere
     * 
     * @param m round object represented by a multivector
     * @return squared size/radius squared
     */
    public double squaredSize(){
        return squaredSize(this);
    }
    /**
     * Determination of the squared size. 
     * 
     * @param m round or dual round object represented by a multivector
     * @return squared size/radius
     */
    static double squaredSize(CGAKVector m){
        // following Dorst2008 p.407/08
        CGAMultivector result = m.gp(m.gradeInversion()).div((createInf(1d).lc(m)).sqr()).negate();
        System.out.println(result.toString("squaredSize/radiusSquared"));
        
        // https://github.com/pygae/clifford/blob/master/clifford/cga.py
        // hier findet sich eine leicht andere Formel, mit der direkt die size/radius
        // also nicht squaredSize bestimmt werden kann
        //dual_sphere = self.dual
        //dual_sphere /= (-dual_sphere | self.cga.einf)
        //return math.sqrt(abs(dual_sphere * dual_sphere))
        //result = m.dual().div(m.dual().negate().ip(CGAMultivector.createInf(1d)));
        //result = result.gp(result);
        
        return result.scalarPart();
    }
    @Override
    public Point3d location(Point3d probe){
        throw new RuntimeException("Not available. Use location() without argument instead!");
    }
    @Override
    public Point3d location(){
        CGARoundPointIPNS result = locationIntern();
        return result.extractE3ToPoint3d();
    }
    @Override
    public CGARoundPointIPNS locationIntern(){
        return locationFromTangentAndRoundAsNormalizedSphere(); 
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
    public CGAOrientedPointPairOPNS project(CGARoundPointIPNS point){
        return new CGAOrientedPointPairOPNS(point.op(CGAMultivector.createInf(1d)).lc(this).lc(this).negate());
    }
}