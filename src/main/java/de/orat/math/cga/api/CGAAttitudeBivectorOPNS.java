package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Tuple3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Direction/attitude bivector of grade 3.
 * 
 * A 2-dimensional direction element. Drawn sippled at the origin. 
 *  
 * e1^e2^ni, e2^e3^ni, e3^e1^ni
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAAttitudeBivectorOPNS extends CGAAttitudeOPNS implements iCGATrivector {
    
    public CGAAttitudeBivectorOPNS(CGAMultivector m){
        super(m);
    }
    
    protected CGAAttitudeBivectorOPNS(iCGAMultivector impl){
        super(impl);
    }
    
    
    // composition 
    
    public CGAAttitudeBivectorOPNS(CGAEuclideanBivector B){
        this(B.op(inf));
    }
    
    public CGAAttitudeBivectorOPNS(Tuple3d v1, Tuple3d v2){
        super((new CGAEuclideanVector(v1)).op(new CGAEuclideanVector(v2)).op(inf));
    }
    
    
    // decomposition
    
    /* Extract attitude/direction from Bivector^einf multivector representation.
     * 
     * example: -1.9999999999999991*e1^e2^ei + 1.9999999999999991*e1^e3^ei + 1.9999999999999991*e2^e3^ei
*/
    @Override
    public Vector3d direction(){
        CGAMultivector m = extractGrade(3).rc(o).negate().lc(I3i); //euclideanDual(); //extractE3ToVector3d();
        //System.out.println("###"+m.toString("extractAttFromBiVecEinf")+" "+toString("orig")+
        //        " vec=("+String.valueOf(v.x)+","+String.valueOf(v.y)+","+String.valueOf(v.z)+")");
        return m.extractE3ToVector3d();
        //return extractAttitudeFromBivectorEinfRepresentation();
    }
    
    
    // etc
    
    @Override
    public CGAAttitudeBivectorIPNS dual(){
        return new CGAAttitudeBivectorIPNS(impl.dual());
    }
    
    public CGAKVector undual(){
        throw new RuntimeException("undual() not supported for generic opns attitude bivector!");
    }
}
