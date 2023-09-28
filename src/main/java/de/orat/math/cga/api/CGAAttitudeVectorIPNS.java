package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Vector3d;

/**
 * Also called free or direction vector, elements without position/location in 
 * IPNS representation corresponding to dual direction vector or dual free 
 * vector in [Dorst2007] (grade 3).
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
        // das scheint mir aber gar nicht richtig zu sein
        //super((new CGAEuclideanVector(t)).op(inf).undual());
        // scheint falsches Vorzeichen zu liefern
        // ist euclideanDual() falsch?
        // oder die verwendete Formel?
        //FIXME
        // beim Test von CGAEuclideanVector selbst mit euclideanDual stimmt das
        // Vorzeichen auch schon nicht
        // vermutlich ist euclideanDual() falsch
        super((new CGAEuclideanVector(t)).euclideanDual().negate().op(inf));
    }
    
    
    // decomposition 
    
    public Vector3d attitude(){
        CGAAttitudeVectorOPNS attitude = attitudeIntern();
        return attitude.extractAttitudeFromEeinfRepresentation();
        //TODO
        // mit was muss ich den multivector multiplizieren um die betreffenden 
        // Komponenten dann mit attitude.extractE3ToVector3d() abspalten zu können?
    }
    @Override
    protected CGAAttitudeVectorOPNS attitudeIntern(){
        return undual();
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
        return new CGAAttitudeVectorOPNS(super.undual().compress()/*dual().gp(-1d)*/);
    }
}