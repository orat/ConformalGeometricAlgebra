package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;

/**
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
    
    public boolean isVersor(){
       return true;
    }
    
    @Override
    public CGAVersor inverse(){
        return new CGAVersor(impl.versorInverse());
    }
}
