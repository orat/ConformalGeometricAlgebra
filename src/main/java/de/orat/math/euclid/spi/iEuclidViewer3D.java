package de.orat.math.euclid.spi;

import java.awt.Color;
import org.jogamp.vecmath.Matrix3d;
import org.jogamp.vecmath.Matrix4d;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iEuclidViewer3D {
    /**
     * @param location location of the point
     * @param label name of the point
     * @param color color
     * @return id id to reference the object for later transformation
     */
    public long addSphere(Point3d location, double radius, Color color, String label);
    //public long addOrientedPoint(Vector3d attitude, Point3d location, Color color, String label);
    //public long addPointPair(Point3d p1, Point3d p2, String label, Color color1, Color color2, double lineRadius, double pointRadius);
    public long addLine(Point3d p1, Point3d p2, Color color, double radius, String label);
    public long addArrow(Point3d location, Vector3d direction, double radius, Color color, String label);
    public long addCircle(Point3d location, Vector3d normal, double radius, Color color, String label, boolean isDahed);
    public long addPlane(Point3d location, Vector3d normal, Color color, String label, boolean showNormal);
    //public long addSphere(Point3d location, double radius, Color color, String label);
    /**
     * 
     * @param type robot type, default=ur5e=0
     * @param location
     * @param orientation
     * @param label
     * @param color
     * @return 
     */
    public long addRobot(int type, Point3d location, Matrix3d orientation);
    public void moveRobot(double[] angels);
    
    public void transform(long id, Matrix4d transform);
}
