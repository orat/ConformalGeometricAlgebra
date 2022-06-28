package de.orat.math.cga.api;

import org.jogamp.vecmath.Vector3d;

/**
 * Grade 2 vector
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
 * was ist mit position vector (grade 3) und normal vector (grade 1)?
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGATangentVector extends CGATangent implements iCGABivector {
     
    public CGATangentVector(CGAMultivector m){
        super(m);
    }
    
    public CGATangentVector createCGATangentVector (Vector3d t){
        return new CGATangentVector(createOrigin(1.0).gp(new CGAMultivector(t)));
    }
}
