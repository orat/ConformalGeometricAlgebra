package de.orat.math.cga.api;

import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 * 
 * Tangent in inner product null space representation corresponding to dual 
 * tangent in Dorst2007.
 * 
 * Pure tangents have zero size but a finite weight. 
 * 
 * They are created
 * by wedging any Euclidean element (vector, bivector, or trivector) with the origin o.
 * We explore uses of tangent vectors as generators at the origin of the form ot in Sec-
 * tion 4. Translation of such elements returns an element very similar to a Point Pair.
 * Future work will require more rigorous examination of tangent bivectors, which are
 * closely related to circles, to generate implicit surfaces, and pure tangent trivectors
 * as zero-sized spheres to generate implicit volumes.

 */
class CGATangentIPNS extends CGAKVector {
    
    CGATangentIPNS(CGAMultivector m){
        super(m.impl);
    }
    
    /**
     * Create a cga tangent object in opns representation corresponding to 
     * direct tangent in Dorst2007.
     * 
     * @param location
     * @param u k-vector
     * @return tangent in IPNS representation
     * 
     * TODO bekomme ich hier nicht immer ein k-blade zurück?
     * ist das nicht die Formel für IPNS
     */
    protected static CGAMultivector create(Point3d location, CGAKVector u){
        CGARoundPointIPNS p = new CGARoundPointIPNS(location);
        // following Dorst2007 page 406 or Fernandes2009 (supplementary material B)
        return p.op(p.negate().lc(u.gradeInversion().gp(CGAMultivector.createInf(1d))));
    }
    
    public Vector3d attitude(){
        CGAMultivector result = attitudeIntern();
        System.out.println("attitude="+result.toString());
        return result.extractE3ToVector3d();
    }
    @Override
    protected CGAMultivector attitudeIntern(){
        //return attitudeFromDualTangentAndDualRound();
        return attitudeFromTangentAndRound2(this);
    }
    @Override
    public Point3d location(Point3d probe){
        throw new RuntimeException("Not available. Use location() without argument instead!");
    }
    @Override
    public Point3d location(){
        //return CGATangentOPNS.locationIntern(this);
        CGAMultivector result = locationFromTangentAndRoundAsNormalizedSphere();
        System.out.println("location="+result.toString());
        return extractE3ToPoint3d();
    }
    public double squaredSize(){
        return 0d;
    }
    /**
     * Decompose tangent.
     * 
     * Keep in mind: Corresponding to Dorst2007 dual and not-dual ist switched.
     * 
     * @return direction/attitude and location, size/radius=0
     */
    public RoundAndTangentParameters decompose(){
        return new RoundAndTangentParameters(attitude(), 
                location(), squaredSize());
    }
    
    @Override
    public CGATangentIPNS inverse(){
        throw new RuntimeException("A tangent has no inverse!");
    }
}