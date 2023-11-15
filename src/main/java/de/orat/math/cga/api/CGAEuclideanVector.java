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
        //System.out.println(m.toString("euclidean vec"));
    }
    
    public CGAEuclideanVector(double[] values){
        super(values);
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
    
    
    // etc
    
    /**
     * Cross-product, implementation following [Dorst2009] 3.28.
     * 
     * Its orientation is depended of the coordinate systems handedness.<p>
     * 
     * FIXME
     * brauche ich das überhaupt noch?
     * ist das nicht identisch zu euclideanDual()?
     * Nein, da kommt noch ein Vorzeichen dazu
     * 
     * Das scheint zu stimmen
     * 
     * @param v2
     * @return cross product
     */
    public CGAEuclideanVector cross(CGAEuclideanVector v2){
        // das scheint auch zu stimme:
        //return new CGAEuclideanVector(this.op(v2).lc(I3i));
        // das scheint zu stimmen:
        // https://en.wikipedia.org/wiki/Comparison_of_vector_algebra_and_geometric_algebra#Cross_and_exterior_products
        //return new CGAEuclideanVector(I3.negate().gp(op(v2)));
        // eigentlich soll auch folgendes gehen:
        // das stimmt nicht, da kommt grade-3 heraus, vermutlich brauche ich euclideanDual() statt dual()
        // [Hildenbrand2004]
        // aber mit div(i3) statt dual() stimmts:
        return new CGAEuclideanVector(op(v2).div(I3));
    }
    
    public CGAEuclideanBivector euclideanDual(){
       return new CGAEuclideanBivector(this.lc(I3i)); // [Dorst2009] p.80
       // hat auch nichts gebracht, Vorzeichen scheint immer noch falsch zu sein
       //TODO
       // https://arxiv.org/pdf/1205.5935.pdf eq. 90
       //return new CGAEuclideanBivector(div(I3));
    }
}
