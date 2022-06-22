/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createEinf;
import org.jogamp.vecmath.Point3d;

/**
 * Dual sphere (inner product null space representation) as a multivector of grade 4.
 * 
 * corresponds to Quadvector
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADualSphere extends CGAMultivector {
    
    /**
     * Create dual sphere.
     * 
     * Multiplication of the resulting multivector by double alpha is possible.
     * 
     * @param o origin of the sphere
     * @param p result on the sphere
     */
    public CGADualSphere(Point3d o, Point3d p){
        super((new CGAPoint(p)).ip(createEinf(1d).op((new CGAPoint(o)))).impl);
    }
    
    /**
     * Create dual sphere in outer product null space representation (grade 4 multivector).
     * 
     * @param p1 multivector representing a point on the sphere
     * @param p2 multivector representing a point on the sphere
     * @param p3 multivector representing a point on the sphere
     * @param p4 multivector representing a point on the sphere
     */
    public CGADualSphere(CGAMultivector p1, CGAMultivector p2, 
                         CGAMultivector p3, CGAMultivector p4){
        super(p1.op(p2).op(p3).op(p4).impl);
    }
    
    /**
     * Create dual sphere in outer product null space represenation (grade 4 multivector).
     * 
     * @param p1 a point on the sphere
     * @param p2 a point on the sphere
     * @param p3 a point on the sphere
     * @param p4 a point on the sphere
     */
    public CGADualSphere(Point3d p1, Point3d p2, Point3d p3, Point3d p4){
        super((new CGAPoint(p1)).op((new CGAPoint(p2))).op((new CGAPoint(p3))).op((new CGAPoint(p4))).impl);
    }
}
