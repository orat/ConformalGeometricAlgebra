package de.orat.math.cga.test;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class LineTuple3dComposition {
    Vector3d u;
    Point3d p;
    
    public LineTuple3dComposition(Point3d p, Vector3d n){
        this.u = n;
        this.p = p;
    }
    
    /**
     * 
     * Drehrichtung???
     * 
     * @param l2
     * @return angle in [deg]
     */
    public double angle(LineTuple3dComposition l2){
        Vector3d temp = new Vector3d();
        temp.cross(u, l2.u);
        double sin = temp.length()/(u.length()*l2.u.length());
        double cos = u.dot(l2.u)/(u.length()*l2.u.length());
        return Math.atan2(sin, cos)*180/Math.PI;
    }
    
    /**
     * Directed distance from line 1 to line 2.
     * 
     * @param l2 line 2
     * @return directed distance (from this/l1 to l2)
     */
    public double dist(LineTuple3dComposition l2){
        Vector3d a = new Vector3d(p);
        a.sub(l2.p);
        Vector3d b = new Vector3d();
        b.cross(u, l2.u);
        return a.dot(b)/b.length();
    }
    
    public Point3d[] intersect(LineTuple3dComposition l2){
        Point3d[] result = new Point3d[2];
        
        Vector3d u1Cu2 = new Vector3d();
        u1Cu2.cross(u,l2.u);
        
        
        // 1. Punkt
        
        Vector3d u2Cu1Cu2 = new Vector3d();
        u2Cu1Cu2.cross(l2.u, u1Cu2);
        
        Vector3d p21 = new Vector3d(l2.p);
        p21.sub(p);
        
        result[0] = new Point3d(u);
        result[0].scale(u2Cu1Cu2.dot(p21)/(u2Cu1Cu2.dot(u)));
        result[0].add(p);
        
        
        // 2. Punkt
        
        Vector3d u1Cu1Cu2 = new Vector3d();
        u1Cu1Cu2.cross(u, u1Cu2);
        
        Vector3d p12 = new Vector3d(p);
        p12.sub(l2.p);
        
        result[1] = new Point3d(l2.u);
        result[1].scale(u1Cu1Cu2.dot(p12)/u1Cu1Cu2.dot(l2.u));
        result[1].add(l2.p);
        return result;
    }
}
