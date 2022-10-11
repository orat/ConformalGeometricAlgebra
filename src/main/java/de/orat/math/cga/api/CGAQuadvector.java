package de.orat.math.cga.api;

import org.jogamp.vecmath.Tuple3d;

/**
 * A Quadvector describes spheres and planes in OPNS representation, ...
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAQuadvector extends CGAMultivector implements iCGAQuadvector {

    public CGAQuadvector(CGAMultivector m){
        super(m.impl);
    }
    
    public CGAQuadvector(Tuple3d a,Tuple3d b,Tuple3d c, Tuple3d d){
         this((new CGANormalVector(a)).op(
                (new CGANormalVector(b)).op(
                (new CGANormalVector(c))).op(new CGANormalVector(d))));
    }
    public CGAQuadvector(Tuple3d a,Tuple3d b,Tuple3d c){
         this((new CGANormalVector(a)).op(
                (new CGANormalVector(b)).op(
                (new CGANormalVector(c))).op(CGAMultivector.createInf(1d))));
    }
    
    /*@Override
    public int grade() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }*/
    
}
