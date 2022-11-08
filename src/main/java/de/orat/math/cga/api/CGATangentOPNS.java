package de.orat.math.cga.api;

import de.orat.math.cga.util.Decomposition3d.RoundAndTangentParameters;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Tangents are created from touching objects, in outer product null space 
 * represenation corresponding to direct tangent in Dorst2007.
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
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
class CGATangentOPNS extends CGAKVector {
    
    CGATangentOPNS(CGAMultivector m){
        super(m.impl);
    }
    
    /**
     * Create a cga tangent object in opns representation corresponding to 
     * direct tangent in Dorst2007.
     * 
     * @param location as finite point
     * @param u k-vector representing a direction
     * @return tangent in OPNS representation
     * 
     * TODO bekomme ich hier nicht immer ein k-blade zurück?
     * 
     * fixme das ist die Formel für IPNS only????
     * TODO
     */
    /*protected static CGAMultivector create(Point3d location, CGAKVector u){
        CGARoundPointIPNS o = new CGARoundPointIPNS(location);
        //FIXME in Dorst2007 steht gradeInvolution, ist damit gradeInversion gemeint?
        // The given multivector is not of grade 2: 0
    
        return o.op(o.negate().lc(u.gradeInversion().gp(CGAMultivector.createInf(1d))));
    }*/
    
    // decomposition
    
    public Vector3d attitude(){
        CGAMultivector result = attitudeIntern();
        System.out.println("attitude="+result.toString());
        return result.extractAttitudeFromEeinfRepresentation();
    }
    /**
     * Determine attitude in X^einf representation.
     * 
     * @return attitude as X^einf
     */
    @Override
    protected CGAMultivector attitudeIntern(){
        //return attitudeFromTangentAndRound();
        return attitudeFromTangentAndRound2(this);
    }
    @Override
    public Point3d location(Point3d probe){
        throw new RuntimeException("Not available. Use location() without argument instead!");
    }
    /**
     * Determine the location.
     * 
     * @return location as finite point/dual sphere corresponding to Dorst2007
     */
    @Override
    public Point3d location(){
        CGARoundPointIPNS result = locationFromTangentAndRoundAsNormalizedSphere();
        
        //Point3d result = locationIntern(this);
        //System.out.println("location="+result.toString());
        
        //return result.extractE3ToPoint3d();
        return result.location();
    }
    /*static Point3d locationIntern(CGAKVector tangent){
        return tangent.div(createInf(-1d).lc(tangent)).extractE3ToPoint3d();
    }*/
    public double squaredSize(){
        return 0d;
    }
    /**
     * Decompose tangent.
     * 
     * Keep in mind: Corresponding to Dorst2007 dual and non-dual is switched.
     * 
     * @return direction/attitude and location, size/radius=0
     */
    public RoundAndTangentParameters decompose(){
        return new RoundAndTangentParameters(attitude(), 
                location(), squaredSize());
    }
    
    @Override
    public CGATangentOPNS inverse(){
        throw new RuntimeException("A tangent has no inverse!");
    }
}