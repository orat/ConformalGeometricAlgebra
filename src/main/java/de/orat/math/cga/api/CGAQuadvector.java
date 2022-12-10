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
         this((new CGAEuclideanVector(a)).op(
                (new CGAEuclideanVector(b)).op(
                (new CGAEuclideanVector(c))).op(new CGAEuclideanVector(d))));
    }
    public CGAQuadvector(Tuple3d a,Tuple3d b,Tuple3d c){
         this((new CGAEuclideanVector(a)).op(
                (new CGAEuclideanVector(b)).op(
                (new CGAEuclideanVector(c))).op(inf)));
    }
    
    /*@Override
    public int grade() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }*/
    
}
