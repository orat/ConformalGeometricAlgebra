package de.orat.math.cga.api;

/**
 * A blade is a multivector, which contains only base vectors of the same grade.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGABlade extends CGAMultivector implements iCGABlade {
    
    CGABlade(CGAMultivector m){
        super(m.impl);
        testGrade();
    }
    
    CGABlade(double value){
        super(value);
    }
}
