package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * Also called free or direction vector, elements without position/location in 
 * IPNS representation corresponding to dual direction vector or dual free 
 * vector in Dorst2007 (grade 3).
 * 
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeVectorIPNS extends CGAAttitudeIPNS implements iCGATrivector {
    
    public CGAAttitudeVectorIPNS(CGAMultivector m){
        super(m);
        testDefiningProperties();
    }
    
    protected CGAAttitudeVectorIPNS(iCGAMultivector impl){
        super(impl);
        testDefiningProperties();
    }
    
    
    // composition
    
    public CGAAttitudeVectorIPNS(Vector3d t){
        super((new CGAEuclideanVector(t)).op(inf).dual());
    }
    
    
    // decomposition 
    
    public Vector3d attitude(){
        CGAMultivector attitude = attitudeIntern();
        return attitude.extractAttitudeFromEeinfRepresentation();
        //TODO
        // mit was muss ich den multivector multiplizieren um die betreffenden 
        // Komponenten dann mit attitude.extractE3ToVector3d() abspalten zu können?
    }
    @Override
    protected CGAAttitudeVectorOPNS attitudeIntern(){
        return this.undual();
    }
    
    
    // etc
    
    private void testDefiningProperties(){
        if (!inf.op(this).isNull()){
            throw new IllegalArgumentException("einf^X != 0");
        }
        if (!inf.ip(this).isNull()){
            throw new IllegalArgumentException("einf . X != 0");
        }
    }
    
    @Override
    public CGAAttitudeVectorOPNS undual(){
        return new CGAAttitudeVectorOPNS(impl.dual().gp(-1d));
    }
}