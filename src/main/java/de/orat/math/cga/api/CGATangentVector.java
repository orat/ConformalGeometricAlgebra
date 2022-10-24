package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * A vector with direction u at point location o (grade 2).
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
 * TODO
 * was ist mit normal vector (grade 1)?
 * 
 * The use of tangent blades is an elegant alternative to represent vertices in
 * a mesh, because they encode both the positional as the tangential information 
 * in a simple primitive element. 
 * 
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATangentVector extends CGATangentOPNS implements iCGABivector {
     
    public CGATangentVector(CGAMultivector m){
        super(m);
    }
    
    public CGATangentVector createCGATangentVector (Vector3d t){
        return new CGATangentVector(createOrigin(1.0).gp(new CGAMultivector(t)));
    }
}
