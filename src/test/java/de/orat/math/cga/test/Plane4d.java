package de.orat.math.cga.test;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class Plane4d {
    
    private Vector3d n; // (normalized) normal vector of the plane
    private double d; // distance of the plane to the origin
    
    /**
     * Plane defined by normal vector and an arbitrary point of the plane.
     * 
     * @param n normal vector of the plane
     * @param p arbitrary point of the plane
     */
    public Plane4d(Vector3d n, Point3d p){
        this.n = new Vector3d(n);
        n.normalize();
        d = n.dot(new Vector3d(p));
    }
    /**
     * Get distance of the plane to the origin of the coordinate system.
     * 
     * @return distance to the origin of the coordinate system
     */
    public double getD(){
        return d;
    }
    /**
     * Get (normalized) normal vector of the plane.
     * 
     * @return normal vector of the plane
     */
    public Vector3d getN(){
        return n;
    }
    
    /**
     * Intersection of the plane with a given line.
     * 
     * @param s line to intersect with the plane
     * @return intersection point
     */
    public Point3d intersect(DualVector3d s){
        // Chittawadigi2013: Geometric Model Identification of a serial robot
        Vector3d result = new Vector3d();
        result.cross(n, s.dual());
        Vector3d a = new Vector3d(s.real());
        double adotn = a.dot(n);
        a.scale(d);
        result.add(a);
        result.scale(1d/adotn);
        return new Point3d(result);
    }
}
