package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * Also called free or direction vector, elements without position. 
 * 
 * Just as tangents support round elements, so do directions support flat elements. 
 * grade 2 element
 * 
 * It represents a direction without a location. It is translation 
 * invariant.
 * 
 * This means there is no e0 -component in its formula.
 * 
 * A free vector does not have a position. Given the normal vector n, it can be 
 * calculated as follows: n ∧ e ∞ .
 * 
 * Directions are made by wedging any Euclidean element (vector, bivector, or 
 * trivector) with ∞. Directions are invariant under translations 
 * (they do not change if moved), but they can of course be rotated.
 * 
 * Drawn dashed at origin.
 * 
 * It is a one-dimensional attitude, e.g. a direction vector. A line is build from 
 * this by outer product with a point. 
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeVector extends AbstractCGAAttitude implements iCGABivector {
    
    public CGAAttitudeVector(CGAMultivector m){
        super(m);
        testEinf();
    }
    
    public CGAAttitudeVector(Vector3d t){
        super((new CGANormalVector(t)).gp(createInf(1.0)));
    }
    
    public Vector3d attitude(){
        CGAMultivector attitude = attitudeIntern();
        //FIXME
        // folgendes ist falsch, ich muss die richtigen componenten vom grade 2
        // rausholen
        return attitude.extractE3ToVector3d();
    }
    @Override
    protected CGAMultivector attitudeIntern(){
        System.out.println("attitude="+toString());
        return this;
    }
    
    private void testEinf(){
        //TODO
        // Test darauf, dass alle componenten einf enthalten+
        // throw new IllegalArgumentException("T
    }
        
    public double squaredSize(){
        return 0d;
    }
}
