package de.orat.math.cga.api;

import static de.orat.math.cga.api.CGAMultivector.createInf;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

/**
 * Direction bivector (grade 3).
 * 
  * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGADirectionBivector extends CGABlade implements iCGATrivector {
    
    public CGADirectionBivector(CGAMultivector m){
        super(m);
    }
    
    public CGADirectionBivector(CGABivector B){
        this(B.gp(createInf(1.0)));
    }
}
