package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iCGATangentOrRound {
    
    public EuclideanParameters decompose();
    public record EuclideanParameters(Vector3d attitude, Point3d location, 
                                      double squaredSize, double squaredWeight){}
    
}
