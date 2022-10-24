package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;

/**
 * A blade is a multivector, which contains only base vectors of the same grade 
 * (0..5).
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGABlade extends CGAMultivector implements iCGABlade {
    
    CGABlade(CGAMultivector m){
        super(m.impl);
        try {
            testGrade();
        } catch (IllegalArgumentException e){
            System.out.println(m.toString());
            throw(e);
        }
    }
    CGABlade(iCGAMultivector impl){
        super(impl);
        try {
            testGrade();
        } catch (IllegalArgumentException e){
            System.out.println(impl.toString());
            throw(e);
        }
    }
    CGABlade(double value){
        super(value);
    }
    
    // plane_through_point_tangent_to_x  = (point,x)=>point^ni<<x*point^ni;
    public CGARoundPointIPNS reject(CGARoundPointIPNS p){
        CGAMultivector ni = CGAMultivector.createInf(1d);
        return new CGARoundPointIPNS(p.op(ni).lc(this).gp(p.op(ni)));
    }
    
    @Override
    public CGABlade undual(){
        return new CGABlade(impl.undual());
    }
    @Override
    public CGABlade dual(){
        return new CGABlade(impl.dual());
    }
}
