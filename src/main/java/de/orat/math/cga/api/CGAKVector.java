package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;

/**
 * A k-Vector is a multivector which is a linear combination of the 32 basis blades 
 * (in form of a linear combination) of the same grade k (0..5). 
 * 
 * It is also called blade or k-blade or shorter a blade.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGAKVector extends CGAMultivector implements iCGABlade {
    
    CGAKVector(CGAMultivector m){
        super(m.impl);
        try {
            testGrade();
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage()+" "+impl.toString());
            throw(e);
        }
    }
    CGAKVector(iCGAMultivector impl){
        super(impl);
        try {
            testGrade();
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage()+" "+impl.toString());
            throw(e);
        }
    }
    CGAKVector(double value){
        super(value);
    }
    
    /**
     * Determine the carier flat of a (direkt) round or a (direkt) flat.
     * 
     * point pair e.g.  carrierFlat = (-1.9999999999999987*e1 + 1.9999999999999987*e2) mit w=-8
     * carrierFlat ist der Richtungsvektor von p2 nach p1
     * attitude (round/tangent) = (1.9999999999999996*e1^ei - 1.9999999999999996*e2^ei)
     * attitude (CGAOrientedPointPairOPNS)=1.9999999999999996*e1^ei - 1.9999999999999996*e2^ei
     * attitude (pp OPNS) = (1.9999999999999996,-1.9999999999999996,0.0)
     * attitude (round/tangent) = (1.9999999999999996*e1^ei - 1.9999999999999996*e2^ei)
     * r wird zu -1.75 bestimmt statt 0.5
     * 
     * 
     * circle e.g. carrierFlat = (0.9999999999999993*e1^e2 - 0.9999999999999993*e1^e3 + 0.9999999999999993*e2^e3)
     * 
     * @return 
     */
    CGAKVector carrierFlat(){
        return new CGAKVector(this.op(inf).negate().rc(E));
    }
    
    /**
     * Determine the location of the geometric object which is represented by
     * the k-Vector. For a flat object this is defined by the perpendicular 
     * distance vector of the origin to the carrier plane.
     * 
     * @return 
     */
    /*@Override
    public Point3d location(){
        CGAMultivector result = carrierFlat().inverse().gp(this.op((new CGAScalar(1d).add(E)))).rc(E);
        return result.extractE3ToPoint3d();
    }*/
    
    // plane_through_point_tangent_to_x  = (point,x)=>point^ni<<x*point^ni;
    public CGARoundPointIPNS reject(CGARoundPointIPNS p){
        return new CGARoundPointIPNS(p.op(inf).lc(this).gp(p.op(inf)));
    }
    
    @Override
    public CGAKVector undual(){
        return new CGAKVector(impl.dual().gp(-1));
    }
    @Override
    public CGAKVector dual(){
        return new CGAKVector(impl.dual());
    }
}
