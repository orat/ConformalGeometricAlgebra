package de.orat.math.cga.api;

import de.orat.math.cga.spi.iCGAMultivector;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Oriented point in ipns representation (grade-2).
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAOrientedPointIPNS extends CGACircleIPNS {

    public CGAOrientedPointIPNS(CGAMultivector m) {
        super(m);
    }
    protected CGAOrientedPointIPNS(iCGAMultivector impl){
        super(impl);
    }
    public CGAOrientedPointIPNS(Point3d p, Vector3d v){
        super(create(p,v));
    }
    // Vorzeichen wegen I0 ist überprüft, Formel daher anders als im Video
    // stimmt mit meinem IAS paper überein
    // @CGA("nn∧x+(0.5x²nn-x (x⋅nn))εᵢ+nnε₀-(x⋅nn)E₀")
    //FIXME bei decompose scheint das Vorzeichen der Orientierung falsch zu sein
    // oder ist die decompose()-Methode falsch?
    private static CGAMultivector create(Point3d p, Vector3d v){
        CGAEuclideanVector nq = new CGAEuclideanVector(v);
        CGAEuclideanVector q = new CGAEuclideanVector(p);
        //FIXME woher kommt die Formel?
        CGAMultivector result =  nq.op(q).add(nq.gp(0.5d*Math.abs(q.sqr().decomposeScalar()))
                .sub(q.gp(q.ip(nq))).gp(inf)).
                add(nq.gp(o)).sub(I0.gp(q.ip(nq)));
        //System.out.println(result.toString("orientedPoint"));
        return result;
    }
    
    /**
     * Create an oriented point in ipns representation.
     * 
     * Implementation following [Hildenbrand2011] tab.2, based on cross-products.<p>
     * 
     * 
     * TODO
     * ungetested
     * 
     * @param c location
     * @param n orientation
     * @return oriented point in ipns representation
     */
    private static CGAMultivector create2(Point3d c, Vector3d n){
       Vector3d cn = new Vector3d();
       cn.cross(new Vector3d(c), n);
       CGAEuclideanVector nc = new CGAEuclideanVector(n);
       CGAEuclideanVector cc = new CGAEuclideanVector(c);
       CGAMultivector result = new CGAEuclideanVector(cn);
       return result.gp(I3).add(nc.op(o)).add(cc.ip(nc).gp(inf.op(o))).
               add(nc.gp(0.5*(new Vector3d(c)).dot(cn)).sub(cc.gp(n.dot(new Vector3d(c)))).op(inf));
    }
    /**
     * Creates an oriented point in opns representation.
     * 
     * Implementation following [Hildenbrand2011], based on cross-products.<p>
     * 
     * TODO
     * ungetested
     * 
     * FIXME
     * 
     * @param c location
     * @param n orientation
     * @return oriented point in opns representation
     */
    private static CGAMultivector create2a(Point3d c, Vector3d n){
        Vector3d cn = new Vector3d(); cn.cross(new Vector3d(c),n);
        CGAEuclideanVector nc = new CGAEuclideanVector(n);
        CGAEuclideanVector cc = new CGAEuclideanVector(c);
        Vector3d cv = new Vector3d(c);
        return (new CGAEuclideanVector(cn)).gp(I3).
                add(nc.op(o)).
                add(inf.op(o).gp(cv.dot(n))).
                add(nc.gp(0.5*cv.lengthSquared()).sub(cc.gp(cv.dot(n))).op(inf));
    }
    
    
    public static boolean typeof(CGAMultivector m){
        boolean result = CGARoundIPNS.typeof(m);
        //TODO weitere Tests
        return result;
    }
    
    @Override
    public CGAOrientedPointOPNS dual(){
        return new CGAOrientedPointOPNS(impl.dual());
    }
    
    /**
     * Workaround, da CGACircleIPNS die impl CGARoundIPNS überschreibt und ein
     * falsches Vorzeichen liefert. Diese impl sollte eigentlich wieder identisch
     * sein zu der von CGARoundIPNS ...
     * 
     * @return 
     */
    @Override
    public Vector3d attitude(){
        CGAAttitudeBivectorOPNS result = new CGAAttitudeBivectorOPNS(attitudeFromTangentAndRoundIPNS());
        return result.direction();
    }
}
