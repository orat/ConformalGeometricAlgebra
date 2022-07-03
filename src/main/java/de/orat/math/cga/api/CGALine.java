package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;

/**
 * 
 * Generates the motor algebra. 
 * 
 * There are many ways of finding a line: e.g. 
 * the central axis l of a circle σ can be found by contraction with infinity: ∞⌋σ = l.
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGALine extends CGAFlat implements iCGABivector {
    
    public CGALine(CGAMultivector m){
        super(m);
    }
    CGALine(iCGAMultivector m){
        super(m);
    }
    /**
     * Create line in inner product null space representation (grade 2 multivector).
     * 
     * Be careful: This corresponds to a dual line in Dorst2007.
     * 
     * @param plane1 plane1 in inner product null space representation
     * @param plane2 plane2 in inner product null space representation
     */
    public CGALine(CGAPlane plane1, CGAPlane plane2){
        this(plane1.op(plane2));
    }
    
    public CGALine(CGAPointPair pointPair){
        this(pointPair.op(createInf(1d)));
    }
     
    public CGALine(CGARoundPoint point, CGAAttitudeVector direction){
        this(point.op(direction));
    }
   
    @Override
    public CGADualLine dual(){
        return new CGADualLine(impl.dual());
    }
    
    /**
     * Is this 3-vector representing a line.
     * 
     * Lines Classification in the Conformal Space R{n+1,1}
     * Lilian Aveneau and Ángel F. Tenorio
     * 
     * @return true if this object represents a line.
     */
    /*public boolean isLine(){
        //TODO
        // 1. is 3-blade ist ja bereits klar
        // 2. op(einf) = 0;
        // 3. Richtung ungleich 0, d.h. einf leftcontraction this != 0
        return true;
    }*/
    
    private double weight(){
        // local weight = ( #( ( no .. ( blade ^ ni ) ) * i ) ):tonumber()
        return Math.abs((createOrigin(1d).ip(this.op(createInf(1d)))).gp(createE3()).scalarPart());
    }
    @Override
    public double squaredWeight(){
        return Math.pow(weight(),2d);
    }
    /**
     * Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     *
     * @return attitude
     */
    @Override
    protected CGAMultivector attitudeIntern(){
        // blade = blade / weight
	// local normal = ( no .. ( blade ^ ni ) ) * i
        return createOrigin(1d).ip(this.gp(1d/weight()).op(createInf(1d))).gp(createE3());
    }
    
    /**
     * Determine a point on the line which has the closest distance to the origin.
     * 
     * Implementation following:
     * https://spencerparkin.github.io/GALua/CGAUtilMath.pdf
     * 
     * TODO
     * möglicherweise geht das für alle flats?
     */
    @Override
    public Point3d location(){
        // local center = ( no .. blade ) * normal * i
        return new Point3d((createOrigin(1d).ip(this.gp(1d/weight())).
                gp(attitudeIntern())).gp(createE3()).extractEuclidianVector());
    }
}
