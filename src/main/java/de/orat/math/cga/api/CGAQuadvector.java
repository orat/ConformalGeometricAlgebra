package de.orat.math.cga.api;

import org.jogamp.vecmath.Tuple3d;

/**
 * A Quadvector describes spheres and planes in OPNS representation, ...
 * 
 * Quadvectors are linear combinations of Blades with grade 4.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAQuadvector extends CGAMultivector implements iCGAQuadvector {

    public CGAQuadvector(CGAMultivector m){
        super(m.impl);
    }
    
    public CGAQuadvector(Tuple3d a,Tuple3d b,Tuple3d c, Tuple3d d){
         this((new CGAE3Vector(a)).op(
                (new CGAE3Vector(b)).op(
                (new CGAE3Vector(c))).op(new CGAE3Vector(d))));
    }
    public CGAQuadvector(Tuple3d a,Tuple3d b,Tuple3d c){
         this((new CGAE3Vector(a)).op(
                (new CGAE3Vector(b)).op(
                (new CGAE3Vector(c))).op(CGAMultivector.createInf(1d))));
    }
    
    /*@Override
    public int grade() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }*/
    
}
