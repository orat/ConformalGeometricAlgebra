package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;

/**
 * A blade is a multivector, which contains (in form of a linear combination)
 * only base blades of the same grade (0..5). It is also called k-blade oder k-vector.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGAkBlade extends CGAMultivector implements iCGABlade {
    
    CGAkBlade(CGAMultivector m){
        super(m.impl);
        try {
            testGrade();
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage()+" "+impl.toString());
            throw(e);
        }
    }
    CGAkBlade(iCGAMultivector impl){
        super(impl);
        try {
            testGrade();
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage()+" "+impl.toString());
            throw(e);
        }
    }
    CGAkBlade(double value){
        super(value);
    }
    
    // plane_through_point_tangent_to_x  = (point,x)=>point^ni<<x*point^ni;
    public CGARoundPointIPNS reject(CGARoundPointIPNS p){
        CGAMultivector ni = CGAMultivector.createInf(1d);
        return new CGARoundPointIPNS(p.op(ni).lc(this).gp(p.op(ni)));
    }
    
    @Override
    public CGAkBlade undual(){
        return new CGAkBlade(impl.undual());
    }
    @Override
    public CGAkBlade dual(){
        return new CGAkBlade(impl.dual());
    }
}
