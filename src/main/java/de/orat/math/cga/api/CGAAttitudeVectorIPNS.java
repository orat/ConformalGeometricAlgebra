package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * Also called free or direction vector, elements without position in IPNS representation
 * corresponding to dual direction vector or dual free vector in Dorst2007
 * 
 * It represents a direction without a location. It is translation 
 * invariant.
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
public class CGAAttitudeVectorIPNS extends AbstractCGAAttitude implements iCGABivector {
    
    public CGAAttitudeVectorIPNS(CGAMultivector m){
        super(m);
        testEinf();
    }
    protected CGAAttitudeVectorIPNS(iCGAMultivector impl){
        super(impl);
    }
    public CGAAttitudeVectorIPNS(Vector3d t){
        super((new CGANormalVector(t)).op(createInf(1.0)).dual());
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
        return this.undual();
    }
    
    private void testEinf(){
        //TODO
        // Test darauf, dass alle componenten einf enthalten+
        // throw new IllegalArgumentException("T
    }
    
    
    @Override
    public CGAAttitudeVectorOPNS undual(){
        return new CGAAttitudeVectorOPNS(impl.undual());
    }
}
