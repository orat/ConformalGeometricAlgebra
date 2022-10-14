package de.orat.math.cga.api;

import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 * 
 * Tangent in inner product null space representation corresponding to direct tangent in 
 * Dorst2007.
 * 
 */
class CGATangentIPNS extends CGABlade {
    
    CGATangentIPNS(CGAMultivector m){
        super(m.impl);
    }
    
    public Vector3d attitude(){
        CGAMultivector result = attitudeIntern();
        System.out.println("attitude="+result.toString());
        return result.extractE3ToVector3d();
    }
    @Override
    protected CGAMultivector attitudeIntern(){
        return attitudeFromDualTangentAndDualRound();
    }
    @Override
    public Point3d location(Point3d probe){
        throw new RuntimeException("Not available. Use location() without argument instead!");
    }
    @Override
    public Point3d location(){
        return CGATangentOPNS.locationIntern(this);
        //CGAMultivector result = locationFromTangendAndRoundAsNormalizedSphere();
        //System.out.println("location="+result.toString());
        //return extractE3ToPoint3d();
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
    public CGATangentIPNS inverse(){
        throw new RuntimeException("A tangent has no inverse!");
    }
}