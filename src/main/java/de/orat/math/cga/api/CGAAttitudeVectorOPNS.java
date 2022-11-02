package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * Also called free or direction vector (Dorst2007), elements without position (grade 2). 
 * 
 * Just as tangents support round elements, so do directions support flat elements. 
 * 
 * It represents a direction without a location. It is translation 
 * invariant but rotation covariant.
 * 
 * This means there is no e0-component in its formula.
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
public class CGAAttitudeVectorOPNS extends AbstractCGAAttitude implements iCGABivector {
    
    public CGAAttitudeVectorOPNS(CGAMultivector m){
        super(m);
        testDefiningProperties();
    }
    protected CGAAttitudeVectorOPNS(iCGAMultivector impl){
        super(impl);
        testDefiningProperties();
    }
    
    public CGAAttitudeVectorOPNS(Vector3d t){
        super((new CGANormalVector(t)).op(createInf(1.0)));
    }
    
    public Vector3d attitude(){
        CGAMultivector attitude = attitudeIntern();
        return attitude.extractAttitudeFromEeinfRepresentation();
        //TODO
        // mit was muss ich den multivector multiplizieren um die betreffenden 
        // Komponenten dann mit attitude.extractE3ToVector3d() abspalten zu können?
    }
    @Override
    protected CGAMultivector attitudeIntern(){
        System.out.println("attitude="+toString());
        return this;
    }
    
    private void testDefiningProperties(){
        if (!CGAMultivector.createInf(1d).op(this).isNull()){
            throw new IllegalArgumentException("einf^X != 0");
        }
        if (!CGAMultivector.createInf(1d).ip(this).isNull()){
            throw new IllegalArgumentException("einf . X != 0");
        }
    }
    
    
    @Override
    public CGAAttitudeVectorIPNS dual(){
        return new CGAAttitudeVectorIPNS(impl.dual());
    }
}
