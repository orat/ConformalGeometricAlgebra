package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createE3;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Tuple3d;
import org.jogamp.vecmath.Vector3d;

/**
 * An euclidean vector (e1, e2, e3).
 * 
 * TODO implement test-classes for the basisblades.
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAEuclideanVector extends AbstractEuclideanKVector implements iCGAVector {
    
    public CGAEuclideanVector(CGAMultivector m){
        super(m);
        // TODO test dass e0, einf nicht vorhanden ist!!!
    }
    
    
    // composition
    
    public CGAEuclideanVector(Tuple3d t){
        this(createE3(t));
    }
    
    
    // decomposition
    
    public double squaredSize(){
        return extractE3ToVector3d().lengthSquared();
    }
    
    @Override
    public Point3d location(){
        return extractE3ToPoint3d();
    }
    public Vector3d direction(){
        return extractE3ToVector3d();
    }
}
