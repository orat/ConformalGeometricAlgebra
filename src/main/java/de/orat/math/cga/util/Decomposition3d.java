package de.orat.math.cga.util;

import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class Decomposition3d {
    
    public record PointPairParameters(Point3d p1, Point3d p2){}
    
    public record FlatAndDirectionParameters(Vector3d attitude, Point3d location){}
    
    /**
     * Parameters describing round objects (circles, spheres) in 3d euclidian space.
     * 
     * @param attitude normalized attitude (e.g normal vector of the plane a circle lays in)
     * @param squaredSize squared size (e.g. squared-radius for a sphere)
     */
    public record RoundAndTangentParameters(Vector3d attitude, Point3d location, double squaredSize){}
    
    // statt location und atttitude, vielleciht besser location1 und location2?
    public record LinePairParameters(double alpha, Point3d location, Vector3d attitude){}
}
