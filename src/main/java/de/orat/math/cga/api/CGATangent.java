package de.orat.math.cga.api;

import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGATangent extends CGAMultivector {
    
    CGATangent(CGAMultivector m){
        super(m.impl);
    }
    
    public Vector3d attitude(){
        CGAMultivector result = attitudeIntern();
        System.out.println("attitude="+result.toString());
        //TODO
        return null;
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
        CGAMultivector m = locationFromTangendAndRound();
        System.out.println("location="+m.toString());
        //TODO
        return null;
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
    public CGATangent inverse(){
        throw new RuntimeException("A tangent has no inverse!");
    }
}