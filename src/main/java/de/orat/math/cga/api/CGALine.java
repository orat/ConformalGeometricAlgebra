package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;

/**
 * 
 * Generates the motor algebra. 
 * 
 * There are many ways of finding a line: e.g. 
 * the central axis l of a circle σ can be found by contraction with infinity: ∞⌋σ = l.
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGALine extends CGAFlat implements iCGABivector {
    
    public CGALine(CGAMultivector m){
        super(m);
    }
    CGALine(iCGAMultivector m){
        super(m);
    }
    /**
     * Create line in inner product null space representation (grade 2 multivector).
     * 
     * Be careful: This corresponds to a dual line in Dorst2007.
     * 
     * @param plane1 plane1 in inner product null space representation
     * @param plane2 plane2 in inner product null space representation
     */
    public CGALine(CGAPlane plane1, CGAPlane plane2){
        this(plane1.op(plane2));
    }
    
    public CGALine(CGAPointPair pointPair){
        this(pointPair.op(createInf(1d)));
    }
     
    public CGALine(CGAPoint point, CGAAttitudeVector direction){
        this(point.op(direction));
    }
   
    @Override
    public CGADualLine dual(){
        return new CGADualLine(impl.dual());
    }
    
    /**
     * Is this 3-vector representing a line.
     * 
     * Lines Classification in the Conformal Space R{n+1,1}
     * Lilian Aveneau and Ángel F. Tenorio
     * 
     * @return true if this object represents a line.
     */
    /*public boolean isLine(){
        //TODO
        // 1. is 3-blade ist ja bereits klar
        // 2. op(einf) = 0;
        // 3. Richtung ungleich 0, d.h. einf leftcontraction this != 0
        return true;
    }*/
}
