package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * Also called free or direction vector [Dorst2007)] or a free blade, elements 
 * without position (grade 2). 
 * 
 * e1^ni, e2^ni, e3^ni<p>
 * 
 * Just as tangents support round elements, so do directions support flat elements. <p>
 * 
 * It represents a direction without a location. It is translation 
 * invariant but rotation covariant. This means there is no e0-component in its formula.<p>
 * 
 * A free vector does not have a position. Given the normal vector n, it can be 
 * calculated as follows: n ∧ e ∞ .<p>
 * 
 * Drawn dashed at origin.<p>
 * 
 * It is a one-dimensional attitude, e.g. a direction vector. A line is build from 
 * this by outer product with a point. 
 * 
 * It is translation invariant.<p>
 * 
 * This means there is no e0-component in its formula.<p>
  * 
 * It is a one-dimensional attitude, e.g. a direction vector. A line is build from 
 * this by outer product with a point.<p>
 * 
 * Computed by the meet/outer product of two parallel lines with normalvector n.<p>
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeVectorOPNS extends CGAAttitudeOPNS implements iCGABivector {
    
    public CGAAttitudeVectorOPNS(CGAMultivector m){
        super(m);
        testDefiningProperties();
    }
    
    protected CGAAttitudeVectorOPNS(iCGAMultivector impl){
        super(impl);
        testDefiningProperties();
    }
    
    
    // composition 
    
    /**
     * 
     * @param t direction vector. Its length is a distance of two parallel planes 
     * this attitude corresponds to. 
     * 
     * The atttidue can be computed by the outer product
     * of two parallel lines with direction t and distance of the length of t.
     */
    public CGAAttitudeVectorOPNS(Vector3d t){
        super((new CGAEuclideanVector(t)).op(inf));
    }
    
    
    // decomposition 
    
    @Override
    public Vector3d direction(){
        return extractAttitudeFromEeinfRepresentation();
    }
    
    
    // etc
    
    private void testDefiningProperties(){
        if (!inf.op(this).isNull()){
            throw new IllegalArgumentException("einf^X != 0");
        }
        //FIXME
        // warum geht das nicht, bzw. warum sollte der folgende test eigentlich funktionieren
        /*if (!inf.ip(this).isNull()){
            throw new IllegalArgumentException("einf . X != 0");
        }*/
    }
    
    @Override
    public CGAAttitudeVectorIPNS dual(){
        return new CGAAttitudeVectorIPNS(impl.dual().getCompressed());
    }
}
