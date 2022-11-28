package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;

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
    
    // plane_through_point_tangent_to_x  = (point,x)=>point^ni<<x*point^ni;
    public CGARoundPointIPNS reject(CGARoundPointIPNS p){
        CGAMultivector ni = CGAMultivector.createInf(1d);
        return new CGARoundPointIPNS(p.op(ni).lc(this).gp(p.op(ni)));
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
