package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;

/**
 * A versor is a multivector that can be expressed as the geometric product of 
 * invertable vectors, especially of non-null 1-vectors.
 * 
 * Hint: A sum of two versors does not in general result in a versor!<p>
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public abstract class CGAVersor extends CGAMultivector {
     
    public CGAVersor(CGAMultivector m){
        super(m.impl);
        //if (!m.isVersor()) throw new IllegalArgumentException("Construction of versor object from "
        //        +m.toString()+" failed!");
    }
    CGAVersor(iCGAMultivector impl){
        super(impl);
    }
    
    public CGAMultivector transform(CGAMultivector m){
        // even number of blades
        if (isEven()){
            return this.gp(m).gp(this.inverse());
        } 
        // odd number of blades
        return this.gp(m.gradeInversion()).gp(this.inverse());
    }
    
    public abstract boolean isEven();
    
    public boolean isVersor(){
       return true;
    }
}
