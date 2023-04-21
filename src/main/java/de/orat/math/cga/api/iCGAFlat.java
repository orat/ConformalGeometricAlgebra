package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iCGAFlat {
    
    public EuclideanParameters decomposeFlat();
    public EuclideanParameters decomposeFlat(Point3d probePoint);
    
    //public EuclideanParameters decompose();
    //public EuclideanParameters decompose(Point3d probePoint);
    public record EuclideanParameters(Vector3d attitude, Point3d location, 
                                      double squaredWeight){}
}
