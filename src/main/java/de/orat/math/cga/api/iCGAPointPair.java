package de.orat.math.cga.api;

import org.jogamp.vecmath.Point3d;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iCGAPointPair {
    
    public record PointPair(Point3d p1, Point3d p2){};
    public PointPair decomposePoints();
}
