package de.orat.math.cga.api;

import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Tangents are created from touching objects, in outer product null space 
 * represenation corresponding to direct tangent in Dorst2007.
 * 
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGATangentOPNS extends CGABlade {
    
    CGATangentOPNS(CGAMultivector m){
        super(m.impl);
    }
    
    public Vector3d attitude(){
        CGAMultivector result = attitudeIntern();
        System.out.println("attitude="+result.toString());
        return result.extractE3ToVector3d();
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
        //CGAMultivector result = locationFromTangendAndRoundAsNormalizedSphere();
        
        Point3d result = locationIntern(this);
        System.out.println("location="+result.toString());
        return result;
        
    }
    static Point3d locationIntern(CGABlade tangent){
        return tangent.div(createInf(-1d).lc(tangent)).extractE3ToPoint3d();
    }
    public double squaredSize(){
        return 0d;
    }
    /**
     * Decompose tangent.
     * 
     * Keep in mind: Corresponding to Dorst2007 dual and not-dual ist switched.
     * 
     * @return direction/attitude and location, size/radius=0
     */
    public RoundAndTangentParameters decompose(){
        return new RoundAndTangentParameters(attitude(), 
                location(), squaredSize());
    }
    
    @Override
    public CGATangentOPNS inverse(){
        throw new RuntimeException("A tangent has no inverse!");
    }
}