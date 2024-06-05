package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;

/**
 * A versor is a multivector that can be expressed as the geometric product of 
 * invertable vectors, especially of non-null 1-vectors. 
 * 
 * A Versor can combine reflections, rotations and anisotropic scaling.<p>
 * 
 * Hint: A sum of two versors does not in general result in a versor!<p>
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public abstract class CGAVersor extends CGAMultivector {
     
    public CGAVersor(CGAMultivector m){
        super(m.impl);
        // das geht so nicht
        //if (!m.isVersor()) throw new IllegalArgumentException("Construction of versor object from "
        //        +m.toString()+" failed!");
    }
    CGAVersor(iCGAMultivector impl){
        super(impl);
    }
    
    // TODO Umstellung der API mit generics sodass spezifischere Class als Argument
    // und Rückgabewert möglich werden
    public CGAMultivector transform(CGAMultivector m){
        // even number of blades
        if (isEven()){
            return this.gp(m).gp(this.reverse());
        } 
        // odd number of blades
        CGAMultivector result = this.gp(m.gradeInversion()).gp(this.inverse());
        
        result = result.compress();
        return result;
    }
    
    // eigentlich sollte ich doch mit log() den Bivector aus dem Rotor wieder rausbekommen
    //TODO
    // decompose()
    
    /**
     * is even?
     * 
     * @return true if versor is even, false if Versor is odd
     */
    public abstract boolean isEven();
    
    public boolean isVersor(){
       return true;
    }
}
