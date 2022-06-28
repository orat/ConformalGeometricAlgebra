package de.orat.math.cga.api;

/**
 * Flat points are null vectors wedged with Infinity: p ∧ ∞. 
 * 
 * Flat points are blades with grade 2. 
 * 
 * Flat points are the result of an intersection between a line and a plane, and 
 * they are useful for describing potential elements within the algebra. 
 * For instance, given a dual line λ and a flat point q not on the line, their 
 * union λ ∧ q defines a dual 
 * plane π through q orthogonal to λ. Similarly, the contraction of a flat point 
 * from a direct line q'Λ defines a direct plane Π. Another example: given a dual 
 * circle (a point pair) τ and a flat point q, their union τ ∧ q defines a dual 
 * plane that goes through the axis of the circle τ and the point q. We can also 
 * construct such a relationship with the contraction product – given a direct 
 * circle κ, the contraction with a point q'κ returns a direct plane that goes 
 * through the circle κ and the point q.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAFlatPoint extends CGAFlat implements iCGABivector {
    
    public CGAFlatPoint(CGAMultivector m){
        super(m);
    }
   
    public CGAFlatPoint(CGARoundPoint p){
        this(p.op(createInf(1d)));
    }
}
