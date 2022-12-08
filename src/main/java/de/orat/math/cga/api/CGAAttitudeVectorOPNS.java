package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * Also called free or direction vector (Dorst2007) or a free blade, elements 
 * without position (grade 2). 
 * 
 * e1^ni, e2^ni, e3^ni
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
 * 
 * Drawn dashed at origin.
 * 
 * It is a one-dimensional attitude, e.g. a direction vector. A line is build from 
 * this by outer product with a point. 
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
    
    public CGAAttitudeVectorOPNS(Vector3d t){
        super((new CGAE3Vector(t)).op(inf));
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
        return new CGAAttitudeVectorIPNS(impl.dual());
    }
}
