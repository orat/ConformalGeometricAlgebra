package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGALinePair extends CGAMultivector {
    
    public CGALinePair(CGALineOPNS line1, CGALineOPNS line2){
        super(line1.gp(line2).compress().impl);
    }
    
    
    // decompose
    
    // statt location und atttitude, vielleciht besser location1 und location2?
    public record LinePairParameters(double alpha, Point3d location, Vector3d attitude){}
    
    /**
     * Decompose the geometric product of two lines.
     *
     * @return dij and P if l1 and l2 are not coincident and not parallel else an empty array
     */
    public LinePairParameters decomposeLinePair(){
        
        // X soll eine sum aus 1- und 2-blade sein
        CGAMultivector X = sub(o.ip(this).op(inf)).compress();
        System.out.println(X.toString("X"));
        
        // sum aus 3- und 1-blade
        CGAMultivector Y = o.ip(this);
        System.out.println("Y="+Y.toString());
        CGAMultivector Y3 = Y.extractGrade(3);
        System.out.println(Y3.toString("Y3"));
        
        CGAMultivector X2 = X.extractGrade(2);
        System.out.println(X2.toString("X2"));
        
         // quatrieren und test auf !=0
        CGAMultivector X22 = X2.gp(X2);
        
        
        // identisch
        if (this.isScalar()){
            return new LinePairParameters(0d, null, null);
        // coplanar
        } else if (X2.isNull()){
            // parallel
            if (Y3.op(inf).isNull()){
                Vector3d dir = new Vector3d();
                //TODO
                return new LinePairParameters(0d, null, dir);
            // coplanar mit Schnittpunkt    
            } else {
                //TODO
                double alpha=0;
                Point3d p = new Point3d();
                //TODO
                return new LinePairParameters(alpha, p, null);
            }
        // skewed
        } else {
            CGAMultivector d = Y3.gp(X2.reverse().gp(1d/X2.squaredNorm()));
            System.out.println("d="+d.toString());

            double[] dValues = d.impl.extractCoordinates(2);
         
            double alpha = 0d;
            Vector3d p = new Vector3d(); 
            return new LinePairParameters(alpha, new Point3d(p.x,p.y,p.z), 
                    //TODO indizes?
                    new Vector3d(dValues[0], dValues[1], dValues[2]));
        }
    }
    
    
    
    /**
      * Decompose l2l1 into angle, distance, direction.
      * 
      * A Covariant approach to Geometry using Geometric Algebra.
      * Technical report. Universit of Cambridge Departement of Engineering, 
      * Cambridge, UK (2004). 
      * A. Lasenby, J. Lasenby, R. Wareham
      * Formula 5.22, page 46
      * 
      * @param l2l1
      * @return parameters describing the pose of two lines to each other
      */
    public static LinePairParameters decomposeLinePair(CGAMultivector l2l1){
        
        System.out.println("l2l1:"+l2l1.toString());
        
        // Skalar
        double cosalpha = l2l1.impl.extractCoordinates(0)[0];
        System.out.println("cosalpha="+String.valueOf(cosalpha));
        System.out.println("alpha="+String.valueOf(Math.acos(cosalpha)*180/Math.PI));
       
        // Bivektoren 
        double[] bivectors = l2l1.impl.extractCoordinates(2);
        
        double[] quadvectors = l2l1.impl.extractCoordinates(4);
        
        // attitude zeigt von l1 nach l2?
        
        double dist = 0d;
        double sinalpha = 0;
        
        org.jogamp.vecmath.Vector3d attitude = null;
        
        // Geraden nicht senkrecht zueinander
        if (cosalpha != 0){
            System.out.println("attitude aus e01, e02 und e03 bestimmen!");
            attitude = new org.jogamp.vecmath.Vector3d(
                -bivectors[0]/cosalpha, -bivectors[1]/cosalpha, -bivectors[2]/cosalpha);
            System.out.println("d(vectors)= ("+String.valueOf(attitude.x)+", "+String.valueOf(attitude.y)+", "+String.valueOf(attitude.z)+")");
            dist = attitude.length();
            System.out.println("dist = "+dist);
            attitude.normalize();
            System.out.println("d(vectors) normiert= ("+String.valueOf(attitude.x)+", "+String.valueOf(attitude.y)+", "+String.valueOf(attitude.z)+")");
            
        } 
            
        // Geraden sind nicht parallel
        if (cosalpha*cosalpha != 1){
            System.out.println("attitude aus e23, e13, e12 und e0123 bestimmen!");
            double cos2alpha = 1d-cosalpha*cosalpha;
            attitude = new org.jogamp.vecmath.Vector3d(
                -bivectors[7]*quadvectors[0]/cos2alpha, 
                 bivectors[5]*quadvectors[0]/cos2alpha, 
                -bivectors[4]*quadvectors[0]/cos2alpha);
            System.out.println("d(vectors)= ("+String.valueOf(attitude.x)+", "+String.valueOf(attitude.y)+", "+String.valueOf(attitude.z)+")");
            dist = attitude.length();
            System.out.println("dist = "+dist);
            attitude.normalize();
            System.out.println("d(vectors) normiert= ("+String.valueOf(attitude.x)+", "+String.valueOf(attitude.y)+", "+String.valueOf(attitude.z)+")");
            
        } 
         
        // Geraden haben keinen Schnittpunkt
        if (dist != 0d){
            sinalpha = -quadvectors[0]/dist;
        } else {
            System.out.println("Geraden schneiden sich!");
            //FIXME
            // ist das so richtig?
            sinalpha = 0d;
        }
        //TODO
        Point3d location = null;
        
        return new LinePairParameters(Math.atan2(cosalpha, sinalpha), location, attitude);
    }
}
