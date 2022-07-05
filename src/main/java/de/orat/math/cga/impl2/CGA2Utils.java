package de.orat.math.cga.impl2;

import de.orat.math.cga.api.CGAMultivector;
import de.orat.math.cga.util.Decomposition3d;
import de.orat.math.cga.util.Decomposition3d.FlatAndDirectionParameters;
import de.orat.math.cga.util.Decomposition3d.LinePairParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * @Deprecated code nach CGA2Multivector erschieben
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGA2Utils {
    
    /**
     * Angle between two lines.
     * 
     * Kleppe2016
     * 
     * @param l1 line1
     * @param l2 line2
     * @return angle [rad]
     */
    public static double angle(CGA2Multivector l1, CGA2Multivector l2){
        // Richtungsvektor von l1
        CGA2Multivector a = (l1.ip(CGA2Multivector.defaultInstance.createOrigin(1d))).ip(
                CGA2Multivector.defaultInstance.createInf(1d));
        System.out.println("a:\n"+a.toString());
        
        //FIXME bereits bei a sind alle Komponenten = 0
        
        CGA2Multivector b = l2.ip(CGA2Multivector.defaultInstance.createOrigin(1d)).ip(
                CGA2Multivector.defaultInstance.createInf(1d));
        System.out.println("b:\n"+b.toString());
        CGA2Multivector an = new CGA2Multivector(a.normalized());
        CGA2Multivector bn = new CGA2Multivector(b.normalized());
        
        CGA2Multivector nn = new CGA2Multivector(bn.op(an).normalized());
        System.out.println("nn:\n"+nn.toString());
        CGA2Multivector y = a.op(b).ip(nn);
        CGA2Multivector x = a.ip(b);
        System.out.println("y:\n"+y.toString());
        System.out.println("x:\n"+y.toString());
        
        return Math.atan2(y.extractCoordinates(0)[0], x.extractCoordinates(0)[0]);
    }
    
    /**
     * Angle between two lines.
     * 
     * Zhang2016
     * 
     * @param l1
     * @param l2
     * @return 
     */
    /*public static double angle2(CGA2Multivector l1, CGA2Multivector l2){
        CGA2Multivector l1l2 = l1.mul(l2);
        CGA2Multivector x = l1l2.sub(CGA2Multivector.createE0().dot(l1l2).wedge(CGA2Multivector.createInf()));
        CGA2Multivector y = CGA2Multivector.createE0().dot(l1l2);

        CGA2Multivector p = x.project(0).mul(y.project(3)).sub(x.project(1).mul(x.project(2)));
        //TODO
        // Teilen durch projection vom Grade 2 im Quadrat
        
        return 0d;
    }*/
    
    /*public static void decomposeLinePair(CGA2Multivector l){
        double[] bivectors = l.getKBlade(2);
        
        org.jogamp.vecmath.Vector3d d = new org.jogamp.vecmath.Vector3d(bivectors[4], bivectors[1], bivectors[0]);
        System.out.println("l: dx="+String.valueOf(d.x)+", dy="+String.valueOf(d.y)+", dz="+String.valueOf(d.z));
    }*/
    
  
    /*public static LinePairParameters decomposeLinePair(CGAMultivector l2l1){
        
        System.out.println("l2l1:"+l2l1.toString());
        
        // Bivektoren 
        double[] bivectors = l2l1.extractCoordinates(2);
        // d zeigt von l1 nach l2?
        org.jogamp.vecmath.Vector3d d = new org.jogamp.vecmath.Vector3d(bivectors[4], bivectors[1], bivectors[0]);
        // da sollte noch ein Faktor sin(theta) drinstecken
        System.out.println("dx="+String.valueOf(d.x)+", dy="+String.valueOf(d.y)+", dz="+String.valueOf(d.z));
     
        // Vektoren
        //double[] vectors = l2l1.extractCoordinates(1);
        //org.jogamp.vecmath.Vector3d d2 = new org.jogamp.vecmath.Vector3d(vectors[0], vectors[1], vectors[2]);
        Vector3d d2 = l2l1.extractE3ToVector3d();
        // identisch zu dx,dy,dz wo ist der sin(theta) faktor?
        System.out.println("d2x="+String.valueOf(d.x)+", d2y="+String.valueOf(d.y)+", d2z="+String.valueOf(d.z));
        
        
        // theta=163.82184787299215
        // das scheint aber falsch zu sein
        double costheta = l2l1.extractCoordinates(0)[0];
        System.out.println("theta="+String.valueOf(Math.acos(costheta)*180/Math.PI));
        
        //TODO
        double alpha = 0d;
        Point3d p = null;
        Vector3d dir = null;
        return new Decomposition3d.LinePairParameters(alpha, p, dir);
    }*/
    
     /**
      * Decompose l2l1 into angle, distance, direction.A Covariant approach to 
      * Geometry using Geometric Algebra.Technical report.Universit of Cambridge 
      * Departement of Engineering, Cambridge, UK (2004).
      * 
      * A. Lasenby, J. Lasenby, R. Wareham
      * Formula 5.22, page 46
      * 
      * @param l1
      * @param l2
      */
    public static void decompose2(CGA2Multivector l1, CGA2Multivector l2){
        CGAMultivector l1l2 = (CGAMultivector) l1.gp(l2);
      
        double costheta = l1l2.scalarPart();//extractCoordinates(0)[0];
        System.out.println("theta="+String.valueOf(Math.acos(costheta)*180/Math.PI));
        //org.jogamp.vecmath.Vector3d d = new org.jogamp.vecmath.Vector3d(l1l2.extractCoordinates(1));
        Vector3d d = l1l2.extractE3ToVector3d();
        d.scale(1d/costheta);
        System.out.println("d="+String.valueOf(d.length()));
        d.normalize();
        System.out.println("dx="+String.valueOf(d.x)+", dy="+String.valueOf(d.y)+", dz="+String.valueOf(d.z));
    }
    
    public static String toString(String name, double[] values){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("=[");
        for (int i=0;i<values.length;i++){
            sb.append(String.valueOf(values[i]));
            sb.append(", ");
        }
        sb.delete(sb.length()-2, sb.length());
        sb.append("]");
        return sb.toString();
    }
}
