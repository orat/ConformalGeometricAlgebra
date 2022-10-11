package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;

/**
 * A versor is a multivector that can be expressed as the geometric product of 
 * a number of non-null 1-vectors. 
 * 
 * A sum of two versors does not in general result in a versor!<p>
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAVersor extends CGAMultivector {
     
    public CGAVersor(CGAMultivector m){
        super(m.impl);
        //if (!m.isVersor()) throw new IllegalArgumentException("Construction of versor object from "
        //        +m.toString()+" failed!");
    }
    public CGAVersor(iCGAMultivector impl){
        super(impl);
    }
    
    public CGAMultivector transform(CGAMultivector m){
         return this.gp(m).gp(this.reverse());
    }
    public boolean isVersor(){
       return true;
    }
    
    /**
     * An more efficient implementation can use the information that the multivector 
     * a versor.
     * 
     * The inverse of a basis blade coincides with the conjugate.
     * 
     * @return the default implementation is identical to generalInverse()
     */
    @Override
    public CGAVersor inverse(){
        //TODO eine spezifische implementation for versors only bauen
        return new CGAVersor(impl.versorInverse());
    }
}
