package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;

/**
 * A blade is a multivector, which contains only base vectors of the same grade.
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
}
